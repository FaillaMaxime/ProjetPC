package prodcons.v6;

import prodcons.Message;

public class ProducerV2 extends Thread{
	
	int nbProd ; 
	int timeProd; 
	ProdConsBuffer pcb ; 
	
	public ProducerV2(int prodTime, int nbP , ProdConsBuffer prodConsBuff) {
		timeProd = prodTime ; 
		nbProd = nbP ; 
		pcb = prodConsBuff ; 
		this.start();
		
	}
	
	@Override
	public void run() {
		while(nbProd > 0) {
			try {
				pcb.put(new Message(nbProd), 6);
				sleep(timeProd);
				nbProd -- ; 
			} catch (InterruptedException e) {}
		}
	}

}
