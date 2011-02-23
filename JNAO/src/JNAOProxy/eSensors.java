package JNAOProxy;

public enum eSensors {
    BUMP(0),
    BATTERY(1),
    TEMPERATURE(2),
    FSR(3),
    ODOMETRY(4),
    SONAR(5),
    POSITION(6),
    SENTINEL(7),
    VISION(8),
    CONSUMESUCCESS(9),    
    UNKNOWN(999);
	
	public final int id;
	eSensors(int id) {
		this.id = id;
	}
	
	public String toString() {
		return ""+this.id;
	}
	
	public static eSensors idToEnum(int id)  {
		eSensors result = UNKNOWN;
		
		for (eSensors p : eSensors.values()) {
			if (id == p.id) {
				result = p;
				break;
			}
		}
		
		return result;
	}
}

