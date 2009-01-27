/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

import java.util.HashMap;
import java.util.Iterator;

import bw.utils.datatypes.clsMutableFloat;

/**
 * Food describes a piece of junk that can be transferred from one agent to the other (e.g. plant to bubble). It is
 * a composition of nutritionfractions (which sum up to 1.0) and the amount (or weight) of the food piece.
 * 
 * FIXME (deutsch) is not working - debugging needed!
 * 
 * @author deutsch
 * 
 */
public class clsFood {
	private HashMap<Integer, clsMutableFloat> moComposition;
	private float mrAmount;
	private boolean mnDirtyFlag;
	
	/**
	 * 
	 */
	public clsFood() {
		moComposition = new HashMap<Integer, clsMutableFloat>();
		mrAmount = 0.0f;
		mnDirtyFlag = false;
	}
	
	/**
	 * set the weight of the food piece (0.0 <= x < FLOATMAX).
	 *
	 * @param prAmount
	 */
	public void setAmount(float prAmount) {
		mrAmount = prAmount;
		
		if (mrAmount < 0.0f) {
			mrAmount = 0.0f;
		}
	}
	
	/**
	 * returns the weight of the food piece
	 *
	 * @return the mrAmount
	 */
	public float getAmount() {
		return mrAmount;
	}
	
	/**
	 * Returns the total weight of a certain nutrition within this piece of food.
	 *
	 * @param pnNutritionId
	 * @return mrAmount * mrNutritionFraction
	 */
	public float getNutritionAmount(int pnNutritionId) {
		return getNutritionAmount(new Integer(pnNutritionId));
	}
	
	/**
	 * Returns the total weight of a certain nutrition within this piece of food.
	 * 
	 * @param poNutritionId
	 * @return mrAmount * mrNutritionFraction
	 */
	public float getNutritionAmount(Integer poNutritionId) {
		normalizeFractions();
		return mrAmount * moComposition.get(poNutritionId).floatValue();
	}
	
	/**
	 * Similar to getNutritionAmount - only this time, the function returns a HashMap containting all
	 * total weights of all nutritions within this type of food.
	 *
	 * @return the HashMap<Integer, clsMutableFloat>
	 */
	public HashMap<Integer, clsMutableFloat> getNutritonAmounts() {
		normalizeFractions();
		HashMap<Integer, clsMutableFloat> oComposition = new HashMap<Integer, clsMutableFloat>();
		
		Iterator<Integer> i =  moComposition.keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = (Integer) i.next();
			clsMutableFloat oFraction = moComposition.get(oKey);
			oComposition.put(new Integer(oKey), new clsMutableFloat(oFraction.floatValue() * mrAmount));
		}		
		
		return oComposition;
	}
	
	/**
	 * This functions adds another piece of food to the current one. In fact the results of getNutritonAmounts()
	 * of both pieces are added and afterwards normalized. The weights are simply added.
	 *
	 * @param poFood
	 */
	public void addFood(clsFood poFood) {
		HashMap<Integer, clsMutableFloat> moOther = poFood.getNutritonAmounts();
		HashMap<Integer, clsMutableFloat> moThis = this.getNutritonAmounts();
		
		Iterator<Integer> i =  moOther.keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = (Integer) i.next();
			clsMutableFloat oOtherFraction = moOther.get(oKey);

			clsMutableFloat oFraction = null;
			
			if (moThis.containsKey(oKey)) {
				oFraction = moThis.get(oKey);
			} else {
				oFraction = new clsMutableFloat(0.0f);
			}
			
			oFraction.add(oOtherFraction.floatValue());
			
			moThis.put(oKey, oFraction);
		}
		
		moComposition = moThis;
	
		normalizeFractions();
		
		mrAmount += poFood.mrAmount;		
	}
	
	/**
	 * Adds a new nutrition type with its fraction to the object. Already existing nutrition type entries
	 * will be overwritten by the new fraction.
	 *
	 * @param pnId
	 * @param prFraction
	 */
	public void addNutritionFraction(int pnNurtritionId, float prFraction) {
		addNutritionFraction(new Integer(pnNurtritionId), new clsMutableFloat(prFraction));
	}
	
	/**
	 * Adds a new nutrition type with its fraction to the object. Already existing nutrition type entries
	 * will be overwritten by the new fraction.
	 * 
	 * @param poId
	 * @param poFraction
	 */
	public void addNutritionFraction(Integer poNutritionId, clsMutableFloat poFraction) {
		if (poFraction.floatValue() < 0.0f) {
			poFraction.set(0.0f);
		}
		
		mnDirtyFlag = true;
		
		moComposition.put(poNutritionId, poFraction);
		
		normalizeFractions();
	}
	
	/**
	 * Normalize all fractions to a total sum of 1.0f
	 *
	 */
	private void normalizeFractions() {
		if (mnDirtyFlag) {			
			float rFractionSum = 0.0f;
			Iterator<Integer> i =  moComposition.keySet().iterator();
			
			while (i.hasNext()) {
				rFractionSum += moComposition.get(i.next()).floatValue();
			}
			
			float rInvFSum = 1.0f/rFractionSum;
			
			i = moComposition.keySet().iterator();
			while (i.hasNext()) {
				clsMutableFloat oFraction = moComposition.get(i.next());
				oFraction.mult(rInvFSum);
			}
		}
		
		mnDirtyFlag = false;
	}

}
