/**
 * eAffectLevel.java: DecisionUnits - pa._v19.memorymgmt.enums
 * 
 * @author kohlhauser
 * 31.08.2010, 13:38:25
 */
package pa._v19.memorymgmt.enums;

/**
 * DOCUMENT (kohlhauser) - Enumeration class for the affect levels and conversions to strings.
 * 
 * For comparisons see java API for Enum class
 * 
 * General comparison: compareTo(E)
 * eAffectLevel.HIGH.compareTo(eAffectLevel.LOW)
 * returns a negative value
 * eAffectLevel.HIGH.compareTo(eAffectLevel.HIGH)
 * returns 0
 * eAffectLevel.LOW.compareTo(eAffectLevel.HIGH)
 * returns a postive value
 * 
 * If you want to compare two affect levels you can also use the ordinal() method of enums
 * it returns the integer value of the enum element (the first is 0, the second 1, etc)
 * eAffectLevel.HIGH.ordinal() > eAffectLevel.LOW.ordinal()
 * returns "true"
 * 
 * For equality you can also use the equals(Object) method. 
 * 
 * @author kohlhauser
 * 31.08.2010, 13:38:25
 * 
 */
public enum eAffectLevel {
	VERYLOW ("VERYLOW"),
	LOW ("LOW"),
	MEDIUM ("MEDIUM"),
	HIGH ("HIGH"),
	VERYHIGH ("VERYHIGH");
	
	private String lvl;
	
	eAffectLevel(String lvl)
	{
		this.lvl = lvl;
	}

	@Override
	public String toString()
	{
		return lvl;
	}
	
	public static String[] getAffectLevels()
	{
		eAffectLevel lvls[] = eAffectLevel.values();
		int amount = lvls.length;
		String olvls[] = new String[amount];

		for (int i = 0; i < amount; i++)
			olvls[i] = lvls[i].toString();
		
		return olvls;
	}
	
	public static eAffectLevel elementAt(int i)
	{
		eAffectLevel lvls[] = eAffectLevel.values();
		return lvls[i];
	}
	
	@Deprecated
	public static boolean compare(eAffectLevel poAffectLevel1, eAffectLevel poAffectLevel2){
		boolean oRetVal = false; 
		
		if(poAffectLevel1 == LOW && (poAffectLevel2 == VERYLOW)){oRetVal = true;}
		else if (poAffectLevel1 == MEDIUM && (poAffectLevel2 == VERYLOW || poAffectLevel2 == LOW)){oRetVal = true;}
		else if (poAffectLevel1 == HIGH && (poAffectLevel2 != VERYHIGH)){oRetVal = true;}
		else if (poAffectLevel1 == VERYHIGH){oRetVal = true;}
		
		return oRetVal;
	}
}
