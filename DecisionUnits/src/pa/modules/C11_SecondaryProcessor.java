/**
 * C10_PrimaryProcessor.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:37:00
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:37:00
 * 
 */
public class C11_SecondaryProcessor extends clsModuleContainer {

	public static final String P_C15 = "C15";
	public static final String P_C16 = "C16";
	public static final String P_C17 = "C17";
	
	public C14_PerceptualPreprocessing moC15PerceptualPreprocessing;
	public C15_Deliberation moC16Deliberation;
	public C16_SecondaryKnowledgeUtilizer moC17SecondaryKnowledgeUtilizer;

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
	public C11_SecondaryProcessor(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( C14_PerceptualPreprocessing.getDefaultProperties(pre+P_C15) );
		oProp.putAll( C15_Deliberation.getDefaultProperties(pre+P_C16) );
		oProp.putAll( C16_SecondaryKnowledgeUtilizer.getDefaultProperties(pre+P_C17) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moC15PerceptualPreprocessing = new C14_PerceptualPreprocessing(pre+P_C15, poProp, this);
		moC16Deliberation = new C15_Deliberation(pre+P_C16, poProp, this);
		moC17SecondaryKnowledgeUtilizer = new C16_SecondaryKnowledgeUtilizer(pre+P_C17, poProp, this);
	}
}
