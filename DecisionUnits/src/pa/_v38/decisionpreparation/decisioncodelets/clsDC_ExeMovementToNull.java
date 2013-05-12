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
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.tools.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2012, 16:09:00
 * 
 */
public class clsDC_ExeMovementToNull extends clsDecisionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 01.10.2012 16:09:15
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poReachableGoalList
	 * @param poCodeletHandler
	 */
	public clsDC_ExeMovementToNull(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 16:09:19
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		if (clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_PERCEPTIONAL_SOURCE)) {

			clsGoalTools.setCondition(this.moGoal, eCondition.NEED_GOAL_FOCUS);
		} else if (clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_MEMORY_SOURCE)) {
			//Remove FOCUS_MOVEMENTACTION_SET if set

			clsGoalTools.setCondition(this.moGoal, eCondition.NEED_BASIC_ACT_ANALYSIS);	//As in this step a movement will take place, order a new act analysis for the next step.
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 16:09:19
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.SET_MOVEMENT_EXECUTED));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 16:09:19
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 16:09:19
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:13:39
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "An external movement is executed and new conditions are set to start a new movement.";
		
	}

}
