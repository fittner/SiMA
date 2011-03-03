package NAOProxyClient;

import java.util.Arrays;
import java.util.Vector;


public class Sensor {
	private final static String delimiter = ",";
	public final static String outerdelimiter = ";";

	public final eSensors id;
	public Vector<SensorValueTuple> values;	
	
	public Sensor(eSensors id) {
		this.id = id;
		this.values = new Vector<SensorValueTuple>();
	}
	public Sensor(eSensors id, Vector<SensorValueTuple> params) {
		this.id = id;
		
		this.values = params;
	}
	
	public String toString() {
		return id+":"+values;
	}
	
	public static Sensor stringToSensor(String data) {
		String tmp = data.substring(0, data.indexOf(Sensor.delimiter));
		int i = Integer.parseInt(tmp.trim());
		eSensors id = eSensors.idToEnum(i);
		
		String subdata = data.substring(data.indexOf(Sensor.delimiter)+1);
		String[] temp = subdata.split(Sensor.delimiter);
		Vector<String> strparams = new Vector<String>(Arrays.asList(temp));
		
		Vector<SensorValueTuple> params = new Vector<SensorValueTuple>();
		for (String param:strparams) {
			if (param.length() == 0) {
				continue;
			}
			
			switch (id) {
				case VISION: params.add( new SensorValueVision(param) );
					break;
					
				case BUMP: params.add( new SensorValueBump(param));
				    break;
				    
				case SENTINEL: params.add( new SensorValueString(param) );
					break;

				default:params.add( new SensorValueDouble(param) );
					break;
			}
			
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
