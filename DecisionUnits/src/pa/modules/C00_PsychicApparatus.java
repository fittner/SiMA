/**
 * C0_PsychicApparatus.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:06:02
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:06:02
 * 
 */
public class C00_PsychicApparatus extends clsModuleContainer {
	public static final String P_C01 = "C01";
	public static final String P_C02 = "C02";
	public static final String P_C03 = "C03";
	public static final String P_C04 = "C04";
	
	public C01_Body moC01Body;
	public C02_Id moC02Id;
	public C03_Ego moC03Ego;
	public C04_SuperEgo moC04SuperEgo;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:06:42
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public C00_PsychicApparatus(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp, null);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( C01_Body.getDefaultProperties(pre+P_C01) );
		oProp.putAll( C02_Id.getDefaultProperties(pre+P_C02) );
		oProp.putAll( C03_Ego.getDefaultProperties(pre+P_C03) );
		oProp.putAll( C04_SuperEgo.getDefaultProperties(pre+P_C04) );
		
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moC01Body = new C01_Body(pre+P_C01, poProp, this);
		moC02Id = new C02_Id(pre+P_C02, poProp, this);
		moC03Ego = new C03_Ego(pre+P_C03, poProp, this);
		moC04SuperEgo = new C04_SuperEgo(pre+P_C04, poProp, this);

	}	


}
