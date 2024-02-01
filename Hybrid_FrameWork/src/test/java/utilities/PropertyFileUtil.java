package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtil {
	public static String getValueForKey(String Key) throws Throwable
	{
		Properties conpro=new Properties();
		conpro.load(new FileInputStream("PropertyFiles\\Environment.properties"));
		return conpro.getProperty(Key);
		
	}

}
