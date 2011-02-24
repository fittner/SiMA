package NAOProxyClient;

public class SensorValueBump extends SensorValueTuple {
	private boolean bumped;

	public SensorValueBump(String data) {
		super(data);
		setBumped();
	}
	
	private void setBumped() {
		if (value.equals("0.0")) {
			bumped = false;
		} else {
			bumped = true;
		}
	}
	
	public boolean getBumped() {
		return bumped;
	}

	public String toString() {
		return getName()+namedelimiter+getBumped();
	}
}
