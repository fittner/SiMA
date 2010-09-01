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
	
	public static eAffectLevel stringToAffectLevel(String lvl)
	{
		for (eAffectLevel level : eAffectLevel.values())
			if (level.toString().equals(lvl.toString()))
				return level;
		
		return null;
	}
	
	public static eAffectLevel elementAt(int i)
	{
		eAffectLevel lvls[] = eAffectLevel.values();
		return lvls[i];
	}
}
