/**
 * @author deutsch
 * 05.05.2009, 17:18:11
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
 * 05.05.2009, 17:18:11
 * 
 * @deprecated 
 */
public class clsConfigBoolean extends clsConfigSkalar {
	private boolean mnValue;
	
	public clsConfigBoolean() {
		mnValue = false;
	}
	
	public clsConfigBoolean(boolean pnValue) {
		mnValue = pnValue;
	}
	
	public void set(boolean pnValue) {
		mnValue = pnValue;
	}

	public boolean get() {
		return mnValue;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 17:18:11
	 * 
	 * @see bw.utils.container.clsBaseConfig#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "bool: " + ((mnValue)?"true":"false");
	}

}
