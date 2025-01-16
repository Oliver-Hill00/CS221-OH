
import java.io.FileNotFoundException;

public class GridMonitor implements GridMonitorInterface 
{
    
    private String filename;


    public GridMonitor(String filename) throws FileNotFoundException
    {
        this.filename = filename;
    }

    public double[][] getBaseGrid()
    {

    }

    public double[][] getSurroundingSumGrid()
    {

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
