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
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.09.2012, 11:22:34
 * 
 */
public class clsAC_FOCUS_MOVEMENT extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 26.09.2012 11:22:50
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsAC_FOCUS_MOVEMENT(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:22:53
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
		
		eAction oChosenAction = eAction.NONE;
		
		if (oExternalPlans.isEmpty()==false) {
			oChosenAction = eAction.valueOf(oExternalPlans.get(0).getContent());
		}
		
		//Now the movement is gotten. Compose a new action for focusing, rename the action
		if (oChosenAction.equals(eAction.MOVE_FORWARD)) {
			oChosenAction = eAction.FOCUS_MOVE_FORWARD;
		} else if (oChosenAction.equals(eAction.TURN_LEFT)) {
			oChosenAction = eAction.FOCUS_MOVE_FORWARD;
		} else if (oChosenAction.equals(eAction.TURN_RIGHT)) {
			oChosenAction = eAction.FOCUS_MOVE_FORWARD;
		} else if (oChosenAction.equals(eAction.SEARCH1)) {
			oChosenAction = eAction.FOCUS_MOVE_FORWARD;
		} else if (oChosenAction.equals(eAction.GOTO)) {
		    oChosenAction = eAction.FOCUS_MOVE_FORWARD;
		} else if (oChosenAction.equals(eAction.FLEE)) {
            oChosenAction = eAction.FOCUS_MOVE_FORWARD;
        } else {
//		    //FIXME This concept has to be remade as here a condition is corrected, which it should not be
//		    try {
//                this.moGoal.removeCondition(eCondition.NEED_FOCUS_MOVEMENT);
//                this.moGoal.removeCondition(eCondition.SET_FOCUS_MOVEMENT);
//            } catch (ElementNotFoundException e) {
//                log.error("", e);
//            }
		}

		this.generateAction(oChosenAction);
		
		//Associate the action with the goal
		setActionAssociationInGoal();
		
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:22:53
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		//Drive
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.NEED_SEARCH_INFO, eCondition.SET_INTERNAL_INFO));
		//Perception
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.NEED_FOCUS_MOVEMENT, eCondition.SET_FOCUS_ON));
		//Memory
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.NEED_PERFORM_RECOMMENDED_ACTION, eCondition.SET_FOCUS_ON));
		
		
		
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:22:53
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:16:46
	 * 
	 * @see pa._v38.decisionpreparation.clsActionCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
	    try {
//            this.moGoal.removeCondition(eCondition.NEED_SEARCH_INFO);
//            this.moGoal.removeCondition(eCondition.NEED_FOCUS_MOVEMENT);
        } catch (Exception e) {
            log.error("", e);
        }
	    
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:05:33
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "If an external movement shall be executed, then this codelet first sets the focus of attention on the are" +
				"defined by the movement.";
		
	}

}
