package todolistgui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

/**
 *  
 * @author William Hess
 */
public class MenuBar extends HBox {
    private final int btnSize = 20;
    private Button accessoryButton = new Button();
    private Label heading = new Label("Your To-Do List! :)");
    private Pane spacer = new Pane();
    private Button optionButton = new Button();
    private boolean backButtonOn = false;

    public MenuBar() {
        setMenuBarProperties();
        setAccessoryButtonProperties();
        setHeadingProperties();
        setSpacerProperties();
        setOptionButtonProperties();
        super.getChildren().addAll(accessoryButton, heading, spacer, optionButton);
    }
    
    private void setMenuBarProperties() {
        super.setPadding(new Insets(5, 5, 5, 5));
        super.setSpacing(10);
        super.setStyle("-fx-background-color: linear-gradient(to bottom right, #00c9ff, #92fe9d);");
    }
    
    private void setAccessoryButtonProperties() {
        setAccessoryButtonImage();
        accessoryButton.setStyle("-fx-background-color: #00AEDE;");
        // make a hover effect for the button
        accessoryButton.setOnMouseEntered(e -> {
            accessoryButton.setStyle("-fx-background-color: #008CB3;" +
                    "-fx-cursor: hand;");
        });
        accessoryButton.setOnMouseExited(e -> {
            accessoryButton.setStyle("-fx-background-color: #00AEDE;");
        });
    }

    public Button getAccessoryButton() {
        return accessoryButton;
    }

    public boolean isBackButtonOn() {
        return backButtonOn;
    }

    public void toggleBackButton() {
        backButtonOn = !backButtonOn;
    }

    private void setHeadingProperties() {
        heading.setFont(new Font("Calibri Bold", 22));
    }


    public void setHeading() {
        heading.setText("Your To-Do List! :)");
    }
    
    public void setHeading(String s) {
        heading.setText(s);
    }
    
    public String getHeading() {
        return heading.toString();
    }
    
    private void setOptionButtonProperties() {
        Image dots = new Image("todolistgui/icons/3dots.png", btnSize, btnSize, true, true);
        optionButton.setGraphic(new ImageView(dots));
        // make a hover effect for the button
        optionButton.setStyle("-fx-background-color: #77D781;");
        optionButton.setOnMouseEntered(e -> {
            optionButton.setStyle("-fx-background-color: #55A05D;" +
                    "-fx-cursor: hand;");
        });
        optionButton.setOnMouseExited(e -> {
            optionButton.setStyle("-fx-background-color: #77D781;");
        });
    }
    
    public Button getOptionButton() {
        return optionButton;
    }
    
    public void hideOptionButton() {
        super.getChildren().remove(optionButton);
    }
    
    public void showOptionButton() {
        super.getChildren().add(optionButton);
    }
    
    public void setBackButton() {
        Image backArrow = new Image("todolistgui/icons/backArrow.png", btnSize, btnSize, true, true);
        accessoryButton.setGraphic(new ImageView(backArrow));
        backButtonOn = true;
        hideOptionButton();
    }
    
    public void setAccessoryButtonImage() {
        Image lines = new Image("todolistgui/icons/3lines.png", btnSize, btnSize, true, true);
        accessoryButton.setGraphic(new ImageView(lines));
    }
    
    private void setSpacerProperties() {
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);
    }
    
}