/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.consequencecodelets;

import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsConsequenceCodelet;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.tools.clsActDataStructureTools;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 15:25:15
 * 
 */
public class clsCC_EXECUTE_MOVEMENT extends clsConsequenceCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.09.2012 15:25:36
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poReachableGoalList
	 * @param poCodeletHandler
	 */
	public clsCC_EXECUTE_MOVEMENT(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:25:39
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		//Clear the environmental image
		this.moEnvironmentalImageMemory.clearEnvironmentalImage();
		
		//Remove conditions for the movement
		clsGoalTools.removeCondition(this.moGoal, eCondition.SET_FOCUS_MOVEMENT);
		clsGoalTools.removeCondition(this.moGoal, eCondition.SET_FOCUS_ON);
		
		if (clsGoalTools.checkIfConditionExists(this.moGoal, eCondition.IS_MEMORY_SOURCE)==true) {
			clsGoalTools.removeCondition(this.moGoal, eCondition.SET_BASIC_ACT_ANALYSIS);
			clsGoalTools.removeCondition(this.moGoal, eCondition.SET_FOLLOW_ACT);
			
			clsWordPresentationMesh oAct = clsGoalTools.getSupportiveDataStructure(this.moGoal);
			clsWordPresentationMesh oMoment = clsActDataStructureTools.getMoment(oAct);
			
			if (oMoment.isNullObject()==false) {
				int nTimeOutValue = clsActTools.getMovementTimeoutValue(oMoment);
				
				if (nTimeOutValue>0) {
					nTimeOutValue--;
					clsActTools.setMovementTimeoutValue(oMoment, nTimeOutValue);
				}
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:25:39
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_MOVE_FORWARD));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_TURN_LEFT));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_TURN_RIGHT));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_SEARCH1));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_STRAFE_LEFT));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_STRAFE_RIGHT));
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:25:39
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:23:29
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		//Remove the focus movement as a movement has happened
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_MOVE_FORWARD);
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_TURN_LEFT);
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_TURN_RIGHT);
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_SEARCH1);
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_STRAFE_LEFT);
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_STRAFE_RIGHT);
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:08:30
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Executes the consequence of the action EXECUTE_MOVEMENT.";
		
	}

}
