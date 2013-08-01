/**
 * @author deutsch
 * 05.05.2009, 13:54:11
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.container;

import java.util.HashMap;
import java.util.Iterator;

import bw.utils.enums.eConfigEntries;

/**
 *  
 * 
 * @author deutsch
 * 05.05.2009, 13:54:11
 * 
 * @deprecated
 */
public class clsConfigMap extends clsConfigCollection {
	private HashMap<Integer, clsBaseConfig> moConfig;
	
	public clsConfigMap() {
		moConfig = new HashMap<Integer, clsBaseConfig>();
	}

	public void add(Integer poKey, clsBaseConfig poValue) {		
		moConfig.put(poKey, poValue);
	}

	public void add(eConfigEntries poKey, clsBaseConfig poValue) {
		add(poKey.ordinal(), poValue);
	}
	
	public clsBaseConfig get(Integer poKey) {
		return moConfig.get(poKey);
	}

	public clsBaseConfig get(eConfigEntries poKey) {
		return get(poKey.ordinal());
	}
	
	public Iterator<Integer> iterator() {
		return moConfig.keySet().iterator();
	}
	
	public void clear() {
		moConfig.clear();
	}
	
	public boolean containsKey(Integer poKey) {
		return moConfig.containsKey(poKey);
	}
	

	public boolean containsKey(eConfigEntries poKey) {
		return containsKey(poKey.ordinal());
	}
	
	public void overwritewith(clsConfigMap poOther) {
		if (poOther != null) {
			Iterator<Integer> i = poOther.moConfig.keySet().iterator();
			
			while (i.hasNext()) {
				Integer oKey = i.next();
				moConfig.put(oKey, poOther.moConfig.get(oKey));
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 13:56:03
	 * 
	 * @see bw.utils.container.clsBaseConfig#toString()
	 */
	@Override
	public String toString() {
		String oResult = "ConfigMap\n";
		
		Iterator<Integer> i = moConfig.keySet().iterator();
		
		while (i.hasNext()) {
			oResult += i + ": "+ moConfig.get(i.next()).toString() + "\n";
		}
		oResult +="----\n";
		
		return oResult;
	}
}
