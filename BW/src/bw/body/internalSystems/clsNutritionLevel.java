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
 * 
 * @author deutsch
 * 
 */
public class clsNutritionLevel extends clsFillLevel {
	private float mrDecreasePerStep;
	
	/**
	 * @param prContent
	 * @param prMaxContent
	 * @param prLowerBound
	 * @param prUpperBound
	 * @param prDecreasePerStep
	 */
	public clsNutritionLevel(float prContent, float prMaxContent, float prLowerBound, float prUpperBound, float prDecreasePerStep) {
		super(prContent, prMaxContent, prLowerBound, prUpperBound);
		
		setDecreasePerStep(prDecreasePerStep);
	}

	/**
	 * @return the mrDecreasePerStep
	 */
	public float getDecreasePerStep() {
		return mrDecreasePerStep;
	}

	/**
	 * @param mrDecreasePerStep the mrDecreasePerStep to set
	 */
	public void setDecreasePerStep(float prDecreasePerStep) {
		mrDecreasePerStep = prDecreasePerStep;
		
		checkDecreasePerStep();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void checkDecreasePerStep() {
		if (mrDecreasePerStep < 0.0f) {
			mrDecreasePerStep = 0.0f;
		} 
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prDecreaseBy
	 */
	public void decreaseLevel(float prDecreaseBy) {
		this.decrease(prDecreaseBy);
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	public void step() {
		this.decreaseLevel(mrDecreasePerStep);
	}
}
