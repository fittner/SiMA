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
import bw.utils.container.clsConfigDouble;
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
	
	/**
	 * @author langr
	 * 12.05.2009, 18:28:26
	 * 
	 * @return the moStamina
	 */
	public clsFillLevel getStamina() {
		return moStamina;
	}

	public clsStaminaSystem(clsConfigMap poConfig) {
		moConfig = getFinalConfig(poConfig);
		applyConfig();
		
		moStamina = null;
		
		try {
			moStamina = new clsFillLevel(
					((clsConfigDouble)moConfig.get(eConfigEntries.CONTENT)).get(), 
					((clsConfigDouble)moConfig.get(eConfigEntries.MAXCONTENT)).get(), 
					((clsConfigDouble)moConfig.get(eConfigEntries.CHANGE)).get()
					);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}
	
	private void applyConfig() {

		//TODO add custom code
	}

	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();

		oDefault.add(eConfigEntries.CONTENT, new clsConfigDouble(1.0f));
		oDefault.add(eConfigEntries.MAXCONTENT, new clsConfigDouble(1.0f));
		oDefault.add(eConfigEntries.CHANGE, new clsConfigDouble(0.05f));
		
		return oDefault;
	}	
	
	public void consumeStamina(double prStaminaConsumed) {
		try {
			moStamina.decrease(prStaminaConsumed);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}
	
	public void regainStamina(double prStaminaRegained) {
		try {
			moStamina.increase(prStaminaRegained);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}
	
	public double getRecoveryRate() {
		return moStamina.getChange();
	}
	
	public void setRecoveryRate(double prRecoveryRate) {
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
