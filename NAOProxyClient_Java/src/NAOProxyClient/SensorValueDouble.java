package NAOProxyClient;

public class SensorValueDouble extends SensorValueTuple {
	private double rvalue;

	public SensorValueDouble(String data) {
		super(data);
		setDouble();
	}

	public double getDouble() {
		return rvalue;
	}
	
	private void setDouble() {
		rvalue = Double.parseDouble(value);
	}
	
	public String toString() {
		return name+namedelimiter+rvalue;
	}
}
