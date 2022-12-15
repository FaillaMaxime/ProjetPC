package prodcons.v5;

import prodcons.Message;

public class Consumer extends Thread{
	
	int consTime;
	ProdConsBuffer pcb; 
	
	public Consumer(int consT ,ProdConsBuffer prodConsBuff) {
		consTime = consT ; 
		pcb = prodConsBuff ; 
		this.start();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Message m = pcb.get();
				sleep(consTime); 
			} catch (InterruptedException e) {} 
		}
	}

}
