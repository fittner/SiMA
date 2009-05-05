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
import enums.eSensorIntType;
import enums.eSensorExtType;
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
import bw.utils.container.clsConfigContainer;
import enums.eActionCommandMotion;
import enums.eActionCommandType;
import enums.eEntityType;
import enums.eSensorExtType;

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
    
    private clsConfigContainer moConfig;	
	
	public clsBrainSocket(HashMap<eSensorExtType, clsSensorExt> poSensorsExt, HashMap<eSensorIntType, clsSensorInt> poSensorsInt, clsConfigContainer poConfig) {
		moConfig = getDefaultConfig();
		moConfig.overwritewith(poConfig);
				
		moSensorsExt = poSensorsExt;
		moSensorsInt = poSensorsInt;
	}

	private clsConfigContainer getDefaultConfig() {
		clsConfigContainer oDefault = new clsConfigContainer();
		//TODO add default values
		return oDefault;
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
		
		oData.addSensor(eSensorExtType.BUMP, convertBumpSensor() );
		oData.addSensor(eSensorExtType.EATABLE_AREA, convertEatAbleAreaSensor() );
		oData.addSensor(eSensorExtType.VISION, converVisionSensor() );
		
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
		
		oData.mnEntityType = getEntityType(visionObj);		
		oData.moPolarcoordinate = new clsPolarcoordinate(visionDir.mrLength, visionDir.moAzimuth.radians);
		
	
		oData.moEntityId = oEntity.getId();
		
		// TODO Auto-generated method stub
		return oData;
	}

	private clsEatableArea convertEatAbleAreaSensor() {
		clsEatableArea oData = new clsEatableArea();
		
		clsSensorEatableArea oEatableSensor = (clsSensorEatableArea) moSensorsExt.get(eSensorExtType.EATABLE_AREA);
		
		Iterator<Integer> i = oEatableSensor.getViewObj().keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			oData.mnNumEntitiesPresent = oEatableSensor.getViewObj().size();
			oData.mnTypeOfFirstEntity = getEntityType( oEatableSensor.getViewObj().get(oKey) );
			
			if (oData.mnTypeOfFirstEntity != eEntityType.UNDEFINED) {
				break;
			}
		}
			
		return oData;
	}
	
	private  enums.eEntityType getEntityType(PhysicalObject2D poObject) {
		clsEntity oEntity = getEntity(poObject);
		
		if (oEntity != null) {
		  return getEntity(poObject).getEntityType();
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
			bw.body.motionplatform.clsMotionAction oMotionAction = bw.body.motionplatform.clsMotionAction.creatAction(oAction.meMotionType);
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
