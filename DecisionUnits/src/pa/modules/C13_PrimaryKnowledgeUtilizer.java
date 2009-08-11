/**
 * C14_PrimaryKnowledgeUtilizer.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:41:11
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:41:11
 * 
 */
public class C13_PrimaryKnowledgeUtilizer extends clsModuleContainer {

	public static final String P_E09 = "E09";
	public static final String P_E16 = "E16";
	
	public E09_KnowledgeAboutReality_unconscious moE09KnowledgeAboutReality_unconscious;
	public E16_ManagmentOfMemoryTraces moE16ManagmentOfMemoryTraces;

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
	public C13_PrimaryKnowledgeUtilizer(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E09_KnowledgeAboutReality_unconscious.getDefaultProperties(pre+P_E09) );
		oProp.putAll( E16_ManagmentOfMemoryTraces.getDefaultProperties(pre+P_E16) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE09KnowledgeAboutReality_unconscious = new E09_KnowledgeAboutReality_unconscious(pre+P_E09, poProp, this);
		moE16ManagmentOfMemoryTraces = new E16_ManagmentOfMemoryTraces(pre+P_E16, poProp, this);
	}

}
