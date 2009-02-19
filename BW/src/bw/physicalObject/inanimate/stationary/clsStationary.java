/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.inanimate.stationary;

import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bw.clsEntity;



/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsStationary extends clsEntity {

	/**
	 * @return the moMobile
	 */
	public clsStationaryObject2D getMobile() {
		return (clsStationaryObject2D)moPhysicalObject2D;
	}

	
	/**
	 * 
	 */
	public clsStationary() {
		super();
		
		moPhysicalObject2D = new clsStationaryObject2D(this);
		//TODO: register irgendwie
	}
}
