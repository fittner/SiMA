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
public class exSlowMessengerDoesNotExist extends exException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4661856014822575735L;

	private int mnSlowMessengerId;
	
	public exSlowMessengerDoesNotExist(int pnSlowMessengerId) {
		mnSlowMessengerId = pnSlowMessengerId;
	}	
	
	/* (non-Javadoc)
	 * @see bw.exceptions.bwException#toString()
	 */
	@Override
	public String toString() {
		return ("SlowMessengerDoesNotExist: slow messenger with id "+mnSlowMessengerId+" not found.");
	}

}
