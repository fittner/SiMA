/**
 * CHANGELOG
 *
 * 01.10.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.decisioncodelets;

import memorymgmt.enums.eCondition;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

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
		if (this.moGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)) {

		    this.moGoal.setCondition(eCondition.NEED_GOAL_FOCUS);
		} else if (this.moGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
			//Remove FOCUS_MOVEMENTACTION_SET if set

		    this.moGoal.setCondition(eCondition.NEED_BASIC_ACT_ANALYSIS);	//As in this step a movement will take place, order a new act analysis for the next step.
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
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.SET_MOVEMENT_EXECUTED, eCondition.IS_PERCEPTIONAL_SOURCE));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.SET_MOVEMENT_EXECUTED, eCondition.IS_MEMORY_SOURCE));
		
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
