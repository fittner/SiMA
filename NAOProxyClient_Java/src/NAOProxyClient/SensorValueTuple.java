package NAOProxyClient;

public class SensorValueTuple {
	protected String name = "";
	protected String value = "";
	protected final static String namedelimiter = ":";
	
	public SensorValueTuple(String data) {
		split(data);
	}
	
	protected void split(String data) {
		String[] temp = data.split(namedelimiter);
		name = temp[0];
		try {
			value = temp[1];
		} catch (Exception e) {
			value = "";
		}
	}
	
	public String toString() {
		return getName()+namedelimiter+getValue();
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}
