package application;

import javafx.scene.shape.Polyline;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
				map[i][j] = true;
			}
		}
		
		int[] temp = Arrays.copyOf(obstacles, obstacles.length);
		int[] obs = new int[temp.length - 2];
		int obsIndex = 0;

		for (int i = 0; i < temp.length; i++) {
		    if (temp[i] == start_id || temp[i] == end_id) {
		        continue;
		    }
		    
		    obs[obsIndex] = temp[i];
		    obsIndex++;
		}
		
		Arrays.sort(obs);
		System.out.println("Obstacles: " + Arrays.toString(obs));
        for(int i=0; i<obs.length; i++) {
        	int row = (obs[i]-1)/10;
        	int col = (obs[i]-1)%10;
        	map[row][col] = false; // obstacle
        }
        
        Grid grid = new Grid(10, 10, map);
        
        // STARTING AND ENDING CELL INFO
        int start_row = (start_id-1)/10;
        int start_col = (start_id-1)%10;
        int end_row = (end_id-1)/10;
        int end_col = (end_id-1)%10;
        
        Point start = new Point(start_row, start_col);
        Point end = new Point(end_row, end_col);
        
        List<Point> path = PathFinding.findPath(grid, start, end, false);
        
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
        // Unlikely to compare incorrect type so removed for performance
        // if (!(obj.GetType() == typeof(PathFind.Point)))
        //     return false;
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

    @Override
    public String toString() {
        return "Point = {" + x +", " + y +'}';
    }
}

/**
 * A node in the grid map
 */
class Nodep {
    // node starting params
    public boolean walkable;
    public int x;
    public int y;
    public float price;

    // calculated values while finding path
    public int gCost;
    public int hCost;
    public Nodep parent;

    /**
     * Create the node
     * @param x X tile location in grid
     * @param y Y tile location in grid
     * @param price how much does it cost to pass this tile. less is better, but 0.0f is for non-walkable.
     */
    public Nodep(int x, int y, float price) {
        walkable = price != 0.0f;
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
    public Nodep[][] nodes;
    int gridWidth, gridHeight;

    /**
     * Create a new Grid with tile prices.
     *
     * @param width      Grid width
     * @param height     Grid height
     * @param tile_costs 2d array of floats, representing the cost of every tile.
     *                   0.0f = unwalkable tile.
     *                   1.0f = normal tile.
     */
    public Grid(int width, int height, float[][] tile_costs) {
        gridWidth = width;
        gridHeight = height;
        nodes = new Nodep[width][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                nodes[x][y] = new Nodep(x, y, tile_costs[x][y]);
    }

    /**
     * Create a new grid of just walkable / unwalkable tiles.
     *
     * @param width         Grid width
     * @param height        Grid height
     * @param walkableTiles the tilemap. true for walkable, false for blocking.
     */
    public Grid(int width, int height, boolean[][] walkableTiles) {
        gridWidth = width;
        gridHeight = height;
        nodes = new Nodep[width][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                nodes[x][y] = new Nodep(x, y, walkableTiles[x][y] ? 1.0f : 0.0f);
    }

    public List<Nodep> get8Neighbours(Nodep node) {
        List<Nodep> neighbours = new ArrayList<Nodep>();

        for (int x = -1; x <= 1; x++)
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;

                int checkX = node.x + x;
                int checkY = node.y + y;

                if (checkX >= 0 && checkX < gridWidth && checkY >= 0 && checkY < gridHeight)
                    neighbours.add(nodes[checkX][checkY]);
            }

        return neighbours;
    }

    public List<Nodep> get4Neighbours(Nodep node) {
        List<Nodep> neighbours = new ArrayList<Nodep>();

        if (node.y + 1 >= 0 && node.y + 1  < gridHeight) neighbours.add(nodes[node.x][node.y + 1]); // N
        if (node.y - 1 >= 0 && node.y - 1  < gridHeight) neighbours.add(nodes[node.x][node.y - 1]); // S
        if (node.x + 1 >= 0 && node.x + 1  < gridHeight) neighbours.add(nodes[node.x + 1][node.y]); // E
        if (node.x - 1 >= 0 && node.x - 1  < gridHeight) neighbours.add(nodes[node.x - 1][node.y]); // W

        return neighbours;
    }
}


/**
 * Class used to find the best path from A to B.
 */
class PathFinding {
    /**
     * Method you should use to get path allowing 4 directional movement
     * @param grid grid to search in.
     * @param startPos starting position.
     * @param targetPos ending position.
     * @param allowDiagonals Pass true if you want 8 directional pathfinding, false for 4 direcitonal
     * @return
     */
    public static List<Point> findPath(Grid grid, Point startPos, Point targetPos, boolean allowDiagonals) {
        // Find path
        List<Nodep> pathInNodes = findPathNodes(grid, startPos, targetPos, allowDiagonals);

        // Convert to a list of points and return
        List<Point> pathInPoints = new ArrayList<Point>();

        if (pathInNodes != null)
            for (Nodep node : pathInNodes)
                pathInPoints.add(new Point(node.x, node.y));

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
    private static List<Nodep> findPathNodes(Grid grid, Point startPos, Point targetPos, boolean allowDiagonals) {
        Nodep startNode = grid.nodes[startPos.x][startPos.y];
        Nodep targetNode = grid.nodes[targetPos.x][targetPos.y];

        List<Nodep> openSet = new ArrayList<Nodep>();
        HashSet<Nodep> closedSet = new HashSet<Nodep>();
        openSet.add(startNode);

        while (openSet.size() > 0) {
            Nodep currentNode = openSet.get(0);

            for (int k = 1; k < openSet.size(); k++) {
                Nodep open = openSet.get(k);

                if (open.getFCost() < currentNode.getFCost() ||
                        open.getFCost() == currentNode.getFCost() &&
                                open.hCost < currentNode.hCost)
                    currentNode = open;
            }

            openSet.remove(currentNode);
            closedSet.add(currentNode);

            if (currentNode == targetNode)
                return retracePath(startNode, targetNode);

            List<Nodep> neighbours;
            if (allowDiagonals) neighbours = grid.get8Neighbours(currentNode);
            else neighbours = grid.get4Neighbours(currentNode);

            for (Nodep neighbour : neighbours) {
                if (!neighbour.walkable || closedSet.contains(neighbour)) continue;

                int newMovementCostToNeighbour = currentNode.gCost + getDistance(currentNode, neighbour) * (int) (10.0f * neighbour.price);
                if (newMovementCostToNeighbour < neighbour.gCost || !openSet.contains(neighbour)) {
                    neighbour.gCost = newMovementCostToNeighbour;
                    neighbour.hCost = getDistance(neighbour, targetNode);
                    neighbour.parent = currentNode;

                    if (!openSet.contains(neighbour)) openSet.add(neighbour);
                }
            }
        }

        return null;
    }

    private static List<Nodep> retracePath(Nodep startNode, Nodep endNode) {
        List<Nodep> path = new ArrayList<Nodep>();
        Nodep currentNode = endNode;

        while (currentNode != startNode) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }

        Collections.reverse(path);
        return path;
    }

    private static int getDistance(Nodep nodeA, Nodep nodeB) {
        int distanceX = Math.abs(nodeA.x - nodeB.x);
        int distanceY = Math.abs(nodeA.y - nodeB.y);

        if (distanceX > distanceY)
            return 14 * distanceY + 10 * (distanceX - distanceY);
        return 14 * distanceX + 10 * (distanceY - distanceX);
    }
}


