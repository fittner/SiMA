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
 * 26.09.2012, 11:52:09
 * 
 */
public class clsACExecuteExternalAction extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 26.09.2012 11:52:26
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsACExecuteExternalAction(
			clsWordPresentationMesh poEnvironmentalImage,
			clsShortTermMemory poShortTermMemory,
			clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poCodeletHandler);
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
		ArrayList<clsWordPresentationMesh> oExternalPlans = this.moExternalActionPlanner.generatePlans_AW(this.moEnvironmentalImage, this.moGoal);
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
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.GOAL_NOT_REACHABLE, eCondition.FOCUS_MOVEMENTACTION_SET, eCondition.NEED_INTERNAL_INFO_SET));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.FOCUS_MOVEMENTACTION_SET, eCondition.FOCUS_ON_SET, eCondition.GOAL_REACHABLE_IN_PERCEPTION));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.FOCUS_MOVEMENTACTION_SET, eCondition.PERFORM_RECOMMENDED_ACTION, eCondition.NEED_INTERNAL_INFO_SET));
		
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
	 * @since 26.09.2012 11:52:28
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setName()
	 */
	@Override
	protected void setName() {
		this.moCodeletName = "EXECUTE_EXTERNAL_ACTION";
		
	}

}
