package todolistgui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author William Hess
 */
public class AccessoryPane extends VBox{
    private Label content = new Label();
    private Label sig = new Label("-W.A.H.");
    
    public AccessoryPane() {
        setAccessoryPaneProperties();
        setContentProperties();
        setSigProperties();
        super.getChildren().addAll(content, sig);
    }
    
    private void setAccessoryPaneProperties() {
        super.setAlignment(Pos.CENTER_RIGHT);
        super.setSpacing(5);
        super.setPadding(new Insets(20, 20, 20, 20));
        super.setStyle("-fx-background-color: linear-gradient(to right, #00c9ff, #92fe9d);");
//        super.setStyle("-fx-background-color: linear-gradient(to right, #00c9ff, #92fe9d);" +
//                "-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 2, 2);");
    }
    
    private void setContentProperties() {
        content.setText("This application was created to satisfy an assignment in a second"
                + " semester, freshmen level, CS course taught in Java.  Enjoy!\n");
        content.setStyle("-fx-font-size: 22;" +
                "-fx-wrap-text: true;");
    }
    
    private void setSigProperties() {
        sig.setStyle("-fx-font-size: 20;");
    }
    
}
