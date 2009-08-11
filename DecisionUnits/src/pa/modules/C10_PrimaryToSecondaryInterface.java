/**
 * C11_PrimaryToSecondaryInterface.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:37:27
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:37:27
 * 
 */
public class C10_PrimaryToSecondaryInterface extends clsModuleContainer {

	public static final String P_E08 = "E08";
	public static final String P_E20 = "E20";
	public static final String P_E21 = "E21";
	
	public E08_ConversionToSecondaryProcess moE08ConversionToSecondaryProcess;
	public E20_InnerPerception_Affects moE20InnerPerception_Affects;
	public E21_ConversionToSecondaryProcess moE21ConversionToSecondaryProcess;

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
	public C10_PrimaryToSecondaryInterface(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E08_ConversionToSecondaryProcess.getDefaultProperties(pre+P_E08) );
		oProp.putAll( E20_InnerPerception_Affects.getDefaultProperties(pre+P_E20) );
		oProp.putAll( E21_ConversionToSecondaryProcess.getDefaultProperties(pre+P_E21) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE08ConversionToSecondaryProcess = new E08_ConversionToSecondaryProcess(pre+P_E08, poProp, this);
		moE20InnerPerception_Affects = new E20_InnerPerception_Affects(pre+P_E20, poProp, this);
		moE21ConversionToSecondaryProcess = new E21_ConversionToSecondaryProcess(pre+P_E21, poProp, this);
	}
}
