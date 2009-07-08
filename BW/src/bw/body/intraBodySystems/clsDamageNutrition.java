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
import bw.body.internalSystems.clsInternalSystem;
import bw.body.internalSystems.clsStomachSystem;
import bw.utils.container.clsConfigDouble;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
import bw.utils.enums.partclass.clsPartBrain;
import bw.utils.enums.partclass.clsPartDamageNutrition;
import bw.utils.tools.clsNutritionLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageNutrition implements itfStepUpdateInternalState {
	
	private double mrHealthPenalty;
	private double mrPainThreshold;

	private clsStomachSystem moStomachSystem;
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
	public clsDamageNutrition(clsStomachSystem poStomachSystem, clsHealthSystem poHealthSystem, clsConfigMap poConfig) {
		moStomachSystem = poStomachSystem;
		moHealthSystem = poHealthSystem;
		moFastMessengerSystem = null;
		
		moConfig = getFinalConfig(poConfig);
		applyConfig();
	}
	
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.02.2009, 19:51:48
	 *
	 * @param poInternalSystem
	 */
	public clsDamageNutrition(clsInternalSystem poInternalSystem, clsConfigMap poConfig) {
		moStomachSystem = poInternalSystem.getStomachSystem();
		moHealthSystem = poInternalSystem.getHealthSystem();
		moFastMessengerSystem = poInternalSystem.getFastMessengerSystem();
		
		moFastMessengerSystem.addMapping(new clsPartDamageNutrition(), new clsPartBrain());
		
		moConfig = getFinalConfig(poConfig);
		applyConfig();
	}
	
	private void applyConfig() {
		mrPainThreshold = ((clsConfigDouble)moConfig.get(eConfigEntries.PAINTHRESHOLD)).get();
		mrHealthPenalty = ((clsConfigDouble)moConfig.get(eConfigEntries.HEALTHPENALTY)).get();
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.PAINTHRESHOLD, new clsConfigDouble(0.1f));
		oDefault.add(eConfigEntries.HEALTHPENALTY, new clsConfigDouble(0.5f));
		
		return oDefault;
	}	

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 19.02.2009, 19:51:45
	 *
	 */
	private double nutritionPenaltySum() {
		HashMap<Integer, clsNutritionLevel> oList = moStomachSystem.getList();
		double rPenaltySum = 0.0f;
		
		Iterator<Integer> i = oList.keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = i.next();
			clsNutritionLevel oNL = oList.get(oKey);
			
			double rTemp = oNL.percentageHigh() + oNL.percentageLow();
			
			rPenaltySum += rTemp * rTemp;
		}
		
		return rPenaltySum;
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
			if (moFastMessengerSystem != null) {
				moFastMessengerSystem.addMessage(new clsPartDamageNutrition(), new clsPartBrain(), prPenaltySum);
			}
		}
	}
	
    public void stepUpdateInternalState() {
    	double rPenaltySum = nutritionPenaltySum();
    	
    	hurt(rPenaltySum);
    	pain(rPenaltySum);
    }
}
