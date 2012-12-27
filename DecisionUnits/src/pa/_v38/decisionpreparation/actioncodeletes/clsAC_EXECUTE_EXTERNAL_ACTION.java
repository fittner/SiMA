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
 * 26.09.2012, 11:52:09
 * 
 */
public class clsAC_EXECUTE_EXTERNAL_ACTION extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 26.09.2012 11:52:26
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsAC_EXECUTE_EXTERNAL_ACTION(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:52:28
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
		
		this.generateAction(oChosenAction);
		
		//Associate the action with the goal
		setActionAssociationInGoal();
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:52:28
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		//Drives
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.NEED_SEARCH_INFO, eCondition.SET_FOCUS_MOVEMENT, eCondition.SET_INTERNAL_INFO));
		//Perception
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.NEED_MOVEMENT, eCondition.SET_FOCUS_ON, eCondition.SET_FOCUS_MOVEMENT));
		//Memory
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.NEED_PERFORM_RECOMMENDED_ACTION, eCondition.SET_FOCUS_MOVEMENT));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:52:28
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:15:19
	 * 
	 * @see pa._v38.decisionpreparation.clsActionCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		//Update goal status - remove the conditions to execute this codelet
		clsGoalTools.removeCondition(this.moGoal, eCondition.NEED_PERFORM_RECOMMENDED_ACTION);
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:03:43
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Executes an external action with the action planner.";
		
	}

}
