/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import bw.body.interalSystems.clsInternalEnergyConsumption;

/**
 * The agent body is the basic container for each entity the body needs: 
 * int./ext. I/O's,  flesh, int. states, biosys., brain 
 * 
 * 
 * @author langr
 * 
 */
public class clsAgentBody {

	
	private clsInternalEnergyConsumption moInternalEnergyConsumptionList = new clsInternalEnergyConsumption(); // list of all the bodies energy consumers

	/**
	 * @return the moInternalEnergyConsumption
	 */
	public clsInternalEnergyConsumption getInternalEnergyConsumptionList() {
		return moInternalEnergyConsumptionList;

	}

	/**
	 * CTOR
	 */
	public clsAgentBody() {
		super();
		
		//testing the energy consumption
		moInternalEnergyConsumptionList.setValue(1, 27);
	}
	
	
}
