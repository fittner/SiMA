/**
 * C10_PrimaryProcessor.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:37:00
 */
package pa.modules;

import java.util.ArrayList;

import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.interfaces.I1_7;
import pa.interfaces.I2_11;
import pa.interfaces.I2_12;
import pa.interfaces.I2_13;
import pa.interfaces.I3_3;
import pa.interfaces.I5_5;
import pa.interfaces.I6_1;
import pa.interfaces.I6_2;
import pa.interfaces.I7_2;
import pa.interfaces.I7_4;
import pa.loader.plan.clsPlanAction;
import pa.memory.clsMemory;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:37:00
 * 
 */
public class C11_SecondaryProcessor extends clsModuleContainer implements 
				I1_7,
				I2_11,
				I2_12,
				I2_13,
				I3_3,
				I5_5,
				I6_1,
				I6_2,
				I7_2,
				I7_4
				{

	public static final String P_C15 = "C15";
	public static final String P_C16 = "C16";
	public static final String P_C17 = "C17";
	
	public C14_PerceptualPreprocessing moC15PerceptualPreprocessing;
	public C15_Deliberation moC16Deliberation;
	public C16_SecondaryKnowledgeUtilizer moC17SecondaryKnowledgeUtilizer;

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
	public C11_SecondaryProcessor(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( C14_PerceptualPreprocessing.getDefaultProperties(pre+P_C15) );
		oProp.putAll( C15_Deliberation.getDefaultProperties(pre+P_C16) );
		oProp.putAll( C16_SecondaryKnowledgeUtilizer.getDefaultProperties(pre+P_C17) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moC15PerceptualPreprocessing = new C14_PerceptualPreprocessing(pre+P_C15, poProp, this, moMemory);
		moC16Deliberation = new C15_Deliberation(pre+P_C16, poProp, this, moMemory);
		moC17SecondaryKnowledgeUtilizer = new C16_SecondaryKnowledgeUtilizer(pre+P_C17, poProp, this, moMemory);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:34:19
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(int pnData) {
		moC16Deliberation.receive_I3_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:49:48
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList) {
		moC16Deliberation.receive_I1_7(poDriveList);
		moC15PerceptualPreprocessing.receive_I1_7(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:49:48
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryInformation> poPerception) {
		moC15PerceptualPreprocessing.receive_I2_11(poPerception);
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
		moC16Deliberation.receive_I5_5(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@Override
	public void receive_I2_12(ArrayList<clsSecondaryInformation> poFocusedPerception) {
		moC17SecondaryKnowledgeUtilizer.receive_I2_12(poFocusedPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 */
	@Override
	public void receive_I2_13(ArrayList<clsPair<clsSecondaryInformation, clsSecondaryInformationMesh>> poRealityPerception) {
		moC16Deliberation.receive_I2_13(poRealityPerception);
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
		moC15PerceptualPreprocessing.receive_I6_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I6_2#receive_I6_2(int)
	 */
	@Override
	public void receive_I6_2(int pnData) {
		moC16Deliberation.receive_I6_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I7_2#receive_I7_2(int)
	 */
	@Override
	public void receive_I7_2(int pnData) {
		moC17SecondaryKnowledgeUtilizer.receive_I7_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:21:41
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(ArrayList<clsPlanAction> poActionCommands) {
		((I7_4)moEnclosingContainer).receive_I7_4(poActionCommands);
	}
}
