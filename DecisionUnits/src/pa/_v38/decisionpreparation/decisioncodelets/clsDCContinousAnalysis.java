/**
 * CHANGELOG
 *
 * 23.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.decisioncodelets;

import java.util.ArrayList;

import pa._v38.decisionpreparation.clsDecisionCodelet;
import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsCommonCodeletTools;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActDataStructureTools;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.09.2012, 12:34:31
 * 
 */
public class clsDCContinousAnalysis extends clsDecisionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 23.09.2012 12:34:52
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsDCContinousAnalysis(clsWordPresentationMesh poEnvironmentalImage, clsShortTermMemory poShortTermMemory, ArrayList<clsWordPresentationMesh> poReachableGialList, clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poReachableGialList, poCodeletHandler);
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
		
		//Remove currently processed stati
		clsGoalTools.removeTaskStatus(this.moGoal, eCondition.IS_NEW_CONTINUED_GOAL);
		clsGoalTools.removeTaskStatus(this.moGoal, eCondition.NEED_CONTINUOS_ANALYSIS);
		
		
		//Transfer previous stati in general
		if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.NEED_INTERNAL_INFO_SET)==true) {
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.NEED_INTERNAL_INFO_SET);
		}
		if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.FOCUS_MOVEMENTACTION_SET)==true) {
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.FOCUS_MOVEMENTACTION_SET);
		}
		if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.GOAL_NOT_REACHABLE)==true) {
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.GOAL_NOT_REACHABLE);
		}
		
		
		//Transfer previous stati in special
		if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.IS_DRIVE_SOURCE)==true) {
			
			if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.NEED_INTERNAL_INFO_SET)==false &&
					clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.EXECUTED_SEND_TO_PHANTASY)==false) {
				clsGoalTools.setTaskStatus(this.moGoal, eCondition.NEED_INTERNAL_INFO);
			}
			
			
		} else if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.IS_MEMORY_SOURCE)==true) {
			
			//Remove all PI-matches from the images
			clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(this.moGoal);
			clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oSupportiveDataStructure);
			clsActTools.removePIMatchFromWPMAndSubImages(oIntention);
			
		} else if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.IS_PERCEPTIONAL_SOURCE)==true) {
			
			if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.GOAL_REACHABLE_IN_PERCEPTION)==true) {
				clsGoalTools.setTaskStatus(this.moGoal, eCondition.GOAL_REACHABLE_IN_PERCEPTION);
			}
		}
		
		
		
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 12:34:55
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.IS_NEW_CONTINUED_GOAL, eCondition.NEED_CONTINUOS_ANALYSIS));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.IS_NEW_CONTINUED_GOAL, eCondition.NEED_CONTINUOS_ANALYSIS));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.IS_NEW_CONTINUED_GOAL, eCondition.NEED_CONTINUOS_ANALYSIS));
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
		this.moCodeletName = this.getClass().getName();
		
	}

}
