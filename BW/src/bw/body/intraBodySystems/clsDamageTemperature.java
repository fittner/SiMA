/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import bw.body.itfStep;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.internalSystems.clsHealthSystem;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.internalSystems.clsTemperatureSystem;
import bw.utils.enums.partclass.clsPartBrain;
import bw.utils.enums.partclass.clsPartDamageTemperature;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageTemperature implements itfStep {

	private float mrDefaultHealthPenalty = 0.1f;
	private float mrPainThreshold = 0.5f;
	
	private clsTemperatureSystem moTemperatureSystem;
	private clsHealthSystem moHealthSystem;
	private clsFastMessengerSystem moFastMessengerSystem;
	
	public clsDamageTemperature(clsInternalSystem poInternalSystem) {
		moTemperatureSystem = poInternalSystem.getTemperatureSystem();
		moHealthSystem = poInternalSystem.getHealthSystem();
		moFastMessengerSystem = poInternalSystem.getFastMessengerSystem();
		
		moFastMessengerSystem.addMapping(new clsPartDamageTemperature(), new clsPartBrain());
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
		float rHealthPenalty = prPenaltySum * mrDefaultHealthPenalty;
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
	
    /* (non-Javadoc)
     *
     * @author deutsch
     * 19.02.2009, 19:51:04
     * 
     * @see bw.body.itfStep#step()
     */
    public void step() {
    	float rPenaltySum = temperaturePenalty();
    	
    	hurt(rPenaltySum);
    	pain(rPenaltySum);
    }
}
