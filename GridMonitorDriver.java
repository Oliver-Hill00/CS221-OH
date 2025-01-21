
import java.io.FileNotFoundException;

public class GridMonitorDriver 
{
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
}
