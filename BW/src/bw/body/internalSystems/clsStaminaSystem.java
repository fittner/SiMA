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
import bw.utils.config.clsBWProperties;
import bw.utils.tools.clsContentColumn;
import bw.utils.tools.clsFillLevel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsStaminaSystem implements itfStepUpdateInternalState {   
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

	public clsStaminaSystem(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+clsContentColumn.P_CONTENT, 1);
		oProp.setProperty(pre+clsContentColumn.P_MAXCONTENT, 1);
		oProp.setProperty(pre+clsFillLevel.P_CHANGE, "0.1");
		oProp.setProperty(pre+clsFillLevel.P_LOWERBOUND, "0.33");
		oProp.setProperty(pre+clsFillLevel.P_UPPERBOUND, "0.66");	
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		moStamina = new clsFillLevel(pre, poProp);
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
