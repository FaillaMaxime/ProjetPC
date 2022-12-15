package prodcons.v5;

import prodcons.Message;

public class ConsumerType2 extends Thread {
	
	int consTime;
	ProdConsBuffer pcb; 
	
	public ConsumerType2(int consT ,ProdConsBuffer prodConsBuff) {
		consTime = consT ; 
		pcb = prodConsBuff ; 
		this.start();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Message[] m = pcb.get(8);
				sleep(consTime); 
				int u = m[0].getval();
				for(int i = 0; i < 8; i++) {
					if (m[i].getval() != u + i) {
						System.out.println();System.out.println();System.out.println(m[i].getval() + " != " + (u+i) + " i :" +i);  System.out.println();System.out.println();System.out.println();
						throw new RuntimeException("l'ordre de lecture est pas bon");
					}
				}
			} catch (InterruptedException e) {} 
		}
	}

}
