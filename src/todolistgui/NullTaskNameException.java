package todolistgui;

import javafx.scene.control.Alert;

/**
 *
 * @author William Hess
 */
public class NullTaskNameException extends Exception {
    
    public NullTaskNameException() {
        super("User didn't give the task a name!");
    }

    public void alertUser() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Woah! What happened?");
        alert.setHeaderText(null);
        alert.setContentText("It seems like you tried to add a task without giving it a name, "
                + "please try again while specifying an item name.");

        alert.showAndWait();
    }
}
