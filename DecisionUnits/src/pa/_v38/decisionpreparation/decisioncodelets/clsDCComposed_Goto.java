/**
 * CHANGELOG
 *
 * 01.10.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.decisioncodelets;

import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsDecisionCodelet;
import pa._v38.decisionpreparation.eCodeletType;
import pa._v38.memorymgmt.enums.eCondition;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2012, 20:25:52
 * 
 */
public class clsDCComposed_Goto extends clsDecisionCodelet {

	private clsCodeletHandler moInternalCodeletHandler;
	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 01.10.2012 20:26:10
	 *
	 * @param poCodeletHandler
	 */
	public clsDCComposed_Goto(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);

		moInternalCodeletHandler = new clsCodeletHandler(poCodeletHandler.getMoEnvironmentalImageMemory(), poCodeletHandler.getShortTermMemory());
		
		//Register codelets in the new codelethandler
		clsDC_InitAction oDCTrans_InitAction = new clsDC_InitAction(moInternalCodeletHandler);
		clsDC_SET_NEED_MOVEMENT_FOCUS oDCTrans_SET_NEED_FOCUS = new clsDC_SET_NEED_MOVEMENT_FOCUS(moInternalCodeletHandler);
		clsDC_ExeMovementToNull oDCTrans_ExeMovementToNull = new clsDC_ExeMovementToNull(moInternalCodeletHandler);
		clsDC_FocusToMoveFocus oDCTrans_FocusToMove = new clsDC_FocusToMoveFocus(moInternalCodeletHandler);
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 20:26:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		this.moInternalCodeletHandler.executeMatchingCodelets(moGoal, eCodeletType.DECISION, -1);
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 20:26:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.GOTO_GOAL_IN_PERCEPTION, eCondition.COMPOSED_CODELET));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 20:26:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 20:26:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		// TODO (wendt) - Auto-generated method stub
		
	}

}
