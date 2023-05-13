package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class City{
	// These are three lines that define the city name, cell id and city id variables
	private String cityName;
	private int cityId;
	private int cellId;
	private ImageView image;
	private static Image[] images = {
			(new Image("file:images/a.png")),
			(new Image("file:images/b.png")),
			(new Image("file:images/c.png")),
			(new Image("file:images/d.png")),
			(new Image("file:images/e.png")),
			(new Image("file:images/f.png"))
	};
	
	public City(String cityName,String cellId,String cityId)
	{
		this.cityName = cityName;
		this.cityId = Integer.parseInt(cityId);
		this.cellId = Integer.parseInt(cellId);
		this.image = getRandomImageView();
	}
	
	private static ImageView getRandomImageView() {
		return new ImageView(images[(int) (Math.random() * 6)]);
	}
	
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
