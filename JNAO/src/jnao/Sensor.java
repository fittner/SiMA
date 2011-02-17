package jnao;

import java.util.Arrays;
import java.util.Vector;


public class Sensor {
	private final static String delimiter = ",";
	public final static String outerdelimiter = ";";

	public final eSensors id;
	public Vector<String> params;	
	
	public Sensor(eSensors id) {
		this.id = id;
		params = new Vector<String>();
	}
	public Sensor(eSensors id, Vector<String> params) {
		this.id = id;
		this.params = params;
	}
	
	public String toString() {
		return id+":"+params;
	}
	
	public static Sensor stringToSensor(String data) {
		String tmp = data.substring(0, data.indexOf(Sensor.delimiter));
		int i = Integer.parseInt(tmp.trim());
		eSensors id = eSensors.idToEnum(i);
		
		String subdata = data.substring(data.indexOf(Sensor.delimiter)+1);
		String[] temp = subdata.split(Sensor.delimiter);
		Vector<String> params = new Vector<String>(Arrays.asList(temp));
		
		return new Sensor(id, params);
	}

}
