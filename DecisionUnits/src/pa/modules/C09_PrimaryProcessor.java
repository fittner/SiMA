/**
 * C09_PreliminaryExternalPerception.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:35:28
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:35:28
 * 
 */
public class C09_PrimaryProcessor extends clsModuleContainer{

	public static final String P_E17 = "E17";
	public static final String P_C13 = "C13";
	public static final String P_C14 = "C14";
	
	public E17_FusionOfExternalPerceptionAndMemoryTraces moE17FusionOfExternalPerceptionAndMemoryTraces;
	public C12_PrimaryDecision moC13PrimaryDecision;
	public C13_PrimaryKnowledgeUtilizer moC14PrimaryKnowledgeUtilizer;

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
	public C09_PrimaryProcessor(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E17_FusionOfExternalPerceptionAndMemoryTraces.getDefaultProperties(pre+P_E17) );
		oProp.putAll( C12_PrimaryDecision.getDefaultProperties(pre+P_C13) );
		oProp.putAll( C13_PrimaryKnowledgeUtilizer.getDefaultProperties(pre+P_C14) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE17FusionOfExternalPerceptionAndMemoryTraces = new E17_FusionOfExternalPerceptionAndMemoryTraces(pre+P_E17, poProp, this);
		moC13PrimaryDecision = new C12_PrimaryDecision(pre+P_C13, poProp, this);
		moC14PrimaryKnowledgeUtilizer = new C13_PrimaryKnowledgeUtilizer(pre+P_C14, poProp, this);
	}
}
