/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;



/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsStationary extends clsEntity {


	/**
	 * 
	 */
	public clsStationary() {
		super();
		
		moPhysicalObject2D = new clsStationaryObject2D(this);
		//TODO: register irgendwie
	}
	
	/**
	 * @return the moMobile
	 */
	public clsStationaryObject2D getStationaryObject2D() {
		return (clsStationaryObject2D)moPhysicalObject2D;
	}
	
	
	public Double2D getPosition() {
		return getStationaryObject2D().getPosition();
	}	
}
