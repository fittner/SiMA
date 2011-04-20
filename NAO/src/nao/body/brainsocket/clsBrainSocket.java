/**
 * @author mucha
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package nao.body.brainsocket;

import java.awt.Color;
import java.util.Vector;
import bfg.utils.enums.eSide;
import NAOProxyClient.Sensor;
import NAOProxyClient.SensorValueBump;
import NAOProxyClient.SensorValueDouble;
import NAOProxyClient.SensorValueTuple;
import NAOProxyClient.SensorValueVision;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.itf.itfDecisionUnit;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsBump;
import du.itf.sensors.clsEnergy;
import du.itf.sensors.clsEnergyConsumption;
import du.itf.sensors.clsSensorData;
import du.itf.sensors.clsTemperatureSystem;
import du.itf.sensors.clsVision;
import du.itf.sensors.clsVisionEntry;
import nao.body.itfStepProcessing;


/**
 * The brain is the container for the mind and has a direct connection to external and internal IO.
 * Done: re-think if we insert a clsCerebellum for the neuroscientific perception-modules like R. Velik.
 * Answer: moSymbolization -> all done in another project
 * 
 * @author muchistch
 * 
 */
public class clsBrainSocket implements itfStepProcessing {
	
	public  final double _NAO_NEAR_DISTANCE = 1;
	public  final double _NAO_MEDIUM_DISTANCE = 5;
	public  final double _NAO_FAR_DISTANCE = 20;
	public  final double _NAO_AREA_OF_VIEW_RADIANS = Math.PI/2;
	

	private itfDecisionUnit moDecisionUnit; //reference
	private itfActionProcessor moActionProcessor; //reference
	private Vector<Sensor> moSensors;
	
	public clsBrainSocket(itfActionProcessor poActionProcessor, Vector<Sensor> poSensordata) {
		moActionProcessor=poActionProcessor;
		moSensors = poSensordata;
	}

	private clsSensorData convertSensorData() throws Exception {
		clsSensorData oData = new clsSensorData();
		
		//HIER UMWANDELN von NAO Sensordaten in ARS Sensordaten
		
		for(Sensor oSensor : moSensors) {
			
			switch(oSensor.id )
			{
				case BATTERY:		processBattery(oSensor, oData); break;
				case BUMP:      	processBump(oSensor, oData); break;
				case CONSUMESUCCESS:processConsumeSuccess(oSensor, oData); break;
				case FSR:			processFSR(oSensor, oData); break;
				case ODOMETRY:  	processOdometry(oSensor, oData); break;
				case POSITION:  	processPosition(oSensor, oData); break;
				case SENTINEL:  	processSentinel(oSensor, oData); break;
				case SONAR:     	processSonar(oSensor, oData); break;
				case TEMPERATURE: 	processTemperature(oSensor, oData); break;
				case VISION: 	  	processVision(oSensor, oData); break;

//				case UNKNOWN:
//					break;
				default:throw new java.lang.NullPointerException("NAO sensor type not implemented");
			}
			
		}

		return oData;
	}
		
	private void processBattery(Sensor poSensor, clsSensorData poData) throws Exception {
		for (SensorValueTuple value:poSensor.values) {
			SensorValueDouble v = (SensorValueDouble)value;
			
			if (v.getName().equals("Battery/Charge")) {
				clsEnergy oE = new clsEnergy();
				oE.setEnergy(v.getDouble());
				poData.addSensorInt(eSensorIntType.ENERGY, oE);
			} else if (v.getName().equals("Battery/Current")) {
				clsEnergyConsumption oEC = new clsEnergyConsumption();
				oEC.setEnergyConsumption(v.getDouble());
				poData.addSensorInt(eSensorIntType.ENERGY_CONSUMPTION, oEC);
			} else {
				throw new java.lang.Exception("unkown battery sensory key '"+v.getName()+"'");
			}
		}
	}
	
	private void processBump(Sensor poSensor, clsSensorData poData) {
		clsBump bumped = new clsBump(false);
		
		for (SensorValueTuple value:poSensor.values) {
			if (((SensorValueBump)value).getBumped()) {
				bumped.setBumped(true);
			}
		}
		poData.addSensorExt(eSensorExtType.BUMP, bumped);
	}
	
	private void processConsumeSuccess(Sensor poSensor, clsSensorData poData) {
		processSENSORNOTIMPLEMENTED(poSensor);
	}
	private void processFSR(Sensor poSensor, clsSensorData poData) {
		// left empty on purpose
	}
	private void processOdometry(Sensor poSensor, clsSensorData poData) {
		// left empty on purpose
	}
	private void processPosition(Sensor poSensor, clsSensorData poData) {
		// left empty on purpose
	}
	private void processSentinel(Sensor poSensor, clsSensorData poData) {
		// left empty on purpose
	}
	private void processSonar(Sensor poSensor, clsSensorData poData) {
		// left empty on purpose
	}
	private void processTemperature(Sensor poSensor, clsSensorData poData) {
		clsTemperatureSystem temperature = new clsTemperatureSystem();
		double max = 0;
		for (SensorValueTuple value:poSensor.values) {
			double temp = ((SensorValueDouble)value).getDouble();
			if (temp > max) {
				max = temp;
			}
		}
		temperature.setTemperatureValue(max);
		poData.addSensorInt(eSensorIntType.TEMPERATURE, temperature);
	}
	
	private void processSENSORNOTIMPLEMENTED(Sensor poSensor) {
		System.out.println("processSensor not implemented. "+poSensor);
	}
	
	private void processVision(Sensor poSensor, clsSensorData poData) {
		for (SensorValueTuple data:poSensor.values) {
			SensorValueVision oVisionEntry = (SensorValueVision)data;
			
			double oVisionDistance = oVisionEntry.getR();
			
			if(oVisionDistance  >= 0 && oVisionDistance <_NAO_NEAR_DISTANCE  )
				poData.addSensorExt(eSensorExtType.VISION_NEAR, convertNAOVision2DUVision(oVisionEntry, eSensorExtType.VISION_NEAR));
			if(oVisionDistance  >= _NAO_NEAR_DISTANCE && oVisionDistance <_NAO_MEDIUM_DISTANCE)
				poData.addSensorExt(eSensorExtType.VISION_MEDIUM, convertNAOVision2DUVision(oVisionEntry, eSensorExtType.VISION_MEDIUM));
			if(oVisionDistance  >= _NAO_MEDIUM_DISTANCE)
				poData.addSensorExt(eSensorExtType.VISION_FAR, convertNAOVision2DUVision(oVisionEntry, eSensorExtType.VISION_FAR));
		}		
	}
	

	//creates one vision entry transformed to ARS vision types
	private clsVision convertNAOVision2DUVision(SensorValueVision poNAOSensorVision, eSensorExtType poVisionType){
		clsVision oData = new clsVision();
		oData.setSensorType(poVisionType);

		//the real conversion
		clsVisionEntry oEntry = convertVisionEntry(poNAOSensorVision);
		oData.add(oEntry);
		
		return oData;
	}
	
	
	//the real deep transformation to ARS vision data
	private clsVisionEntry convertVisionEntry(SensorValueVision poNAOSensorVisionData) {
		clsVisionEntry oData = new clsVisionEntry();
		
		//set data, for all objects these are the same
		oData.setObjectPosition( getObjectPosition(poNAOSensorVisionData) );
		oData.setEntityId(poNAOSensorVisionData.getName());
		
	
		switch(poNAOSensorVisionData.getType())
		{
			case BUBBLE:
			{
				oData.setEntityType(du.enums.eEntityType.BUBBLE);
				oData.setAlive(true);
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( new Color(0,200,0) );
				break;
			}
			
			case CAKE:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.CAKE );
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( Color.PINK );
				//oData.setPolarcoordinate(oRel);
				break;
			}
				
			case  CAN:
			{
				oData.setEntityType(du.enums.eEntityType.CAN);
				break;
			}
				
			case  STONE:
			{
				oData.setAlive(false);
				oData.setEntityType( du.enums.eEntityType.STONE );
				oData.setShapeType( du.enums.eShapeType.CIRCLE );
				oData.setColor( Color.DARK_GRAY );
				break;
			}
				
			case  UNKNOWN:
			{
				oData.setEntityType(du.enums.eEntityType.UNDEFINED);
				break;
			}
				
			default:throw new java.lang.NullPointerException("NAO vision type not implemented");	
		}

		return oData;
	}
	

	//convert the objects from the nao vision to symbols where the object is
	private eSide getObjectPosition(SensorValueVision poNAOSensorVisionData){
		eSide oSide = eSide.UNDEFINED;
		
		//where is the object
		if (Math.abs(poNAOSensorVisionData.getA()) <= _NAO_AREA_OF_VIEW_RADIANS/18)
		{
			oSide = eSide.CENTER;
		}
		else if(Math.abs(poNAOSensorVisionData.getA()) <= _NAO_AREA_OF_VIEW_RADIANS/4)
		{
			if (poNAOSensorVisionData.getA()<0)
			{
				oSide = eSide.MIDDLE_LEFT;
			}
			else 
			{
				oSide = eSide.MIDDLE_RIGHT;
			}
		}
		else {
				if(poNAOSensorVisionData.getA()<0){
					oSide = eSide.LEFT; 
				}
				else{
					oSide = eSide.RIGHT; 
				}
		}
		return oSide;
	}
	
	
	
	
		
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
//	public void stepProcessing(clsAnimate poAnimate, clsBrainActionContainer poActionList) {
	@Override
	public void stepProcessing() {
		if (moDecisionUnit != null) {
			try {
				moDecisionUnit.update(convertSensorData());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			moDecisionUnit.process();
			moDecisionUnit.updateActionProcessorToHTML();
		} 
	}
	

	/*************************************************
	 *         GETTER & SETTER
	 ************************************************/

	public itfDecisionUnit getDecisionUnit() {
		return moDecisionUnit;		
	}

	public void setDecisionUnit(itfDecisionUnit poDecisionUnit) {
		moDecisionUnit = poDecisionUnit;
		moDecisionUnit.setActionProcessor(moActionProcessor);
	}

	
}
