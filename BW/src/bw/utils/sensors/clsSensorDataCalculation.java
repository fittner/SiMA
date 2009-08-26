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
	public boolean checkIfObjectInView(double pnColPointOrientation, double pnEntityOrientation, double pnAreaOfViewRadians){
		
		//(horvath) - fixed
		double nColPointOrientation = this.normalizeRadian(pnColPointOrientation);
		double nEntityOrientation = this.normalizeRadian(pnEntityOrientation);
		
		if(Math.abs(nEntityOrientation - nColPointOrientation) <= pnAreaOfViewRadians/2)
		{
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
	public double normalizeRadian(double pnEntityOrientation){
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
	
	public clsPolarcoordinate getRelativeCollisionPosition(Double2D poCollisionPosition){   
		
		double nOrientation;
				
		nOrientation = Math.atan2(poCollisionPosition.y, 
				                  poCollisionPosition.x);
		
		if(nOrientation < 0)
			nOrientation = 2*Math.PI+nOrientation; 
		
		return new clsPolarcoordinate(poCollisionPosition.length(), 
				                                	nOrientation); 
	}
}
