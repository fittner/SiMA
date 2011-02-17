
public enum eSensors {
	VISION(0),
	ODOMETRY(1),
	DISTANCE(2),
	UNKOWN(999);
	
	public final int id;
	eSensors(int id) {
		this.id = id;
	}
	
	public String toString() {
		return ""+this.id;
	}
	
	public static eSensors idToEnum(int id)  {
		eSensors result = UNKOWN;
		
		for (eSensors p : eSensors.values()) {
			if (id == p.id) {
				result = p;
				break;
			}
		}
		
		return result;
	}
}

