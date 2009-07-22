/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

import bw.body.itfStep;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.utils.config.clsBWProperties;

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
	 * @throws exContentColumnMinContentUnderrun 
	 * @throws exContentColumnMaxContentExceeded 
	 */
	public clsNutritionLevel(double prContent, double prMaxContent, double prLowerBound, double prUpperBound, double prDecreasePerStep) throws exContentColumnMaxContentExceeded, exContentColumnMinContentUnderrun {
		super(prContent, prMaxContent, -prDecreasePerStep, prLowerBound, prUpperBound);
		
		setDecreasePerStep(prDecreasePerStep);
	}

	public clsNutritionLevel(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+clsContentColumn.P_CONTENT, 0.75);
		oProp.setProperty(pre+clsContentColumn.P_MAXCONTENT, 1.2);
		oProp.setProperty(pre+clsFillLevel.P_CHANGE, "0.05");
		oProp.setProperty(pre+clsFillLevel.P_LOWERBOUND, "0.3");
		oProp.setProperty(pre+clsFillLevel.P_UPPERBOUND, "1.0");
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

		//no params to be set - everything done in clsFillLevel and clsContentColumn	
	}	
	
	/**
	 * @return the mrDecreasePerStep
	 */
	public double getDecreasePerStep() {
		return -getChange();
	}

	/**
	 * @param mrDecreasePerStep the mrDecreasePerStep to set
	 */
	public void setDecreasePerStep(double prDecreasePerStep) {
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
		try {
			this.update();
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
	}
}
