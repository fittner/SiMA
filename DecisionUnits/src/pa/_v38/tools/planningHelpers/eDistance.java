/**
 * CHANGELOG
 *
 * 12.07.2011 perner - File created
 *
 */
package pa._v38.tools.planningHelpers;

import pa._v38.memorymgmt.enums.eRadius;

/**
 * DOCUMENT (perner) - insert description 
 * 
 * @author perner
 * 12.07.2011, 18:26:54
 * 
 */
public enum eDistance {

	FAR,
	MEDIUM,
	NEAR,
	NODISTANCE;
	
	public static eDistance getDistance(eRadius poRadius) {
		eDistance oResult = null;
		
		if (poRadius.equals(eRadius.NODISTANCE)) {
			oResult = eDistance.NODISTANCE;
		} else if (poRadius.equals(eRadius.NEAR)) {
			oResult = eDistance.NEAR;
		} else if (poRadius.equals(eRadius.MEDIUM)) {
			oResult = eDistance.MEDIUM;
		} else if (poRadius.equals(eRadius.FAR)) {
			oResult = eDistance.FAR;
		} 
		
		return oResult;
	}
	
	
/*	
	FAR,
	MEDIUM,
	CLOSE,
	UNKNOWN,
	INHAND,

	FARRIGHT,
	FARLEFT,
	FARCENTER,
	FARMIDDLERIGHT,
	FARMIDDLELEFT,
	
	MEDIUMRIGHT,
	MEDIUMLEFT,
	MEDIUMCENTER,
	MEDIUMMIDDLERIGHT,
	MEDIUMMIDDLELEFT,
	
	NEARRIGHT,
	NEARLEFT,
	NEARCENTER,
	NEARMIDDLE_RIGHT,
	NEARMIDDLELEFT

*/
}
