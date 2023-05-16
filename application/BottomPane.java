package application;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BottomPane extends BorderPane {
	// Label
	private static Label driveLabel;

    // Constants for pane width and height
    private static final int PANE_WIDTH = 500;
    private static final int PANE_HEIGHT = 140;

    // Create bottom pane
    public BottomPane() {
        // Set the preferred size of the pane
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);

        // Set the border color
        setStyle("-fx-border-color: black;");

        // Create the "DRIVE" label and set its font and color
        driveLabel = new Label("DRIVE");
        driveLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        driveLabel.setTextFill(Color.GREY);

        // Set the "DRIVE" label on the right side of the pane
        setRight(driveLabel);

        // Add padding to the "DRIVE" label
        driveLabel.setPadding(new Insets(45, 20, 0, 0));

        // !!!Add the other label to the left side of the pane
    }
    
    // Getters
    public static Label getDriveLabel() {
    	return driveLabel;
    }
}
