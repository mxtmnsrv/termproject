package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class Game extends Pane {
	
	// Used in the DRIVE handler, hence public
	public Label selectedCity;
	public Polyline polyline;
	public ImageView vehicleImage;
	
	// Constant for cell size
	private static final int CELL_SIZE = 50;
	
	// ArrayLists
	private ArrayList<Passenger> passengersList = new ArrayList<Passenger>();
	private ArrayList<City> cityList = new ArrayList<City>();
	private ArrayList<FixedCell> fixedList = new ArrayList<FixedCell>();
	
	// Vehicle and Path
	private Vehicle vehicle = new Vehicle("1", "5"); // If new removed then error occurs
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
		
		// Read level file
		private void readFile(String filename) {
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
					// We get: list = {"City","Istanbul","14","1"};
					String[] list = line.split(",");
					
					// Fill ArrayLists: passengersList, cityList, fixedList
					// Also set Vehicle object
					initObjs(list);
				}
				input.close();
			}
			catch(FileNotFoundException ex) {
				System.out.println("File not found!");
			}
			
		}	
		
		// This method is used in readFile() to initialize Game's data fields
		private void initObjs(String[] list) {
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
				// public Passenger(String numberOfPassengers, String startingCityId, String destinationCityId)
				Passenger passenger = new Passenger(list[1], list[2], list[3]);
				passengersList.add(passenger);
				break;
			case "Vehicle":
				// public Vehicle(String currentCityId, String passengerCapacity)
				vehicle = new Vehicle(list[1], list[2]);
				break;
			}
		}
	
		
		// Draw a path line from the vehicle to the cell id given in the parameters
		public void placePath(int endID) {
			polyline = new Polyline();
			
			// It is always the cell id where vehicle is located
			int startID = getCellIdByCityId(vehicle.getCurrentCityId());
			
			Double[] points = getPoints(startID, endID);
			polyline.getPoints().addAll(points);
			getChildren().add(polyline);
			
		}
		
		
		// Place city in the game (pane)
		private void placeCity(City city) {
			// Image of the city
			ImageView image = city.getImage();
			image.setFitHeight(CELL_SIZE);
			image.setFitWidth(CELL_SIZE);
			
			// Name of the city
			Label label = new Label(city.getCityName(), image);
			label.setTextFill(Color.BLACK);
			label.setContentDisplay(ContentDisplay.TOP);
			
			// Place city
			int cellID = city.getCellId();
			label.setLayoutX(getX(cellID));
			label.setLayoutY(getY(cellID));
			
			// If city icon (label) was clicked, then icon's node (Label object) is copied to selectedCity variable
			EventHandler<MouseEvent> labelEventHandler = new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                // Store the selected Label
	                selectedCity = (Label) event.getSource();
	            }
	        };
	        label.setOnMouseClicked(labelEventHandler);
			
			
			// Add it to the game (pane)
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
			
		// Place all cities from the list
		private void placeCities() {
			for(int i = 0;i<cityList.size();i++)
				placeCity(cityList.get(i));
		}
			
		// Place fixed cell in the game (pane)
		private void placeFixedCell(FixedCell fixedCell) {
			// Image of the fixed cell
			ImageView image = FixedCell.getFixedImage();
			image.setFitHeight(CELL_SIZE);
			image.setFitWidth(CELL_SIZE);
			
			// Place fixed cell
			int cellID = fixedCell.getCellId();
			image.setLayoutX(getX(cellID));
			image.setLayoutY(getY(cellID));
			
			// Add it to the game (pane)
			getChildren().add(image);
		}
			
		// Place all fixed cells from the list
		private void placeFixedCells() {
			for(int i = 0;i<fixedList.size();i++)
				placeFixedCell(fixedList.get(i));
		}
			
		// Place vehicle in the game (pane)
		private void placeVehicle(Vehicle vehicle) {
			// Image of the vehicle
			vehicleImage = vehicle.getVehicleImage();
			vehicleImage.setFitHeight(CELL_SIZE);
			vehicleImage.setFitWidth(CELL_SIZE);
			
			// Place vehicle
			int cityID = vehicle.getCurrentCityId();
			int cellId = getCellIdByCityId(cityID);
			vehicleImage.setLayoutX(getX(cellId));
			vehicleImage.setLayoutY(getY(cellId)+25);
			
			// Add it to the game (pane) 
			getChildren().add(vehicleImage);
		}
			
		// This method is used in placeVehicle() to get cell id 
		private int getCellIdByCityId(int cityId) {
			for(int i=0;i<cityList.size();i++) {
				if(cityList.get(i).getCityId() == cityId) {
					return cityList.get(i).getCellId();
				}
			}
			return -1; // not found
		}
		
		// Returns city id by the known city cell id 
		private int getCityIdByCellId(int cellId) {
			for(int i=0;i<cityList.size();i++) {
				if(cityList.get(i).getCellId() == cellId) {
					return cityList.get(i).getCityId();
				}
			}
			return -1; // not found
		}
			
		// Get value of Y coordinate that's used in setLayoutY() method
		private static int getY(int cellID) {
			int rowID = (cellID - 1) / 10;
			return (rowID * CELL_SIZE);
		}
		
		// Get value of X coordinate that's used in setLayoutX() method
		private static int getX(int cellID) {
			int columnID = (cellID - 1) % 10;
			return (columnID * CELL_SIZE);
		}
		
		// Get cell id with X and Y coordinates
		public static int getCellID(double x, double y) {
		    int columnID = (int) (x / CELL_SIZE);
		    int rowID = (int) (y / CELL_SIZE);
		    int cellID = (rowID * 10) + columnID + 1;
		    return cellID;
		}
		
		// Returns points array that will be used in placePath() method
		// TODO: THIS METHOD DOES NOT WORK WELL, ALGORITHM SHOULD BE IMPROVED
		public static Double[] getPoints(int id1, int id2) {
			int row1 = (id1 - 1)/10;
			int col1 = (id1 - 1)%10;
			int row2 = (id2 - 1)/10;
			int col2 = (id2 - 1)%10;
			
			// Row and column differences
			int rowd = row1 - row2;
			int cold = col1 - col2;
			
			int id = id1; // starting point
			int[] id_points = new int[Math.abs(cold)+Math.abs(rowd)+1];
			id_points[0] = id1;
			int k = 1;
			
			// X=0 : first go left/right then up/down
			// X=1 : first go up/down then left/right
			int X = (int) (Math.random() * 2);
			
			if(X == 0) {
				for(int i=0; i<Math.abs(cold); i++) {
					if(cold > 0) {
						id--;
					} else if(cold < 0) {
						id++;
					}
					id_points[k] = id;
					k++;
				}
				
				for(int i=0; i<Math.abs(rowd); i++) {
					if(rowd > 0) {
						id-=10;
					} else if(rowd < 0) {
						id+=10;
					}
					id_points[k] = id;
					k++;
				}
				
			} else if(X == 1) {
				for(int i=0; i<Math.abs(rowd); i++) {
					if(rowd > 0) {
						id-=10;
					} else if(rowd < 0) {
						id+=10;
					}
					id_points[k] = id;
					k++;
				}
				
				for(int i=0; i<Math.abs(cold); i++) {
					if(cold > 0) {
						id--;
					} else if(cold < 0) {
						id++;
					}
					id_points[k] = id;
					k++;
				}
				
			}
			
			// Convert {21, ..., 64} to {0.0, 100.0, ..., 150.0, 300.0}
			double[] points = new double[id_points.length * 2];
			for(int i=0, j=0; i<points.length; i+=2, j++) {
				points[i] = getX(id_points[j]) + 25;
				points[i+1] = getY(id_points[j]) + 25;
			}
			
			// Convert double[] to Double[]
			Double[] list = new Double[points.length];
			for (int i = 0; i < points.length; i++) {
			    list[i] = Double.valueOf(points[i]);
			}
			
			// Return Double[]
			return list;
		}
			
}
