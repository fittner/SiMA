/**
 * @author Benny Dönz
 * 15.04.2009, 16:31:13
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionCommands;

import bw.utils.datatypes.clsMutableFloat;
import bw.body.io.actuators.clsActionCommand;
import bw.body.itfget.itfGetBody;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.entities.clsEntity;
import bw.entities.clsAnimate;
import bw.factories.clsSingletonUniqueIdGenerator;

/**
 * TODO Temporary eat command derived from clsActuatorEat 
 * 
 * @author Benny Dönz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsEatAction extends clsActionCommand{

	public clsEatAction() {
	}
	
	public boolean execute(clsEntity poEntity) {
		float rDefaultEnergyConsuptionValue = 1.0f;
		
		((itfGetInternalEnergyConsumption)((itfGetBody)poEntity).getBody()).getInternalEnergyConsumption().setValue(new Integer(clsSingletonUniqueIdGenerator.getUniqueId()), new clsMutableFloat(rDefaultEnergyConsuptionValue + 3.5f));

		/*
		//Commentary suggests this doens't work anyway...

			clsEntityActionResponses oEntityActionResponse = oEatenEntity.getEntityActionResponses();
			//when we eat, we need more energy
			registerEnergyConsumption(mrDefaultEnergyConsuptionValue + 3.5f); //TODO clemens: change 50 to the real value
			
			float rWeight = 3.33f; //größe des Bissen
			
			clsFood oReturnedFood = oEntityActionResponse.actionEatResponse(rWeight); //Apfel gibt mir einen Bisset food retour
			
			//TODO CM geht noch nicht! digest wirft exception
			moAnimate.moAgentBody.getInterBodyWorldSystem().getConsumeFood().digest(oReturnedFood); // food an Body zur weiterverarbeitung geben
			
			if(oReturnedFood == null)
				throw(new exEntityNotEatable(oViewedAnimate.getEntityType()) );
		 */
		
		return true;
	}	

}
