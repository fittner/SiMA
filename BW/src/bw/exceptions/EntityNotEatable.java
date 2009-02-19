/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.exceptions;

/**
 * TODO (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 
 */
public class EntityNotEatable extends bwException {

	private static final long serialVersionUID = 4667881354723355802L;
	private int mnEntityId;

	/**
	 * 
	 */
	public EntityNotEatable(int pnEntityId) {
		mnEntityId = pnEntityId;
	}

	/* (non-Javadoc)
	 * @see bw.exceptions.bwException#toString()
	 */
	@Override
	public String toString() {
		return ("EntityNotEatable: Entity with id "+mnEntityId+" can not be eaten.");
	}

}
