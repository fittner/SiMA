/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

import bw.body.itfStep;

/**
 * A nutrition level is a special case of a fill level. By implementing the itfStep interface and guaranteeing,
 * that each step, the currently stored amount is decreased or left unchanged it provides the needs for the stomach.
 * 
 * @author deutsch
 * 
 */
public class clsNutritionLevel extends clsFillLevel implements itfStep {
	/**
	 * @param prContent
	 * @param prMaxContent
	 * @param prLowerBound
	 * @param prUpperBound
	 * @param prDecreasePerStep
	 */
	public clsNutritionLevel(float prContent, float prMaxContent, float prLowerBound, float prUpperBound, float prDecreasePerStep) {
		super(prContent, prMaxContent, -prDecreasePerStep, prLowerBound, prUpperBound);
		
		setDecreasePerStep(prDecreasePerStep);
	}

	/**
	 * @return the mrDecreasePerStep
	 */
	public float getDecreasePerStep() {
		return -getChange();
	}

	/**
	 * @param mrDecreasePerStep the mrDecreasePerStep to set
	 */
	public void setDecreasePerStep(float prDecreasePerStep) {
		setChange(-prDecreasePerStep);
		
		checkDecreasePerStep();
	}
	
	/**
	 * Guarantees that mrChange is always zero or lower
	 *
	 */
	private void checkDecreasePerStep() {
		if (getChange() > 0.0f) {
			setChange( 0.0f );
		} 
	}
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	public void step() {
		this.update();
	}
}
