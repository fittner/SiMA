/**
 * C04_SuperEgo.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:11:20
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:11:20
 * 
 */
public class C04_SuperEgo extends clsModuleContainer  {
	public static final String P_E07 = "E07";
	public static final String P_E22 = "E22";
	
	public E07_SuperEgo_unconscious moE07SuperEgoUnconscious;
	public E22_SuperEgo_preconscious moE22SuperEgoPreconscious;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:11:51
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public C04_SuperEgo(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();

		oProp.putAll( E07_SuperEgo_unconscious.getDefaultProperties(pre+P_E07) );
		oProp.putAll( E22_SuperEgo_preconscious.getDefaultProperties(pre+P_E22) );
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE07SuperEgoUnconscious = new E07_SuperEgo_unconscious(pre+P_E07, poProp, this);
		moE22SuperEgoPreconscious = new E22_SuperEgo_preconscious(pre+P_E22, poProp, this);
	}

}
