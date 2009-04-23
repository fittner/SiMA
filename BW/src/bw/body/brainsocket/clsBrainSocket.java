/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.brainsocket;

import java.util.HashMap;
import java.util.Iterator;

import sim.physics2D.physicalObject.PhysicalObject2D;

import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.clsActionCommands;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsSensorData;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsVisionEntry;
import decisionunit.itf.sensors.eSensorType;
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
import bw.utils.enums.eSensorExtType;
import bw.utils.enums.eSensorIntType;

/**
 * The brain is the container for the mind and has a direct connection to external and internal IO.
 * Done: re-think if we insert a clsCerebellum for the neuroscientific perception-modules like R. Velik.
 * Answer: moSymbolization
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
			clsActionCommands oDecisionUnitResult = moDecisionUnit.process();

			return convertActionCommands(oDecisionUnitResult);
		} else {
			return new clsBrainActionContainer();
		}
	}
	
	private clsSensorData convertSensorData() {
		clsSensorData oData = new clsSensorData();
		
		oData.addSensor(eSensorType.BUMP, convertBumpSensor() );
		oData.addSensor(eSensorType.EATABLE_AREA, convertEatAbleAreaSensor() );
		oData.addSensor(eSensorType.VISION, converVisionSensor() );
		
		return null;
	}
	
	private clsVision converVisionSensor() {
		clsVision oData = new clsVision();
		
		clsSensorVision oVision = (clsSensorVision)(moSensorsExt.get(eSensorExtType.VISION));
		
		Iterator<Integer> i = oVision.getViewObj().keySet().iterator();
		while (i.hasNext()) {
			Integer oKey = i.next();
			PhysicalObject2D visionObj = oVision.getViewObj().get(oKey);
			ARSsim.physics2D.util.clsPolarcoordinate visionDir = oVision.getViewObjDir().get(oKey);
			convertVisionEntry(visionObj, visionDir);
		}
		
		return oData;
	}

	private clsVisionEntry convertVisionEntry(PhysicalObject2D visionObj, ARSsim.physics2D.util.clsPolarcoordinate visionDir) {
		clsVisionEntry oData = new clsVisionEntry();
		
		oData.moEntityType = getEntityType(visionObj);		
		oData.moPolarcoordinate = new clsPolarcoordinate(visionDir.mrLength, visionDir.moAzimuth.radians);
		
		clsEntity oEntity = getEntity(visionObj);
		
		oData.moEntityId = oEntity.getId();
		
		// TODO Auto-generated method stub
		return oData;
	}

	private clsEatableArea convertEatAbleAreaSensor() {
		clsEatableArea oData = new clsEatableArea();
		
		clsSensorEatableArea oEatableSensor = (clsSensorEatableArea) moSensorsExt.get(eSensorExtType.EATABLE_AREA);
		
		if (oEatableSensor.getViewObj() != null) {
			oData.mnNumEntitiesPresent = oEatableSensor.getViewObj().size();
			oData.mnTypeOfFirstEntity = getEntityType( oEatableSensor.getViewObj().get(oEatableSensor.getViewObj().keySet().toArray()[0]) );
		}
			
		return oData;
	}
	
	private  decisionunit.itf.sensors.eEntityType getEntityType(PhysicalObject2D poObject) {	
		return convertEntityType( getEntity(poObject).getEntityType() );		
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
	
	private decisionunit.itf.sensors.eEntityType convertEntityType(bw.utils.enums.eEntityType peType) {
		decisionunit.itf.sensors.eEntityType eResult = decisionunit.itf.sensors.eEntityType.UNDEFINED;
		switch(peType) {
			case ANIMAL: eResult = decisionunit.itf.sensors.eEntityType.ANIMAL; break;
			case BOT: eResult = decisionunit.itf.sensors.eEntityType.BOT; break;
			case BUBBLE: eResult = decisionunit.itf.sensors.eEntityType.BUBBLE; break;
			case PLANT: eResult = decisionunit.itf.sensors.eEntityType.PLANT; break;
			case REMOTEBOT: eResult = decisionunit.itf.sensors.eEntityType.REMOTEBOT; break;
			case CAN: eResult = decisionunit.itf.sensors.eEntityType.CAN; break;
			case CAKE: eResult = decisionunit.itf.sensors.eEntityType.CAKE; break;
			case STONE: eResult = decisionunit.itf.sensors.eEntityType.STONE; break;
			case WALL: eResult = decisionunit.itf.sensors.eEntityType.WALL; break;
			case FOOD: eResult = decisionunit.itf.sensors.eEntityType.FOOD; break;
		}

		return eResult;
	}

	private clsBump convertBumpSensor() {
		clsBump oData = new clsBump();
		
		clsSensorBump oBumpSensor = (clsSensorBump) moSensorsExt.get(eSensorExtType.BUMP);	
		oData.mnBumped = oBumpSensor.isBumped();

		return oData;
	}

	private clsBrainActionContainer convertActionCommands(clsActionCommands poCommands) {
		return null;
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
