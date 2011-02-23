package jnao;

public enum eVisionEntryTypes {
	CAKE(0),
	BUBBLE(1),
	CAN(2),
	STONE(3),
    UNKNOWN(999);
	
	public final int id;
	eVisionEntryTypes(int id) {
		this.id = id;
	}
	
	public String toString() {
		return ""+this.id;
	}
	
	public static eVisionEntryTypes idToEnum(int id)  {
		eVisionEntryTypes result = UNKNOWN;
		
		for (eVisionEntryTypes p : eVisionEntryTypes.values()) {
			if (id == p.id) {
				result = p;
				break;
			}
		}
		
		return result;
	}
}
