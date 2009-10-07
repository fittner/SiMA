/**
 * C03_Ego.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:11:09
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;

import pa.datatypes.clsThingPresentationMesh;
import pa.interfaces.I1_5;
import pa.interfaces.I1_7;
import pa.interfaces.I2_11;
import pa.interfaces.I2_2;
import pa.interfaces.I2_4;
import pa.interfaces.I2_5;
import pa.interfaces.I2_6;
import pa.interfaces.I2_8;
import pa.interfaces.I2_9;
import pa.interfaces.I3_1;
import pa.interfaces.I3_2;
import pa.interfaces.I3_3;
import pa.interfaces.I4_1;
import pa.interfaces.I4_2;
import pa.interfaces.I4_3;
import pa.interfaces.I7_4;
import pa.memory.clsMemory;
import pa.interfaces.I8_1;
import config.clsBWProperties;
import decisionunit.itf.sensors.clsSensorExtern;
import enums.eSensorExtType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:11:09
 * 
 */
public class C03_Ego extends clsModuleContainer implements
				I1_7,
				I2_2,
				I2_4,
				I2_5,
				I2_6,
				I2_11,
				I1_5,
				I2_8,
				I2_9,
				I3_1,
				I3_2,
				I3_3,
				I4_1,
				I4_2,
				I4_3,
				I7_4,
				I8_1
				{

	public static final String P_C07 = "C07";
	public static final String P_C08 = "C08";
	
	public C07_EnvironmentalInterfaceFunctions moC07EnvironmentalInterfaceFunctions;
	public C08_PsychicMediator moC08PsychicMediator;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:11:44
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public C03_Ego(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( C07_EnvironmentalInterfaceFunctions.getDefaultProperties(pre+P_C07) );
		oProp.putAll( C08_PsychicMediator.getDefaultProperties(pre+P_C08) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moC07EnvironmentalInterfaceFunctions = new C07_EnvironmentalInterfaceFunctions(pre+P_C07, poProp, this, moMemory);
		moC08PsychicMediator = new C08_PsychicMediator(pre+P_C07, poProp, this, moMemory);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@Override
	public void receive_I2_2(HashMap<eSensorExtType, clsSensorExtern> poEnvironmentalData) {
		moC07EnvironmentalInterfaceFunctions.receive_I2_2(poEnvironmentalData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(HashMap<eSensorExtType, clsSensorExtern> poBodyData) {
		moC07EnvironmentalInterfaceFunctions.receive_I2_4(poBodyData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(int pnData) {
		moC08PsychicMediator.receive_I1_5(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(int pnData) {
		moC08PsychicMediator.receive_I2_9(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		moC08PsychicMediator.receive_I3_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		moC08PsychicMediator.receive_I3_2(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:45:19
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(int pnData) {
		moC08PsychicMediator.receive_I2_6(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:45:19
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(int pnData) {
		moC08PsychicMediator.receive_I4_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 16:55:05
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsThingPresentationMesh> poEnvironmentalTP) {
		((I2_5)moEnclosingContainer).receive_I2_5(poEnvironmentalTP);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:30:05
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
	 * 11.08.2009, 17:31:07
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(int pnData) {
		moC08PsychicMediator.receive_I3_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:49:53
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
	 * 12.08.2009, 09:49:53
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
	 * 12.08.2009, 10:56:38
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(int pnData) {
		((I1_7)moEnclosingContainer).receive_I1_7(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:24:59
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(int pnData) {
		moC07EnvironmentalInterfaceFunctions.receive_I7_4(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:45:33
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(int pnData) {
		((I2_11)moEnclosingContainer).receive_I2_11(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:49:00
	 * 
	 * @see pa.interfaces.I8_1#receive_I8_1(int)
	 */
	@Override
	public void receive_I8_1(int pnData) {
		((I8_1)moEnclosingContainer).receive_I8_1(pnData);
		
	}

}
