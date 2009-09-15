/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.brainsocket;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import config.clsBWProperties;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.*;
import sim.physics2D.util.Angle;
import simple.remotecontrol.clsRemoteControl;

import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.itfActionProcessor;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsEnergyConsumption;
import decisionunit.itf.sensors.clsFastMessenger;
import decisionunit.itf.sensors.clsHealthSystem;
import decisionunit.itf.sensors.clsSensorManipulateArea;
import decisionunit.itf.sensors.clsStomachTension;
import decisionunit.itf.sensors.clsTemperatureSystem;
import decisionunit.itf.sensors.clsPositionChange;
import decisionunit.itf.sensors.clsSensorData;
import decisionunit.itf.sensors.clsStaminaSystem;
import decisionunit.itf.sensors.clsEnergy;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsRadiation;
import decisionunit.itf.sensors.clsVisionEntry;
import enums.eFastMessengerSources;
import enums.eSensorIntType;
import enums.eSensorExtType;
import ARSsim.physics2D.physicalObject.clsCollidingObject;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import ARSsim.physics2D.util.clsPolarcoordinate;
import bw.body.itfStepProcessing;

import bw.body.io.sensors.ext.clsSensorVision;
import bw.body.io.sensors.ext.clsSensorEatableArea;
import bw.body.io.sensors.ext.clsSensorBump;
import bw.body.io.sensors.ext.clsSensorPositionChange;
import bw.body.io.sensors.ext.clsSensorRadiation;

import bw.body.internalSystems.clsFastMessengerEntry;
import bw.body.io.sensors.ext.clsSensorExt;
import bw.body.io.sensors.internal.clsEnergyConsumptionSensor;
import bw.body.io.sensors.internal.clsEnergySensor;
import bw.body.io.sensors.internal.clsFastMessengerSensor;
import bw.body.io.sensors.internal.clsHealthSensor;
import bw.body.io.sensors.internal.clsStomachTensionSensor;
import bw.body.io.sensors.internal.clsTemperatureSensor;
import bw.body.io.sensors.internal.clsSensorInt;
import bw.body.io.sensors.internal.clsStaminaSensor;
import bw.entities.clsEntity;
import bw.entities.clsAnimal;
//import bw.entities.clsUraniumOre;
import bw.utils.sensors.clsSensorDataCalculation;
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
	private clsSensorDataCalculation moSensorCalculation;
	
	public clsBrainSocket(String poPrefix, clsBWProperties poProp, HashMap<eSensorExtType, clsSensorExt> poSensorsExt, HashMap<eSensorIntType, clsSensorInt> poSensorsInt, itfActionProcessor poActionProcessor) {
		moActionProcessor=poActionProcessor;
		moSensorsExt = poSensorsExt;
		moSensorsInt = poSensorsInt;
		moSensorCalculation = new clsSensorDataCalculation();
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
			moDecisionUnit.process();
			moDecisionUnit.updateActionProcessorToHTML();
		} 
	}
	
	/* **************************************************** CONVERT SENSOR DATA *********************************************** */
	private clsSensorData convertSensorData() {
		clsSensorData oData = new clsSensorData();
		
		//ZEILINGER - Integration of the Sensor Engine
		oData.addSensorExt(eSensorExtType.BUMP, convertBumpSensor() );
		oData.addSensorExt(eSensorExtType.POSITIONCHANGE, convertPositionChangeSensor() );
		oData.addSensorExt(eSensorExtType.RADIATION, convertRadiationSensor() );
		oData.addSensorExt(eSensorExtType.VISION, convertVisionSensor(eSensorExtType.VISION) );
		oData.addSensorExt(eSensorExtType.VISION_NEAR, convertVisionSensor(eSensorExtType.VISION_NEAR) );
		oData.addSensorExt(eSensorExtType.VISION_MEDIUM, convertVisionSensor(eSensorExtType.VISION_MEDIUM) );
		oData.addSensorExt(eSensorExtType.VISION_FAR, convertVisionSensor(eSensorExtType.VISION_FAR) );
		oData.addSensorExt(eSensorExtType.EATABLE_AREA, convertEatAbleAreaSensor() );
		oData.addSensorExt(eSensorExtType.MANIPULATE_AREA, convertManipulateSensor(eSensorExtType.MANIPULATE_AREA) );
		//ad homeostasis sensor data
		oData.addSensorInt(eSensorIntType.ENERGY_CONSUMPTION, convertEnergySystem() );
		oData.addSensorInt(eSensorIntType.HEALTH, convertHealthSystem() );
		oData.addSensorInt(eSensorIntType.STAMINA, convertStaminaSystem() );
		oData.addSensorInt(eSensorIntType.ENERGY, convertStomachSystem_Energy() );
		oData.addSensorInt(eSensorIntType.STOMACHTENSION, convertStomachSystem_Tension() );
		oData.addSensorInt(eSensorIntType.TEMPERATURE, convertTemperatureSystem() );
		oData.addSensorInt(eSensorIntType.FASTMESSENGER, convertFastMessengerSystem() );
		
		return oData;
	}
	
	private clsDataBase convertFastMessengerSystem() {

		clsFastMessenger oRetVal = new clsFastMessenger();
		clsFastMessengerSensor oSensor = (clsFastMessengerSensor)(moSensorsInt.get(eSensorIntType.FASTMESSENGER));

		if (oSensor.getFastMessages() != null) {
			for(clsFastMessengerEntry oEntry:oSensor.getFastMessages()) {
				oRetVal.add( convertFastMessengerEntry(oEntry) );
			}
		}
		
		return oRetVal;
	}
	
	private decisionunit.itf.sensors.clsFastMessengerEntry convertFastMessengerEntry(bw.body.internalSystems.clsFastMessengerEntry poEntry) {
		eFastMessengerSources oSource;

		switch(poEntry.getSource()) {
			case INTRA_DAMAGE_NUTRITION:oSource=eFastMessengerSources.STOMACH;
				break;
			
			case INTER_DAMAGE_BUMP:;
			case SENSOR_EXT_TACTILE_BUMP:;
			case SENSOR_EXT_TACTITLE:oSource=eFastMessengerSources.BUMP;
				break;
			
			case INTRA_DAMAGE_TEMPERATURE:oSource=eFastMessengerSources.TEMPERATURE;
				break;
			
			case INTER_DAMAGE_LIGHTNING:oSource=eFastMessengerSources.LIGHTNING;
				break;
			
			case ACTIONEX_ATTACKBITE:oSource=eFastMessengerSources.MANIPULATION_AREA;
				break;
			
			case ACTIONEX_EAT:oSource=eFastMessengerSources.EATABLE_AREA;
				break;
			
			default:throw new java.lang.NullPointerException("unkown fast messenger source: "+poEntry.getSource());
		}
		
		decisionunit.itf.sensors.clsFastMessengerEntry oRes = new decisionunit.itf.sensors.clsFastMessengerEntry(oSource, poEntry.getIntensity());
		return oRes;
	}
	
	private clsPositionChange convertPositionChangeSensor() {
		clsPositionChange oData = new clsPositionChange();
		
		clsSensorPositionChange oSensor = (clsSensorPositionChange)(moSensorsExt.get(eSensorExtType.POSITIONCHANGE));
		
		oData.x = oSensor.getPositionChange().getPosition().x;
		oData.y = oSensor.getPositionChange().getPosition().y;
		oData.a = oSensor.getPositionChange().getAngle().radians;
		
		return oData;
	}
	
	private clsDataBase convertStomachSystem_Energy() {

		clsEnergy oRetVal = new clsEnergy();
		clsEnergySensor oStomachSensor = (clsEnergySensor)(moSensorsInt.get(eSensorIntType.ENERGY));

		oRetVal.mrEnergy = oStomachSensor.getEnergy();
		
		return oRetVal;
	}
	
	private clsDataBase convertStomachSystem_Tension() {

		clsStomachTension oRetVal = new clsStomachTension();
		clsStomachTensionSensor oStomachSensor = (clsStomachTensionSensor)(moSensorsInt.get(eSensorIntType.STOMACHTENSION));

		oRetVal.mrTension = oStomachSensor.getTension();
		
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


   //ZEILINGER Integration of the SensorEngine
	private clsVision convertVisionSensor(eSensorExtType poVisionType) {
		clsVision oData = new clsVision();
		oData.moSensorType = poVisionType;
		clsSensorVision oVision = (clsSensorVision)(moSensorsExt.get(poVisionType));
		if(oVision != null) {
			ArrayList<clsCollidingObject> eDetectedObjectList = oVision.getSensorData();
	
			Iterator <clsCollidingObject> i = eDetectedObjectList.iterator(); 
			while(i.hasNext()){
				clsVisionEntry oEntry = convertVisionEntry(i.next());
				
				if (oEntry != null) {
					oData.add(oEntry);
				}	
			}
		}
		return oData;
	}
	
	   //ZEILINGER Integration of the SensorEngine
	private clsSensorManipulateArea convertManipulateSensor(eSensorExtType poVisionType) {
		clsSensorManipulateArea oData = new clsSensorManipulateArea();
		oData.moSensorType = poVisionType;
		bw.body.io.sensors.ext.clsSensorManipulateArea oManip = (bw.body.io.sensors.ext.clsSensorManipulateArea)(moSensorsExt.get(poVisionType));
		if(oManip != null) {
			ArrayList<clsCollidingObject> eDetectedObjectList = oManip.getSensorData();
	
			Iterator <clsCollidingObject> i = eDetectedObjectList.iterator(); 
			while(i.hasNext()){
				clsVisionEntry oEntry = convertVisionEntry(i.next());
				
				if (oEntry != null) {
					oData.add(oEntry);
				}	
			}
		}
		return oData;
	}

	private clsRadiation convertRadiationSensor() {
		clsRadiation oData = new clsRadiation();
    	clsSensorRadiation oRadiationSensor = (clsSensorRadiation) moSensorsExt.get(eSensorExtType.RADIATION);
		
//		Iterator<Integer> i = oRadiationSensor.getViewObj().keySet().iterator();
//		
//		while (i.hasNext()) {
//			Integer oKey = i.next();
//			oData.mnNumEntitiesPresent = oRadiationSensor.getViewObj().size();
//			oData.mnTypeOfFirstEntity = getEntityType( oRadiationSensor.getViewObj().get(oKey) );
//			
//			if (oData.mnTypeOfFirstEntity != eEntityType.UNDEFINED) {
//				break;
//			}
//		}
	
		oRadiationSensor.updateSensorData();		
		oData.mrIntensity = oRadiationSensor.getMrRadiation();		

		return oData;
	}
	
	

	private clsVisionEntry convertVisionEntry(clsCollidingObject collidingObj) {
		clsEntity oEntity = getEntity(collidingObj.moCollider);
		if (oEntity == null) {
			return null;
		}

		clsVisionEntry oData = new clsVisionEntry();
		
		oData.mnEntityType = getEntityType(collidingObj.moCollider);		
		oData.mnShapeType = getShapeType(collidingObj.moCollider);
		oData.moColor = (Color) oEntity.getShape().getPaint();
		
		// FIXME: (horvath) - temporary polar coordinates calculation
		clsSensorPositionChange oSensor = (clsSensorPositionChange)(moSensorsExt.get(eSensorExtType.POSITIONCHANGE));
		clsPolarcoordinate oRel = collidingObj.mrColPoint;
		oRel.moAzimuth = new Angle(moSensorCalculation.normalizeRadian(oRel.moAzimuth.radians - oSensor.getLastPosition().getAngle().radians));
				
		oData.moPolarcoordinate = new bfg.tools.shapes.clsPolarcoordinate(oRel.mrLength,oRel.moAzimuth.radians);
		
		if( oEntity instanceof clsAnimal )
		{
			oData.mnAlive = ((clsAnimal)oEntity).isAlive();
		}

		//oData.moEntityId = oEntity.getUniqueId();
		
		return oData;
	}



	//ZEILINGER Integration of the SensorEngine
	private clsEatableArea convertEatAbleAreaSensor() {
		clsEatableArea oData = new clsEatableArea();
		
		clsSensorEatableArea oEatableSensor = (clsSensorEatableArea) moSensorsExt.get(eSensorExtType.EATABLE_AREA);
		ArrayList<clsCollidingObject> eDetectedObjectList = oEatableSensor.getSensorData();
		
		Iterator<clsCollidingObject> i = eDetectedObjectList.iterator();
		
		while (i.hasNext()) {
			PhysicalObject2D oCollider = i.next().moCollider; 
			oData.mnNumEntitiesPresent = eDetectedObjectList.size();
			oData.mnTypeOfFirstEntity = getEntityType(oCollider);
			oData.moColorOfFirstEntity = getEntityColor(oCollider);
						
			if (oData.mnTypeOfFirstEntity != eEntityType.UNDEFINED) {
				break;
			}
		}
				
		return oData;
	}
	
	private  Color getEntityColor(PhysicalObject2D poObject) {
		clsEntity oEntity = getEntity(poObject);
		
		if (oEntity != null) {
		  return  (Color) oEntity.getShape().getPaint();
		} else {
			return null;
		}
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
		moDecisionUnit.setActionProcessor(moActionProcessor);
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 04.08.2009, 12:01:32
	 *
	 * @param keyPressed
	 */
	public void setKeyPressed(int keyPressed) {
		if( moDecisionUnit instanceof clsRemoteControl) {
			((clsRemoteControl) moDecisionUnit).setKeyPressed(keyPressed);
		}
		
	}
}
