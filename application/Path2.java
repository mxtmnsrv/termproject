package application;

import javafx.scene.shape.Polyline;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Objects;

public class Path2 {
	// Data fields
	private int start_id;
	private int end_id;
	private int[] obstacles;
	private Polyline polyline;
		
	// Constructor
	public Path2(int start_id, int end_id, int[] obstacles) {
		boolean[][] map = new boolean[10][10];
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				map[i][j] = true; // true means empty
			}
		}
		
		int[] temp = Arrays.copyOf(obstacles, obstacles.length);
		int[] obs = new int[temp.length - 2];
		int k = 0;
		for (int i = 0; i<temp.length; i++) {
		    if (temp[i] == start_id || temp[i] == end_id) {
		        continue;
		    }
		    obs[k] = temp[i];
		    k++;
		}
		
		Arrays.sort(obs);
		System.out.println("Obstacles: " + Arrays.toString(obs));
        for(int i=0; i<obs.length; i++) {
        	int row = (obs[i]-1)/10;
        	int col = (obs[i]-1)%10;
        	map[row][col] = false; // false for obstacle
        }
        
        Grid grid = new Grid(10, 10, map);
        
        // STARTING AND ENDING CELL INFO
        int start_row = (start_id-1)/10;
        int start_col = (start_id-1)%10;
        int end_row = (end_id-1)/10;
        int end_col = (end_id-1)%10;
        
        Point start = new Point(start_row, start_col);
        Point end = new Point(end_row, end_col);
        
        ArrayList<Point> path = PathFinding.findPath(grid, start, end);
        
        ArrayList<Integer> points = new ArrayList<>();
        points.add( (start.x * 10) + start.y + 1 );
                
        for (Point point : path) {
            int id = (point.x * 10) + point.y + 1;
            points.add(id);
        }
        
        int[] points_array = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
        	points_array[i] = points.get(i);
        }
        
        Double[] points_for_polyline = getPoints(points_array);
        this.polyline = new Polyline();
        this.polyline.getPoints().addAll(points_for_polyline);
        
        
        System.out.println("Path: " + points.toString());
        System.out.println("Start: " + start_id);
        System.out.println("End: " + end_id);
        System.out.println("\n-----------------------------\n");
	
        
        
	}
	
	public Polyline getPolyline() {
		return this.polyline;
	}
	
	
	private static Double[] getPoints(int[] points) {
		// Convert {21, ..., 64} to {0.0, 100.0, ..., 150.0, 300.0}
		double[] dpoints = new double[points.length * 2];
		for(int i=0, j=0; i<dpoints.length; i+=2, j++) {
			dpoints[i] = Game.getX(points[j]) + 25;
			dpoints[i+1] = Game.getY(points[j]) + 25;
		}
		
		// Convert double[] to Double[]
		Double[] list = new Double[dpoints.length];
		for (int i = 0; i < dpoints.length; i++) {
		    list[i] = Double.valueOf(dpoints[i]);
		}
		
		// Return Double[]
		return list;
	}
	
	
}


/**
 * A 2D point on the grid
 */
class Point {

	public int x;
    public int y;

    public Point() {
        this(0, 0);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        x = point.x;
        y = point.y;
    }

    @Override
    public boolean equals(Object object) {
        Point point = (Point) object;

        if (point.equals(null)) return false;

        // Return true if the fields match:
        return (x == point.x) && (y == point.y);
    }

    public boolean equals(Point point) {
        if (point.equals(null)) return false;

        // Return true if the fields match:
        return (x == point.x) && (y == point.y);
    }

    public Point set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
}

/**
 * A cell in the grid map
 */
class Cell {
    // node starting params
    public boolean walkable;
    public int x;
    public int y;
    public float price;

    // calculated values while finding path
    public int gCost;
    public int hCost;
    public Cell parent;

    /**
     * Create the node
     * @param x X tile location in grid
     * @param y Y tile location in grid
     * @param price how much does it cost to pass this tile. less is better, but 0.0f is for non-walkable.
     */
    public Cell(int x, int y, float price) {
        walkable = (price != 0.0f);
        this.price = price;
        this.x = x;
        this.y = y;
    }

    public int getFCost() {
        return gCost + hCost;
    }
}


/**
 * The grid of nodes we use to find path
 */
class Grid {
    public Cell[][] cells;
    int gridWidth, gridHeight;

    /**
     * Create a new grid of just walkable / unwalkable tiles.
     *
     * @param width         Grid width
     * @param height        Grid height
     * @param walkableCells the tilemap. true for walkable, false for blocking.
     */
    public Grid(int width, int height, boolean[][] walkableCells) {
        gridWidth = width;
        gridHeight = height;
        cells = new Cell[width][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                cells[x][y] = new Cell(x, y, walkableCells[x][y] ? 1.0f : 0.0f);
    }

    public ArrayList<Cell> get4Neighbours(Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<Cell>();

        if (cell.y + 1 >= 0 && cell.y + 1  < gridHeight) neighbours.add(cells[cell.x][cell.y + 1]); // N
        if (cell.y - 1 >= 0 && cell.y - 1  < gridHeight) neighbours.add(cells[cell.x][cell.y - 1]); // S
        if (cell.x + 1 >= 0 && cell.x + 1  < gridHeight) neighbours.add(cells[cell.x + 1][cell.y]); // E
        if (cell.x - 1 >= 0 && cell.x - 1  < gridHeight) neighbours.add(cells[cell.x - 1][cell.y]); // W

        return neighbours;
    }
}


/**
 * Class used to find the best path from A to B.
 */
class PathFinding {
	
    public static ArrayList<Point> findPath(Grid grid, Point startPos, Point targetPos) {
        // Find path
        ArrayList<Cell> pathInCells = findPathCells(grid, startPos, targetPos);

        // Convert to a list of points and return
        ArrayList<Point> pathInPoints = new ArrayList<Point>();

        if (pathInCells != null) {
        	for (Cell cell : pathInCells) {
        		pathInPoints.add(new Point(cell.x, cell.y));
        	}
                
        }
            

        return pathInPoints;
    }

    /**
     * Helper method for findPath8Directions()
     * @param grid Gird instance containing information about tiles
     * @param startPos Starting position.
     * @param targetPos Targeted position.
     * @param allowDiagonals Pass true if you want 8 directional pathfinding, false for 4 direcitonal
     * @return List of Node's with found path.
     */
    private static ArrayList<Cell> findPathCells(Grid grid, Point startPos, Point targetPos) {
        Cell startCell = grid.cells[startPos.x][startPos.y];
        Cell targetCell = grid.cells[targetPos.x][targetPos.y];

        ArrayList<Cell> openSet = new ArrayList<Cell>();
        HashSet<Cell> closedSet = new HashSet<Cell>();
        openSet.add(startCell);

        while (openSet.size() > 0) {
            Cell currentCell = openSet.get(0);

            for (int k = 1; k < openSet.size(); k++) {
                Cell open = openSet.get(k);

                if (open.getFCost() < currentCell.getFCost() ||
                        open.getFCost() == currentCell.getFCost() &&
                                open.hCost < currentCell.hCost) 
                {
                	currentCell = open;
                }
                    
            }

            openSet.remove(currentCell);
            closedSet.add(currentCell);

            if (currentCell == targetCell) {
            	return retracePath(startCell, targetCell);
            }
                

            ArrayList<Cell> neighbours = grid.get4Neighbours(currentCell);

            for (Cell neighbour : neighbours) {
                if (!neighbour.walkable || closedSet.contains(neighbour)) {
                	continue;
                }

                int newMovementCostToNeighbour = currentCell.gCost + getDistance(currentCell, neighbour) * (int) (10.0f * neighbour.price);
                if (newMovementCostToNeighbour < neighbour.gCost || !openSet.contains(neighbour)) {
                    neighbour.gCost = newMovementCostToNeighbour;
                    neighbour.hCost = getDistance(neighbour, targetCell);
                    neighbour.parent = currentCell;

                    if (!openSet.contains(neighbour)) {
                    	openSet.add(neighbour);
                    }
                    
                }
            }
        }

        return null;
    }

    private static ArrayList<Cell> retracePath(Cell startCell, Cell endCell) {
        ArrayList<Cell> path = new ArrayList<Cell>();
        Cell currentCell = endCell;

        while (currentCell != startCell) {
            path.add(currentCell);
            currentCell = currentCell.parent;
        }

        Collections.reverse(path);
        return path;
    }

    private static int getDistance(Cell cellA, Cell cellB) {
        int distanceX = Math.abs(cellA.x - cellB.x);
        int distanceY = Math.abs(cellA.y - cellB.y);

        return 10 * (distanceX + distanceY);
    }
}


