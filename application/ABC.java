package application;
	
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Node;

public class ABC extends Application {
	
	private static final int cellSize = 50;
	
	private static int getY(int cellID) {
		int rowID = (cellID - 1) / 10;
		return (rowID * 50);
	}
	
	private static int getX(int cellID) {
		int columnID = (cellID - 1) % 10;
		return (columnID * 50);
	}
	
	@Override
	public void start(Stage primaryStage) {
		Scanner input = new Scanner(System.in);
		
		Pane pane = new Pane();
		pane.setStyle("-fx-border-color: black");
		
		ImageView image = getRandomImageView();
		// 50x50
		image.setFitHeight(50);
		image.setFitWidth(50);
		
		Label text = new Label("Ankara", image);
		//text.setFont(Font.font("Arial", FontWeight.BOLD, 7));
		text.setTextFill(Color.BLACK);
		text.setContentDisplay(ContentDisplay.TOP);
		//text.setAlignment(Pos.BOTTOM_CENTER);
		
		// int cellID = input.nextInt();
		int cellID = 55;
		text.setLayoutX(getX(cellID));
		text.setLayoutY(getY(cellID));
		
		pane.getChildren().add(text);
		
		
		ImageView image2 = getRandomImageView();
		// 50x50
		image2.setFitHeight(50);
		image2.setFitWidth(50);
		
		Label text2 = new Label("Ankara", image2);
		//text.setFont(Font.font("Arial", FontWeight.BOLD, 7));
		text2.setTextFill(Color.BLACK);
		text2.setContentDisplay(ContentDisplay.TOP);
		//text.setAlignment(Pos.BOTTOM_CENTER);
		
		// int cellID = input.nextInt();
		cellID = 3;
		text2.setLayoutX(getX(cellID));
		text2.setLayoutY(getY(cellID));
		
		pane.getChildren().add(text2);
		
		
		
		// Scene(Parent, width, height)
		Scene scene = new Scene(pane, cellSize*10, cellSize*10);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("TEST");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
