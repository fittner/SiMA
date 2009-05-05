/**
 * @author deutsch
 * 05.05.2009, 14:49:43
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
 * 05.05.2009, 14:49:43
 * 
 */
public class clsConfigFloatClass extends clsConfigObject {
	private Float moValue;
	
	public clsConfigFloatClass() {
		moValue = new Float(0);
	}
	
	public clsConfigFloatClass(Float poValue) {
		moValue = poValue;
	}
	
	public void set(Float poValue) {
		moValue = poValue;
	}
	
	public Float get() {
		return moValue;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 14:49:43
	 * 
	 * @see bw.utils.container.clsBaseConfig#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Float: " + moValue;
	}

}
