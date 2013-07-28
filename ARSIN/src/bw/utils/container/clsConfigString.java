/**
 * @author deutsch
 * 05.05.2009, 14:12:41
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.container;

/**
 *  
 * 
 * @author deutsch
 * 05.05.2009, 14:12:41
 * 
 * @deprecated
 */
public class clsConfigString extends clsConfigObject {
	private String moValue;
	
	public clsConfigString() {
		moValue = "";
	}
	
	public clsConfigString(String poValue) {
		moValue = poValue;
	}
	
	public void set(String poValue) {
		moValue = poValue;
	}
	
	public String get() {
		return moValue;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 14:12:41
	 * 
	 * @see bw.utils.container.clsBaseConfig#toString()
	 */
	@Override
	public String toString() {
		return "String: " + moValue;
	}

}
