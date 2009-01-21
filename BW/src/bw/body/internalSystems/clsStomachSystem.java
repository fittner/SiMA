/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import java.util.HashMap;
import java.util.Iterator;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsStomachSystem {
	private HashMap<Integer, clsNutritionLevel> moNutritions;
	private HashMap<Integer, Float> moFractions;
	private float mrFractionSum;
	private float mrEnergy;
	
	private float mrDefaultMaxLevel = 5.0f; //pseudo const for init purposes
	private float mrDefaultContent = 0.0f; //pseudo const for init purposes
	private float mrDefaultLowerBorder = 0.5f; //pseudo const for init purposes
	private float mrDefaultUpperBorder = 2.5f; //pseudo const for init purposes
	private float mrDefaultDecreasePerStep = 0.1f; //pseudo const for init purposes
	private float mrDefaultFraction = 1.0f; //pseudo const for init purposes
	
	/**
	 * TODO (deutsch) - insert description
	 */
	public clsStomachSystem() {
		super();
		
		moNutritions = new HashMap<Integer, clsNutritionLevel>();
		moFractions = new HashMap<Integer, Float>();
		updateFractionSum();
		updateEnergy();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poId
	 */
	public void addNutritionType(Integer poId) {
		if (!(moNutritions.containsKey(poId))) {
			clsNutritionLevel oNL = new clsNutritionLevel(mrDefaultContent, mrDefaultMaxLevel, mrDefaultLowerBorder, 
					mrDefaultUpperBorder, mrDefaultDecreasePerStep);
			moNutritions.put(poId, oNL);
			moFractions.put(poId, new Float(mrDefaultFraction));
		}
		
		updateFractionSum();
		updateEnergy();		
	}
	
	public void removeNutritionType(Integer poId) {
		if (moNutritions.containsKey(poId)) {
			moNutritions.remove(poId);
		}
		
		updateFractionSum();
		updateEnergy();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void updateFractionSum() {
		mrFractionSum = 0.0f;
		
		Iterator<Integer> i = moFractions.keySet().iterator();
		
		while (i.hasNext()) {
			mrFractionSum += (Float)moFractions.get(i.next()).floatValue();
		}
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poId
	 * @return
	 */
	public clsNutritionLevel getNutritionLevel(Integer poId) {
		return moNutritions.get(poId);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poId
	 * @param prAmount
	 */
	public void addNutrition(Integer poId, float prAmount) {
		clsNutritionLevel oNL = this.getNutritionLevel(poId);
		
		if (oNL != null) {
			oNL.increase(prAmount);
		}
		this.updateEnergy();		
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poId
	 * @param prAmount
	 */
	public void withdrawNutrition(Integer poId, float prAmount) {
		clsNutritionLevel oNL = this.getNutritionLevel(poId);
		
		if (oNL != null) {
			oNL.decrease(prAmount);
		}
		this.updateEnergy();		
	}	
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prAmount
	 */
	public void addEnergy(float prAmount) {
		float rAmountFraction = prAmount / this.mrFractionSum;

		Iterator<Integer> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			
			clsNutritionLevel oNL = moNutritions.get(oKey);
			
			float rFraction = moFractions.get(oKey).floatValue();
			
			oNL.increase(rFraction * rAmountFraction);
		}
				
		this.updateEnergy();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prAmount
	 * @return
	 */
	public float withdrawEnergy(float prAmount) {
		float rWithdrawn = 0.0f;
		float rAmountFraction = prAmount / this.mrFractionSum;
		
		Iterator<Integer> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			
			clsNutritionLevel oNL = moNutritions.get(oKey);
			
			float rFraction = moFractions.get(oKey).floatValue();
			
			rWithdrawn += oNL.decrease(rFraction * rAmountFraction);
		}		
		
		this.updateEnergy();		
		return rWithdrawn / prAmount;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	public void step() {
		Iterator<Integer> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			((clsNutritionLevel)moNutritions.get(i.next())).step();
		}
		
		updateEnergy();
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void updateEnergy() {
		mrEnergy = 0.0f;

		Iterator<Integer> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			
			clsNutritionLevel oNL = moNutritions.get(oKey);
			float rFraction = moFractions.get(oKey).floatValue();
			
			mrEnergy += oNL.getContent() * rFraction;
		}
		
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public float getEnergy() {
		return this.mrEnergy;
	}
}
