/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import bw.body.itfStep;
import bw.body.internalSystems.clsHealthSystem;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.internalSystems.clsStomachSystem;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageNutrition implements itfStep {

	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.02.2009, 19:51:54
	 */
	private clsStomachSystem moStomachSystem;
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.02.2009, 19:51:52
	 */
	private clsHealthSystem moHealthSystem;
	
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.02.2009, 19:51:48
	 *
	 * @param poInternalSystem
	 */
	public clsDamageNutrition(clsInternalSystem poInternalSystem) {
		moStomachSystem = poInternalSystem.getStomachSystem();
		moHealthSystem = poInternalSystem.getHealthSystem();
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 19.02.2009, 19:51:45
	 *
	 */
	private void nutritionNotWithinHealthyRange() {
		
	}
	
    /* (non-Javadoc)
     *
     * @author deutsch
     * 19.02.2009, 19:51:04
     * 
     * @see bw.body.itfStep#step()
     */
    public void step() {
    	nutritionNotWithinHealthyRange();
    }
}
