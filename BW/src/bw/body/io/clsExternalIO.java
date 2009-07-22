/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import java.util.HashMap;
import bw.body.clsBaseBody;
import bw.body.io.actuators.clsActionProcessor;
import bw.body.io.actuators.actionExecutors.*;
import decisionunit.itf.actions.*; 
import bw.body.io.sensors.ext.clsSensorEngine;
import bw.body.io.sensors.external.clsSensorAcceleration;
import bw.body.io.sensors.external.clsSensorBump;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorExt;
import bw.body.io.sensors.external.clsSensorPositionChange;
//HZ -- integration Sensor Engine
//import bw.body.io.sensors.ext.clsSensorVisionNEW;
//import bw.entities.clsRemoteBot;

import bw.body.io.sensors.external.clsSensorVision;
import bw.body.io.sensors.external.clsSensorRadiation;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.utils.config.clsBWProperties;
import enums.eSensorExtType;

/**
 * TODO (langr) - THIS CLASS NEEDS A REFACTORING - reason: not every
 * entity has the same Sensors/Actuators
 * Possible Solution without refactor: derivate subclasses that override the ctor
 * 
 * This class holds one list for all sensor-instances and 
 *                  one list for all actuator instances
 * within the current entity.
 * 
 * The method step is automatically called from clsBody and ensures
 * the sensor update for all sensors during the sensing-cycle.
 * 
 * TODO (langr) - the same should be done with the actuators:
 * The method @@@ is automatically called from clsBody and ensures
 * the actuator-execution for all actuators during the execution-cycle.
 * 
 * BD - adapted the class to host the actionprocessor
 * 
 * @author langr
 * 
 */
public class clsExternalIO extends clsBaseIO {
	public static final String P_NUMSENSORS = "numsensors";
	public static final String P_SENSORTYPE = "sensortype";
	public static final String P_SENSORACTIVE = "sensoractive";	
	
	private clsActionProcessor moProcessor; 
	public  clsSensorEngine moSensorEngine;
	public HashMap<eSensorExtType, clsSensorExt> moSensorExternal;
		
	public clsEntity moEntity;

	public clsExternalIO(String poPrefix, clsBWProperties poProp, clsBaseBody poBody, clsEntity poEntity) {
		super(poPrefix, poProp, poBody);
		moEntity = poEntity; //the entity for physics engine access
				
		moSensorExternal = new HashMap<eSensorExtType, clsSensorExt>();
		moProcessor = new clsActionProcessor(poEntity);
				
		moProcessor.addCommand(clsActionMove.class, new clsExecutorMove(poEntity, 10));
		moProcessor.addCommand(clsActionTurn.class, new clsExecutorTurn(poEntity));
		moProcessor.addCommand(clsActionEat.class, new clsExecutorEat(poEntity,eSensorExtType.EATABLE_AREA,1));
		moProcessor.addCommand(clsActionKill.class, new clsExecutorKill(poEntity,eSensorExtType.EATABLE_AREA,1));
		
		if (poEntity instanceof clsMobile) {
			moProcessor.addCommand(clsActionPickUp.class, new clsExecutorPickUp((clsMobile) poEntity,eSensorExtType.EATABLE_AREA ,0.01f));
			moProcessor.addCommand(clsActionDrop.class, new clsExecutorDrop((clsMobile) poEntity));
			moProcessor.addCommand(clsActionFromInventory.class, new clsExecutorFromInventory((clsMobile) poEntity));
			moProcessor.addCommand(clsActionToInventory.class, new clsExecutorToInventory((clsMobile) poEntity));
		}

		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_NUMSENSORS, 6);
		
		oProp.putAll( clsSensorAcceleration.getDefaultProperties( pre+"0") );
		oProp.setProperty(pre+"0."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+"0."+P_SENSORTYPE, eSensorExtType.ACCELERATION.toString());
				
		oProp.putAll( clsSensorBump.getDefaultProperties( pre+"1") );
		oProp.setProperty(pre+"1."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+"1."+P_SENSORTYPE, eSensorExtType.BUMP.toString());
				
		oProp.putAll( clsSensorVision.getDefaultProperties( pre+"2") );
		oProp.setProperty(pre+"2."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+"2."+P_SENSORTYPE, eSensorExtType.VISION.toString());
		
		oProp.putAll( clsSensorRadiation.getDefaultProperties( pre+"3") );
		oProp.setProperty(pre+"3."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+"3."+P_SENSORTYPE, eSensorExtType.RADIATION.toString());
		
		oProp.putAll( clsSensorEatableArea.getDefaultProperties( pre+"4") );
		oProp.setProperty(pre+"3."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+"3."+P_SENSORTYPE, eSensorExtType.EATABLE_AREA.toString());
		
		oProp.putAll( clsSensorPositionChange.getDefaultProperties( pre+"5") );
		oProp.setProperty(pre+"3."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+"3."+P_SENSORTYPE, eSensorExtType.POSITIONCHANGE.toString());		
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);

		int num = poProp.getPropertyInt(pre+P_NUMSENSORS);
		for (int i=0; i<num; i++) {
			String tmp_pre = pre+i+".";
			
			boolean nActive = poProp.getPropertyBoolean(tmp_pre+P_SENSORACTIVE);
			if (nActive) {
				String oType = poProp.getPropertyString(tmp_pre+P_SENSORTYPE);
				eSensorExtType eType = eSensorExtType.valueOf(oType);
				
				switch(eType) {
					case ACCELERATION:
						moSensorExternal.put(eType, new clsSensorAcceleration(tmp_pre, poProp)); 
						break;
					case BUMP:
						moSensorExternal.put(eType, new clsSensorBump(tmp_pre, poProp, this, moEntity)); 
						break;
					case VISION:
						moSensorExternal.put(eType, new clsSensorVision(tmp_pre, poProp, this, moEntity)); 
						break;
					case RADIATION:
						moSensorExternal.put(eType, new clsSensorRadiation(tmp_pre, poProp, this, moEntity)); 
						break;
					case EATABLE_AREA:
						moSensorExternal.put(eType, new clsSensorEatableArea(tmp_pre, poProp, this, moEntity)); 
						break;
					case POSITIONCHANGE:
						moSensorExternal.put(eType, new clsSensorPositionChange(tmp_pre, poProp, this, moEntity)); 
						break;
						
					default:
						throw new java.lang.NoSuchMethodError(eType.toString());
				}
			}
		}

		
	}	
		
	
	public clsActionProcessor getActionProcessor() {
		return moProcessor;
	}
	
	
	/* (non-Javadoc)
	 *
	 * each sensor has to be updated to guarantee valid data for the next cycle 'processing'
	 *
	 * @author langr
	 * 25.02.2009, 16:50:36
	 * 
	 * @see bw.body.itfStepSensing#stepSensing()
	 */
	public void stepSensing() {
		//moSensorEngine.updateSensorData(); // HZ integration of the Sensor Engine
		
		for (clsSensorExt sensor : moSensorExternal.values()) {
			sensor.updateSensorData();
		}
	}
	
	/* (non-Javadoc)
	 *
	 * each actuator must execute the commands originated from the processing-cycle within the 'brain',
	 * within the 'mind'
	 *
	 * @author langr
	 * 25.02.2009, 16:50:36
	 * 
	 * @see bw.body.itfStepExecution#stepExecution()
	 */
	public void stepExecution() {
		moProcessor.dispatch();
	}

}
