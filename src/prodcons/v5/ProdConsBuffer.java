package prodcons.v5;

import prodcons.Message;

public class ProdConsBuffer implements IProdConsBuffer{
	
	Message[] listeMsg ; 
	int nbMsg; 
	int head; 
	int tail; 
	int totMsg ; 
	
	boolean lectureRafalleUngoing = false;

	public ProdConsBuffer(int nbMaxMsg) {
		listeMsg = new Message[nbMaxMsg];
	}
	

	@Override
	public synchronized void put(Message m) throws InterruptedException {
		while (head == tail && nbMsg == listeMsg.length) {
			wait();
		}
		listeMsg[head] = m ;
		printMemory();
		m.setval(totMsg);
		head = (head+1)%(listeMsg.length) ;
		nbMsg ++; 
		totMsg++;
		notifyAll();
	}

	@Override
	public synchronized Message get() throws InterruptedException {
		while (head == tail && nbMsg == 0 || lectureRafalleUngoing) {
			wait(); 
		}

		Message m = listeMsg[tail]; 
		listeMsg[tail] = null;
		printMemory();
		tail = (tail+1)%(listeMsg.length) ;
		nbMsg -- ; 
		notifyAll() ; 
		return m ; 
	}
	

	@Override
	public synchronized Message[] get(int k) throws InterruptedException {
		while (lectureRafalleUngoing) {
			wait(); 
		}
		lectureRafalleUngoing = true ; 
		
		Message[] listM = new Message[k]; 
		
		for(int i = 0 ; i < k ; i++) {
			
			while (head == tail && nbMsg == 0) {
				notifyAll();
				wait();
			}
			listM[i] = listeMsg[tail];
			listeMsg[tail] = null;
			System.out.print("plus que " + (k-i-1) + " à lire   ");
			printMemory();
			tail = (tail+1)%(listeMsg.length) ;
			nbMsg -- ; 
			
		}
		
		lectureRafalleUngoing = false ; 
		notifyAll() ;
		return listM;  
		
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



}
