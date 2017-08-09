package todolistgui;

import javafx.scene.control.Alert;

/**
 *
 * @author William Hess
 */
public class TaskExistsException extends Exception {

    public TaskExistsException() {
        super("User entered a task that already exists!");
    }

    public void alertUser() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Woah! What happened?");
        alert.setHeaderText(null);
        alert.setContentText("It seems like you tried to add a task that already exists, "
                + "please try again with a different item name.");

        alert.showAndWait();
    }
}
