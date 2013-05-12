/**
 * C16_Deliberation.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:42:32
 */
package pa.modules;

import pa.interfaces.I1_7;
import pa.interfaces.I2_13;
import pa.interfaces.I3_3;
import pa.interfaces.I5_5;
import pa.interfaces.I6_2;
import pa.interfaces.I7_1;
import pa.interfaces.I7_2;
import pa.interfaces.I7_3;
import pa.interfaces.I7_4;
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
					I7_4
					{

	public static final String P_E26 = "E26";
	public static final String P_E27 = "E27";
	public static final String P_E29 = "E29";
	
	public E26_DecisionMaking moE26DecisionMaking;
	public E27_GenerationOfImaginaryActions moE27GenerationOfImaginaryActions;
	public E29_EvaluationOfImaginaryActions moE29EvaluationOfImaginaryActions;

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
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E26_DecisionMaking.getDefaultProperties(pre+P_E26) );
		oProp.putAll( E27_GenerationOfImaginaryActions.getDefaultProperties(pre+P_E27) );
		oProp.putAll( E29_EvaluationOfImaginaryActions.getDefaultProperties(pre+P_E29) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE26DecisionMaking = new E26_DecisionMaking(pre+P_E26, poProp, this);
		moE27GenerationOfImaginaryActions = new E27_GenerationOfImaginaryActions(pre+P_E27, poProp, this);
		moE29EvaluationOfImaginaryActions = new E29_EvaluationOfImaginaryActions(pre+P_E29, poProp, this);
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
	public void receive_I1_7(int pnData) {
		moE26DecisionMaking.receive_I1_7(pnData);
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
	public void receive_I2_13(int pnData) {
		moE26DecisionMaking.receive_I2_13(pnData);
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
	public void receive_I7_1(int pnData) {
		moE27GenerationOfImaginaryActions.receive_I7_1(pnData);
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
	public void receive_I7_3(int pnData) {
		moE29EvaluationOfImaginaryActions.receive_I7_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:16:16
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(int pnData) {
		((I7_4)moEnclosingContainer).receive_I7_4(pnData);
	}
}
