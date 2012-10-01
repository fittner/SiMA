/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.decisioncodelets;

import java.util.ArrayList;

import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsDecisionCodelet;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 14:51:02
 * 
 */
public class clsDCActionSendToPhantasy extends clsDecisionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.09.2012 14:51:16
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsDCActionSendToPhantasy(clsWordPresentationMesh poEnvironmentalImage, clsShortTermMemory poShortTermMemory, ArrayList<clsWordPresentationMesh> poReachableGialList, clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poReachableGialList, poCodeletHandler);
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 14:51:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {

		clsGoalTools.setTaskStatus(this.moGoal, eCondition.NEED_INTERNAL_INFO_SET);	//Do not send this goal to phantasy twice
		
		//Perform dec
		if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.IS_DRIVE_SOURCE)) {
			
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.GOAL_NOT_REACHABLE);	//Set first focus, Trigger search no transfer between images
			
			//Set the focus structure for phantasy
			clsActionTools.setSupportiveDataStructure(this.moGoal, clsGoalTools.getSupportiveDataStructure(this.moGoal));
		
		} else if (clsGoalTools.checkIfTaskStatusExists(this.moGoal, eCondition.IS_MEMORY_SOURCE)) {
			//Replace the supportive data structure with the one from the act
			//Find a goal in the list, which has the same act inside of it
			ArrayList<clsWordPresentationMesh> oGoalWithSameAct = clsGoalTools.getOtherGoalsWithSameSupportiveDataStructure(this.moReachableGoalList, this.moGoal);
			if (oGoalWithSameAct.isEmpty()==false) {
				//Get the act
				clsWordPresentationMesh oAct = clsGoalTools.getSupportiveDataStructure(oGoalWithSameAct.get(0));
				//Set this act
				clsGoalTools.setSupportiveDataStructure(this.moGoal, oAct);
			}
			
			clsGoalTools.setTaskStatus(this.moGoal, eCondition.NEED_BASIC_ACT_ANALYSIS);	//Trigger search
		}

		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 14:51:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.EXECUTED_SEND_TO_PHANTASY));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.EXECUTED_SEND_TO_PHANTASY));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 14:51:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 14:51:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setName()
	 */
	@Override
	protected void setName() {
		this.moCodeletName = this.getClass().getName();
		
	}

}
