/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.exceptions;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class FoodAmountBelowZero extends bwException {

	
	public String toString() {
		return ("FoodAmountBelowZero\n")+super.toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7788291810120561668L;

}
