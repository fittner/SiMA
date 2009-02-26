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
 * Exceptions used by sensors and actuators, for Information if a object is eatable poisned etc.
 * 
 * @author muchitsch
 * 
 */
public class exEntityNotEatable extends exException {

	private static final long serialVersionUID = 4667881354723355802L;
	private eEntityType eEntityType;

	/**
	 * 
	 */
	public exEntityNotEatable(eEntityType peEntityType) {
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
