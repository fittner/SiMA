/**
 * @author deutsch
 * 05.05.2009, 14:07:46
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.container;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.05.2009, 14:07:46
 * 
 */
public class clsConfigDouble extends clsConfigSkalar {
	private double mrValue;
	
	public clsConfigDouble() {
		mrValue = 0;
	}
	
	public clsConfigDouble(double prValue) {
		mrValue = prValue;
	}
	
	public void set(double prValue) {
		mrValue = prValue;
	}
	
	public double get() {
		return mrValue;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 14:07:46
	 * 
	 * @see bw.utils.container.clsBaseConfig#toString()
	 */
	@Override
	public String toString() {
		return "double: " + mrValue;
	}

}
