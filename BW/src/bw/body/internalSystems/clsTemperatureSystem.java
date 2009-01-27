/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.body.itfStep;
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
		moTemperature = new clsFillLevel(1.0f, 2.0f, 0.00f);
	}
	
	public void cool(float prCooledBy) {
		moTemperature.decrease(prCooledBy);
	}
	
	public void heat(float prHeatedBy) {
		moTemperature.increase(prHeatedBy);
	}
	
	public float getRecoveryRate() {
		return moTemperature.getChange();
	}
	
	public void setRecoveryRate(float prRecoveryRate) {	
		moTemperature.setChange(prRecoveryRate);
	}
	
	public void step() {
		moTemperature.update();
	}
}
