/**
 * clsStomachToSlowMessenger.java: BW - bw.body.intraBodySystems
 * 
 * @author deutsch
 * 05.10.2009, 11:34:35
 */
package complexbody.intraBodySystems;

import java.util.HashMap;
import java.util.Map;

import properties.clsProperties;

import utils.exceptions.exContentColumnMaxContentExceeded;
import utils.exceptions.exContentColumnMinContentUnderrun;
import utils.exceptions.exSlowMessengerDoesNotExist;
import utils.exceptions.exValueNotWithinRange;

import complexbody.internalSystems.clsDigestiveSystem;
import complexbody.internalSystems.clsSlowMessengerSystem;

import du.enums.eSlowMessenger;
import entities.enums.eNutritions;
import body.itfStepUpdateInternalState;
import body.utils.clsNutritionLevel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.10.2009, 11:34:35
 * 
 */
public class clsStomachToSlowMessenger implements itfStepUpdateInternalState  {
	private clsDigestiveSystem moStomachSystem;
	private clsSlowMessengerSystem moSlowMessengerSystem;
	
	public clsStomachToSlowMessenger(String poPrefix, clsProperties poProp, clsDigestiveSystem poStomachSystem, clsSlowMessengerSystem poSlowMessengerSystem) {
		moStomachSystem = poStomachSystem;		
		moSlowMessengerSystem = poSlowMessengerSystem;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
//		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		// nothing to do
				
		return oProp;
	}

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);

		// nothing to do
		
		//TD 2011/04/29 - set init value for bloodsugar slow messenger.
		try {
			moSlowMessengerSystem.setValue(eSlowMessenger.BLOODSUGAR, calcTargetValue());
		} catch (exSlowMessengerDoesNotExist e) {
			// nothing to do
		} catch (exValueNotWithinRange e) {
			// nothing to do
		} catch (exContentColumnMaxContentExceeded e) {
			// nothing to do
		} catch (exContentColumnMinContentUnderrun e) {
			// nothing to do
		}
	}
	
	private double calcTargetValue() {
		HashMap<eNutritions, clsNutritionLevel> oList = moStomachSystem.getList();
		double rSum = 0.0f;
		double rFactorSum = 0.0f;
		
		for (Map.Entry<eNutritions, clsNutritionLevel> oEntry:oList.entrySet()) {
			double rMetabolismFactor = moStomachSystem.getEnergyMetabolismFactor(oEntry.getKey());
			double rFraction = oEntry.getValue().getContent() / oEntry.getValue().getMaxContent();
			
			rSum += rFraction * rMetabolismFactor;
			rFactorSum += rMetabolismFactor;
		}
		
		double result = 0;
		
		if (rFactorSum > 0) {
			result = rSum / rFactorSum;
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.10.2009, 11:38:12
	 * 
	 * @see bw.body.itfStepUpdateInternalState#stepUpdateInternalState()
	 */
	@Override
	public void stepUpdateInternalState() {

		try {
			moSlowMessengerSystem.setInjectionValue(eSlowMessenger.BLOODSUGAR, calcTargetValue());
		} catch (exSlowMessengerDoesNotExist e) {
			// nothing to do
		} catch (exValueNotWithinRange e) {
			// nothing to do
		}
		
	}

}
