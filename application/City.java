package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class City {
	private String cityName;
	private int cityId; // cityId can be {1, 2, 3, ...}
	private int cellId;
	private ImageView image;
	
	// These images are used in getRandomImageView() method
	private static Image[] images = {
			(new Image("file:images/a.png")),
			(new Image("file:images/b.png")),
			(new Image("file:images/c.png")),
			(new Image("file:images/d.png")),
			(new Image("file:images/e.png")),
			(new Image("file:images/f.png"))
	};
	
	// Constructor is used in initObjs() method in Game.java
	public City(String cityName, String cellId, String cityId) {
		this.cityName = cityName;
		this.cityId = Integer.parseInt(cityId);
		this.cellId = Integer.parseInt(cellId);
		this.image = getRandomImageView();
	}
	
	// Returns randomly selected ImageView from images array
	private static ImageView getRandomImageView() {
		return new ImageView(images[(int) (Math.random() * 6)]);
	}
	
	// Getters
	public String getCityName() {
		return cityName;
	}
	
	public int getCellId() {
		return cellId;
	}
	
	public int getCityId() {
		return cityId;
	}

	public ImageView getImage() {
		return image;
	}
	
	
	
	
}
