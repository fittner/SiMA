/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import bw.body.itfStepUpdateInternalState;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.internalSystems.clsHealthSystem;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.internalSystems.clsTemperatureSystem;
import bw.utils.container.clsConfigFloat;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
import bw.utils.enums.partclass.clsPartBrain;
import bw.utils.enums.partclass.clsPartDamageTemperature;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageTemperature implements itfStepUpdateInternalState {

	private float mrHealthPenalty;
	private float mrPainThreshold;
	
	private clsTemperatureSystem moTemperatureSystem;
	private clsHealthSystem moHealthSystem;
	private clsFastMessengerSystem moFastMessengerSystem;
	
	private clsConfigMap moConfig;
	
	public clsDamageTemperature(clsInternalSystem poInternalSystem, clsConfigMap poConfig) {
		moTemperatureSystem = poInternalSystem.getTemperatureSystem();
		moHealthSystem = poInternalSystem.getHealthSystem();
		moFastMessengerSystem = poInternalSystem.getFastMessengerSystem();
		
		moFastMessengerSystem.addMapping(new clsPartDamageTemperature(), new clsPartBrain());
		
		moConfig = getFinalConfig(poConfig);
		applyConfig();
	}
	
	private void applyConfig() {
		
		mrPainThreshold = ((clsConfigFloat)moConfig.get(eConfigEntries.PAINTHRESHOLD)).get();
		mrHealthPenalty = ((clsConfigFloat)moConfig.get(eConfigEntries.HEALTHPENALTY)).get();
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.PAINTHRESHOLD, new clsConfigFloat(0.1f));
		oDefault.add(eConfigEntries.HEALTHPENALTY, new clsConfigFloat(0.5f));
		
		return oDefault;
	}	
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 19.02.2009, 19:51:45
	 *
	 */
	private float temperaturePenalty() {
		float rPenalty = 0.0f;
		
		rPenalty = moTemperatureSystem.getPercentageHigh() + moTemperatureSystem.getPercentageLow();
		rPenalty = rPenalty * rPenalty;
		
		return rPenalty;
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
		float rHealthPenalty = prPenaltySum * mrHealthPenalty;
		moHealthSystem.hurt(rHealthPenalty);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 20.02.2009, 12:00:00
	 *
	 * @param prPenaltySum
	 */
	private void pain(float prPenaltySum) {
		if (prPenaltySum > mrPainThreshold) {
			moFastMessengerSystem.addMessage(new clsPartDamageTemperature(), new clsPartBrain(), prPenaltySum);
		}
	}
	
    public void stepUpdateInternalState() {
    	float rPenaltySum = temperaturePenalty();
    	
    	hurt(rPenaltySum);
    	pain(rPenaltySum);
    }
}
