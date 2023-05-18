package application;

import javafx.scene.shape.Polyline;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Path {
	// Data fields
	private int start_id;
	private int end_id;
	private int[] obstacles;
	private Polyline polyline;
		
	// Constructor
	public Path(int start_id, int end_id, int[] obstacles) {
		int[][] map = new int[10][10];
        
        //int[] obs = {7, 29, 60, 62};
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
        	map[row][col]++; // obstacle
        }
        
        ArrayList<Integer> points = new ArrayList<>();
        
        // STARTING AND ENDING CELL INFO
        int start_row = (start_id-1)/10;
        int start_col = (start_id-1)%10;
        int end_row = (end_id-1)/10;
        int end_col = (end_id-1)%10;
        
        Point start = new Point(start_row, start_col, null);
        Point end = new Point(end_row, end_col, null);
        
        List<Point> path = FindPath(map, start, end);
        
        points.add( (start.x * 10) + start.y + 1 );
        
        if (path != null) {
            for (Point point : path) {
                int id = (point.x * 10) + point.y + 1;
                points.add(id);
            }
        } else {
        	System.out.println("No path found");
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
	
	
	// Inner Class
	public static class Point {
        public int x;
        public int y;
        public Point previous;

        public Point(int x, int y, Point previous) {
            this.x = x;
            this.y = y;
            this.previous = previous;
        }

        @Override
        public boolean equals(Object o) {
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }

        public Point offset(int ox, int oy) { return new Point(x + ox, y + oy, this);  }
    }

    public static boolean IsWalkable(int[][] map, Point point) {
        if (point.y < 0 || point.y > map.length - 1) return false;
        if (point.x < 0 || point.x > map[0].length - 1) return false;
        return map[point.y][point.x] == 0;
    }

    public static List<Point> FindNeighbors(int[][] map, Point point) {
        List<Point> neighbors = new ArrayList<>();
        Point up = point.offset(0,  1);
        Point down = point.offset(0,  -1);
        Point left = point.offset(-1, 0);
        Point right = point.offset(1, 0);
        if (IsWalkable(map, up)) neighbors.add(up);
        if (IsWalkable(map, down)) neighbors.add(down);
        if (IsWalkable(map, left)) neighbors.add(left);
        if (IsWalkable(map, right)) neighbors.add(right);
        return neighbors;
    }

    public static List<Point> FindPath(int[][] map, Point start, Point end) {
        boolean finished = false;
        List<Point> used = new ArrayList<>();
        used.add(start);
        while (!finished) {
            List<Point> newOpen = new ArrayList<>();
            for(int i = 0; i < used.size(); ++i){
                Point point = used.get(i);
                for (Point neighbor : FindNeighbors(map, point)) {
                    if (!used.contains(neighbor) && !newOpen.contains(neighbor)) {
                        newOpen.add(neighbor);
                    }
                }
            }

            for(Point point : newOpen) {
                used.add(point);
                if (end.equals(point)) {
                    finished = true;
                    break;
                }
            }

            if (!finished && newOpen.isEmpty())
                return null;
        }

        List<Point> path = new ArrayList<>();
        Point point = used.get(used.size() - 1);
        while(point.previous != null) {
            path.add(0, point);
            point = point.previous;
        }
        return path;
    }
    
	
}
