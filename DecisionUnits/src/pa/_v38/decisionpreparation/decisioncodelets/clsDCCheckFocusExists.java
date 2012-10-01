/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
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
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:20:52
 * 
 */
public class clsDCCheckFocusExists extends clsDecisionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 22.09.2012 17:38:43
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsDCCheckFocusExists(clsWordPresentationMesh poEnvironmentalImage, clsShortTermMemory poShortTermMemory, ArrayList<clsWordPresentationMesh> poReachableGialList, clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poReachableGialList, poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	
	}

	/* (non-Javadoc)
	 *
	 * @since 22.09.2012 17:38:53
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		
		clsWordPresentationMesh oPreviousGoal = clsCommonCodeletTools.getPreviousGoalFromShortTermMemory(moShortTermMemory);
		
		if (clsGoalTools.checkIfTaskStatusExists(oPreviousGoal, eCondition.FOCUS_ON_SET)) {
			//If focus was set the last time, check if focus is still there in the STM
			//Find the supportive structure in the STM
			
			ArrayList<clsWordPresentationMesh> oEntityList = clsMeshTools.getAllSubWPMInWPMImage(moEnvironmentalImage);
			boolean bEntityInFocus = false;
			for (clsWordPresentationMesh oExistingEntity : oEntityList) {
				if (oExistingEntity.getMoDS_ID()==clsGoalTools.getGoalObject(oPreviousGoal).getMoDS_ID()) {
					//If the goal object was found in the image, no matter of where it is, then the focus is still set.
					//In this case, in F23 ALL goal objects are set in the image, therefore it does not matter which instance of the entity is found
					clsGoalTools.setTaskStatus(this.moGoal, eCondition.FOCUS_ON_SET);
					bEntityInFocus = true;
					break;
				}
			}
			
			if (bEntityInFocus==false) {
				clsGoalTools.setTaskStatus(this.moGoal, eCondition.NEED_GOAL_FOCUS);
			}
		}
		
		clsGoalTools.removeTaskStatus(this.moGoal, eCondition.IS_NEW_CONTINUED_GOAL);

		
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 11:41:20
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		//1. Perception
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.IS_NEW_CONTINUED_GOAL));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 11:41:20
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		this.moPostConditionGroupList.add(new clsConditionGroup(eCondition.FOCUS_ON_SET));
		this.moPostConditionGroupList.add(new clsConditionGroup(eCondition.NEED_GOAL_FOCUS));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 12:06:17
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setName()
	 */
	@Override
	protected void setName() {
		this.moCodeletName = "ACCheckFocusExists";
	}

}
