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

	private double mrValue;
	private double mrMaxValue;

	public exContentColumnMaxContentExceeded(double prValue, double prMaxValue) {
		mrValue = prValue;
		mrMaxValue = prMaxValue;
	}
	
	@Override
	public String toString() {
		return ("ContentColumnMaxContentExceeded: value "+mrValue+" exceeds max value "+mrMaxValue+" -> value set to max value.\n");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1966099641504460937L;

}
