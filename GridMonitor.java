import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GridMonitor implements GridMonitorInterface
{
    
    private double[][] = baseGrid;
    private double[][] = surroundingSumGrid;
    private double[][] = surroundingAvgGrid;
    private double[][] = deltaGrid;
    private boolean[][] = dangerGrid;

    
    public GridMonitor(String filename) throws FileNotFoundException
    {
        loadGrid(filename);
    }

    private void loadGrid(String filename) throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new File(filename));
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        baseGrid = new double[rows][cols];

        for(int i = 0; i < rows; i++)
        {
            for(int k = 0; k < cols; k++)
            {
                baseGrid[i][k] = scanner.nextDouble();
            } 
        }

        scanner.close();

    }

    public double[][] getBaseGrid()
    {
        return baseGrid;
    }

    public double[][] getSurroundingSumGrid()
    {
        if(surroundingSumGrid == null)
        {
            calculateSurroundingSumGrid;
        }
        return surroundingSumGrid;
    }

    public void calculateSurroundingSumGrid()
    {
        int rows = baseGrid.length;
        int cols = basegrid[0].length;
        surroundingSumGrid = new double[rows][cols];
    }
   
    public double[][] getSurroundingAvgGrid()
    {
        
    }

    public double[][] getDeltaGrid()
    {

    }

    public boolean[][] getDangerGrid()
    {

    }

}
