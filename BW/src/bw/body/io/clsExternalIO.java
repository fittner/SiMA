/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import java.util.ArrayList;

import sim.physics2D.util.Double2D;

import bw.body.clsAgentBody;
import bw.body.io.actuators.external.*;
import bw.body.io.sensors.external.*;
import bw.body.motionplatform.clsBrainAction;
import bw.entities.clsAnimate;
import bw.entities.clsEntity;

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

	
	public ArrayList<clsSensorExt> moSensorExternal;
	public ArrayList<clsActuatorExt> moActuatorExternal;
	
	public clsEntity moEntity;
	
/**
	 * 
	 */
	public clsExternalIO(clsEntity poEntity, clsAgentBody poBody) {
		super(poBody.getInternalSystem().getInternalEnergyConsumption());
		
		moEntity = poEntity; //the entity for physics engine access
		
		moSensorExternal = new ArrayList<clsSensorExt>();
		moActuatorExternal = new ArrayList<clsActuatorExt>();
		
		//initialization of sensors
		moSensorExternal.add(new clsSensorAcceleration(moEntity, this));
		moSensorExternal.add(new clsSensorBump(moEntity, this));
		moSensorExternal.add(new clsSensorVision(moEntity, this));
		
		moSensorExternal.add(new clsSensorEatableArea(moEntity, this, new Double2D(20,0) ));
		
		//initialization of actuators
		moActuatorExternal.add(new clsActuatorEat((clsAnimate)moEntity, this));
		moActuatorExternal.add(new clsActuatorMove(moEntity, this));
		
		
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
	@Override
	public void stepSensing() {
		for (clsSensorExt sensor : moSensorExternal) {
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
	@Override
	public void stepExecution(ArrayList<clsBrainAction> poActionList) {
		for (clsActuatorExt actuator : moActuatorExternal) {
			actuator.updateActuatorData(poActionList);
		}
		
	}


}
