/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.exceptions;

import bw.utils.enums.eEntityType;

/**
 * TODO (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 
 */
public class EntityNotEatable extends bwException {

	private static final long serialVersionUID = 4667881354723355802L;
	private eEntityType eEntityType;

	/**
	 * 
	 */
	public EntityNotEatable(eEntityType peEntityType) {
		eEntityType = peEntityType;
	}

	/* (non-Javadoc)
	 * @see bw.exceptions.bwException#toString()
	 */
	@Override
	public String toString() {
		return ("EntityNotEatable: Entity with type "+eEntityType+" can not be eaten.");
	}

}
