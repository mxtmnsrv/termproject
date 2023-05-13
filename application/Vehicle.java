package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Vehicle{

	private int currentCityId;
	private int passengerCapacity;
	private static Image[] images = {
			(new Image("file:images/car.png")),
			(new Image("file:images/car.png")),
			(new Image("file:images/car.png"))
	};
	
	// There will be different image attributes according to the PASSENGER_CAPACITY !!
	public Vehicle(String currentCityId, String passengerCapacity) {
		
		this.currentCityId = Integer.parseInt(currentCityId);
		this.passengerCapacity = Integer.parseInt(passengerCapacity);
	}

	public int getCurrentCityId() {
		return currentCityId;
	}

	public int getPassengerCapacity() {
		return passengerCapacity;
	}
	
	public ImageView getVehicleImage() {
		if(this.passengerCapacity < 6) {
			return new ImageView(images[0]);
		} else if(this.passengerCapacity < 14) {
			return new ImageView(images[1]);
		} else {
			return new ImageView(images[2]);
		}
	}

}