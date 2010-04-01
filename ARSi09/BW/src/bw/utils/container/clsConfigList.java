/**
 * @author deutsch
 * 07.05.2009, 11:33:04
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.container;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.05.2009, 11:33:04
 * 
 * @deprecated
 */
public class clsConfigList extends clsConfigCollection {
	private ArrayList<clsBaseConfig> moConfig;
	
	public clsConfigList() {
		moConfig = new ArrayList<clsBaseConfig>();
	}
	
	public void add(clsBaseConfig poValue) {		
		moConfig.add(poValue);
	}

	public clsBaseConfig get(int index) {
		return moConfig.get(index);
	}
	
	public Iterator<clsBaseConfig> iterator() {
		return moConfig.iterator();
	}
	
	public void clear() {
		moConfig.clear();
	}	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.05.2009, 11:33:04
	 * 
	 * @see bw.utils.container.clsBaseConfig#toString()
	 */
	@Override
	public String toString() {
		String oResult = "ConfigList\n";
		
		Iterator<clsBaseConfig> i = moConfig.iterator();
		
		while (i.hasNext()) {
			oResult += i.next() + "\n";
		}
		
		oResult +="----\n";
		
		return oResult;
	}

}
