package prodcons.v5;

import prodcons.Message;

public interface IProdConsBuffer {
	
	public void put (Message m) throws InterruptedException; 
	
	public Message get() throws InterruptedException;
	
	public Message[] get (int k) throws InterruptedException;
	
	//nombre de message dans le buffer pour le moment
	public int nmsg();
	
	//nombre de message qui on été mit en tout dans le buffer
	public int totmsg(); 

}
