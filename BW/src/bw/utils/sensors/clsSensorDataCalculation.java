/**
 * @author zeilinger
 * 20.07.2009, 16:26:54
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.sensors;

import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsCollidingObject;
import ARSsim.physics2D.util.clsPolarcoordinate;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.07.2009, 16:26:54
 * 
 */
public class clsSensorDataCalculation {

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
		
		double nAngleDiff = 0; 
		double nColObjX = Math.cos(pnCollidingObject.mrColPoint.moAzimuth.radians); 
		double nColObjY = Math.sin(pnCollidingObject.mrColPoint.moAzimuth.radians); 
		//System.out.println(nColObjX +" "+ nColObjY); 
		double nEntObjX = Math.cos(pnEntityOrientation); 
		double nEntObjY = Math.sin(pnEntityOrientation);
		
		Double2D oVecColObj = new Double2D(nColObjX, nColObjY); 
		Double2D oVecEntObj = new Double2D(nEntObjX, nEntObjY); 
		
		nAngleDiff = Math.acos(oVecEntObj.dotProduct(oVecColObj)/(oVecColObj.length()*oVecEntObj.length())); 
		
		if(nAngleDiff <=pnAreaOfViewRadians/2){
			return true; 
		}
		return false; 
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
	
	/**
	 * TODO (zeilinger) - returns the angle of the relative position
	 * to the perceived objectn
	 *
	 * @param poPos
	 * @return nOrientation 
	 */
	
	public static clsPolarcoordinate getRelativeCollisionPosition(Double2D poCollisionPosition){   
		
		double nOrientation;
				
		nOrientation = Math.atan2(poCollisionPosition.y, 
				                  poCollisionPosition.x);
		if(nOrientation < 0)
			nOrientation = 2*Math.PI+nOrientation; 
		
		return new clsPolarcoordinate(poCollisionPosition.length(), nOrientation); 
	}
}
