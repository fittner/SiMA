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
 * 01.10.2012, 16:19:10
 * 
 */
public class clsDC_XToMoveFocus extends clsDecisionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 01.10.2012 16:19:27
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poReachableGoalList
	 * @param poCodeletHandler
	 */
	public clsDC_XToMoveFocus(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 16:19:29
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		if (clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_PERCEPTIONAL_SOURCE)) {
			clsGoalTools.setCondition(this.moGoal, eCondition.NEED_MOVEMENT);
		} else if (clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_DRIVE_SOURCE)) {
			clsGoalTools.setCondition(this.moGoal, eCondition.NEED_SEARCH_INFO);
		} else if (clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_MEMORY_SOURCE)) {
			clsGoalTools.setCondition(this.moGoal, eCondition.NEED_PERFORM_RECOMMENDED_ACTION);
		}

	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 16:19:29
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.SET_FOCUS_ON, eCondition.SET_FOCUS_MOVEMENT));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.SET_FOCUS_MOVEMENT));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.SET_INTERNAL_INFO));
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 16:19:29
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 16:19:29
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:16:28
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "All focus is set, trigger to perform an external action.";
		
	}

}
