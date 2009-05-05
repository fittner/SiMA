/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import java.util.Random;
import bw.body.itfStepUpdateInternalState;
import bw.exceptions.exFoodAlreadyNormalized;
import bw.utils.container.clsConfigContainer;
import bw.utils.datatypes.clsMutableFloat;
import bw.utils.enums.eBodyParts;

/**
 * TODO (deutsch) - insert description 
 * TODO clean class from test/debug functions
 * 
 * @author deutsch
 * 
 */
public class clsInternalSystem implements itfStepUpdateInternalState {
    private clsFlesh moFlesh;
    private clsSlowMessengerSystem moSlowMessengerSystem;
    private clsFastMessengerSystem moFastMessengerSystem;
    private clsTemperatureSystem moTemperatureSystem;
    private clsHealthSystem moHealthSystem;
    private clsStaminaSystem moStaminaSystem;
    private clsStomachSystem moStomachSystem;
    private clsInternalEnergyConsumption moInternalEnergyConsumption; // list of all the bodies energy consumers
    
    private clsConfigContainer moConfig;
	
	
	/**
	 * 
	 */
	public clsInternalSystem(clsConfigContainer poConfig) {
		moConfig = getDefaultConfig();
		moConfig.overwritewith(poConfig);
		
  	    moFlesh = new clsFlesh();
  	    moSlowMessengerSystem = new clsSlowMessengerSystem();
  	    moFastMessengerSystem = new clsFastMessengerSystem();
		moTemperatureSystem = new clsTemperatureSystem();
		moHealthSystem = new clsHealthSystem();
		moStaminaSystem = new clsStaminaSystem();
		moStomachSystem = new clsStomachSystem();
   	    moInternalEnergyConsumption = new clsInternalEnergyConsumption();
		
   	   //testing the energy consumption
   	   setupStomach();
   	   setupFlesh();

	}

	private clsConfigContainer getDefaultConfig() {
		clsConfigContainer oDefault = new clsConfigContainer();
		//TODO add default values
		return oDefault;
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
	 *
	 */
	private void setupStomach() {
		for (int i=0; i<10; i++) {
			moStomachSystem.addNutritionType(i);
		}
		
		moStomachSystem.addEnergy(10.0f);
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
	public void stepUpdateInternalState() {	
		moStomachSystem.stepUpdateInternalState();
		moStaminaSystem.stepUpdateInternalState();
		moHealthSystem.stepUpdateInternalState();
		moTemperatureSystem.stepUpdateInternalState();
		moSlowMessengerSystem.stepUpdateInternalState();
		moFastMessengerSystem.stepUpdateInternalState();
		
		moStomachSystem.withdrawEnergy( moInternalEnergyConsumption.getSum() );
		
		moInternalEnergyConsumption.step();
	}

	public void stepExecution() {
	}
	
}
