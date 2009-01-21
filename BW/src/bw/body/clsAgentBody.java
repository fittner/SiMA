/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import bw.body.internalSystems.clsInternalEnergyConsumption;

import java.util.Random;

import tstBw.body.internalSystems.*;

/**
 * The agent body is the basic container for each entity the body needs: 
 * int./ext. I/O's,  flesh, int. states, biosys., brain 
 * 
 * 
 * @author langr
 * 
 */
public class clsAgentBody {

	
	private clsInternalEnergyConsumption moInternalEnergyConsumptionList; // list of all the bodies energy consumers
    //private clsFlesh moFlesh;
	
	/**
	 * CTOR
	 */
	public clsAgentBody() {
  	   moInternalEnergyConsumptionList = new clsInternalEnergyConsumption();
		
  	   //testing the energy consumption
  	   randomFillIEC();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 * TODO remove this again
	 *
	 */
	private void randomFillIEC() {
		Random rand = new Random();
		
		if (moInternalEnergyConsumptionList.getSum() == 0) {
			// init a value for each consumer
			for (int i = 0; i<20; i++) {
				moInternalEnergyConsumptionList.setValue(i+1, rand.nextInt(100));
			}
		} else {
			for (int i = 0; i<20; i++) {
				//update values randomly 
				if (rand.nextInt(100)>80) {
					//but only sometimes - not every cycle ....
					moInternalEnergyConsumptionList.setValue(i+1, rand.nextInt(100));	
				}
			}		
		}
	}
	
	/**
	 * @return the moInternalEnergyConsumption
	 */
	public clsInternalEnergyConsumption getInternalEnergyConsumptionList() {
		return moInternalEnergyConsumptionList;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	public void step() {
		randomFillIEC();	
	}
}
