package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FixedCell{

	private int cellId;
	
	public FixedCell(String cellId) {
		this.cellId = Integer.parseInt(cellId);
	}
	public int getCellId() {
		return cellId;
	}
	
	public static ImageView getFixedImage() {
		return new ImageView(new Image("file:images/block.jpg"));
	}

}
