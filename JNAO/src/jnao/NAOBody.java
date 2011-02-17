package jnao;

import java.util.Arrays;
import java.util.Vector;


public class NAOBody {
	private TCPClient client;
	
	private Vector<Sensor> splitReturnMsg(String msg) {
		Vector<Sensor> sensors= new Vector<Sensor>();
		
		if (msg!=null && msg.length() > 0) {
			String[] temp = msg.split(Sensor.outerdelimiter);
	
			Vector<String> smsg = new Vector<String>(Arrays.asList(temp));
			for (String s:smsg) {
				Sensor sensor = Sensor.stringToSensor(s);
				sensors.add(sensor);
			}
		}
		
		return sensors;
	}
	
	public Vector<Sensor> communicate(Command cmd) throws Exception {
		client.send(cmd.toMsg());
		
		String received = client.recieve();
		
		Vector<Sensor> sensordata = splitReturnMsg(received);
		
		return sensordata;
	}
	
	public NAOBody(String URL, int port) throws Exception {
		client = new TCPClient(URL, port);
	}	
	
	public void close() throws Exception  {
		client.close();
	}
}
