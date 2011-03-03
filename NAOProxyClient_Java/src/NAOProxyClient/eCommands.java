package NAOProxyClient;

public enum eCommands {
	MOVE(0),
	TURN(1),
	HALT(2),
	INITPOSE(3),
	STIFFNESS(4),
	SAY(5),
    HEADMOVE(6),
    HEADRESET(7),
    COWER(8),    
    RESET(9),
    CONSUME(10),
	UNKNOWN(999);
	
	public final int id;
	eCommands(int id) {
		this.id = id;
	}
	
	public String toString() {
		return ""+this.id;
	}
	
	public static eCommands idToEnum(int id)  {
		eCommands result = UNKNOWN;
		
		for (eCommands p : eCommands.values()) {
			if (id == p.id) {
				result = p;
				break;
			}
		}
		
		return result;
	}	
}
