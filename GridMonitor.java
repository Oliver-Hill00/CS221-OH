import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class GridMonitor implements GridMonitorInterface {

    private double[][] baseGrid;
    private double[][] surroundingSumGrid;
    private double[][] surroundingAvgGrid;
    private double[][] deltaGrid;
    private boolean[][] dangerGrid;

    public GridMonitor(String filename) throws FileNotFoundException {
        loadGrid(filename);
    }

    private void loadGrid(String filename) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
            baseGrid = new double[rows][cols];
            
            for (int i = 0; i < rows; i++) {
                for (int k = 0; k < cols; k++) {
                    baseGrid[i][k] = scanner.nextDouble();
                }
            }
        }
    }
    @Override
    public double[][] getBaseGrid() {
        return baseGrid;
    }
    @Override
    public double[][] getSurroundingSumGrid() {
        if (surroundingSumGrid == null) {
            calculateSurroundingSumGrid();
        }
        return surroundingSumGrid;
    }

    private void calculateSurroundingSumGrid() {
        int rows = baseGrid.length;
        int cols = baseGrid[0].length;
        surroundingSumGrid = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                surroundingSumGrid[i][k] = getSumOfNeighbors(i, k);
            }
        }
    }

    private double getSumOfNeighbors(int row, int col) {
        double sum = 0.0;
        sum += baseGrid[Math.max(row - 1, 0)][col]; // Top
        sum += baseGrid[Math.min(row + 1, baseGrid.length - 1)][col]; // Bottom
        sum += baseGrid[row][Math.max(col - 1, 0)]; // Left
        sum += baseGrid[row][Math.min(col + 1, baseGrid[0].length - 1)]; // Right
        return sum;
    }
    @Override
    public double[][] getSurroundingAvgGrid() {
        if (surroundingAvgGrid == null) {
            calculateSurroundingAvgGrid();
        }
        return surroundingAvgGrid;
    }

    private void calculateSurroundingAvgGrid() {
        int rows = baseGrid.length;
        int cols = baseGrid[0].length;
        surroundingAvgGrid = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                surroundingAvgGrid[i][k] = getSurroundingSumGrid()[i][k] / 4.0;
            }
        }
    }
    @Override
    public double[][] getDeltaGrid() {
        if (deltaGrid == null) {
            calculateDeltaGrid();
        }
        return deltaGrid;
    }

    private void calculateDeltaGrid() {
        int rows = baseGrid.length;
        int cols = baseGrid[0].length;
        deltaGrid = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                deltaGrid[i][k] = surroundingAvgGrid[i][k] / 2.0;
            }
        }
    }
    @Override
    public boolean[][] getDangerGrid() {
        if (dangerGrid == null) {
            calculateDangerGrid();
        }
        return dangerGrid;
    }

    private void calculateDangerGrid() {
        int rows = baseGrid.length;
        int cols = baseGrid[0].length;
        dangerGrid = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                double value = baseGrid[i][k];
                double avg = surroundingAvgGrid[i][k];
                double delta = deltaGrid[i][k];

                dangerGrid[i][k] = value < (avg - delta) || value > (avg + delta);
            }
        }
    }

    @Override
    public String toString() {
        String result = "GridMonitor:\n";
        result += "Base Grid:\n" + Arrays.deepToString(baseGrid) + "\n";
        result += "Surrounding Sum Grid:\n" + Arrays.deepToString(getSurroundingSumGrid()) + "\n";
        result += "Surrounding Average Grid:\n" + Arrays.deepToString(getSurroundingAvgGrid()) + "\n";
        result += "Delta Grid:\n" + Arrays.deepToString(getDeltaGrid()) + "\n";
        result += "Danger Grid:\n" + Arrays.deepToString(getDangerGrid()) + "\n";
        return result;
    }
}
