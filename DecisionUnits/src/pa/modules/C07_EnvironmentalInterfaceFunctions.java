/**
 * C07_EnvironmentalInterfaceFunctions.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:32:50
 */
package pa.modules;

import pa.interfaces.I2_2;
import pa.interfaces.I2_4;
import pa.interfaces.I2_6;
import pa.interfaces.I7_4;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:32:50
 * 
 */
public class C07_EnvironmentalInterfaceFunctions extends clsModuleContainer implements
						I2_2,
						I2_4,
						I2_6,
						I7_4
						{

	public static final String P_E14 = "E14";
	public static final String P_E30 = "E308";
	
	public E14_PreliminaryExternalPerception moE14PreliminaryExternalPerception;
	public E30_MotilityControl moE30MotilityControl;

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
	public C07_EnvironmentalInterfaceFunctions(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E14_PreliminaryExternalPerception.getDefaultProperties(pre+P_E14) );
		oProp.putAll( E30_MotilityControl.getDefaultProperties(pre+P_E30) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE14PreliminaryExternalPerception = new E14_PreliminaryExternalPerception(pre+P_E14, poProp, this);
		moE30MotilityControl = new E30_MotilityControl(pre+P_E30, poProp, this);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:07:36
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(int pnData) {
		moE14PreliminaryExternalPerception.receive_I2_6(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:01:47
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@Override
	public void receive_I2_2(int pnData) {
		moE14PreliminaryExternalPerception.receive_I2_2(pnData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:03:26
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(int pnData) {
		moE14PreliminaryExternalPerception.receive_I2_4(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:26:23
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(int pnData) {
		moE30MotilityControl.receive_I7_4(pnData);
	}
}
