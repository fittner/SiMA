/**
 * C16_Deliberation.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:42:32
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;

import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.interfaces.I1_7;
import pa.interfaces.I2_13;
import pa.interfaces.I3_3;
import pa.interfaces.I5_5;
import pa.interfaces.I6_2;
import pa.interfaces.I7_1;
import pa.interfaces.I7_2;
import pa.interfaces.I7_3;
import pa.interfaces.I7_4;
import pa.interfaces.I7_5;
import pa.interfaces.I7_6;
import pa.loader.plan.clsPlanAction;
import pa.memory.clsMemory;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:42:32
 * 
 */
public class C15_Deliberation extends clsModuleContainer implements
					I1_7,
					I2_13,
					I3_3,
					I5_5,
					I6_2,
					I7_1,
					I7_2,
					I7_3,
					I7_4,
					I7_5,
					I7_6
					{

	public static final String P_E26 = "E26";
	public static final String P_E27 = "E27";
	public static final String P_E29 = "E29";
	public static final String P_E33 = "E33";
	public static final String P_E34 = "E34";
	
	public E26_DecisionMaking moE26DecisionMaking;
	public E27_GenerationOfImaginaryActions moE27GenerationOfImaginaryActions;
	public E29_EvaluationOfImaginaryActions moE29EvaluationOfImaginaryActions;
	public E33_RealityCheck2 moE33RealityCheck2;
	public E34_KnowledgeAboutReality2 moE34KnowledgeAboutReality2;
	
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
	public C15_Deliberation(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E26_DecisionMaking.getDefaultProperties(pre+P_E26) );
		oProp.putAll( E27_GenerationOfImaginaryActions.getDefaultProperties(pre+P_E27) );
		oProp.putAll( E29_EvaluationOfImaginaryActions.getDefaultProperties(pre+P_E29) );
		oProp.putAll( E33_RealityCheck2.getDefaultProperties(pre+P_E33) );
		oProp.putAll( E34_KnowledgeAboutReality2.getDefaultProperties(pre+P_E34) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE26DecisionMaking = new E26_DecisionMaking(pre+P_E26, poProp, this);
		moE27GenerationOfImaginaryActions = new E27_GenerationOfImaginaryActions(pre+P_E27, poProp, this);
		moE29EvaluationOfImaginaryActions = new E29_EvaluationOfImaginaryActions(pre+P_E29, poProp, this);
		moE33RealityCheck2 = new E33_RealityCheck2(pre+P_E33, poProp, this);
		moE34KnowledgeAboutReality2 = new E34_KnowledgeAboutReality2(pre+P_E34, poProp, this);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:34:59
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(int pnData) {
		moE26DecisionMaking.receive_I3_3(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:59:42
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList) {
		moE26DecisionMaking.receive_I1_7(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:59:42
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 */
	@Override
	public void receive_I5_5(int pnData) {
		moE26DecisionMaking.receive_I5_5(pnData);
		moE29EvaluationOfImaginaryActions.receive_I5_5(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:16:16
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 */
	@Override
	public void receive_I2_13(ArrayList<clsPair<clsSecondaryInformation, clsSecondaryInformationMesh>> poRealityPerception) {
		moE26DecisionMaking.receive_I2_13(poRealityPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:16:16
	 * 
	 * @see pa.interfaces.I6_2#receive_I6_2(int)
	 */
	@Override
	public void receive_I6_2(int pnData) {
		moE27GenerationOfImaginaryActions.receive_I6_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:16:16
	 * 
	 * @see pa.interfaces.I7_1#receive_I7_1(int)
	 */
	@Override
	public void receive_I7_1(HashMap<String, clsPair<clsSecondaryInformation, Double>> poTemplateResult) {
		moE27GenerationOfImaginaryActions.receive_I7_1(poTemplateResult);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:16:16
	 * 
	 * @see pa.interfaces.I7_2#receive_I7_2(int)
	 */
	@Override
	public void receive_I7_2(int pnData) {
		((I7_2)moEnclosingContainer).receive_I7_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:16:16
	 * 
	 * @see pa.interfaces.I7_3#receive_I7_3(int)
	 */
	@Override
	public void receive_I7_3(ArrayList<clsPlanAction> poActionCommands) {
		moE29EvaluationOfImaginaryActions.receive_I7_3(poActionCommands);
		moE33RealityCheck2.receive_I7_3(poActionCommands);
		moE34KnowledgeAboutReality2.receive_I7_3(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:16:16
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(ArrayList<clsPlanAction> poActionCommands) {
		((I7_4)moEnclosingContainer).receive_I7_4(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:46:30
	 * 
	 * @see pa.interfaces.I7_6#receive_I7_6(int)
	 */
	@Override
	public void receive_I7_6(int pnData) {
		moE29EvaluationOfImaginaryActions.receive_I7_6(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:54:28
	 * 
	 * @see pa.interfaces.I7_5#receive_I7_5(int)
	 */
	@Override
	public void receive_I7_5(int pnData) {
		moE33RealityCheck2.receive_I7_5(pnData);
		
	}
}
