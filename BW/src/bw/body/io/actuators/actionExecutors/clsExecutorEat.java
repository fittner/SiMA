/**
 * @author Benny Dönz
 * 13.05.2009, 21:44:44
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import bw.body.io.actuators.clsActionExecutor;
import bw.body.itfget.itfGetBody;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.entities.clsEntity;
import bw.factories.clsSingletonUniqueIdGenerator;
import bw.utils.datatypes.clsMutableFloat;
import decisionunit.itf.actions.*;
/**
 * TODO Temporary eat command derived from clsActuatorEat 
 * 
 * @author Benny Dönz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsExecutorEat extends clsActionExecutor{

	public boolean execute(itfActionCommand poCommand, clsEntity poEntity) {
		float rDefaultEnergyConsuptionValue = 1.0f;
		
		((itfGetInternalEnergyConsumption)((itfGetBody)poEntity).getBody()).getInternalEnergyConsumption().setValue(new Integer(clsSingletonUniqueIdGenerator.getUniqueId()), new clsMutableFloat(rDefaultEnergyConsuptionValue + 3.5f));

		/*
		//Commentary suggests this doens't work anyway...

			clsEntityActionResponses oEntityActionResponse = oEatenEntity.getEntityActionResponses();
			//when we eat, we need more energy
			registerEnergyConsumption(mrDefaultEnergyConsuptionValue + 3.5f); //TODO clemens: change 50 to the real value
			
			float rWeight = 1.0f; //größe des Bissen
			
			clsFood oReturnedFood = oEntityActionResponse.actionEatResponse(rWeight); //Apfel gibt mir einen Bisset food retour
			
			//TODO CM geht noch nicht! digest wirft exception
			moAnimate.moAgentBody.getInterBodyWorldSystem().getConsumeFood().digest(oReturnedFood); // food an Body zur weiterverarbeitung geben
			
			if(oReturnedFood == null)
				throw(new exEntityNotEatable(oViewedAnimate.getEntityType()) );
		 */

		return true;
	}	

}
