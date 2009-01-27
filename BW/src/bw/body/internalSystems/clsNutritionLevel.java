/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.utils.tools.clsFillLevel;

/**
 * TODO (deutsch) - insert description 
 * TODO maybe its better not to implement clsNutritionLevel as a subclass of clsFillLevel. another
 * approach would be to encapsulates clsFilleLevel and only allow access to the necessary functions 
 * 
 * @author deutsch
 * 
 */
public class clsNutritionLevel extends clsFillLevel {
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
	 * TODO (deutsch) - insert description
	 *
	 */
	private void checkDecreasePerStep() {
		if (getChange() > 0.0f) {
			setChange( 0.0f );
		} 
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	public void step() {
		this.update();
	}
}
