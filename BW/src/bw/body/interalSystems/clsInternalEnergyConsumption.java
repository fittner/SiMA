/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.interalSystems;

import java.util.HashMap;
import java.util.Iterator;
import bw.utils.datatypes.clsMutableInteger;

/**
 * The class energy consumption maintains a list where all active objects of the agent 
 * can register and update their current energy consumption. this value remains until 
 * the object updates the value. at the end of each round, the total of all values is 
 * calculated and subtracted from the energy.
 * 
 * TODO exchange clsMutableInteger by a mutable fixed width value
 * TODO enable the possibility to add consumption of a specific nutrition 
 * 
 * @author deutsch
 * 
 */
public class clsInternalEnergyConsumption {
	private HashMap<Integer, clsMutableInteger> moList; // this list stores all registered values.
	int mnSum; 											// stores the sum of all values within moList.
	boolean mnDirtyFlag; 								// set to true if moList has been changed.
	
	/**
	 * This constructor initializes moList with an empty HashMap, mnDirtyFlag is set to true, and mnSum is set to 0.
	 */
	public clsInternalEnergyConsumption() {
		moList = new HashMap<Integer, clsMutableInteger>();
		mnDirtyFlag = true;
		mnSum = 0;
	}
	
	
	/**
	 * returns a clone of the complete list of stored values
	 * 
	 * TODO Type safety: Unchecked cast from Object to HashMap<Integer,clsMutableInteger>
	 *
	 * @return moList clone
	 */
	public HashMap<Integer, clsMutableInteger> getList() {
		return (HashMap<Integer, clsMutableInteger>) moList.clone();
	}
	
	/**
	 * Adds or updates the value pnValue of key pnKey. If no entry for pnKey is found, a new one is generated.
	 * If pnKey exists, the object is updated to the value of pnKey. As HashMap accepts keys as objects only, pnKey is
	 * converted into an object of type Integer and setValue(Integer poKey, int pnValue) is called.
	 *
	 * @param pnKey - the key 
	 * @param pnValue - the value
	 */
	public void setValue(int pnKey, int pnValue) {
	  Integer oKey = new Integer(pnKey);
	  
	  this.setValue(oKey, pnValue);
	}

	/**
	 * returns true if a value has been added or stored.
	 *
	 * @return boolean dirty flag
	 */
	public boolean hasChanged() {
		return mnDirtyFlag;
	}
	
	/**
	 * Adds or updates the value pnValue of key poKey. If no entry for poKey is found, a new one is generated.
	 * If poKey exists, the object is updated to the value of pnKey
	 *
	 * @param poKey - the key
	 * @param pnValue - the value
	 */
	public void setValue(Integer poKey, int pnValue) {
		mnDirtyFlag = true;
		
		clsMutableInteger oValue;
		
		if (this.keyExists(poKey)) {
			oValue = (clsMutableInteger)moList.get(poKey);
			oValue.set(pnValue);
		 
		} else {
			oValue = new clsMutableInteger(pnValue);
			moList.put(poKey, oValue);
		}
		
		moList.put(poKey, oValue);
	}
	
	/**
	 * Does moList contains an entry with id pnKey. As HashMap accepts keys as objects only, pnKey is
	 * converted into an object of type Integer and keyExists(Integer poKey) is called.
	 *
	 * @param pnKey - the key
	 * @return true if this key is in the list
	 */
	public boolean keyExists(int pnKey) {
		Integer oKey = new Integer(pnKey);
		
		return keyExists(oKey);
	}
	
	/**
	 * Does moList contains an entry with id pnKey.
	 *
	 * @param poKey - the key
	 * @return true if this key is in the list
	 */
	public boolean keyExists(Integer poKey) {
		return moList.containsKey(poKey);
	}

	/**
	 * Returns the value for the key. If the key does not exists, it returns null.  As HashMap accepts keys as objects only, pnKey is
	 * converted into an object of type Integer and getValue(Integer poKey) is called.
	 *
	 * @param pnKey - the key
	 * @return the value as a clsMutableInteger object
	 */
	public clsMutableInteger getValue(int pnKey) {
		return this.getValue(new Integer(pnKey));
	}
	
	/**
	 * Returns the value for the key. If the key does not exists, it returns null.
	 *
	 * @param poKey - the key
	 * @return the value as a clsMutableInteger object
	 */
	public clsMutableInteger getValue(Integer poKey)  {
		return (clsMutableInteger)moList.get(poKey);
	}
	
	/**
	 * Calculates the sum of all stored values and stores it. mnDirtyFlag is set to false. If an update 
	 * occurs using setValue, the stored sum is invalideted and a recalculation is necessary.
	 *
	 * @return the sum of all stored values.
	 */
	public int getSum() {
		int nSum = 0;
		
		if (mnDirtyFlag) {
			Iterator<Integer> i = moList.keySet().iterator();
			
			while (i.hasNext()) {
				clsMutableInteger oValue = moList.get(i.next());
				nSum += oValue.intValue();
			}
			
			mnDirtyFlag = false;
			mnSum = nSum;
		} else {
			nSum = mnSum;
		}
		
		return nSum;
	}
}
