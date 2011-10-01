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
	//EATABLE (0),
	MANIPULATEABLE (0),
	NEAR (1),		
	MEDIUM (2),	
	FAR(3);		
	
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
}
