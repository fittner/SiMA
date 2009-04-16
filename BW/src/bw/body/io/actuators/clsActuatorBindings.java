/**
 * @author Benny Dönz
 * 15.04.2009, 17:56:53
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators;

import java.util.ArrayList;
import bw.body.io.actuators.actionProxies.itfCarryable;
import bw.exceptions.*;

/**
 * This class manages the bindings of an agent and entities it is carrying. 
 * Only the get-methods should be called by other entities, the set-methods 
 * are called by the appropriate action commands. 
 * 
 * @author Benny Dönz
 * 15.04.2009, 17:56:53
 * 
 */
public class clsActuatorBindings {

	private itfCarryable moCarriedObject = null;
	private ArrayList<itfCarryable> moInventory = new ArrayList<itfCarryable>();
	
	public itfCarryable getCarriedObject() {
		return moCarriedObject;
	}

	/*
	 * Set the carried object. An exception is thrown when already carrying something
	 */
	public void setCarriedObject(itfCarryable poObject) throws exInvalidBinding {
		if (moCarriedObject != null) throw(new exInvalidBinding());
		moCarriedObject = poObject;
	}

	/*
	 * Array of objects in the inventory
	 */
	public ArrayList<itfCarryable> getInventoryObjects() {
		return moInventory;
	}
	
	/*
	 * Moves the carried object to the inventory. Exception thrown 
	 * when no object is being carried
	 */
	public void toInventory() throws exInvalidBinding  {
		if (moCarriedObject == null) throw(new exInvalidBinding());
		moInventory.add(moCarriedObject);
		moCarriedObject=null;
	}

	/*
	 * Moves the object from the inventory and sets it as carried object. Exception is 
	 * thrown when already carrying an object or when the object is not in the inventory
	 */
	public void fromInventory(itfCarryable poObject)  throws exInvalidBinding {
		if (moCarriedObject != null) throw(new exInvalidBinding());
		if (!moInventory.contains(poObject)) throw(new exInvalidBinding());
		moInventory.remove(poObject);
		moCarriedObject=poObject;
	}
	
}
