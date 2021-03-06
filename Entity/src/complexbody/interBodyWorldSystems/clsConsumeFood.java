/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.interBodyWorldSystems;

import java.util.HashMap;

import java.util.Iterator;

import properties.clsProperties;

import utils.exceptions.exFoodNotFinalized;
import utils.exceptions.exNoSuchNutritionType;

import complexbody.internalSystems.clsDigestiveSystem;

import datatypes.clsMutableDouble;
import entities.enums.eNutritions;

import body.utils.clsFood;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsConsumeFood {
	public static final String P_GARBAGENUTRITIONTYPE = "garbagenutritiontype";
	
	private eNutritions mnGarbageNutritionType;
	private clsDigestiveSystem moStomachSystem; // reference to existing stomach


	public clsConsumeFood(String poPrefix, clsProperties poProp, clsDigestiveSystem poStomach) {
		moStomachSystem = poStomach;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_GARBAGENUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

		String temp = poProp.getProperty(pre+P_GARBAGENUTRITIONTYPE);
		mnGarbageNutritionType = eNutritions.valueOf(temp);
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poFood
	 */
	public void digest(clsFood poFood) {
		HashMap<eNutritions, clsMutableDouble> oNutritions = null;
		
		try {
			oNutritions = poFood.getNutritionWeights();
		} catch (exFoodNotFinalized e) {
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
					e1.printStackTrace();
				}
			}
		}
		
	}

}
