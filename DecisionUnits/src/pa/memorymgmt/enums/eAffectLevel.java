/**
 * eAffectLevel.java: DecisionUnits - pa.memorymgmt.enums
 * 
 * @author kohlhauser
 * 31.08.2010, 13:38:25
 */
package pa.memorymgmt.enums;

/**
 * DOCUMENT (kohlhauser) - Enumeration class for the affect levels and conversions to strings.
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
	
	public static boolean compare(eAffectLevel poAffectLevel1, eAffectLevel poAffectLevel2){
		boolean oRetVal = false; 
		
		if(poAffectLevel1 == LOW && (poAffectLevel2 == VERYLOW)){oRetVal = true;}
		else if (poAffectLevel1 == MEDIUM && (poAffectLevel2 == VERYLOW || poAffectLevel2 == LOW)){oRetVal = true;}
		else if (poAffectLevel1 == HIGH && (poAffectLevel2 != VERYHIGH)){oRetVal = true;}
		else if (poAffectLevel1 == VERYHIGH){oRetVal = true;}
		
		return oRetVal;
	}
}
