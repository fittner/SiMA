/**
 * @author Benny Dönz
 * 13.05.2009, 21:44:44
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import java.util.Iterator;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import sim.physics2D.physicalObject.PhysicalObject2D;
import statictools.clsSingletonUniqueIdGenerator;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.itfget.itfGetBody;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.entities.clsEntity;
import bw.utils.datatypes.clsMutableFloat;
import decisionunit.itf.actions.*;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import enums.eSensorExtType;
import bw.actionresponses.clsEntityActionResponses;
import bw.utils.tools.clsFood;

/**
 * TODO Temporary eat command derived from clsActuatorEat 
 * 
 * @author Benny Dönz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsExecutorEat extends clsActionExecutor{

	float mrDefaultEnergyConsuptionValue = 1.0f;
	
	public String getName() {
		return "Eat executor";
	}
	
	public boolean execute(itfActionCommand poCommand, clsEntity poEntity) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)poEntity).getBody();
		
		//Get something to eat
		clsEntity oEatenEntity = null;
		clsSensorEatableArea oEatableSensor = (clsSensorEatableArea) oBody.getExternalIO().moSensorExternal.get(eSensorExtType.EATABLE_AREA);
		
		Iterator<Integer> i = oEatableSensor.getViewObj().keySet().iterator();
		if (i.hasNext()) {
			Integer oKey = i.next();
			oEatenEntity = getEntity( oEatableSensor.getViewObj().get(oKey) );
		}

		//Eat it
        if(oEatenEntity != null)
        {
        	try { //Catch anything ... it's only a temporary solution!
            clsEntityActionResponses oEntityActionResponse = oEatenEntity.getEntityActionResponses();
               
            //registerEnergyConsumptionOnce(mrDefaultEnergyConsuptionValue + 3.5f);                 //when we eat, we need more energy
            oBody.getInternalEnergyConsumption().setValueOnce(new Integer(clsSingletonUniqueIdGenerator.getUniqueId()), new clsMutableFloat(mrDefaultEnergyConsuptionValue + 3.5f));
            
            float rWeight = 1.0f; //größe des Bissen       
            clsFood oReturnedFood =oEntityActionResponse.actionEatResponse(rWeight); //Apfel gibt mir einen Bisset food retour
               
            if(oReturnedFood != null) {                
            	oBody.getInterBodyWorldSystem().getConsumeFood().digest(oReturnedFood);
            }
        	}
            catch (Exception e)
            { }
        }

        return true;
	}	

	private clsEntity getEntity(PhysicalObject2D poObject) {
		clsEntity oResult = null;
		
		if (poObject instanceof clsMobileObject2D) {
			oResult = ((clsMobileObject2D) poObject).getEntity();
		} else if (poObject instanceof clsStationaryObject2D) {
			oResult = ((clsStationaryObject2D) poObject).getEntity();
		}	
		
		return oResult;
	}
}
