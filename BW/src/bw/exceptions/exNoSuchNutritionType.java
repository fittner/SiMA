/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.exceptions;

import bw.utils.enums.eNutritions;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class exNoSuchNutritionType extends exException {

	private eNutritions mnNutritionType;
	
	public exNoSuchNutritionType(eNutritions pnNutritionType) {
		mnNutritionType = pnNutritionType;
	}
	
	@Override
	public String toString() {
		return ("NoSuchNutritionType: nutrition of type "+mnNutritionType+" is unkown.\n");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4182037712570041652L;

}
