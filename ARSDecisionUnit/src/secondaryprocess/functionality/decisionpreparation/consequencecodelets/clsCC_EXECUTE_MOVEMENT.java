/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.consequencecodelets;

import memorymgmt.enums.eCondition;
import base.datatypes.clsWordPresentationMesh;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

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
		try {
            this.moEnvironmentalImageMemory.clearEnvironmentalImage();
        } catch (Exception e1) {
            log.error("Cannot clear and update the environmental image", e1);
        }
		
		//Remove conditions for the movement
		try {
            //this.moGoal.removeCondition(eCondition.SET_FOCUS_MOVEMENT);
            //this.moGoal.removeCondition(eCondition.SET_FOCUS_ON);
            
            if (this.moGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)==true) {
                //this.moGoal.removeCondition(eCondition.SET_BASIC_ACT_ANALYSIS);
                //this.moGoal.removeCondition(eCondition.SET_FOLLOW_ACT);
                
                clsWordPresentationMesh oAct = this.moGoal.getSupportiveDataStructure();
                clsWordPresentationMesh oMoment = clsActDataStructureTools.getMoment(oAct);
                
                if (oMoment.isNullObject()==false) {
                    int nTimeOutValue = clsActTools.getMovementTimeoutValue(oMoment);
                    
                    if (nTimeOutValue>0) {
                        nTimeOutValue--;
                        clsActTools.setMovementTimeoutValue(oMoment, nTimeOutValue);
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
		
		moGoal.setCondition(eCondition.SET_DECISION_PHASE_COMPLETE);
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:25:39
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_MOVE_FORWARD, eCondition.IS_CONTINUED_PLANGOAL));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_TURN_LEFT, eCondition.IS_CONTINUED_PLANGOAL));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_TURN_RIGHT, eCondition.IS_CONTINUED_PLANGOAL));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_SEARCH1, eCondition.IS_CONTINUED_PLANGOAL));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_STRAFE_LEFT, eCondition.IS_CONTINUED_PLANGOAL));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_STRAFE_RIGHT, eCondition.IS_CONTINUED_PLANGOAL));
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.GOTO_GOAL_IN_PERCEPTION, eCondition.IS_CONTINUED_PLANGOAL));
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
	    try {
            this.moGoal.removeCondition(eCondition.EXECUTED_MOVE_FORWARD);
            this.moGoal.removeCondition(eCondition.EXECUTED_TURN_LEFT);
            this.moGoal.removeCondition(eCondition.EXECUTED_TURN_RIGHT);
            this.moGoal.removeCondition(eCondition.EXECUTED_SEARCH1);
            this.moGoal.removeCondition(eCondition.EXECUTED_STRAFE_LEFT);
            this.moGoal.removeCondition(eCondition.EXECUTED_STRAFE_RIGHT);
            
            //Remove init conditions for external action
            this.moGoal.removeCondition(eCondition.NEED_PERFORM_RECOMMENDED_ACTION);
            this.moGoal.removeCondition(eCondition.NEED_MOVEMENT);
            this.moGoal.removeCondition(eCondition.NEED_SEARCH_INFO);
            
            //Remove focus
            this.moGoal.removeCondition(eCondition.SET_FOCUS_ON);
            this.moGoal.removeCondition(eCondition.SET_FOCUS_MOVEMENT);
            
            //If memory source, remove conditions
            this.moGoal.removeCondition(eCondition.SET_BASIC_ACT_ANALYSIS);
            this.moGoal.removeCondition(eCondition.SET_FOLLOW_ACT);
            
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
	    
		
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
