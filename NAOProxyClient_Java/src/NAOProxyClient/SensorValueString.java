package NAOProxyClient;

public class SensorValueString extends SensorValueTuple {

	public SensorValueString(String data) {
		super(data);
	}
	
	public String getValue() {
		return value;
	}

}
