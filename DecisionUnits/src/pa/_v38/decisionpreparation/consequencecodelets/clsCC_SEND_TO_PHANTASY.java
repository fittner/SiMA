/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.consequencecodelets;

import java.util.ArrayList;

import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsConsequenceCodelet;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 14:51:02
 * 
 */
public class clsCC_SEND_TO_PHANTASY extends clsConsequenceCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.09.2012 14:51:16
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsCC_SEND_TO_PHANTASY(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 14:51:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {

		clsGoalTools.setCondition(this.moGoal, eCondition.SET_INTERNAL_INFO);	//Do not send this goal to phantasy twice
		
		//Perform dec
		if (clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_DRIVE_SOURCE)) {
			
			clsGoalTools.setCondition(this.moGoal, eCondition.GOAL_NOT_REACHABLE);	//Set first focus, Trigger search no transfer between images
			
			//Set the focus structure for phantasy
			clsActionTools.setSupportiveDataStructure(this.moGoal, clsGoalTools.getSupportiveDataStructure(this.moGoal));
		
			
		} else if (clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_MEMORY_SOURCE)) {
			//Replace the supportive data structure with the one from the act
			//Find a goal in the list, which has the same act inside of it
			ArrayList<clsWordPresentationMesh> oGoalWithSameAct = clsGoalTools.getOtherGoalsWithSameSupportiveDataStructure(this.moReachableGoalList, this.moGoal);
			if (oGoalWithSameAct.isEmpty()==false) {
				//Get the act
				clsWordPresentationMesh oAct = clsGoalTools.getSupportiveDataStructure(oGoalWithSameAct.get(0));
				//Set this act
				clsGoalTools.setSupportiveDataStructure(this.moGoal, oAct);
			}
			
			//clsGoalTools.setTaskStatus(this.moGoal, eCondition.NEED_BASIC_ACT_ANALYSIS);x	//Trigger search
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
	 * @since 01.10.2012 15:36:29
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_SEND_TO_PHANTASY);
		
	}

}
