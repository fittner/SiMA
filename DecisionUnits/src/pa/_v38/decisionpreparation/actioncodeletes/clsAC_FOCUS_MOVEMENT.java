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
import pa._v38.tools.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.09.2012, 11:22:34
 * 
 */
public class clsAC_FOCUS_MOVEMENT extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 26.09.2012 11:22:50
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsAC_FOCUS_MOVEMENT(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
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
		
		ArrayList<clsWordPresentationMesh> oExternalPlans = new ArrayList<clsWordPresentationMesh>();
		
		if (clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_DRIVE_SOURCE)==true || clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_PERCEPTIONAL_SOURCE)==true) {
			oExternalPlans.addAll(this.moExternalActionPlanner.generatePlans_AW(this.moEnvironmentalImage, this.moGoal));
		} else if (clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_MEMORY_SOURCE)==true) {
			oExternalPlans.addAll(this.moExternalActionPlanner.extractRecommendedActionsFromAct(this.moGoal));
		}
		
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
		//Drive
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.NEED_SEARCH_INFO, eCondition.SET_INTERNAL_INFO));
		//Perception
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.NEED_FOCUS_MOVEMENT, eCondition.SET_FOCUS_ON));
		//Memory
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.NEED_PERFORM_RECOMMENDED_ACTION));
		
		
		
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
	 * @since 01.10.2012 15:16:46
	 * 
	 * @see pa._v38.decisionpreparation.clsActionCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		clsGoalTools.removeCondition(this.moGoal, eCondition.NEED_SEARCH_INFO);
		clsGoalTools.removeCondition(this.moGoal, eCondition.NEED_FOCUS_MOVEMENT);
		
	}

}
