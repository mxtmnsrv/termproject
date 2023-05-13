package application;
import javafx.scene.shape.Polyline;
import java.util.ArrayList;

public class Path {
	// Data fields
	private int startingCityId;
	private int endingCityId;
	private Polyline path;
	
	//public void drawPath(City startingCity,City endingCity,ArrayList<FixedCell> fixeds,ArrayList<City> cities)
		
	// Constructor
	public Path(int startingCityId) {
		this.startingCityId = startingCityId;
	}
	
	// Getters and setters
	public int getStartingCityId() {
		return startingCityId;
	}

	public void setStartingCityId(int startingCityId) {
		this.startingCityId = startingCityId;
	}

	public int getEndingCityId() {
		return endingCityId;
	}

	public void setEndingCityId(int endingCityId) {
		this.endingCityId = endingCityId;
	}
	
	public Polyline getPath() {
		return path;
	}
	
}
