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
public class exContentColumnMaxContentExceeded extends exException {

	private float mrValue;
	private float mrMaxValue;

	public exContentColumnMaxContentExceeded(float prValue, float prMaxValue) {
		mrValue = prValue;
		mrMaxValue = prMaxValue;
	}
	
	public String toString() {
		return ("ContentColumnMaxContentExceeded: value "+mrValue+" exceeds max value "+mrMaxValue+" -> value set to max value.\n");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1966099641504460937L;

}
