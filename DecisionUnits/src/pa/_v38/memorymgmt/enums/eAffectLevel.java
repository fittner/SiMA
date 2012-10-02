/**
 * eAffectLevel.java: DecisionUnits - pa._v38.memorymgmt.enums
 * 
 * @author kohlhauser
 * 31.08.2010, 13:38:25
 */
package pa._v38.memorymgmt.enums;

/**
 * DOCUMENT (kohlhauser) - Enumeration class for the affect levels and conversions to strings.
 * 
 * XXXX OLD XXXXX
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
 * XXXX OLD XXXXX
 * 
 * @author kohlhauser
 * 31.08.2010, 13:38:25
 * 
 */
public enum eAffectLevel {
	HIGHNEGATIVE (-1000),	//-10 < x <= -0.8
	NEGATIVE (-600),		//-0.8 < x <= -0.5
	LOWNEGATIVE (-200),			//-0.5 < x <= -0.1
	INSIGNIFICANT (0),		//-0.1 < x <= 0.1
	LOWPOSITIVE (200),			//0.1 < x <= 0.5
	POSITIVE (600),		//0.5 < x <= 0.8
	HIGHPOSITIVE (1000);	//0.8 < x <= 10
	
	public int mnAffectLevel;
	
	eAffectLevel(int pnAffectLevel)
	{
		this.mnAffectLevel = pnAffectLevel;
	}
	
	public static eAffectLevel elementAt(int i)
	{
		eAffectLevel oRetVal = null;
		eAffectLevel lvls[] = eAffectLevel.values();
		for (int j=0; j<lvls.length-1;j++) {
			eAffectLevel oBottom = lvls[j];
			eAffectLevel oTop = lvls[j+1];
			
			if (j<=oBottom.mnAffectLevel && j==0) {
				oRetVal=oBottom;
				break;
			} else if (j>=oTop.mnAffectLevel && j==lvls.length-2) {
				oRetVal=oTop;
				break;
			} else if (j>=oBottom.mnAffectLevel && j<oTop.mnAffectLevel) {
				oRetVal = oBottom;
				break;
			}
		}
//		if ((i>=-3) && (i<=3)) {
//			oRetVal = lvls[i+3];
//		}
		
		return oRetVal;
	}
	
	/**
	 * Convert the quota of affect as double to an affect level
	 * 
	 * (wendt)
	 *
	 * @since 20.07.2012 21:19:16
	 *
	 * @param poQoA
	 * @return
	 */
	public static eAffectLevel convertQuotaOfAffectToAffectLevel(double poQoA) {
		eAffectLevel oResult = eAffectLevel.INSIGNIFICANT;
		
		if (poQoA <=-0.90) {
			oResult = eAffectLevel.HIGHNEGATIVE;
		} else if (poQoA >-0.90 && poQoA <=-0.50) {
			oResult = eAffectLevel.NEGATIVE;
		} else if (poQoA >-0.50 && poQoA <=-0.05) {
			oResult = eAffectLevel.LOWNEGATIVE;
		} else if (poQoA >-0.05 && poQoA <=0.05) {
			oResult = eAffectLevel.INSIGNIFICANT;
		} else if (poQoA >0.05 && poQoA <=0.50) {
			oResult = eAffectLevel.LOWPOSITIVE;
		} else if (poQoA >0.50 && poQoA <=0.90) {
			oResult = eAffectLevel.POSITIVE;
		} else if (poQoA >0.90) {
			oResult = eAffectLevel.HIGHPOSITIVE;
		} else {
			try {
				throw new Exception("Error: No acceptable value");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return oResult;
	}

	/*@Override
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
	}*/
}
