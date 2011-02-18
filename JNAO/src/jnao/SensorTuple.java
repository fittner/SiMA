package jnao;

public class SensorTuple {
	private String name = "";
	private String value = "";
	private final static String namedelimiter = ":";
	
	public SensorTuple(String data) {
		split(data);
	}
	
	private void split(String data) {
		String[] temp = data.split(namedelimiter);
		name = temp[0];
		value = temp[1];
	}
	
	public String toString() {
		return name+namedelimiter+value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}
