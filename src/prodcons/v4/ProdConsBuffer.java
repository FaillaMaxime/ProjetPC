package prodcons.v4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import prodcons.IProdConsBuffer;
import prodcons.Message;

public class ProdConsBuffer implements IProdConsBuffer{
	
	Message[] listeMsg ; 
	int nbMsg; 
	int head; 
	int tail; 
	int totMsg ; 

	Lock lock = new ReentrantLock();
	Condition waitFull = lock.newCondition(); 
	Condition waitEmpty = lock.newCondition(); 		   
	
	public ProdConsBuffer(int nbMaxMsg) {
		listeMsg = new Message[nbMaxMsg];
	}
	

	@Override
	public void put(Message m) throws InterruptedException {
		lock.lock();
		
		while (head == tail && nbMsg == listeMsg.length) {
			waitFull.await();
		}
		listeMsg[head] = m ;
		printMemory();
		head = (head+1)%(listeMsg.length) ;
		nbMsg ++; 
		totMsg++;
		
		waitEmpty.signal();
		
		lock.unlock();
	}

	
	@Override
	public Message get() throws InterruptedException {
		lock.lock();
		while (head == tail && nbMsg == 0) {
			waitEmpty.await();
		}
		Message m = listeMsg[tail]; 
		listeMsg[tail] = null; 
		printMemory();
		tail = (tail+1)%(listeMsg.length) ;
		nbMsg -- ; 
		waitFull.signal();
		lock.unlock();
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

}
