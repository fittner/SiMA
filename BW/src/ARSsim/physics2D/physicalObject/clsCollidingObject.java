/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package ARSsim.physics2D.physicalObject;

import bfg.utils.enums.eSide;
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

	/**
	 * @param moCollider
	 * @param mrColPoint
	 */
	public clsCollidingObject(PhysicalObject2D poCollider, clsPolarcoordinate prColPoint, eSide peColPos) {
		this.moCollider = poCollider;
		this.mrColPoint = prColPoint;
		this.meColPos = peColPos; 
	}
	public PhysicalObject2D moCollider;
	public clsPolarcoordinate mrColPoint;
	public eSide meColPos; 	
}
