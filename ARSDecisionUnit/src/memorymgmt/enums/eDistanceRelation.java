/**
 * CHANGELOG
 *
 * 19.02.2012 wendt - File created
 *
 */
package memorymgmt.enums;

/**
 * Enum of a distance relation
 * 
 * (wendt)
 * 
 * @author wendt
 * 19.02.2012, 15:49:34
 * 
 */
public enum eDistanceRelation {
	GENERAL (0),
	NEAROF (1),
	MEDIUMOF (2),
	FAROF (3),
	OUT_OF_SIGHT_OF (4);
	
	public int mnDistance;
	
	eDistanceRelation(int pnDistance) {
		mnDistance = pnDistance;
	}
	
	/**
	 * Get the distance relation enum from a double > 0
	 * 
	 * (wendt)
	 *
	 * @since 19.02.2012 16:20:23
	 *
	 * @param prDistance
	 * @return
	 */
	public static eDistanceRelation getDistanceRelation(double prDistance) {
		eDistanceRelation oRetVal = null;
		
		if (prDistance<0) {
			try {
				throw new Exception("Distance < 0");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (prDistance >= 0 && prDistance < 1) {
			oRetVal = eDistanceRelation.NEAROF;
		} else if (prDistance >= 1 && prDistance < 2) {
			oRetVal = eDistanceRelation.MEDIUMOF;
		} else if (prDistance >= 2) {
			oRetVal = eDistanceRelation.FAROF;
		}
		
		return oRetVal;
	}

}
