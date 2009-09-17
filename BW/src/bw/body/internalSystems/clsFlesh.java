/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.exceptions.exFoodWeightBelowZero;
import bw.utils.tools.clsFood;
import java.lang.Math;

import config.clsBWProperties;

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
	
	
	public clsFlesh(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
		totallyConsumed();
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsFood.getDefaultProperties(pre) );
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

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
	public void setWeight(double prAmount) throws bw.exceptions.exFoodWeightBelowZero {
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
