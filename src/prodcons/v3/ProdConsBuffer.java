package prodcons.v3;

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
	
	//pour l'affichage
	Semaphore s_aff ; 
	
	public ProdConsBuffer(int nbMaxMsg) {
		listeMsg = new Message[nbMaxMsg];
		scons= new Semaphore(0); // on peux pas lire tant que y'as pas de message
		sprod= new Semaphore(nbMaxMsg); // on peux avoir jusqu'a tant de messagte
		s_put= new Semaphore(1); // pour que un seul puisse posser un message
		s_get= new Semaphore(1);  // et un seul puisse en voler un (on peux faire les deux en
		s_aff = new Semaphore(1); 
	}
	

	@Override
	public void put(Message m) throws InterruptedException {
		sprod.acquire();	
		
		s_put.acquire();
		listeMsg[head] = m ;
		printMemory();
		head = (head+1)%(listeMsg.length) ;
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
		listeMsg[tail] = null; 
		printMemory(); 
		tail = (tail+1)%(listeMsg.length) ;
		nbMsg -- ;
		s_get.release();
		
		
		sprod.release();
		return m ; 
	}
	
	private void printMemory() {
		try {
			s_aff.acquire();
		} catch (InterruptedException e) {}
		System.out.print("état interne : {");
		for(int i = 0; i < listeMsg.length; i ++) {
			if (listeMsg[i] == null) {
				System.out.print('.');
			}else {
				System.out.print('|');
			}
		}
		System.out.println('}');
		s_aff.release();
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
