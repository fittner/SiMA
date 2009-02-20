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
import bw.utils.enums.partclass.clsPartDamageNutrition;
import bw.utils.enums.partclass.clsPartSensorBump;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageBump implements itfStep {
	
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
	public clsDamageBump(clsInternalSystem poInternalSystem) {
		moHealthSystem = poInternalSystem.getHealthSystem();
		moFastMessengerSystem = poInternalSystem.getFastMessengerSystem();
		
		moFastMessengerSystem.addMapping(new clsPartDamageBump(), new clsPartBrain());
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 20.02.2009, 12:00:02
	 *
	 * @param prPenaltySum
	 */
	private void hurt(float prPenaltySum) {
		if (prPenaltySum > mrDefaultHurtThreshold) {
			float rHealthPenalty = prPenaltySum * mrDefaultHealthPenalty;
			moHealthSystem.hurt(rHealthPenalty);
		}
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 20.02.2009, 12:00:00
	 *
	 * @param prPenaltySum
	 */
	private void pain(clsPartSensorBump poSource, float prPenaltySum) {
		if (prPenaltySum > mrDefaultPainThreshold) {
			moFastMessengerSystem.addMessage((clsPartSensorBump)poSource.clone(), new clsPartBrain(), prPenaltySum);
		}
	}
	
	public void bumped(clsPartSensorBump poSource, float prForce) {
		hurt(prForce);
		pain(poSource, prForce);
	}
	
	
    /**
     * TODO (deutsch) - insert description
     *
     */
    public void step() {
    	//do nothing
    }
}
