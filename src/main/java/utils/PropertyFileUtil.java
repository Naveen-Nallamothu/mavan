package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtil {

public static String getvalueForkey(String key) throws IOException {

	Properties p=new Properties();
	FileInputStream fis =new FileInputStream("D:\\Naveen82\\StockAccount_Hybrid_maven\\PropertiesFile\\Enviroment.properties");
	p.load(fis);
	return p.getProperty(key);		

}

}
