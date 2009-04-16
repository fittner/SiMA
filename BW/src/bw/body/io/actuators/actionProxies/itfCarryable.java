/**
 * @author Benny Dönz
 * 15.04.2009, 16:31:31
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionProxies;

/**
 * This interface must be implemented when an entity can be picked up by an agent. 
 * 
 * @author Benny Dönz
 * 15.04.2009, 16:31:31
 * 
 */

import bw.utils.enums.*;

public interface itfCarryable {
	/*
	 * Returns the weight of the object so energy and stamina consumption can be calculated
	 */
	double getWeight();
	
	/*
	 * Set the binding state of the object, so the object “knows” what is happening to it
	 */
	void setBindingState(eBindingState pBindingState);	
}
