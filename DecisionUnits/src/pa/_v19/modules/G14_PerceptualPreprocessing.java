/**
 * C15_PerceptualPreprocessing.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:42:06
 */
package pa._v19.modules;

import java.util.ArrayList;

import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I1_7_receive;
import pa._v19.interfaces.receive.I2_11_receive;
import pa._v19.interfaces.receive.I2_12_receive;
import pa._v19.interfaces.receive.I2_13_receive;
import pa._v19.interfaces.receive.I6_1_receive;
import pa._v19.memory.clsMemory;
import pa._v19.memorymgmt.clsKnowledgeBaseHandler;
import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:42:06
 * 
 */
@Deprecated
public class G14_PerceptualPreprocessing extends clsModuleContainer implements
						I1_7_receive,
						I2_11_receive,
						I2_12_receive,
						I2_13_receive,
						I6_1_receive
						{

	public static final String P_E23 = "E23";
	public static final String P_E24 = "E24";
	
	public E23_ExternalPerception_focused moE23ExternalPerception_focused;
	public E24_RealityCheck               moE24RealityCheck;

	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:36:51
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public G14_PerceptualPreprocessing(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
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
	
		moE23ExternalPerception_focused = new E23_ExternalPerception_focused(pre+P_E24, poProp, this, moInterfaceHandler);
		moE24RealityCheck = new E24_RealityCheck(pre+P_E23, poProp, this, moInterfaceHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:02:03
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
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
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
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
	public void receive_I2_12(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception, ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		((I2_12_receive)moEnclosingContainer).receive_I2_12(poFocusedPerception, poDriveList); //to e25 (know. real)
		moE24RealityCheck.receive_I2_12(poFocusedPerception, poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:11:44
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 */
	@Override
	public void receive_I2_13(ArrayList<clsSecondaryDataStructureContainer> poRealityPerception) {
		((I2_13_receive)moEnclosingContainer).receive_I2_13(poRealityPerception);
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
