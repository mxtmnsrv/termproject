package application;
import java.util.*;

public class ShortestPathFinder {
    private static final int ROWS = 10;
    private static final int COLS = 10;

    public static List<Cell> findShortestPath(int[][] grid, int startRow, int startCol, int targetRow, int targetCol) {
        // Check if the start or target position is an obstacle
        if (grid[startRow][startCol] == 1 || grid[targetRow][targetCol] == 1) {
            return null; // No valid path exists
        }

        // Initialize the visited array and the queue
        boolean[][] visited = new boolean[ROWS][COLS];
        Queue<Cell> queue = new LinkedList<>();

        // Enqueue the start position
        Cell startCell = new Cell(startRow, startCol, null);
        queue.offer(startCell);
        visited[startRow][startCol] = true;

        // Perform BFS
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

        return null; // No valid path exists
    }

    private static void exploreNeighboringCells(int[][] grid, int row, int col, boolean[][] visited, Queue<Cell> queue, Cell parent) {
        // Check if the neighboring cell is within the grid bounds
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            // Check if the neighboring cell is not an obstacle and has not been visited
            if (grid[row][col] != 1 && !visited[row][col]) {
                // Enqueue the neighboring cell and mark it as visited
                Cell cell = new Cell(row, col, parent);
                queue.offer(cell);
                visited[row][col] = true;
            }
        }
    }

    private static List<Cell> getPath(Cell targetCell) {
        List<Cell> path = new ArrayList<>();

        // Backtrack from the target cell to the start cell
        while (targetCell != null) {
            path.add(0, targetCell);
            targetCell = targetCell.parent;
        }

        return path;
    }

    public static void main(String[] args) {
        int[][] grid = {
        //   0  1  2  3  4  5  6  7  8  9
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 0
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 1
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 2
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 3
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 4
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},// 5
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 6
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 7
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},// 8
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0} // 9
        };

        int startRow = 1;
        int startCol = 6;
        int targetRow = 2;
        int targetCol = 3;

        List<Cell> path = findShortestPath(grid, startRow, startCol, targetRow, targetCol);

        if (path != null) {
            System.out.println("Shortest Path:");
            for (Cell cell : path) {
                System.out.println("(" + cell.row + ", " + cell.col + ")");
            }
        } else {
            System.out.println("No valid path exists.");
        }
    }
}

class Cell {
    int row;
    int col;
    Cell parent;

    public Cell(int row, int col, Cell parent) {
        this.row = row;
        this.col = col;
        this.parent = parent;
    }
}
