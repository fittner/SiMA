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
import pa._v38.tools.clsImportanceTools;
import pa._v38.tools.clsTriple;
import pa._v38.tools.datastructures.clsActDataStructureTools;
import pa._v38.tools.datastructures.clsActionTools;
import pa._v38.tools.datastructures.clsEntityTools;
import pa._v38.tools.datastructures.clsMentalSituationTools;

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
	
//	/**
//	 * This method prepoesses goal in F51. There are 2 types of goals: One goal, which is continued and many goals, which are processed for the first time. These goals get default preconditions 
//	 * and the continued goal is mapped to one of the new goals
//	 * 
//	 * (wendt)
//	 *
//	 * @since 01.10.2012 14:50:37
//	 *
//	 * @param poSTM
//	 * @param poGoalList
//	 * @return
//	 */
//	public static clsWordPresentationMeshGoal getContinuedGoal(clsShortTermMemory poSTM, ArrayList<clsWordPresentationMeshGoal> poGoalList) {
//		
//		//--- GET PREVIOUS MENTAL SITUATION ---//
//		clsWordPresentationMesh oPreviousMentalSituation = poSTM.findPreviousSingleMemory();
//		//Get the previous goal
//		clsWordPresentationMeshGoal oPreviousGoal = clsMentalSituationTools.getGoal(oPreviousMentalSituation);
//		log.debug("Previous goal: " + oPreviousGoal);
//		
//		
//		// --- GET AND INIT THE CONTINUED GOAL --- //
//		//Set condition for continuous preprocessing
//		clsWordPresentationMeshGoal oResult = getContinuedGoalFromPreviousGoal(oPreviousGoal, poGoalList);
//		log.trace("Continued goal: " + oResult);
//		
//		return oResult;
//	}
	
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