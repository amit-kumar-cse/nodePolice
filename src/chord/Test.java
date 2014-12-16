package chord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Test {
	public static void main(String[] args)	{
		File configFile = new File("chord_sim.properties");
		
		try	{
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			int bits = Integer.parseInt(props.getProperty("noOfBits"));
			System.out.println(bits);
			System.out.println(props.getProperty("a.b"));
		}
		catch	(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

