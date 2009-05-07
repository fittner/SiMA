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
public class clsTemperatureSystem implements itfStepUpdateInternalState {
    private clsConfigMap moConfig;
    
	private clsFillLevel moTemperature;
	
	private float mrSelfRegulationAdaption;
	
	public clsTemperatureSystem(clsConfigMap poConfig) {
		moConfig = getFinalConfig(poConfig);
		applyConfig();	
		
		try {
			moTemperature = new clsFillLevel(
					((clsConfigFloat)moConfig.get(eConfigEntries.CONTENT)).get(), 
					((clsConfigFloat)moConfig.get(eConfigEntries.MAXCONTENT)).get(), 
					((clsConfigFloat)moConfig.get(eConfigEntries.CHANGE)).get(), 
					((clsConfigFloat)moConfig.get(eConfigEntries.LOWERBOUND)).get(), 
					((clsConfigFloat)moConfig.get(eConfigEntries.UPPERBOUND)).get()
					);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
		
	}
	
	private void applyConfig() {

		mrSelfRegulationAdaption = ((clsConfigFloat)moConfig.get(eConfigEntries.SELFREGULATIONADAPTION)).get();
	}

	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.CONTENT, new clsConfigFloat(1.0f));
		oDefault.add(eConfigEntries.MAXCONTENT, new clsConfigFloat(2.0f));
		oDefault.add(eConfigEntries.CHANGE, new clsConfigFloat(0.0f));
		oDefault.add(eConfigEntries.LOWERBOUND, new clsConfigFloat(0.9f));
		oDefault.add(eConfigEntries.UPPERBOUND, new clsConfigFloat(1.1f));
		oDefault.add(eConfigEntries.SELFREGULATIONADAPTION, new clsConfigFloat(0.01f));

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
	
	public void stepUpdateInternalState() {
		setChange();
		
		try {		
			moTemperature.update();		
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
			
	}
}
