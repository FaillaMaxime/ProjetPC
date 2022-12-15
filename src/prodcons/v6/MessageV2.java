package prodcons.v6;

import prodcons.Message;

public class MessageV2 {
	
	Message m ;
	int nblec ;
	
	public MessageV2(Message mess, int nblecture) {
		m = mess ; 
		nblec = nblecture; 
	}
	
	public void decrement() {
		nblec -- ; 
	}
	
	public boolean finished() {
		return nblec == 0; 
	}
	
	public Message getm() {
		return m; 
	}

}
