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

import enums.eActuatorExtType;
import bw.body.io.actuators.external.clsActuatorEat;
import bw.body.io.actuators.external.clsActuatorExt;
import bw.body.io.actuators.external.clsActuatorMove;
import bw.body.io.sensors.external.clsSensorAcceleration;
import bw.body.io.sensors.external.clsSensorBump;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorExt;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsAnimate;
import bw.entities.clsEntity;
import bw.utils.container.clsBaseConfig;
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
 * @author langr
 * 
 */
public class clsExternalIO extends clsBaseIO {

	
	public HashMap<eSensorExtType, clsSensorExt> moSensorExternal;
	public HashMap<eActuatorExtType, clsActuatorExt> moActuatorExternal;
	
	public clsEntity moEntity;
	
/**
	 * 
	 */
	public clsExternalIO(clsEntity poEntity, clsConfigMap poConfig) {
		super(poEntity, clsExternalIO.getFinalConfig(poConfig));
		
		moEntity = poEntity; //the entity for physics engine access
		
		moSensorExternal = new HashMap<eSensorExtType, clsSensorExt>();
		moActuatorExternal = new HashMap<eActuatorExtType, clsActuatorExt>();
		
		applyConfig();
		
		
		//initialization of actuators
		moActuatorExternal.put(eActuatorExtType.EAT , new clsActuatorEat((clsAnimate)moEntity, this));
		moActuatorExternal.put(eActuatorExtType.MOTION, new clsActuatorMove(moEntity, this));
	}
	
	private void applyConfig() {
		
		//initialization of sensors
		initSensorExternal((clsConfigList)moConfig.get(eConfigEntries.EXTSENSORS), (clsConfigMap)moConfig.get(eConfigEntries.EXTSENSORCONFIG));


	}

	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		clsConfigList oSensors = new clsConfigList();
		oSensors.add(new clsConfigEnum(eSensorExtType.ACCELERATION));
		oSensors.add(new clsConfigEnum(eSensorExtType.BUMP));
		oSensors.add(new clsConfigEnum(eSensorExtType.VISION));
		oSensors.add(new clsConfigEnum(eSensorExtType.EATABLE_AREA));	
		oDefault.add(eConfigEntries.EXTSENSORS, oSensors);
		

		clsConfigMap oSensorConfigs = new clsConfigMap();
		oSensorConfigs.add(eSensorExtType.ACCELERATION, new clsConfigMap());
		oSensorConfigs.add(eSensorExtType.BUMP, new clsConfigMap());
		oSensorConfigs.add(eSensorExtType.VISION, new clsConfigMap());
		oSensorConfigs.add(eSensorExtType.EATABLE_AREA, new clsConfigMap());
		oDefault.add(eConfigEntries.EXTSENSORCONFIG, oSensorConfigs);
		
		return oDefault;
	}	
		
	private void initSensorExternal(clsConfigList poExternalSensors, clsConfigMap poSensorConfigs) {
		Iterator<clsBaseConfig> i = poExternalSensors.iterator();
		while (i.hasNext()) {
			eSensorExtType eType = (eSensorExtType) ((clsConfigEnum)i.next()).get();
			clsConfigMap oConfig = (clsConfigMap)poSensorConfigs.get(eType);

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
			}
		}
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
	public void stepExecution(clsBrainActionContainer poActionList) {
		for (clsActuatorExt actuator : moActuatorExternal.values()) {
			actuator.updateActuatorData(poActionList);
		}
		
	}

}
