package todolistgui;

import java.time.LocalDate;
import java.sql.Date;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

/**
 *
 * @author William Hess
 */
public class EditItemPane extends GridPane{
    private Label taskNameLabel = new Label();
    private Line line = new Line(0, 0, 210, 0);
    private Label deadlineLabel = new Label();
    private Label progressLabel = new Label();
    
    private DatePicker startDatePicker = new DatePicker(LocalDate.now());
    private Button startBtn = new Button("Start");
    
    private Button completeBtn = new Button("Complete");

    public EditItemPane(Label selectedItem) {
        setEditItemPaneProperties();
        setButtonProperties();
        setDatePickerProperties();
        setLineProperties();
        addNodes();
        parseLabel(selectedItem);
        setLabelStyles();
    }

    /**
     * The parseLabel() method takes the selected item's label, and reorganizes
     * it's contents to a preferred style in the EditItemPane.
     * 
     * @param selectedItem 
     */
    
    private void parseLabel(Label selectedItem) {
        String fullLabel = selectedItem.getText();
        // Split the fullLabel up by line
        String[] strings = fullLabel.split("\n -");
        // Everything before the escape character is the Task name
        String taskNameStr = strings[0];
        String deadlineStr;
        String progressStr = "";
        // If the task has some kind of progress i.e. (started or completed),
        // parse accordingly
        if (strings[1].contains("(started") || strings[1].contains("(completed)")) {
            deadlineStr = strings[1].split(" \\(")[0];
            progressStr = strings[1].split(" \\(")[1].replace(")", "");
            progressStr = progressStr.toUpperCase();
            if (progressStr.contains("STARTED")) {
                // if the progress is already started, remove start fields
                super.getChildren().removeAll(startDatePicker, startBtn);
                // make the complete button an option
                super.add(completeBtn, 0, 5, 2,  1);
            } else if (progressStr.equals("COMPLETED")) {
                // if completed, remove all progress related fields
                super.getChildren().removeAll(startDatePicker, startBtn, completeBtn);
            }
        } else {
            // The task doesn't have its progress set, therefore only the tasks
            // deadline exists.
            deadlineStr = strings[1];
        }
        // set labels to their string representations
        taskNameLabel.setText(taskNameStr);
        deadlineLabel.setText(deadlineStr);
        // Display the selected item to the console
        System.out.println("\nSelected Item:");
        System.out.println("\tItem Name: " + taskNameStr);
        System.out.println("\tDue Date: " + deadlineStr);
        
        // When there is some progress set, create the progress Label
        if (!progressStr.equals("")) {
           System.out.println("\tProgress: " + progressStr); 
           progressLabel.setText(progressStr);
        }

        
        
    }
    
    private void addNodes() {
        super.add(taskNameLabel, 0,0);
        super.add(line, 0, 1, 2, 1);
        super.add(deadlineLabel, 0, 2);
        super.add(progressLabel, 0, 3);
        super.add(startDatePicker, 0, 4);
        super.add(startBtn, 1, 4);
    }

    private void setLineProperties() {
        line.setOpacity(0.6);
    }

    private void setLabelStyles() {
        taskNameLabel.setStyle("-fx-font-size: 18;"
                + "-fx-font-weight: bold;"
                + "-fx-wrap-text: true;");
        deadlineLabel.setStyle("-fx-font-size: 14;");
        progressLabel.setStyle("-fx-font-size: 14;");
    }
    
    private void setEditItemPaneProperties() {
        super.setMaxSize(250, 200);
        super.setAlignment(Pos.TOP_LEFT);
        super.setPadding(new Insets(20, 20, 20, 20));
        super.setVgap(5);
        super.setHgap(10);
        super.setStyle("-fx-background-color: linear-gradient(to bottom right, #00c9ff, #92fe9d);"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0);"
                + "-fx-border-width: 1;"
                + "-fx-border-color: black;");
    }

    public Button getStartBtn() {
        return startBtn;
    }

    public Date getStartDate() throws NullDateException {
        // The following converts the Local Date from the Date Picker to
        // a Date from java.sql.Date
        try {
            Date startDate = Date.valueOf(startDatePicker.getValue());
            return startDate;
        } catch(Exception e) {
            throw new NullDateException();
        }
    }
    
    public Button getCompleteBtn() {
        return completeBtn;
    }

    private void setButtonProperties() {
        // set the button's style on hover
        startBtn.setStyle("-fx-font-size: 14;" +
                "-fx-background-color: #50BD98;");
        startBtn.setOnMouseEntered(e -> {
            startBtn.setStyle("-fx-font-size: 14;" +
                    "-fx-background-color: #3E9074;" +
                    "-fx-cursor: hand;");
        });
        startBtn.setOnMouseExited(e -> {
            startBtn.setStyle("-fx-font-size: 14;" +
                    "-fx-background-color: #50BD98;");
        });
        // Right align the complete button
        GridPane.setHalignment(completeBtn, HPos.RIGHT);
        // set the buttons style on hover
        completeBtn.setStyle("-fx-font-size: 14;" +
                "-fx-background-color: #50BD98;");
        completeBtn.setOnMouseEntered(e -> {
            completeBtn.setStyle("-fx-font-size: 14;" +
                    "-fx-background-color: #3E9074;" +
                    "-fx-cursor: hand;");
        });
        completeBtn.setOnMouseExited(e -> {
            completeBtn.setStyle("-fx-font-size: 14;" +
                    "-fx-background-color: #50BD98;");
        });


    }

    private void setDatePickerProperties() {
        startDatePicker.setStyle("-fx-base: #32C0BA;" +
                "-fx-control-inner-background: #32C0BA;");
    }
    
}
