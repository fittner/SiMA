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

import bw.body.itfStep;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.internalSystems.clsHealthSystem;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.internalSystems.clsStomachSystem;
import bw.utils.enums.partclass.clsPartBrain;
import bw.utils.enums.partclass.clsPartDamageNutrition;
import bw.utils.tools.clsNutritionLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageNutrition implements itfStep {
	
	private float mrDefaultHealthPenalty = 0.1f;
	private float mrPainThreshold = 0.5f;

	private clsStomachSystem moStomachSystem;
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
	public clsDamageNutrition(clsInternalSystem poInternalSystem) {
		moStomachSystem = poInternalSystem.getStomachSystem();
		moHealthSystem = poInternalSystem.getHealthSystem();
		moFastMessengerSystem = poInternalSystem.getFastMessengerSystem();
		
		moFastMessengerSystem.addMapping(new clsPartDamageNutrition(), new clsPartBrain());
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 19.02.2009, 19:51:45
	 *
	 */
	private float nutritionPenaltySum() {
		HashMap<Integer, clsNutritionLevel> oList = moStomachSystem.getList();
		float rPenaltySum = 0.0f;
		
		Iterator<Integer> i = oList.keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = i.next();
			clsNutritionLevel oNL = oList.get(oKey);
			
			float rTemp = oNL.percentageHigh() + oNL.percentageLow();
			
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
			moFastMessengerSystem.addMessage(new clsPartDamageNutrition(), new clsPartBrain(), prPenaltySum);
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
    	float rPenaltySum = nutritionPenaltySum();
    	
    	hurt(rPenaltySum);
    	pain(rPenaltySum);
    }
}
