package application;

public class Passenger {
	
	private int numberOfPassengers;
	private int startingCityId;
	private int destinationCityId;

	// Constructor is used in initObjs() method in Game.java
	public Passenger(String numberOfPassengers, String startingCityId, String destinationCityId) {
		this.startingCityId = Integer.parseInt(startingCityId);
		this.numberOfPassengers = Integer.parseInt(numberOfPassengers);
		this.destinationCityId = Integer.parseInt(destinationCityId);
	}

	// Getters
	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public int getStartingCityId() {
		return startingCityId;
	}

	public int getDestinationCityId() {
		return destinationCityId;
	}
	
	

}