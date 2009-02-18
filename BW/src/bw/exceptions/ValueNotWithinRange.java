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
public class ValueNotWithinRange extends bwException {
	private static final long serialVersionUID = -7714328851195940069L;

	private float mrMinValue;
	private float mrValue;
	private float mrMaxValue;
	
	public ValueNotWithinRange(float prMinValue, float prValue, float prMaxValue) {
		super();
		
		mrMinValue = prMinValue;
		mrValue = prValue;
		mrMaxValue = prMaxValue;
	}
	
	public String toString() {
		return ("ValueNotWithinRange: violation of "+mrMinValue+" <= "+mrValue+" <= "+mrMaxValue+"\n");
	}
}
