/**
 * clsPsychicMediator.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:33:25
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:33:25
 * 
 */
public class C08_PsychicMediator extends clsModuleContainer {

	public static final String P_C09 = "C09";
	public static final String P_C10 = "C10";
	public static final String P_C11 = "C11";
	
	public C09_PrimaryProcessor moC09PrimaryProcessor;
	public C11_SecondaryProcessor moC10SecondaryProcessor;
	public C10_PrimaryToSecondaryInterface moC11PrimaryToSecondaryInterface1;

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
	public C08_PsychicMediator(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( C09_PrimaryProcessor.getDefaultProperties(pre+P_C09) );
		oProp.putAll( C11_SecondaryProcessor.getDefaultProperties(pre+P_C10) );
		oProp.putAll( C10_PrimaryToSecondaryInterface.getDefaultProperties(pre+P_C11) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moC09PrimaryProcessor = new C09_PrimaryProcessor(pre+P_C09, poProp, this);
		moC10SecondaryProcessor = new C11_SecondaryProcessor(pre+P_C10, poProp, this);
		moC11PrimaryToSecondaryInterface1 = new C10_PrimaryToSecondaryInterface(pre+P_C11, poProp, this);
	}

}
