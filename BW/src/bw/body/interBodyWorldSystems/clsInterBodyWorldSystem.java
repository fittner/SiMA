/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.interBodyWorldSystems;

import bw.body.itfStepUpdateInternalState;
import bw.body.internalSystems.clsInternalSystem;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInterBodyWorldSystem implements itfStepUpdateInternalState {
	private clsConsumeFood moConsumeFood;
	private clsDamageBump moDamageBump;
	private clsDamageLightning moDamageLightning;
	
	private static final int mnDefaultGarbageNutritionType = 1; 

	/**
	 * 
	 */
	public clsInterBodyWorldSystem(clsInternalSystem poInternalSystem) {
		moConsumeFood = new clsConsumeFood(mnDefaultGarbageNutritionType, poInternalSystem.getStomachSystem());
		moDamageBump = new clsDamageBump(poInternalSystem);
		moDamageLightning = new clsDamageLightning(poInternalSystem);
	}
	
	public clsConsumeFood getConsumeFood() {
		return moConsumeFood;
	}
	
	public clsDamageBump getDamageBump() {
		return moDamageBump;
	}
	
	public clsDamageLightning getDamageLightning() {
		return moDamageLightning;
	}
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	@Override
	public void stepUpdateInternalState() {
		// TODO Auto-generated method stub
		
	}

}
