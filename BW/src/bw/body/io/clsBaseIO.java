/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import bw.body.itfStep;
import bw.body.itfStepExecution;
import bw.body.itfStepSensing;
import bw.body.internalSystems.clsInternalEnergyConsumption;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsBaseIO implements itfStepSensing, itfStepExecution  {
	private clsInternalEnergyConsumption moInternalEnergyConsumption;
	
	public clsBaseIO(clsInternalEnergyConsumption poInternalEnergyConsumption) {
		moInternalEnergyConsumption = poInternalEnergyConsumption;
	}

	public void registerEnergyConsumption(int pnId, float prValue) {
		moInternalEnergyConsumption.setValue(pnId, prValue);
	}
}
