package jnao;

public enum eCommands {
	MOVE(0),
	TURN(1),
	HALT(2),
	INITPOSE(3),
	STIFFNESS(4),
	UNKOWN(999);
	
	public final int id;
	eCommands(int id) {
		this.id = id;
	}
	
	public String toString() {
		return ""+this.id;
	}
	
	public static eCommands idToEnum(int id)  {
		eCommands result = UNKOWN;
		
		for (eCommands p : eCommands.values()) {
			if (id == p.id) {
				result = p;
				break;
			}
		}
		
		return result;
	}	
}
