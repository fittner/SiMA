/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package nao.body.brainsocket;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import config.clsBWProperties;

import du.enums.eAntennaPositions;
import du.enums.eFastMessengerSources;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.enums.eSlowMessenger;
import du.itf.itfDecisionUnit;
import du.itf.itfProcessKeyPressed;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsBump;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsEatableArea;
import du.itf.sensors.clsEatableAreaEntry;
import du.itf.sensors.clsEnergy;
import du.itf.sensors.clsEnergyConsumption;
import du.itf.sensors.clsFastMessenger;
import du.itf.sensors.clsHealthSystem;
import du.itf.sensors.clsManipulateArea;
import du.itf.sensors.clsManipulateAreaEntry;
import du.itf.sensors.clsPositionChange;
import du.itf.sensors.clsRadiation;
import du.itf.sensors.clsSensorData;
import du.itf.sensors.clsSlowMessenger;
import du.itf.sensors.clsStaminaSystem;
import du.itf.sensors.clsStomachTension;
import du.itf.sensors.clsTemperatureSystem;
import du.itf.sensors.clsVision;
import du.itf.sensors.clsVisionEntry;


import nao.body.itfStepProcessing;


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
	private itfActionProcessor moActionProcessor; //reference
//	private HashMap<eSensorExtType, clsSensorExt> moSensorsExt; //reference
//	private HashMap<eSensorIntType, clsSensorInt> moSensorsInt; //reference
//	private clsSensorDataCalculation moSensorCalculation;
	
	public clsBrainSocket(itfActionProcessor poActionProcessor) {
		moActionProcessor=poActionProcessor;
//		moSensorsExt = poSensorsExt;
//		moSensorsInt = poSensorsInt;

//		applyProperties(poPrefix, poProp);
	}

	private clsSensorData convertSensorData() {
		clsSensorData oData = new clsSensorData();
		
		//ZEILINGER - Integration of the Sensor Engine
//		oData.addSensorExt(eSensorExtType.BUMP, convertBumpSensor() );
//		oData.addSensorExt(eSensorExtType.POSITIONCHANGE, convertPositionChangeSensor(eSensorExtType.POSITIONCHANGE) );

//		//HZ VISION Sensor is required for lynx and hares setup; 
//		oData.addSensorExt(eSensorExtType.VISION, convertVisionSensor(eSensorExtType.VISION) );
//		oData.addSensorExt(eSensorExtType.VISION_NEAR, convertVisionSensor(eSensorExtType.VISION_NEAR) );
	
		return oData;
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
