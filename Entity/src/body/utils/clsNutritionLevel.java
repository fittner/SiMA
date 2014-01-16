/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package body.utils;

import properties.clsProperties;
import utils.exceptions.exContentColumnMaxContentExceeded;
import utils.exceptions.exContentColumnMinContentUnderrun;
import body.itfStep;

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

	public clsNutritionLevel(String poPrefix, clsProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+clsContentColumn.P_CONTENT, 0.4);
		oProp.setProperty(pre+clsContentColumn.P_MAXCONTENT, 1.2);
		oProp.setProperty(pre+clsFillLevel.P_CHANGE, 0.0);
		oProp.setProperty(pre+clsFillLevel.P_LOWERBOUND, 0.3);
		oProp.setProperty(pre+clsFillLevel.P_UPPERBOUND, 1.0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);

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
