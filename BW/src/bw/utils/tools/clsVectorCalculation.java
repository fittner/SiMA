/**
 * @author langr
 * 01.04.2009, 17:31:49
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

import sim.physics2D.util.Double2D;

/**
 * cartesian to polar and vice versa
 * 
 * @author langr
 * 01.04.2009, 17:31:49
 * 
 */
public class clsVectorCalculation {

	/**
	 * returns the angle of the vector clockwise starting as shown (MASON compatible with getOrientation)
	 * 
	 * 
	 * 					  2PI/3
	 *                     |
	 *                     |
	 *                     |
	 *                     |
	 *       PI  ------------------------   0
	 *                     |
	 *                     |
	 *                     |
	 *                     |
	 *                   PI/2 
	 * 
	 *
	 * @author langr
	 * 01.04.2009, 17:41:23
	 *
	 * @param poDirection
	 * @return
	 */
	public static double getDirectionPolar(Double2D poDirection) {
		
		double oAbsAngle = Math.atan(poDirection.y/poDirection.x);
		
		if( poDirection.x <= 0 && poDirection.y > 0 ) {
			oAbsAngle = Math.PI+oAbsAngle;
		}
		if( poDirection.x < 0 && poDirection.y <= 0 ) {
			oAbsAngle = oAbsAngle+Math.PI;
		}
		if( poDirection.x > 0 && poDirection.y <= 0 ) {
			oAbsAngle = (2*Math.PI)+oAbsAngle;
		}

		return oAbsAngle;
		
	}
	
}
