/**
 * CHANGELOG
 *
 * 23.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.initcodelets;

import java.util.ArrayList;
import java.util.Arrays;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.tools.ElementNotFoundException;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;
import secondaryprocess.datamanipulation.meshprocessor.MeshProcessor;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsCommonCodeletTools;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;
import testfunctions.clsTester;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.09.2012, 12:34:31
 * 
 */
public class clsIC_InitContinuedGoalAct extends clsInitCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 23.09.2012 12:34:52
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsIC_InitContinuedGoalAct(clsCodeletHandler poCodeletHandler) {
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
		clsWordPresentationMeshGoal oPreviousPlanGoal = clsCommonCodeletTools.getPreviousCorrespondingGoalFromShortTermMemory(moShortTermMemory, moGoal);
		
		
		//Transfer previous stati in general
		if (oPreviousPlanGoal.checkIfConditionExists(eCondition.SET_INTERNAL_INFO)==true) {
		    this.moGoal.setCondition(eCondition.SET_INTERNAL_INFO);
		}
		if (oPreviousPlanGoal.checkIfConditionExists(eCondition.SET_FOCUS_MOVEMENT)==true) {
		    this.moGoal.setCondition(eCondition.SET_FOCUS_MOVEMENT);
		}
		if (oPreviousPlanGoal.checkIfConditionExists(eCondition.SET_BASIC_ACT_ANALYSIS)==true) {
		    this.moGoal.setCondition(eCondition.SET_BASIC_ACT_ANALYSIS);
		}
		if (oPreviousPlanGoal.checkIfConditionExists(eCondition.SET_FOLLOW_ACT)==true) {
		    this.moGoal.setCondition(eCondition.SET_FOLLOW_ACT);
		}
		
		if (oPreviousPlanGoal.checkIfConditionExists(eCondition.GOAL_NOT_REACHABLE)==true) {
            this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
        } else {
          //Check if any of the goals in the STM has a "GOAL_COMPLETED". If it has and is the same goal as here, then this goal shall receive
            //goal not reachable
            ArrayList<clsWordPresentationMeshGoal> oSameGoalList = clsGoalManipulationTools.getAllSameGoalsFromSTM(this.moGoal, this.moShortTermMemory);
            for (clsWordPresentationMeshGoal oSameGoal : oSameGoalList) {
                if (oSameGoal.checkIfConditionExists(eCondition.GOAL_COMPLETED)==true) {
                    this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
                }
            }
            
            //Get the act from the continued goal
            clsWordPresentationMesh poNewAct = this.moGoal.getSupportiveDataStructure();
            
            //Get the act of the previous goal
            clsWordPresentationMesh oPreviousAct = oPreviousPlanGoal.getSupportiveDataStructure();
            
            //Set the Act of the previous goal as the new act of the continued goal
            
            //=== Perform system tests ===//
            clsTester.getTester().setActivated(true);
            if (clsTester.getTester().isActivated()) {
                try {
                    log.warn("System tester active");
                    for (clsWordPresentationMesh mesh : new ArrayList<clsWordPresentationMesh>(Arrays.asList(oPreviousAct))) {
                        clsTester.getTester().exeTestCheckLooseAssociations(mesh); 
                    }
                } catch (Exception e) {
                    log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
                }
            }
            
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
            MeshProcessor processor = new MeshProcessor();
            processor.setSafeControlMode(true);
            processor.complementMesh(oContinuedSupportiveDataStructure, poNewAct);
            
            
            //clsMeshTools.mergeMesh(oContinuedSupportiveDataStructure, poNewAct);

            //-----------------------------------------------//
            
            //FIXME: Find a better solution. In order to break a deadlock, check if the moment is the last image of the act
            //If yes, set goal completed, in order to lower the pleasure value of the goal.
            clsWordPresentationMesh oMoment = clsActDataStructureTools.getMoment(oContinuedSupportiveDataStructure);
            if (oMoment.isNullObject()==false && clsActTools.isLastImage(oMoment)==true) {
                this.moGoal.setCondition(eCondition.GOAL_COMPLETED);
            } else if (oMoment.isNullObject()==false && clsActTools.getMovementTimeoutValue(oMoment)<=0) {
                this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
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
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_CONTINUED_GOAL, eCondition.IS_MEMORY_SOURCE, eCondition.IS_UNPROCESSED_GOAL));
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
	protected void removeTriggerCondition() throws ElementNotFoundException {
	    this.moGoal.removeCondition(eCondition.IS_UNPROCESSED_GOAL);
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:19:46
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Default initial anaylsis of continued goal from the last turn if it is an act.";
		
	}

}
