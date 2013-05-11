/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package ARSsim.physics2D.physicalObject;

import bfg.utils.enums.eSide;
import bw.entities.clsEntity;
import ARSsim.physics2D.util.clsPolarcoordinate;
import sim.physics2D.physicalObject.PhysicalObject2D;

/**
 * Used in the handleCollision method of clsMobileObject2D
 * to provide a list of colliding objects for eg the AccelerationSensor 
 * 
 * @author langr
 * 
 */
public class clsCollidingObject {
	public PhysicalObject2D moCollider;
	public clsPolarcoordinate mrColPoint;
	public eSide meColPos; 	
	public clsEntity moEntity;
	
	/**
	 * @param moCollider
	 * @param mrColPoint
	 * @param meColPos  stores the position of the colliding object relative to the object
	 */
	public clsCollidingObject(PhysicalObject2D poCollider, clsPolarcoordinate prColPoint, eSide peColPos) {
		moCollider = poCollider;
		mrColPoint = prColPoint;
		meColPos = peColPos; 
		moEntity = getEntity(moCollider);
	}
	
	private clsEntity getEntity(PhysicalObject2D poObject) {
		clsEntity oResult = null;
		
		if (poObject instanceof clsMobileObject2D) {
			oResult = ((clsMobileObject2D) poObject).getEntity();
		} else if (poObject instanceof clsStationaryObject2D) {
			oResult = ((clsStationaryObject2D) poObject).getEntity();
		}	
		
		return oResult;
	}	

}
