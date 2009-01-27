/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import java.util.Random;

import bw.body.itfStep;

/**
 * TODO (deutsch) - insert description 
 * TODO clean class from test/debug functions
 * 
 * @author deutsch
 * 
 */
public class clsInternalStates implements itfStep {
    public clsFlesh moFlesh;
    public clsMessengerSystem moMessengerSystem;
    public clsTemperatureSystem moTemperatureSystem;
    public clsHealthSystem moHealthSystem;
    public clsStaminaSystem moStaminaSystem;
    public clsStomachSystem moStomachSystem;
    public clsInternalEnergyConsumption moInternalEnergyConsumption; // list of all the bodies energy consumers
	
	
	/**
	 * 
	 */
	public clsInternalStates() {
  	    moFlesh = new clsFlesh();
		moMessengerSystem = new clsMessengerSystem();
		moTemperatureSystem = new clsTemperatureSystem();
		moHealthSystem = new clsHealthSystem();
		moStaminaSystem = new clsStaminaSystem();
		moStomachSystem = new clsStomachSystem();
   	    moInternalEnergyConsumption = new clsInternalEnergyConsumption();
		
   	   //testing the energy consumption
   	   randomFillIEC();
   	   setupStomach();

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
	 * TODO (deutsch) - insert description
	 *
	 */
	private void setupStomach() {
		for (int i=0; i<10; i++) {
			moStomachSystem.addNutritionType(i);
		}
		
		moStomachSystem.addEnergy(10.0f);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 * TODO remove this again
	 *
	 */
	private void randomFEEDAGENT() {
		Random rand = new Random();
		
		if (rand.nextInt(100)>95) {
			moStomachSystem.addEnergy( rand.nextFloat() );
		}
		
		for (int i=0; i<10; i++) {
			if (rand.nextInt(100)>95) {
				moStomachSystem.addNutrition(i, rand.nextFloat() * 0.1f );
			}
		}
	}
	
	/**
	 * @return the moInternalEnergyConsumption
	 */
	public clsInternalEnergyConsumption getInternalEnergyConsumption() {
		return moInternalEnergyConsumption;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	public void step() {
		randomFillIEC();
		randomFEEDAGENT();
		
		moStomachSystem.step();
		moStaminaSystem.step();
		moHealthSystem.step();
		moTemperatureSystem.step();
		
		moStomachSystem.withdrawEnergy( moInternalEnergyConsumption.getSum() );
	}

}
