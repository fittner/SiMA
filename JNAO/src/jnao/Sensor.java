package jnao;

import java.util.Arrays;
import java.util.Vector;


public class Sensor {
	private final static String delimiter = ",";
	public final static String outerdelimiter = ";";

	public final eSensors id;
	public Vector<SensorTuple> params;	
	
	public Sensor(eSensors id) {
		this.id = id;
		this.params = new Vector<SensorTuple>();
	}
	public Sensor(eSensors id, Vector<SensorTuple> params) {
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
		Vector<String> strparams = new Vector<String>(Arrays.asList(temp));
		
		Vector<SensorTuple> params = new Vector<SensorTuple>();
		for (String param:strparams) {
			params.add( new SensorTuple(param) );
		}
		
		return new Sensor(id, params);
	}
	
	public static Vector<Sensor> splitReturnMsg(String msg) {
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

}
