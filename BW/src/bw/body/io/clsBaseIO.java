/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import config.clsBWProperties;
import bw.body.clsBaseBody;
import bw.body.itfStepExecution;
import bw.body.itfStepSensing;
import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.utils.datatypes.clsMutableDouble;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsBaseIO implements itfStepSensing, itfStepExecution  {
	private clsInternalEnergyConsumption moInternalEnergyConsumption;
	
	public clsBaseIO(String poPrefix, clsBWProperties poProp, clsBaseBody poBody) {
		if (poBody instanceof itfGetInternalEnergyConsumption) {
			moInternalEnergyConsumption = ((itfGetInternalEnergyConsumption)poBody).getInternalEnergyConsumption();
		} else {
			moInternalEnergyConsumption = null;
		}
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

		//nothing to do
	}	
	
	public void registerEnergyConsumption(int pnId, double prValue) {
		if (moInternalEnergyConsumption != null) {
			moInternalEnergyConsumption.setValue(new Integer(pnId), new clsMutableDouble(prValue));
		}
	}

	public void registerEnergyConsumptionOnce(int pnId, double prValue) {
		if (moInternalEnergyConsumption != null) {
			moInternalEnergyConsumption.setValueOnce(new Integer(pnId), new clsMutableDouble(prValue));
		}
	}
}
