/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import java.util.ArrayList;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 
 */
public class clsExternalIO {

	
	private ArrayList<clsSensorExt> moSensorExternal;
	private ArrayList<clsActuatorExt> moActuatorExternal;
	
/**
	 * 
	 */
	public clsExternalIO() {
		moSensorExternal = new ArrayList<clsSensorExt>();
		moActuatorExternal = new ArrayList<clsActuatorExt>();
		
		moSensorExternal.add(new clsSensorAcceleration());
		
	}

}
