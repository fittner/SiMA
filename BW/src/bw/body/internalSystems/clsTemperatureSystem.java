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
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsTemperatureSystem implements itfStepUpdateInternalState {
    public static final String P_SELFREGULATION = "selfregulation";
    
	private clsFillLevel moTemperature;
	
	private double mrSelfRegulationAdaption;
	
	public clsTemperatureSystem(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+clsContentColumn.P_CONTENT, 1);
		oProp.setProperty(pre+clsContentColumn.P_MAXCONTENT, 1);
		oProp.setProperty(pre+clsFillLevel.P_CHANGE, "0.05");
		oProp.setProperty(pre+clsFillLevel.P_LOWERBOUND, "0.33");
		oProp.setProperty(pre+clsFillLevel.P_UPPERBOUND, "0.66");	
		oProp.setProperty(pre+P_SELFREGULATION, "0.01");		
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		mrSelfRegulationAdaption = poProp.getPropertyDouble(pre+P_SELFREGULATION);
		moTemperature = new clsFillLevel(pre, poProp);
	}
		
	public void cool(double prCooledBy) {
		try {
			moTemperature.decrease(prCooledBy);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}
	
	public void heat(double prHeatedBy) {
		try {		
			moTemperature.increase(prHeatedBy);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}
	
	public double getRecoveryRate() {
		return moTemperature.getChange();
	}
	
	public void setRecoveryRate(double prRecoveryRate) {	
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
	
	public double getPercentageLow() {
		return moTemperature.percentageLow();
	}
	
	public double getPercentageHigh() {
		return moTemperature.percentageHigh();
	}
	
	public void stepUpdateInternalState() {
		setChange();
		
		try {		
			moTemperature.update();		
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
			
	}
}
