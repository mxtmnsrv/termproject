package application;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TopPane extends BorderPane {
    private int levelNumber;
    private int score;
    private static Label nextLabel;
    private static Label scoreLabel;
    private static Label levelLabel;

    public TopPane() {
    	
    	// Style of top pane
        setStyle("-fx-border-color: black");
        // Create 3 different labels
        levelLabel = new Label("Level #" + levelNumber);
        scoreLabel = new Label("Score: " + score);
        nextLabel = new Label("Next Level >>");
        // Add them to top pane
        setLeft(levelLabel);
        setCenter(scoreLabel);
        setRight(nextLabel);
        
    }

	public int getLevelNumber() {
		return levelNumber;
	}
	
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	public int getScore() {
		return score;
	}

	public static Label getNextLabel() {
		return nextLabel;
	}

	public static Label getScoreLabel() {
		return scoreLabel;
	}

	public static Label getLevelLabel() {
		return levelLabel;
	}
    
    
}
