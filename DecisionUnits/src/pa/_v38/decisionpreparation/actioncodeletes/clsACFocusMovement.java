/**
 * CHANGELOG
 *
 * 26.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.actioncodeletes;

import java.util.ArrayList;

import pa._v38.decisionpreparation.clsActionCodelet;
import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.storage.clsShortTermMemory;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.09.2012, 11:22:34
 * 
 */
public class clsACFocusMovement extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 26.09.2012 11:22:50
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsACFocusMovement(clsWordPresentationMesh poEnvironmentalImage,
			clsShortTermMemory poShortTermMemory,
			clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:22:53
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		
		ArrayList<clsWordPresentationMesh> oExternalPlans = this.moExternalActionPlanner.generatePlans_AW(this.moEnvironmentalImage, this.moGoal);
		eAction oChosenAction = eAction.NONE;
		
		if (oExternalPlans.isEmpty()==false) {
			oChosenAction = eAction.valueOf(oExternalPlans.get(0).getMoContent());
		}
		
		//Now the movement is gotten. Compose a new action for focusing, rename the action
		if (oChosenAction.equals(eAction.MOVE_FORWARD)) {
			oChosenAction = eAction.FOCUS_MOVE_FORWARD;
		} else if (oChosenAction.equals(eAction.TURN_LEFT)) {
			oChosenAction = eAction.FOCUS_MOVE_FORWARD;
		} else if (oChosenAction.equals(eAction.TURN_RIGHT)) {
			oChosenAction = eAction.FOCUS_MOVE_FORWARD;
		} else if (oChosenAction.equals(eAction.SEARCH1)) {
			oChosenAction = eAction.FOCUS_MOVE_FORWARD;
		} //else if (oExternalActionWPM.getMoContent().equals(eAction.FLEE.toString())) {
		//	oExternalActionWPM.setMoContent(eAction.FOCUS_MOVE_FORWARD.toString());
		//}
		
		this.generateAction(oChosenAction);
		
		//Associate the action with the goal
		setActionAssociationInGoal();
		
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:22:53
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.GOAL_NOT_REACHABLE, eCondition.NEED_INTERNAL_INFO_SET));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.FOCUS_ON_SET, eCondition.GOAL_REACHABLE_IN_PERCEPTION));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.PERFORM_RECOMMENDED_ACTION, eCondition.NEED_INTERNAL_INFO_SET));
		
		
		
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:22:53
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:22:53
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setName()
	 */
	@Override
	protected void setName() {
		this.moCodeletName = "FOCUS_MOVEMENT";
		
	}

}
