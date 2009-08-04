/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import java.util.HashMap;
import java.util.Iterator;

import bw.body.itfStepUpdateInternalState;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.internalSystems.clsHealthSystem;
import bw.body.internalSystems.clsStomachSystem;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyParts;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsNutritionLevel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageNutrition implements itfStepUpdateInternalState {
	public static final String P_PAINTHRESHOLD = "painthreshold";
	public static final String P_PAINFACTOR = "painfactor";
	public static final String P_HEALTHPENALTY = "healthpenalty";
	
	private double mrHealthPenalty;
	private double mrPainThreshold;
	private double mrPainFactor;		

	private clsStomachSystem moStomachSystem;
	private clsHealthSystem moHealthSystem;
	private clsFastMessengerSystem moFastMessengerSystem;

	public clsDamageNutrition(String poPrefix, clsBWProperties poProp, clsHealthSystem poHealthSystem, clsStomachSystem poStomachSystem, clsFastMessengerSystem poFastMessengerSystem) {
		moHealthSystem = poHealthSystem;
		moStomachSystem = poStomachSystem;		
		moFastMessengerSystem = poFastMessengerSystem;
		moFastMessengerSystem.addMapping(eBodyParts.INTRA_DAMAGE_NUTRITION, eBodyParts.BRAIN);
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_PAINTHRESHOLD, 0.1);
		oProp.setProperty(pre+P_PAINFACTOR, 1);
		oProp.setProperty(pre+P_HEALTHPENALTY, 0.06);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
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
	
    public void stepUpdateInternalState() {
    	double rPenaltySum = nutritionPenaltySum();
    	
    	hurt(rPenaltySum);
    	pain(rPenaltySum);
    }
}
