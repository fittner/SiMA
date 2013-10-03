/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.consequencecodelets;

import pa._v38.memorymgmt.enums.eCondition;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 15:20:49
 * 
 */
public class clsCC_FOCUS_MOVEMENT extends clsConsequenceCodelet {

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
	public clsCC_FOCUS_MOVEMENT(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
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
		//This is the consequence of the movement
	    this.moGoal.setCondition(eCondition.SET_FOCUS_MOVEMENT);	//Set first focus
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
		this.moPostConditionGroupList.add(new clsConditionGroup(eCondition.SET_FOCUS_MOVEMENT));
		
	}


	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:29:16
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
	    try {
            this.moGoal.removeCondition(eCondition.EXECUTED_FOCUS_MOVE_FORWARD);
            this.moGoal.removeCondition(eCondition.EXECUTED_FOCUS_TURN_LEFT);
            this.moGoal.removeCondition(eCondition.EXECUTED_FOCUS_TURN_RIGHT);
            this.moGoal.removeCondition(eCondition.EXECUTED_FOCUS_SEARCH1);
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:09:09
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Executes the consequence of the action FOCUS MOVEMENT, i. e. set condition SET_FOCUS_MOVEMENT.";
		
	}

}
