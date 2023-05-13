package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Game extends Pane{
	
	private ArrayList<Passenger> passangersList = new ArrayList<Passenger>();
	private ArrayList<City> cityList = new ArrayList<City>();
	private ArrayList<FixedCell> fixedList = new ArrayList<FixedCell>();
	private Vehicle vehicle = new Vehicle("1", "5");
	private Path path;
	
	
		// This is constructor for Level that will be invoked in Main class
		// Its parameter is the filename of the level text file
			public Game(String filename) {
				setPrefHeight(500);
				setPrefWidth(500);
				readFile(filename);
				placeCities();
				placeFixedCells();
				placeVehicle(vehicle);
				
				

			}

//			public Game() {
//				
//			}
			
			private void readFile(String filename)
			{
				// Create new file object to read it through Scanner
				File file = new File(filename);
				// Use try-catch clause is required as there is a checked exception (FileNotFoundException)
				try {
					// input object will read level text file
					Scanner input = new Scanner(file);
					// Reading
					while(input.hasNext()) {
						// Get first line of text file
						String line = input.nextLine();
						// Split it into words and put them in array
						String[] list = line.split(",");
						// we got: list = {"City","Istanbul","14","1"};
						
						initObjs(list);
					}
					input.close();
				}
				catch(FileNotFoundException ex) {
					System.out.println("File not found!");
				}
				
			}	
			private void initObjs(String[] list)
			{
				// In switch we check what is the first word of a line; City, Fixed, Passenger or Vehicle
				switch (list[0]) {
				case "City":
					// public City(String name, String cellId, String cityId)
					City city = new City(list[1], list[2], list[3]);
					cityList.add(city);
					break;
				case "Fixed":
					// public Fixed(String cellId)
					FixedCell fixed = new FixedCell(list[1]);
					fixedList.add(fixed);
					break;
				case "Passenger":
					// public Passenger(String num_of_passengers, String startingCityId, String destinationCityId)
					Passenger passenger = new Passenger(list[1], list[2], list[3]);
					passangersList.add(passenger);
					break;
				case "Vehicle":
					// public Vehicle(String currentCityId, String passengerCapacity)
					vehicle = new Vehicle(list[1], list[2]);
					break;
				}
			}
			private void placeCity(City city)
			{
				ImageView image = city.getImage();
				image.setFitHeight(50);
				image.setFitWidth(50);
				
				Label label = new Label(city.getCityName(), image);
				label.setTextFill(Color.BLACK);
				label.setContentDisplay(ContentDisplay.TOP);
				
				int cellID = city.getCellId();
				label.setLayoutX(getX(cellID));
				label.setLayoutY(getY(cellID));
				
				getChildren().add(label);
//				if(true)
//				{	
//					image.setOnMouseClicked(e-> {
//						path.setEndingCityId(city.getCityId());
//					});
//				}
//				add(vImage,city.getCellId()%10,city.getCellId()/10);
//				add(text,city.getCellId()%10,city.getCellId()/10);
				
			}
			
			private void placeCities()
			{
				for(int i = 0;i<cityList.size();i++)
					placeCity(cityList.get(i));
			}
			
			private void placeFixedCell(FixedCell fixedCell)
			{
				ImageView image = FixedCell.getFixedImage();
				image.setFitHeight(50);
				image.setFitWidth(50);
				
				int cellID = fixedCell.getCellId();
				image.setLayoutX(getX(cellID));
				image.setLayoutY(getY(cellID));
				
				getChildren().add(image);
			}
			
			private void placeFixedCells()
			{
				for(int i = 0;i<fixedList.size();i++)
					placeFixedCell(fixedList.get(i));
			}
			
			private void placeVehicle(Vehicle vehicle)
			{
				ImageView image = vehicle.getVehicleImage();
				image.setFitHeight(50);
				image.setFitWidth(50);
				
				int cityID = vehicle.getCurrentCityId();
				int cellId = getCellIdByCityId(cityID);
				image.setLayoutX(getX(cellId));
				image.setLayoutY(getY(cellId)+25);
				
				getChildren().add(image);
			}
			
			private int getCellIdByCityId(int cityId) {
				for(int i=0;i<cityList.size();i++) {
					if(cityList.get(i).getCityId() == cityId) {
						return cityList.get(i).getCellId();
					}
				}
				return -1;
			}
			
			private static int getY(int cellID) {
				int rowID = (cellID - 1) / 10;
				return (rowID * 50);
			}
			
			private static int getX(int cellID) {
				int columnID = (cellID - 1) % 10;
				return (columnID * 50);
			}
			
}
