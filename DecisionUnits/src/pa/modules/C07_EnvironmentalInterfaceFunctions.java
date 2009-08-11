/**
 * C07_EnvironmentalInterfaceFunctions.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:32:50
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:32:50
 * 
 */
public class C07_EnvironmentalInterfaceFunctions extends clsModuleContainer {

	public static final String P_E14 = "E14";
	public static final String P_E30 = "E308";
	
	public E14_PreliminaryExternalPerception moE14PreliminaryExternalPerception;
	public E30_MotilityControl moE30MotilityControl;

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
	public C07_EnvironmentalInterfaceFunctions(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E14_PreliminaryExternalPerception.getDefaultProperties(pre+P_E14) );
		oProp.putAll( E30_MotilityControl.getDefaultProperties(pre+P_E30) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE14PreliminaryExternalPerception = new E14_PreliminaryExternalPerception(pre+P_E14, poProp, this);
		moE30MotilityControl = new E30_MotilityControl(pre+P_E30, poProp, this);
	}
}
