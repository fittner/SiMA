/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package system.algorithm.goals;

import java.util.ArrayList;
import java.util.ListIterator;

import org.slf4j.Logger;

import datatypes.helpstructures.clsPair;
import datatypes.helpstructures.clsTriple;
import logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import system.datamanipulation.clsActDataStructureTools;
import system.datamanipulation.clsActionTools;
import system.datamanipulation.clsEntityTools;
import system.datamanipulation.clsGoalTools;
import system.datamanipulation.clsImportanceTools;
import system.datamanipulation.clsMentalSituationTools;
import system.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 09:58:07
 * 
 */
public class clsDecisionPreparationTools {

	private static Logger log = clsLogger.getLog("DecisionPreparation");
	

	
	   /**
     * Get the goal condition from the goal and set it
     * 
     * (wendt)
     * 
     * algorithm
     *
     * @since 23.05.2013 22:31:43
     *
     * @param poGoalType
     * @return
     */
    private static eCondition getConditionFromGoalType(eGoalType poGoalType) {
        eCondition oResult = null;
        
        if (poGoalType.equals(eGoalType.MEMORYFEELING)) {
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
    
    private static eCondition getPreconditionFromAction(eAction poPreviousAction) throws Exception {
        eCondition oActionCondition = eCondition.EXECUTED_NONE;
        
        switch (poPreviousAction) {
        case BITE:
            oActionCondition = eCondition.EXECUTED_BITE;
            break;
        case EAT:
            oActionCondition = eCondition.EXECUTED_EAT;
            break;
        case BEAT:
            oActionCondition = eCondition.EXECUTED_BEAT;
            break;
        case PICKUP:
            oActionCondition = eCondition.EXECUTED_PICKUP;
            break;
        case DEPOSIT:
            oActionCondition = eCondition.EXECUTED_DEPOSIT;
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
            throw new Exception("This action " + poPreviousAction.toString() + " has no correspondance in the conditions. Add action to eCondition");
        }
        
        
        return oActionCondition;
    }

	
	/**
	 * DOCUMENT (wendt) - insert description
	 * 
	 * algorithm
	 *
	 * @since 23.05.2013 13:22:59
	 *
	 * @param poContinuedGoal
	 * @param poSTM
	 */
	public static void appendPreviousActionsAsPreconditions(clsWordPresentationMeshGoal poContinuedGoal, clsShortTermMemory poSTM) {
		//eCondition oActionCondition = eCondition.EXECUTED_NONE;
		
		//--- GET PREVIOUS MENTAL SITUATION ---//
		clsWordPresentationMesh oPreviousMentalSituation = poSTM.findPreviousSingleMemory();

		//Get the previous action
		clsWordPresentationMesh oPreviousActionMesh = clsMentalSituationTools.getAction(oPreviousMentalSituation);
		log.debug("Previous action " + oPreviousActionMesh);
		
		eAction oPreviousAction = eAction.valueOf(clsActionTools.getAction(oPreviousActionMesh));
		
		eCondition oActionCondition=null;
        try {
            oActionCondition = getPreconditionFromAction(oPreviousAction);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
		
		
		if (oActionCondition.equals(eCondition.NULLOBJECT)==false && oActionCondition.equals(eCondition.EXECUTED_NONE)==false) {
		    poContinuedGoal.setCondition(oActionCondition);
		}
		
		log.debug("Append previous action, goal: Action: " + oActionCondition + ", goal: " + poContinuedGoal.toString());
		
	}
	
	/**
	 * Calculate the effort penalty
	 * 
	 * algorithm
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
	
	   /**
     * Search the perception or memories for goals with very strong negative affect. These object are converted to drive demands
     * and put on the to of the priolist. This forces the agent to avoid these objects
     * (wendt)
     *
     * @since 17.09.2011 08:27:41
     *
     * @param poPotentialGoalList
     * @return
     */
    public static clsWordPresentationMeshGoal generatePanicGoalFromFeeling(ArrayList<clsWordPresentationMeshFeeling> poFeelingList) {
        clsWordPresentationMeshGoal oResult = clsGoalTools.getNullObjectWPM();
        
        if (poFeelingList.isEmpty()==false) {
            if (eEmotionType.valueOf(poFeelingList.get(0).getMoContent()).equals(eEmotionType.ANXIETY) ||
                    eEmotionType.valueOf(poFeelingList.get(0).getMoContent()).equals(eEmotionType.CONFLICT)) {
                oResult = clsGoalTools.createGoal("PANIC", eGoalType.EMOTIONSOURCE, -1, eAction.FLEE, new ArrayList<clsWordPresentationMeshFeeling>(), clsMeshTools.getNullObjectWPM(), clsMeshTools.getNullObjectWPM());
                oResult.setCondition(eCondition.PANIC);
            }   
        }
        
        return oResult;
    }
    
    /**
     * Remove all non reachable goals, which are kept in the STM
     * 
     * (wendt)
     *
     * @since 25.07.2012 11:39:25
     *
     * @param poGoalList
     */
    public static void removeNonReachableGoals(ArrayList<clsWordPresentationMeshGoal> poGoalList, clsShortTermMemory shortTermMemory) {
        ListIterator<clsWordPresentationMeshGoal> Iter = poGoalList.listIterator();
        
        ArrayList<clsWordPresentationMeshGoal> oRemoveList = new ArrayList<clsWordPresentationMeshGoal>();
        
        //Get all goals from STM
        ArrayList<clsPair<Integer, clsWordPresentationMesh>> oSTMList = shortTermMemory.getMoShortTimeMemory();
        for (clsPair<Integer, clsWordPresentationMesh> oSTM : oSTMList) {
            //Check if precondition GOAL_NOT_REACHABLE_EXISTS and Goal type != DRIVE_SOURCE
            ArrayList<clsWordPresentationMesh> oTEMPLIST = clsMentalSituationTools.getExcludedGoal(oSTM.b);
            ArrayList<clsWordPresentationMeshGoal> oExcludedGoalList = new ArrayList<clsWordPresentationMeshGoal>();
            for (clsWordPresentationMesh oWPM : oTEMPLIST) {
                oExcludedGoalList.add((clsWordPresentationMeshGoal) oWPM);
            }
            
             
            oRemoveList.addAll(oExcludedGoalList);
//          for (clsWordPresentationMesh oExcludedGoal : oExcludedGoalList) {
//              if (clsGoalTools.checkIfConditionExists(oSTM.b, eCondition.GOAL_NOT_REACHABLE)==true) {
//                  oRemoveList.add(oSTM.b);
//              }
//          }
            
        }
                        
        //Find all unreachable goals from STMList
        while (Iter.hasNext()) {
            clsWordPresentationMeshGoal oGoal = Iter.next();
            
            //Check if this is one of the STM goals, which shall be removed
            for (clsWordPresentationMeshGoal oRemoveGoal : oRemoveList) {
                if (oGoal.getGoalContentIdentifier().equals(oRemoveGoal.getGoalContentIdentifier())==true) {
                    //if yes, remove this goal      
                    Iter.remove();
                    log.debug("Non reachable goal removed: " + oGoal.toString());
                }
            }
            
        }
        
    }

}