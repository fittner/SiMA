/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package physics2D.physicalObject;

import physical2d.physicalObject.datatypes.eSide;
import interfaces.itfEntity;
import sim.physics2D.physicalObject.PhysicalObject2D;
import tools.clsPolarcoordinate;

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
	public itfEntity moEntity;
	
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
	
	private itfEntity getEntity(PhysicalObject2D poObject) {
		itfEntity oResult = null;
		
		if (poObject instanceof clsMobileObject2D) {
			oResult = ((clsMobileObject2D) poObject).getEntity();
		} else if (poObject instanceof clsStationaryObject2D) {
			oResult = ((clsStationaryObject2D) poObject).getEntity();
		}	
		
		return oResult;
	}	

}
