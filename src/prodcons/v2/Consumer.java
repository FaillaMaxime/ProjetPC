package prodcons.v2;

import prodcons.Message;

public class Consumer extends Thread{
	
	int consTime;
	ProdConsBuffer pcb; 
	static boolean prdFin= false ; 
	
	public Consumer(int consT ,ProdConsBuffer prodConsBuff) {
		consTime = consT ; 
		pcb = prodConsBuff ; 
		this.start();
	}
	
	@Override
	public void run() {
		while (!prdFin || (pcb.nmsg() != 0)) {
			try {
				Message m = pcb.get();
				sleep(consTime);
			} catch (InterruptedException e) {} 
		}
	}
	
	static void produceFin () {
		prdFin = true ; 
	}

}
