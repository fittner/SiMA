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
public class NoSuchNutritionType extends bwException {

	private int mnNutritionType;
	
	public NoSuchNutritionType(int pnNutritionType) {
		mnNutritionType = pnNutritionType;
	}
	
	public String toString() {
		return ("NoSuchNutritionType: nutrition of type "+mnNutritionType+" is unkown.\n");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4182037712570041652L;

}
