/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.body.itfStep;
import bw.exceptions.ContentColumnMaxContentExceeded;
import bw.exceptions.ContentColumnMinContentUnderrun;
import bw.utils.tools.clsFillLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsTemperatureSystem implements itfStep {
	private clsFillLevel moTemperature;
	
	public clsTemperatureSystem() {
		try {
			moTemperature = new clsFillLevel(1.0f, 2.0f, 0.00f);
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		}
	}
	
	public void cool(float prCooledBy) {
		try {
			moTemperature.decrease(prCooledBy);
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		}
	}
	
	public void heat(float prHeatedBy) {
		try {		
			moTemperature.increase(prHeatedBy);
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		}
	}
	
	public float getRecoveryRate() {
		return moTemperature.getChange();
	}
	
	public void setRecoveryRate(float prRecoveryRate) {	
		moTemperature.setChange(prRecoveryRate);
	}
	
	public void step() {
		try {		
			moTemperature.update();		
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		}
			
	}
}
