package prodcons.v1;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestProdCons {
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
		
		Properties props = new Properties(); 
		props.loadFromXML(
				TestProdCons.class.getClassLoader().getResourceAsStream("option.xml"));	
		int nProd = Integer.parseInt(props.getProperty("nProd"));
		int nCons = Integer.parseInt(props.getProperty("nCons"));
	}
}