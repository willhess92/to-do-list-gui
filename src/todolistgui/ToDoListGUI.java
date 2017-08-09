
package todolistgui;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Collections;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


/**
 * CS1181L-C05
 * Instructor: Dr. Cheatham
 * Project2
 * 
 * This project allows the user to create a persistent To-Do list, and conduct
 * the following operations in an attractive GUI:
 *      -Create a new task
 *      -Mark an item as started
 *      -Mark an item as completed
 *      -List the items currently in the list
 *      -Sort items by date or name
 *      -Display a specific set of items (i.e. started, or completed items)
 *      -Remove completed items
 *      -Save To-Do list on exit
 *
 * When creating new items, the user must specify a label for the item/task and
 * a date. When marking an item as in progress, the user will need to click
 * the item they wish to start, and specify the date of which it was started.  When
 * marking an item as completed, the process is similar, though excluding the need 
 * for a date. 
 * 
 * 
 * @author William Hess
 */

public class ToDoListGUI extends Application {

    /**
     * The homeScreen() method is used to revert back to the "home screen" of the 
     * program when other nodes are stacked on it. 
     */
    public static void homeScreen() {
        // Removal of the option Menu, if displayed
        if (optionMenuOn) {
//            System.out.println("Removing nodes...");
            root.getChildren().remove(1, root.getChildren().size()); // delete all nodes except for the underlying pane
            // Reset the option Menu to off
            optionMenuOn = false;
        } else {
            // Removal of pane overlaying the border pane center
            BorderPane bp = ((BorderPane) root.getChildren().get(0)); // select the boarderpane in root
            StackPane sp = ((StackPane) bp.getChildren().get(1)); // select the windowpane at the center of the borderpane
            // if something more than the itemsPane and addItemButton is there, remove it
            if (sp.getChildren().size() > 2) {
                sp.getChildren().remove(2);
                // reset the heading to "Your To-Do List :)"
                ((MenuBar) bp.getChildren().get(0)).setHeading();
            }
        } 
    }

    /**
     * The importList() method takes a file name as a string corresponding to a
     * binary file containing an ArrayList object, and returns the object.
     * @param filename
     * @return ArrayList
     */

    public static ArrayList<Item> importList(String filename) {
        System.out.println("Importing to-do list...");
        File objFile = new File(filename);
        try {
            // Attempt to load the binary file specified, and return the arraylist
            // Object of type Item within the file.
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(objFile));
            return (ArrayList<Item>) ois.readObject();
        } catch (Exception e) {
            // If the file does not exist, return an empty ToDoList
            System.out.println("Creating new to-do list...");
            return new ArrayList<>();
        }
    }

    /**
     * The exportList() method takes a file name as a string corresponding to a
     * binary file that the To-Do List should be wrote to.
     * @param filename
     */

    public static void exportList(String filename) {
        System.out.println("Exporting to-do list...");
        File objFile = new File(filename);
        try {// Attempt to write the To-Do list to the file specified
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(objFile));
            oos.writeObject(toDoList);
            oos.close();
            System.out.println("Your To-Do List has been saved.");
        } catch (Exception e) {
            // If something went wrong in the process, display an error message
            System.err.println("Error writing to file!");
        }
    }

    /**
     * The createTask() method adds an item to the To-Do list if the task doesn't 
     * already exist.
     *
     * @param taskName // name of the item to be created
     * @param deadline // date corresponding to the items due date
     * @throws todolistgui.NullTaskNameException
     * @throws todolistgui.TaskExistsException
     */

    public static void createTask(String taskName, Date deadline) throws NullTaskNameException, TaskExistsException {
        if (taskName == null || taskName.equals("")) {
            throw new NullTaskNameException();
        }
        Item tempItem = new Item(taskName, deadline);
        // verify the item isn't already in the toDoList
        if (!toDoList.contains(tempItem)) {
            toDoList.add(tempItem);
        } else {
            throw new TaskExistsException();
        }
    }

    /**
     * The isAny() method checks to see if an item in the toDoList has the specified
     * progress ("started" or "completed"). If at least one item is found, the
     * method returns true.
     * 
     * @param progress
     * @return true:    item found with specified progress
     *         false:   no items with the specified progress exist in the toDoList
     */
    
    public static boolean isAny(String progress) {
        for (Item item : toDoList) {
            if (item.getStatus().equals(progress)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The sortToDoList() method sorts the items in the toDoList by the specified
     * sort method.
     * 
     * @param sortMethod "date" or "name"
     */
    
    public static void sortToDoList(String sortMethod) {
        switch (sortMethod) {
            case "date":
                // sort according to compareTo method in Item class
                Collections.sort(toDoList);
                break;
            case "name":
                // sort by standard compareTo method for strings with a comparator
                Collections.sort(toDoList, (a, b) -> a.getLabel().compareToIgnoreCase(b.getLabel()));
                break;
        }
    }

      /**
     * The removeCompleted() method removes completed items from the To-Do list
     * after verifying that the user wishes to complete this operation. If the 
     * user calls this method, but there aren't any completed items in the toDoList,
     * the method with throw a NoTasksCompletedException.
     * 
     * @return 
     * @throws todolistgui.NoTasksCompletedException
     */
    
    public static boolean removeCompleted() throws NoTasksCompletedException {
        // verify that there are completed items to remove
        boolean delete = isAny("completed");
        if (!delete) {
            throw new NoTasksCompletedException();
        } else {
            // verify that the user wishes to continue with deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirming Your Decision");
            alert.setHeaderText("I'M ABOUT TO DELETE THINGS!");
            alert.setContentText("Are you sure you want to remove completed items "
                    + "from your To-Do List?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // remove completed items
                System.out.println("Removing Completed Items...");
                for (int i = 0; i < toDoList.size(); i++) {
                    if (toDoList.get(i).getStatus().equals("completed")) {
                        toDoList.remove(i); // remove completed item
                        i--; // i must be reset to its prior iteration since the
                             // size of the toDoList is dynamically changing with 
                             // each successive removal
                    }
                }
                return delete; // true: successfully deleted completed items
            } else {
                // User chose CANCEL or closed the dialog
                delete = false;
                return delete;
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private static StackPane root = new StackPane();
    private static boolean optionMenuOn = false; // since the option menu is not displayed initially
    private static String sortMethod = "date";  // The toDoList is sorted by the date by default. This can also be sorted by "name"
    private static String listToDisplay = "all"; // Portion of the toDoList to display. (default: "all"; others: "started" or "completed")
    private static ArrayList<Item> toDoList = importList("./ToDoList.dat");
    private static int startSize = toDoList.size(); 
    
    @Override
    public void stop() {
        // sort the toDoList by date
        sortToDoList("date");
        // save toDoList
        exportList("./ToDoList.dat");
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        WindowPane windowPane = new WindowPane(toDoList);
        NewItemPane newItemPane = new NewItemPane();
        MenuBar menuBar = new MenuBar();
        OptionPane optionPane = new OptionPane();
        SortByPane sortByPane = new SortByPane();

        borderPane.setTop(menuBar);
        borderPane.setCenter(windowPane);
        ListView<Label> itemsLV = windowPane.getItemsLV();
        ListView<Label> optionList = optionPane.getOptionList();
        
        root.getChildren().add(borderPane);
        Scene scene = new Scene(root, 350, 400);
        primaryStage.setMinWidth(350);
        primaryStage.setMinHeight(400);
        primaryStage.getIcons().add(new Image("todolistgui/icons/checkmark.png"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("To-Do List GUI");
        primaryStage.show();

        // The following code contains event handlers for nodes that need access
        // to other nodes in the application.

        menuBar.getAccessoryButton().setOnAction(e -> {
            if (menuBar.isBackButtonOn()) { // if the accessory button is acting as a back button
//                System.out.println("Back Button Pressed!");
                homeScreen(); // revert to homescreen nodes
                menuBar.showOptionButton();
                menuBar.toggleBackButton();
                menuBar.setAccessoryButtonImage();
                windowPane.unBlur();
            } else {
//                System.out.println("Accessory Button Pressed!");
                homeScreen(); // since the option pane might be displayed
                menuBar.setHeading("Accessories?");
                menuBar.setBackButton();
                windowPane.getChildren().add(new AccessoryPane());
            }
        });

        
        menuBar.getOptionButton().setOnAction(e -> {
            // only run if the option Menu isn't already being displayed
            if (!optionMenuOn) {
//                System.out.println("Displaying option Menu...");
                root.setAlignment(Pos.TOP_RIGHT); // sets the alignment of the optionMenu to the upper right
                root.getChildren().add(optionPane);
                // Animate the optionPane to come from the top right corner
                Line path = new Line(230, -84, 115, 84);
                PathTransition pt = new PathTransition(Duration.millis(200), path, optionPane);
                pt.play();
                optionMenuOn = true;  // since the option Menu is now being displayed
            }
        });
        
        menuBar.setOnMouseClicked(e -> {
            // Only remove overlaying nodes when clicking the menubar if the node to be removed
            // is the option Menu.
            if (optionMenuOn) {
                homeScreen();
            }
        });

        optionList.setOnMouseClicked(e -> {
            Label selectedOption = optionList.getSelectionModel().getSelectedItem(); // get the selected option
            // Should an option be selected in the list other than "Sort By" and sortByPane is displayed,
            // remove the sortByPane before continuing.
            if (root.getChildren().contains(sortByPane)) {
                root.getChildren().remove(sortByPane);
            }
            switch (selectedOption.getText()) {
                case "All Tasks": // Display all tasks
                    listToDisplay = "all";
                    windowPane.refreshLabelList(listToDisplay);
                    break;
                case "Started Tasks": // Display only started tasks
                    // verify that there are started tasks
                    if (isAny("started")) {
                        listToDisplay = "started";
                        windowPane.refreshLabelList(listToDisplay);
                    } else {
                        NoTasksStartedException ex = new NoTasksStartedException();
                        System.err.println(ex.getMessage());
                        ex.alertUser();
                    }
                    break;
                case "Completed Tasks": // Display only completed tasks
                    // verify that there are completed tasks
                    if (isAny("completed")) {
                        listToDisplay = "completed";
                        windowPane.refreshLabelList(listToDisplay);
                    } else {
                        NoTasksCompletedException ex = new NoTasksCompletedException();
                        System.err.println(ex.getMessage());
                        ex.alertUser("displayIssue");
                    }
                    break;
                case "Sort By": // Display the sortByPane next to this option
                    root.getChildren().add(sortByPane);
                    // Animate the sortByPane to come in from the right just under the Sort By option in the optionPane
                    Line path = new Line(230, 117, 75, 117);
                    PathTransition pt = new PathTransition(Duration.millis(200), path, sortByPane);
                    pt.play();
                    break;
                case "Remove Completed Tasks": 
                    try {
                        // attempt to remove completed items
                        boolean tasksDeleted = removeCompleted();
                        // Refresh the list if tasks were deleted
                        if (tasksDeleted) {
                            sortToDoList(sortMethod);
                            windowPane.refreshLabelList(listToDisplay);
                        }
                    } catch (NoTasksCompletedException ex) {
                        System.err.println(ex.getMessage());
                        ex.alertUser("deletionIssue");
                    }
                    break;
            }
        });

// sortByPane RadioButtons -----------------------------------------------------
        sortByPane.getRbDate().setOnAction(e -> {
//            System.out.println("Sorting by Date...");
            sortToDoList("date");
            windowPane.refreshLabelList(listToDisplay);
        });

        sortByPane.getRbName().setOnAction(e -> {
//            System.out.println("Sorting by Name...");
            sortToDoList("name");
            windowPane.refreshLabelList(listToDisplay);
        });

// sortByPane RadioButtons ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        
        newItemPane.getAddItemBtn().setOnAction(event -> {
//            System.out.println("Add Item Button Pressed!");
            try {
                String taskName = newItemPane.getItemTFString();
                Date date = newItemPane.getDeadlineDate();
                createTask(taskName, date); // attempt to create the new item
                sortToDoList(sortMethod); // since the item will be added to the end of the list
                // If the toDoList was empty, meaning that the noItemsPane was displayed, then replace it
                // with the listview of the toDoList.
                if (startSize == 0) {
                    windowPane.replaceNoItemsPane();
                    startSize = 1; // Change the start size, so this statement only runs once
                }
                windowPane.refreshLabelList(listToDisplay);
                // Now that the item has been add successfully, the back button is
                // fired to revert back to the homescreen, and unblur nodes. 
                menuBar.getAccessoryButton().fire(); 
            } catch (NullTaskNameException e) { // the user failed to enter a task name
                System.err.println(e.getMessage());
                e.alertUser();
            } catch (TaskExistsException e) { // the task already exists
                System.err.println(e.getMessage());
                e.alertUser();
            } catch(NullDateException e) { // there wasn't a deadline date specified
                System.err.println(e.getMessage());
                e.alertUser();
            } 
        });
        
        windowPane.getNewItemButton().setOnAction(e -> {
//            System.out.println("New Item Button Pressed!");
            newItemPane.resetFields(); // reset the textfield and date picker to defaults
            homeScreen(); // since the option pane might be displayed
            menuBar.setHeading("Create New Item");
            menuBar.setBackButton();
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(newItemPane);
            windowPane.blur(); // blur the underlying nodes
            windowPane.getChildren().add(stackPane);
            newItemPane.getItemTF().requestFocus(); // so the user can start typing task name immediately 
            // Fade in the newItemPane
            FadeTransition ft = new FadeTransition(Duration.millis(300), newItemPane);
            ft.setFromValue(0);
            ft.setToValue(1.0);
            ft.play();
        });

        itemsLV.setOnMouseClicked(e -> {
            /* The contents for this lambda expression are only run if the back button is not displayed.
               There was a bug whereby the user could select an item from the list by clicking the top edge of the window
               pane, even when covered with the editItemPane or newItemPane.  When clicking on this edge, the user can still
               select the last selected item, if not null, (selection will turn blue in background). However, this kind of
               selection no longer results to anything. */
            if (!menuBar.isBackButtonOn()) {
                homeScreen();
                Label selectedLabel = itemsLV.getSelectionModel().getSelectedItem(); // get the selected item
                if (selectedLabel == null) {
                    return;
                }
                int indexOfItem;
                if (listToDisplay.equals("all")) {
                    indexOfItem = itemsLV.getSelectionModel().getSelectedIndex();
                } else { // when only the "started" or "completed" tasks are being displayed
                    // get the index of the item in the toDoList from the specific list hashmap in windowPane
                    indexOfItem = windowPane.getIndexFromLabel(selectedLabel);
                }
                
                EditItemPane editItemPane = new EditItemPane(selectedLabel);

// Button Properties for EditItemPane ------------------------------------------

                editItemPane.getStartBtn().setOnAction(event -> {
//                    System.out.println("Start Item Button Pressed!");
                    try {
                        Date date = editItemPane.getStartDate();
                        toDoList.get(indexOfItem).setProgress("started", date); // mark the item as started
                        sortToDoList(sortMethod); // this might not be necessary 
                        windowPane.refreshLabelList(listToDisplay);
                        menuBar.getAccessoryButton().fire();
                    } catch (NullDateException exception) {
                        System.err.println(exception.getMessage());
                        exception.alertUser();
                    }
                });
                editItemPane.getCompleteBtn().setOnAction(event -> {
//                    System.out.println("Complete Button Pressed!");
                    toDoList.get(indexOfItem).setProgress("completed", null); // mark the item as completed
                    sortToDoList(sortMethod); // this might not be necessary
                    windowPane.refreshLabelList(listToDisplay);
                    menuBar.getAccessoryButton().fire();
                });
                
// Button Properties for EditItemPane ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

                menuBar.setHeading("Edit Item");
                menuBar.setBackButton();
                StackPane centerPane = new StackPane();
                centerPane.getChildren().add(editItemPane);
                windowPane.blur();
                windowPane.getChildren().add(centerPane);

                // Fade in the editItemPane
                FadeTransition ft = new FadeTransition(Duration.millis(300), editItemPane);
                ft.setFromValue(0);
                ft.setToValue(0.95);
                ft.play();
            }
        });

    }
    
}
