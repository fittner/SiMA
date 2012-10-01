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
 * 27.09.2012, 15:25:15
 * 
 */
public class clsDCActionMovement extends clsDecisionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.09.2012 15:25:36
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poReachableGoalList
	 * @param poCodeletHandler
	 */
	public clsDCActionMovement(clsWordPresentationMesh poEnvironmentalImage,
			clsShortTermMemory poShortTermMemory,
			ArrayList<clsWordPresentationMesh> poReachableGoalList,
			clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poReachableGoalList,
				poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:25:39
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		//Remove the focus movement as a movement has happened
		clsGoalTools.removeTaskStatus(this.moGoal, eCondition.FOCUS_MOVEMENTACTION_SET);
		
		if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.IS_PERCEPTIONAL_SOURCE)) {
			//Remove FOCUS_MOVEMENTACTION_SET if set
			clsGoalTools.removeTaskStatus(this.moGoal, eCondition.FOCUS_ON_SET);
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.NEED_GOAL_FOCUS);
		} else if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.IS_MEMORY_SOURCE)) {
			//Remove FOCUS_MOVEMENTACTION_SET if set
			clsGoalTools.removeTaskStatus(this.moGoal, eCondition.PERFORM_RECOMMENDED_ACTION);
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.NEED_BASIC_ACT_ANALYSIS);	//As in this step a movement will take place, order a new act analysis for the next step.
		}
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:25:39
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_MOVE_FORWARD));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_TURN_LEFT));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_TURN_RIGHT));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_SEARCH1));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:25:39
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:25:39
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setName()
	 */
	@Override
	protected void setName() {
		this.moCodeletName = this.getClass().getName();
		
	}

}
