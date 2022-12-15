package prodcons.v5;

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
		Consumer[] listCons = new Consumer[3*nCons/4];
		ConsumerType2[] listConst2 = new ConsumerType2[nCons/4];
		Producer[] listProd = new Producer[nProd];
		
		Random rand = new Random(); 
		
		for (int i = 0; i < 3*nCons/4 ; i++) {
			listCons[i] = new Consumer(consTime, prodConsBuff) ; 
		}
		
		for (int i = 0; i < nCons/4 ; i++) {
			listConst2[i] = new ConsumerType2(consTime, prodConsBuff) ; 
		}
		
		for (int i = 0; i < nProd ; i++) {
			int alea = rand.nextInt(maxProd - minProd + 1) + minProd ; 
			listProd[i] = new Producer(prodTime ,alea, prodConsBuff) ; 
		}

		
		
		
		for (int i = 0; i < nProd ; i++) {
			listProd[i].join();
		}
		
		System.out.println("il n'y as plus de production possible, mais le programme continue car les consomateurs ne sont pas arrété");
		
	}
}