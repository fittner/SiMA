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
import base.tools.ElementNotFoundException;

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
			
			//if (this.moGoal.checkIfConditionExists(eCondition.SET_INTERNAL_INFO)==false && this.moGoal.checkIfConditionExists(eCondition.EXECUTED_SEND_TO_PHANTASY)==false) {
			this.moGoal.setCondition(eCondition.NEED_INTERNAL_INFO);
			//}
		} else if (this.moGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)==true) {
            
            //if (this.moGoal.checkIfConditionExists(eCondition.SET_INTERNAL_INFO)==false && this.moGoal.checkIfConditionExists(eCondition.EXECUTED_SEND_TO_PHANTASY)==false) {
            //TODO: This is a fast solution, which is not general
		    if (this.moGoal.checkIfConditionExists(eCondition.SET_INTERNAL_INFO)==false) {
                this.moGoal.setCondition(eCondition.NEED_INTERNAL_INFO);
            } else {
                this.moGoal.setCondition(eCondition.NEED_BASIC_ACT_ANALYSIS);
            }
		    
            //}
        } else if (this.moGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)==true) {
            this.moGoal.setCondition(eCondition.COMPOSED_CODELET);
            this.moGoal.setCondition(eCondition.GOTO_GOAL_IN_PERCEPTION);
            this.moGoal.setCondition(eCondition.NEED_GOAL_FOCUS);
		}
			
		this.moGoal.setCondition(eCondition.IS_CONTINUED_GOAL);
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 17:27:51
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.IS_NEW_GOAL));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.IS_NEW_GOAL));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.IS_NEW_GOAL));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.SET_DECISION_PHASE_COMPLETE));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.SET_DECISION_PHASE_COMPLETE));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.SET_DECISION_PHASE_COMPLETE));
		
		
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
	protected void removeTriggerCondition() throws ElementNotFoundException {
	    this.moGoal.removeCondition(eCondition.IS_NEW_GOAL);
	    this.moGoal.removeCondition(eCondition.SET_DECISION_PHASE_COMPLETE);
	    
	    //FIXME: These conditions should be removed somewhere else, but it is not the case
	    //this.moGoal.removeCondition(eCondition.SET_INTERNAL_INFO);
	    this.moGoal.removeCondition(eCondition.NEED_BASIC_ACT_ANALYSIS);
	    this.moGoal.removeCondition(eCondition.SET_FOLLOW_ACT);
	    this.moGoal.removeCondition(eCondition.SET_BASIC_ACT_ANALYSIS);
	    this.moGoal.removeCondition(eCondition.NEED_PERFORM_RECOMMENDED_ACTION);
	    
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
