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
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.05.2009, 11:40:36
 * 
 * @deprecated
 */
public class clsConfigEnum<E extends Enum<E>> extends clsConfigSkalar {
	private Enum<E> eValue;
	
	public clsConfigEnum() {
		
	}
	
	public clsConfigEnum(Enum<E> peValue) {
		eValue = peValue;
	}
	
	public void set(Enum<E> peValue) {
		eValue = peValue;
	}
	
	public Enum<E> get() {
		return eValue;
	}
	
	public int ordinal() {
		return eValue.ordinal();
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
