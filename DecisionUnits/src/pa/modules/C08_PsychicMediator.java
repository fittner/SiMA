/**
 * clsPsychicMediator.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:33:25
 */
package pa.modules;

import java.util.List;

import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I1_5;
import pa.interfaces.I1_6;
import pa.interfaces.I1_7;
import pa.interfaces.I2_10;
import pa.interfaces.I2_11;
import pa.interfaces.I2_6;
import pa.interfaces.I2_8;
import pa.interfaces.I2_9;
import pa.interfaces.I3_1;
import pa.interfaces.I3_2;
import pa.interfaces.I3_3;
import pa.interfaces.I4_1;
import pa.interfaces.I4_2;
import pa.interfaces.I4_3;
import pa.interfaces.I5_1;
import pa.interfaces.I5_2;
import pa.interfaces.I5_5;
import pa.interfaces.I6_3;
import pa.interfaces.I7_4;
import pa.memory.clsMemory;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:33:25
 * 
 */
public class C08_PsychicMediator extends clsModuleContainer implements 
					I1_5,
					I1_6,
					I1_7,
					I2_6,
					I2_8,
					I2_9,
					I2_10,
					I2_11,
					I3_1,
					I3_2,
					I3_3,
					I4_1,
					I4_2,
					I4_3,
					I5_1,
					I5_2,
					I5_5,
					I6_3,
					I7_4
					{

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
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
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
	
		moC09PrimaryProcessor = new C09_PrimaryProcessor(pre+P_C09, poProp, this, moMemory);
		moC10SecondaryProcessor = new C11_SecondaryProcessor(pre+P_C10, poProp, this, moMemory);
		moC11PrimaryToSecondaryInterface1 = new C10_PrimaryToSecondaryInterface(pre+P_C11, poProp, this, moMemory);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:21:52
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(int pnData) {
		moC09PrimaryProcessor.receive_I2_6(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:22:04
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(int pnData) {
		((I2_8)moEnclosingContainer).receive_I2_8(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:32:38
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(int pnData) {
		moC10SecondaryProcessor.receive_I3_3(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
		moC09PrimaryProcessor.receive_I1_5(poData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(int pnData) {
		moC11PrimaryToSecondaryInterface1.receive_I1_6(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(int pnData) {
		moC09PrimaryProcessor.receive_I2_9(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		moC09PrimaryProcessor.receive_I3_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(int pnData) {
		((I4_1)moEnclosingContainer).receive_I4_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(int pnData) {
		((I4_2)moEnclosingContainer).receive_I4_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(int pnData) {
		moC09PrimaryProcessor.receive_I4_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(int pnData) {
		moC11PrimaryToSecondaryInterface1.receive_I5_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I6_3#receive_I6_3(int)
	 */
	@Override
	public void receive_I6_3(int pnData) {
		((I5_2)moEnclosingContainer).receive_I5_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:25
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		moC09PrimaryProcessor.receive_I3_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:35:06
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(int pnData) {
		moC11PrimaryToSecondaryInterface1.receive_I5_2(pnData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:45:16
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(int pnData) {
		moC10SecondaryProcessor.receive_I1_7(pnData); //e23&e26 (perc & delib)
		((I1_7)moEnclosingContainer).receive_I1_7(pnData); //e22 (super ego)
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:45:16
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(int pnData) {
		((I2_11)moEnclosingContainer).receive_I2_11(pnData); //to e22 (super ego)
		moC10SecondaryProcessor.receive_I2_11(pnData);		 //to e23
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:45:16
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 */
	@Override
	public void receive_I5_5(int pnData) {
		moC10SecondaryProcessor.receive_I5_5(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:24:22
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(int pnData) {
		((I7_4)moEnclosingContainer).receive_I7_4(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 13:09:48
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I2_10(int pnData) {
		moC11PrimaryToSecondaryInterface1.receive_I2_10(pnData);
		
	}

}
