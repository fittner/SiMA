/**
 * C13_PrimaryDecision.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:40:41
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:40:41
 * 
 */
public class C12_PrimaryDecision extends clsModuleContainer {

	public static final String P_E06 = "E06";
	public static final String P_E19 = "E19";
	
	public E06_DefenseMechanismsForDriveContents moE06DefenseMechanismsForDriveContents;
	public E19_DefenseMechanismsForPerception moE19DefenseMechanismsForPerception;

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
	public C12_PrimaryDecision(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E06_DefenseMechanismsForDriveContents.getDefaultProperties(pre+P_E06) );
		oProp.putAll( E19_DefenseMechanismsForPerception.getDefaultProperties(pre+P_E19) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE06DefenseMechanismsForDriveContents = new E06_DefenseMechanismsForDriveContents(pre+P_E06, poProp, this);
		moE19DefenseMechanismsForPerception = new E19_DefenseMechanismsForPerception(pre+P_E19, poProp, this);
	}

}
