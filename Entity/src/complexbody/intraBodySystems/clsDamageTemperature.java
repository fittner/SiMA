/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.intraBodySystems;

import complexbody.internalSystems.clsFastMessengerSystem;
import complexbody.internalSystems.clsHealthSystem;
import complexbody.internalSystems.clsTemperatureSystem;

import config.clsProperties;
import entities.enums.eBodyParts;
import body.itfStepUpdateInternalState;

/**
 * DOCUMENT (deutsch) - insert description 
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
	
	public clsDamageTemperature(String poPrefix, clsProperties poProp, clsHealthSystem poHealthSystem, clsTemperatureSystem poTemperatureSystem, clsFastMessengerSystem poFastMessengerSystem) {
		moHealthSystem = poHealthSystem;
		moTemperatureSystem = poTemperatureSystem;		
		moFastMessengerSystem = poFastMessengerSystem;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_PAINTHRESHOLD, 0.1);
		oProp.setProperty(pre+P_PAINFACTOR, 1);
		oProp.setProperty(pre+P_HEALTHPENALTY, 0.5);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		mrPainThreshold = poProp.getPropertyDouble(pre+P_PAINTHRESHOLD);
		mrPainFactor = poProp.getPropertyDouble(pre+P_PAINFACTOR);
		mrHealthPenalty = poProp.getPropertyDouble(pre+P_HEALTHPENALTY);
	}		
	
	/**
	 * DOCUMENT (deutsch) - insert description
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
	 * DOCUMENT (deutsch) - insert description
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
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 20.02.2009, 12:00:00
	 *
	 * @param prPenaltySum
	 */
	private void pain(double prPenaltySum) {
		if (prPenaltySum > mrPainThreshold) {
			moFastMessengerSystem.addMessage(eBodyParts.INTRA_DAMAGE_TEMPERATURE, eBodyParts.BRAIN, prPenaltySum * mrPainFactor);
		}
	}
	
    @Override
	public void stepUpdateInternalState() {
    	double rPenaltySum = temperaturePenalty();
    	
    	hurt(rPenaltySum);
    	pain(rPenaltySum);
    }
}
