package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Vehicle {

	private int currentCityId;
	private int passengerCapacity;
	
	// These images are used in getVehicleImage() method
	private static Image[] images = {
			(new Image("file:images/car.png")),
			(new Image("file:images/minibus.png")),
			(new Image("file:images/bus.png"))
	};
	
	// Constructor is used in initObjs() method in Game.java
	public Vehicle(String currentCityId, String passengerCapacity) {
		
		this.currentCityId = Integer.parseInt(currentCityId);
		this.passengerCapacity = Integer.parseInt(passengerCapacity);
	}

	// Getters and Setters
	public int getCurrentCityId() {
		return currentCityId;
	}
	
	public void setCurrentCityId(int id) {
		this.currentCityId = id;
	}

	public int getPassengerCapacity() {
		return passengerCapacity;
	}
	
	/*
	Less than 6 passenger capacity use car image, less than 
	14 use minibus image and greater than 14 use bus image.
	*/
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