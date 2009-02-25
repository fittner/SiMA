/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators;

import java.util.ArrayList;

import bw.body.motionplatform.clsBrainAction;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public interface itfActuatorUpdate {

	void updateActuatorData(ArrayList<clsBrainAction> poActionList);
	
}
