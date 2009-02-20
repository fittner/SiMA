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
	
	private float mrDefaultContent = 1.0f;
	private float mrDefaultMaxContent = 2.0f;
	private float mrDefaultChange = 0.0f;
	private float mrDefaultLowerBound = 0.9f;
	private float mrDefaultUpperBound = 1.1f;
	
	private float mrSelfRegulationAdaption;
	
	public clsTemperatureSystem() {
		try {
			moTemperature = new clsFillLevel(mrDefaultContent, mrDefaultMaxContent, mrDefaultChange, mrDefaultLowerBound, mrDefaultUpperBound);
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		}
		
		mrSelfRegulationAdaption = 0.01f;
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
	
	private void setChange() {
		if (moTemperature.isHigh()) {
			moTemperature.setChange(-mrSelfRegulationAdaption);
		} else if (moTemperature.isLow()) {
			moTemperature.setChange(mrSelfRegulationAdaption);
		} else {
			moTemperature.setChange(0.0f);
		}
	}
	
	public float getPercentageLow() {
		return moTemperature.percentageLow();
	}
	
	public float getPercentageHigh() {
		return moTemperature.percentageHigh();
	}
	
	public void step() {
		setChange();
		
		try {		
			moTemperature.update();		
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		}
			
	}
}
