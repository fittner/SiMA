/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package ARSsim.physics2D.physicalObject;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;

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
	public clsCollidingObject(PhysicalObject2D poCollider, Double2D prColPoint) {
		this.moCollider = poCollider;
		this.mrColPoint = prColPoint;
	}
	public PhysicalObject2D moCollider;
	public Double2D mrColPoint;
	
}
