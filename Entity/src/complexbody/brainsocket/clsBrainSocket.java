/**
< * @author langr
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

import org.slf4j.Logger;

import physical2d.physicalObject.datatypes.eCount;
import physics2D.physicalObject.clsCollidingObject;
import physics2D.physicalObject.clsMobileObject2D;
import physics2D.physicalObject.clsStationaryObject2D;
import properties.clsProperties;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Circle;
import sim.physics2D.shape.Rectangle;
import base.clsCommunicationInterface;
import body.clsComplexBody;
import body.itfStepProcessing;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import communicationPorts.clsCommunicationPortDUControl;
import communicationPorts.clsCommunicationPortDUData;
import complexbody.expressionVariables.clsExpressionVariable;
import complexbody.expressionVariables.clsExpressionVariableCheeksRedning;
import complexbody.expressionVariables.clsExpressionVariableFacialEyeBrows;
import complexbody.expressionVariables.clsExpressionVariableFacialEyes;
import complexbody.expressionVariables.clsExpressionVariableFacialMouth;
import complexbody.expressionVariables.clsExpressionVariableShake;
import complexbody.internalSystems.clsFastMessengerEntry;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;
import complexbody.io.actuators.actionCommands.itfActionProcessor;
import complexbody.io.actuators.actionCommands.itfInternalActionProcessor;
import complexbody.io.actuators.actionExecutors.clsAction;
import complexbody.io.sensors.datatypes.clsBump;
import complexbody.io.sensors.datatypes.enums.eFastMessengerSources;
import complexbody.io.sensors.datatypes.enums.eSaliency;
import complexbody.io.sensors.datatypes.enums.eSensorExtType;
import complexbody.io.sensors.datatypes.enums.eSensorIntType;
import complexbody.io.sensors.datatypes.enums.eSlowMessenger;
import complexbody.io.sensors.external.clsSensorBump;
import complexbody.io.sensors.external.clsSensorExt;
import complexbody.io.sensors.external.clsSensorVision;
import complexbody.io.sensors.internal.clsCryingSensor;
import complexbody.io.sensors.internal.clsEnergyConsumptionSensor;
import complexbody.io.sensors.internal.clsEnergySensor;
import complexbody.io.sensors.internal.clsFastMessengerSensor;
import complexbody.io.sensors.internal.clsHealthSensor;
import complexbody.io.sensors.internal.clsHeartbeatSensor;
import complexbody.io.sensors.internal.clsIntestinePressureSensor;
import complexbody.io.sensors.internal.clsMuscleTensionSensor;
import complexbody.io.sensors.internal.clsSensorInt;
import complexbody.io.sensors.internal.clsSlowMessengerSensor;
import complexbody.io.sensors.internal.clsStaminaSensor;
import complexbody.io.sensors.internal.clsStomachTensionSensor;
import complexbody.io.sensors.internal.clsSweatSensor;
import complexbody.io.sensors.internal.clsTemperatureSensor;

import control.interfaces.itfDecisionUnit;
import control.interfaces.itfProcessKeyPressed;
import entities.abstractEntities.clsEntity;

/**
 * The brain is the container for the mind and has a direct connection to external and internal IO.
 * Done: re-think if we insert a clsCerebellum for the neuroscientific perception-modules like R. Velik.
 * Answer: moSymbolization -> all done in another project
 * 
 * @author langr
 * 
 */
public class clsBrainSocket implements itfStepProcessing {
	


	private itfDecisionUnit moDecisionUnit; //reference
	private HashMap<eSensorExtType, clsSensorExt> moSensorsExt; //reference
	private HashMap<eSensorIntType, clsSensorInt> moSensorsInt; //reference
//	private clsSensorDataCalculation moSensorCalculation;
	
	private clsCommunicationPortDUData moCommunicationPortDUData;
	private clsCommunicationPortDUControl moCommunicationPortControl;
	
	protected final Logger log;
	
	public clsBrainSocket(String poPrefix, clsProperties poProp, HashMap<eSensorExtType, clsSensorExt> poSensorsExt, HashMap<eSensorIntType, clsSensorInt> poSensorsInt, itfActionProcessor poActionProcessor, itfInternalActionProcessor poInternalActionProcessor) {
		log = logger.clsLogger.getLog("BrainSocket");
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

		//Step the DU
		log.trace("Step Decision Unit");
		boolean oDUResponse = moCommunicationPortControl.stepDU();
		moCommunicationPortDUData.recvActionCommands();
	}
	
	public void sendDataToDU(){
		//1 send Sensor Data to DU
		clsDataContainer sonsorInformation = convertSensorInformation();
		log.trace("Send Sensor Data to Decision Unit: " + sonsorInformation.toString());
		moCommunicationPortDUData.sendToDU(sonsorInformation);


	}
	
	public ArrayList<clsActionCommand> getActions(){
		//log.trace("Action Commands receieved: "+ moCommunicationPortDUData.getActions());
		return moCommunicationPortDUData.getActions();
	}
	public ArrayList<clsInternalActionCommand> getInternalActions(){
		//log.trace("Internal Action Commands receieved: "+ moCommunicationPortDUData.getActions());
		return moCommunicationPortDUData.getInternalActions();
	}
	
	private clsDataContainer convertSensorInformation() {
		clsDataContainer oRetVal = new clsDataContainer();
		oRetVal.addDataPoint(convertSlowMessengerSystem());
		oRetVal.addDataPoint(generateLibido());
		oRetVal.addDataPoint(convertFastMessengerSystem());
		oRetVal.addDataPoint(convertStomachSystem_Energy());
		oRetVal.addDataPoint(convertStomachSystem_Tension());
		oRetVal.addDataPoint(convertStomachSystem_IntestineTension());
		oRetVal.addDataPoint(convertStaminaSystem());
		oRetVal.addDataPoint(convertHealthSystem());
		oRetVal.addDataPoint(convertTemperatureSystem());
		oRetVal.addDataPoint(convertEnergySystem());
		oRetVal.addDataPoint(convertVisionSensors());
		oRetVal.addDataPoint(convertHeartBeatSensor());
		oRetVal.addDataPoint(convertSweatSensor());
		oRetVal.addDataPoint(convertMuscleTensionLegsSensor());
		oRetVal.addDataPoint(convertMuscleTensionArmsSensor());
		oRetVal.addDataPoint(convertCryingSensor());

		return oRetVal;
	}
	
	private clsDataPoint generateLibido() {

		clsDataPoint oDataPoint = new clsDataPoint("LIBIDO","0.01");
		oDataPoint.setBufferType("EVENT");		
		return oDataPoint;
	}
	
	private clsDataPoint convertSlowMessengerSystem() {

		clsDataPoint oDataPoint = new clsDataPoint("SLOW_MESSENGER_SYSTEM","");
		oDataPoint.setBufferType("SIGNAL");
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
		oDataPoint.setBufferType("EVENT");
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
		clsDataPoint oDataPoint = new clsDataPoint("STOMACH_ENERGY",""+oStomachSensor.getEnergy());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
	}
	
	private clsDataPoint convertStomachSystem_Tension() {
		clsStomachTensionSensor oStomachSensor = (clsStomachTensionSensor)(moSensorsInt.get(eSensorIntType.STOMACHTENSION));
		clsDataPoint oDataPoint = new clsDataPoint("STOMACH_TENSION",""+oStomachSensor.getTension());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
	}
	
	private clsDataPoint convertStomachSystem_IntestineTension() {
		clsIntestinePressureSensor oIntestineSensor = (clsIntestinePressureSensor)(moSensorsInt.get(eSensorIntType.INTESTINEPRESSURE));	
		clsDataPoint oDataPoint = new clsDataPoint("STOMACH_INTESTINE_TENSION",""+oIntestineSensor.getTension());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
	}
	private clsDataPoint convertStaminaSystem() {
		clsStaminaSensor oStaminaSensor = (clsStaminaSensor)(moSensorsInt.get(eSensorIntType.STAMINA));
		clsDataPoint oDataPoint = new clsDataPoint("STAMINA",""+oStaminaSensor.getStaminaValue());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
	}
	public clsDataPoint convertHealthSystem() {
		clsHealthSensor oHealthSensor = (clsHealthSensor)(moSensorsInt.get(eSensorIntType.HEALTH));	
		clsDataPoint oDataPoint = new clsDataPoint("HEALTH",""+oHealthSensor.getHealthValue());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
	}

	private clsDataPoint convertTemperatureSystem() {
		clsTemperatureSensor oTemperatureSensor = (clsTemperatureSensor)(moSensorsInt.get(eSensorIntType.TEMPERATURE));
		clsDataPoint oDataPoint = new clsDataPoint("TEMPERATURE",""+oTemperatureSensor.getTemperatureValue());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
	}
	private clsDataPoint convertEnergySystem() {
		clsEnergyConsumptionSensor oEnergySensor = (clsEnergyConsumptionSensor)(moSensorsInt.get(eSensorIntType.ENERGY_CONSUMPTION));
		clsDataPoint oDataPoint =  new clsDataPoint("ENERGY_CONSUMPTION",""+oEnergySensor.getEnergy());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
		
	}
	public clsDataPoint convertHeartBeatSensor() {
		clsHeartbeatSensor oSensor = (clsHeartbeatSensor)(moSensorsInt.get(eSensorIntType.HEARTBEAT));	
		clsDataPoint oDataPoint = new clsDataPoint("HEART_BEAT",""+oSensor.getHeartbeat());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
	}
	public clsDataPoint convertSweatSensor() {
		clsSweatSensor oSensor = (clsSweatSensor)(moSensorsInt.get(eSensorIntType.SWEAT));	
		clsDataPoint oDataPoint = new clsDataPoint("SWEAT_INTENSITY",""+oSensor.getSweatRate());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
	}
	public clsDataPoint convertCryingSensor() {
		clsCryingSensor oSensor = (clsCryingSensor)(moSensorsInt.get(eSensorIntType.CRYING));	
		clsDataPoint oDataPoint = new clsDataPoint("CRYING_INTENSITY",""+oSensor.getCryingIntensity());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
	}
	public clsDataPoint convertMuscleTensionArmsSensor() {
		clsMuscleTensionSensor oSensor = (clsMuscleTensionSensor)(moSensorsInt.get(eSensorIntType.MUSCLE_TENSION_ARMS));	
		clsDataPoint oDataPoint = new clsDataPoint("MUSCLE_TENSION_ARMS_INTENSITY",""+oSensor.getMuscleTension());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
	}
	public clsDataPoint convertMuscleTensionLegsSensor() {
		clsMuscleTensionSensor oSensor = (clsMuscleTensionSensor)(moSensorsInt.get(eSensorIntType.MUSCLE_TENSION_LEGS));	
		clsDataPoint oDataPoint = new clsDataPoint("MUSCLE_TENSION_Legs_INTENSITY",""+oSensor.getMuscleTension());
		oDataPoint.setBufferType("SIGNAL");
		return oDataPoint;
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
		oRetVal.setBufferType("SIGNAL");
		//oData.setSensorType(poVisionType);
		// Get VISION SELF
		ArrayList<eSensorExtType> oVisionTypes = new ArrayList<eSensorExtType>();
		oVisionTypes.add(eSensorExtType.VISION_SELF);
		oVisionTypes.add(eSensorExtType.VISION_NEAR);
		oVisionTypes.add(eSensorExtType.VISION_MEDIUM);
		oVisionTypes.add(eSensorExtType.VISION_FAR);
		//oVisionTypes.add(eSensorExtType.VISION_CARRIED_ITEMS);


		//add vision sensors
		for(eSensorExtType oType : oVisionTypes){
			clsSensorVision oVision = (clsSensorVision)(moSensorsExt.get(oType));
			
			if(oVision != null){
				ArrayList<clsCollidingObject> oDetectedObjectList = oVision.getSensorData();
				
				for(clsCollidingObject oCollider : oDetectedObjectList){
					clsDataPoint oEntry = convertVisionEntry(oCollider, convertDistance(oType));
					oRetVal.addAssociation(oEntry);
				}
			}
		}
		
		//add sensor carried items
		HashMap<Double, ArrayList<clsCollidingObject>> oDetectedObjectList = moSensorsExt.get(eSensorExtType.VISION_CARRIED_ITEMS).moSensorData.getMeDetectedObject();
		
		for(clsCollidingObject oCollider : oDetectedObjectList.get(0.0)){
			clsDataPoint oEntry = convertVisionEntry(oCollider, "CARRYING");
			oRetVal.addAssociation(oEntry);
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
		
		if(oEntity != null){ 
		   oRetVal =  new clsDataPoint("ENTITY",getEntityType(collidingObj.moCollider).toString());
		   oRetVal.addAssociation(new clsDataPoint("SHAPE",getShapeType(oEntity)));
		   oRetVal.addAssociation(new clsDataPoint("COLOR",""+(((Color) oEntity.get2DShape().getPaint()).getRGB())));
		   oRetVal.addAssociation(new clsDataPoint("ENTITYID",oEntity.getId().toString()));
		   oRetVal.addAssociation(new clsDataPoint("BRIGHTNESS",getEntityBrightness(collidingObj.moCollider).toString()));
		   oRetVal.addAssociation(new clsDataPoint("ALIVE",""+oEntity.isAlive()));
		   oRetVal.addAssociation(new clsDataPoint("OBJECT_POSITION",collidingObj.meColPos.toString()));
		   oRetVal.addAssociation(new clsDataPoint("DEBUG_AROUSAL_VALUE",""+oEntity.getVisionBrightness()));
		   oRetVal.addAssociation(new clsDataPoint("DISTANCE",""+poDistance));
		   oRetVal.addAssociation(convertExpressionVariables(oEntity));
		   

		   if(oEntity.getBody() != null){
			   oRetVal.addAssociation(new clsDataPoint("BODY_INTEGRITY",""+oEntity.getBody().getBodyIntegrity()));
		   }
		   
		   //clsSensorPositionChange oSensor = (clsSensorPositionChange)(moSensorsExt.get(eSensorExtType.POSITIONCHANGE));
		   //clsPolarcoordinate oRel = collidingObj.mrColPoint;
			//oRel.moAzimuth = new Angle(clsSensorDataCalculation.normalizeRadian(oRel.moAzimuth.radians - oSensor.getLastPosition().getAngle().radians));
		   //clsDataPoint oPolar = new clsDataPoint("POLARCOORDINATE","");
		   //oPolar.addAssociation(new clsDataPoint("LENGTH",""+oRel.mrLength));
		   //oPolar.addAssociation(new clsDataPoint("RADIANS",""+oRel.moAzimuth.radians));
		   //oRetVal.addAssociation(oPolar);
		   
		   clsDataPoint oDebugPos = new clsDataPoint("DEBUG_POSITION","");
		   oDebugPos.addAssociation(new clsDataPoint("X",""+oEntity.getPosition().getX()));
		   oDebugPos.addAssociation(new clsDataPoint("Y",""+oEntity.getPosition().getY()));
		   oDebugPos.addAssociation(new clsDataPoint("ANGLE",""+ oEntity.getPose().getAngle().radians));

		   oRetVal.addAssociation(oDebugPos);

		   if(oEntity.getExecutedActions().size()>0){
			   clsDataPoint oActions =new clsDataPoint("ACTIONS","");
			   for(clsAction oAction :oEntity.getExecutedActions()){
				   oActions.addAssociation(convertVisionEntryAction(oAction));
			   }
			   oRetVal.addAssociation(oActions);
		   }
		}
		
		
		return oRetVal;
	}


	private clsDataPoint convertExpressionVariables(clsEntity poEntity){
		clsDataPoint oRetVal = new clsDataPoint("EXPRESSIONS","");

		if(poEntity.getBody() instanceof clsComplexBody){
			clsComplexBody body = (clsComplexBody) poEntity.getBody();
			ArrayList<clsExpressionVariable> expVariables = body.getInternalSystem().getBOrganSystem().getExpressionsList();
			for(clsExpressionVariable expVar : expVariables){
				
				
				if(expVar instanceof clsExpressionVariableCheeksRedning){
					oRetVal.addAssociation(new clsDataPoint(expVar.getName(),converstIntensity(expVar.getEIntensity())));
				} 
				else if(expVar instanceof clsExpressionVariableFacialEyeBrows){
					//clsDataPoint oData = new clsDataPoint(expVar.getName(),"");	
					
					
					oRetVal.addAssociation(new clsDataPoint("EYE_BROW_CENTER",convertEyeBrowValue(((clsExpressionVariableFacialEyeBrows) expVar).getEyeBrowsCenterUpOrDown())));
					oRetVal.addAssociation(new clsDataPoint("EYE_BROW_CORNERS",convertEyeBrowValue(((clsExpressionVariableFacialEyeBrows) expVar).getEyeBrowsCornersUpOrDown())));
					//oRetVal.add(oData);
				}
				else if(expVar instanceof clsExpressionVariableFacialEyes){
					oRetVal.addAssociation(new clsDataPoint(expVar.getName(),converstIntensity(expVar.getEIntensity())));

				}
				else if(expVar instanceof clsExpressionVariableFacialMouth){
					//clsDataPoint oData = new clsDataPoint(expVar.getName(),"");	
					oRetVal.addAssociation(new clsDataPoint("MOUTH_OPEN",converstMouthOpen(((clsExpressionVariableFacialMouth) expVar).getMouthOpen())));
					oRetVal.addAssociation(new clsDataPoint("MOUTH_SIDES",convertMouthSides(((clsExpressionVariableFacialMouth) expVar).getMouthSidesUpOrDown())));
					oRetVal.addAssociation(new clsDataPoint("MOUTH_STRECHINESS",converstMouthStretch(((clsExpressionVariableFacialMouth) expVar).getMouthStretchiness())));
					//oRetVal.add(oData);
				}
				else if(expVar instanceof clsExpressionVariableShake){
					oRetVal.addAssociation(new clsDataPoint(expVar.getName(),converstIntensity(expVar.getEIntensity())));
				}
				else{
					oRetVal.addAssociation(new clsDataPoint(expVar.getName(),converstIntensity(expVar.getEIntensity())));
				}
			}
				
			
			
		}
				
	
		
		return oRetVal;
	
	}
	
	public String convertEyeBrowValue(double value){
		if(value> 0.66) return "UP";
		else if (value>0.33) return "MIDDLE";
		else return "DOWN";
	}
	
	public String convertMouthSides(double value){
		if(value> 0.33) return "UP";
		else if (value>-0.33) return "NORMAL";
		else return "DOWN";
	}
	
	public String converstIntensity(double value){
		if(value> 0.66) return "HIGH";
		else if (value>0.33) return "MEDIUM";
		else return "LOW";
	}
	
	public String converstMouthOpen(double value){
		if(value> 0.66) return "OPEN";
		else if (value>0.33) return "HALF_OPEN";
		else return "CLOSED";
	}
	
	public String converstMouthStretch(double value){
		if(value> 0.66) return "STRETCHED";
		else if (value>0.33) return "HALF_STRETCHED";
		else return "NORMAL";
	}
	

	
	
	/**
	 * DOCUMENT (herret) - insert description
	 *
	 * @since 31.03.2014 15:41:19
	 *
	 * @param executedActions
	 * @return
	 */
	private clsDataPoint convertVisionEntryAction(clsAction poAction) {
		clsDataPoint oRetVal = new clsDataPoint("ACTION","");
		oRetVal.addAssociation(new clsDataPoint("ACTION_NAME",poAction.getActionName()));
		if(poAction.getCorrespondingEntity()!=null)  oRetVal.addAssociation(convertVisionEntryActionEntity(poAction.getCorrespondingEntity()));
		return oRetVal;
	}
	private clsDataPoint convertVisionEntryActionEntity(clsEntity poEntity) {
		clsDataPoint oRetVal = new clsDataPoint("CORRESPONDING_ENTITY", poEntity.getEntityType().toString());
		oRetVal.addAssociation(new clsDataPoint("COLOR", ""+ (((Color) poEntity.get2DShape().getPaint()).getRGB())));
		oRetVal.addAssociation(new clsDataPoint("SHAPE", getShapeType(poEntity)));
		oRetVal.addAssociation(new clsDataPoint("ALIVE", ""+ poEntity.isAlive()));

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
	
		//translate the Brightness Info to enum values
	private  eSaliency getEntityBrightness(PhysicalObject2D poObject) {
		clsEntity oEntity = getEntity(poObject);
		complexbody.io.sensors.datatypes.enums.eSaliency retVal = complexbody.io.sensors.datatypes.enums.eSaliency.UNDEFINED;
			if (oEntity != null) {
			double oVisionBrightness = oEntity.getVisionBrightness();
	        
	        if(oVisionBrightness  >= 0 && oVisionBrightness < 0.25  )
	        	retVal = complexbody.io.sensors.datatypes.enums.eSaliency.LOW;
	       if(oVisionBrightness  >= 0.25 && oVisionBrightness <0.5)
	       	retVal = complexbody.io.sensors.datatypes.enums.eSaliency.MEDIUM;
	        if(oVisionBrightness   >= 0.5 && oVisionBrightness <0.75)
	        	retVal = complexbody.io.sensors.datatypes.enums.eSaliency.HIGH;
	        if(oVisionBrightness   >= 0.75 && oVisionBrightness <1)
	        	retVal = complexbody.io.sensors.datatypes.enums.eSaliency.VERYHIGH;
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
	
	private  complexbody.io.sensors.datatypes.enums.eEntityType getEntityType(PhysicalObject2D poObject) {
		clsEntity oEntity = getEntity(poObject);
		
		if (oEntity != null) {
		  return getEntity(poObject).getEntityType();
		} else {
			return complexbody.io.sensors.datatypes.enums.eEntityType.UNDEFINED;
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
	

	private  String getShapeType(clsEntity poObject) {
		
		if (poObject.get2DShape() instanceof  Circle) {
			return "CIRCLE";
		}else if(poObject.get2DShape() instanceof  Rectangle){
			return "SQUARE";
		} else {
			return "UNDEFINED";
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
