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
import bw.utils.container.clsConfigDouble;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
import bw.utils.enums.partclass.clsPartBrain;
import bw.utils.enums.partclass.clsPartDamageBump;
import bw.utils.enums.partclass.clsPartSensorBump;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageBump implements itfStep {
	
	private float mrPainThreshold;
	private float mrHealthPenalty;
	private float mrHurtThreshold;

	private clsHealthSystem moHealthSystem;
	private clsFastMessengerSystem moFastMessengerSystem;
	
    private clsConfigMap moConfig;	
	
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.02.2009, 19:51:48
	 *
	 * @param poInternalSystem
	 */
	public clsDamageBump(clsInternalSystem poInternalSystem, clsConfigMap poConfig) {
		moConfig = getFinalConfig(poConfig);
		applyConfig();
		
		moHealthSystem = poInternalSystem.getHealthSystem();
		moFastMessengerSystem = poInternalSystem.getFastMessengerSystem();
		
		moFastMessengerSystem.addMapping(new clsPartDamageBump(), new clsPartBrain());
	}
	
	private void applyConfig() {
		mrPainThreshold = ((clsConfigDouble)moConfig.get(eConfigEntries.PAINTHRESHOLD)).get();
		mrHealthPenalty = ((clsConfigDouble)moConfig.get(eConfigEntries.HEALTHPENALTY)).get();
		mrHurtThreshold = ((clsConfigDouble)moConfig.get(eConfigEntries.HURTTHRESHOLD)).get();		
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();

		oDefault.add(eConfigEntries.PAINTHRESHOLD, new clsConfigDouble(0.0f));
		oDefault.add(eConfigEntries.HEALTHPENALTY, new clsConfigDouble(1.0f));
		oDefault.add(eConfigEntries.HURTTHRESHOLD, new clsConfigDouble(0.0f));

		return oDefault;
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
		if (prPenaltySum > mrHurtThreshold) {
			float rHealthPenalty = prPenaltySum * mrHealthPenalty;
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
		if (prPenaltySum > mrPainThreshold) {
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
