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
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.07.2009, 16:26:54
 * 
 */
public class clsSensorCalculation {

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
	public boolean checkIfObjectInView(double pnColPointOrientation, double pnEntityOrientation, 
								       double pnAreaOfViewRadians){
		
		double nEntityOrientation = pnEntityOrientation;
		double nMinBorder; 
		double nMaxBorder; 
		
		nEntityOrientation = this.normalizeToRadian(nEntityOrientation);
		nMinBorder = nEntityOrientation -  pnAreaOfViewRadians/2; 
		nMaxBorder = nEntityOrientation +  pnAreaOfViewRadians/2; 
		
		if(pnAreaOfViewRadians == 2*Math.PI) // => nMinBorder == nMaxBorder, 360-degrees view    
			return true; 
		if(nMaxBorder>2*Math.PI)
			nMaxBorder-=2*Math.PI; 
		if(nMinBorder<0)
			nMinBorder+=2*Math.PI; 
		
		if(nMaxBorder > nMinBorder && 
				pnColPointOrientation <= nMaxBorder &&
				pnColPointOrientation >= nMinBorder)
		{
			return true;  
		}
		else if (nMaxBorder < nMinBorder && 
				(pnColPointOrientation <= nMaxBorder || 
				 pnColPointOrientation >= nMinBorder))
		{
			
			return true; 
		}
		return false; 
	}
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.02.2009, 16:22:39
	 *
	 * @param pnOrientation
	 * @return
	 */
	public double normalizeToRadian(double pnEntityOrientation){
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
