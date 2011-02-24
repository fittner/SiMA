package NAOProxyClient;

public class SensorVision extends SensorTuple {
	private eVisionEntryTypes type;
	private double r;
	private double a;
	private final static String delimiter = "@";

	public SensorVision(String data) {
		super(data);

		type = eVisionEntryTypes.UNKNOWN;
		r = 0;
		a = 0;
		
		valuesplit();
	}
	
	private void valuesplit() {
		try {
			String[] temp2 = value.split(delimiter);
			type = eVisionEntryTypes.idToEnum( Integer.parseInt(temp2[0]) );
			r = Float.parseFloat( temp2[1] );
			a = Float.parseFloat( temp2[2] );
		} catch (Exception e) {
			
		}
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
