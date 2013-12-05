/**
 * @author Benny Doenz
 * 15.04.2009, 16:31:31
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionProxies;

import bw.entities.base.clsMobile;
import bw.utils.enums.*;

/**
 * Proxy-Interface for actions PickUp, Drop, ToInventory, FromInventory
 * Method getCarryableEntity: return a reference to the entity 
 * Method setCarriedBindingState: Is called when Binding state changes (to/from Inventory or picked up/dropped) 
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:38:56
 * 
 */

public interface itfAPCarryable {
	/*
	 * Returns a reference to the entity. Reference is used to determine the weight and to 
	 * add/remove object from the Simulation when added to the inventory. Return null if entity 
	 * currently can not be picked up 
	 */
	clsMobile getCarryableEntity();
	
	/*
	 * Set the binding state of the object, so the object “knows” what is happening to it
	 */
	void setCarriedBindingState(eBindingState pBindingState);	
}
