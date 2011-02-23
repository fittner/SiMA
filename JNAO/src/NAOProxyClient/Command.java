package NAOProxyClient;

import java.util.Vector;


public class Command {
	private final static String delimiter = ";"; 
	public final eCommands id;
	public Vector<String> params;
	
	public Command(eCommands id) {
		this.id=id;
		params = new Vector<String>();
	}
	
	public String toMsg() {
		String msg = id+delimiter;
		
		for (String p:params) {
			msg += p+delimiter;
		}
		
		msg = msg.substring(0, msg.length() - delimiter.length());
		
		return msg;
	}
	
	public String toString() {
		return id+":"+params;
	}

}
