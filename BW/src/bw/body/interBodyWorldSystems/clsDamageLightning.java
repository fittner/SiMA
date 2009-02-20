/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.interBodyWorldSystems;

import bw.body.itfStep;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.internalSystems.clsHealthSystem;
import bw.body.internalSystems.clsInternalSystem;
import bw.utils.enums.partclass.clsPartBrain;
import bw.utils.enums.partclass.clsPartDamageBump;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageLightning implements itfStep {

	private float mrDefaultPainThreshold = 0.0f;
	private float mrDefaultHealthPenalty = 1.0f;
	private float mrDefaultHurtThreshold = 0.0f;

	private clsHealthSystem moHealthSystem;
	private clsFastMessengerSystem moFastMessengerSystem;
	
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.02.2009, 19:51:48
	 *
	 * @param poInternalSystem
	 */
	public clsDamageLightning(clsInternalSystem poInternalSystem) {
		moHealthSystem = poInternalSystem.getHealthSystem();
		moFastMessengerSystem = poInternalSystem.getFastMessengerSystem();
		
		moFastMessengerSystem.addMapping(new clsPartDamageBump(), new clsPartBrain());
	}	
    /**
     * TODO (deutsch) - insert description
     *
     */
    public void step() {
    	
    }
}
