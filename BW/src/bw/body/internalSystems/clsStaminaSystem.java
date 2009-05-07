/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.body.itfStepUpdateInternalState;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.utils.container.clsConfigMap;
import bw.utils.container.clsConfigFloat;
import bw.utils.enums.eConfigEntries;
import bw.utils.tools.clsFillLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsStaminaSystem implements itfStepUpdateInternalState {
    private clsConfigMap moConfig;
    
	private clsFillLevel moStamina;
	
	public clsStaminaSystem(clsConfigMap poConfig) {
		applyConfig(poConfig);
		
		moStamina = null;
		
		try {
			moStamina = new clsFillLevel(
					((clsConfigFloat)moConfig.get(eConfigEntries.CONTENT)).get(), 
					((clsConfigFloat)moConfig.get(eConfigEntries.MAXCONTENT)).get(), 
					((clsConfigFloat)moConfig.get(eConfigEntries.CHANGE)).get()
					);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}
	
	private void applyConfig(clsConfigMap poConfig) {
		moConfig = getDefaultConfig();
		moConfig.overwritewith(poConfig);	
		
		//TODO add custom code
	}

	private clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();

		oDefault.add(eConfigEntries.CONTENT, new clsConfigFloat(1.0f));
		oDefault.add(eConfigEntries.MAXCONTENT, new clsConfigFloat(1.0f));
		oDefault.add(eConfigEntries.CHANGE, new clsConfigFloat(0.05f));
		
		return oDefault;
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
	
	public void stepUpdateInternalState() {
		try {
			moStamina.update();
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}

}
