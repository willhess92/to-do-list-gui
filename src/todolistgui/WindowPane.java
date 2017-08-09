package todolistgui;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 *
 * @author William Hess
 */
public class WindowPane extends StackPane {
    private ArrayList<Item> toDoList;
    
    // Label is based on specific progress, and Integer contains the index of the item in the toDoList
    private LinkedHashMap<Label, Integer> specificList = new LinkedHashMap<Label, Integer>();
    
    private ArrayList<Label> labelList = new ArrayList<>();
    private ListView<Label> itemsLV = new ListView<>();
    private ObservableList<Label> obvList = FXCollections.observableList(labelList);
    
    private HBox buttonOverlay = new HBox();
    private Button newItemButton = new Button("+");
    
    private VBox noItemsPane = new VBox(25);

    public WindowPane(ArrayList<Item> toDoList) {
        this.toDoList = toDoList;
        itemsToLabels("all"); // convert all items in the toDoList to labels in labelList
        setWindowPaneProperties();
        setListViewProperties();
        setButtonOverlayProperties();
        setNewItemButtonProperties();

        if (toDoList.isEmpty()) { // add noItemsPane instead of listview
            setNoItemsPaneProperties();
            super.getChildren().addAll(noItemsPane, buttonOverlay);
        } else {
            super.getChildren().addAll(itemsLV, buttonOverlay);
        }
    }

    /**
     * The getIndexFromLabel() method takes a label corresponding to an item in 
     * the toDoList and returns the index of this item in toDoList.  This method
     * should be called when a specific list of some progress is being displayed,
     * and the user is attempting to edit the item corresponding to the selected
     * label.
     * 
     * @param label
     * @return index of item in toDoList from label representation
     */
    
    public int getIndexFromLabel (Label label) {
        return specificList.get(label);
    }

    /**
     * The itemsToLabels() method converts items to their label representation
     * based on the selected progress for display in the list view.
     * 
     * @param selectedProgress "all"; "started"; "completed"
     */
    
    private void itemsToLabels(String selectedProgress) {
        switch (selectedProgress) {
            case "all":
                for (Item item : toDoList) {
                    labelList.add(new Label(item.toString()));
                }

                for (Label label : labelList) {
                    label.setStyle("-fx-font-size: 16;");
                }
                break;
            case "started":
            case "completed":
                specificList.clear();
                for (int i = 0; i < toDoList.size(); i++) {
                    // if the item has the selected progress (i.e. user only wants to display "started" or "completed"
                    if (toDoList.get(i).getStatus().equals(selectedProgress)) {
                        // store in linked hashmap with index of item in toDoList
                        specificList.put(new Label(toDoList.get(i).toString()), i);
                    }
                }

                // Add selected items to labelList
                for (Label label : specificList.keySet()) {
                    label.setStyle("-fx-font-size: 16;");
                    labelList.add(label);
                }
                break;
        }
    }

    /**
     * The refreshLabelList() method updates the list view with labels based on
     * the users selectedProgress.
     * 
     * @param selectedProgress 
     */
    
    public void refreshLabelList(String selectedProgress) {
//        System.out.println("Refreshing List...");
        labelList.clear();
        // convert specific items to labels
        itemsToLabels(selectedProgress);
        obvList = FXCollections.observableList(labelList);
        itemsLV.setItems(obvList);
        itemsLV.refresh();
    }

    private void setWindowPaneProperties() {
        super.setAlignment(Pos.BOTTOM_RIGHT);
        // set an innershadow with offset to position on top and left edges
        super.setStyle("-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 2, 2);");
    }

    private void setNoItemsPaneProperties() {
        Label content = new Label("It looks like your To-Do List is empty!  Lets change that by creating" +
                " a new task here.");
        content.setStyle("-fx-font-size: 22;" +
                "-fx-wrap-text: true;");
        // Arrow pointing to newItemButton
        ImageView pointer = new ImageView(new Image("todolistgui/icons/arrowOrange.png"));
        pointer.setTranslateX(-75);
        pointer.setTranslateY(-32);

        noItemsPane.setAlignment(Pos.BOTTOM_RIGHT);
        noItemsPane.setPadding(new Insets(20, 20, 20, 20));
        noItemsPane.setStyle("-fx-background-color: linear-gradient(to right, #00c9ff, #92fe9d);" +
                "-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 2, 2);");
        noItemsPane.getChildren().addAll(content, pointer);
    }

    /**
     * The replaceNoItemsPane() replaces the noItemsPane with the list view containing
     * toDoList items. This method should be called when the toDoList is no longer 
     * empty.
     */
    
    public void replaceNoItemsPane() {
        super.getChildren().remove(noItemsPane);
        super.getChildren().add(0, itemsLV);
    }

    private void setListViewProperties() {
        itemsLV.setItems(obvList);
    }

    public ListView<Label> getItemsLV() {
        return itemsLV;
    }
    
    private void setButtonOverlayProperties() {
        buttonOverlay.setMaxSize(5, 5); // make the size of hbox smaller than button
        buttonOverlay.setPadding(new Insets(0, 35, 25, 0));
        buttonOverlay.getChildren().add(newItemButton);
    }
    
    private void setNewItemButtonProperties() {
        String style = "-fx-font-size: 26;"
                + "-fx-text-fill: white;"
                + "-fx-base: orange;"
                + "-fx-focus-color: transparent;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 3);";
        newItemButton.setStyle(style);
        newItemButton.setShape(new Circle(1));
        newItemButton.setOnMouseEntered(e -> {
            newItemButton.setStyle(style + "-fx-cursor: hand;");
        });
    }
    
    public Button getNewItemButton() {
        return newItemButton;
    }

    /**
     * The blur() method blurs all nodes currently in the WindowPane with a 
     * Gaussian blur.
     */
    
    public void blur() {
        for (Node node: super.getChildren()) {
            node.setEffect(new GaussianBlur());
        }
    }

    /**
     * The unBlur() method removes the Gaussian blur effect from nodes currently
     * in the WindowPane.
     */
    
    public void unBlur() {
        for (Node node: super.getChildren()) {
            node.setEffect(null);
        }
    }
}
