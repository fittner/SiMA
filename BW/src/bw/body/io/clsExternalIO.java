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

	
	private ArrayList<clsSensorExt> moSensorExternal = new ArrayList<clsSensorExt>();
	private ArrayList<clsActuatorExt> moActuatorExternal = new ArrayList<clsActuatorExt>();
	
	/**
	 * 
	 */
	public clsExternalIO() {
		
		moSensorExternal.add(new clsSensorAcceleration());
		
	}

}
