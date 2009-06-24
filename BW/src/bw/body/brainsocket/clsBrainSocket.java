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
import decisionunit.itf.actions.itfActionProcessor;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsEnergyConsumption;
import decisionunit.itf.sensors.clsHealthSystem;
import decisionunit.itf.sensors.clsPositionChange;
import decisionunit.itf.sensors.clsSensorData;
import decisionunit.itf.sensors.clsStaminaSystem;
import decisionunit.itf.sensors.clsStomachSystem;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsVisionEntry;
import enums.eSensorIntType;
import enums.eSensorExtType;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import ARSsim.physics2D.util.clsPose;
import bfg.tools.shapes.clsPolarcoordinate;
import bw.body.itfStepProcessing;
import bw.body.io.sensors.external.clsSensorBump;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorExt;
import bw.body.io.sensors.external.clsSensorPositionChange;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.io.sensors.internal.clsEnergySensor;
import bw.body.io.sensors.internal.clsHealthSensor;
import bw.body.io.sensors.internal.clsSensorInt;
import bw.body.io.sensors.internal.clsStaminaSensor;
import bw.body.io.sensors.internal.clsStomachSensor;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigMap;
import enums.eEntityType;

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
	private itfActionProcessor moActionProcessor;
	private HashMap<eSensorExtType, clsSensorExt> moSensorsExt;
	private HashMap<eSensorIntType, clsSensorInt> moSensorsInt;
    
    private clsConfigMap moConfig;	
	
	public clsBrainSocket(HashMap<eSensorExtType, clsSensorExt> poSensorsExt, HashMap<eSensorIntType, clsSensorInt> poSensorsInt, itfActionProcessor poActionProcessor, clsConfigMap poConfig) {
		moConfig = getDefaultConfig();
		moConfig.overwritewith(poConfig);
				
		moActionProcessor=poActionProcessor;
		moSensorsExt = poSensorsExt;
		moSensorsInt = poSensorsInt;
	}

	private clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		//TODO add default values
		return oDefault;
	}	
		
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
//	public void stepProcessing(clsAnimate poAnimate, clsBrainActionContainer poActionList) {
	public void stepProcessing() {
		if (moDecisionUnit != null) {
			moDecisionUnit.update(convertSensorData());
			moDecisionUnit.process(moActionProcessor);
		} 
	}
	
	/* **************************************************** CONVERT SENSOR DATA *********************************************** */
	private clsSensorData convertSensorData() {
		clsSensorData oData = new clsSensorData();
		
		oData.addSensorExt(eSensorExtType.BUMP, convertBumpSensor() );
		oData.addSensorExt(eSensorExtType.POSITIONCHANGE, convertPositionChangeSensor() );
		oData.addSensorExt(eSensorExtType.EATABLE_AREA, convertEatAbleAreaSensor() );
		oData.addSensorExt(eSensorExtType.VISION, converVisionSensor() );
		
		//ad homeostasis sensor data
		oData.addSensorInt(eSensorIntType.ENERGY_CONSUMPTION, convertEnergySystem() );
		oData.addSensorInt(eSensorIntType.HEALTH_SYSTEM, convertHealthSystem() );
		oData.addSensorInt(eSensorIntType.STAMINA, convertStaminaSystem() );
		oData.addSensorInt(eSensorIntType.STOMACH, convertStomachSystem() );
		
		return oData;
	}
	
	private clsPositionChange convertPositionChangeSensor() {
		clsPositionChange oData = new clsPositionChange();
		
		clsSensorPositionChange oSensor = (clsSensorPositionChange)(moSensorsExt.get(eSensorExtType.POSITIONCHANGE));
		
		oData.x = oSensor.getPositionChange().getPosition().x;
		oData.y = oSensor.getPositionChange().getPosition().y;
		oData.a = oSensor.getPositionChange().getAngle().radians;
		
		return oData;
	}
	
	/**
	 * TODO (langr) - insert description
	 *
	 * @author langr
	 * 11.05.2009, 17:44:19
	 *
	 * @return
	 */
	private clsDataBase convertStomachSystem() {

		clsStomachSystem oRetVal = new clsStomachSystem();
		clsStomachSensor oStomachSensor = (clsStomachSensor)(moSensorsInt.get(eSensorIntType.STOMACH));

		oRetVal.mrEnergy = oStomachSensor.getEnergy();
		
		return oRetVal;
	}

	/**
	 * TODO (langr) - insert description
	 *
	 * @author langr
	 * 11.05.2009, 17:44:15
	 *
	 * @return
	 */
	private clsDataBase convertStaminaSystem() {
		
		clsStaminaSystem oRetVal = new clsStaminaSystem();
		clsStaminaSensor oStaminaSensor = (clsStaminaSensor)(moSensorsInt.get(eSensorIntType.STAMINA));

		oRetVal.mrStaminaValue = oStaminaSensor.getStaminaValue();
		
		return oRetVal;	
	}

	/**
	 * TODO (langr) - insert description
	 *
	 * @author langr
	 * 11.05.2009, 17:44:01
	 *
	 * @return
	 */
	private clsDataBase convertHealthSystem() {
		
		clsHealthSystem oRetVal = new clsHealthSystem();
		clsHealthSensor oHealthSensor = (clsHealthSensor)(moSensorsInt.get(eSensorIntType.HEALTH_SYSTEM));

		oRetVal.mrHealthValue = oHealthSensor.getHealthValue();
		
		return oRetVal;	
	}

	/**
	 * TODO (langr) - insert description
	 *
	 * @author langr
	 * 11.05.2009, 17:43:58
	 *
	 * @return
	 */
	private clsDataBase convertEnergySystem() {

		clsEnergyConsumption oRetVal = new clsEnergyConsumption();
		clsEnergySensor oEnergySensor = (clsEnergySensor)(moSensorsInt.get(eSensorIntType.ENERGY_CONSUMPTION));

		oRetVal.mrEnergy = oEnergySensor.getEnergy();
		
		return oRetVal;
		
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
