package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	BorderPane root;
	
	TopPane topPane;
	Game game;
	BottomPane bottomPane;
	
	@Override
	public void start(Stage primaryStage) {
		root = new BorderPane(); // root
		
		topPane = new TopPane(); // top
		root.setTop(topPane);
		
		game = new Game("levels/level0.txt"); // center
		root.setCenter(game);
		
		bottomPane = new BottomPane(); // bottom
		root.setBottom(bottomPane);
		
		TopPane.getNextLabel().setOnMouseClicked(e -> {
			String level_handler = "levels/level" + 
        			(topPane.getLevelNumber() + 1) + ".txt";
			
			//System.out.println(level_handler);
			game = new Game(level_handler);
        	root.setCenter(game);
        	
        	topPane.setLevelNumber(topPane.getLevelNumber() + 1);
        	topPane.setLeft(new Label("Level #" + topPane.getLevelNumber()));
        });
		
		
		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
