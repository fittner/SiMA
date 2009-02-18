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
import bw.physicalObject.inanimate.clsInanimate;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsMobile extends clsEntity {

	private clsMobileObject2D moMobile;

	/**
	 * @return the moMobile
	 */
	public clsMobileObject2D getMobile() {
		return moMobile;
	}

	
	/**
	 * 
	 */
	public clsMobile() {
		super();
		
		moMobile = new clsMobileObject2D(this);
		//TODO: register irgendwie
	}

	
	
}
