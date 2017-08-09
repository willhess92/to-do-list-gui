package todolistgui;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 *
 * @author William Hess
 */
public class OptionPane extends VBox {
    private ArrayList<Label> optionMenu = new ArrayList<>();
    private ObservableList<Label> obvOptions = FXCollections.observableList(optionMenu);
    private ListView<Label> optionList = new ListView<>();
    
    public OptionPane() {
        setOptions();
        optionList.setItems(obvOptions);
        optionList.setOnMouseClicked(e -> {
            String s = optionList.getSelectionModel().getSelectedItem().toString();
//            System.out.println("Option Selected is " + s);
        });
        super.setMaxSize(225, 167);
        super.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 0);");
        super.getChildren().add(optionList);
    }

    private void setOptions() {
        optionMenu.add(new Label("All Tasks"));
        optionMenu.add(new Label("Started Tasks"));
        optionMenu.add(new Label("Completed Tasks"));
        optionMenu.add(new Label("Sort By"));
        optionMenu.add(new Label("Remove Completed Tasks"));
        // Set their font size
        for (Label label : optionMenu) {
            label.setStyle("-fx-font-size: 18;");
        }
    }
    
    public ListView<Label> getOptionList() {
        return optionList;
    }
    
}