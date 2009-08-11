/**
 * C1_Body.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:09:50
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:09:50
 * 
 */
public class C01_Body extends clsModuleContainer {
	public static final String P_E01 = "E01";
	public static final String P_E02 = "E02";
	public static final String P_E10 = "E10";
	public static final String P_E11 = "E11";
	public static final String P_E12 = "E12";
	public static final String P_E13 = "E13";
	public static final String P_E31 = "E31";
	public static final String P_E32 = "E32";
	
	public E01_Homeostases moE01Homeostases;
	public E02_NeurosymbolizationOfNeeds moE02NeurosymbolizationOfNeeds;
	public E10_SensorsEnvironment moE10SensorsEnvironment;
	public E11_NeuroSymbolsEnvironment moE11NeuroSymbolsEnvironment;
	public E12_SensorsBody moE12SensorsBody;
	public E13_NeuroSymbolsBody moE13NeuroSymbolsBody;
	public E31_NeuroDeSymbolization moE31NeuroDeSymbolization;
	public E32_Actuators moE32Actuators;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:10:04
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public C01_Body(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E01_Homeostases.getDefaultProperties(pre+P_E01) );
		oProp.putAll( E02_NeurosymbolizationOfNeeds.getDefaultProperties(pre+P_E02) );
		oProp.putAll( E10_SensorsEnvironment.getDefaultProperties(pre+P_E10) );
		oProp.putAll( E11_NeuroSymbolsEnvironment.getDefaultProperties(pre+P_E11) );
		oProp.putAll( E12_SensorsBody.getDefaultProperties(pre+P_E12) );
		oProp.putAll( E13_NeuroSymbolsBody.getDefaultProperties(pre+P_E13) );
		oProp.putAll( E31_NeuroDeSymbolization.getDefaultProperties(pre+P_E31) );
		oProp.putAll( E32_Actuators.getDefaultProperties(pre+P_E32) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE01Homeostases = new E01_Homeostases(pre+P_E01, poProp, this);
		moE02NeurosymbolizationOfNeeds = new E02_NeurosymbolizationOfNeeds(pre+P_E02, poProp, this);
		moE10SensorsEnvironment = new E10_SensorsEnvironment(pre+P_E10, poProp, this);
		moE11NeuroSymbolsEnvironment = new E11_NeuroSymbolsEnvironment(pre+P_E11, poProp, this);
		moE12SensorsBody = new E12_SensorsBody(pre+P_E12, poProp, this);
		moE13NeuroSymbolsBody = new E13_NeuroSymbolsBody(pre+P_E13, poProp, this);
		moE31NeuroDeSymbolization = new E31_NeuroDeSymbolization(pre+P_E31, poProp, this);
		moE32Actuators = new E32_Actuators(pre+P_E32, poProp, this);
	}

}
