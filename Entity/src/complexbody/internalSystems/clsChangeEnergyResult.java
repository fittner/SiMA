/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.internalSystems;

import java.util.HashMap;

import entities.enums.eNutritions;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsChangeEnergyResult {
	private double mrTotalPercentage;
	private HashMap<eNutritions, Double> moFractions;
	
	/**
	 * 
	 */
	public clsChangeEnergyResult() {
		mrTotalPercentage = 0.0;
		moFractions = new HashMap<eNutritions, Double>();
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param prPercentage
	 */
	public void setTotalPercentage(double prPercentage) {
		mrTotalPercentage = checkValue(prPercentage);
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public double getTotalPercentage() {
		return mrTotalPercentage;
	}

	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poNutritionType
	 * @param poFraction
	 */
	public void addFraction(eNutritions poNutritionType, Double poFraction) {
		moFractions.put(poNutritionType, poFraction);
	}

	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poNutritionType
	 * @return
	 */
	public double getFraction(eNutritions poNutritionType) {
		return moFractions.get(poNutritionType).floatValue();
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public HashMap<eNutritions, Double> getFractions() {
		return moFractions;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param prValue
	 * @return
	 */
	private static double checkValue(double prValue) {
		if (prValue > 1.0f) {
			prValue = 1.0f; 
		} else if (prValue < 0.0f) {
			prValue = 0.0f;
		}
		
		return prValue;
	}

}
