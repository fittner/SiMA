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

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2012, 17:27:27
 * 
 */
public class clsDC_InitAction extends clsDecisionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 01.10.2012 17:27:49
	 *
	 * @param poCodeletHandler
	 */
	public clsDC_InitAction(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 17:27:51
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		if (this.moGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)==true) {
			
			if (this.moGoal.checkIfConditionExists(eCondition.SET_INTERNAL_INFO)==false &&
			        this.moGoal.checkIfConditionExists(eCondition.EXECUTED_SEND_TO_PHANTASY)==false) {
			    this.moGoal.setCondition(eCondition.NEED_INTERNAL_INFO);
			}
		} else if (this.moGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)==true) {
		    this.moGoal.setCondition(eCondition.NEED_GOAL_FOCUS);
		}
			
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 17:27:51
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 17:27:51
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 17:27:51
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:14:45
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Set the initial action for a goal.";
		
	}

}
