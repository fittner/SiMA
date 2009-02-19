/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.external;

import bw.clsEntity;
import bw.actionresponses.clsEntityActionResponses;
import bw.body.io.clsBaseIO;
import bw.exceptions.*;
import bw.utils.enums.eBodyParts;

/**
 *  implementation of a actuator for eating
 * 
 * @author muchitsch
 * 
 */
public class clsActuatorEat extends clsActuatorExt {

	private clsEntity moEntity;
	private float mrDefaultEnergyConsuptionValue = 1.0f;  //pseudo const for init purposes


	/**
	 * @param poEntity 
	 * @param poBaseIO
	 */
	public clsActuatorEat(clsEntity poEntity, clsBaseIO poBaseIO) {
		super(poBaseIO);
		
		setEntity(poEntity);
		
		//this registers a default energy consumption
		registerEnergyConsumption(mrDefaultEnergyConsuptionValue);
	}
	
	/**
	 * @param poEntity the Entity to set
	 */
	public void setEntity(clsEntity poEntity) {
		this.moEntity = poEntity;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.ACTUATOR_EXT_EAT;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. eat actuator";
	}

	/* (non-Javadoc)
	 * @see bw.body.io.actuators.itfActuatorUpdate#updateActuatorData()
	 */
	@Override
	public void updateActuatorData() {
		
		
		//is there something present?
		// can i put it in?
		//eat/bite object
		//food = XYZ.withdrawFood()
		//internal system.addFood(food)
		

	}
	
	
	/**
	 * Method for eating, takes a clsEntity in, and tries to chew it. Exception catched if not eatable.
	 *
	 * @param poEntity
	 * @throws EntityActionResponseNotImplemented 
	 */
	public void eat(clsEntity poEatenEntity) throws EntityActionResponseNotImplemented{
		
		clsEntity oEatenEntity = poEatenEntity;
		clsEntityActionResponses oEntityActionResponse = moEntity.getEntityActionResponses();
		
		try{

			//when we eat, we need more energy
			registerEnergyConsumption(mrDefaultEnergyConsuptionValue + 3.5f); //TODO clemens: change 50 to the real value
			
			float rWeight = 27;
			
			oEntityActionResponse.actionEatResponse(rWeight);
			
			//testing the exception
			throw(new EntityNotEatable(oEatenEntity.getEntityType()) );
			
			
		}catch(EntityNotEatable ex){
			
		}
		finally{
			//register default value again
			registerEnergyConsumption(mrDefaultEnergyConsuptionValue);
		}
	
		
		
		
	
		
	}

}
