/**
 * C11_PrimaryToSecondaryInterface.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:37:27
 */
package pa.modules;

import pa.interfaces.I1_6;
import pa.interfaces.I1_7;
import pa.interfaces.I2_10;
import pa.interfaces.I2_11;
import pa.interfaces.I5_1;
import pa.interfaces.I5_2;
import pa.interfaces.I5_3;
import pa.interfaces.I5_4;
import pa.interfaces.I5_5;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:37:27
 * 
 */
public class C10_PrimaryToSecondaryInterface extends clsModuleContainer implements
						I1_6,
						I1_7,
						I2_10,
						I2_11,
						I5_1,
						I5_2,
						I5_3,
						I5_4,
						I5_5
						{

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

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:20:36
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(int pnData) {
		moE08ConversionToSecondaryProcess.receive_I1_6(pnData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:35:40
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(int pnData) {
		moE20InnerPerception_Affects.receive_I5_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:35:40
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(int pnData) {
		moE20InnerPerception_Affects.receive_I5_2(pnData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(int pnData) {
		((I1_7)moEnclosingContainer).receive_I1_7(pnData); //to e22 (super ego)
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I2_10(int pnData) {
		moE21ConversionToSecondaryProcess.receive_I2_10(pnData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(int pnData) {
		((I2_11)moEnclosingContainer).receive_I2_11(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I5_3#receive_I5_3(int)
	 */
	@Override
	public void receive_I5_3(int pnData) {
		moE20InnerPerception_Affects.receive_I5_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I5_4#receive_I5_4(int)
	 */
	@Override
	public void receive_I5_4(int pnData) {
		moE20InnerPerception_Affects.receive_I5_4(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 */
	@Override
	public void receive_I5_5(int pnData) {
		((I5_5)moEnclosingContainer).receive_I5_5(pnData);
	}
}
