/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.exceptions;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class SlowMessengerAlreadyExists extends bwException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8188633263734083524L;

	private int mnSlowMessengerId;
	
	public SlowMessengerAlreadyExists(int pnSlowMessengerId) {
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
