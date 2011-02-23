package jnao;

public class SensorVision extends SensorTuple {
	private eVisionEntryTypes type;
	private double r;
	private double a;
	private final String delimiter = "@";

	public SensorVision(String data) {
		super(data);

		type = eVisionEntryTypes.UNKNOWN;
		r = 0;
		a = 0;
	}
	
	protected void split(String data) {
		String[] temp = data.split(namedelimiter);
		name = temp[0];
		value = temp[1];
		
		String[] temp2 = value.split(delimiter);
		type = eVisionEntryTypes.valueOf( temp2[0] );
		r = Float.parseFloat( temp[1] );
		a = Float.parseFloat( temp[2] );
	}	
	
	public String getValue() {
		return type + delimiter + r + delimiter + a;
	}
	
	public eVisionEntryTypes getType() {
		return type;
	}
	
	public double getR() {
		return r;
	}
	
	public double getA() {
		return a;
	}

}
