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
	private float mrValue;
	
	public clsConfigDouble() {
		mrValue = 0;
	}
	
	public clsConfigDouble(float prValue) {
		mrValue = prValue;
	}
	
	public void set(float prValue) {
		mrValue = prValue;
	}
	
	public float get() {
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
		return "float: " + mrValue;
	}

}
