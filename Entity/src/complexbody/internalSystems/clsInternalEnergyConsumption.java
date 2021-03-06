/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.internalSystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import properties.clsProperties;


import datatypes.clsMutableDouble;
import entities.enums.eBodyParts;

import body.itfStep;


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
    
	private HashMap<eBodyParts, clsMutableDouble> moList; // this list stores all registered values.
	private HashMap<eBodyParts, clsMutableDouble> moListOnce; // this list stores all registered values.
	private ArrayList<eBodyParts> moPartList;
	double mrSum; 											// stores the sum of all values within moList.
	boolean mnDirtyFlag; 								// set to true if moList has been changed.
	
	/**
	 * This constructor initializes moList with an empty HashMap, mnDirtyFlag is set to true, and mnSum is set to 0.
	 */
	public clsInternalEnergyConsumption(String poPrefix, clsProperties poProp) {
		moList = new HashMap<eBodyParts, clsMutableDouble>();
		moListOnce = new HashMap<eBodyParts, clsMutableDouble>();
		moPartList = new ArrayList<eBodyParts>();
		mnDirtyFlag = true;
		mrSum = 0.0f;
		
		applyProperties(poPrefix, poProp);	
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		//String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();
		
		// no properties
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
        // nothing to do		
	}	
	
	
	public HashMap<eBodyParts, clsMutableDouble> getMergedList() {
		HashMap<eBodyParts, clsMutableDouble> oTemp = new HashMap<eBodyParts, clsMutableDouble>(moList);
		
		Iterator<eBodyParts> i = moListOnce.keySet().iterator();
		
		while (i.hasNext()) {
			eBodyParts oKey = i.next();
			oTemp.put(oKey, moListOnce.get(oKey));
		}
		
		return oTemp;
	}
	public HashMap<eBodyParts, clsMutableDouble> getListConstant() {
		return moList;
	}
	public HashMap<eBodyParts, clsMutableDouble> getListOnce() {
		return moListOnce;
	}
	/**
	 * returns true if a value has been added or stored.
	 *
	 * @return boolean dirty flag
	 */
	public boolean hasChanged() {
		return mnDirtyFlag;
	}
	
	private void addKey(eBodyParts poKey) {
		if (!moPartList.contains(poKey)) {
			moPartList.add(poKey);
		}
	}
	
	/**
	 * Adds or updates the value pnValue of key poKey. If no entry for poKey is found, a new one is generated.
	 * If poKey exists, the object is updated to the value of pnKey
	 *
	 * @param poKey - the key
	 * @param pnValue - the value
	 */
	public void setValue(eBodyParts poKey, clsMutableDouble poValue) {
		addKey(poKey);
		mnDirtyFlag = true;
	
		moList.put(poKey, poValue);
	}
	
	public void setValueOnce(eBodyParts poKey, clsMutableDouble poValue) {
		addKey(poKey);
		mnDirtyFlag = true;
		
		moListOnce.put(poKey, poValue);
		
	}
	
	public clsMutableDouble getValue(eBodyParts poKey) {
		return moList.get(poKey);
	}

	public clsMutableDouble getValueOnce(eBodyParts poKey) {
		return moListOnce.get(poKey);
	}
		
	/**
	 * Does moList contains an entry with id pnKey.
	 *
	 * @param poKey - the key
	 * @return true if this key is in the list
	 */
	public boolean keyExists(eBodyParts poKey) {
		return moList.containsKey(poKey);
	}

	/**
	 * Does moListOnce contains an entry with id pnKey.
	 *
	 * @param poKey - the key
	 * @return true if this key is in the list
	 */
	public boolean keyExistsOnce(eBodyParts poKey) {
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
			Iterator<eBodyParts> i = getMergedList().keySet().iterator();
			
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
	
	public ArrayList<eBodyParts> getPartList() {
		return moPartList;
	}
}
