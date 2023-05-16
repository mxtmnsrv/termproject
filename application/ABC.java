package application;

import java.util.Arrays;

public class ABC {
//	public static void main(String[] args) {
//		
//		double[] list = getPoints(21, 64, 1);
//		
//		System.out.println(Arrays.toString(list));
//	}

	
	public static double[] getPoints(int id1, int id2, int X) {
		int row1 = (id1 - 1)/10;
		int col1 = (id1 - 1)%10;
		int row2 = (id2 - 1)/10;
		int col2 = (id2 - 1)%10;
		
		int rowd = row1 - row2;
		int cold = col1 - col2;
		
		int id = id1; // starting point
		int[] points = new int[Math.abs(cold)+Math.abs(rowd)+1];
		points[0] = id1;
		int k = 1;
		
		if(X == 1) {
			for(int i=0; i<Math.abs(cold); i++) {
				if(cold > 0) {
					id--;
				} else if(cold < 0) {
					id++;
				}
				points[k] = id;
				k++;
			}
			
			for(int i=0; i<Math.abs(rowd); i++) {
				if(rowd > 0) {
					id-=10;
				} else if(rowd < 0) {
					id+=10;
				}
				points[k] = id;
				k++;
			}
			
		} else if(X == 2) {
			for(int i=0; i<Math.abs(rowd); i++) {
				if(rowd > 0) {
					id-=10;
				} else if(rowd < 0) {
					id+=10;
				}
				points[k] = id;
				k++;
			}
			
			for(int i=0; i<Math.abs(cold); i++) {
				if(cold > 0) {
					id--;
				} else if(cold < 0) {
					id++;
				}
				points[k] = id;
				k++;
			}
			
		}
		
		double[] POINTS = new double[points.length * 2];
		for(int i=0, j=0; i<POINTS.length; i+=2, j++) {
			POINTS[i] = getX(points[j]);
			POINTS[i+1] = getY(points[j]);
		}
		
		return POINTS;
	}
	
	// Get value of Y coordinate that's used in setLayoutY() method
	private static int getY(int cellID) {
		int rowID = (cellID - 1) / 10;
		return (rowID * 50);
	}
	
	// Get value of X coordinate that's used in setLayoutX() method
	private static int getX(int cellID) {
		int columnID = (cellID - 1) % 10;
		return (columnID * 50);
	}
	
	
	
	
}