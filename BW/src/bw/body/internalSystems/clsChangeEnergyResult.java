/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import java.util.HashMap;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsChangeEnergyResult {
	private float mrTotalPercentage;
	private HashMap<Integer, Float> moFractions;
	
	/**
	 * 
	 */
	public clsChangeEnergyResult() {
		mrTotalPercentage = 0.0f;
		moFractions = new HashMap<Integer, Float>();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prPercentage
	 */
	public void setTotalPercentage(float prPercentage) {
		mrTotalPercentage = checkValue(prPercentage);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public float getTotalPercentage() {
		return mrTotalPercentage;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param pnNutritionType
	 * @param prFraction
	 */
	public void addFraction(int pnNutritionType, float prFraction) {
		addFraction(new Integer(pnNutritionType), new Float(prFraction));
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poNutritionType
	 * @param poFraction
	 */
	public void addFraction(Integer poNutritionType, Float poFraction) {
		moFractions.put(poNutritionType, poFraction);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param pnNutritionType
	 * @return
	 */
	public float getFraction(int pnNutritionType) {
		return getFraction(new Integer(pnNutritionType));
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poNutritionType
	 * @return
	 */
	public float getFraction(Integer poNutritionType) {
		return moFractions.get(poNutritionType).floatValue();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public HashMap<Integer, Float> getFractions() {
		return moFractions;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prValue
	 * @return
	 */
	private static float checkValue(float prValue) {
		if (prValue > 1.0f) {
			prValue = 1.0f; 
		} else if (prValue < 0.0f) {
			prValue = 0.0f;
		}
		
		return prValue;
	}

}
