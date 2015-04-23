/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.consequencecodelets;

import java.util.ArrayList;

import memorymgmt.enums.eCondition;
import secondaryprocess.algorithm.acts.clsActPreparationTools;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;
import base.datatypes.clsWordPresentationMesh;
import base.tools.ElementNotFoundException;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 15:38:41
 * 
 */
public class clsCC_PERFORM_BASIC_ACT_ANALYSIS extends clsConsequenceCodelet {
    
    private final double P_ACTMATCHACTIVATIONTHRESHOLD = 1.0;

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.09.2012 15:39:02
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poReachableGoalList
	 * @param poCodeletHandler
	 */
	public clsCC_PERFORM_BASIC_ACT_ANALYSIS(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:39:04
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		//Get Previous goal
		
		//Perform basic act analysis as the act is complete
		clsWordPresentationMesh oCurrentAct = this.moGoal.getSupportiveDataStructure();
		
		//This function shall extract the current moment and the expectation
		ArrayList<eCondition> oTaskStatusList = clsActPreparationTools.performBasicActAnalysis(oCurrentAct, this.moShortTermMemory);
		
		//Check if act analysis failed and remove all status if this is the case
		if (oTaskStatusList.contains(eCondition.GOAL_NOT_REACHABLE)==true) {
		    this.moGoal.removeAllConditions();
		    this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
		    this.moGoal.setCondition(eCondition.IS_MEMORY_SOURCE);
		} else if (oTaskStatusList.contains(eCondition.RESET_GOAL)==true) {
		    //Delete Moment
		    clsActDataStructureTools.setMoment(oCurrentAct, clsWordPresentationMesh.getNullObject());
		    //Delete Expectation
		    clsActDataStructureTools.setExpectation(oCurrentAct, clsWordPresentationMesh.getNullObject());
		    //Remove all unnecessary previous conditions
		    try {
                //Remove act specifics
		        this.moGoal.removeCondition(eCondition.SET_FOLLOW_ACT);
                this.moGoal.removeCondition(eCondition.SET_BASIC_ACT_ANALYSIS);
                
                //Remove the condition that this is the plan goal
                //this.moGoal.removeCondition(eCondition.IS_CONTINUED_PLANGOAL);
            } catch (ElementNotFoundException e) {
                log.error("", e);
            }
		    
		    //Check start conditions. They do the same as the codelet in the init new goal
	        //Get the intention
	        clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oCurrentAct);
	        ArrayList<eCondition> conditionList = clsActPreparationTools.initActInGoal(oIntention);
	        
	        for (eCondition c: conditionList) {
	            this.moGoal.setCondition(c);
	        }
	        
	        this.moGoal.setCondition(eCondition.IS_NEW_GOAL);
            this.moGoal.setCondition(eCondition.IS_MEMORY_SOURCE);
            this.moGoal.setCondition(eCondition.SET_INTERNAL_INFO);
		    
//		    if (clsActTools.checkIfConditionExists(oIntention, eCondition.START_WITH_FIRST_IMAGE)==true) {
//	            //Cases:
//	            //1. If the first image has match 1.0 and there is no first act ||
//	            //2. If the this act is the same as from the previous goal -> start this act as normal
//	            //else set GOAL_CONDITION_BAD
//	            clsWordPresentationMesh oFirstImage = clsActTools.getFirstImageFromIntention(oIntention);
//	            rCurrentImageMatch = clsActTools.getPIMatch(oFirstImage);
//	            
//	        } else {
//	            //Get best match from an intention
//	            clsWordPresentationMesh oBestMatchEvent = clsActTools.getHighestPIMatchFromSubImages(oIntention);
//	            rCurrentImageMatch = clsActTools.getPIMatch(oBestMatchEvent);
//	        }
//	        
//	        if (rCurrentImageMatch < P_ACTMATCHACTIVATIONTHRESHOLD) {
//	            moGoal.setCondition(eCondition.ACT_MATCH_TOO_LOW);
//	        } else {
//	            this.moGoal.setCondition(eCondition.IS_NEW_GOAL);
//	            this.moGoal.setCondition(eCondition.IS_MEMORY_SOURCE);
//	            this.moGoal.setCondition(eCondition.SET_INTERNAL_INFO);
//	        }
		    

		} else {
			for (eCondition oTaskStatus : oTaskStatusList) {
			    this.moGoal.setCondition(oTaskStatus);
			}
		}
		
	}
	

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:39:04
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.EXECUTED_PERFORM_BASIC_ACT_ANALYSIS, eCondition.IS_CONTINUED_PLANGOAL));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:39:04
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:38:01
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
	    try {
            this.moGoal.removeCondition(eCondition.EXECUTED_PERFORM_BASIC_ACT_ANALYSIS);
            this.moGoal.removeCondition(eCondition.NEED_BASIC_ACT_ANALYSIS);
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:11:43
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Performs basic act analysis";
		
	}

}
