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
import bw.utils.container.clsConfigMap;
import bw.utils.datatypes.clsMutableFloat;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsBaseIO implements itfStepSensing, itfStepExecution  {
	private clsInternalEnergyConsumption moInternalEnergyConsumption;
    protected clsConfigMap moConfig;
    
	public clsBaseIO(clsInternalEnergyConsumption poInternalEnergyConsumption, clsConfigMap poConfig) {
		moConfig = getDefaultConfig();
		moConfig.overwritewith(poConfig);
		
		moInternalEnergyConsumption = poInternalEnergyConsumption;
	}

	public void registerEnergyConsumption(int pnId, float prValue) {
		moInternalEnergyConsumption.setValue(new Integer(pnId), new clsMutableFloat(prValue));
	}
	
	protected abstract clsConfigMap getDefaultConfig();
}
