/**
 * C04_SuperEgo.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:11:20
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsSecondaryInformation;
import pa.interfaces.I1_5;
import pa.interfaces.I1_7;
import pa.interfaces.I2_11;
import pa.interfaces.I2_9;
import pa.interfaces.I3_1;
import pa.interfaces.I3_2;
import pa.interfaces.I3_3;
import pa.memory.clsMemory;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:11:20
 * 
 */
public class G04_SuperEgo extends clsModuleContainer implements
						I1_5,
						I1_7,
						I3_1,
						I3_2,
						I2_9,
						I2_11,
						I3_3
						{
	public static final String P_E07 = "E07";
	public static final String P_E22 = "E22";
	
	public E07_SuperEgo_unconscious  moE07SuperEgoUnconscious;
	public E22_SuperEgo_preconscious moE22SuperEgoPreconscious;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:11:51
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public G04_SuperEgo(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();

		oProp.putAll( E07_SuperEgo_unconscious.getDefaultProperties(pre+P_E07) );
		oProp.putAll( E22_SuperEgo_preconscious.getDefaultProperties(pre+P_E22) );
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE07SuperEgoUnconscious = new E07_SuperEgo_unconscious(pre+P_E07, poProp, this);
		moE22SuperEgoPreconscious = new E22_SuperEgo_preconscious(pre+P_E22, poProp, this);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
		moE07SuperEgoUnconscious.receive_I1_5(poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList) {
		moE22SuperEgoPreconscious.receive_I1_7(poDriveList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		((I3_1)moEnclosingContainer).receive_I3_1(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation) {
		moE07SuperEgoUnconscious.receive_I2_9(poMergedPrimaryInformation);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryInformation> poPerception) {
		moE22SuperEgoPreconscious.receive_I2_11(poPerception);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(int pnData) {
		((I3_3)moEnclosingContainer).receive_I3_3(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:28:01
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		((I3_2)moEnclosingContainer).receive_I3_2(pnData);
		
	}

}
