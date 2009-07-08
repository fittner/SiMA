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
public class clsTemperatureSystem implements itfStepUpdateInternalState {
    private clsConfigMap moConfig;
    
	private clsFillLevel moTemperature;
	
	private double mrSelfRegulationAdaption;
	
	public clsTemperatureSystem(clsConfigMap poConfig) {
		moConfig = getFinalConfig(poConfig);
		applyConfig();	
		
		try {
			moTemperature = new clsFillLevel(
					((clsConfigDouble)moConfig.get(eConfigEntries.CONTENT)).get(), 
					((clsConfigDouble)moConfig.get(eConfigEntries.MAXCONTENT)).get(), 
					((clsConfigDouble)moConfig.get(eConfigEntries.CHANGE)).get(), 
					((clsConfigDouble)moConfig.get(eConfigEntries.LOWERBOUND)).get(), 
					((clsConfigDouble)moConfig.get(eConfigEntries.UPPERBOUND)).get()
					);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
		
	}
	
	private void applyConfig() {

		mrSelfRegulationAdaption = ((clsConfigDouble)moConfig.get(eConfigEntries.SELFREGULATIONADAPTION)).get();
	}

	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.CONTENT, new clsConfigDouble(1.0f));
		oDefault.add(eConfigEntries.MAXCONTENT, new clsConfigDouble(2.0f));
		oDefault.add(eConfigEntries.CHANGE, new clsConfigDouble(0.0f));
		oDefault.add(eConfigEntries.LOWERBOUND, new clsConfigDouble(0.9f));
		oDefault.add(eConfigEntries.UPPERBOUND, new clsConfigDouble(1.1f));
		oDefault.add(eConfigEntries.SELFREGULATIONADAPTION, new clsConfigDouble(0.01f));

		return oDefault;
	}	
	
	public void cool(float prCooledBy) {
		try {
			moTemperature.decrease(prCooledBy);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}
	
	public void heat(float prHeatedBy) {
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
