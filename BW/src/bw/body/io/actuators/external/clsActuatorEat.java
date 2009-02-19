/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.external;


import bw.actionresponses.clsEntityActionResponses;
import bw.body.io.clsBaseIO;
import bw.exceptions.*;
import bw.physicalObject.animate.clsAnimate;
import bw.utils.enums.eBodyParts;
import bw.utils.tools.clsFood;

/**
 *  implementation of a actuator for eating
 * 
 * @author muchitsch
 * 
 */
public class clsActuatorEat extends clsActuatorExt {

	private clsAnimate moAnimate;
	private float mrDefaultEnergyConsuptionValue = 1.0f;  //pseudo const for init purposes


	/**
	 * @param poEntity 
	 * @param poBaseIO
	 */
	public clsActuatorEat(clsAnimate poAnimate, clsBaseIO poBaseIO) {
		super(poBaseIO);
		
		setAnimate(poAnimate);
		
		//this registers a default energy consumption
		registerEnergyConsumption(mrDefaultEnergyConsuptionValue);
	}
	
	/**
	 * @param poEntity the Entity to set
	 */
	public void setAnimate(clsAnimate poEntity) {
		this.moAnimate = poEntity;
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
		
		try {
			eatAction();
		} catch (EntityActionResponseNotImplemented e) {
			// TODO clemens
			e.printStackTrace();
		}
		

	}

	
	/**
	 * Method for eating, takes a clsEntity in, and tries to chew it. Exception catched if not eatable.
	 *
	 * @param poEntity
	 * @throws EntityActionResponseNotImplemented 
	 */
	public void eatAction() throws EntityActionResponseNotImplemented{
		
		clsAnimate oViewedAnimate = null;
		clsEntityActionResponses oEntityActionResponse = moAnimate.getEntityActionResponses();
		
		try{
			
			//read what EatSensor sees in front of him and give it to eat action, exception if more then 1?
			//...clsAnimate oViewedAnimate = clsEatAction.View();
			
			//when we eat, we need more energy
			registerEnergyConsumption(mrDefaultEnergyConsuptionValue + 3.5f); //TODO clemens: change 50 to the real value
			
			float rWeight = 27; //größe des Bissen
			clsFood oReturnedFood = oEntityActionResponse.actionEatResponse(rWeight); //Apfel gibt mir einen Bisset food retour
			
			moAnimate.moAgentBody.getInterBodyWorldSystem().getConsumeFood().digest(oReturnedFood); // food an Body zur weiterverarbeitung geben
			
			if(oReturnedFood == null)
				throw(new EntityNotEatable(oViewedAnimate.getEntityType()) );
			
		}catch(EntityNotEatable ex){
			ex.printStackTrace();
			//TODO clemens
		}
		finally{
			//register default value again
			registerEnergyConsumption(mrDefaultEnergyConsuptionValue);
		}
		
	}

}
