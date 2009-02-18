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
public class NutritionEmpty extends bwException {

	private int mnNutritionType;
	
	public NutritionEmpty(int pnNutritionType) {
		mnNutritionType = pnNutritionType;
	}
	
	public String toString() {
		return ("NutritionEmpty: nutrition of type "+mnNutritionType+" is empty.\n");
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6935786612345528631L;

}
