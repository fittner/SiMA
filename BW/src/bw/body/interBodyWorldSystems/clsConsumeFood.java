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
import bw.utils.container.clsConfigEnum;
import bw.utils.container.clsConfigMap;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.enums.eConfigEntries;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsConsumeFood {
	private eNutritions moGarbageNutritionType;
	private clsStomachSystem moStomachSystem;

    private clsConfigMap moConfig;	
	
    public clsConsumeFood(clsStomachSystem poStomach, clsConfigMap poConfig) {
    	moConfig = getFinalConfig(poConfig);
    	applyConfig();
    	
		moStomachSystem = poStomach;
	}
	
	private void applyConfig() {
		
		moGarbageNutritionType = eNutritions.UNDIGESTABLE;

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.GARBAGENUTRITIONTYPE, new clsConfigEnum<eNutritions>(eNutritions.UNDIGESTABLE));
		
		return oDefault;
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
					moStomachSystem.addNutrition(moGarbageNutritionType, oAmount.doubleValue());
				} catch (exNoSuchNutritionType e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}

}
