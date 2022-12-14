package prodcons.v2;

import prodcons.IProdConsBuffer;
import prodcons.Message;

public class ProdConsBuffer implements IProdConsBuffer{
	
	Message[] listeMsg ; 
	int nbMsg; 
	int head; 
	int tail; 
	int totMsg ; 
	boolean end;
	
	public ProdConsBuffer(int nbMaxMsg) {
		listeMsg = new Message[nbMaxMsg];
	}
	

	@Override
	public synchronized void put(Message m) throws InterruptedException {
		while (head == tail && nbMsg == listeMsg.length) {
			wait();
		}
		listeMsg[head] = m ;
		printMemory() ; 
		head = (head+1)%(listeMsg.length) ;
		nbMsg ++; 
		totMsg++;
		notifyAll() ; 
	}

	@Override
	public synchronized Message get() throws InterruptedException {
		while (head == tail && nbMsg == 0 && !end) {
			wait(); 
		}
		if (end && nbMsg == 0)
			return null;
		Message m = listeMsg[tail]; 
		listeMsg[tail] = null ; 
		printMemory();
		tail = (tail+1)%(listeMsg.length) ;
		nbMsg -- ; 
		notifyAll() ; 
		return m ; 
	}
	
	private void printMemory() {
		System.out.print("état interne : {");
		for(int i = 0; i < listeMsg.length; i ++) {
			if (listeMsg[i] == null) {
				System.out.print('.');
			}else {
				System.out.print('|');
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
	
	public synchronized void prodEnded() {
		end = true;
		notifyAll();
	}

}
