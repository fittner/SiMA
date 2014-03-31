/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.brainsocket;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import communicationPorts.clsCommunicationPortDUControl;
import communicationPorts.clsCommunicationPortDUData;
import complexbody.internalSystems.clsFastMessengerEntry;
import complexbody.io.sensors.external.clsSensorBump;
import complexbody.io.sensors.external.clsSensorExt;
import complexbody.io.sensors.external.clsSensorPositionChange;
import complexbody.io.sensors.external.clsSensorRadiation;
import complexbody.io.sensors.external.clsSensorVision;
import complexbody.io.sensors.internal.clsEnergyConsumptionSensor;
import complexbody.io.sensors.internal.clsEnergySensor;
import complexbody.io.sensors.internal.clsFastMessengerSensor;
import complexbody.io.sensors.internal.clsHealthSensor;
import complexbody.io.sensors.internal.clsIntestinePressureSensor;
import complexbody.io.sensors.internal.clsSensorInt;
import complexbody.io.sensors.internal.clsSlowMessengerSensor;
import complexbody.io.sensors.internal.clsStaminaSensor;
import complexbody.io.sensors.internal.clsStomachTensionSensor;
import complexbody.io.sensors.internal.clsTemperatureSensor;
import complexbody.io.sensors.uitils.clsSensorDataCalculation;

import physics2D.physicalObject.clsCollidingObject;
import physics2D.physicalObject.clsMobileObject2D;
import physics2D.physicalObject.clsStationaryObject2D;
import properties.clsProperties;

import sim.field.grid.DoubleGrid2D;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Circle;
import sim.physics2D.shape.Rectangle;
import sim.physics2D.util.Angle;
import sim.util.Double2D;
import singeltons.clsSingletonMasonGetter;
import tools.clsPolarcoordinate;
import base.clsCommunicationInterface;
import bfg.utils.enums.eCount;
import bfg.utils.enums.eSide;
import body.itfStepProcessing;
import body.attributes.clsAttributeAlive;
import du.enums.eFastMessengerSources;
import du.enums.eSaliency;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.enums.eSlowMessenger;
import du.itf.itfDecisionUnit;
import du.itf.itfProcessKeyPressed;
import du.itf.actions.clsActionCommand;
import du.itf.actions.clsInternalActionCommand;
import du.itf.actions.itfActionProcessor;
import du.itf.actions.itfInternalActionProcessor;
import du.itf.sensors.clsBump;
import du.itf.sensors.clsInspectorPerceptionItem;
import du.itf.sensors.clsOlfactoricEntry;
import du.itf.sensors.clsRadiation;
import du.itf.sensors.clsSensorData;
import du.itf.sensors.clsUnrealSensorValueVision;
import du.itf.sensors.clsVision;
import du.itf.sensors.clsVisionEntry;
import entities.abstractEntities.clsEntity;
import entities.enums.eBodyAttributes;

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
	
	private clsCommunicationPortDUData moCommunicationPortDUData;
	private clsCommunicationPortDUControl moCommunicationPortControl;
	
	private Vector<clsUnrealSensorValueVision> moUnrealVisionValues;
	protected final Logger log;
	
	public clsBrainSocket(String poPrefix, clsProperties poProp, HashMap<eSensorExtType, clsSensorExt> poSensorsExt, HashMap<eSensorIntType, clsSensorInt> poSensorsInt, itfActionProcessor poActionProcessor, itfInternalActionProcessor poInternalActionProcessor) {
		log = logger.clsLogger.getLog("BrainSocket");
		moActionProcessor=poActionProcessor;
		moInternalActionProcessor=poInternalActionProcessor;
		moSensorsExt = poSensorsExt;
		moSensorsInt = poSensorsInt;
		
		moCommunicationPortDUData = new clsCommunicationPortDUData(this);
		moCommunicationPortControl = new clsCommunicationPortDUControl(this);
		
//		moSensorCalculation = new clsSensorDataCalculation();
		applyProperties(poPrefix, poProp);
	}
	
	public void setDUDataInterface(clsCommunicationInterface poInterface){
		moCommunicationPortDUData.setCommunicationInterface(poInterface);
	}
	

	public void setDUControlInterface(clsCommunicationInterface poInterface){
		moCommunicationPortControl.setCommunicationInterface(poInterface);
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
		//OLD
		/*if (moDecisionUnit != null) {
			clsSensorData oData = convertSensorData();
			log.debug(oData.toString());
			moDecisionUnit.update(oData);
			moDecisionUnit.process();
			
			//receive Action Commands
			//devide into Internal and External Actions
			//for all external Action executr moActionProcesser.call()
			//for all internal Action executr moInternalActionProcesser.call()
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
		*/
		boolean oDUResponse = moCommunicationPortControl.stepDU();
		moCommunicationPortDUData.recvActionCommands();
	}
	
	public void sendDataToDU(){
		//1 send Sensor Data to DU
		moCommunicationPortDUData.sendToDU(convertSensorInformation());
	}
	
	public ArrayList<clsActionCommand> getActions(){
		return moCommunicationPortDUData.getActions();
	}
	public ArrayList<clsInternalActionCommand> getInternalActions(){
		return moCommunicationPortDUData.getInternalActions();
	}
	
	private clsDataContainer convertSensorInformation() {
		clsDataContainer oRetVal = new clsDataContainer();
		oRetVal.addDataPoint(convertSlowMessengerSystem());
		oRetVal.addDataPoint(convertFastMessengerSystem());
		oRetVal.addDataPoint(convertStomachSystem_Energy());
		oRetVal.addDataPoint(convertStomachSystem_Tension());
		oRetVal.addDataPoint(convertStomachSystem_IntestineTension());
		oRetVal.addDataPoint(convertStaminaSystem());
		oRetVal.addDataPoint(convertHealthSystem());
		oRetVal.addDataPoint(convertTemperatureSystem());
		oRetVal.addDataPoint(convertEnergySystem());
		//oRetVal.addDataPoint(convertVisionSensor(eSensorExtType.VISION_NEAR));
		//oRetVal.addDataPoint(convertVisionSensor(eSensorExtType.VISION_MEDIUM));
		//oRetVal.addDataPoint(convertVisionSensor(eSensorExtType.VISION_FAR));
		//oRetVal.addDataPoint(convertVisionSensor(eSensorExtType.VISION_SELF));
		oRetVal.addDataPoint(convertVisionSensors());

		return oRetVal;
	}
	
	private clsDataPoint convertSlowMessengerSystem() {

		clsDataPoint oDataPoint = new clsDataPoint("SLOW_MESSENGER_SYSTEM","");
		clsSlowMessengerSensor oSensor = (clsSlowMessengerSensor)(moSensorsInt.get(eSensorIntType.SLOWMESSENGER));
		
		if (oSensor.getSlowMessages() != null) {
			for(Map.Entry<eSlowMessenger, Double> oEntry:oSensor.getSlowMessages().entrySet()) {
				oDataPoint.addAssociation(new clsDataPoint(oEntry.getKey().toString(),""+oEntry.getValue()));
			}
		}
		
		return oDataPoint;
	}
	
	private clsDataPoint convertFastMessengerSystem() {

		clsDataPoint oDataPoint = new clsDataPoint("FAST_MESSENGER_SYSTEM","");
		clsFastMessengerSensor oSensor = (clsFastMessengerSensor)(moSensorsInt.get(eSensorIntType.FASTMESSENGER));

		if (oSensor.getFastMessages() != null) {
			for(clsFastMessengerEntry oEntry:oSensor.getFastMessages()) {
				oDataPoint.addAssociation(convertFastMessengerEntry(oEntry));
			}
		}
		
		return oDataPoint;

	}
	private clsDataPoint convertFastMessengerEntry(clsFastMessengerEntry poEntry) {
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
		
		clsDataPoint oRes = new clsDataPoint(oSource.toString(), ""+poEntry.getIntensity());
		return oRes;
	}
	
	private clsDataPoint convertStomachSystem_Energy() {
		clsEnergySensor oStomachSensor = (clsEnergySensor)(moSensorsInt.get(eSensorIntType.ENERGY));
		return new clsDataPoint("STOMACH_ENERGY",""+oStomachSensor.getEnergy());
	}
	
	private clsDataPoint convertStomachSystem_Tension() {
		clsStomachTensionSensor oStomachSensor = (clsStomachTensionSensor)(moSensorsInt.get(eSensorIntType.STOMACHTENSION));		
		return new clsDataPoint("STOMACH_TENSION",""+oStomachSensor.getTension());
	}
	
	private clsDataPoint convertStomachSystem_IntestineTension() {
		clsIntestinePressureSensor oIntestineSensor = (clsIntestinePressureSensor)(moSensorsInt.get(eSensorIntType.INTESTINEPRESSURE));		
		return new clsDataPoint("STOMACH_INTESTINE_TENSION",""+oIntestineSensor.getTension());
	}
	private clsDataPoint convertStaminaSystem() {
		clsStaminaSensor oStaminaSensor = (clsStaminaSensor)(moSensorsInt.get(eSensorIntType.STAMINA));		
		return new clsDataPoint("STAMINA",""+oStaminaSensor.getStaminaValue());
	}
	public clsDataPoint convertHealthSystem() {
		clsHealthSensor oHealthSensor = (clsHealthSensor)(moSensorsInt.get(eSensorIntType.HEALTH));		
		return new clsDataPoint("HEALTH",""+oHealthSensor.getHealthValue());
	}
	private clsDataPoint convertTemperatureSystem() {
		clsTemperatureSensor oTemperatureSensor = (clsTemperatureSensor)(moSensorsInt.get(eSensorIntType.TEMPERATURE));
		return new clsDataPoint("TEMPERATURE",""+oTemperatureSensor.getTemperatureValue());
	}
	private clsDataPoint convertEnergySystem() {
		clsEnergyConsumptionSensor oEnergySensor = (clsEnergyConsumptionSensor)(moSensorsInt.get(eSensorIntType.ENERGY_CONSUMPTION));		
		return new clsDataPoint("ENERGY_CONSUMPTION",""+oEnergySensor.getEnergy());
		
	}
/*
	private clsDataPoint convertVisionSensor(eSensorExtType poVisionType) {
		clsDataPoint oRetVal = new clsDataPoint(poVisionType.toString(),"");
		//oData.setSensorType(poVisionType);
		clsSensorVision oVision = (clsSensorVision)(moSensorsExt.get(poVisionType));
		
		if(oVision != null){
			ArrayList<clsCollidingObject> oDetectedObjectList = oVision.getSensorData();
			
			for(clsCollidingObject oCollider : oDetectedObjectList){
				clsDataPoint oEntry = convertVisionEntry(oCollider, poVisionType);
				//oEntry.setNumEntitiesPresent(setMeNumber(oDetectedObjectList.size()) );
				oRetVal.addAssociation(oEntry);
			}
		}
		return oRetVal;
	}*/
	private clsDataPoint convertVisionSensors() {
		clsDataPoint oRetVal = new clsDataPoint("VISION","");
		//oData.setSensorType(poVisionType);
		// Get VISION SELF
		ArrayList<eSensorExtType> oVisionTypes = new ArrayList<eSensorExtType>();
		oVisionTypes.add(eSensorExtType.VISION_SELF);
		oVisionTypes.add(eSensorExtType.VISION_NEAR);
		oVisionTypes.add(eSensorExtType.VISION_MEDIUM);
		oVisionTypes.add(eSensorExtType.VISION_FAR);
		//oVisionTypes.add(eSensorExtType.VISION_CARRIED_ITEMS);


		for(eSensorExtType oType : oVisionTypes){
			clsSensorVision oVision = (clsSensorVision)(moSensorsExt.get(oType));
			
			if(oVision != null){
				ArrayList<clsCollidingObject> oDetectedObjectList = oVision.getSensorData();
				
				for(clsCollidingObject oCollider : oDetectedObjectList){
					clsDataPoint oEntry = convertVisionEntry(oCollider, convertDistance(oType));
					//oEntry.setNumEntitiesPresent(setMeNumber(oDetectedObjectList.size()) );
					oRetVal.addAssociation(oEntry);
				}
			}
		}
		
		return oRetVal;
	}
	private String convertDistance(eSensorExtType poType) {
		if(poType == eSensorExtType.VISION_SELF) return "SELF";
		else if(poType == eSensorExtType.VISION_NEAR) return "NEAR";
		else if(poType == eSensorExtType.VISION_MEDIUM) return "MEDIUM";
		else if(poType == eSensorExtType.VISION_FAR) return "FAR";
		else return "";
		
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
	private clsDataPoint convertVisionEntry(clsCollidingObject collidingObj, String poDistance) {
		clsDataPoint oRetVal =null;
		clsEntity oEntity = getEntity(collidingObj.moCollider);
		//clsVisionEntry oData = new clsVisionEntry();
		
		if(oEntity != null){ 
		   oRetVal =  new clsDataPoint("ENTITY",getEntityType(collidingObj.moCollider).toString());
		   oRetVal.addAssociation(new clsDataPoint("SHAPE",getShapeType(collidingObj.moCollider).toString()));
		   oRetVal.addAssociation(new clsDataPoint("COLOR",""+(((Color) oEntity.get2DShape().getPaint()).getRGB())));
		   oRetVal.addAssociation(new clsDataPoint("ENTITYID",oEntity.getId().toString()));
		   oRetVal.addAssociation(new clsDataPoint("BRIGHTNESS",getEntityBrightness(collidingObj.moCollider).toString()));
		   oRetVal.addAssociation(new clsDataPoint("ALIVE",""+oEntity.isAlive()));
		   oRetVal.addAssociation(new clsDataPoint("OBJECT_POSITION",collidingObj.meColPos.toString()));
		   oRetVal.addAssociation(new clsDataPoint("DEBUG_AROUSAL_VALUE",""+oEntity.getVisionBrightness()));
		   oRetVal.addAssociation(new clsDataPoint("DISTANCE",""+poDistance));

		   if(oEntity.getBody() != null){
			   oRetVal.addAssociation(new clsDataPoint("BODY_INTEGRITY",""+oEntity.getBody().getBodyIntegrity()));
		   }
		   
		   clsSensorPositionChange oSensor = (clsSensorPositionChange)(moSensorsExt.get(eSensorExtType.POSITIONCHANGE));
		   clsPolarcoordinate oRel = collidingObj.mrColPoint;
			oRel.moAzimuth = new Angle(clsSensorDataCalculation.normalizeRadian(oRel.moAzimuth.radians - oSensor.getLastPosition().getAngle().radians));
		   clsDataPoint oPolar = new clsDataPoint("POLARCOORDINATE","");
		   oPolar.addAssociation(new clsDataPoint("LENGTH",""+oRel.mrLength));
		   oPolar.addAssociation(new clsDataPoint("RADIANS",""+oRel.moAzimuth.radians));
		   oRetVal.addAssociation(oPolar);
		   
		   clsDataPoint oDebugPos = new clsDataPoint("DEBUG_POSITION","");
		   oDebugPos.addAssociation(new clsDataPoint("X",""+oEntity.getPosition().getX()));
		   oDebugPos.addAssociation(new clsDataPoint("Y",""+oEntity.getPosition().getY()));
		   oDebugPos.addAssociation(new clsDataPoint("ANGLE",""+ oEntity.getPose().getAngle().radians));

		   oRetVal.addAssociation(oDebugPos);

		
		   //TODO: convert Actions with new Action strategy
		  // oData.setActions(convertActions(oEntity.getExecutedActions()));

		}
		
		
		return oRetVal;
	}
	
	
	/* **************************************************** CONVERT SENSOR DATA *********************************************** */
/*	private clsSensorData convertSensorData() {
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
		oData.addSensorExt(eSensorExtType.VISION_CARRIED_ITEMS, convertVisionSensorCarriedItems() );
		//oData.addSensorExt(eSensorExtType.ACOUSTIC, convertAcousticSensor(eSensorExtType.ACOUSTIC) ); // MW
		//oData.addSensorExt(eSensorExtType.OLFACTORIC, convertOlfactoricSensor(eSensorExtType.OLFACTORIC) );
		//oData.addSensorExt(eSensorExtType.ACOUSTIC_NEAR, convertAcousticSensor(eSensorExtType.ACOUSTIC_NEAR) );
		//oData.addSensorExt(eSensorExtType.ACOUSTIC_MEDIUM, convertAcousticSensor(eSensorExtType.ACOUSTIC_MEDIUM) );
        //oData.addSensorExt(eSensorExtType.ACOUSTIC_FAR, convertAcousticSensor(eSensorExtType.ACOUSTIC_FAR) );
        oData.addSensorExt(eSensorExtType.ACOUSTIC_SELF, convertAcousticSensor(eSensorExtType.ACOUSTIC_SELF) );  
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
	}*/
	
	/**
	 * DOCUMENT (herret) - insert description
	 *
	 * @since 25.02.2014 09:45:45
	 *
	 * @param visionSelf
	 * @return
	 */
/*	private clsDataBase convertVisionSensorCarriedItems() {
		
		clsVision oData = new clsVision();
		oData.setSensorType(eSensorExtType.VISION_CARRIED_ITEMS);
		clsSensorExt oSensor = (moSensorsExt.get(eSensorExtType.VISION_CARRIED_ITEMS));
		

		HashMap <Double, ArrayList<clsCollidingObject>> oDetectedObjectList = oSensor.getSensorDataObj().getMeDetectedObject();
			
			for(ArrayList<clsCollidingObject> oColliderList : oDetectedObjectList.values()){
				for (clsCollidingObject oCollider : oColliderList){
					clsVisionEntry oEntry = convertVisionEntry(oCollider, eSensorExtType.VISION_CARRIED_ITEMS.toString());
					oEntry.setNumEntitiesPresent(setMeNumber(oDetectedObjectList.size()) );
					oData.add(oEntry);
				}
			}
		return oData;
	}
*/
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
			
			
			case SPEECH:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.SPEECH );
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
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
	

	


	
/*private clsOlfactoric convertOlfactoricSensor(eSensorExtType poSensorType) {
	

	 
	clsOlfactoric oData = new clsOlfactoric();
	oData.setSensorType(poSensorType); 
	clsSensorOlfactoric oSensor = (clsSensorOlfactoric) moSensorsExt.get(poSensorType);
	
	ArrayList<clsCollidingObject> oDetectedObjectList = oSensor.getSensorData();
	

	
	for (clsCollidingObject oCollider : oDetectedObjectList) {
		clsVisionEntry oVisionEntry = convertVisionEntry(oCollider, poSensorType);
		
		if(oVisionEntry != null){
			clsOlfactoricEntry oEntry = new clsOlfactoricEntry(oVisionEntry);
		
			if(oEntry != null && oCollider.moCollider != null){
				convertOlfactoricEntry(oCollider, oEntry);
				oData.add(oEntry);
			}
		}
	}
	
	return oData;
	}*/
	
private clsOlfactoricEntry convertOlfactoricEntry(clsCollidingObject oCollider, clsOlfactoricEntry oEntry) {
	
	clsEntity oEntity= getEntity(oCollider.moCollider); 
	
	if(oEntity != null){
		
		
		
		oEntry.setEntityType( getEntityType(oCollider.moCollider));
		oEntry.setOdor(oEntity.getOdor());
		 
		clsAttributeAlive oAlive = (clsAttributeAlive)oEntity.getBody().getAttributes().getAttribute(eBodyAttributes.ALIVE);
		
		//oEntry.setIsAlive( oAlive.isAlive());
		//oEntry.setIsConsumeable( oAlive.isConsumeable());
	}
	
	return oEntry;
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
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @author hi
	 * 03.09.2010, 11:21:29
	 *
	 * @param collidingObj
	 * @param poSensorType
	 * @return
	 *//*
	
	//FIXME: Why accoustic entry has a shapetype color, brightness,... ?
	private clsAcousticEntry convertAcousticEntry(clsCollidingObject collidingObj, eSensorExtType poSensorType) {
		clsEntity oEntity = getEntity(collidingObj.moCollider);
		clsAcousticEntry oData = new clsAcousticEntry();
		
		if(oEntity != null){ 
		   oData.setEntityType( getEntityType(collidingObj.moCollider));		
		 //  oData.setShapeType( getShapeType(oEntity));
		 //  oData.setColor( (Color) oEntity.get2DShape().getPaint());
		   oData.setEntityId(oEntity.getId());
		 //  oData.setBrightness( getEntityBrightness(collidingObj.moCollider));

		   oData.setAlive(oEntity.isAlive());
		
		   //set corresponding Actions
		  // oData.setActions(convertActions(oEntity.getExecutedActions()));
		   
		   oData.setObjectPosition( collidingObj.meColPos);  
		   oData.setSensorType(poSensorType);
		    
		   oData.setExactDebugPosition(oEntity.getPosition().getX(), oEntity.getPosition().getY(), oEntity.getPose().getAngle().radians);
		   
		   double sensorArousalValue = oEntity.getVisionBrightness();
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
		
		}
		
		return oData;
	}*/



	   
	   
		//translate the Brightness Info to enum values
	private  eSaliency getEntityBrightness(PhysicalObject2D poObject) {
		clsEntity oEntity = getEntity(poObject);
		du.enums.eSaliency retVal = du.enums.eSaliency.UNDEFINED;
			if (oEntity != null) {
			double oVisionBrightness = oEntity.getVisionBrightness();
	        
	        if(oVisionBrightness  >= 0 && oVisionBrightness < 0.25  )
	        	retVal = du.enums.eSaliency.LOW;
	       if(oVisionBrightness  >= 0.25 && oVisionBrightness <0.5)
	       	retVal = du.enums.eSaliency.MEDIUM;
	        if(oVisionBrightness   >= 0.5 && oVisionBrightness <0.75)
	        	retVal = du.enums.eSaliency.HIGH;
	        if(oVisionBrightness   >= 0.75 && oVisionBrightness <1)
	        	retVal = du.enums.eSaliency.VERYHIGH;
			}
			return retVal;
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
			oResult = (clsEntity) ((clsMobileObject2D) poObject).getEntity();
		} else if (poObject instanceof clsStationaryObject2D) {
			oResult = (clsEntity) ((clsStationaryObject2D) poObject).getEntity();
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
