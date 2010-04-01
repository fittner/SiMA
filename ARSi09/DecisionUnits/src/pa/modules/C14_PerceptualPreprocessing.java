/**
 * C15_PerceptualPreprocessing.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:42:06
 */
package pa.modules;

import java.util.ArrayList;

import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.interfaces.I1_7;
import pa.interfaces.I2_11;
import pa.interfaces.I2_12;
import pa.interfaces.I2_13;
import pa.interfaces.I6_1;
import pa.memory.clsMemory;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:42:06
 * 
 */
public class C14_PerceptualPreprocessing extends clsModuleContainer implements
						I1_7,
						I2_11,
						I2_12,
						I2_13,
						I6_1
						{

	public static final String P_E23 = "E23";
	public static final String P_E24 = "E24";
	
	public E23_ExternalPerception_focused moE23ExternalPerception_focused;
	public E24_RealityCheck moE24RealityCheck;

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
	public C14_PerceptualPreprocessing(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E23_ExternalPerception_focused.getDefaultProperties(pre+P_E23) );
		oProp.putAll( E24_RealityCheck.getDefaultProperties(pre+P_E24) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE23ExternalPerception_focused = new E23_ExternalPerception_focused(pre+P_E24, poProp, this);
		moE24RealityCheck = new E24_RealityCheck(pre+P_E23, poProp, this);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:02:03
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList) {
		moE23ExternalPerception_focused.receive_I1_7(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:02:03
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryInformation> poPerception) {
		moE23ExternalPerception_focused.receive_I2_11(poPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:11:44
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@Override
	public void receive_I2_12(ArrayList<clsSecondaryInformation> poFocusedPerception) {
		((I2_12)moEnclosingContainer).receive_I2_12(poFocusedPerception); //to e25 (know. real)
		moE24RealityCheck.receive_I2_12(poFocusedPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:11:44
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 */
	@Override
	public void receive_I2_13(ArrayList<clsPair<clsSecondaryInformation, clsSecondaryInformationMesh>> poRealityPerception) {
		((I2_13)moEnclosingContainer).receive_I2_13(poRealityPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:11:44
	 * 
	 * @see pa.interfaces.I6_1#receive_I6_1(int)
	 */
	@Override
	public void receive_I6_1(int pnData) {
		moE24RealityCheck.receive_I6_1(pnData);
	}
}
