/**
 * CHANGELOG
 *
 * 12.07.2011 perner - File created
 *
 */
package system.algorithm.planningHelpers;

import pa._v38.memorymgmt.enums.ePhiPosition;

/**
 * DOCUMENT (perner) - insert description 
 * 
 * @author perner
 * 12.07.2011, 18:28:18
 * 
 */
public enum eDirection {

	LEFT,
	RIGHT,
	CENTER,
	MIDDLE_LEFT,
	MIDDLE_RIGHT;
	
	public static eDirection getDirection(ePhiPosition poPhiPos) {
		eDirection oResult = null;
		
		if (poPhiPos.equals(ePhiPosition.LEFT)) {
			oResult = eDirection.LEFT;
		} else if (poPhiPos.equals(ePhiPosition.MIDDLE_LEFT)) {
			oResult = eDirection.MIDDLE_LEFT;
		} else if (poPhiPos.equals(ePhiPosition.CENTER)) {
			oResult = eDirection.CENTER;
		} else if (poPhiPos.equals(ePhiPosition.MIDDLE_RIGHT)) {
			oResult = eDirection.MIDDLE_RIGHT;
		} else if (poPhiPos.equals(ePhiPosition.RIGHT)) {
			oResult = eDirection.RIGHT;
		} 
		
		return oResult;
	}
}
