/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import config.clsProperties;
import bw.body.clsBaseBody;
import bw.body.itfStepExecution;
import bw.body.itfStepSensing;
import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsBaseIO implements itfStepSensing, itfStepExecution  {
	private clsInternalEnergyConsumption moInternalEnergyConsumption;
	
	public clsBaseIO(String poPrefix, clsProperties poProp, clsBaseBody poBody) {
		if (poBody instanceof itfGetInternalEnergyConsumption) {
			moInternalEnergyConsumption = ((itfGetInternalEnergyConsumption)poBody).getInternalEnergyConsumption();
		} else {
			moInternalEnergyConsumption = null;
		}
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		// String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		//nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);

		//nothing to do
	}	
	
	public void registerEnergyConsumption(eBodyParts pnId, double prValue) {
		if (moInternalEnergyConsumption != null) {
			moInternalEnergyConsumption.setValue(pnId, new clsMutableDouble(prValue));
		}
	}

	public void registerEnergyConsumptionOnce(eBodyParts pnId, double prValue) {
		if (moInternalEnergyConsumption != null) {
			moInternalEnergyConsumption.setValueOnce(pnId, new clsMutableDouble(prValue));
		}
	}
}
