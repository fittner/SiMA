/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.tools.clsActDataStructureTools;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsEntityTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsImportanceTools;
import pa._v38.tools.clsMentalSituationTools;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 09:58:07
 * 
 */
public class clsDecisionPreparationTools {

	private static Logger log = Logger.getLogger("pa._v38.decisionpreparation");
	
	//private static final double P_ACTMATCHACTIVATIONTHRESHOLD = 1.0; 
	
	/**
	 * This method prepoesses goal in F51. There are 2 types of goals: One goal, which is continued and many goals, which are processed for the first time. These goals get default preconditions 
	 * and the continued goal is mapped to one of the new goals
	 * 
	 * (wendt)
	 *
	 * @since 01.10.2012 14:50:37
	 *
	 * @param poSTM
	 * @param poGoalList
	 * @return
	 */
	public static clsWordPresentationMeshGoal getContinuedGoal(clsShortTermMemory poSTM, ArrayList<clsWordPresentationMeshGoal> poGoalList) {
		
		//--- GET PREVIOUS MENTAL SITUATION ---//
		clsWordPresentationMesh oPreviousMentalSituation = poSTM.findPreviousSingleMemory();
		//Get the previous goal
		clsWordPresentationMeshGoal oPreviousGoal = clsMentalSituationTools.getGoal(oPreviousMentalSituation);
		log.debug("Previous goal: " + oPreviousGoal);
		
		
		// --- GET AND INIT THE CONTINUED GOAL --- //
		//Set condition for continuous preprocessing
		clsWordPresentationMeshGoal oResult = clsDecisionPreparationTools.getContinuedGoal(oPreviousGoal, poGoalList);
		log.trace("Continued goal: " + oResult);
		
		return oResult;
	}
	
//	   /**
//     * Execute matching codelets for the continuous goal. The condition IS_NEW_CONTINUED_GOAL is set prior and after the execution of
//     * those codelets
//     * 
//     * (wendt)
//     *
//     * @since 01.10.2012 15:43:40
//     *
//     * @param poContinuedGoal
//     */
//    private void proveContinousConditions(clsWordPresentationMeshGoal poContinuedGoal) {
//        
//        //Execute all codelets, which are using IS_NEW_CONTINUED_GOAL
//        this.moCodeletHandler.executeMatchingCodelets(this, poContinuedGoal, eCodeletType.INIT, -1);
//            
//        //Remove conditions for continuous preprocessing
//        //clsGoalTools.removeTaskStatus(poContinuedGoal, eCondition.IS_NEW_CONTINUED_GOAL);
//        log.debug("Prove previous, goal:" + poContinuedGoal.toString());
//    }
	
	/**
	 * Map the previous goal with a new goal from the goal list. The new goal is used, but enhanced with info from the previous step. 
	 * 
	 * (wendt)
	 *
	 * @since 27.09.2012 10:22:34
	 *
	 * @param poPreviousGoal
	 * @param poGoalList
	 * @return
	 */
	private static clsWordPresentationMeshGoal getContinuedGoal(clsWordPresentationMeshGoal poPreviousGoal, ArrayList<clsWordPresentationMeshGoal> poGoalList) {
		clsWordPresentationMeshGoal oResult = clsGoalTools.getNullObjectWPM();
		
		//Check if goal exists in the goal list
		ArrayList<clsWordPresentationMeshGoal> oEquivalentGoalList = clsGoalTools.getEquivalentGoalFromGoalList(poGoalList, poPreviousGoal);
		
		//If the goal could not be found
		if (oEquivalentGoalList.isEmpty()==true) {
			
			//--- Remove the temporal data from the last turn ---//
			if (poPreviousGoal.getGoalType().equals(eGoalType.MEMORYDRIVE)==true) { 
				//--- COPY PREVIOUS GOAL ---//
				clsWordPresentationMeshGoal oNewGoalFromPrevious = clsGoalTools.copyGoalWithoutTaskStatusAndAction(poPreviousGoal);
				
				//Add to goallist
				if (poGoalList.contains(oNewGoalFromPrevious)==false) {
					poGoalList.add(oNewGoalFromPrevious);
				}
				oResult = oNewGoalFromPrevious;	
				
				oResult.setCondition(eCondition.IS_MEMORY_SOURCE);	//FIXME: This operation should not be necessary here
				
			} else if (poPreviousGoal.getGoalType().equals(eGoalType.DRIVESOURCE)==true) {
				//--- COPY PREVIOUS GOAL ---//
				clsWordPresentationMeshGoal oNewGoalFromPrevious = clsGoalTools.copyGoalWithoutTaskStatusAndAction(poPreviousGoal);
				
				//Add to goallist
				poGoalList.add(oNewGoalFromPrevious);
				oResult = oNewGoalFromPrevious;	
				
				oResult.setCondition(eCondition.IS_DRIVE_SOURCE);
			}

		} else {
			//Assign the right spatially nearest goal from the previous goal if the goal is from the perception
			eGoalType oPreviousGoalType = poPreviousGoal.getGoalType();
			
			if (oPreviousGoalType.equals(eGoalType.PERCEPTIONALDRIVE)==true) {
				oResult = clsGoalTools.getSpatiallyNearestGoalFromPerception(oEquivalentGoalList, poPreviousGoal);
				oResult.setCondition(eCondition.IS_PERCEPTIONAL_SOURCE);
			} else if (oPreviousGoalType.equals(eGoalType.MEMORYDRIVE)==true) {
				oResult = oEquivalentGoalList.get(0);	//drive or memory is always present
				oResult.setCondition(eCondition.IS_MEMORY_SOURCE);
			} else {
				oResult = oEquivalentGoalList.get(0);	//drive or memory is always present
			}
			
			
			
		}
		
//		//Set default precondition for all continued goals
//		
//		clsGoalTools.setTaskStatus(oResult, eCondition.NEED_CONTINUOS_ANALYSIS);
		
		return oResult;
	}
	
	   /**
     * Get the goal condition from the goal and set it
     * 
     * (wendt)
     *
     * @since 23.05.2013 22:31:43
     *
     * @param poGoalType
     * @return
     */
    private static eCondition getConditionFromGoalType(eGoalType poGoalType) {
        eCondition oResult = null;
        
        if (poGoalType.equals(eGoalType.MEMORYEMOTION)) {
            oResult = eCondition.IS_MEMORY_SOURCE;
        } else if (poGoalType.equals(eGoalType.MEMORYDRIVE)) {
            oResult = eCondition.IS_MEMORY_SOURCE;
        } if (poGoalType.equals(eGoalType.DRIVESOURCE)) {
            oResult = eCondition.IS_DRIVE_SOURCE;
        } else if (poGoalType.equals(eGoalType.PERCEPTIONALDRIVE)) {
            oResult = eCondition.IS_PERCEPTIONAL_SOURCE;
        } if (poGoalType.equals(eGoalType.PERCEPTIONALEMOTION)) {
            oResult = eCondition.IS_PERCEPTIONAL_SOURCE;
        }
        
        return oResult;
    }
    
    /**
     * DOCUMENT (wendt) - insert description
     *
     * @since 23.05.2013 23:25:11
     *
     * @param poGoal
     * @throws Exception
     */
    public static void setConditionFromGoalType(clsWordPresentationMeshGoal poGoal) throws Exception {
        eCondition oCondition = clsDecisionPreparationTools.getConditionFromGoalType(poGoal.getGoalType());
        poGoal.removeCondition(eCondition.IS_DRIVE_SOURCE);
        poGoal.removeCondition(eCondition.IS_MEMORY_SOURCE);
        poGoal.removeCondition(eCondition.IS_PERCEPTIONAL_SOURCE);
        poGoal.setCondition(oCondition);
    }
	
	
//	/**
//	 * Set the default conditions for all goals in the list except the continued goal.
//	 * 
//	 * (wendt)
//	 *
//	 * @since 27.09.2012 10:22:03
//	 *
//	 * @param poContinuedGoal
//	 * @param poGoalList
//	 */
//	public static void setDefaultConditionForGoalList(clsWordPresentationMeshGoal poContinuedGoal, ArrayList<clsWordPresentationMeshGoal> poGoalList) {
//		
//		for (clsWordPresentationMeshGoal oGoal : poGoalList) {
//			if (poContinuedGoal!=oGoal) {
//				setDefaultGoalCondition(oGoal, poContinuedGoal);
//			}
//		}
//	}
	

//	/**
//	 * For a certain goal, depending on the goal type, default start conditions are added. It means that if some continued goal erroneously gets here, the conditions below are added
//	 * 
//	 * (wendt)
//	 *
//	 * @since 27.09.2012 10:20:38
//	 *
//	 * @param poGoal
//	 */
//	private static void setDefaultGoalCondition(clsWordPresentationMeshGoal poGoal, clsWordPresentationMeshGoal poContinuedGoal) {
//		//All other goals will have a "NEED_FOCUS" or "NEED_INTERNAL_INFO" status
//		if (poGoal.getGoalType().equals(eGoalType.PERCEPTIONALDRIVE) || poGoal.getGoalType().equals(eGoalType.PERCEPTIONALEMOTION)) {
//			//Set the NEED_FOCUS for all focus images
//		    poGoal.setCondition(eCondition.COMPOSED_CODELET);
//		    poGoal.setCondition(eCondition.GOTO_GOAL_IN_PERCEPTION);
//		    poGoal.setCondition(eCondition.IS_PERCEPTIONAL_SOURCE);
//			
//		    
//		    
//		} else if (poGoal.getGoalType().equals(eGoalType.DRIVESOURCE)) {
//			//Set the NEED_INTERNAL_INFO, in order to trigger phantasy to activate memories
//		    poGoal.setCondition(eCondition.NEED_INTERNAL_INFO);
//		    poGoal.setCondition(eCondition.IS_DRIVE_SOURCE);
//		    
//		    
//		    
//		} else if (poGoal.getGoalType().equals(eGoalType.MEMORYEMOTION) || poGoal.getGoalType().equals(eGoalType.MEMORYDRIVE)) {
//		    poGoal.setCondition(eCondition.IS_MEMORY_SOURCE);
//			
//			// --- Check the conditions in the intention --- //
//			//Get the intention
//			clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(poGoal.getSupportiveDataStructure());
//			
//			//Check if the previous act is the same as this one
//			boolean bSameAct = clsActPreparationTools.checkIfPreviousActIsEqualToCurrentAct(poContinuedGoal, poGoal);
//			double rCurrentImageMatch = 0.0;
//			
//			//If the act has to start with the first image:
//			if (clsActTools.checkIfConditionExists(oIntention, eCondition.START_WITH_FIRST_IMAGE)==true) {
//				//Cases:
//				//1. If the first image has match 1.0 and there is no first act ||
//				//2. If the this act is the same as from the previous goal -> start this act as normal
//				//else set GOAL_CONDITION_BAD
//				clsWordPresentationMesh oFirstImage = clsActTools.getFirstImageFromIntention(oIntention);
//				rCurrentImageMatch = clsActTools.getPIMatch(oFirstImage);
//				
//			} else {
//				//Get best match from an intention
//				clsWordPresentationMesh oBestMatchEvent = clsActTools.getHighestPIMatchFromSubImages(oIntention);
//				rCurrentImageMatch = clsActTools.getPIMatch(oBestMatchEvent);
//			}
//			
//			//if (bSameAct==true && rCurrentImageMatch < P_ACTMATCHACTIVATIONTHRESHOLD) {
//            if (rCurrentImageMatch < P_ACTMATCHACTIVATIONTHRESHOLD) {
//                poGoal.setCondition(eCondition.ACT_MATCH_TOO_LOW);
//			} else {
//				//Set the need to perform a basic act recognition analysis
//			    poGoal.setCondition(eCondition.NEED_INTERNAL_INFO);
//			}
//		}
//	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 23.05.2013 13:22:59
	 *
	 * @param poContinuedGoal
	 * @param poSTM
	 */
	public static void appendPreviousActionsAsPreconditions(clsWordPresentationMeshGoal poContinuedGoal, clsShortTermMemory poSTM) {
		eCondition oActionCondition = eCondition.EXECUTED_NONE;
		
		//--- GET PREVIOUS MENTAL SITUATION ---//
		clsWordPresentationMesh oPreviousMentalSituation = poSTM.findPreviousSingleMemory();

		//Get the previous action
		clsWordPresentationMesh oPreviousActionMesh = clsMentalSituationTools.getAction(oPreviousMentalSituation);
		log.debug("Previous action " + oPreviousActionMesh);
		
		eAction oPreviousAction = eAction.valueOf(clsActionTools.getAction(oPreviousActionMesh));
		
		
		switch (oPreviousAction) {
			case BITE:
				oActionCondition = eCondition.EXECUTED_BITE;
				break;
			case EAT:
				oActionCondition = eCondition.EXECUTED_EAT;
				break;
			case PICKUP:
				oActionCondition = eCondition.EXECUTED_PICKUP;
				break;
			case EXCREMENT:
				oActionCondition = eCondition.EXECUTED_EXCREMENT;
				break;
			case REPRESS:
				oActionCondition = eCondition.EXECUTED_REPRESS;
				break;
			case SLEEP:
				oActionCondition = eCondition.EXECUTED_SLEEP;
				break;
			case RELAX:
				oActionCondition = eCondition.EXECUTED_RELAX;
				break;
			case MOVE_FORWARD:
				oActionCondition = eCondition.EXECUTED_MOVE_FORWARD;
				break;
			case MOVE_BACKWARD:
				oActionCondition = eCondition.EXECUTED_MOVE_BACKWARD;
				break;
			case TURN_LEFT:
				oActionCondition = eCondition.EXECUTED_TURN_LEFT;
				break;
			case TURN_RIGHT:
				oActionCondition = eCondition.EXECUTED_TURN_RIGHT;
				break;
			case NONE:
				oActionCondition = eCondition.EXECUTED_NONE;
				break;
			case SEARCH1:
				oActionCondition = eCondition.EXECUTED_SEARCH1;
				break;
			case SEND_TO_PHANTASY:
				oActionCondition = eCondition.EXECUTED_SEND_TO_PHANTASY;
				break;
			case PERFORM_BASIC_ACT_ANALYSIS:
				oActionCondition = eCondition.EXECUTED_PERFORM_BASIC_ACT_ANALYSIS;
				break;
			case FOCUS_ON:
				oActionCondition = eCondition.EXECUTED_FOCUS_ON;
				break;
			case FOCUS_MOVE_FORWARD:
				oActionCondition = eCondition.EXECUTED_FOCUS_MOVE_FORWARD;
				break;
			case FOCUS_TURN_LEFT:
				oActionCondition = eCondition.EXECUTED_FOCUS_TURN_LEFT;
				break;
			case FOCUS_TURN_RIGHT:
				oActionCondition = eCondition.EXECUTED_FOCUS_TURN_RIGHT;
				break;
			case FOCUS_SEARCH1:
				oActionCondition = eCondition.EXECUTED_FOCUS_SEARCH1;
				break;
			case STRAFE_LEFT:
				oActionCondition = eCondition.EXECUTED_STRAFE_LEFT;
				break;
			case STRAFE_RIGHT:
				oActionCondition = eCondition.EXECUTED_STRAFE_RIGHT;
				break;
			case NULLOBJECT:
				oActionCondition = eCondition.NULLOBJECT;
				break;
				
			default: 
			try {
				throw new Exception("This action " + oPreviousAction.toString() + " has no correspondance in the conditions. Add action to eCondition");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (oActionCondition.equals(eCondition.NULLOBJECT)==false && oActionCondition.equals(eCondition.EXECUTED_NONE)==false) {
		    poContinuedGoal.setCondition(oActionCondition);
		}
		
		log.debug("Append previous action, goal: Action: " + oActionCondition + ", goal: " + poContinuedGoal.toString());
		
	}
	
	/**
	 * Calculate the effort penalty
	 * 
	 * (wendt)
	 *
	 * @since 19.10.2012 10:41:45
	 *
	 * @param poGoal
	 * @return
	 */
	public static double calculateEffortPenalty(clsWordPresentationMeshGoal poGoal) {
		double nResult = 0;
		
		ArrayList<eCondition> oGoalConditionList = poGoal.getCondition();
		
		for (eCondition oC: oGoalConditionList) {
			nResult += clsImportanceTools.getEffortValueOfCondition(oC);
		}
		
		if (poGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)) {
			//Check how far away the goal is
			clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oPosition = clsEntityTools.getPosition(poGoal.getGoalObject());
			nResult += clsImportanceTools.getEffortValueOfDistance(oPosition.b, oPosition.c);

		} else if (poGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
			if (poGoal.checkIfConditionExists(eCondition.SET_BASIC_ACT_ANALYSIS)) {
				//There are only the acts: Check the act confidence. If it is low, then lower the pleasure value
				nResult = clsImportanceTools.getEffortValueOfActConfidence(clsActDataStructureTools.getIntention(poGoal.getSupportiveDataStructure()));
			}
			
		}
		
		return nResult;
	}
}