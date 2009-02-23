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

import bw.exceptions.exFoodAlreadyNormalized;
import bw.exceptions.exFoodAmountBelowZero;
import bw.exceptions.exFoodNotFinalized;
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
	private boolean mnFinalized;
	
	/**
	 * 
	 */
	public clsFood() {
		moComposition = new HashMap<Integer, clsMutableFloat>();
		mrAmount = 0.0f;
		mnFinalized = false;
	}
	
	/**
	 * set the weight of the food piece (0.0 <= x < FLOATMAX).
	 *
	 * @param prAmount
	 * @throws bw.exceptions.exFoodAmountBelowZero 
	 */
	public void setAmount(float prAmount) throws bw.exceptions.exFoodAmountBelowZero {
		mrAmount = prAmount;
		
		if (mrAmount < 0.0f) {
			mrAmount = 0.0f;
			
			throw new bw.exceptions.exFoodAmountBelowZero();
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
	 * @throws exFoodNotFinalized 
	 */
	public float getNutritionAmount(int pnNutritionId) throws exFoodNotFinalized {
		return getNutritionAmount(new Integer(pnNutritionId));
	}
	
	/**
	 * Returns the total weight of a certain nutrition within this piece of food.
	 * 
	 * @param poNutritionId
	 * @return mrAmount * mrNutritionFraction
	 * @throws exFoodNotFinalized 
	 */
	public float getNutritionAmount(Integer poNutritionId) throws bw.exceptions.exFoodNotFinalized {
		if (!mnFinalized) {
			throw new bw.exceptions.exFoodNotFinalized();
		}
		
		float rValue = moComposition.get(poNutritionId).floatValue();
		float rTemp = mrAmount * rValue;
		return rTemp;
		
//		return mrAmount * moComposition.get(poNutritionId).floatValue();
	}
	
	/**
	 * Similar to getNutritionAmount - only this time, the function returns a HashMap containting all
	 * total weights of all nutritions within this type of food.
	 *
	 * @return the HashMap<Integer, clsMutableFloat>
	 * @throws bw.exceptions.exFoodNotFinalized
	 */
	public HashMap<Integer, clsMutableFloat> getNutritionAmounts() throws bw.exceptions.exFoodNotFinalized {
		if (!mnFinalized) {
			throw new bw.exceptions.exFoodNotFinalized();
		}
		
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
	 * @throws exFoodNotFinalized 
	 * @throws exFoodAlreadyNormalized 
	 */
	public void addFood(clsFood poFood) throws exFoodNotFinalized, exFoodAlreadyNormalized {
		float rAmount = this.getAmount() + poFood.getAmount();
		
		HashMap<Integer, clsMutableFloat> oSetA = poFood.getNutritionAmounts();
		HashMap<Integer, clsMutableFloat> oSetB = this.getNutritionAmounts();
		
		//look for each entry of setA if there is a matching entry in setB. if yes, add value of setB to setA
		Iterator<Integer> i = oSetA.keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = i.next();
			clsMutableFloat oValue = oSetA.get(oKey);
			
			if (oSetB.containsKey(oKey)) {
				oValue.add( oSetB.get(oKey) );
			}
		}
		
		//add all entries from setB which have not corresponding entry in setA to setA
		Iterator<Integer> j = oSetB.keySet().iterator();
		while (j.hasNext()) {
			Integer oKey = j.next();
			
			if (!oSetA.containsKey(oKey)) {
				clsMutableFloat oValue = oSetB.get(oKey);
				oSetA.put(oKey, oValue);
			}
		}
		
		//reset this food
		this.mnFinalized = false;
		this.mrAmount = rAmount;
		this.moComposition = new HashMap<Integer, clsMutableFloat>(oSetA);
		this.finalize();
	}
	
	/**
	 * Adds a new nutrition type with its fraction to the object. Already existing nutrition type entries
	 * will be overwritten by the new fraction.
	 *
	 * @param pnId
	 * @param prFraction
	 */
	public void addNutritionFraction(int pnNurtritionId, float prFraction) throws bw.exceptions.exFoodAlreadyNormalized {
		addNutritionFraction(new Integer(pnNurtritionId), new clsMutableFloat(prFraction));
	}
	
	/**
	 * Adds a new nutrition type with its fraction to the object. Already existing nutrition type entries
	 * will be overwritten by the new fraction.
	 * 
	 * @param poId
	 * @param poFraction
	 */
	public void addNutritionFraction(Integer poNutritionId, clsMutableFloat poFraction) throws bw.exceptions.exFoodAlreadyNormalized {
		if (mnFinalized) {
			throw new bw.exceptions.exFoodAlreadyNormalized();
		}
		
		if (poFraction.floatValue() < 0.0f) {
			poFraction.set(0.0f);
		}
		
		moComposition.put(poNutritionId, poFraction);
	}
	
	/**
	 * Normalize all fractions to a total sum of 1.0f
	 *
	 */
	private void normalizeFractions() throws bw.exceptions.exFoodAlreadyNormalized {
		if (mnFinalized) {
			throw new bw.exceptions.exFoodAlreadyNormalized();
		}
		
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
	
	public void finalize() throws bw.exceptions.exFoodAlreadyNormalized {
		normalizeFractions();
		mnFinalized = true;
	}

}
