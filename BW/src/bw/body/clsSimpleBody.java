/**
 * @author deutsch
 * 11.05.2009, 17:55:57
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import bw.body.internalSystems.clsHealthSystem;
import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.body.internalSystems.clsStaminaSystem;
import bw.body.internalSystems.clsStomachSystem;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigMap;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.05.2009, 17:55:57
 * 
 */
public class clsSimpleBody extends clsBaseBody {

	private clsHealthSystem moHealthSystem;
	private clsStomachSystem moStomachSystem;
	private clsStaminaSystem moStaminaSystem;
	private clsInternalEnergyConsumption moInternalEnergyConsumption;

	
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.05.2009, 17:56:02
	 *
	 * @param poConfig
	 */
	public clsSimpleBody(clsEntity poEntity, clsConfigMap poConfig) {
		super(poEntity, getFinalConfig(poConfig));
		applyConfig();
		// TODO Auto-generated constructor stub
		
		//Systems:
		//Flesh
		//Health
		//Stomach (1 Nutrition for food, ggf 1 for undegistable)
		//EnergyConsumption (ggf: only with one default value - no dynamic updates from the actuators)
		
		//Sensors:
		//VISION
		//EATABLE
		//BUMP
		//EnergyLevel
		//HealthLevel
		
		//Actuators:
		//EAT
		//MOVE
	}
	
	

	private void applyConfig() {
		//TODO add code ...
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();

		//TODO add code ...

		return oDefault;
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
	

	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.05.2009, 17:55:57
	 * 
	 * @see bw.body.itfStepSensing#stepSensing()
	 */
	@Override
	public void stepSensing() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.05.2009, 17:55:57
	 * 
	 * @see bw.body.itfStepUpdateInternalState#stepUpdateInternalState()
	 */
	@Override
	public void stepUpdateInternalState() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.05.2009, 17:55:57
	 * 
	 * @see bw.body.itfStepProcessing#stepProcessing()
	 */
	@Override
	public clsBrainActionContainer stepProcessing() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.05.2009, 17:55:57
	 * 
	 * @see bw.body.itfStepExecution#stepExecution(bw.body.motionplatform.clsBrainActionContainer)
	 */
	@Override
	public void stepExecution(clsBrainActionContainer poActionList) {
		// TODO Auto-generated method stub

	}

}
