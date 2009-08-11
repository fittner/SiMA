/**
 * clsSecondaryKnowledgeUtilizer.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:43:00
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:43:00
 * 
 */
public class C16_SecondaryKnowledgeUtilizer extends clsModuleContainer {

	public static final String P_E25 = "E25";
	public static final String P_E28 = "E28";
	
	public E25_KnowledgeAboutReality moE25KnowledgeAboutReality;
	public E28_KnowledgeBase_StoredScenarios moE28KnowledgeBase_StoredScenarios;

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
	public C16_SecondaryKnowledgeUtilizer(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E25_KnowledgeAboutReality.getDefaultProperties(pre+P_E25) );
		oProp.putAll( E28_KnowledgeBase_StoredScenarios.getDefaultProperties(pre+P_E28) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE25KnowledgeAboutReality = new E25_KnowledgeAboutReality(pre+P_E25, poProp, this);
		moE28KnowledgeBase_StoredScenarios = new E28_KnowledgeBase_StoredScenarios(pre+P_E28, poProp, this);
	}

}
