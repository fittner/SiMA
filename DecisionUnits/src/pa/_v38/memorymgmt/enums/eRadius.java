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
public enum eRadius {
	NODISTANCE (0), 
	NEAR (1),		
	MEDIUM (2),	
	FAR(3);		
	
	public int mnPos;
	
	eRadius(int pnPos)
	{
		this.mnPos = pnPos;
	}
	
	public static eRadius elementAt(int i)
	{
		int nMinRange = 0;
		int nMaxRange = 3;
		
		eRadius oRetVal = null;
		
		eRadius lvls[] = eRadius.values();
		if ((i>=nMinRange) && (i<=nMaxRange)) {
			oRetVal = lvls[i+nMinRange];
		}
		
		return oRetVal;
	}
	
	public static int getValue(String poInput) {
		int oRetVal = -10;
		
		for (eRadius oE : eRadius.values()) {
			if (poInput.equals(oE.toString())) {
				oRetVal = oE.mnPos;
			}
		}
		
		return oRetVal;
	}
	
	public static eRadius elementAt(String poCompareString) {
		eRadius oRetVal = null;
		
		if (poCompareString.equals("EATABLE")==true || poCompareString.equals("MANIPULATEABLE")==true) {
			oRetVal = NEAR;
		} else {
			eRadius lvls[] = eRadius.values();
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
