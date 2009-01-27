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
import bw.body.itfStep;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 
 */
public class clsExternalIO implements itfStep {

	
	private ArrayList<clsSensorExt> moSensorExternal;
	private ArrayList<clsActuatorExt> moActuatorExternal;
	
	public clsAgentBody moBody;
	public clsEntity moEntity;
	
/**
	 * 
	 */
	public clsExternalIO(clsEntity poEntity, clsAgentBody poBody) {
		moBody = poBody; 	 //the agents body
		moEntity = poEntity; //the entity for physics engine access
		
		moSensorExternal = new ArrayList<clsSensorExt>();
		moActuatorExternal = new ArrayList<clsActuatorExt>();
		
		//initialization of sensors
		moSensorExternal.add(new clsSensorAcceleration());
		
	}

/* (non-Javadoc)
 * @see bw.body.itfStep#step()
 */
@Override
public void step() {
	
	//moSensorExternal.updateSensorData(moEntity);
}

}
