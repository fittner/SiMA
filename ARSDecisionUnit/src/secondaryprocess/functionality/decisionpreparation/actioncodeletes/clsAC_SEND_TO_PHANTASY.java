/**
 * CHANGELOG
 *
 * 23.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.actioncodeletes;

import java.util.ArrayList;

import memorymgmt.enums.eAction;
import memorymgmt.enums.eCondition;
import memorymgmt.enums.eContentType;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import secondaryprocess.datamanipulation.clsPhantasyTools;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;
import base.datatypes.clsWordPresentationMesh;

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
		if (this.moGoal.getSupportiveDataStructure().isNullObject()==true) {
			//Create a supportive data structure
		    try {
                this.moGoal.createSupportiveDataStructureFromGoalObject(eContentType.PHI);
                log.trace("Supportive data structure created for phantasy: " + this.moGoal.getSupportiveDataStructure().toString());
            } catch (Exception e) {
                log.error("Error", e);
            }
		}
		
		//Check if drive or goal
		if (this.moGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)==true) {
			//Get the supportive data structure
			clsWordPresentationMesh oSupportiveDataStructure = this.moGoal.getSupportiveDataStructure();
			
			//Associate this structure with the action
			clsActionTools.setSupportiveDataStructureForAction(this.moAction, oSupportiveDataStructure);
			
		} else if (this.moGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)==true) {
			//Get the supportive data structure
			clsWordPresentationMesh oAct = this.moGoal.getSupportiveDataStructure();
			
			//Check if the intention already has a PP-Image
			clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oAct);
			//Check if the intention has content
			
			if (clsMeshTools.checkIfWPMImageHasSubImages(oIntention)) {
				//Associate this structure with the action
				clsActionTools.setSupportiveDataStructureForAction(this.moAction, oIntention);
			} else {
				//Search the trigger structure, i. e. the substructure with the highest match
				clsWordPresentationMesh oNearestMatch = clsActTools.getHighestPIMatchFromSubImages(oIntention);
				
				//Set the nearest match as the best try
				if (oNearestMatch.isNullObject()==false) {
					clsActionTools.setSupportiveDataStructureForAction(this.moAction, oNearestMatch);
				}
			}
		} else {
			//Get the supportive data structure
			clsWordPresentationMesh oSupportiveDataStructure = this.moGoal.getSupportiveDataStructure();
			
			//Associate this structure with the action
			clsActionTools.setSupportiveDataStructureForAction(this.moAction, oSupportiveDataStructure);
		}
		
		//Set phantasyflag
		try {
			//If it is an existing image, use only indirect activation ,otherwhise direct activation
		    ArrayList<clsWordPresentationMesh> supportiveDataStructures = clsActionTools.getSupportiveDataStructureForAction(moAction);
		    boolean isExistingImage = true;
		    for (clsWordPresentationMesh wpm : supportiveDataStructures) {
		        if (wpm.getDS_ID()==-1) {
		            isExistingImage=false;
		            break;
		        }
		    }
		    
		    if (isExistingImage==true) {
		        clsPhantasyTools.setPhantasyExecutePhantasySourceIndirectActivation(this.moAction);
		    } else {
		        clsPhantasyTools.setPhantasyExecutePhantasySourceDirectActivation(this.moAction);
		    }
		    
			
			//}
		} catch (Exception e) {
			log.error("", e);
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
	    try {
            //this.moGoal.removeCondition(eCondition.NEED_INTERNAL_INFO);
        } catch (Exception e) {
            log.error("", e);
        }
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:07:56
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Send an image to the phantasy by passing it to module F47.";
		
	}

}
