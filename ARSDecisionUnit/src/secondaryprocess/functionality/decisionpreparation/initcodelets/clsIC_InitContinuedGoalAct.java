/**
 * CHANGELOG
 *
 * 23.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.initcodelets;

import java.util.ArrayList;
import java.util.Arrays;

import memorymgmt.enums.eCondition;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshGoal;
import base.tools.ElementNotFoundException;
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
    
    private static final double P_ACTMATCHACTIVATIONTHRESHOLD = 1.0;

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
		
		if (oPreviousPlanGoal.checkIfConditionExists(eCondition.GOAL_NOT_REACHABLE)==true) {
            //this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
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
            clsTester.getTester().setActivated(false);
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
                    //=== Perform system tests ===//
                    clsTester.getTester().setActivated(false);
                    if (clsTester.getTester().isActivated()) {
                        try {
                            log.warn("System tester active");
                            clsTester.getTester().exeTestCheckLooseAssociations(oPreviousAct); 
                            clsTester.getTester().exeTestAssociationAssignment(oPreviousAct);
                            
                        } catch (Exception e) {
                            log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
                        }
                    }
                    
                    log.trace("Previous act: {}", oPreviousAct);
                    clsWordPresentationMesh oClonedPreviousAct = (clsWordPresentationMesh) oPreviousAct.clone();
                    //Set the cloned act as this act
                    this.moGoal.setSupportiveDataStructure(oClonedPreviousAct);
                    
                } catch (CloneNotSupportedException e) {
                    log.error("", e);
                }
            }
            
            clsTester.getTester().setActivated(false);
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
            
            //Remove all PI-matches from the images in this goal
            clsWordPresentationMesh oContinuedSupportiveDataStructure = this.moGoal.getSupportiveDataStructure();
            clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oContinuedSupportiveDataStructure);
            clsActTools.removePIMatchFromWPMAndSubImages(oIntention);
            
            //Merge the acts
            MeshProcessor processor = new MeshProcessor();
            processor.setSafeControlMode(false);
            processor.complementMesh(oContinuedSupportiveDataStructure, poNewAct, true);

            //-----------------------------------------------//
            
            //FIXME: Find a better solution. In order to break a deadlock, check if the moment is the last image of the act
            //If yes, set goal completed, in order to lower the pleasure value of the goal.
            clsWordPresentationMesh oMoment = clsActDataStructureTools.getMoment(oContinuedSupportiveDataStructure);
            ArrayList<eCondition> conditionList = new ArrayList<eCondition>();
            if (oMoment.isNullObject()==false && clsActTools.isLastImage(oMoment)==true) {
                conditionList.add(eCondition.GOAL_COMPLETED);
                //F29_EvaluationOfImaginaryActions.moArrayFeelingsInMoments.add("\nGOAL_COMPLETED2");
                //this.moGoal.setCondition(eCondition.GOAL_COMPLETED);
            } else if (oMoment.isNullObject()==false && clsActTools.getMovementTimeoutValue(oMoment)<=0) {
                conditionList.add(eCondition.GOAL_NOT_REACHABLE);
                //F29_EvaluationOfImaginaryActions.moArrayFeelingsInMoments.add("\nGOAL_NOT_REACHABLE2");
                //this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
            } 
            
            //conditionList.addAll(clsActPreparationTools.checkProcessFirstImageStartCondition(oIntention, oMoment));
            
            this.moGoal.setCondition(conditionList);
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
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_CONTINUED_GOAL, eCondition.IS_MEMORY_SOURCE));
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
