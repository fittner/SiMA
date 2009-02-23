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
import bw.exceptions.exFoodAlreadyNormalized;
import bw.exceptions.exNoSuchNutritionType;

/**
 * TODO (deutsch) - insert description 
 * TODO clean class from test/debug functions
 * 
 * @author deutsch
 * 
 */
public class clsInternalSystem implements itfStep {
    private clsFlesh moFlesh;
    private clsSlowMessengerSystem moSlowMessengerSystem;
    private clsFastMessengerSystem moFastMessengerSystem;
    private clsTemperatureSystem moTemperatureSystem;
    private clsHealthSystem moHealthSystem;
    private clsStaminaSystem moStaminaSystem;
    private clsStomachSystem moStomachSystem;
    private clsInternalEnergyConsumption moInternalEnergyConsumption; // list of all the bodies energy consumers
	
	
	/**
	 * 
	 */
	public clsInternalSystem() {
  	    moFlesh = new clsFlesh();
  	    moSlowMessengerSystem = new clsSlowMessengerSystem();
  	    moFastMessengerSystem = new clsFastMessengerSystem();
		moTemperatureSystem = new clsTemperatureSystem();
		moHealthSystem = new clsHealthSystem();
		moStaminaSystem = new clsStaminaSystem();
		moStomachSystem = new clsStomachSystem();
   	    moInternalEnergyConsumption = new clsInternalEnergyConsumption();
		
   	   //testing the energy consumption
   	   randomFillIEC();
   	   setupStomach();
   	   setupFlesh();

	}

	
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void setupFlesh() {
		try {
			moFlesh.addNutritionFraction(1, 0.3f);
			moFlesh.addNutritionFraction(2, 0.1f);
			moFlesh.addNutritionFraction(3, 0.2f);
			moFlesh.addNutritionFraction(4, 1.3f);
			moFlesh.addNutritionFraction(5, 0.9f);
			moFlesh.finalize();
		} catch (exFoodAlreadyNormalized e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * @return the moFlesh
	 */
	public clsFlesh getFlesh() {
		return moFlesh;
	}


	/**
	 * @return the moSlowMessengerSystem
	 */
	public clsSlowMessengerSystem getSlowMessengerSystem() {
		return moSlowMessengerSystem;
	}

	/**
	 * @return the moFastMessengerSystem
	 */
	public clsFastMessengerSystem getFastMessengerSystem() {
		return moFastMessengerSystem;
	}

	/**
	 * @return the moTemperatureSystem
	 */
	public clsTemperatureSystem getTemperatureSystem() {
		return moTemperatureSystem;
	}


	/**
	 * @return the moHealthSystem
	 */
	public clsHealthSystem getHealthSystem() {
		return moHealthSystem;
	}


	/**
	 * @return the moStaminaSystem
	 */
	public clsStaminaSystem getStaminaSystem() {
		return moStaminaSystem;
	}


	/**
	 * @return the moStomachSystem
	 */
	public clsStomachSystem getStomachSystem() {
		return moStomachSystem;
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
				try {
					moStomachSystem.addNutrition(i, rand.nextFloat() * 0.1f );
				} catch (exNoSuchNutritionType e) {
					// ignore exception
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
		moSlowMessengerSystem.step();
		moFastMessengerSystem.step();
		
		moStomachSystem.withdrawEnergy( moInternalEnergyConsumption.getSum() );
	}

}
