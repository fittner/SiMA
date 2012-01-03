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
 * 01.10.2011, 09:31:30
 * 
 */
public enum eYPosition {
	NEAR (0),		
	MEDIUM (1),	
	FAR(2);		
	
	public int mnPos;
	
	eYPosition(int pnPos)
	{
		this.mnPos = pnPos;
	}
	
	public static eYPosition elementAt(int i)
	{
		int nMinRange = 0;
		int nMaxRange = 3;
		
		eYPosition oRetVal = null;
		
		eYPosition lvls[] = eYPosition.values();
		if ((i>=nMinRange) && (i<=nMaxRange)) {
			oRetVal = lvls[i+nMinRange];
		}
		
		return oRetVal;
	}
	
	public static int getValue(String poInput) {
		int oRetVal = -10;
		
		for (eYPosition oE : eYPosition.values()) {
			if (poInput.equals(oE.toString())) {
				oRetVal = oE.mnPos;
			}
		}
		
		return oRetVal;
	}
	
	public static eYPosition elementAt(String poCompareString) {
		eYPosition oRetVal = null;
		
		if (poCompareString.equals("EATABLE")==true || poCompareString.equals("MANIPULATEABLE")==true) {
			oRetVal = NEAR;
		} else {
			eYPosition lvls[] = eYPosition.values();
			for (int i=0;i<lvls.length;i++) {
				if (poCompareString.equals(lvls[i].toString())) {
					oRetVal = lvls[i];
					break;
				}
			}
		}

		return oRetVal;
	}
}
