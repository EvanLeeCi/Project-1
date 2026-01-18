import itsc2214.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

/**
 * Game of Life implementation using two boolean 2D arrays.
 * Tracks the current and previous generations.
 */
public class Project1 implements GameOfLife {

    private boolean[][] currentGrid;
    private boolean[][] previousGrid;

    /**
     * Default constructor (3x3 grid).
     */
    public Project1() {
        currentGrid = new boolean[3][3];
        previousGrid = new boolean[3][3];
    }

    /**
     * Constructor with a specific size.
     * @param rows number of rows
     * @param cols number of columns
     */
    public Project1(int rows, int cols) {
        currentGrid = new boolean[rows][cols];
        previousGrid = new boolean[rows][cols];
    }

    @Override
    public void randomInitialize(double aliveProbability) {
        Random rand = new Random();
        for (int i = 0; i < currentGrid.length; i++) {
            for (int j = 0; j < currentGrid[0].length; j++) {
                currentGrid[i][j] = rand.nextDouble() < aliveProbability;
            }
        }
    }

    @Override
    public void loadFromString(String data) {
        Scanner sc = new Scanner(data);
        int rows = sc.nextInt();
        int cols = sc.nextInt();
        sc.nextLine();

        currentGrid = new boolean[rows][cols];
        previousGrid = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < cols; j++) {
                currentGrid[i][j] = line.charAt(j) == 'O';
            }
        }
        sc.close();
    }

    @Override
    public void loadFromFile(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        StringBuilder data = new StringBuilder();
        while (sc.hasNextLine()) {
            data.append(sc.nextLine()).append("\n");
        }
        sc.close();
        loadFromString(data.toString());
    }

    @Override
    public int countLiveNeighbors(int i, int j) {
        int count = 0;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if (x == i && y == j) {
                    continue;
                }
                if (x >= 0 && x < currentGrid.length &&
                    y >= 0 && y < currentGrid[0].length) {
                    if (currentGrid[x][y]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public void nextGeneration() {
        boolean[][] newGrid = new boolean[currentGrid.length][currentGrid[0].length];

        // copy current into previous
        for (int i = 0; i < currentGrid.length; i++) {
            for (int j = 0; j < currentGrid[0].length; j++) {
                previousGrid[i][j] = currentGrid[i][j];
            }
        }

        // apply rules
        for (int i = 0; i < currentGrid.length; i++) {
            for (int j = 0; j < currentGrid[0].length; j++) {
                int neighbors = countLiveNeighbors(i, j);
                if (currentGrid[i][j]) {
                    newGrid[i][j] = (neighbors == 2 || neighbors == 3);
                } else {
                    newGrid[i][j] = (neighbors == 3);
                }
            }
        }

        currentGrid = newGrid;
    }

    @Override
    public boolean isAlive(int i, int j) {
        return currentGrid[i][j];
    }

    @Override
    public int numRows() {
        return currentGrid.length;
    }

    @Override
    public int numCols() {
        return currentGrid[0].length;
    }

    @Override
    public boolean isStillLife() {
        for (int i = 0; i < currentGrid.length; i++) {
            for (int j = 0; j < currentGrid[0].length; j++) {
                if (currentGrid[i][j] != previousGrid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}