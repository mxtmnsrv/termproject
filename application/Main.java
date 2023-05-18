package application;
	
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	BorderPane root;
	
	TopPane topPane;
	Game game; // centerPane
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
		
		// Next Level Handler
		TopPane.getNextLabel().setOnMouseClicked(e -> {
			String level_handler = "levels/level" + 
        			(topPane.getLevelNumber() + 1) + ".txt";
			
			//System.out.println(level_handler);
			game = new Game(level_handler);
        	root.setCenter(game);
        	
        	topPane.setLevelNumber(topPane.getLevelNumber() + 1);
        	topPane.setLeft(new Label("Level #" + topPane.getLevelNumber()));
        });
		
		
		// Set an event handler for the DRIVE
		BottomPane.getDriveLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Check if a city Label has been selected
                if (game.selectedCity != null) {
                    
                	double xo = game.selectedCity.getLayoutX();
                	double yo = game.selectedCity.getLayoutY();
//                		Print a message to the console
//                  	System.out.println(game.selectedCity.getLayoutX());
//                  	System.out.println(game.selectedCity.getLayoutY());
//                  	System.out.println("-----");
                    int endID = Game.getCellID(xo, yo);
                    int startID = game.getCellIdByCityId(game.vehicle.getCurrentCityId());
                    game.placePath(startID, endID);
                    game.selectedCity = null;
                    // TODO: Vehicle animation >>> Remove path line >>> Update VEHICLE!
                    
//                    PathTransition path = new PathTransition();
//                    path.setAutoReverse(false);
//                    path.setNode(game.vehicleImage);
//                    path.setPath(game.polyline);
//                    path.setDuration(Duration.seconds(3));
//                    //path.setOrientation();
//                    path.setCycleCount(1);
//                    path.play();
                    
                    // Update vehicle position!
                    //game.vehicle.setCurrentCityId(game.getCityIdByCellId(endID));
                    
                    // Create a PauseTransition to wait for 2 seconds
                    PauseTransition pause = new PauseTransition(Duration.seconds(5));
                    // When the PauseTransition finishes, remove the polyline from the game pane
                    pause.setOnFinished(e -> {
                        game.getChildren().remove(game.polyline);
                    });
                    // Start the PauseTransition
                    pause.play();

                    
                }
            }
        });
		
		
		
		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setTitle("GAME");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
