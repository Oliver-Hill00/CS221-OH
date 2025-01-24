import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * GridMonitor class with main method for implementation testing
 */
public class GridMonitor implements GridMonitorInterface {
    //private instance variables
    private double[][] baseGrid;
    private double[][] surroundingSumGrid;
    private double[][] surroundingAvgGrid;
    private double[][] deltaGrid;
    private boolean[][] dangerGrid;

    /**
     * 
     * @param args
     * main method for testing
     */
    public static void main(String[] args) 
    {
        String [] filenames = {
            "negatives.txt",
            "oneByOne.txt",
            "sample.txt",
            "sample4x5.txt",
            "sampleDoubles.txt",
            "wideRange.txt"
        };

        for (String filename : filenames)
        {
            System.out.println("Processing file: " + filename);
            try
            {
                GridMonitor gridMonitor = new GridMonitor(filename);

                System.out.println(gridMonitor);
            }
            catch (FileNotFoundException e)
            {
                System.err.println("File is not found: " + filename);
            }
            catch (Exception e)
            {
                System.err.println("There is an error with your file: " + filename);
            }
            
            System.out.println();
        }
    }
    
    /**
     * 
     * GridMonitor constructor for parent class
     */
    public GridMonitor(String filename) throws FileNotFoundException {
        loadGrid(filename);
    }

    /**
     * 
     * loadGrid method loads a baseGrid that assigns a grid to a scanned file producing an array with rows/cols
     */
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
    /*
     * getBaseGrid defines baseGrid as a double identified array
     */
    @Override
    public double[][] getBaseGrid() 
    {
        double[][] copy = new double[baseGrid.length][baseGrid[0].length];
        for (int i = 0; i < baseGrid.length; i++)
        {
            System.arraycopy(baseGrid[i], 0, copy[i], 0, baseGrid[0].length);
        }
        return copy;
    }
    /*
     * take surrounding sum and call calcuateSurroundingSumGrid function if surroundingSumGrid == null result
     * 
     * returns surroundingSumGrid
     */
    @Override
    public double[][] getSurroundingSumGrid() 
    {
        if (surroundingSumGrid == null) 
        {
            calculateSurroundingSumGrid();
        }
        return surroundingSumGrid;
    }

    /*
     * defines surroundingSumGrid as a double array grid using rows and cols variables
     */
    private void calculateSurroundingSumGrid() 
    {
        int rows = baseGrid.length;
        int cols = baseGrid[0].length;
        surroundingSumGrid = new double[rows][cols];

        for (int i = 0; i < rows; i++) 
        {
            for (int k = 0; k < cols; k++) 
            {
                surroundingSumGrid[i][k] = getSumOfNeighbors(i, k);
            }
        }
    }

    /*
     * sums all outside grids outside of 3x3 grid scope, also gets length of baseGrd to establish where the grid ends and starts using values
     */
    private double getSumOfNeighbors(int row, int col) 
    {
        double sum = 0.0;
        sum += baseGrid[Math.max(row - 1, 0)][col]; // Top
        sum += baseGrid[Math.min(row + 1, baseGrid.length - 1)][col]; // Bottom
        sum += baseGrid[row][Math.max(col - 1, 0)]; // Left
        sum += baseGrid[row][Math.min(col + 1, baseGrid[0].length - 1)]; // Right
        return sum;
    }
    /*
     * take surrounding average and call calcuateSurroundingAvgGrid function if surroundingAvgGrid == null result
     * 
     * returns surroundingAvgGrid
     */
    @Override
    public double[][] getSurroundingAvgGrid() 
    {
        if (surroundingAvgGrid == null) 
        {
            calculateSurroundingAvgGrid();
        }
        return surroundingAvgGrid;
    }

    /*
     * averages grids based on length using rows/cols variables, defined when we take the getSurroundingSumGrid and 
     * divide it by 4.0
     */
    private void calculateSurroundingAvgGrid() 
    {
        int rows = baseGrid.length;
        int cols = baseGrid[0].length;
        surroundingAvgGrid = new double[rows][cols];

        for (int i = 0; i < rows; i++) 
        {
            for (int k = 0; k < cols; k++) 
            {
                surroundingAvgGrid[i][k] = getSurroundingSumGrid()[i][k] / 4.0;
            }
        }
    }
    /*
     * divide average grid by half and call calcuateDeltaGrid function if deltaGrid == null result
     * 
     * return deltaGrid
     */
    @Override
    public double[][] getDeltaGrid() 
    {
        if (deltaGrid == null) 
        {
            // Ensure surroundingAvgGrid is calculated
            if (surroundingAvgGrid == null) 
            { 
                getSurroundingAvgGrid();
            }
            calculateDeltaGrid();
        }
        return deltaGrid;
    }

    /*
     * defines deltaGrid using rows/cols in a double array grid
     * 
     * if surroundingAvgGrid == null, then getSurroundingAvgGrid is called as there is a null pointer exception
     * 
     * rows/cols are defined by baseGrid.length method.
     */
    private void calculateDeltaGrid()
    {
        if(surroundingAvgGrid == null)
        {
            getSurroundingAvgGrid();
        }
        
        int rows = baseGrid.length;
        int cols = baseGrid[0].length;
        deltaGrid = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                deltaGrid[i][k] = surroundingAvgGrid[i][k] / 2.0;
            }
        }
    }
    /*
     * take dangerGrid values and call calculateDangerGrid function if dangerGrid == null result
     */
    @Override
    public boolean[][] getDangerGrid() {
        if (dangerGrid == null)
        {
            if (surroundingAvgGrid == null || deltaGrid == null)
            { 
                getSurroundingAvgGrid();
                getDeltaGrid();
            }
            calculateDangerGrid();
        }
        return dangerGrid;
    }

    /*
     * defines dangerGrid using rows/cols in a double array grid
     * 
     * The danger grid is computed based on the base grid, surrounding average grid,
     * and delta grid
     * 
     * rows/cols are defined by baseGrid.length method.
     */
    private void calculateDangerGrid() 
    {
        if (surroundingAvgGrid == null) 
        {
            getSurroundingAvgGrid();
        }
        if (deltaGrid == null) 
        {
            getDeltaGrid();
        }
        int rows = baseGrid.length;
        int cols = baseGrid[0].length;
        dangerGrid = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) 
        {
            for (int k = 0; k < cols; k++) 
            {
                double value = baseGrid[i][k];
                double avg = surroundingAvgGrid[i][k];
                double delta = deltaGrid[i][k];

                dangerGrid[i][k] = value < (avg - delta) || value > (avg + delta);
            }
        }
    }

    /*
     * toString method builds a new string subset and appends all values of grids to a string titled 
     * as to what the grid represents. This format is neat in the output and displays ordered grids with the results displayed
     * for danger levels or acceptable levels for the solar array
     */
    @Override
    public String toString() 
    {
        StringBuilder result = new StringBuilder("GridMonitor:\n");

        result.append("Base Grid:\n");
        result.append(formatGrid(baseGrid));

        result.append("Surrounding Sum Grid:\n");
        result.append(formatGrid(getSurroundingSumGrid()));

        result.append("Surrounding Average Grid:\n");
        result.append(formatGrid(getSurroundingAvgGrid()));

        result.append("Delta Grid:\n");
        result.append(formatGrid(getDeltaGrid()));

        result.append("Danger Grid:\n");
        result.append(formatBooleanGrid(getDangerGrid()));

        return result.toString();
    }

    /*
     * take double[][] grid as a parameter, with a stringBuilder to insert and append methods that may be overloaded
     */
    public String formatGrid(double[][] grid)
    {
        StringBuilder sb = new StringBuilder();

        for (double[] row : grid)
        {
            for (double value : row)
            {
                sb.append(String.format("%8.2f", value));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /*
     * take double[][] grid as a parameter, with a stringBuilder to insert and append methods that may be overloaded
     * 
     * this is different that formatGrid as it bases the dangerGrid off of true/false vaules of danger or safe
     */
    public String formatBooleanGrid(boolean[][] grid)
    {
        StringBuilder sb = new StringBuilder();

        for (boolean[] row : grid)
        {
            for (boolean value : row)
            {
                sb.append(String.format("%8s", value ? "DANGER" : "SAFE"));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
