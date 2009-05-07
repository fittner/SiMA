/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import java.util.ArrayList;
import java.util.HashMap;
import enums.eActuatorExtType;
import bw.body.clsAgentBody;
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
import bw.utils.container.clsConfigMap;
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
	public clsExternalIO(clsEntity poEntity, clsAgentBody poBody, clsConfigMap poConfig) {
		super(poBody.getInternalSystem().getInternalEnergyConsumption(), poConfig);
		
		moEntity = poEntity; //the entity for physics engine access
		
		moSensorExternal = new HashMap<eSensorExtType, clsSensorExt>();
		moActuatorExternal = new HashMap<eActuatorExtType, clsActuatorExt>();
		
		//initialization of sensors
		ArrayList<eSensorExtType> oExternalSensors = new ArrayList<eSensorExtType>();
		oExternalSensors.add(eSensorExtType.ACCELERATION);
		oExternalSensors.add(eSensorExtType.BUMP);
		oExternalSensors.add(eSensorExtType.VISION);
		oExternalSensors.add(eSensorExtType.EATABLE_AREA);
		
		initSensorExternal(oExternalSensors);
		
		//initialization of actuators
		moActuatorExternal.put(eActuatorExtType.EAT , new clsActuatorEat((clsAnimate)moEntity, this));
		moActuatorExternal.put(eActuatorExtType.MOTION, new clsActuatorMove(moEntity, this));
	}
	
	protected clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		//TODO add default values
		return oDefault;
	}	
		
	private void initSensorExternal(ArrayList<eSensorExtType> poExternalSensors) {
		for (eSensorExtType eType:poExternalSensors) {
			switch (eType) {
				case ACCELERATION: moSensorExternal.put(eSensorExtType.ACCELERATION, new clsSensorAcceleration(moEntity, this)); break;
				case BUMP: moSensorExternal.put(eSensorExtType.BUMP, new clsSensorBump(moEntity, this)); break;
				case VISION: moSensorExternal.put(eSensorExtType.VISION ,new clsSensorVision(moEntity, this)); break;
				case EATABLE_AREA: moSensorExternal.put(eSensorExtType.EATABLE_AREA ,new clsSensorEatableArea(moEntity, this, clsSensorEatableArea.DEFAULTVISIONOFFSETT)); break;
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
