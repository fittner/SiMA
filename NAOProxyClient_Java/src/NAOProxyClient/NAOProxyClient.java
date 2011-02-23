package NAOProxyClient;

import java.util.Vector;


public class NAOProxyClient {
	private TCPClient client;
	
	public Vector<Sensor> communicate(Command cmd) throws Exception {
		client.send(cmd.toMsg());
		
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
