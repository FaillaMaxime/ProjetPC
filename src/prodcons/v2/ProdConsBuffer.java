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
		if(head == listeMsg.length -1) {
			head = 0; 
		}else {
			head ++; 
		}
		nbMsg ++; 
		totMsg++;
		notifyAll() ; 
	}

	@Override
	public synchronized Message get() throws InterruptedException {
		while (head == tail && nbMsg == 0 && !end) {
			wait(); 
		}
		if (end)
			return null;
		Message m = listeMsg[tail]; 
		if (tail == listeMsg.length-1) {
			tail = 0;
		}else {
			tail ++ ; 
		}
		nbMsg -- ; 
		notifyAll() ; 
		return m ; 
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
