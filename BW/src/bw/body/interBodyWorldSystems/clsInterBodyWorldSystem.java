/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.interBodyWorldSystems;

import bw.body.itfStep;
import bw.body.internalSystems.clsInternalSystem;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInterBodyWorldSystem implements itfStep {
	private clsConsumeFood moConsumeFood;
	
	private static final int mnDefaultGarbageNutritionType = 1; 

	/**
	 * 
	 */
	public clsInterBodyWorldSystem(clsInternalSystem poInternalSystem) {
		moConsumeFood = new clsConsumeFood(mnDefaultGarbageNutritionType, poInternalSystem.getStomachSystem());
	}
	
	public clsConsumeFood getConsumeFood() {
		return moConsumeFood;
	}
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
