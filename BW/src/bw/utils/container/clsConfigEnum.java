/**
 * @author deutsch
 * 07.05.2009, 11:40:36
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
 * 07.05.2009, 11:40:36
 * 
 */
public class clsConfigEnum extends clsConfigSkalar {
	private Enum eValue;
	
	public clsConfigEnum() {
		
	}
	
	public clsConfigEnum(Enum peValue) {
		eValue = peValue;
	}
	
	public void set(Enum peValue) {
		eValue = peValue;
	}
	
	public Enum get() {
		return eValue;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.05.2009, 11:40:36
	 * 
	 * @see bw.utils.container.clsBaseConfig#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "enum: " + eValue;
	}

}
