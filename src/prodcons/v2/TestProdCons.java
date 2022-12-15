package prodcons.v2;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Random;

public class TestProdCons {
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException, InterruptedException {
		
		Properties props = new Properties(); 
		props.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("option.xml"));	
		int nProd = Integer.parseInt(props.getProperty("nProd"));
		int nCons = Integer.parseInt(props.getProperty("nCons"));
		int buffSize = Integer.parseInt(props.getProperty("bufSz"));
		int prodTime = Integer.parseInt(props.getProperty("prodTime"));
		int consTime = Integer.parseInt(props.getProperty("consTime"));
		int minProd = Integer.parseInt(props.getProperty("minProd"));
		int maxProd = Integer.parseInt(props.getProperty("maxProd"));
		
		
		ProdConsBuffer prodConsBuff = new ProdConsBuffer(buffSize);
		Consumer[] listCons = new Consumer[nCons];
		Producer[] listProd = new Producer[nProd];
		
		Random rand = new Random(); 
		
		int messageToConsume = 0; 
		
		for (int i = 0; i < nProd ; i++) {
			int alea = rand.nextInt(maxProd - minProd + 1) + minProd ;
			messageToConsume += alea ; 
			listProd[i] = new Producer(prodTime ,alea, prodConsBuff) ; 
		}
		
		for (int i = 0; i < nCons ; i++) {
			listCons[i] = new Consumer(consTime, prodConsBuff) ; 
		}

		for (int i = 0; i < nProd ; i++) {
			listProd[i].join();
		}
		
		Consumer.produceFin() ; 
		prodConsBuff.prodEnded();
		
		for (int i = 0; i < nCons ; i++) {
			listCons[i].join();
		}
		
		System.out.println("il n'y as plus de production possible, tout les consomateurs se sont fini, le programme est fini avec succes");
		
	}
}
