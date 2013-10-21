/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.consequencecodelets;

import pa._v38.memorymgmt.enums.eCondition;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

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

	    this.moGoal.setCondition(eCondition.SET_INTERNAL_INFO);	//Do not send this goal to phantasy twice
		
		//Perform dec
		if (this.moGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)) {
			
			//clsGoalTools.setCondition(this.moGoal, eCondition.GOAL_NOT_REACHABLE);	//Set first focus, Trigger search no transfer between images
			
			//Set the focus structure for phantasy
			//clsActionTools.setSupportiveDataStructure(this.moGoal, this.moGoal.getSupportiveDataStructure());
		
			
		} else if (this.moGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
			//Replace the supportive data structure with the one from the act
			//Find a goal in the list, which has the same act inside of it
//			ArrayList<clsWordPresentationMesh> oGoalWithSameAct = clsGoalTools.getOtherGoalsWithSameSupportiveDataStructure(this.moReachableGoalList, this.moGoal, true);
//			if (oGoalWithSameAct.isEmpty()==false) {
//				//Get the act
//				clsWordPresentationMesh oAct = clsGoalTools.getSupportiveDataStructure(oGoalWithSameAct.get(0));
//				//Set this act
//				clsGoalTools.setSupportiveDataStructure(this.moGoal, oAct);
//			}
			
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
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.EXECUTED_SEND_TO_PHANTASY, eCondition.IS_CONTINUED_PLANGOAL));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.EXECUTED_SEND_TO_PHANTASY, eCondition.IS_CONTINUED_PLANGOAL));
		
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
	    try {
            this.moGoal.removeCondition(eCondition.EXECUTED_SEND_TO_PHANTASY);
            
            this.moGoal.removeCondition(eCondition.NEED_INTERNAL_INFO);
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:12:17
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Executes the consequence of the action SEND_TO_PHANTASY.";
		
	}

}
