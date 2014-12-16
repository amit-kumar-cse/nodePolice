package chord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Utils {
	/*
	 * returns true if lower<data<=upper in a circular sense.
	 */
	static boolean isInCircularRange(long lower, long data, long upper)	{
		boolean mid = false;
        if (lower < upper) {
            if ((lower < data) && (data <= upper)) {
                mid = true;
            }
        }
        if (lower > upper) {
            if((lower<data)||(data<=upper))
            {
            mid=true;
            }
            
        }
        return mid;
	}
	
	static int getNoOfBits()	{
		File configFile = new File("chord_sim.properties");
		int bits = 0;
		try	{
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			bits = Integer.parseInt(props.getProperty("noOfBits"));
			reader.close();
			return bits;
		}
		catch	(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		assert(bits>0);
		assert(false);
		return bits;
	}
}
