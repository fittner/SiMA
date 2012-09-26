/**
 * CHANGELOG
 *
 * 23.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.analysiscodelets;

import pa._v38.decisionpreparation.clsAnalysisCodelet;
import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsCommonCodeletTools;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.09.2012, 12:34:31
 * 
 */
public class clsACDriveContinousAnalysis extends clsAnalysisCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 23.09.2012 12:34:52
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsACDriveContinousAnalysis(
			clsWordPresentationMesh poEnvironmentalImage,
			clsShortTermMemory poShortTermMemory,
			clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 12:34:55
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		clsWordPresentationMesh oPreviousGoal = clsCommonCodeletTools.getPreviousGoalFromShortTermMemory(moShortTermMemory);
		
		if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.NEED_INTERNAL_INFO_SET)==true) {
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.NEED_INTERNAL_INFO_SET);
		}
		if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.FOCUS_MOVEMENTACTION_SET)==true) {
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.FOCUS_MOVEMENTACTION_SET);
		}
		if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.GOAL_NOT_REACHABLE)==true) {
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.GOAL_NOT_REACHABLE);
		}
		
		clsGoalTools.removeTaskStatus(this.moGoal, eCondition.IS_NEW_CONTINUED_GOAL);
		
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 12:34:55
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.IS_NEW_CONTINUED_GOAL));
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 12:34:55
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 12:34:55
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setName()
	 */
	@Override
	protected void setName() {
		this.moCodeletName = "ACDriveContinousAnalysis";
		
	}

}
