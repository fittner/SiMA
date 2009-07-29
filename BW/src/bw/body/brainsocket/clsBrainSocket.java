/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.brainsocket;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.*;

import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.itfActionProcessor;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsEnergyConsumption;
import decisionunit.itf.sensors.clsHealthSystem;
import decisionunit.itf.sensors.clsTemperatureSystem;
import decisionunit.itf.sensors.clsPositionChange;
import decisionunit.itf.sensors.clsSensorData;
import decisionunit.itf.sensors.clsStaminaSystem;
import decisionunit.itf.sensors.clsStomachSystem;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsRadiation;
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
import bw.body.io.sensors.external.clsSensorPositionChange;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.io.sensors.external.clsSensorRadiation;
import bw.body.io.sensors.internal.clsEnergyConsumptionSensor;
import bw.body.io.sensors.internal.clsHealthSensor;
import bw.body.io.sensors.internal.clsTemperatureSensor;
import bw.body.io.sensors.internal.clsSensorInt;
import bw.body.io.sensors.internal.clsStaminaSensor;
import bw.body.io.sensors.internal.clsStomachSensor;
import bw.entities.clsEntity;
import bw.entities.clsAnimal;
import bw.utils.config.clsBWProperties;
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

	private clsBaseDecisionUnit moDecisionUnit; //reference
	private itfActionProcessor moActionProcessor; //reference
	private HashMap<eSensorExtType, clsSensorExt> moSensorsExt; //reference
	private HashMap<eSensorIntType, clsSensorInt> moSensorsInt; //reference
	
	public clsBrainSocket(String poPrefix, clsBWProperties poProp, HashMap<eSensorExtType, clsSensorExt> poSensorsExt, HashMap<eSensorIntType, clsSensorInt> poSensorsInt, itfActionProcessor poActionProcessor) {
		moActionProcessor=poActionProcessor;
		moSensorsExt = poSensorsExt;
		moSensorsInt = poSensorsInt;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

		//nothing to do
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
		oData.addSensorExt(eSensorExtType.VISION, convertVisionSensor() );
		oData.addSensorExt(eSensorExtType.RADIATION, convertRadiationSensor() );
		
		//ad homeostasis sensor data
		oData.addSensorInt(eSensorIntType.ENERGY_CONSUMPTION, convertEnergySystem() );
		oData.addSensorInt(eSensorIntType.HEALTH, convertHealthSystem() );
		oData.addSensorInt(eSensorIntType.STAMINA, convertStaminaSystem() );
		oData.addSensorInt(eSensorIntType.STOMACH, convertStomachSystem() );
		oData.addSensorInt(eSensorIntType.TEMPERATURE, convertTemperatureSystem() );
		
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
	 * DOCUMENT (langr) - insert description
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
	 * DOCUMENT (langr) - insert description
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
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 11.05.2009, 17:44:01
	 *
	 * @return
	 */
	private clsDataBase convertHealthSystem() {
		
		clsHealthSystem oRetVal = new clsHealthSystem();
		clsHealthSensor oHealthSensor = (clsHealthSensor)(moSensorsInt.get(eSensorIntType.HEALTH));

		oRetVal.mrHealthValue = oHealthSensor.getHealthValue();
		
		return oRetVal;	
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 11.05.2009, 17:44:01
	 *
	 * @return
	 */
	private clsDataBase convertTemperatureSystem() {
		
		clsTemperatureSystem oRetVal = new clsTemperatureSystem();
		clsTemperatureSensor oTemperatureSensor = (clsTemperatureSensor)(moSensorsInt.get(eSensorIntType.TEMPERATURE));

		oRetVal.mrTemperatureValue = oTemperatureSensor.getTemperatureValue();
		
		return oRetVal;	
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 11.05.2009, 17:43:58
	 *
	 * @return
	 */
	private clsDataBase convertEnergySystem() {

		clsEnergyConsumption oRetVal = new clsEnergyConsumption();
		clsEnergyConsumptionSensor oEnergySensor = (clsEnergyConsumptionSensor)(moSensorsInt.get(eSensorIntType.ENERGY_CONSUMPTION));

		oRetVal.mrEnergy = oEnergySensor.getEnergy();
		
		return oRetVal;
		
	}

	private clsVision convertVisionSensor() {
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
	
	private clsRadiation convertRadiationSensor() {
		clsRadiation oData = new clsRadiation();
		
		clsSensorRadiation oRadiationSensor = (clsSensorRadiation) moSensorsExt.get(eSensorExtType.RADIATION);
		
		Iterator<Integer> i = oRadiationSensor.getViewObj().keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			oData.mnNumEntitiesPresent = oRadiationSensor.getViewObj().size();
			oData.mnTypeOfFirstEntity = getEntityType( oRadiationSensor.getViewObj().get(oKey) );
			
			if (oData.mnTypeOfFirstEntity != eEntityType.UNDEFINED) {
				break;
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
		oData.mnShapeType = getShapeType(visionObj);
		oData.moPolarcoordinate = new clsPolarcoordinate(visionDir.mrLength, visionDir.moAzimuth.radians);
		oData.moColor = (Color) oEntity.getShape().getPaint();
		
		if( oEntity instanceof clsAnimal )
		{
			oData.mnAlive = ((clsAnimal)oEntity).isAlive();
		}
		
	
		oData.moEntityId = oEntity.getId();
		
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
	
	private  enums.eShapeType getShapeType(PhysicalObject2D poObject) {
		
		if (poObject.getShape() instanceof  Circle) {
			return enums.eShapeType.CIRCLE;
		}else if(poObject.getShape() instanceof  Rectangle){
			return enums.eShapeType.SQUARE;
		} else {
			return enums.eShapeType.UNDEFINED;
		}
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
