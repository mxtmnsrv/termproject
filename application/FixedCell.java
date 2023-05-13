package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FixedCell {

	private int cellId;
	
	// Constructor is used in initObjs() method in Game.java
	public FixedCell(String cellId) {
		this.cellId = Integer.parseInt(cellId);
	}
	
	// Getters
	public int getCellId() {
		return cellId;
	}
	
	// It's static, because image is common for every fixed cell
	public static ImageView getFixedImage() {
		return new ImageView(new Image("file:images/block.jpg"));
	}

}
