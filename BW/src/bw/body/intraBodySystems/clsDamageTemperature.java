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
import bw.body.internalSystems.clsTemperatureSystem;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.partclass.clsPartBrain;
import bw.utils.enums.partclass.clsPartDamageNutrition;
import bw.utils.enums.partclass.clsPartDamageTemperature;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageTemperature implements itfStepUpdateInternalState {
	public static final String P_PAINTHRESHOLD = "painthreshold";
	public static final String P_PAINFACTOR = "painfactor";
	public static final String P_HEALTHPENALTY = "healthpenalty";
	
	private double mrHealthPenalty;
	private double mrPainThreshold;
	private double mrPainFactor;
	
	private clsTemperatureSystem moTemperatureSystem;
	private clsHealthSystem moHealthSystem;
	private clsFastMessengerSystem moFastMessengerSystem;
	
	public clsDamageTemperature(String poPrefix, clsBWProperties poProp, clsHealthSystem poHealthSystem, clsTemperatureSystem poTemperatureSystem, clsFastMessengerSystem poFastMessengerSystem) {
		moHealthSystem = poHealthSystem;
		moTemperatureSystem = poTemperatureSystem;		
		moFastMessengerSystem = poFastMessengerSystem;
		moFastMessengerSystem.addMapping(new clsPartDamageNutrition(), new clsPartBrain());
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_PAINTHRESHOLD, 0.1);
		oProp.setProperty(pre+P_PAINFACTOR, 1);
		oProp.setProperty(pre+P_HEALTHPENALTY, 0.5);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		mrPainThreshold = poProp.getPropertyDouble(pre+P_PAINTHRESHOLD);
		mrPainFactor = poProp.getPropertyDouble(pre+P_PAINFACTOR);
		mrHealthPenalty = poProp.getPropertyDouble(pre+P_HEALTHPENALTY);
	}		
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 19.02.2009, 19:51:45
	 *
	 */
	private double temperaturePenalty() {
		double rPenalty = 0.0f;
		
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
	private void hurt(double prPenaltySum) {
		double rHealthPenalty = prPenaltySum * mrHealthPenalty;
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
	private void pain(double prPenaltySum) {
		if (prPenaltySum > mrPainThreshold) {
			moFastMessengerSystem.addMessage(new clsPartDamageTemperature(), new clsPartBrain(), prPenaltySum * mrPainFactor);
		}
	}
	
    public void stepUpdateInternalState() {
    	double rPenaltySum = temperaturePenalty();
    	
    	hurt(rPenaltySum);
    	pain(rPenaltySum);
    }
}
