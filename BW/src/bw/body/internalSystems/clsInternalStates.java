/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import java.util.Random;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInternalStates {
	private clsMessengerSystem moMessengerSystem;
	private clsTemperatureSystem moTemperatureSystem;
	private clsHealthSystem moHealthSystem;
	private clsStaminaSystem moStaminaSystem;
	private clsStomachSystem moStomachSystem;
	private clsInternalEnergyConsumption moInternalEnergyConsumption; // list of all the bodies energy consumers
	
	
	public clsInternalStates() {
		moMessengerSystem = new clsMessengerSystem();
		moTemperatureSystem = new clsTemperatureSystem();
		moHealthSystem = new clsHealthSystem();
		moStaminaSystem = new clsStaminaSystem();
		moStomachSystem = new clsStomachSystem();
   	    moInternalEnergyConsumption = new clsInternalEnergyConsumption();
		
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
		
		if (moInternalEnergyConsumption.getSum() == 0) {
			// init a value for each consumer
			for (int i = 0; i<20; i++) {
				moInternalEnergyConsumption.setValue(i+1, rand.nextInt(100));
			}
		} else {
			for (int i = 0; i<20; i++) {
				//update values randomly 
				if (rand.nextInt(100)>80) {
					//but only sometimes - not every cycle ....
					moInternalEnergyConsumption.setValue(i+1, rand.nextInt(100));	
				}
			}		
		}
	}
	
	/**
	 * @return the moInternalEnergyConsumption
	 */
	public clsInternalEnergyConsumption getInternalEnergyConsumption() {
		return moInternalEnergyConsumption;
	}
	
	public void step() {
		randomFillIEC();
		
		moStomachSystem.step();
	}

}
