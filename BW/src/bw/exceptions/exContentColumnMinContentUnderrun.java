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
public class exContentColumnMinContentUnderrun extends exException {

	private double mrValue;
	private double mrMinValue;

	public exContentColumnMinContentUnderrun(double prValue, double prMinValue) {
		mrValue = prValue;
		mrMinValue = prMinValue;
	}
	
	@Override
	public String toString() {
		return ("ContentColumnMaxContentExceeded: value "+mrValue+" is below min value "+mrMinValue+" -> value set to min value.\n");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4490875970083958938L;

}
