/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.consequencecodelets;

import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsConsequenceCodelet;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.tools.clsGoalTools;

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
		clsGoalTools.setCondition(this.moGoal, eCondition.SET_FOCUS_MOVEMENT);	//Set first focus
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
	 * @since 01.10.2012 15:29:16
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_FOCUS_MOVE_FORWARD);
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_FOCUS_TURN_LEFT);
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_FOCUS_TURN_RIGHT);
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_FOCUS_SEARCH1);
		
	}

}
