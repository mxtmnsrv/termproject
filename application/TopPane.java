package application;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TopPane extends BorderPane {
    private int levelNumber;
    private int score;
    
    // Labels
    private static Label nextLabel;
    private static Label scoreLabel;
    private static Label levelLabel;

    // Create top pane
    public TopPane() {
    	// Set the border color
        setStyle("-fx-border-color: black");
        
        // Create 3 different labels
        levelLabel = new Label("Level #" + levelNumber);
        scoreLabel = new Label("Score: " + score);
        nextLabel = new Label("Next Level >>");
        
        // Set the labels
        setLeft(levelLabel);
        setCenter(scoreLabel);
        setRight(nextLabel);
        
    }
    
    // Getters and setters
	public int getLevelNumber() {
		return levelNumber;
	}
	
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
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
