/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.body.itfStep;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.utils.tools.clsFillLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsStaminaSystem implements itfStep {
	private clsFillLevel moStamina;
	
	public clsStaminaSystem() {
		moStamina = null;
		
		try {
			moStamina = new clsFillLevel(1.0f, 1.0f, 0.05f);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}
	
	public void consumeStamina(float prStaminaConsumed) {
		try {
			moStamina.decrease(prStaminaConsumed);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}
	
	public void regainStamina(float prStaminaRegained) {
		try {
			moStamina.increase(prStaminaRegained);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
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
		try {
			moStamina.update();
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}

}
