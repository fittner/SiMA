/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import bw.utils.datatypes.clsMutableInteger;

/**
 * The class energy consumption maintains a list where all active objects of the agent 
 * can register and update their current energy consumption. this value remains until 
 * the object updates the value. at the end of each round, the total of all values is 
 * calculated and subtracted from the energy.
 * 
 * @author deutsch
 * 
 */
public class clsInternalEnergyConsumption {
	private HashMap<Integer, clsMutableInteger> moList;
	
	public clsInternalEnergyConsumption() {
		moList = new HashMap<Integer, clsMutableInteger>();	
	}
	
	public HashMap<Integer, clsMutableInteger> getList() {
		return moList;
	}
	
	public void setValue(int pnKey, int pnValue) {
	  Integer oKey = new Integer(pnKey);
	  
	  this.setValue(oKey, pnValue);
	}

	public void setValue(Integer poKey, int pnValue) {
		clsMutableInteger oValue;
		
		if (this.keyExists(poKey)) {
			oValue = (clsMutableInteger)moList.get(poKey);
			oValue.set(pnValue);
		 
		} else {
			oValue = new clsMutableInteger(pnValue);
			
		}
		
		moList.put(poKey, oValue);
	}
	
	public boolean keyExists(int pnKey) {
		Integer oKey = new Integer(pnKey);
		
		return keyExists(oKey);
	}
	public boolean keyExists(Integer poKey) {
		return moList.containsKey(poKey);
	}

	public clsMutableInteger getValue(int pnKey) {
		return this.getValue(new Integer(pnKey));
	}
	public clsMutableInteger getValue(Integer poKey)  {
		return (clsMutableInteger)moList.get(poKey);
	}
	
	public int getSum() {
		int nSum = 0;
		
		LinkedList<clsMutableInteger> oList = (LinkedList<clsMutableInteger>) moList.values();
		
		Iterator<clsMutableInteger> i = oList.iterator();
		
		while (i.hasNext()) {
			clsMutableInteger oValue = (clsMutableInteger)i.next();
			nSum += oValue.intValue();
		}
		
		return nSum;
	}
}
