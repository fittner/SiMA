/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import java.util.HashMap;
import java.util.Iterator;

import bw.body.clsBaseBody;
import bw.body.io.actuators.clsActionProcessor;
import bw.body.io.actuators.actionExecutors.*;
import decisionunit.itf.actions.*; 
import bw.body.io.sensors.external.clsSensorAcceleration;
import bw.body.io.sensors.external.clsSensorBump;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorExt;
import bw.body.io.sensors.external.clsSensorPositionChange;
import bw.body.io.sensors.external.clsSensorVision;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.utils.container.clsBaseConfig;
import bw.utils.container.clsConfigBoolean;
import bw.utils.container.clsConfigEnum;
import bw.utils.container.clsConfigList;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
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

	private clsActionProcessor moProcessor; 
	public HashMap<eSensorExtType, clsSensorExt> moSensorExternal;
	
	public clsEntity moEntity;
	
/**
	 * 
	 */
	public clsExternalIO(clsBaseBody poBody, clsEntity poEntity, clsConfigMap poConfig) {
		super(poBody, clsExternalIO.getFinalConfig(poConfig));
		
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

		applyConfig();
	}
	
	private void applyConfig() {
		
		//initialization of sensors
		if ( ((clsConfigBoolean)moConfig.get(eConfigEntries.ACTIVATE)).get() ) {
			initSensorExternal((clsConfigList)moConfig.get(eConfigEntries.EXTSENSORS), (clsConfigMap)moConfig.get(eConfigEntries.EXTSENSORCONFIG));
		}

	}

	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		
		clsConfigList oSensors = new clsConfigList();
		oSensors.add(new clsConfigEnum(eConfigEntries.ACCELERATION));
		oSensors.add(new clsConfigEnum(eConfigEntries.BUMP));
		oSensors.add(new clsConfigEnum(eConfigEntries.VISION));
		oSensors.add(new clsConfigEnum(eConfigEntries.EATABLE_AREA));	
		oSensors.add(new clsConfigEnum(eConfigEntries.POSITIONCHANGE));
		oDefault.add(eConfigEntries.EXTSENSORS, oSensors);
		

		clsConfigMap oSensorConfigs = new clsConfigMap();
		clsConfigMap oSC_Temp;
		
		oSC_Temp = new clsConfigMap();
		oSC_Temp.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		oSensorConfigs.add(eConfigEntries.ACCELERATION, oSC_Temp);
		
		oSC_Temp = new clsConfigMap();
		oSC_Temp.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		oSensorConfigs.add(eConfigEntries.BUMP, oSC_Temp);

		oSC_Temp = new clsConfigMap();
		oSC_Temp.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		oSensorConfigs.add(eConfigEntries.VISION, oSC_Temp);
		
		oSC_Temp = new clsConfigMap();
		oSC_Temp.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		oSensorConfigs.add(eConfigEntries.EATABLE_AREA, oSC_Temp);
		
		oSC_Temp = new clsConfigMap();
		oSC_Temp.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		oSensorConfigs.add(eConfigEntries.POSITIONCHANGE, oSC_Temp);
		
		oDefault.add(eConfigEntries.EXTSENSORCONFIG, oSensorConfigs);
		
		return oDefault;
	}	
		
	private void initSensorExternal(clsConfigList poExternalSensors, clsConfigMap poSensorConfigs) {
		Iterator<clsBaseConfig> i = poExternalSensors.iterator();
		while (i.hasNext()) {
			eConfigEntries eType = (eConfigEntries) ((clsConfigEnum)i.next()).get();
			clsConfigMap oConfig = (clsConfigMap)poSensorConfigs.get(eType);
			boolean nActivate = ((clsConfigBoolean)oConfig.get(eConfigEntries.ACTIVATE)).get();

			if (nActivate) {
				switch (eType) {
					case ACCELERATION: 
						moSensorExternal.put(eSensorExtType.ACCELERATION, new clsSensorAcceleration(moEntity, this, oConfig)); 
						break;
					case BUMP: 
						moSensorExternal.put(eSensorExtType.BUMP, new clsSensorBump(moEntity, this, oConfig)); 
						break;
					case VISION: 
						moSensorExternal.put(eSensorExtType.VISION, new clsSensorVision(moEntity, this, oConfig)); 
						break;
					case EATABLE_AREA: 
						moSensorExternal.put(eSensorExtType.EATABLE_AREA, new clsSensorEatableArea(moEntity, this, oConfig)); 
						break;
						
					case POSITIONCHANGE: 
						moSensorExternal.put(eSensorExtType.POSITIONCHANGE, new clsSensorPositionChange(moEntity, this, oConfig)); 
						break;						
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
