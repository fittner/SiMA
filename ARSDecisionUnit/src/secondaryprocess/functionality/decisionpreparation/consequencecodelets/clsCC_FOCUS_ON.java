/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.consequencecodelets;

import memorymgmt.enums.eCondition;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 15:16:15
 * 
 */
public class clsCC_FOCUS_ON extends clsConsequenceCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.09.2012 15:16:41
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poReachableGoalList
	 * @param poCodeletHandler
	 */
	public clsCC_FOCUS_ON(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:16:45
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		//If the goal is not found in perception, it has to be newly analysed. If the focus is lost, then default need focus is searched for.
		//As the environmental image is not "mitgedreht", only fix positions are used.
		
		
		//Set consequence
	    this.moGoal.setCondition(eCondition.SET_FOCUS_ON);
		//clsGoalTools.setTaskStatus(this.moGoal, eCondition.GOAL_REACHABLE_IN_PERCEPTION);
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:16:45
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.EXECUTED_FOCUS_ON, eCondition.IS_CONTINUED_PLANGOAL));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:16:45
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		this.moPostConditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.SET_FOCUS_ON, eCondition.IS_CONTINUED_PLANGOAL));
		
	}


	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:41:30
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
	    try {
            this.moGoal.removeCondition(eCondition.EXECUTED_FOCUS_ON);
            this.moGoal.removeCondition(eCondition.NEED_GOAL_FOCUS);
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:10:44
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Executes the consequence of the action FOCUS ON, i. e. set condition SET_FOCUS_ON.";
		
	}

}
