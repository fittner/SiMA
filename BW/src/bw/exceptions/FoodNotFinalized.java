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
public class FoodNotFinalized extends bwException {


	public String toString() {
		return ("FoodNotFinalized: food object has not been finalized after setup.\n")+super.toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 188532958620907164L;

}
