/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.body.itfStepUpdateInternalState;
import bw.utils.container.clsConfigMap;
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
    
    private clsConfigMap moConfig;
	
	
	/**
	 * 
	 */
	public clsInternalSystem(clsConfigMap poConfig) {
		applyConfig(poConfig);
		
  	    moFlesh 				= new clsFlesh((clsConfigMap) moConfig.get(eBodyParts.INTSYS_FLESH));
  	    moSlowMessengerSystem 	= new clsSlowMessengerSystem((clsConfigMap) moConfig.get(eBodyParts.INTSYS_SLOW_MESSENGER_SYSTEM));
  	    moFastMessengerSystem 	= new clsFastMessengerSystem((clsConfigMap) moConfig.get(eBodyParts.INTSYS_FAST_MESSENGER_SYSTEM));
		moTemperatureSystem 	= new clsTemperatureSystem((clsConfigMap) moConfig.get(eBodyParts.INTSYS_TEMPERATURE_SYSTEM));
		moHealthSystem 			= new clsHealthSystem((clsConfigMap) moConfig.get(eBodyParts.INTSYS_HEALTH_SYSTEM));
		moStaminaSystem			= new clsStaminaSystem((clsConfigMap) moConfig.get(eBodyParts.INTSYS_STAMINA_SYSTEM));
		moStomachSystem 		= new clsStomachSystem((clsConfigMap) moConfig.get(eBodyParts.INTSYS_STOMACH_SYSTEM));
   	    moInternalEnergyConsumption = new clsInternalEnergyConsumption((clsConfigMap) moConfig.get(eBodyParts.INTSYS_INTERNAL_ENERGY_CONSUMPTION));		
	}
	
	private void applyConfig(clsConfigMap poConfig) {
		moConfig = getDefaultConfig();
		moConfig.overwritewith(poConfig);	
		
		//TODO add custom code
	}

	private clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		//TODO add default values
		return oDefault;
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
