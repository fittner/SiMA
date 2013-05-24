/**
 * CHANGELOG
 *
 * 23.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.initcodelets;

import java.util.ArrayList;

import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsCommonCodeletTools;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsInitCodelet;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.tools.clsActDataStructureTools;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.09.2012, 12:34:31
 * 
 */
public class clsIC_InitContinuedGoal extends clsInitCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 23.09.2012 12:34:52
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsIC_InitContinuedGoal(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 12:34:55
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		clsWordPresentationMeshGoal oPreviousGoal = clsCommonCodeletTools.getPreviousGoalFromShortTermMemory(moShortTermMemory);
		
		
		//Transfer previous stati in general
		if (oPreviousGoal.checkIfConditionExists(eCondition.SET_INTERNAL_INFO)==true) {
		    this.moGoal.setCondition(eCondition.SET_INTERNAL_INFO);
		}
		if (oPreviousGoal.checkIfConditionExists(eCondition.SET_FOCUS_MOVEMENT)==true) {
		    this.moGoal.setCondition(eCondition.SET_FOCUS_MOVEMENT);
		}
		if (oPreviousGoal.checkIfConditionExists(eCondition.GOAL_NOT_REACHABLE)==true) {
		    this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
		}
		if (oPreviousGoal.checkIfConditionExists(eCondition.SET_BASIC_ACT_ANALYSIS)==true) {
		    this.moGoal.setCondition(eCondition.SET_BASIC_ACT_ANALYSIS);
		}
		if (oPreviousGoal.checkIfConditionExists(eCondition.SET_FOLLOW_ACT)==true) {
		    this.moGoal.setCondition(eCondition.SET_FOLLOW_ACT);
		}
		
		
		if (this.moGoal.checkIfConditionExists(eCondition.GOAL_NOT_REACHABLE)==false) {
			//Transfer previous stati in special
			if (this.moGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)==true) {
//			    if (moGoal.checkIfConditionExists(eCondition.SET_INTERNAL_INFO)==false) {
//			        moGoal.setCondition(eCondition.NEED_INTERNAL_INFO);
//			    }
			    
				
				
			} else if (this.moGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)==true) {
				
				//Check if any of the goals in the STM has a "GOAL_COMPLETED". If it has and is the same goal as here, then this goal shall receive
				//goal not reachable
				ArrayList<clsWordPresentationMeshGoal> oSameGoalList = clsGoalTools.getAllSameGoalsFromSTM(this.moGoal, this.moShortTermMemory);
				for (clsWordPresentationMeshGoal oSameGoal : oSameGoalList) {
					if (oSameGoal.checkIfConditionExists(eCondition.GOAL_COMPLETED)==true) {
					    this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
					}
				}
				
				//Get the act from the continued goal
				clsWordPresentationMesh poNewAct = this.moGoal.getSupportiveDataStructure();
				
				//Get the act of the previous goal
				clsWordPresentationMesh oPreviousAct = oPreviousGoal.getSupportiveDataStructure();
				
				//Set the Act of the previous goal as the new act of the continued goal
				if (oPreviousAct.isNullObject()==false) {
					try {
						clsWordPresentationMesh oClonedPreviousAct = (clsWordPresentationMesh) oPreviousAct.clone();
						//Set the cloned act as this act
						this.moGoal.setSupportiveDataStructure(oClonedPreviousAct);
						
					} catch (CloneNotSupportedException e) {
						// TODO (wendt) - Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//Remove all PI-matches from the images in this goal
				clsWordPresentationMesh oContinuedSupportiveDataStructure = this.moGoal.getSupportiveDataStructure();
				clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oContinuedSupportiveDataStructure);
				clsActTools.removePIMatchFromWPMAndSubImages(oIntention);
				
				//Merge the acts
				clsMeshTools.mergeMesh(oContinuedSupportiveDataStructure, poNewAct);

				//-----------------------------------------------//
				
				//FIXME: Find a better solution. In order to break a deadlock, check if the moment is the last image of the act
				//If yes, set goal completed, in order to lower the pleasure value of the goal.
				clsWordPresentationMesh oMoment = clsActDataStructureTools.getMoment(oContinuedSupportiveDataStructure);
				if (oMoment.isNullObject()==false && clsActTools.isLastImage(oMoment)==true) {
				    this.moGoal.setCondition(eCondition.GOAL_COMPLETED);
				} else if (oMoment.isNullObject()==false && clsActTools.getMovementTimeoutValue(oMoment)<=0) {
				    this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
				}
				
				

			} else if (this.moGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)==true) {

				if (oPreviousGoal.checkIfConditionExists(eCondition.COMPOSED_CODELET)==true) {
				    this.moGoal.setCondition(eCondition.COMPOSED_CODELET);
				}
				if (oPreviousGoal.checkIfConditionExists(eCondition.GOTO_GOAL_IN_PERCEPTION)==true) {
				    this.moGoal.setCondition(eCondition.GOTO_GOAL_IN_PERCEPTION);
				}
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 12:34:55
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_CONTINUED_GOAL));
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 12:34:55
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:32:19
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		//Special case, do not remove the condition
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:19:46
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Default initial anaylsis of all new goals.";
		
	}

}
