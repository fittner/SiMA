/**
 * CHANGELOG
 *
 * 19.02.2012 wendt - File created
 *
 */
package pa._v38.memorymgmt.enums;

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
	NEAROF (1),
	FAROF (2);
	
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
		
		if (prDistance > 1) {
			oRetVal = eDistanceRelation.FAROF;
		} else {
			oRetVal = eDistanceRelation.NEAROF;
		}
		
		return oRetVal;
	}

}
