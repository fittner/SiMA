/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.exceptions;

import du.enums.eSlowMessenger;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class exSlowMessengerAlreadyExists extends exException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8188633263734083524L;

	private eSlowMessenger mnSlowMessengerId;
	
	public exSlowMessengerAlreadyExists(eSlowMessenger pnSlowMessengerId) {
		mnSlowMessengerId = pnSlowMessengerId;
	}
	
	/* (non-Javadoc)
	 * @see bw.exceptions.bwException#toString()
	 */
	@Override
	public String toString() {
		return ("SlowMessengerAlreadyExists: slow messenger with id "+mnSlowMessengerId+" already in system.");
	}

}
