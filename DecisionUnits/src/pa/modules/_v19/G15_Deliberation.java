/**
 * C16_Deliberation.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:42:32
 */
package pa.modules._v19;

import java.util.ArrayList;

import pa._v19.clsInterfaceHandler;
import pa.interfaces.receive._v19.I1_7_receive;
import pa.interfaces.receive._v19.I2_13_receive;
import pa.interfaces.receive._v19.I3_3_receive;
import pa.interfaces.receive._v19.I5_5_receive;
import pa.interfaces.receive._v19.I6_2_receive;
import pa.interfaces.receive._v19.I7_1_receive;
import pa.interfaces.receive._v19.I7_2_receive;
import pa.interfaces.receive._v19.I7_3_receive;
import pa.interfaces.receive._v19.I7_4_receive;
import pa.interfaces.receive._v19.I7_5_receive;
import pa.interfaces.receive._v19.I7_6_receive;
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
 * 11.08.2009, 15:42:32
 * 
 */
@Deprecated
public class G15_Deliberation extends clsModuleContainer implements
					I1_7_receive,
					I2_13_receive,
					I3_3_receive,
					I5_5_receive,
					I6_2_receive,
					I7_1_receive,
					I7_2_receive,
					I7_3_receive,
					I7_4_receive,
					I7_5_receive,
					I7_6_receive
					{

	public static final String P_E26 = "E26";
	public static final String P_E27 = "E27";
	public static final String P_E29 = "E29";
	public static final String P_E33 = "E33";
	
	public E26_DecisionMaking               moE26DecisionMaking;
	public E27_GenerationOfImaginaryActions moE27GenerationOfImaginaryActions;
	public E29_EvaluationOfImaginaryActions moE29EvaluationOfImaginaryActions;
	public E33_RealityCheck2                moE33RealityCheck2;
	
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
	public G15_Deliberation(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E26_DecisionMaking.getDefaultProperties(pre+P_E26) );
		oProp.putAll( E27_GenerationOfImaginaryActions.getDefaultProperties(pre+P_E27) );
		oProp.putAll( E29_EvaluationOfImaginaryActions.getDefaultProperties(pre+P_E29) );
		oProp.putAll( E33_RealityCheck2.getDefaultProperties(pre+P_E33) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE26DecisionMaking = new E26_DecisionMaking(pre+P_E26, poProp, this, moInterfaceHandler);
		moE27GenerationOfImaginaryActions = new E27_GenerationOfImaginaryActions(pre+P_E27, poProp, this, moInterfaceHandler);
		moE29EvaluationOfImaginaryActions = new E29_EvaluationOfImaginaryActions(pre+P_E29, poProp, this, moInterfaceHandler);
		moE33RealityCheck2 = new E33_RealityCheck2(pre+P_E33, poProp, this, moInterfaceHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:34:59
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(ArrayList<clsAct> poRuleList) {
		moE26DecisionMaking.receive_I3_3(poRuleList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:59:42
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
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
	public void receive_I2_13(ArrayList<clsSecondaryDataStructureContainer> poRealityPerception) {
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
	public void receive_I6_2(ArrayList<ArrayList<clsAct>> poPlanOutput) {
		moE27GenerationOfImaginaryActions.receive_I6_2(poPlanOutput);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:16:16
	 * 
	 * @see pa.interfaces.I7_1#receive_I7_1(int)
	 */
	@Override
	public void receive_I7_1(ArrayList<clsSecondaryDataStructureContainer> poTemplateResult) {
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
	public void receive_I7_2(ArrayList<clsSecondaryDataStructureContainer> poGoal_Output) {
		((I7_2_receive)moEnclosingContainer).receive_I7_2(poGoal_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:16:16
	 * 
	 * @see pa.interfaces.I7_3#receive_I7_3(int)
	 */
	@Override
	public void receive_I7_3(ArrayList<clsWordPresentation> poActionCommands) {
		moE29EvaluationOfImaginaryActions.receive_I7_3(poActionCommands);
		moE33RealityCheck2.receive_I7_3(poActionCommands);
		((I7_3_receive)moEnclosingContainer).receive_I7_3(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:16:16
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
