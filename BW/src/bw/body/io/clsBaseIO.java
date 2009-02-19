/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import bw.body.itfStep;
import bw.body.internalSystems.clsInternalEnergyConsumption;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsBaseIO implements itfStep  {
	private clsInternalEnergyConsumption moInternalEnergyConsumption;
	
	public clsBaseIO(clsInternalEnergyConsumption poInternalEnergyConsumption) {
		moInternalEnergyConsumption = poInternalEnergyConsumption;
	}

	public void registerEnergyConsumption(int pnId, float prValue) {
		moInternalEnergyConsumption.setValue(pnId, prValue);
	}
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	@Override
	public abstract void step();
}
