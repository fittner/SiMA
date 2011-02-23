package JNAOProxy;

public class SensorTuple {
	protected String name = "";
	protected String value = "";
	protected final static String namedelimiter = ":";
	
	public SensorTuple(String data) {
		split(data);
	}
	
	protected void split(String data) {
		String[] temp = data.split(namedelimiter);
		name = temp[0];
		value = temp[1];
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
