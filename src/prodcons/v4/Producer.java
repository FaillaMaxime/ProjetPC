package prodcons.v4;

import prodcons.Message;

public class Producer extends Thread{
	
	int nbProd ; 
	int timeProd; 
	ProdConsBuffer pcb ; 
	
	public Producer(int prodTime, int nbP , ProdConsBuffer prodConsBuff) {
		timeProd = prodTime ; 
		nbProd = nbP ; 
		pcb = prodConsBuff ; 
		this.start();
		
	}
	
	@Override
	public void run() {
		while(nbProd > 0) {
			try {
				pcb.put(new Message(nbProd));
				sleep(timeProd);
				nbProd -- ; 
			} catch (InterruptedException e) {}
		}
	}

}
