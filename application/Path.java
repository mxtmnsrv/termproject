package application;

import java.util.*;
import javafx.scene.shape.Polyline;

/* READ IT TO UNDERSTAND THE PATH CLASS:
 * 
 * Queue is the FIFO (first-in first-out) data structure. It holds the elements in a sequence.
 * Can be created only using Queue<E> queue = new LinkedList<>(); due to Queue is an abstract class.
 * There are two methods that we use: queue.offer(E o); and queue.poll();
 * First:  queue.offer(E o) enqueues (adds) an element to the tail of a queue.
 * Second: queue.poll() dequeues (removes) the first element from the head of a queue and also returns it.
 * 
 * To find the path BFS algorithm was used that finds the shortest path.
 * 
 * Reference: https://www.youtube.com/watch?v=oDqjPvD54Ss&t=0s
 * */

public class Path {
    private static final int ROWS = 10;
    private static final int COLS = 10;

    private int startId;
    private int endId;
    private int[] obstacles;
    private Polyline polyline;
    
    public Path(int startId, int endId, int[] obstacles) {
    	this.startId = startId;
    	this.endId = endId;
    	
    	// Grid consists entirely of zeros (1 means that there is an obstacle)
    	int[][] grid = new int[ROWS][COLS];
    	
    	// Remove start and end cell IDs from the obstacles
    	int[] temp = Arrays.copyOf(obstacles, obstacles.length);
		this.obstacles = new int[temp.length - 2];
		int k = 0;
		for (int i = 0; i<temp.length; i++) {
		    if (temp[i] == startId || temp[i] == endId) {
		        continue;
		    }
		    this.obstacles[k] = temp[i];
		    k++;
		}
		
		// Set the obstacles
        for(int i=0; i<this.obstacles.length; i++) {
        	int row = (this.obstacles[i] - 1) / 10;
        	int col = (this.obstacles[i] - 1) % 10;
        	grid[row][col] = 1; // 1 for obstacle
        }
    	
    	// Row and column values
    	int startRow = (startId - 1) / 10;
    	int startCol = (startId - 1) % 10;
    	int endRow = (endId - 1) / 10;
    	int endCol = (endId - 1) % 10;
    	
    	// Get the array of cells
    	List<Cell> path = findShortestPath(grid, startRow, startCol, endRow, endCol);
    	
    	// Get id values using this formula: id = (row * 10) + column + 1
    	int[] ids = new int[path.size()];
    	for(int i = 0; i < path.size(); i++) {
    		ids[i] = (path.get(i).row * 10) + (path.get(i).col) + 1;
    	}
    	
    	// Initialize the polyline
    	Double[] polyline_points = getDoublePoints(ids);
    	this.polyline = new Polyline();
    	this.polyline.getPoints().addAll(polyline_points);
    	
    }
    
    // Convert id values to the X and Y coordinates
    private static Double[] getDoublePoints(int[] points) {
		// Convert {21, 64} to {0.0, 100.0, 150.0, 300.0}
		double[] double_points = new double[points.length * 2];
		for(int i=0, j=0; i<double_points.length; i+=2, j++) {
			double_points[i] = getX(points[j]) + 25;
			double_points[i+1] = getY(points[j]) + 25;
		}
		
		// Convert double[] to Double[]
		Double[] list = new Double[double_points.length];
		for (int i = 0; i < double_points.length; i++) {
		    list[i] = Double.valueOf(double_points[i]);
		}
		
		// Return Double[]
		return list;
	}
    
    // Get value of Y coordinate that's used in setLayoutY() method
	public static int getY(int cellID) {
		int rowID = (cellID - 1) / 10;
		return (rowID * 50);
	}
	
	// Get value of X coordinate that's used in setLayoutX() method
	public static int getX(int cellID) {
		int columnID = (cellID - 1) % 10;
		return (columnID * 50);
	}
    
    
    public static List<Cell> findShortestPath(int[][] grid, int startRow, int startCol, int targetRow, int targetCol) {
        // Check if the start or target position is an obstacle
        if (grid[startRow][startCol] == 1 || grid[targetRow][targetCol] == 1) {
            return null; // No valid path exists
        }

        // Initialize the visited array and the queue
        boolean[][] visited = new boolean[ROWS][COLS];
        Queue<Cell> queue = new LinkedList<>();

        // Enqueue the start cell
        Cell startCell = new Cell(startRow, startCol, null);
        queue.offer(startCell);
        visited[startRow][startCol] = true;

        // Perform BFS algorithm
        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();

            // Check if the current cell is the target position
            if (currentCell.row == targetRow && currentCell.col == targetCol) {
                // Reconstruct the path from the target position to the start position
                return getPath(currentCell);
            }

            // Explore neighboring cells (up, down, left, right)
            exploreNeighboringCells(grid, currentCell.row - 1, currentCell.col, visited, queue, currentCell); // Up
            exploreNeighboringCells(grid, currentCell.row + 1, currentCell.col, visited, queue, currentCell); // Down
            exploreNeighboringCells(grid, currentCell.row, currentCell.col - 1, visited, queue, currentCell); // Left
            exploreNeighboringCells(grid, currentCell.row, currentCell.col + 1, visited, queue, currentCell); // Right
        }

        return null; // Path does not exist
    }

    // Fill queue with the cells that are neighbors to the current cell without repeated cells (checked by 2D array visited)
    private static void exploreNeighboringCells(int[][] grid, int row, int col, boolean[][] visited, Queue<Cell> queue, Cell parent) {
        // Check if the neighboring cell exists inside of the grid
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            // Check if the neighboring cell is not an obstacle and has not been visited
            if (grid[row][col] != 1 && !visited[row][col]) {
                // Enqueue the neighboring cell and change it as visited
                Cell cell = new Cell(row, col, parent);
                queue.offer(cell);
                visited[row][col] = true; // true if visited
            }
        }
    }
    
    // If the current cell becomes the target cell then get the path (list of the cells)
    private static List<Cell> getPath(Cell targetCell) {
        List<Cell> path = new ArrayList<>();

        // Backtrack from the target cell to the start cell
        // Stop at the start cell, because it's parent is null
        while (targetCell != null) {
            path.add(0, targetCell); // Insert cell at the beginning of a list
            targetCell = targetCell.parent; // Update target cell
        }

        return path;
    }
    
    
    public Polyline getPolyline() {
    	return this.polyline;
    }
    
    
    // Cell class is an inner class that use in BFS algorithm
    private static class Cell {
        int row;
        int col;
        Cell parent;

        public Cell(int row, int col, Cell parent) {
            this.row = row;
            this.col = col;
            this.parent = parent;
        }
    }
    
    
}



