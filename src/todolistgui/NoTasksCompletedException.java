package todolistgui;

import javafx.scene.control.Alert;

/**
 *
 * @author William Hess
 */
public class NoTasksCompletedException extends Exception{
    public NoTasksCompletedException() {
        super("User didn't have any completed tasks!");
    }

    public void alertUser(String problem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Woah! What happened?");
        alert.setHeaderText(null);
        switch (problem) {
            case "deletionIssue":
                alert.setContentText("Unfortunately, you haven't completed anything in your"
                + " To-Do List.  Therefore, I cannot delete something that doesn't exist."
                + "  Why don't you try to complete something, and try again.");
                break;
            case "displayIssue":
                alert.setContentText("Sorry, but you don't have any completed items to show.");
                break;
        }
        alert.showAndWait();
    }
}
