/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.utils.tools.clsFillLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsStaminaSystem {
	private clsFillLevel moStamina;
	
	public clsStaminaSystem() {
		moStamina = new clsFillLevel(1.0f, 1.0f, 0.05f);
	}
	
	public void consumeStamina(float prStaminaConsumed) {
		moStamina.decrease(prStaminaConsumed);
	}
	
	public void regainStamina(float prStaminaRegained) {
		moStamina.increase(prStaminaRegained);
	}
	
	public float getRecoveryRate() {
		return moStamina.getChange();
	}
	
	public void setRecoveryRate(float prRecoveryRate) {
		if (prRecoveryRate < 0.0f) {
			prRecoveryRate = 0.0f;
		}
		
		moStamina.setChange(prRecoveryRate);
	}
	
	public void step() {
		moStamina.update();
	}

}
