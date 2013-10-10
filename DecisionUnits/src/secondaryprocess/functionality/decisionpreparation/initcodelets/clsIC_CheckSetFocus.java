/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.initcodelets;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eCondition;
import secondaryprocess.datamanipulation.clsMeshTools;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsCommonCodeletTools;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:20:52
 * 
 */
public class clsIC_CheckSetFocus extends clsInitCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 22.09.2012 17:38:43
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsIC_CheckSetFocus(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
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
		
		clsWordPresentationMeshGoal oPreviousGoal = clsCommonCodeletTools.getPreviousPlanGoalFromShortTermMemory(moShortTermMemory);
		
		if (oPreviousGoal.checkIfConditionExists(eCondition.SET_FOCUS_ON)) {
			//If focus was set the last time, check if focus is still there in the STM
			//Find the supportive structure in the STM
			
			ArrayList<clsWordPresentationMesh> oEntityList = clsMeshTools.getAllSubWPMInWPMImage(moEnvironmentalImage);
			boolean bEntityInFocus = false;
			for (clsWordPresentationMesh oExistingEntity : oEntityList) {
				if (oExistingEntity.getMoDS_ID()==oPreviousGoal.getGoalObject().getMoDS_ID()) {
					//If the goal object was found in the image, no matter of where it is, then the focus is still set.
					//In this case, in F23 ALL goal objects are set in the image, therefore it does not matter which instance of the entity is found
				    this.moGoal.setCondition(eCondition.SET_FOCUS_ON);
					bEntityInFocus = true;
					break;
				}
			}
			
			if (bEntityInFocus==false) {
			    this.moGoal.setCondition(eCondition.NEED_GOAL_FOCUS);
			}
		}
		
		

		
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
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.IS_CONTINUED_GOAL));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 11:41:20
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		this.moPostConditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.IS_CONTINUED_GOAL, eCondition.SET_FOCUS_ON));
		this.moPostConditionGroupList.add(new clsConditionGroup(eCondition.IS_PERCEPTIONAL_SOURCE, eCondition.IS_CONTINUED_GOAL, eCondition.NEED_GOAL_FOCUS));
	}


	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:33:43
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		//Do not remove as this is continuous analysis
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:19:12
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Check if focus on is still set on an entity in the environment.";
		
	}

}
