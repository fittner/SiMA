/**
 * CHANGELOG
 *
 * 19.02.2012 wendt - File created
 *
 */
package pa._v38.memorymgmt.enums;

import pa._v38.tools.clsPair;

/**
 * Enum for position relations
 * 
 * (wendt)
 * 
 * @author wendt
 * 19.02.2012, 15:49:52
 * 
 */
public enum ePositionRelation {
	GENERAL (-100), 
	LEFTBEHINDOF (-3),
	LEFTOF (-2),
	LEFTINFRONTOF (-1),
	INFRONTOF (0),
	RIGHTINFRONTOF (1),
	RIGHTOF (2),
	RIGHTBEHINDOF (3),
	BEHINDOF (4),	
	ONPOSITION (8);

	public int mnDirection;
	
	ePositionRelation(int pnDirection) {
		mnDirection = pnDirection;
	}
	
	/**
	 * Get the position relation. Only the direction play a role.
	 * 
	 * (wendt)
	 *
	 * @since 19.02.2012 16:34:13
	 *
	 * @param poRelationVector
	 * @return
	 */
	public static ePositionRelation getPositionRelation(clsPair<Double, Double> poRelationVector) {
		ePositionRelation oRetVal = null;
		
		double rX = poRelationVector.a;
		double rY = poRelationVector.b;
		
		if (rX==0) {
			if (rY>0) {
				oRetVal = ePositionRelation.INFRONTOF;
			} else if (rY<0) {
				oRetVal = ePositionRelation.BEHINDOF;
			} else if (rY==0) {
				oRetVal = ePositionRelation.ONPOSITION;
			}
				
		} else if (rX<0) {
			if (rY>0) {
				oRetVal = ePositionRelation.LEFTINFRONTOF;
			} else if (rY<0) {
				oRetVal = ePositionRelation.LEFTBEHINDOF;
			} else if (rY==0) {
				oRetVal = ePositionRelation.LEFTOF;
			}
		} else if (rX>0){
			if (rY>0) {
				oRetVal = ePositionRelation.RIGHTINFRONTOF;
			} else if (rY<0) {
				oRetVal = ePositionRelation.RIGHTBEHINDOF;
			} else if (rY==0) {
				oRetVal = ePositionRelation.RIGHTOF;
			}
		}
		
		return oRetVal;
	}
	
}
