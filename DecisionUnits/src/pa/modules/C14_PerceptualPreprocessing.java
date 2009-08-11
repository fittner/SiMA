/**
 * C15_PerceptualPreprocessing.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:42:06
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:42:06
 * 
 */
public class C14_PerceptualPreprocessing extends clsModuleContainer {

	public static final String P_E23 = "E23";
	public static final String P_E24 = "E24";
	
	public E23_ExternalPerception_focused moE23ExternalPerception_focused;
	public E24_RealityCheck moE24RealityCheck;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:36:51
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public C14_PerceptualPreprocessing(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E23_ExternalPerception_focused.getDefaultProperties(pre+P_E23) );
		oProp.putAll( E24_RealityCheck.getDefaultProperties(pre+P_E24) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE23ExternalPerception_focused = new E23_ExternalPerception_focused(pre+P_E24, poProp, this);
		moE24RealityCheck = new E24_RealityCheck(pre+P_E23, poProp, this);
	}
}
