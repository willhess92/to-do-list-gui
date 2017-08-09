package todolistgui;

import java.time.LocalDate;
import java.sql.Date;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

/**
 *
 * @author William Hess
 */
public class NewItemPane extends GridPane {
    private HBox itemNameBox = new HBox(8);
    private Label itemName = new Label("Item Name");
    private Line line = new Line(0, 0, 110, 0);
    
    private TextField itemTF = new TextField();
    
    private HBox deadlineBox = new HBox(8);
    private Label deadlineLabel = new Label("Deadline");
    private Line line2 = new Line(0, 0, 129, 0);
    
    private DatePicker deadlinePicker = new DatePicker(LocalDate.now());
    private Button addItemBtn = new Button("Add Item");

    public NewItemPane() {
        setNewItemPaneProperties();
        setAddButtonProperties();
        setTFProperties();
        setLabelProperties();
        setHBoxProperties();
        addNodes();
    }

    public void resetFields() {
        itemTF.setText(null);
        deadlinePicker.setValue(LocalDate.now());
    }

    public String getItemTFString() {
        return itemTF.getText();
    }

    public TextField getItemTF() {
        return itemTF;
    }
    
    public Date getDeadlineDate() throws NullDateException {
        // The following converts the Local Date from the Date Picker to
        // a Date from java.sql.Date
        try {
            Date deadline = Date.valueOf(deadlinePicker.getValue());
            return deadline;
        } catch(Exception e) {
            throw new NullDateException();
        }
    }

    private void setNewItemPaneProperties() {
        super.setMaxSize(250, 200);
        super.setAlignment(Pos.TOP_CENTER);
        super.setPadding(new Insets(20, 20, 20, 20));
        super.setVgap(2);
        super.setHgap(10);
        super.setStyle("-fx-background-color: linear-gradient(to bottom right, #00c9ff, #92fe9d);"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0);"
                + "-fx-border-width: 1;"
                + "-fx-border-color: black;");
    }
    
    private void addNodes() {
        super.addColumn(0, itemNameBox, itemTF, deadlineBox, deadlinePicker, addItemBtn);
    }

    private void setHBoxProperties() {
        setLineProperties();
        itemNameBox.getChildren().addAll(itemName, line);
        deadlineBox.getChildren().addAll(deadlineLabel, line2);
    }

    private void setLineProperties() {
        line.setTranslateY(15);
        line.setOpacity(0.5);
        line2.setTranslateY(15);
        line2.setOpacity(0.5);
    }

    private void setTFProperties () {
//        itemTF.requestFocus();
        itemTF.setStyle("-fx-background-color: linear-gradient(to right, #00AAD8, #53CB9D);");
        deadlinePicker.setStyle("-fx-base: #32C0BA;" +
                "-fx-control-inner-background: #32C0BA;");
    }
    
    private void setLabelProperties() {
        String style = "-fx-font-size: 18;";
        itemName.setStyle(style);
        deadlineLabel.setStyle(style);
    }
    
    private void setAddButtonProperties() {
        // position the button in the bottom right corner
        GridPane.setHalignment(addItemBtn, HPos.RIGHT);
        addItemBtn.setTranslateY(13);
        // set the button style on hover
        addItemBtn.setStyle("-fx-font-size: 14;" +
                "-fx-background-color: #5CCA9A;");
        addItemBtn.setOnMouseEntered(e -> {
            addItemBtn.setStyle("-fx-font-size: 14;" +
                    "-fx-background-color: #42916F;" +
                    "-fx-cursor: hand;");
        });
        addItemBtn.setOnMouseExited(e -> {
            addItemBtn.setStyle("-fx-font-size: 14;" +
                    "-fx-background-color: #5CCA9A;");
        });
    }
    
    public Button getAddItemBtn() {
        return addItemBtn;
    }
}
