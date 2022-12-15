package prodcons.v6;

import prodcons.Message;

public class ProdConsBuffer implements IProdConsBuffer{
	
	MessageV2[] listeMsg ; 
	int nbMsg; 
	int head; 
	int tail; 
	int totMsg ; 
	
	public ProdConsBuffer(int nbMaxMsg) {
		listeMsg = new MessageV2[nbMaxMsg];
	}
	

	@Override
	public synchronized void put(Message m) throws InterruptedException {
		while (head == tail && nbMsg == listeMsg.length) {
			wait();
		}
		listeMsg[head] = new MessageV2(m, 1) ;
		head = (head+1)%(listeMsg.length) ;
		nbMsg ++; 
		totMsg++;
		notifyAll() ; 
	}
	
	@Override
	public synchronized void put(Message m, int n) throws InterruptedException {
		while (head == tail && nbMsg == listeMsg.length) {
			wait();
		}
		MessageV2 mes = new MessageV2(m, n) ; 
		listeMsg[head] = mes ;
		printMemory();
		head = (head+1)%(listeMsg.length) ;
		nbMsg ++; 
		totMsg++;
		notifyAll() ;
		while ( ! mes.finished()) {
			wait() ; 
		}
		
	}

	@Override
	public synchronized Message get() throws InterruptedException {
		while (head == tail && nbMsg == 0) {
			wait(); 
		}
		MessageV2 m = listeMsg[tail]; 
		m.decrement();
		if(m.finished()) {
			listeMsg[tail] = null; 
			printMemory();
			tail = (tail+1)%(listeMsg.length) ;
			nbMsg -- ; 
		}else {
			printMemory();
			while (!m.finished()) {
				wait(); 
			}
		}		
		notifyAll() ; 
		return m.getm() ; 
	}
	
	private void printMemory() {
		System.out.print("état interne : {");
		for(int i = 0; i < listeMsg.length; i ++) {
			if (listeMsg[i] == null) {
				System.out.print('.');
			}else {
				System.out.print(listeMsg[i].nblec);
			}
		}
		System.out.println('}');
	}

	@Override
	public int nmsg() {
		return nbMsg;
	}

	@Override
	public int totmsg() {
		return totMsg;
	}




}
