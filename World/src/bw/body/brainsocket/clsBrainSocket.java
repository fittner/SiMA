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
import java.util.Map;
import java.util.Vector;

import sim.field.grid.DoubleGrid2D;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Circle;
import sim.physics2D.shape.Rectangle;
import sim.physics2D.util.Angle;
import sim.util.Double2D;
import ARSsim.physics2D.physicalObject.clsCollidingObject;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.physicalObject.clsStationaryObject2D;
import ARSsim.physics2D.util.clsPolarcoordinate;
import bfg.utils.enums.eCount;
import bfg.utils.enums.ePercievedActionType;
import bfg.utils.enums.eSide;
import bw.body.itfStepProcessing;
import bw.body.attributes.clsAttributeAlive;
import bw.body.internalSystems.clsFastMessengerEntry;
import bw.body.io.actuators.actionExecutors.clsAction;
import bw.body.io.sensors.external.clsSensorAcoustic;
import bw.body.io.sensors.external.clsSensorBump;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorExt;
import bw.body.io.sensors.external.clsSensorManipulateArea;
import bw.body.io.sensors.external.clsSensorPositionChange;
import bw.body.io.sensors.external.clsSensorRadiation;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.io.sensors.internal.clsEnergyConsumptionSensor;
import bw.body.io.sensors.internal.clsEnergySensor;
import bw.body.io.sensors.internal.clsFastMessengerSensor;
import bw.body.io.sensors.internal.clsHealthSensor;
import bw.body.io.sensors.internal.clsIntestinePressureSensor;
import bw.body.io.sensors.internal.clsSensorInt;
import bw.body.io.sensors.internal.clsSlowMessengerSensor;
import bw.body.io.sensors.internal.clsStaminaSensor;
import bw.body.io.sensors.internal.clsStomachTensionSensor;
import bw.body.io.sensors.internal.clsTemperatureSensor;
import bw.entities.clsARSIN;
import bw.entities.clsAnimal;
import bw.entities.clsEntity;
import bw.entities.clsRemoteBot;
import bw.entities.clsSpeech;
import bw.factories.clsSingletonMasonGetter;
import bw.factories.clsSingletonProperties;
import bw.utils.enums.eBodyAttributes;
import bw.utils.sensors.clsSensorDataCalculation;
import config.clsProperties;
import du.enums.eAntennaPositions;
import du.enums.eEntityType;
import du.enums.eFastMessengerSources;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.enums.eSlowMessenger;
import du.itf.itfDecisionUnit;
import du.itf.itfProcessKeyPressed;
import du.itf.actions.itfActionProcessor;
import du.itf.actions.itfInternalActionProcessor;
import du.itf.sensors.clsAcoustic;
import du.itf.sensors.clsAcousticEntry;
import du.itf.sensors.clsBump;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsEatableArea;
import du.itf.sensors.clsEatableAreaEntry;
import du.itf.sensors.clsEnergy;
import du.itf.sensors.clsEnergyConsumption;
import du.itf.sensors.clsFastMessenger;
import du.itf.sensors.clsHealthSystem;
import du.itf.sensors.clsInspectorPerceptionItem;
import du.itf.sensors.clsIntestinePressure;
import du.itf.sensors.clsManipulateArea;
import du.itf.sensors.clsManipulateAreaEntry;
import du.itf.sensors.clsPositionChange;
import du.itf.sensors.clsRadiation;
import du.itf.sensors.clsSensorData;
import du.itf.sensors.clsSlowMessenger;
import du.itf.sensors.clsStaminaSystem;
import du.itf.sensors.clsStomachTension;
import du.itf.sensors.clsTemperatureSystem;
import du.itf.sensors.clsUnrealSensorValueVision;
import du.itf.sensors.clsVision;
import du.itf.sensors.clsVisionEntry;
import du.itf.tools.clsAbstractSpeech;

/**
 * The brain is the container for the mind and has a direct connection to external and internal IO.
 * Done: re-think if we insert a clsCerebellum for the neuroscientific perception-modules like R. Velik.
 * Answer: moSymbolization -> all done in another project
 * 
 * @author langr
 * 
 */
public class clsBrainSocket implements itfStepProcessing {
	
	private  final double _UNREAL_NEAR_DISTANCE = 250;
	private  final double _UNREAL_MEDIUM_DISTANCE = 500;
	//private  final double _UNREAL_FAR_DISTANCE =  750;
	public  final double _UNREAL_AREA_OF_VIEW_RADIANS = Math.PI/2;

	private itfDecisionUnit moDecisionUnit; //reference
	private itfActionProcessor moActionProcessor; //reference
	private itfInternalActionProcessor moInternalActionProcessor; //reference
	private HashMap<eSensorExtType, clsSensorExt> moSensorsExt; //reference
	private HashMap<eSensorIntType, clsSensorInt> moSensorsInt; //reference
//	private clsSensorDataCalculation moSensorCalculation;
	
	private Vector<clsUnrealSensorValueVision> moUnrealVisionValues;
	
	public clsBrainSocket(String poPrefix, clsProperties poProp, HashMap<eSensorExtType, clsSensorExt> poSensorsExt, HashMap<eSensorIntType, clsSensorInt> poSensorsInt, itfActionProcessor poActionProcessor, itfInternalActionProcessor poInternalActionProcessor) {
		moActionProcessor=poActionProcessor;
		moInternalActionProcessor=poInternalActionProcessor;
		moSensorsExt = poSensorsExt;
		moSensorsInt = poSensorsInt;
//		moSensorCalculation = new clsSensorDataCalculation();
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		// String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		//nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);

		//nothing to do
	}		
		
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
//	public void stepProcessing(clsAnimate poAnimate, clsBrainActionContainer poActionList) {
	@Override
	public void stepProcessing() {
		if (moDecisionUnit != null) {
			moDecisionUnit.update(convertSensorData());
			moDecisionUnit.process();
			if (clsSingletonProperties.useLogger()) {
				moDecisionUnit.updateActionLogger();
			}
			
			if(clsSingletonProperties.showArousalGridPortrayal()){
				ConvertSensorDataAndAddToArousalGrid(moDecisionUnit.getPerceptionInspectorData());
			}
			
			if(clsSingletonProperties.showTPMNetworkGrid()){
				ConvertTPMDataAndAddToTPMNetworkGrid(moDecisionUnit.getPerceptionInspectorData());
			}
			
			
		} 
	}
	
	/* **************************************************** CONVERT SENSOR DATA *********************************************** */
	private clsSensorData convertSensorData() {
		clsSensorData oData = new clsSensorData();
		
		//ZEILINGER - Integration of the Sensor Engine
		oData.addSensorExt(eSensorExtType.BUMP, convertBumpSensor() );
		oData.addSensorExt(eSensorExtType.POSITIONCHANGE, convertPositionChangeSensor(eSensorExtType.POSITIONCHANGE) );
		oData.addSensorExt(eSensorExtType.RADIATION, convertRadiationSensor() );
		//HZ VISION Sensor is required for lynx and hares setup; 
		oData.addSensorExt(eSensorExtType.VISION, convertVisionSensor(eSensorExtType.VISION) );
		oData.addSensorExt(eSensorExtType.VISION_NEAR, convertVisionSensor(eSensorExtType.VISION_NEAR) );
		oData.addSensorExt(eSensorExtType.VISION_MEDIUM, convertVisionSensor(eSensorExtType.VISION_MEDIUM) );
		oData.addSensorExt(eSensorExtType.VISION_FAR, convertVisionSensor(eSensorExtType.VISION_FAR) );
		oData.addSensorExt(eSensorExtType.VISION_SELF, convertVisionSensor(eSensorExtType.VISION_SELF) );
		oData.addSensorExt(eSensorExtType.ACOUSTIC, convertAcousticSensor(eSensorExtType.ACOUSTIC) ); // MW
		
		//oData.addSensorExt(eSensorExtType.EATABLE_AREA, convertEatAbleAreaSensor(eSensorExtType.EATABLE_AREA) );
		//oData.addSensorExt(eSensorExtType.MANIPULATE_AREA, convertManipulateSensor(eSensorExtType.MANIPULATE_AREA) );
		//ad homeostasis sensor data
		oData.addSensorInt(eSensorIntType.ENERGY_CONSUMPTION, convertEnergySystem() );
		oData.addSensorInt(eSensorIntType.HEALTH, convertHealthSystem() );
		oData.addSensorInt(eSensorIntType.STAMINA, convertStaminaSystem() );
		oData.addSensorInt(eSensorIntType.ENERGY, convertStomachSystem_Energy() );
		oData.addSensorInt(eSensorIntType.STOMACHTENSION, convertStomachSystem_Tension() );
		oData.addSensorInt(eSensorIntType.INTESTINEPRESSURE, convertStomachSystem_IntestineTension() );
		oData.addSensorInt(eSensorIntType.TEMPERATURE, convertTemperatureSystem() );
		oData.addSensorInt(eSensorIntType.FASTMESSENGER, convertFastMessengerSystem() );
		oData.addSensorInt(eSensorIntType.SLOWMESSENGER, convertSlowMessengerSystem() );
		
		//if there is external unreal date: fake it into the system
		if(moUnrealVisionValues!=null && !moUnrealVisionValues.isEmpty())
			processUnrealVision(moUnrealVisionValues, oData);
		
		
		
		return oData;
	}
	
	private void ConvertTPMDataAndAddToTPMNetworkGrid(HashMap<String, ArrayList<clsInspectorPerceptionItem>> poPerceptionInspectorData){
		if(poPerceptionInspectorData != null && poPerceptionInspectorData.containsKey("F14")){
			ArrayList<clsInspectorPerceptionItem> oF14Data = poPerceptionInspectorData.get("F14");
			
			clsInspectorPerceptionItem lastNode = null;
			
			for(clsInspectorPerceptionItem oItem: oF14Data){
				
					clsSingletonMasonGetter.getTPMNodeField().setObjectLocation(oItem, new Double2D(oItem.moExactX, oItem.moExactY ));
					clsSingletonMasonGetter.getTPMNetworkField().addNode(oItem);
					
					if(lastNode != null){
						clsSingletonMasonGetter.getTPMNetworkField().addEdge(oItem, lastNode, "test");
					}
	
					lastNode = oItem;
			}
		}
	}
	
	private void ConvertSensorDataAndAddToArousalGrid(HashMap<String, ArrayList<clsInspectorPerceptionItem>> poPerceptionInspectorData){
		
		//clsSingletonMasonGetter.setArousalGridEnvironment( clsSingletonMasonGetter.getArousalGridEnvironment().multiply(4) );
		
		clsSingletonMasonGetter.getArousalGridEnvironment().add(-0.05);
		
		if(poPerceptionInspectorData != null && poPerceptionInspectorData.containsKey("F14")){
			ArrayList<clsInspectorPerceptionItem> oF14Data = poPerceptionInspectorData.get("F14");
			
			for(clsInspectorPerceptionItem oItem: oF14Data){
				
				int posX = (int)oItem.moExactX;
				int posY = (int)oItem.moExactY;
				
				if(posX < 0)
					posX = 0;
				
				if(posY < 0)
					posY = 0;
				
				double sensorArousal = (double)oItem.moSensorArousal;
				
				if(sensorArousal < 0)
					sensorArousal = 0;
				
				SetDifusedArousalGridDate(posX, posY, sensorArousal);
			}
		}
	}
	
	private void SetDifusedArousalGridDate(int X, int Y, double value){

			
			int dotSize = 26;
			
			DoubleGrid2D arousalGrid = clsSingletonMasonGetter.getArousalGridEnvironment();
						
			for(int x=X-dotSize/2;x< X+dotSize/2;x++){
                for(int y=Y-dotSize/2;y< Y+dotSize/2;y++){
                	if(x<0 || y<0){
                		//do nothing
                	}
                	else if(x>arousalGrid.getWidth()-1 || y>arousalGrid.getHeight()-1){
                		//do nothing
                	}
                	else
                	{
                		//distance to dot center
                		int dx = X - x;
                		int dy = Y - y;
                		double distancetospot = Math.sqrt(dx*dx + dy*dy);
                		
                		try{
                			double actualvalue = arousalGrid.field[x][y];
                        	//arousalGrid.field[x][y] = actualvalue+ value;
                			arousalGrid.field[x][y] =  value - (distancetospot/14);
                		}catch(java.lang.ArrayIndexOutOfBoundsException e){
                			e.printStackTrace();
                		}
                		
                	} //is not under 0
                }//all y
            }//all x
	}
	
	private void processUnrealVision(Vector<clsUnrealSensorValueVision> poUnrealVisionValues, clsSensorData poData) {
        
        //create object lists for all vision ranges
        clsVision oVisionDataNear = new clsVision();
        clsVision oVisionDataMedium = new clsVision();
        clsVision oVisionDataFar = new clsVision();
       
        //go through the vision ranges and convert from unreal to ars
        for (clsUnrealSensorValueVision data:poUnrealVisionValues) {
                       
                        clsUnrealSensorValueVision oVisionEntry = (clsUnrealSensorValueVision)data;
                       
                        double oVisionDistance = oVisionEntry.getRadius();
                       
                        if(oVisionDistance  >= 0 && oVisionDistance <_UNREAL_NEAR_DISTANCE  )
                                        oVisionDataNear.add( convertUNREALVision2DUVision(oVisionEntry, eSensorExtType.VISION_NEAR));
                        if(oVisionDistance  >= _UNREAL_NEAR_DISTANCE && oVisionDistance <_UNREAL_MEDIUM_DISTANCE)
                                        oVisionDataMedium.add( convertUNREALVision2DUVision(oVisionEntry, eSensorExtType.VISION_MEDIUM));
                        if(oVisionDistance  >= _UNREAL_MEDIUM_DISTANCE)
                                        oVisionDataFar.add( convertUNREALVision2DUVision(oVisionEntry, eSensorExtType.VISION_FAR));
        }             
       
        //now add the three vision ranges to the sensor data object
        //near
        oVisionDataNear.setSensorType(eSensorExtType.VISION_NEAR);
        poData.addSensorExt(eSensorExtType.VISION_NEAR, oVisionDataNear);
        //medium
        oVisionDataMedium.setSensorType(eSensorExtType.VISION_MEDIUM);
        poData.addSensorExt(eSensorExtType.VISION_MEDIUM, oVisionDataMedium);
        //far
        oVisionDataFar.setSensorType(eSensorExtType.VISION_FAR);
        poData.addSensorExt(eSensorExtType.VISION_FAR, oVisionDataFar);

}

//creates one vision entry transformed to ARS vision types
private clsVisionEntry convertUNREALVision2DUVision(clsUnrealSensorValueVision poUNREALSensorVision, eSensorExtType poVisionType){
       
        //the real conversion
        clsVisionEntry oEntry = convertUNREALVisionEntry(poUNREALSensorVision);
        oEntry.setSensorType(poVisionType);

        return oEntry;
}
	
	//the real deep transformation to ARS vision data
	private clsVisionEntry convertUNREALVisionEntry(clsUnrealSensorValueVision poUNREALSensorVisionData) {
		clsVisionEntry oData = new clsVisionEntry();
		
		//set data, for all objects these are the same
		oData.setObjectPosition( getObjectPositionFromUNREAL(poUNREALSensorVisionData) );
		oData.setEntityId(poUNREALSensorVisionData.getID());
		
	
		switch(poUNREALSensorVisionData.getType())
		{
			case HEALTH:
			{
				oData.setEntityType(du.enums.eEntityType.HEALTH);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor( new Color(34,139,34) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
			case ARSIN:
			{
				oData.setEntityType(du.enums.eEntityType.ARSIN);
				oData.setAlive(true);
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( new Color(204,0,0) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
			case WALL:
			{
				oData.setEntityType(du.enums.eEntityType.WALL);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.SQUARE);
				oData.setColor(Color.BLACK);
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}	
			case MINI_HEALTH:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.MINI_HEALTH );
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor( new Color(0,128,0) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
				
			}case SMALL_ARMOR:
			{
				oData.setEntityType(du.enums.eEntityType.SMALL_ARMOR);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor( new Color(255,0,0) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
			
			case SUPER_ARMOR:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.SUPER_ARMOR );
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor(  new Color(255,69,0)  );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
				
			}case UDAMAGE:
			{
				oData.setEntityType(du.enums.eEntityType.UDAMAGE);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( new Color(255,20,147) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
			
			case FLAKCANNON_WEAPON:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.FLAKCANNON_WEAPON );
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor( new Color(133,216,230));
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}case FLAKCANNON_AMMO:
			{
				oData.setEntityType(du.enums.eEntityType.FLAKCANNON_AMMO);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( new Color(133,216,230));
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
			
			case BIORIFLE_WEAPON:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.BIORIFLE_WEAPON );
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor( new Color(175,238,238) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}case BIORIFLE_AMMO:
			{
				oData.setEntityType(du.enums.eEntityType.BIORIFLE_AMMO);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( new Color(175,238,238) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
			
			case LINKGUN_WEAPON:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.LINKGUN_WEAPON );
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor( new Color(95,158,160) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
				
			}case LINKGUN_AMMO:
			{
				oData.setEntityType(du.enums.eEntityType.LINKGUN_AMMO);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( new Color(95,158,160) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
			
			case MINIGUN_WEAPON:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.MINIGUN_WEAPON );
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor( new Color(176,196,222) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}case MINIGUN_AMMO:
			{
				oData.setEntityType(du.enums.eEntityType.MINIGUN_AMMO);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( new Color(176,196,222) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
			
			case ROCKET_WEAPON:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.ROCKET_WEAPON );
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor( new Color(135,206,250));
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}case ROCKET_AMMO:
			{
				oData.setEntityType(du.enums.eEntityType.ROCKET_AMMO);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( new Color(135,206,250) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
			
			case SNIPER_WEAPON:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.SNIPER_WEAPON );
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor(new Color(65,105,225));
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
				
			}case SNIPER_AMMO:
			{
				oData.setEntityType(du.enums.eEntityType.SNIPER_AMMO);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor(new Color(65,105,225));
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
			
			case SHOCKRIFLE_WEAPON:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.SHOCKRIFLE_WEAPON );
				oData.setShapeType( du.enums.eShapeType.SQUARE );
				oData.setColor( new Color(72,209,204) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}case SHOCKRIFLE_AMMO:
			{
				oData.setEntityType(du.enums.eEntityType.SHOCKRIFLE_AMMO);
				oData.setAlive(false);
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( new Color(72,209,204) );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
		
			case CAKE:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.CAKE );
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( Color.PINK );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
				
			case  CAN:
			{
				oData.setEntityType(du.enums.eEntityType.CAN);
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
				
			case  STONE:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.STONE );
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( Color.DARK_GRAY );
				oData.setEntityId(poUNREALSensorVisionData.getID());
				break;
			}
				
			case  UNDEFINED:
			{
				oData.setEntityType(du.enums.eEntityType.UNDEFINED);
				break;
			}
				
			default:throw new java.lang.NullPointerException("UNREAL vision type not implemented");	
		}

		return oData;
	}
	
	//convert the objects from the unreal vision to symbols where the object is
	private eSide getObjectPositionFromUNREAL(clsUnrealSensorValueVision poUNREALSensorVisionData){
		eSide oSide = eSide.UNDEFINED;
		
		//where is the object
		if (Math.abs(poUNREALSensorVisionData.getAngle()) <= _UNREAL_AREA_OF_VIEW_RADIANS/18)
		{
			oSide = eSide.CENTER;
		}
		else if(Math.abs(poUNREALSensorVisionData.getAngle()) <= _UNREAL_AREA_OF_VIEW_RADIANS/4)
		{
			if (poUNREALSensorVisionData.getAngle()<0)
			{
				oSide = eSide.MIDDLE_LEFT;
			}
			else 
			{
				oSide = eSide.MIDDLE_RIGHT;
			}
		}
		else {
				if(poUNREALSensorVisionData.getAngle()<0){
					oSide = eSide.LEFT; 
				}
				else{
					oSide = eSide.RIGHT; 
				}
		}
		return oSide;
	}
	
	private clsDataBase convertSlowMessengerSystem() {

		clsSlowMessenger oRetVal = new clsSlowMessenger();
		clsSlowMessengerSensor oSensor = (clsSlowMessengerSensor)(moSensorsInt.get(eSensorIntType.SLOWMESSENGER));
		
		if (oSensor.getSlowMessages() != null) {
			for(Map.Entry<eSlowMessenger, Double> oEntry:oSensor.getSlowMessages().entrySet()) {
				oRetVal.getSlowMessengerValues().put( oEntry.getKey(), oEntry.getValue());
			}
		}
		
		return oRetVal;
	}
	
	private clsDataBase convertFastMessengerSystem() {

		clsFastMessenger oRetVal = new clsFastMessenger();
		clsFastMessengerSensor oSensor = (clsFastMessengerSensor)(moSensorsInt.get(eSensorIntType.FASTMESSENGER));

		if (oSensor.getFastMessages() != null) {
			for(clsFastMessengerEntry oEntry:oSensor.getFastMessages()) {
				oRetVal.getEntries().add( convertFastMessengerEntry(oEntry) );
			}
		}
		
		return oRetVal;
	}
	
	private du.itf.sensors.clsFastMessengerEntry convertFastMessengerEntry(bw.body.internalSystems.clsFastMessengerEntry poEntry) {
		eFastMessengerSources oSource;

		switch(poEntry.getSource()) {
			case INTER_PAIN_STOMACHTENSION:;
			case INTRA_DAMAGE_NUTRITION:oSource=eFastMessengerSources.STOMACH;break;
			
			case INTER_DAMAGE_BUMP:;
			case SENSOR_EXT_TACTILE_BUMP:;
			case SENSOR_EXT_TACTITLE:oSource=eFastMessengerSources.BUMP;break;
			
			case INTRA_DAMAGE_TEMPERATURE:oSource=eFastMessengerSources.TEMPERATURE;break;
			
			case INTER_DAMAGE_LIGHTNING:oSource=eFastMessengerSources.LIGHTNING;break;
			
			case ACTIONEX_ATTACKBITE:oSource=eFastMessengerSources.MANIPULATION_AREA;break;
			
			case ACTIONEX_EAT:oSource=eFastMessengerSources.EATABLE_AREA;break;
			
			case INTER_ORIFICE_ORAL_AGGRESSIV_MUCOSA: oSource=eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA; break;
			case INTER_ORIFICE_ORAL_LIBIDINOUS_MUCOSA: oSource=eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA; break;
			case INTER_ORIFICE_RECTAL_MUCOSA: oSource=eFastMessengerSources.ORIFICE_RECTAL_MUCOSA; break;
			case INTER_ORIFICE_GENITAL_MUCOSA: oSource=eFastMessengerSources.ORIFICE_GENITAL_MUCOSA; break;
			case INTER_ORIFICE_PHALLIC_MUCOSA: oSource=eFastMessengerSources.ORIFICE_PHALLIC_MUCOSA; break;
				
			
			default:throw new java.lang.NullPointerException("unkown fast messenger source: "+poEntry.getSource());
		}
		
		du.itf.sensors.clsFastMessengerEntry oRes = new du.itf.sensors.clsFastMessengerEntry(oSource, poEntry.getIntensity());
		return oRes;
	}
	
	private clsPositionChange convertPositionChangeSensor(eSensorExtType poSensorType) {
		clsPositionChange oData = new clsPositionChange();
		oData.setSensorType(poSensorType); 
		clsSensorPositionChange oSensor = (clsSensorPositionChange)(moSensorsExt.get(poSensorType));
		
		oData.setX(oSensor.getPositionChange().getPosition().x);
		oData.setY(oSensor.getPositionChange().getPosition().y);
		oData.setA(oSensor.getPositionChange().getAngle().radians);
	
		return oData;
	}
	
	private clsDataBase convertStomachSystem_Energy() {

		clsEnergy oRetVal = new clsEnergy();
		clsEnergySensor oStomachSensor = (clsEnergySensor)(moSensorsInt.get(eSensorIntType.ENERGY));

		oRetVal.setEnergy(oStomachSensor.getEnergy());
		
		return oRetVal;
	}
	
	private clsDataBase convertStomachSystem_Tension() {

		clsStomachTension oRetVal = new clsStomachTension();
		clsStomachTensionSensor oStomachSensor = (clsStomachTensionSensor)(moSensorsInt.get(eSensorIntType.STOMACHTENSION));

		oRetVal.setTension(oStomachSensor.getTension());
		
		return oRetVal;
	}
	
	private clsDataBase convertStomachSystem_IntestineTension() {

		clsIntestinePressure oRetVal = new clsIntestinePressure();
		clsIntestinePressureSensor oIntestineSensor = (clsIntestinePressureSensor)(moSensorsInt.get(eSensorIntType.INTESTINEPRESSURE));

		oRetVal.setPressure(oIntestineSensor.getTension());
		
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

		oRetVal.setStaminaValue( oStaminaSensor.getStaminaValue() );
		
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
	public clsDataBase convertHealthSystem() {
		
		clsHealthSystem oRetVal = new clsHealthSystem();
		clsHealthSensor oHealthSensor = (clsHealthSensor)(moSensorsInt.get(eSensorIntType.HEALTH));

		oRetVal.setHealthValue( oHealthSensor.getHealthValue() );
		
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

		oRetVal.setTemperatureValue( oTemperatureSensor.getTemperatureValue() );
		
		return oRetVal;	
	}

	/**
	 * transforms energy information from the sensor into the sensordata class, used as input by the decision unit 
	 *
	 * @author langr
	 * 11.05.2009, 17:43:58
	 *
	 * @return
	 */
	private clsDataBase convertEnergySystem() {

		clsEnergyConsumption oRetVal = new clsEnergyConsumption();
		clsEnergyConsumptionSensor oEnergySensor = (clsEnergyConsumptionSensor)(moSensorsInt.get(eSensorIntType.ENERGY_CONSUMPTION));

		oRetVal.setEnergyConsumption( oEnergySensor.getEnergy() );
		
		return oRetVal;
		
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 03.09.2010, 11:21:14
	 *
	 * @param poVisionType
	 * @return
	 */
	private clsVision convertVisionSensor(eSensorExtType poVisionType) {
		clsVision oData = new clsVision();
		oData.setSensorType(poVisionType);
		clsSensorVision oVision = (clsSensorVision)(moSensorsExt.get(poVisionType));
		
		if(oVision != null){
			ArrayList<clsCollidingObject> oDetectedObjectList = oVision.getSensorData();
			
			for(clsCollidingObject oCollider : oDetectedObjectList){
				clsVisionEntry oEntry = convertVisionEntry(oCollider, poVisionType);
				oEntry.setNumEntitiesPresent(setMeNumber(oDetectedObjectList.size()) );
				oData.add(oEntry);
			}
		}
		
		return oData;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 03.09.2010, 11:21:18
	 *
	 * @param poVisionType
	 * @return
	 */
	private clsManipulateArea convertManipulateSensor(eSensorExtType poVisionType) {
		clsManipulateArea oData = new clsManipulateArea();
		oData.setSensorType(poVisionType);
		clsSensorManipulateArea oManip = (clsSensorManipulateArea)(moSensorsExt.get(poVisionType));
		
		if(oManip != null){
			ArrayList<clsCollidingObject> oDetectedObjectList = oManip.getSensorData();
		
			for(clsCollidingObject oCollider : oDetectedObjectList){
				clsVisionEntry oEntry = convertVisionEntry(oCollider, poVisionType);
				if(oEntry != null){
					clsManipulateAreaEntry oManipEntry = new clsManipulateAreaEntry(oEntry);
					if(oManipEntry != null){
						oData.add(oManipEntry);
					}
				}
			}
		}

		return oData;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 03.09.2010, 11:21:22
	 *
	 * @param poVisionType
	 * @return
	 */
	private clsEatableArea convertEatAbleAreaSensor(eSensorExtType poVisionType) {
		clsEatableArea oData = new clsEatableArea();
		oData.setSensorType(poVisionType); 
		clsSensorEatableArea oEatableSensor = (clsSensorEatableArea) moSensorsExt.get(poVisionType);
		
		ArrayList<clsCollidingObject> oDetectedObjectList = oEatableSensor.getSensorData();
		
		for (clsCollidingObject oCollider : oDetectedObjectList) {
			clsVisionEntry oVisionEntry = convertVisionEntry(oCollider, poVisionType);
			
			if(oVisionEntry != null){
				clsEatableAreaEntry oEntry = new clsEatableAreaEntry(oVisionEntry);
			
				if(oEntry != null && oCollider.moCollider != null){
					convertEatableAreaEntry(oCollider, oEntry);
					oData.add(oEntry);
				}
			}
		}
		
		return oData;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 03.09.2010, 11:21:25
	 *
	 * @return
	 */
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
		oData.setIntensity( oRadiationSensor.getMrRadiation() );		

		return oData;
	}
	
	

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 03.09.2010, 11:21:29
	 *
	 * @param collidingObj
	 * @param poSensorType
	 * @return
	 */
	private clsVisionEntry convertVisionEntry(clsCollidingObject collidingObj, eSensorExtType poSensorType) {
		clsEntity oEntity = getEntity(collidingObj.moCollider);
		clsVisionEntry oData = new clsVisionEntry();
		
		if(oEntity != null){ 
		   oData.setEntityType( getEntityType(collidingObj.moCollider));		
		   oData.setShapeType( getShapeType(collidingObj.moCollider));
		   oData.setColor( (Color) oEntity.get2DShape().getPaint());
		   oData.setEntityId(oEntity.getId());

		   oData.setAlive(oEntity.isAlive());
		
		   //set corresponding Actions
		   oData.setActions(convertActions(oEntity.getExecutedActions()));
		   
		   oData.setObjectPosition( collidingObj.meColPos);  
		   oData.setSensorType(poSensorType);
		    
		   oData.setExactDebugPosition(oEntity.getPosition().getX(), oEntity.getPosition().getY(), oEntity.getPose().getAngle().radians);
		   
		   //values from 0-1, this is for testing, should be set to the real arousal value
		   double sensorArousalValue = 1;
		   oData.setDebugSensorArousal(sensorArousalValue);
					
			// FIXME: (horvath) - temporary polar coordinates calculation
			clsSensorPositionChange oSensor = (clsSensorPositionChange)(moSensorsExt.get(eSensorExtType.POSITIONCHANGE));
			clsPolarcoordinate oRel = collidingObj.mrColPoint;
			oRel.moAzimuth = new Angle(clsSensorDataCalculation.normalizeRadian(oRel.moAzimuth.radians - oSensor.getLastPosition().getAngle().radians));
					
			oData.setPolarcoordinate( new bfg.tools.shapes.clsPolarcoordinate(oRel.mrLength,oRel.moAzimuth.radians) );
			
			if(oEntity.getBody() != null){
				//sets if the body is full/half etc, 0.5 = 50%
				oData.setObjectBodyIntegrity(oEntity.getBody().getBodyIntegrity());
			}
			
			if( oEntity instanceof clsAnimal ){ oData.setAlive( ((clsAnimal)oEntity).isAlive() ); }
			
			/*FIXME HZ actually the antenna positions are undefined*/
			if (oEntity instanceof clsARSIN || oEntity instanceof  clsRemoteBot){
				oData.setAntennaPositionLeft(eAntennaPositions.UNDEFINED); 
				oData.setAntennaPositionRight(eAntennaPositions.UNDEFINED);
			}
		}
		
		return oData;
	}

	// ** MW 
	private clsAcoustic convertAcousticSensor(eSensorExtType poSensorType) {
		clsAcoustic oData = new clsAcoustic();
		oData.setSensorType(poSensorType);
		
		clsSensorAcoustic oAcoustic = (clsSensorAcoustic)(moSensorsExt.get(poSensorType));
		
		if(oAcoustic != null){
			ArrayList<clsCollidingObject> oDetectedObjectList = oAcoustic.getSensorData();
			
			for(clsCollidingObject oCollider : oDetectedObjectList){
				if (oCollider.moEntity.getEntityType() == eEntityType.SPEECH){
					clsAcousticEntry oEntry = convertAcousticEntry(oCollider, poSensorType);
					oData.add(oEntry);
				}
			}
		}
		
		return oData;
	}
	// MW **
	
	private ArrayList<ePercievedActionType> convertActions(ArrayList<clsAction> poActions){
	    ArrayList<ePercievedActionType> oRetVal = new ArrayList<ePercievedActionType>();
	    
	    for( clsAction oAction : poActions){
	        oRetVal.add(oAction.getActionType());
	    }
	    
	    return oRetVal;
	    
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 03.09.2010, 11:40:25
	 *
	 * @param oCollider
	 * @param oEntry
	 * @return
	 */
	private void convertEatableAreaEntry(clsCollidingObject oCollider, clsEatableAreaEntry oEntry) {
		clsEntity oEntity= getEntity(oCollider.moCollider); 
		
		if(oEntity != null){
			clsAttributeAlive oAlive = (clsAttributeAlive)oEntity.getBody().getAttributes().getAttribute(eBodyAttributes.ALIVE);
			
			oEntry.setIsAlive( oAlive.isAlive());
			oEntry.setIsConsumeable( oAlive.isConsumeable());
		}
	}
	
	/**
	 * DOCUMENT (MW) - insert description
	 *
	 * @since 27.02.2013 11:27:41
	 *
	 * @param oCollider
	 * @param poSensorType
	 */
	private clsAcousticEntry convertAcousticEntry(clsCollidingObject collidingObj, eSensorExtType poSensorType) {
		clsEntity oEntity = getEntity(collidingObj.moCollider);
		clsAcousticEntry oData = new clsAcousticEntry();

		if(oEntity != null && oEntity.isEntityType(eEntityType.SPEECH)){
			oData.setEntityType(getEntityType(collidingObj.moCollider));		
			oData.setEntityId(oEntity.getId());
			oData.setObjectPosition( collidingObj.meColPos); 
			 
			oData.setSensorType(poSensorType);
		    
			oData.setExactDebugPosition(oEntity.getPosition().getX(), oEntity.getPosition().getY(), oEntity.getPose().getAngle().radians);
		   
			//values from 0-1, this is for testing, should be set to the real arousal value
			double sensorArousalValue = 1;
			oData.setDebugSensorArousal(sensorArousalValue);
						   
			clsAbstractSpeech oAbstractSpeech = ((clsSpeech) oEntity).getAbstractSpeech();
			oData.setEntry(oAbstractSpeech);
		}
		
		return oData;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.09.2009, 16:31:19
	 *
	 */
	private eCount setMeNumber(int pnEntryCount) {
				
		switch (pnEntryCount){
			case 0: 
				return eCount.NONE;
			case 1:
				return eCount.ONE; 
			case 2:
				return eCount.TWO;
			default:
				return eCount.MANY; 
		}
	}
	
	private  du.enums.eEntityType getEntityType(PhysicalObject2D poObject) {
		clsEntity oEntity = getEntity(poObject);
		
		if (oEntity != null) {
		  return getEntity(poObject).getEntityType();
		} else {
			return du.enums.eEntityType.UNDEFINED;
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
	
	private  du.enums.eShapeType getShapeType(PhysicalObject2D poObject) {
		
		if (poObject.getShape() instanceof  Circle) {
			return du.enums.eShapeType.CIRCLE;
		}else if(poObject.getShape() instanceof  Rectangle){
			return du.enums.eShapeType.SQUARE;
		} else {
			return du.enums.eShapeType.UNDEFINED;
		}
	}
	

	private clsBump convertBumpSensor() {
		clsBump oData = new clsBump();
		
		clsSensorBump oBumpSensor = (clsSensorBump) moSensorsExt.get(eSensorExtType.BUMP);	
		oData.setBumped( oBumpSensor.isBumped());

		return oData;
	}

	/*************************************************
	 *         GETTER & SETTER
	 ************************************************/

	public itfDecisionUnit getDecisionUnit() {
		return moDecisionUnit;		
	}

	public void setDecisionUnit(itfDecisionUnit poDecisionUnit) {
		moDecisionUnit = poDecisionUnit;
		moDecisionUnit.setInternalActionProcessor(moInternalActionProcessor);
		moDecisionUnit.setActionProcessor(moActionProcessor);
	}
	
	

	/**
	 * @since 16.05.2012 15:49:46
	 * 
	 * @return the moUnrealVisionValues
	 */
	public Vector<clsUnrealSensorValueVision> getUnrealVisionValues() {
		return moUnrealVisionValues;
	}

	/**
	 * @since 16.05.2012 15:49:46
	 * 
	 * @param moUnrealVisionValues the moUnrealVisionValues to set
	 */
	public void setUnrealVisionValues(
			Vector<clsUnrealSensorValueVision> moUnrealVisionValues) {
		this.moUnrealVisionValues = moUnrealVisionValues;
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
		if( moDecisionUnit instanceof itfProcessKeyPressed) {
			((itfProcessKeyPressed) moDecisionUnit).setKeyPressed(keyPressed);
		}
		
	}
}
