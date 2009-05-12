/**
 * @author deutsch
 * 11.05.2009, 17:55:57
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import bw.body.interBodyWorldSystems.clsConsumeFood;
import bw.body.internalSystems.clsFlesh;
import bw.body.internalSystems.clsHealthSystem;
import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.body.internalSystems.clsStomachSystem;
import bw.body.intraBodySystems.clsDamageNutrition;
import bw.body.itfget.itfGetFlesh;
import bw.body.itfget.itfGetHealthSystem;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.body.itfget.itfGetStomachSystem;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eBodyParts;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.05.2009, 17:55:57
 * 
 */
public class clsSimpleBody extends clsBaseBody implements itfGetInternalEnergyConsumption, itfGetHealthSystem, itfGetFlesh, itfGetStomachSystem {

	private clsDamageNutrition moDamageNutrition;
	private clsConsumeFood moConsumeFood;
	
	private clsHealthSystem moHealthSystem;
	private clsStomachSystem moStomachSystem;
	private clsFlesh moFlesh;
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
		
		//Systems:
		//Flesh
		//Health
		//Stomach (1 Nutrition for food, ggf 1 for undegistable)
		//EnergyConsumption (ggf: only with one default value - no dynamic updates from the actuators)		
		moHealthSystem = new clsHealthSystem( (clsConfigMap)moConfig.get(eBodyParts.INTSYS_HEALTH_SYSTEM));
		moStomachSystem = new clsStomachSystem( (clsConfigMap)moConfig.get(eBodyParts.INTSYS_STOMACH_SYSTEM));
		moInternalEnergyConsumption = new clsInternalEnergyConsumption( (clsConfigMap)moConfig.get(eBodyParts.INTSYS_INTERNAL_ENERGY_CONSUMPTION));
		moFlesh = new clsFlesh( (clsConfigMap)moConfig.get(eBodyParts.INTSYS_FLESH));
		
		moConsumeFood = new clsConsumeFood(moStomachSystem, (clsConfigMap)moConfig.get(eBodyParts.INTER_CONSUME_FOOD) );
		moDamageNutrition = new clsDamageNutrition(moStomachSystem, moHealthSystem, (clsConfigMap)moConfig.get(eBodyParts.INTRA_DAMAGE_NUTRITION) );
		// TODO Auto-generated constructor stub
	
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



	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 18:32:32
	 * 
	 * @see bw.body.itfGetFlesh#getFlesh()
	 */
	@Override
	public clsFlesh getFlesh() {
		// TODO Auto-generated method stub
		return moFlesh;
	}

}
