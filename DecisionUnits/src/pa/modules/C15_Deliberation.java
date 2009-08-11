/**
 * C16_Deliberation.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:42:32
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:42:32
 * 
 */
public class C15_Deliberation extends clsModuleContainer {

	public static final String P_E26 = "E26";
	public static final String P_E27 = "E27";
	public static final String P_E29 = "E29";
	
	public E26_DecisionMaking moE26DecisionMaking;
	public E27_GenerationOfImaginaryActions moE27GenerationOfImaginaryActions;
	public E29_EvaluationOfImaginaryActions moE29EvaluationOfImaginaryActions;

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
	public C15_Deliberation(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E26_DecisionMaking.getDefaultProperties(pre+P_E26) );
		oProp.putAll( E27_GenerationOfImaginaryActions.getDefaultProperties(pre+P_E27) );
		oProp.putAll( E29_EvaluationOfImaginaryActions.getDefaultProperties(pre+P_E29) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE26DecisionMaking = new E26_DecisionMaking(pre+P_E26, poProp, this);
		moE27GenerationOfImaginaryActions = new E27_GenerationOfImaginaryActions(pre+P_E27, poProp, this);
		moE29EvaluationOfImaginaryActions = new E29_EvaluationOfImaginaryActions(pre+P_E29, poProp, this);
	}
}
