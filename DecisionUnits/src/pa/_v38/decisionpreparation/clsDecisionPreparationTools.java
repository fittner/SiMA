/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import java.util.ArrayList;

import pa._v38.logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActDataStructureTools;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsEntityTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsImportanceTools;
import pa._v38.tools.clsMentalSituationTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 09:58:07
 * 
 */
public class clsDecisionPreparationTools {

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
	public static clsWordPresentationMesh initGoals(clsShortTermMemory poSTM, ArrayList<clsWordPresentationMesh> poGoalList) {
		
		//--- GET PREVIOUS MENTAL SITUATION ---//
		clsWordPresentationMesh oPreviousMentalSituation = poSTM.findPreviousSingleMemory();
		//Get the previous goal
		clsWordPresentationMesh oPreviousGoal = clsMentalSituationTools.getGoal(oPreviousMentalSituation);
		clsLogger.jlog.debug("Previous goal: " + oPreviousGoal);
		
		
		// --- GET AND INIT THE CONTINUED GOAL --- //
		//Set condition for continuous preprocessing
		clsWordPresentationMesh oResult = clsDecisionPreparationTools.initContinuedGoal(oPreviousGoal, poGoalList);
		clsGoalTools.setCondition(oResult, eCondition.IS_NEW_CONTINUED_GOAL);
		clsLogger.jlog.debug("Continued goal:" + oResult.toString());
		
		return oResult;
	}
	
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
	private static clsWordPresentationMesh initContinuedGoal(clsWordPresentationMesh poPreviousGoal, ArrayList<clsWordPresentationMesh> poGoalList) {
		clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
		
		//Check if goal exists in the goal list
		ArrayList<clsWordPresentationMesh> oEquivalentGoalList = clsGoalTools.getEquivalentGoalFromGoalList(poGoalList, poPreviousGoal);
		
		//If the goal could not be found
		if (oEquivalentGoalList.isEmpty()==true) {
			
			//--- Remove the temporal data from the last turn ---//
			if (clsGoalTools.getGoalType(poPreviousGoal).equals(eGoalType.MEMORYDRIVE)==true) { 
				//--- COPY PREVIOUS GOAL ---//
				clsWordPresentationMesh oNewGoalFromPrevious = clsGoalTools.copyGoalWithoutTaskStatusAndAction(poPreviousGoal);
				
				//Add to goallist
				if (poGoalList.contains(oNewGoalFromPrevious)==false) {
					poGoalList.add(oNewGoalFromPrevious);
				}
				oResult = oNewGoalFromPrevious;	
				
				clsGoalTools.setCondition(oResult, eCondition.IS_MEMORY_SOURCE);	//FIXME: This operation should not be necessary here
				
			} else if (clsGoalTools.getGoalType(poPreviousGoal).equals(eGoalType.DRIVESOURCE)==true) {
				//--- COPY PREVIOUS GOAL ---//
				clsWordPresentationMesh oNewGoalFromPrevious = clsGoalTools.copyGoalWithoutTaskStatusAndAction(poPreviousGoal);
				
				//Add to goallist
				poGoalList.add(oNewGoalFromPrevious);
				oResult = oNewGoalFromPrevious;	
				
				clsGoalTools.setCondition(oResult, eCondition.IS_DRIVE_SOURCE);
			}

		} else {
			//Assign the right spatially nearest goal from the previous goal if the goal is from the perception
			eGoalType oPreviousGoalType = clsGoalTools.getGoalType(poPreviousGoal);
			
			if (oPreviousGoalType.equals(eGoalType.PERCEPTIONALDRIVE)==true) {
				oResult = clsGoalTools.getSpatiallyNearestGoalFromPerception(oEquivalentGoalList, poPreviousGoal);
				clsGoalTools.setCondition(oResult, eCondition.IS_PERCEPTIONAL_SOURCE);
			} else if (oPreviousGoalType.equals(eGoalType.MEMORYDRIVE)==true) {
				oResult = oEquivalentGoalList.get(0);	//drive or memory is always present
				clsGoalTools.setCondition(oResult, eCondition.IS_MEMORY_SOURCE);
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
	 * Set the default conditions for all goals in the list except the continued goal.
	 * 
	 * (wendt)
	 *
	 * @since 27.09.2012 10:22:03
	 *
	 * @param poContinuedGoal
	 * @param poGoalList
	 */
	public static void setDefaultConditionForGoalList(clsWordPresentationMesh poContinuedGoal, ArrayList<clsWordPresentationMesh> poGoalList) {
		
		for (clsWordPresentationMesh oGoal : poGoalList) {
			if (poContinuedGoal!=oGoal) {
				setDefaultGoalCondition(oGoal, poContinuedGoal);
			}
		}
	}
	

	/**
	 * For a certain goal, depending on the goal type, default start conditions are added. It means that if some continued goal erroneously gets here, the conditions below are added
	 * 
	 * (wendt)
	 *
	 * @since 27.09.2012 10:20:38
	 *
	 * @param poGoal
	 */
	private static void setDefaultGoalCondition(clsWordPresentationMesh poGoal, clsWordPresentationMesh poContinuedGoal) {
		//All other goals will have a "NEED_FOCUS" or "NEED_INTERNAL_INFO" status
		if (clsGoalTools.getGoalType(poGoal).equals(eGoalType.PERCEPTIONALDRIVE) || clsGoalTools.getGoalType(poGoal).equals(eGoalType.PERCEPTIONALEMOTION)) {
			//Set the NEED_FOCUS for all focus images
			//clsGoalTools.setTaskStatus(poGoal, eCondition.NEED_GOAL_FOCUS);
			//clsGoalTools.setTaskStatus(poGoal, eCondition.IS_PERCEPTIONAL_SOURCE);
			clsGoalTools.setCondition(poGoal, eCondition.COMPOSED_CODELET);
			clsGoalTools.setCondition(poGoal, eCondition.GOTO_GOAL_IN_PERCEPTION);
			clsGoalTools.setCondition(poGoal, eCondition.IS_PERCEPTIONAL_SOURCE);
			
		} else if (clsGoalTools.getGoalType(poGoal).equals(eGoalType.DRIVESOURCE)) {
			//Set the NEED_INTERNAL_INFO, in order to trigger phantasy to activate memories
			clsGoalTools.setCondition(poGoal, eCondition.NEED_INTERNAL_INFO);
			clsGoalTools.setCondition(poGoal, eCondition.IS_DRIVE_SOURCE);
		} else if (clsGoalTools.getGoalType(poGoal).equals(eGoalType.MEMORYEMOTION) || clsGoalTools.getGoalType(poGoal).equals(eGoalType.MEMORYDRIVE)) {
			clsGoalTools.setCondition(poGoal, eCondition.IS_MEMORY_SOURCE);
			
			// --- Check the conditions in the intention --- //
			
			//Get the intention
			clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(clsGoalTools.getSupportiveDataStructure(poGoal));
			
			//If the act has to start with the first image:
			if (clsActTools.checkIfConditionExists(oIntention, eCondition.START_WITH_FIRST_IMAGE)==true) {
				//Cases:
				//1. If the first image has match 1.0 and there is no first act ||
				//2. If the this act is the same as from the previous goal -> start this act as normal
				//else set GOAL_CONDITION_BAD
				
				//Get the first image and its match to the PI
				ArrayList<clsWordPresentationMesh> oEventImageList = clsActTools.getAllSubImages(oIntention);
				double rCurrentFirstImageMatch = 0.0;
				if (oEventImageList.isEmpty()==false) {
					clsWordPresentationMesh oFirstImage = clsActTools.getFirstImage(oEventImageList.get(0));
					rCurrentFirstImageMatch = clsActTools.getPIMatch(oFirstImage);
				}
				
				
				clsWordPresentationMesh oPreviousIntention = clsActDataStructureTools.getIntention(clsGoalTools.getSupportiveDataStructure(poContinuedGoal));
				
				
				if (oPreviousIntention.getMoDS_ID()!=oIntention.getMoDS_ID() && rCurrentFirstImageMatch < 1.0) {
					clsGoalTools.setCondition(poGoal, eCondition.GOAL_CONDITION_BAD);
				} else {
					//Set the need to perform a basic act recognition analysis
					clsGoalTools.setCondition(poGoal, eCondition.NEED_INTERNAL_INFO);
				}
				
			} else {
				//Set the need to perform a basic act recognition analysis
				clsGoalTools.setCondition(poGoal, eCondition.NEED_INTERNAL_INFO);
			}
		}
	}
	
	public static void appendPreviousActionsAsPreconditions(clsWordPresentationMesh poContinuedGoal, clsShortTermMemory poSTM) {
		eCondition oActionCondition = eCondition.EXECUTED_NONE;
		
		//--- GET PREVIOUS MENTAL SITUATION ---//
		clsWordPresentationMesh oPreviousMentalSituation = poSTM.findPreviousSingleMemory();

		//Get the previous action
		clsWordPresentationMesh oPreviousActionMesh = clsMentalSituationTools.getAction(oPreviousMentalSituation);
		clsLogger.jlog.debug("Previous action " + oPreviousActionMesh);
		
		eAction oPreviousAction = eAction.valueOf(clsActionTools.getAction(oPreviousActionMesh));
		
		
		switch (oPreviousAction) {
			case BITE:
				oActionCondition = eCondition.EXECUTED_BITE;
				break;
			case EAT:
				oActionCondition = eCondition.EXECUTED_EAT;
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
			clsGoalTools.setCondition(poContinuedGoal, oActionCondition);
		}
		
		clsLogger.jlog.debug("Append previous action, goal:" + poContinuedGoal.toString());
		
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
	public static int calculateEffortPenalty(clsWordPresentationMesh poGoal) {
		int nResult = 0;
		
		ArrayList<eCondition> oGoalConditionList = clsGoalTools.getCondition(poGoal);
		
		for (eCondition oC: oGoalConditionList) {
			nResult += clsImportanceTools.getEffortValueOfCondition(oC);
		}
		
		if (clsGoalTools.checkIfConditionExists(poGoal, eCondition.IS_PERCEPTIONAL_SOURCE)) {
			//Check how far away the goal is
			clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oPosition = clsEntityTools.getPosition(clsGoalTools.getGoalObject(poGoal));
			nResult += clsImportanceTools.getEffortValueOfDistance(oPosition.b, oPosition.c);

		} else if (clsGoalTools.checkIfConditionExists(poGoal, eCondition.IS_MEMORY_SOURCE)) {
			if (clsGoalTools.checkIfConditionExists(poGoal, eCondition.SET_BASIC_ACT_ANALYSIS)) {
				//There are only the acts: Check the act confidence. If it is low, then lower the pleasure value
				nResult = clsImportanceTools.getEffortValueOfActConfidence(clsActDataStructureTools.getIntention(clsGoalTools.getSupportiveDataStructure(poGoal)));
			}
			
		}
		
		return nResult;
	}
}