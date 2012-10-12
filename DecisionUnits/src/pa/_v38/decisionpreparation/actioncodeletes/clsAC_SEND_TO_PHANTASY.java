/**
 * CHANGELOG
 *
 * 23.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.actioncodeletes;

import pa._v38.decisionpreparation.clsActionCodelet;
import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.tools.clsActDataStructureTools;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPhantasyTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.09.2012, 13:22:21
 * 
 */
public class clsAC_SEND_TO_PHANTASY extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 23.09.2012 13:23:20
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsAC_SEND_TO_PHANTASY(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 13:23:23
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		//Generate this action
		this.generateAction(eAction.SEND_TO_PHANTASY);
		

		
		//Set supportive datastructure from the goal
		if (clsGoalTools.getSupportiveDataStructure(this.moGoal).isNullObject()==true) {
			//Create a supportive data structure
			clsGoalTools.createSupportiveDataStructureFromGoalObject(this.moGoal, eContentType.PHI);
		}
		
		//Check if drive or goal
		if (clsGoalTools.checkIfConditionExists(moGoal, eCondition.IS_DRIVE_SOURCE)==true) {
			//Get the supportive data structure
			clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(this.moGoal);
			
			//Associate this structure with the action
			clsActionTools.setSupportiveDataStructure(this.moAction, oSupportiveDataStructure);
			
		} else if (clsGoalTools.checkIfConditionExists(moGoal, eCondition.IS_MEMORY_SOURCE)==true) {
			//Get the supportive data structure
			clsWordPresentationMesh oAct = clsGoalTools.getSupportiveDataStructure(this.moGoal);
			
			//Check if the intention already has a PP-Image
			clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oAct);
			//Check if the intention has content
			
			if (clsMeshTools.checkIfWPMImageHasSubImages(oIntention)) {
				//Associate this structure with the action
				clsActionTools.setSupportiveDataStructure(this.moAction, oIntention);
			} else {
				//Search the trigger structure, i. e. the substructure with the highest match
				clsWordPresentationMesh oNearestMatch = clsActTools.getHighestPIMatchFromSubImages(oIntention);
				
				//Set the nearest match as the best try
				if (oNearestMatch.isNullObject()==false) {
					clsActionTools.setSupportiveDataStructure(this.moAction, oNearestMatch);
				}
			}
		} else {
			//Get the supportive data structure
			clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(this.moGoal);
			
			//Associate this structure with the action
			clsActionTools.setSupportiveDataStructure(this.moAction, oSupportiveDataStructure);
		}
		
		//Set phantasyflag
		try {
			clsWordPresentationMesh oSupportDS = clsActionTools.getSupportiveDataStructure(this.moAction);
			if (oSupportDS.isNullObject()==false) {
				clsPhantasyTools.setPhantasyFlagTrue(oSupportDS);
			}
		} catch (Exception e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		//Associate the action with the goal
		setActionAssociationInGoal();
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 13:23:23
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		//NEED_INTERNAL_INFO
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.NEED_INTERNAL_INFO));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 13:23:23
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:22:30
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		//Update goal status - remove the conditions to execute this codelet
		clsGoalTools.removeCondition(this.moGoal, eCondition.NEED_INTERNAL_INFO);
		
	}

}
