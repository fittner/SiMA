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
public class ContentColumnMinContentUnderrun extends bwException {

	private float mrValue;
	private float mrMinValue;

	public ContentColumnMinContentUnderrun(float prValue, float prMinValue) {
		mrValue = prValue;
		mrMinValue = prMinValue;
	}
	
	public String toString() {
		return ("ContentColumnMaxContentExceeded: value "+mrValue+" is below min value "+mrMinValue+" -> value set to min value.\n");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4490875970083958938L;

}
