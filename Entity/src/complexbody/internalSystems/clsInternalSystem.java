/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.internalSystems;

import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import datatypes.clsMutableDouble;
import entities.enums.eBodyParts;
import body.itfStepUpdateInternalState;
import complexbody.internalSystems.clsBodyOrganSystem;

/**
 * DOCUMENT (deutsch) - insert description 
 * TODO clean class from test/debug functions
 * 
 * @author deutsch
 * 
 */
public class clsInternalSystem implements itfStepUpdateInternalState {
	public static final String P_FLESH = "flesh";
	public static final String P_SLOWMESSENGER = "slowmessenger";
	public static final String P_FASTMESSENGER = "fastmessenger";
	public static final String P_TEMPERATURE = "temperature";
	public static final String P_HEALTH = "health";
	public static final String P_STAMINA = "stamina";
	public static final String P_STOMACH = "stomach";
	public static final String P_INTENERGYCONSUMPTION = "intenergyconsumption";
	public static final String P_BASEENERGYCONSUMPTION = "baseenergyconsumption";
	public static final String P_ENERGY_CONSUMPTION_FACTOR ="ENERGY_CONSUMPTION_FACTOR";
	public static final String P_SPEECHSYSTEM = "speechsystem"; // MW 
	public static final String P_BODYORGANSYSTEM = "bodyorgansystem";

	
    private clsFlesh moFlesh;
    private clsSlowMessengerSystem moSlowMessengerSystem;
    private clsFastMessengerSystem moFastMessengerSystem;
    private clsTemperatureSystem moTemperatureSystem;
    private clsHealthSystem moHealthSystem;
    private clsStaminaSystem moStaminaSystem;
    private clsDigestiveSystem moStomachSystem;
    private clsInternalEnergyConsumption moInternalEnergyConsumption; // list of all the bodies energy consumers
    private clsSpeechSystem moSpeechSystem; // MW 
    
    private clsBodyOrganSystem moBOrganSystem;
   
	private clsPersonalityParameterContainer moPersonalityParameterContainer;

	private double mrEnergyConsumptionFactor;
    
    private double mrBaseEnergyConsumption;
    
	public clsInternalSystem(String poPrefix, clsProperties poProp, clsPersonalityParameterContainer poPersonalityParameterContainer) {
		moPersonalityParameterContainer=poPersonalityParameterContainer;
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();

		oProp.putAll( clsFlesh.getDefaultProperties(pre+P_FLESH) );
		oProp.putAll( clsSlowMessengerSystem.getDefaultProperties(pre+P_SLOWMESSENGER) );
		oProp.putAll( clsFastMessengerSystem.getDefaultProperties(pre+P_FASTMESSENGER) );
		oProp.putAll( clsTemperatureSystem.getDefaultProperties(pre+P_TEMPERATURE) );
		oProp.putAll( clsHealthSystem.getDefaultProperties(pre+P_HEALTH) );
		oProp.putAll( clsStaminaSystem.getDefaultProperties(pre+P_STAMINA) );
		oProp.putAll( clsDigestiveSystem.getDefaultProperties(pre+P_STOMACH) );
		oProp.putAll( clsInternalEnergyConsumption.getDefaultProperties(pre+P_INTENERGYCONSUMPTION) );
		oProp.putAll( clsSpeechSystem.getDefaultProperties(pre+P_SPEECHSYSTEM) ); // MW
		oProp.putAll( clsBodyOrganSystem.getDefaultProperties(pre+P_BODYORGANSYSTEM) );

		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.02);


		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

  	    moFlesh 				= new clsFlesh(pre+P_FLESH, poProp);
  	    moSlowMessengerSystem 	= new clsSlowMessengerSystem(pre+P_SLOWMESSENGER, poProp);
  	    moFastMessengerSystem 	= new clsFastMessengerSystem(pre+P_FASTMESSENGER, poProp);
		moTemperatureSystem 	= new clsTemperatureSystem(pre+P_TEMPERATURE, poProp);
		moHealthSystem 			= new clsHealthSystem(pre+P_HEALTH, poProp);
		moStaminaSystem			= new clsStaminaSystem(pre+P_STAMINA, poProp);
		moStomachSystem 		= new clsDigestiveSystem(pre+P_STOMACH, poProp, moPersonalityParameterContainer);
   	    moInternalEnergyConsumption = new clsInternalEnergyConsumption(pre+P_INTENERGYCONSUMPTION, poProp);
   	 	moSpeechSystem 			= new clsSpeechSystem(pre+P_SPEECHSYSTEM, poProp); // MW

   	 	moBOrganSystem			= new clsBodyOrganSystem(pre+P_BODYORGANSYSTEM, poProp);

   	    mrBaseEnergyConsumption = poProp.getPropertyDouble(pre+P_BASEENERGYCONSUMPTION);

   	 	moInternalEnergyConsumption.setValue(eBodyParts.INTSYS, new clsMutableDouble(mrBaseEnergyConsumption));

		mrEnergyConsumptionFactor = moPersonalityParameterContainer.getPersonalityParameter("INTERNAL_SYSTEM", P_ENERGY_CONSUMPTION_FACTOR).getParameterDouble();

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
	public clsDigestiveSystem getStomachSystem() {
		return moStomachSystem;
	}

	
	public clsBodyOrganSystem getBOrganSystem() {
		return moBOrganSystem;
	}

	/**
	 * @return the moInternalEnergyConsumption
	 */
	public clsInternalEnergyConsumption getInternalEnergyConsumption() {
		return moInternalEnergyConsumption;
	}
	
	// ** MW
	/**
	 * @return the moSpeechSystem
	 */
	public clsSpeechSystem getSpeechSystem() {
		return moSpeechSystem;
	}
	// MW **
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 */
	@Override
	public void stepUpdateInternalState() {	
		moStomachSystem.stepUpdateInternalState();
		moStaminaSystem.stepUpdateInternalState();
		moHealthSystem.stepUpdateInternalState();
		moTemperatureSystem.stepUpdateInternalState();
		moSlowMessengerSystem.stepUpdateInternalState();
		moFastMessengerSystem.stepUpdateInternalState();
		moBOrganSystem.stepUpdateInternalState();
		
		moStomachSystem.withdrawEnergy( moInternalEnergyConsumption.getSum()*mrEnergyConsumptionFactor);
		
		moInternalEnergyConsumption.step();
	}

	public void stepExecution() {
	}

}
