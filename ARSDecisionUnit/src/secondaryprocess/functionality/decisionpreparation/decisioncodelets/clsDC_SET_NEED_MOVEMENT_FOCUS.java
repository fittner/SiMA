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
 * 01.10.2012, 15:51:30
 * 
 */
public class clsDC_SET_NEED_MOVEMENT_FOCUS extends clsDecisionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 01.10.2012 15:51:45
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poReachableGoalList
	 * @param poCodeletHandler
	 */
	public clsDC_SET_NEED_MOVEMENT_FOCUS(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:51:48
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
	    this.moGoal.setCondition(eCondition.NEED_FOCUS_MOVEMENT);
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:51:48
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.SET_FOCUS_ON));
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:51:48
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:51:48
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:15:25
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Trigger the action to set movement focus.";
		
	}

}
