package v3;

import java.util.concurrent.Semaphore;

import prodcons.IProdConsBuffer;
import prodcons.Message;

public class ProdConsBuffer implements IProdConsBuffer{
	
	Message[] listeMsg ; 
	int nbMsg; 
	int head; 
	int tail; 
	int totMsg ; 
	
	Semaphore scons , sprod, s_put, s_get; 
	
	public ProdConsBuffer(int nbMaxMsg) {
		listeMsg = new Message[nbMaxMsg];
		scons= new Semaphore(0);
		sprod= new Semaphore(nbMaxMsg);
		s_put= new Semaphore(1);
		s_get= new Semaphore(1); 
		
	}
	

	@Override
	public void put(Message m) throws InterruptedException {
		sprod.acquire();	
		
		s_put.acquire();
		listeMsg[head] = m ;
		if(head == listeMsg.length -1) {
			head = 0; 
		}else {
			head ++; 
		}
		nbMsg ++; 
		totMsg++;
		s_put.release();
		
		scons.release();
	}

	@Override
	public Message get() throws InterruptedException {
		scons.acquire();
		
		s_get.acquire();
		Message m = listeMsg[tail]; 
		if (tail == listeMsg.length-1) {
			tail = 0;
		}else {
			tail ++ ; 
		}
		nbMsg -- ;
		s_get.release();
		
		
		sprod.release();
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

}
