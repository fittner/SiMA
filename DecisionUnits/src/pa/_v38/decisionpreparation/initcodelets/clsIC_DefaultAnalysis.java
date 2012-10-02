/**
 * CHANGELOG
 *
 * 23.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.initcodelets;

import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsCommonCodeletTools;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsInitCodelet;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
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
public class clsIC_DefaultAnalysis extends clsInitCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 23.09.2012 12:34:52
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsIC_DefaultAnalysis(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
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
		
		
		//Transfer previous stati in general
		if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.SET_INTERNAL_INFO)==true) {
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.SET_INTERNAL_INFO);
		}
		if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.SET_FOCUS_MOVEMENT)==true) {
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.SET_FOCUS_MOVEMENT);
		}
		if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.GOAL_NOT_REACHABLE)==true) {
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.GOAL_NOT_REACHABLE);
		}
		
		
		//Transfer previous stati in special
		if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.IS_DRIVE_SOURCE)==true) {
			
			
			
		} else if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.IS_MEMORY_SOURCE)==true) {
			
			//Remove all PI-matches from the images
			clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(this.moGoal);
			clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oSupportiveDataStructure);
			clsActTools.removePIMatchFromWPMAndSubImages(oIntention);
			
		} else if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.IS_PERCEPTIONAL_SOURCE)==true) {

			if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.COMPOSED_CODELET)==true) {
				clsGoalTools.setTaskStatus(this.moGoal, eCondition.COMPOSED_CODELET);
			}
			if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.GOTO_GOAL_IN_PERCEPTION)==true) {
				clsGoalTools.setTaskStatus(this.moGoal, eCondition.GOTO_GOAL_IN_PERCEPTION);
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
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_NEW_CONTINUED_GOAL));
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
	 * @since 01.10.2012 15:32:19
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		//Special case, do not remove the condition
		
	}

}
