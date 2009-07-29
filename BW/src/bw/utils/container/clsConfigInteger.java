/**
 * @author deutsch
 * 05.05.2009, 14:39:12
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.container;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.05.2009, 14:39:12
 * 
 * @deprecated
 */
public class clsConfigInteger extends clsConfigObject {
	private Integer moValue;
	
	public clsConfigInteger() {
		moValue = new Integer(0);
	}
	
	public clsConfigInteger(Integer poValue) {
		moValue = poValue;
	}
	
	public void set(Integer poValue) {
		moValue = poValue;
	}
	
	public Integer get() {
		return moValue;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 14:39:12
	 * 
	 * @see bw.utils.container.clsBaseConfig#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Integer: " + moValue;
	}

}
