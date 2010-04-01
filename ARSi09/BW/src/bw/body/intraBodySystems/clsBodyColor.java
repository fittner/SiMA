/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import java.awt.Color;

import config.clsBWProperties;
import bw.body.itfStepUpdateInternalState;
import bw.entities.clsEntity;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsBodyColor implements itfStepUpdateInternalState {
	public static final String P_REDRATE = "redrate";
	public static final String P_GREENRATE = "greenrate";
	public static final String P_BLUERATE = "bluerate";
	
	private clsEntity moEntity; //reference;
	
	private Color moNormColor;
	
	private int mnRedResetRate;
	private int mnGreenResetRate;
	private int mnBlueResetRate;

	public clsBodyColor(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		moEntity = poEntity;
		
		moNormColor = null;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_REDRATE, 5);
		oProp.setProperty(pre+P_GREENRATE, 5);
		oProp.setProperty(pre+P_BLUERATE, 5);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);

		mnRedResetRate = poProp.getPropertyInt(pre+P_REDRATE);
		mnGreenResetRate = poProp.getPropertyInt(pre+P_GREENRATE);
		mnBlueResetRate = poProp.getPropertyInt(pre+P_BLUERATE);		
	}

	public void setNormColor(Color poColor) {
		moNormColor = poColor;
	}

	public void setNormColor() {
		moNormColor = (Color)moEntity.getShape().getPaint();
	}
	
	private void isNormColorSet() {
		if (moNormColor == null) {
			setNormColor();
		}
	}
	
	private int calcNewValue(int pnTarget, int pnActual, int pnRate) {
		int result = pnTarget;
		int diff = pnTarget - pnActual;
		
		if (Math.abs(diff) < pnRate) {
			pnRate = Math.abs(diff);
		}
		
		if (diff > 0) {
			result = pnActual - pnRate;
		} else if (diff < 0) {
			result = pnActual + pnRate;
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.09.2009, 10:53:20
	 * 
	 * @see bw.body.itfStepUpdateInternalState#stepUpdateInternalState()
	 */
	@Override
	public void stepUpdateInternalState() {
		isNormColorSet(); 
		
		Color moActualColor = (Color)moEntity.getShape().getPaint();
		
		if (!moActualColor.equals(moActualColor)) {
			int nRed = calcNewValue(moNormColor.getRed(), moActualColor.getRed(), mnRedResetRate);
			int nGreen = calcNewValue(moNormColor.getGreen(), moActualColor.getGreen(), mnBlueResetRate);
			int nBlue = calcNewValue(moNormColor.getBlue(), moActualColor.getBlue(), mnGreenResetRate);
		
			moEntity.getShape().setPaint(new Color(nRed, nGreen, nBlue));
		}
	}	
	
	public void changeRed(int pnColorChange) {
		Color moActualColor = (Color)moEntity.getShape().getPaint();
		int nRed = moActualColor.getRed() + pnColorChange;
		int nGreen = moActualColor.getGreen();
		int nBlue = moActualColor.getBlue();
		moEntity.getShape().setPaint(new Color(nRed, nGreen, nBlue));
	}
	
	public void changeGreen(int pnColorChange) {
		Color moActualColor = (Color)moEntity.getShape().getPaint();
		int nRed = moActualColor.getRed();
		int nGreen = moActualColor.getGreen() + pnColorChange;
		int nBlue = moActualColor.getBlue();
		moEntity.getShape().setPaint(new Color(nRed, nGreen, nBlue));
	}
	
	public void changeBlue(int pnColorChange) {
		Color moActualColor = (Color)moEntity.getShape().getPaint();
		int nRed = moActualColor.getRed();
		int nGreen = moActualColor.getGreen() + pnColorChange;
		int nBlue = moActualColor.getBlue();
		moEntity.getShape().setPaint(new Color(nRed, nGreen, nBlue));
	}	
	
	public void changeColor(int pnChangeRed, int pnChangeGreen, int pnChangeBlue) {
		Color moActualColor = (Color)moEntity.getShape().getPaint();
		int nRed = moActualColor.getRed() + pnChangeRed;
		int nGreen = moActualColor.getGreen() + pnChangeGreen;
		int nBlue = moActualColor.getBlue() + pnChangeBlue;
		moEntity.getShape().setPaint(new Color(nRed, nGreen, nBlue));
	}		

}
