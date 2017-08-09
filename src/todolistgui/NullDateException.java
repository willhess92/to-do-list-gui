package todolistgui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author William Hess
 */
public class NullDateException extends Exception {
    public NullDateException() {
        super("User failed to select a date");
    }
    
    public void alertUser() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Woah! What happened?");
        alert.setHeaderText(null);
        alert.setContentText("It seems like you didn't enter a date for that task, "
                + "please try again.");

        alert.showAndWait();
    }
    
}
