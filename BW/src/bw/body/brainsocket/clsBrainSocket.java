/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.brainsocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import sim.physics2D.physicalObject.PhysicalObject2D;

import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.clsActionCommandContainer;
import decisionunit.itf.actions.clsActionCommands;
import decisionunit.itf.actions.clsEatAction;
import decisionunit.itf.actions.clsMotionAction;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsSensorData;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsVisionEntry;
import enums.eSensorType;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import bfg.tools.shapes.clsPolarcoordinate;
import bw.body.itfStepProcessing;
import bw.body.io.sensors.external.clsSensorBump;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorExt;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.io.sensors.internal.clsSensorInt;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsEntity;
import bw.utils.enums.eActionCommandMotion;
import bw.utils.enums.eActionCommandType;
import bw.utils.enums.eSensorExtType;
import bw.utils.enums.eSensorIntType;

/**
 * The brain is the container for the mind and has a direct connection to external and internal IO.
 * Done: re-think if we insert a clsCerebellum for the neuroscientific perception-modules like R. Velik.
 * Answer: moSymbolization -> all done in another project
 * 
 * @author langr
 * 
 */
public class clsBrainSocket implements itfStepProcessing {

	private clsBaseDecisionUnit moDecisionUnit;
	private HashMap<eSensorExtType, clsSensorExt> moSensorsExt;
	private HashMap<eSensorIntType, clsSensorInt> moSensorsInt;
	
	
	public clsBrainSocket(HashMap<eSensorExtType, clsSensorExt> poSensorsExt, HashMap<eSensorIntType, clsSensorInt> poSensorsInt) {
		moSensorsExt = poSensorsExt;
		moSensorsInt = poSensorsInt;
	}

	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
//	public void stepProcessing(clsAnimate poAnimate, clsBrainActionContainer poActionList) {
	public clsBrainActionContainer stepProcessing() {
		if (moDecisionUnit != null) {
			moDecisionUnit.update(convertSensorData());
			clsActionCommandContainer oDecisionUnitResult = moDecisionUnit.process();

			return convertActionCommands(oDecisionUnitResult);
		} else {
			return new clsBrainActionContainer();
		}
	}
	
	/* **************************************************** CONVERT SENSOR DATA *********************************************** */
	private clsSensorData convertSensorData() {
		clsSensorData oData = new clsSensorData();
		
		oData.addSensor(eSensorType.BUMP, convertBumpSensor() );
		oData.addSensor(eSensorType.EATABLE_AREA, convertEatAbleAreaSensor() );
		oData.addSensor(eSensorType.VISION, converVisionSensor() );
		
		return oData;
	}
	
	private clsVision converVisionSensor() {
		clsVision oData = new clsVision();
		
		clsSensorVision oVision = (clsSensorVision)(moSensorsExt.get(eSensorExtType.VISION));
		
		Iterator<Integer> i = oVision.getViewObj().keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = i.next();
			PhysicalObject2D visionObj = oVision.getViewObj().get(oKey);
			ARSsim.physics2D.util.clsPolarcoordinate visionDir = oVision.getViewObjDir().get(oKey);
			clsVisionEntry oEntry = convertVisionEntry(visionObj, visionDir);
			if (oEntry != null) {
			  oData.add(oEntry);
			}
		}
		
		return oData;
	}

	private clsVisionEntry convertVisionEntry(PhysicalObject2D visionObj, ARSsim.physics2D.util.clsPolarcoordinate visionDir) {
		clsEntity oEntity = getEntity(visionObj);
		if (oEntity == null) {
			return null;
		}

		clsVisionEntry oData = new clsVisionEntry();
		
		oData.moEntityType = getEntityType(visionObj);		
		oData.moPolarcoordinate = new clsPolarcoordinate(visionDir.mrLength, visionDir.moAzimuth.radians);
		
	
		oData.moEntityId = oEntity.getId();
		
		// TODO Auto-generated method stub
		return oData;
	}

	private clsEatableArea convertEatAbleAreaSensor() {
		clsEatableArea oData = new clsEatableArea();
		
		clsSensorEatableArea oEatableSensor = (clsSensorEatableArea) moSensorsExt.get(eSensorExtType.EATABLE_AREA);
		
		Iterator<Integer> i = oEatableSensor.getViewObj().keySet().iterator();
		
		if (i.hasNext()) {
			Integer oKey = i.next();
			oData.mnNumEntitiesPresent = oEatableSensor.getViewObj().size();
			oData.mnTypeOfFirstEntity = getEntityType( oEatableSensor.getViewObj().get(oKey) );
		}
			
		return oData;
	}
	
	private  enums.eEntityType getEntityType(PhysicalObject2D poObject) {
		clsEntity oEntity = getEntity(poObject);
		
		if (oEntity != null) {
		  return convertEntityType( getEntity(poObject).getEntityType() );
		} else {
			return enums.eEntityType.UNDEFINED;
		}
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
	
	private enums.eEntityType convertEntityType(bw.utils.enums.eEntityType peType) {
		enums.eEntityType eResult = enums.eEntityType.UNDEFINED;
		switch(peType) {
			case ANIMAL: eResult = enums.eEntityType.ANIMAL; break;
			case BOT: eResult = enums.eEntityType.BOT; break;
			case BUBBLE: eResult = enums.eEntityType.BUBBLE; break;
			case PLANT: eResult = enums.eEntityType.PLANT; break;
			case REMOTEBOT: eResult = enums.eEntityType.REMOTEBOT; break;
			case CAN: eResult = enums.eEntityType.CAN; break;
			case CAKE: eResult = enums.eEntityType.CAKE; break;
			case STONE: eResult = enums.eEntityType.STONE; break;
			case WALL: eResult = enums.eEntityType.WALL; break;
			case FOOD: eResult = enums.eEntityType.FOOD; break;
		}

		return eResult;
	}

	private clsBump convertBumpSensor() {
		clsBump oData = new clsBump();
		
		clsSensorBump oBumpSensor = (clsSensorBump) moSensorsExt.get(eSensorExtType.BUMP);	
		oData.mnBumped = oBumpSensor.isBumped();

		return oData;
	}

	/* **************************************************** CONVERT ACTION DATA *********************************************** */
	private clsBrainActionContainer convertActionCommands(clsActionCommandContainer poCommands) {
		clsBrainActionContainer oBrainActions = new clsBrainActionContainer();
		
		if (poCommands != null) {
		  convertEatActions(oBrainActions, poCommands.getEatAction());
		  convertMoveActions(oBrainActions, poCommands.getMoveAction());
		}
		
		return oBrainActions;
	}
	
	
	private void convertMoveActions(clsBrainActionContainer brainActions, ArrayList<clsMotionAction> moveAction) {
		for (clsMotionAction oAction: moveAction) {
			bw.utils.enums.eActionCommandMotion eMotion = eActionCommandMotion.UNDEFINED;
			
			switch (oAction.meMotionType) {
			   case UNDEFINED: eMotion = eActionCommandMotion.UNDEFINED;break;
			   case MOVE_FORWARD: eMotion = eActionCommandMotion.MOVE_FORWARD;break;
			   case MOVE_LEFT: eMotion = eActionCommandMotion.MOVE_LEFT;break;
			   case MOVE_RIGHT: eMotion = eActionCommandMotion.MOVE_RIGHT;break;
			   case MOVE_DIRECTION: eMotion = eActionCommandMotion.MOVE_DIRECTION;break;
			   case MOVE_BACKWARD: eMotion = eActionCommandMotion.MOVE_BACKWARD;break;
			   case ROTATE_LEFT: eMotion = eActionCommandMotion.ROTATE_LEFT;break;
			   case ROTATE_RIGHT: eMotion = eActionCommandMotion.ROTATE_RIGHT;break;
			   case RUN_FORWARD: eMotion = eActionCommandMotion.RUN_FORWARD;break;
			   case JUMP: eMotion = eActionCommandMotion.JUMP;break;
			}
			bw.body.motionplatform.clsMotionAction oMotionAction = bw.body.motionplatform.clsMotionAction.creatAction(eMotion);
			brainActions.addMoveAction(oMotionAction);
		}
	}

	private void convertEatActions(clsBrainActionContainer brainActions, ArrayList<clsEatAction> eatAction) {
		clsEntity oEntity = null;
		clsSensorEatableArea oEatableSensor = (clsSensorEatableArea) moSensorsExt.get(eSensorExtType.EATABLE_AREA);
		
		Iterator<Integer> i = oEatableSensor.getViewObj().keySet().iterator();
		
		if (i.hasNext()) {
			Integer oKey = i.next();
			oEntity = getEntity( oEatableSensor.getViewObj().get(oKey) );
		}
		
		for (@SuppressWarnings("unused") clsEatAction oAction: eatAction) {
			bw.body.motionplatform.clsEatAction oEatAction = new bw.body.motionplatform.clsEatAction(eActionCommandType.EAT, oEntity);
			brainActions.addEatAction(oEatAction);
		}
	}

	/*************************************************
	 *         GETTER & SETTER
	 ************************************************/

	public clsBaseDecisionUnit getDecisionUnit() {
		return moDecisionUnit;		
	}

	public void setDecisionUnit(clsBaseDecisionUnit poDecisionUnit) {
		moDecisionUnit = poDecisionUnit;
	}
	

}
