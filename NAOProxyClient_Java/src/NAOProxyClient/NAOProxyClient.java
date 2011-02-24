package NAOProxyClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;


public class NAOProxyClient {
	public static final String _DEFAULT_URL = "localhost";
	public static final int _DEFAULT_PORT = 9669;
	
	private TCPClient client;
	
	public Vector<Sensor> communicate(Vector<Command> commands) throws IOException {
		if (commands.size() == 0) { // default command is halt -- just in case decision unit does not return any action command. HALT eq. NOP
			commands.add(CommandGenerator.halt());
		}
		client.send( CommandGenerator.toMsg(commands) );
		
		String received = client.recieve();
		
		Vector<Sensor> sensordata = Sensor.splitReturnMsg(received);
		
		return sensordata;
	}
	
	public NAOProxyClient(String URL, int port) throws UnknownHostException, IOException {
		client = new TCPClient(URL, port);
	}	
	
	public void close() throws Exception  {
		client.close();
	}
}
