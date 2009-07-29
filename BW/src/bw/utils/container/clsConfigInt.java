/**
 * @author deutsch
 * 05.05.2009, 14:11:01
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
 * 05.05.2009, 14:11:01
 * 
 * @deprecated
 */
public class clsConfigInt extends clsConfigSkalar {
	private int mnValue;
	
	public clsConfigInt() {
		mnValue = 0;
	}
	
	public clsConfigInt(int pnValue) {
		mnValue = pnValue;
	}
	
	public void set(int pnValue) {
		mnValue = pnValue;
	}
	
	public int get() {
		return mnValue;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 14:11:01
	 * 
	 * @see bw.utils.container.clsBaseConfig#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "int: " + mnValue;
	}

}
