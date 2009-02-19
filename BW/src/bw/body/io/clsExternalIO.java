/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import java.util.ArrayList;

import bw.clsEntity;
import bw.body.clsAgentBody;
import bw.body.io.actuators.external.clsActuatorExt;
import bw.body.io.sensors.external.*;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 
 */
public class clsExternalIO extends clsBaseIO {

	
	public ArrayList<clsSensorExt> moSensorExternal;
	public ArrayList<clsActuatorExt> moActuatorExternal;
	
	public clsAgentBody moBody;
	public clsEntity moEntity;
	
/**
	 * 
	 */
	public clsExternalIO(clsEntity poEntity, clsAgentBody poBody) {
		super(poBody.getInternalSystem().getInternalEnergyConsumption());
		
		moBody = poBody; 	 //the agents body
		moEntity = poEntity; //the entity for physics engine access
		
		moSensorExternal = new ArrayList<clsSensorExt>();
		moActuatorExternal = new ArrayList<clsActuatorExt>();
		
		//initialization of sensors
		moSensorExternal.add(new clsSensorAcceleration(moEntity, this));
		moSensorExternal.add(new clsSensorBump(moEntity, this));
		
	}

	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	@Override
	public void step() {
		
		for (clsSensorExt sensor : moSensorExternal) {
			sensor.updateSensorData();
		}
	}

}
