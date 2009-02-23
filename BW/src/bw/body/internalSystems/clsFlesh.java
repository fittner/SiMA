/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.exceptions.exFoodAmountBelowZero;
import bw.utils.tools.clsFood;
import java.lang.Math;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFlesh extends clsFood {

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prWeight
	 */
	public void grow(float prWeight) {
		try {
			setAmount(getAmount() + Math.abs(prWeight));
		} catch (exFoodAmountBelowZero e) {
			//can not happen
		}
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prWeight
	 * @throws exFoodAmountBelowZero
	 */
	public void reduce(float prWeight) throws exFoodAmountBelowZero {
		setAmount(getAmount() - Math.abs(prWeight));
	}	
}
