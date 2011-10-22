/**
 * CHANGELOG
 *
 * 01.10.2011 wendt - File created
 *
 */
package pa._v38.memorymgmt.enums;


/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2011, 09:31:08
 * 
 */
public enum eXPosition {

	LEFT (-2),
	MIDDLE_LEFT (-1),
	CENTER (0),		
	MIDDLE_RIGHT (1),	
	RIGHT (2);		
	
	public int mnPos;
	
	eXPosition(int pnPos)
	{
		this.mnPos = pnPos;
	}
	
	public static eXPosition elementAt(int i)
	{
		int nMinRange = -2;
		int nMaxRange = 2;
		
		eXPosition oRetVal = null;
		eXPosition lvls[] = eXPosition.values();
		if ((i>=nMinRange) && (i<=nMaxRange)) {
			oRetVal = lvls[i-nMinRange];
		}
		
		return oRetVal;
	}
	
	public static int getValue(String poInput) {
		int oRetVal = -10;
		
		for (eXPosition oE : eXPosition.values()) {
			if (poInput.equals(oE.toString())) {
				oRetVal = oE.mnPos;
			}
		}
		
		return oRetVal;
	}
}
