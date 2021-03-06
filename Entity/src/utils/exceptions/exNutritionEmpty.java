/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package utils.exceptions;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class exNutritionEmpty extends exException {

	private int mnNutritionType;
	
	public exNutritionEmpty(int pnNutritionType) {
		mnNutritionType = pnNutritionType;
	}
	
	@Override
	public String toString() {
		return ("NutritionEmpty: nutrition of type "+mnNutritionType+" is empty.\n");
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6935786612345528631L;

}
