/**
 * @author zeilinger
 * 20.07.2009, 16:26:54
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.sensors;

import bfg.utils.enums.eSide;
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsCollidingObject;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.07.2009, 16:26:54
 * 
 */
public class clsSensorDataCalculation {

	private static Double2D moVecColObj;
	private static Double2D moVecEntObj;
	
	/**
	 * TODO (zeilinger) - Tests if an object is within an agent's field of
	 * view
	 *
	 * @param pnColPointOrientation ... direction of the collision point 
	 * 									referred to the entity in radians 			
	 * 		  pnEntityOrientation ... orientation of the entity in radians
	 * 		  pnViewArea ... defines the which is covered - e. g. field of
	 * 						 view of an entity. The parameter represents the 
	 * 						 angle in radians  
	 * @return boolean
	 */
	public static  boolean checkIfObjectInView(clsCollidingObject pnCollidingObject, double pnEntityOrientation, double pnAreaOfViewRadians){
		
		double nAngleDiff; 
		nAngleDiff = calculateAngleDifference(pnCollidingObject, pnEntityOrientation);
		
		if(nAngleDiff <=pnAreaOfViewRadians/2){
			return true; 
		}
		return false; 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.09.2009, 10:16:46
	 *
	 * @param pnCollidingObject
	 * @param pnEntityOrientation
	 * @return
	 */
	private static double calculateAngleDifference(
									clsCollidingObject pnCollidingObject, double pnEntityOrientation) {
		double nColObjX = Math.cos(pnCollidingObject.mrColPoint.moAzimuth.radians); 
		double nColObjY = Math.sin(pnCollidingObject.mrColPoint.moAzimuth.radians); 
		double nEntObjX = Math.cos(pnEntityOrientation); 
		double nEntObjY = Math.sin(pnEntityOrientation);
		
		moVecColObj = new Double2D(nColObjX, nColObjY); 
		moVecEntObj = new Double2D(nEntObjX, nEntObjY); 
		
		return Math.acos(moVecEntObj.dotProduct(moVecColObj)/(moVecColObj.length()*moVecEntObj.length())); 
	}

	public static eSide getRelativePositionOfCollidingObject(
				   clsCollidingObject pnCollidingObject, double pnEntityOrientation, double pnAreaOfViewRadians){
		double nAngleDiff = calculateAngleDifference(pnCollidingObject, pnEntityOrientation);
		eSide eRelativePos = eSide.UNDEFINED; 
				
		if (nAngleDiff <= pnAreaOfViewRadians/18)
			eRelativePos = eSide.CENTER;
		else if(nAngleDiff <= pnAreaOfViewRadians/4){
			if (getPosOrNegOrientation()<0)
				eRelativePos = eSide.MIDDLE_LEFT;
			else
				eRelativePos = eSide.MIDDLE_RIGHT;
		}
		else {
				if(getPosOrNegOrientation()<0){
					eRelativePos = eSide.LEFT; 
				}
				else{
				    eRelativePos = eSide.RIGHT; 
				}
		}
			
		return eRelativePos;  
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.09.2009, 11:08:19
	 *
	 * @return
	 */
	private static double getPosOrNegOrientation() {
		// TODO (zeilinger) - Auto-generated method stub
		double nVectorOrientation = moVecColObj.y*moVecEntObj.x - moVecEntObj.y*moVecColObj.x; 
	
		return nVectorOrientation;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.02.2009, 16:22:39
	 *
	 * @param pnOrientation
	 * @return
	 */
	public static double normalizeRadian(double pnEntityOrientation){
		double newVal =  pnEntityOrientation; 
		double twoPI = 2* Math.PI; 
		
		while(newVal > twoPI)
	            newVal -= twoPI;
	                
	    while(newVal < 0)
	            newVal += twoPI;
	  return newVal;  
	}
		
}
