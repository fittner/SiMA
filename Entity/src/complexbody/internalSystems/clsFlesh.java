/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.internalSystems;

import body.utils.clsFood;

import java.lang.Math;

import properties.clsProperties;

import utils.exceptions.exFoodWeightBelowZero;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFlesh extends clsFood {
    private boolean mnTotallyConsumed;
	
	public clsFlesh() {
		super();
		mnTotallyConsumed = false;
	}
	
	
	public clsFlesh(String poPrefix, clsProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
		totallyConsumed();
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsFood.getDefaultProperties(pre) );
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);

		//nothing to do ...
	}

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param prWeight
	 */
	public void grow(double prWeight) {
		try {
			setWeight(getWeight() + Math.abs(prWeight));
			mnTotallyConsumed = false;
		} catch (exFoodWeightBelowZero e) {
			//can not happen
		}
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param prWeight
	 * @throws exFoodWeightBelowZero
	 */
	public void reduce(double prWeight) throws exFoodWeightBelowZero {
		setWeight(getWeight() - Math.abs(prWeight));
	}	
	
	public clsFood withdraw(double prAmount) {		
		clsFood oResult = new clsFood(this);
		double rWeight = getWeight();
		
		try {
			reduce(prAmount);
			oResult.setWeight(prAmount);
		} catch (exFoodWeightBelowZero e) {
			try {
				oResult.setWeight(rWeight);
			} catch (exFoodWeightBelowZero e1) {
				// 
			}
			mnTotallyConsumed = true;
		}
		
		return oResult;
	}
	
	public boolean getTotallyConsumed() {
		return mnTotallyConsumed;
	}
	
	@Override
	public void setWeight(double prAmount) throws utils.exceptions.exFoodWeightBelowZero {
		super.setWeight(prAmount);
		totallyConsumed();
	}
	
	private void totallyConsumed() {
		
		if (getWeight() == 0.0) {
			mnTotallyConsumed = true;
		} else {
			mnTotallyConsumed = false;
		}		
	}
	
	@Override
	public String toString() {
		String oR = super.toString();
		oR+=" | totally consumed:"+mnTotallyConsumed;
		return oR;
	}
}
