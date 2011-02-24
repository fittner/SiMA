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
import NAOProxyClient.SensorTuple;
import NAOProxyClient.SensorVision;
import NAOProxyClient.eSensors;
import du.enums.eSensorExtType;
import du.itf.itfDecisionUnit;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsSensorData;
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

	private clsSensorData convertSensorData() {
		clsSensorData oData = new clsSensorData();
		
		//HIER UMWANDELN von NAO Sensordaten in ARS Sensordaten
		
		for(Sensor oSensor : moSensors) {
			
			switch(oSensor.id )
			{
				case BATTERY:
					break;
				case BUMP:
					break;
				case CONSUMESUCCESS:
					break;
				case FSR:
					break;
				case ODOMETRY:
					break;
				case POSITION:
					break;
				case SENTINEL:
					break;
				case SONAR:
					break;
				case TEMPERATURE:
					break;
				case UNKNOWN:
					break;
				case VISION:
				{
					for (SensorTuple data:oSensor.values) {
						SensorVision oVisionEntry = (SensorVision)data;
						
						double oVisionDistance = oVisionEntry.getR();
						
						if(oVisionDistance  >= 0 && oVisionDistance <_NAO_NEAR_DISTANCE  )
							oData.addSensorExt(eSensorExtType.VISION_NEAR, convertNAOVision2DUVision(oVisionEntry, eSensorExtType.VISION_NEAR));
						if(oVisionDistance  >= _NAO_NEAR_DISTANCE && oVisionDistance <_NAO_MEDIUM_DISTANCE)
							oData.addSensorExt(eSensorExtType.VISION_MEDIUM, convertNAOVision2DUVision(oVisionEntry, eSensorExtType.VISION_MEDIUM));
						if(oVisionDistance  >= _NAO_MEDIUM_DISTANCE)
							oData.addSensorExt(eSensorExtType.VISION_FAR, convertNAOVision2DUVision(oVisionEntry, eSensorExtType.VISION_FAR));
					}
					break;
				}
				default:throw new java.lang.NullPointerException("NAO sensor type not implemented");
			}
			
		}

		return oData;
	}
	

	//creates one vision entry transformed to ARS vision types
	private clsVision convertNAOVision2DUVision(SensorVision poNAOSensorVision, eSensorExtType poVisionType){
		clsVision oData = new clsVision();
		oData.setSensorType(poVisionType);

		//the real conversion
		clsVisionEntry oEntry = convertVisionEntry(poNAOSensorVision);
		oData.add(oEntry);
		
		return oData;
	}
	
	
	//the real deep transformation to ARS vision data
	private clsVisionEntry convertVisionEntry(SensorVision poNAOSensorVisionData) {
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
	private eSide getObjectPosition(SensorVision poNAOSensorVisionData){
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
			moDecisionUnit.update(convertSensorData());
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
