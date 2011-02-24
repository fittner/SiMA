package NAOProxyClient;

import java.util.Vector;


public class NAOProxyClient {
	public final String _DEFAULT_URL = "localhost";
	public final int _DEFAULT_PORT = 9669;
	
	private TCPClient client;
	
	public Vector<Sensor> communicate(Vector<Command> commands) throws Exception {
		client.send( CommandGenerator.toMsg(commands) );
		
		String received = client.recieve();
		
		Vector<Sensor> sensordata = Sensor.splitReturnMsg(received);
		
		return sensordata;
	}
	
	public NAOProxyClient(String URL, int port) throws Exception {
		client = new TCPClient(URL, port);
	}	
	
	public void close() throws Exception  {
		client.close();
	}
}
