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
	private double mrTotalPercentage;
	private HashMap<Integer, Double> moFractions;
	
	/**
	 * 
	 */
	public clsChangeEnergyResult() {
		mrTotalPercentage = 0.0;
		moFractions = new HashMap<Integer, Double>();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prPercentage
	 */
	public void setTotalPercentage(double prPercentage) {
		mrTotalPercentage = checkValue(prPercentage);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public double getTotalPercentage() {
		return mrTotalPercentage;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param pnNutritionType
	 * @param prFraction
	 */
	public void addFraction(int pnNutritionType, double prFraction) {
		addFraction(new Integer(pnNutritionType), new Double(prFraction));
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poNutritionType
	 * @param poFraction
	 */
	public void addFraction(Integer poNutritionType, Double poFraction) {
		moFractions.put(poNutritionType, poFraction);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param pnNutritionType
	 * @return
	 */
	public double getFraction(int pnNutritionType) {
		return getFraction(new Integer(pnNutritionType));
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poNutritionType
	 * @return
	 */
	public double getFraction(Integer poNutritionType) {
		return moFractions.get(poNutritionType).floatValue();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public HashMap<Integer, Double> getFractions() {
		return moFractions;
	}
	
	/**
	 * TODO (deutsch) - insert description
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
