/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.exceptions;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class exValueNotWithinRange extends exException {
	private static final long serialVersionUID = -7714328851195940069L;

	private double mrMinValue;
	private double mrValue;
	private double mrMaxValue;
	
	public exValueNotWithinRange(double prMinValue, double prValue, double prMaxValue) {
		super();
		
		mrMinValue = prMinValue;
		mrValue = prValue;
		mrMaxValue = prMaxValue;
	}
	
	@Override
	public String toString() {
		return ("ValueNotWithinRange: violation of "+mrMinValue+" <= "+mrValue+" <= "+mrMaxValue+"\n");
	}
}
