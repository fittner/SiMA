/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.intraBodySystems;

import java.util.HashMap;
import java.util.Iterator;

import properties.clsProperties;

import complexbody.internalSystems.clsDigestiveSystem;
import complexbody.internalSystems.clsFastMessengerSystem;
import complexbody.internalSystems.clsHealthSystem;

import entities.enums.eBodyParts;
import entities.enums.eNutritions;

import body.itfStepUpdateInternalState;
import body.utils.clsNutritionLevel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsStomachDamageNutrition implements itfStepUpdateInternalState {
	public static final String P_PAINTHRESHOLD = "painthreshold";
	public static final String P_PAINFACTOR = "painfactor";
	public static final String P_HEALTHPENALTY = "healthpenalty";
	
	private double mrHealthPenalty;
	private double mrPainThreshold;
	private double mrPainFactor;		

	private clsDigestiveSystem moStomachSystem;
	private clsHealthSystem moHealthSystem;
	private clsFastMessengerSystem moFastMessengerSystem;

	public clsStomachDamageNutrition(String poPrefix, clsProperties poProp, clsHealthSystem poHealthSystem, clsDigestiveSystem poStomachSystem, clsFastMessengerSystem poFastMessengerSystem) {
		moHealthSystem = poHealthSystem;
		moStomachSystem = poStomachSystem;		
		moFastMessengerSystem = poFastMessengerSystem;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_PAINTHRESHOLD, 0.1);
		oProp.setProperty(pre+P_PAINFACTOR, 1);
		oProp.setProperty(pre+P_HEALTHPENALTY, 0.06);
				
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
	private double nutritionPenaltySum() {
		HashMap<eNutritions, clsNutritionLevel> oList = moStomachSystem.getList();
		double rPenaltySum = 0.0f;
		
		Iterator<eNutritions> i = oList.keySet().iterator();
		while (i.hasNext()) {
			eNutritions oKey = i.next();
			clsNutritionLevel oNL = oList.get(oKey);
			
			double rTemp = oNL.percentageHigh() + oNL.percentageLow();
			
			rPenaltySum += rTemp * rTemp;
		}
		
		return rPenaltySum;
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
			if (moFastMessengerSystem != null) {
				moFastMessengerSystem.addMessage(eBodyParts.INTRA_DAMAGE_NUTRITION, eBodyParts.BRAIN, prPenaltySum * mrPainFactor);
			}
		}
	}
	
    @Override
	public void stepUpdateInternalState() {
    	double rPenaltySum = nutritionPenaltySum();
    	
    	hurt(rPenaltySum);
    	pain(rPenaltySum);
    }
}
