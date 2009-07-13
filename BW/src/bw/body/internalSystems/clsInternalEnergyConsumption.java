/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import java.util.HashMap;
import java.util.Iterator;

import bw.body.itfStep;
import bw.utils.container.clsConfigMap;
import bw.utils.datatypes.clsMutableDouble;

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
public class clsInternalEnergyConsumption implements itfStep {
    // private clsConfigMap moConfig;	// EH - make warning free
    
	private HashMap<Integer, clsMutableDouble> moList; // this list stores all registered values.
	private HashMap<Integer, clsMutableDouble> moListOnce; // this list stores all registered values.
	double mrSum; 											// stores the sum of all values within moList.
	boolean mnDirtyFlag; 								// set to true if moList has been changed.
	
	/**
	 * This constructor initializes moList with an empty HashMap, mnDirtyFlag is set to true, and mnSum is set to 0.
	 */
	public clsInternalEnergyConsumption(clsConfigMap poConfig) {
		moList = new HashMap<Integer, clsMutableDouble>();
		moListOnce = new HashMap<Integer, clsMutableDouble>();
		mnDirtyFlag = true;
		mrSum = 0.0f;
		
		// moConfig = getFinalConfig(poConfig); // EH - make warning free
		applyConfig();		
	}
	
	private void applyConfig() {
		
		//TODO add custom code
	}

/*	// EH - make warning free
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
*/
	
/* // EH - make warning free
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		//TODO add default values
		return oDefault;
	}	
*/
	
	
	public HashMap<Integer, clsMutableDouble> getMergedList() {
		HashMap<Integer, clsMutableDouble> oTemp = new HashMap<Integer, clsMutableDouble>(moList);
		
		Iterator<Integer> i = moListOnce.keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			oTemp.put(oKey, moListOnce.get(oKey));
		}
		
		return oTemp;
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
	public void setValue(Integer poKey, clsMutableDouble poValue) {
		mnDirtyFlag = true;
	
		moList.put(poKey, poValue);
	}
	
	public void setValueOnce(Integer poKey, clsMutableDouble poValue) {
		mnDirtyFlag = true;
		
		moListOnce.put(poKey, poValue);
		
	}
	
	public clsMutableDouble getValue(Integer poKey) {
		return moList.get(poKey);
	}

	public clsMutableDouble getValueOnce(Integer poKey) {
		return moListOnce.get(poKey);
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
	 * Does moListOnce contains an entry with id pnKey.
	 *
	 * @param poKey - the key
	 * @return true if this key is in the list
	 */
	public boolean keyExistsOnce(Integer poKey) {
		return moListOnce.containsKey(poKey);
	}
	
	/**
	 * Calculates the sum of all stored values and stores it. mnDirtyFlag is set to false. If an update 
	 * occurs using setValue, the stored sum is invalidated and a recalculation is necessary.
	 *
	 * @return the sum of all stored values.
	 */
	public double getSum() {
		double rSum = 0;
		
		if (mnDirtyFlag) {
			Iterator<Integer> i = getMergedList().keySet().iterator();
			
			while (i.hasNext()) {
				clsMutableDouble oValue = getMergedList().get(i.next());
				rSum += oValue.doubleValue();
			}
			
			mnDirtyFlag = false;
			mrSum = rSum;
		} else {
			rSum = mrSum;
		}
		
		return rSum;
	}
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	public void step() {
		if (moListOnce.size() > 0) {
			moListOnce.clear();
			mnDirtyFlag = true;
		}
	}
}
