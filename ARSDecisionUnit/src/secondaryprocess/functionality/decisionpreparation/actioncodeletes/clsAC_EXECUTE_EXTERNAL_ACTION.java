/**
 * CHANGELOG
 *
 * 26.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.actioncodeletes;

import java.util.ArrayList;

import memorymgmt.enums.eAction;
import memorymgmt.enums.eCondition;
import base.datatypes.clsWordPresentationMesh;
import secondaryprocess.algorithm.planning.clsActionRefiner;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

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

	/**
     * DOCUMENT - checks if any of the actions can be further refined.
     *            For example: action GOTO can be refined to TURN_LEFT, TURN_RIGHT or MOVE_FORWARD 
     *
     * @author Kollmann
     * @since 10.02.2014 20:47:50
     *
     * @param poMovementActions: ArrayList of action WPMs (which should be part a sub mesh of greater WPM)
     */
    protected void refineMovementActions(ArrayList<clsWordPresentationMesh> poMovementActions) {
        clsWordPresentationMesh oOldItem = null, oNewItem = null;
        clsActionRefiner oRefiner = new clsActionRefiner(this.moShortTermMemory, this.moEnvironmentalImage);
        
        for(int i = 0; i < poMovementActions.size(); ++i) {
            oOldItem = poMovementActions.get(i);
            
            oRefiner.refineAction(oOldItem);
        }
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
		
		if (this.moGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)==true || this.moGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)==true) {
			oExternalPlans.addAll(this.moExternalActionPlanner.generatePlans_AW(this.moEnvironmentalImage, this.moGoal));
		} else if (this.moGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)==true) {
			oExternalPlans.addAll(this.moExternalActionPlanner.extractRecommendedActionsFromAct(this.moGoal));
		}
		
		refineMovementActions(oExternalPlans);
      
		eAction oChosenAction = eAction.NONE;
		
		if (oExternalPlans.isEmpty()==false) {
		    this.generateAction(oExternalPlans.get(0));
		} else {
		    this.generateAction(eAction.NONE);
		}
		
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
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.NEED_PERFORM_RECOMMENDED_ACTION, eCondition.SET_FOCUS_ON, eCondition.SET_FOCUS_MOVEMENT));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:52:28
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		//Drives
				this.moPostConditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.SET_FOCUS_MOVEMENT, eCondition.SET_INTERNAL_INFO));
				//Perception
				this.moPostConditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.SET_FOCUS_ON, eCondition.SET_FOCUS_MOVEMENT));
				//Memory
				this.moPostConditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.SET_FOCUS_ON, eCondition.SET_FOCUS_MOVEMENT));
		
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
	    try {
//            this.moGoal.removeCondition(eCondition.NEED_PERFORM_RECOMMENDED_ACTION);
//            this.moGoal.removeCondition(eCondition.NEED_MOVEMENT);
//            this.moGoal.removeCondition(eCondition.NEED_SEARCH_INFO);
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
	    
		
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
