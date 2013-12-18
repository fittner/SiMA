/**
 * CHANGELOG
 *
 * 01.10.2011 wendt - File created
 *
 */
package memorymgmt.enums;


/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2011, 09:31:08
 * 
 */
public enum ePhiPosition {

	LEFT (-2),
	MIDDLE_LEFT (-1),
	CENTER (0),
	MIDDLE_RIGHT (1),	
	RIGHT (2),
	UNKNOWNPOSITION (666);
	
	public int mnPos;
	
	/**
	 * DOCUMENT - insert description
	 *
	 * @author wendt
	 * @since 03.12.2013 12:11:37
	 *
	 * @param pnPos
	 */
	ePhiPosition(int pnPos)
	{
		this.mnPos = pnPos;
	}
	
//	/**
//	 * DOCUMENT - insert description
//	 *
//	 * @author wendt
//	 * @since 03.12.2013 12:11:31
//	 *
//	 * @param i
//	 * @return
//	 */
//	public static ePhiPosition elementAt(int i)
//	{
//		int nMinRange = -2;
//		int nMaxRange = 2;
//		
//		ePhiPosition oRetVal = null;
//		ePhiPosition lvls[] = ePhiPosition.values();
//		if ((i>=nMinRange) && (i<=nMaxRange)) {
//			oRetVal = lvls[i-nMinRange];
//		}
//		
//		return oRetVal;
//	}
	
//	/**
//	 * DOCUMENT - insert description
//	 *
//	 * @author wendt
//	 * @since 03.12.2013 12:11:28
//	 *
//	 * @param poInput
//	 * @return
//	 */
//	public static int getValue(String poInput) {
//		int oRetVal = -10;
//		
//		for (ePhiPosition oE : ePhiPosition.values()) {
//			if (poInput.equals(oE.toString())) {
//				oRetVal = oE.mnPos;
//			}
//		}
//		
//		return oRetVal;
//	}
	
	/**
	 * DOCUMENT - insert description
	 *
	 * @author wendt
	 * @since 03.12.2013 12:11:41
	 *
	 * @param poCompareString
	 * @return
	 */
	public static ePhiPosition elementAt(String poCompareString) {
		ePhiPosition oRetVal = null;
				
		if (poCompareString.equals("EATABLE")==true || poCompareString.equals("MANIPULATABLE")==true) {
			//Eatable is everything around the agent
			oRetVal = null;
		} else {
			ePhiPosition lvls[] = ePhiPosition.values();
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
