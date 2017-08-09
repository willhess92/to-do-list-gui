package todolistgui;

import javafx.scene.control.Alert;

/**
 *
 * @author William Hess
 */
public class NoTasksStartedException extends Exception {
    public NoTasksStartedException() {
        super("User didn't have any started tasks!");
    }

    public void alertUser() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Woah! What happened?");
        alert.setHeaderText(null);
        alert.setContentText("Sorry, but you don't have any started items to show.");
        
        alert.showAndWait();
    }
}
