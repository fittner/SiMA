/**
 * C10_PrimaryProcessor.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:37:00
 */
package pa.modules._v30;

import java.util.ArrayList;

import pa._v30.clsInterfaceHandler;
import pa.interfaces.receive._v30.I1_7_receive;
import pa.interfaces.receive._v30.I2_11_receive;
import pa.interfaces.receive._v30.I2_12_receive;
import pa.interfaces.receive._v30.I2_13_receive;
import pa.interfaces.receive._v30.I3_3_receive;
import pa.interfaces.receive._v30.I5_5_receive;
import pa.interfaces.receive._v30.I6_1_receive;
import pa.interfaces.receive._v30.I6_2_receive;
import pa.interfaces.receive._v30.I7_2_receive;
import pa.interfaces.receive._v30.I7_3_receive;
import pa.interfaces.receive._v30.I7_4_receive;
import pa.interfaces.receive._v30.I7_5_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAct;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsWordPresentation;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:37:00
 * 
 */
public class G11_SecondaryProcessor extends clsModuleContainer implements 
				I1_7_receive,
				I2_11_receive,
				I2_12_receive,
				I2_13_receive,
				I3_3_receive,
				I5_5_receive,
				I6_1_receive,
				I6_2_receive,
				I7_2_receive,
				I7_3_receive,
				I7_4_receive,
				I7_5_receive
				{

	public static final String P_G14 = "G14";
	public static final String P_G15 = "G15";
	public static final String P_G16 = "G16";
	
	public G14_PerceptualPreprocessing    moG14PerceptualPreprocessing;
	public G15_Deliberation               moG15Deliberation;
	public G16_SecondaryKnowledgeUtilizer moG16SecondaryKnowledgeUtilizer;

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
	public G11_SecondaryProcessor(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( G14_PerceptualPreprocessing.getDefaultProperties(pre+P_G14) );
		oProp.putAll( G15_Deliberation.getDefaultProperties(pre+P_G15) );
		oProp.putAll( G16_SecondaryKnowledgeUtilizer.getDefaultProperties(pre+P_G16) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moG14PerceptualPreprocessing = new G14_PerceptualPreprocessing(pre+P_G14, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
		moG15Deliberation = new G15_Deliberation(pre+P_G15, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
		moG16SecondaryKnowledgeUtilizer = new G16_SecondaryKnowledgeUtilizer(pre+P_G16, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:34:19
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(ArrayList<clsAct> poRuleList) {
		moG15Deliberation.receive_I3_3(poRuleList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:49:48
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moG15Deliberation.receive_I1_7(poDriveList);
		moG14PerceptualPreprocessing.receive_I1_7(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:49:48
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		moG14PerceptualPreprocessing.receive_I2_11(poPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:49:48
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 */
	@Override
	public void receive_I5_5(int pnData) {
		moG15Deliberation.receive_I5_5(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@Override
	public void receive_I2_12(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception, ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moG16SecondaryKnowledgeUtilizer.receive_I2_12(poFocusedPerception, poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 */
	@Override
	public void receive_I2_13(ArrayList<clsSecondaryDataStructureContainer> poRealityPerception) {
		moG15Deliberation.receive_I2_13(poRealityPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I6_1#receive_I6_1(int)
	 */
	@Override
	public void receive_I6_1(int pnData) {
		moG14PerceptualPreprocessing.receive_I6_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I6_2#receive_I6_2(int)
	 */
	@Override
	public void receive_I6_2(ArrayList<ArrayList<clsAct>> poPlanOutput) {
		moG15Deliberation.receive_I6_2(poPlanOutput);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I7_2#receive_I7_2(int)
	 */
	@Override
	public void receive_I7_2(ArrayList<clsSecondaryDataStructureContainer> poGoal_Output) {
		moG16SecondaryKnowledgeUtilizer.receive_I7_2(poGoal_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(ArrayList<clsWordPresentation> poActionCommands) {
		((I7_4_receive)moEnclosingContainer).receive_I7_4(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:51:20
	 * 
	 * @see pa.interfaces.I7_3#receive_I7_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I7_3(ArrayList<clsWordPresentation> poActionCommands) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:54:56
	 * 
	 * @see pa.interfaces.I7_5#receive_I7_5(int)
	 */
	@Override
	public void receive_I7_5(int pnData) {
		moG15Deliberation.receive_I7_5(pnData);
		
	}
}
