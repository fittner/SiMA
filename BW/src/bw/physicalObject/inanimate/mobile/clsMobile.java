/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.inanimate.mobile;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.clsEntity;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsMobile extends clsEntity {

	/**
	 * @return the moMobile
	 */
	public clsMobileObject2D getMobile() {
		return (clsMobileObject2D)moPhysicalObject2D;
	}

	
	/**
	 * 
	 */
	public clsMobile() {
		super();
		
		moPhysicalObject2D = new clsMobileObject2D(this);
		//TODO: register irgendwie
	}

	public void setPosition(){
	
		
	}
	
	
}
