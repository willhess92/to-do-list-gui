package todolistgui;

import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * 
 * @author William Hess
 */
public class SortByPane extends VBox {
    ToggleGroup group = new ToggleGroup();
    RadioButton rbDate = new RadioButton("Date");
    RadioButton rbName = new RadioButton("Name");

    public SortByPane() {
        setSortByPaneProperties();
        setRadioButtonProperties();
        super.getChildren().addAll(rbDate, rbName);
    }

    public RadioButton getRbDate() {
        return rbDate;
    }

    public RadioButton getRbName() {
        return rbName;
    }
    
    private void setRadioButtonProperties() {
        rbDate.setToggleGroup(group);
        rbName.setToggleGroup(group);
        rbDate.setSelected(true);
        String style = "-fx-font-size: 14;" +
                "-fx-text-fill: black;" +
                "-fx-base: #00AEDE;";
        rbDate.setStyle(style);
        rbName.setStyle(style);
    }

    private void setSortByPaneProperties() {
        super.setMaxSize(150, 45);
        super.setPadding(new Insets(7, 7, 6, 7));
        super.setSpacing(10);
        super.setStyle("-fx-background-color: white;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 0);");
    }
}
