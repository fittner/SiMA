/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.decisioncodelets;

import java.util.ArrayList;

import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsDecisionCodelet;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 15:20:49
 * 
 */
public class clsDCActionFocusMovement extends clsDecisionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.09.2012 15:21:09
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poReachableGoalList
	 * @param poCodeletHandler
	 */
	public clsDCActionFocusMovement(
			clsWordPresentationMesh poEnvironmentalImage,
			clsShortTermMemory poShortTermMemory,
			ArrayList<clsWordPresentationMesh> poReachableGoalList,
			clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poReachableGoalList,
				poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:21:12
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		clsGoalTools.setTaskStatus(this.moGoal, eCondition.FOCUS_MOVEMENTACTION_SET);	//Set first focus
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:21:12
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_FOCUS_MOVE_FORWARD));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_FOCUS_TURN_LEFT));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_FOCUS_TURN_RIGHT));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_FOCUS_SEARCH1));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:21:12
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:21:12
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setName()
	 */
	@Override
	protected void setName() {
		this.moCodeletName = this.getClass().getName();
		
	}

}
