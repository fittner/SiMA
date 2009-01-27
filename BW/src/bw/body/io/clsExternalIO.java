/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import java.util.ArrayList;

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
	
	public clsAgentBody moBody = null;
	
/**
	 * 
	 */
	public clsExternalIO(clsAgentBody poBody) {
		//the agents body
		moBody = poBody;
		
		moSensorExternal = new ArrayList<clsSensorExt>();
		moActuatorExternal = new ArrayList<clsActuatorExt>();
		
		moSensorExternal.add(new clsSensorAcceleration());
		
	}

/* (non-Javadoc)
 * @see bw.body.itfStep#step()
 */
@Override
public void step() {
	// TODO Auto-generated method stub
	
}

}
