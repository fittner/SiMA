/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.interBodyWorldSystems;

import java.util.HashMap;

import java.util.Iterator;

import bw.body.internalSystems.clsStomachSystem;
import bw.exceptions.exFoodNotFinalized;
import bw.exceptions.exNoSuchNutritionType;
import bw.utils.datatypes.clsMutableFloat;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsConsumeFood {
	private Integer moGarbageNutritionType;
	private clsStomachSystem moStomachSystem;

	/**
	 * @param pnGarbageNutritionType
	 * @param poStomach
	 */
	public clsConsumeFood(int pnGarbageNutritionType, clsStomachSystem poStomach) {
		moGarbageNutritionType = new Integer(pnGarbageNutritionType);
		moStomachSystem = poStomach;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poFood
	 */
	public void digest(clsFood poFood) {
		HashMap<Integer, clsMutableFloat> oNutritions = null;
		
		try {
			oNutritions = poFood.getNutritionAmounts();
		} catch (exFoodNotFinalized e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<Integer> i = oNutritions.keySet().iterator();
		
		while(i.hasNext()) {
			Integer oNutritionType = i.next();
			clsMutableFloat oAmount = oNutritions.get(oNutritionType);
			
			try {
				moStomachSystem.addNutrition(oNutritionType, oAmount.floatValue());
			} catch (exNoSuchNutritionType e) {
				try {
					moStomachSystem.addNutrition(moGarbageNutritionType, oAmount.floatValue());
				} catch (exNoSuchNutritionType e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}

}
