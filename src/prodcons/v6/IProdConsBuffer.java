package prodcons.v6;

import prodcons.Message;

public interface IProdConsBuffer {
	
	public void put (Message m) throws InterruptedException; 
	
	public void put (Message m, int n) throws InterruptedException;
	
	public Message get() throws InterruptedException;
	
	//nombre de message dans le buffer pour le moment
	public int nmsg();
	
	//nombre de message qui on été mit en tout dans le buffer
	public int totmsg();

}
