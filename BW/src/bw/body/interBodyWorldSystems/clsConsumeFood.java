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
import bw.utils.config.clsBWProperties;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsConsumeFood {
	public static final String P_GARBAGENUTRITIONTYPE = "garbagenutritiontype";
	
	private eNutritions mnGarbageNutritionType;
	private clsStomachSystem moStomachSystem; // reference to existing stomach


	public clsConsumeFood(String poPrefix, clsBWProperties poProp, clsStomachSystem poStomach) {
		moStomachSystem = poStomach;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_GARBAGENUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);

		String temp = poProp.getProperty(pre+P_GARBAGENUTRITIONTYPE);
		mnGarbageNutritionType = eNutritions.valueOf(temp);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poFood
	 */
	public void digest(clsFood poFood) {
		HashMap<eNutritions, clsMutableDouble> oNutritions = null;
		
		try {
			oNutritions = poFood.getNutritionWeights();
		} catch (exFoodNotFinalized e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<eNutritions> i = oNutritions.keySet().iterator();
		
		while(i.hasNext()) {
			eNutritions oNutritionType = i.next();
			clsMutableDouble oAmount = oNutritions.get(oNutritionType);
			
			try {
				moStomachSystem.addNutrition(oNutritionType, oAmount.doubleValue());
			} catch (exNoSuchNutritionType e) {
				try {
					moStomachSystem.addNutrition(mnGarbageNutritionType, oAmount.doubleValue());
				} catch (exNoSuchNutritionType e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}

}
