/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package secondaryprocess.algorithm.goals;

import java.util.ArrayList;

import org.slf4j.Logger;

import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshAimOfDrive;
import base.datatypes.clsWordPresentationMeshGoal;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import base.logging.DataCollector;
import logger.clsLogger;
import memorymgmt.enums.eAction;
import memorymgmt.enums.eCondition;
import memorymgmt.enums.eGoalType;
import memorymgmt.enums.ePhiPosition;
import memorymgmt.enums.eRadius;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsEntityTools;
import secondaryprocess.datamanipulation.clsImportanceTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 09:58:07
 * 
 */
public class GoalAlgorithmTools {

	private static Logger log = clsLogger.getLog("SecondaryProcessFunctionality");
	protected final static Logger logFim = logger.clsLogger.getLog("Fim");
	
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
        eCondition oCondition = GoalAlgorithmTools.getConditionFromGoalType(poGoal.getGoalSource());
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
        case PICK_UP:
            oActionCondition = eCondition.EXECUTED_PICKUP;
            break;
        case DIVIDE:
            oActionCondition = eCondition.EXECUTED_DIVIDE;
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
        case SPEAK_EAT:
            oActionCondition = eCondition.EXECUTED_SPEAK_EAT;
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
        case GOTO:
            oActionCondition = eCondition.GOTO_GOAL_IN_PERCEPTION;
            break;
        case DROP:
            oActionCondition = eCondition.EXECUTED_DROP;
            break;
        case REQUEST:
            oActionCondition = eCondition.EXECUTED_REQUEST;
            break;
        case AGREE:
            oActionCondition = eCondition.EXECUTED_AGREE;
            break;      
        case DISAGREE:
            oActionCondition = eCondition.EXECUTED_DISAGREE;
            break;
        case OBJECT_TRANSFER:
            oActionCondition = eCondition.EXECUTED_OBJECT_TRANSFER;
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
	public static void appendPreviousActionsAsPreconditions(clsWordPresentationMeshGoal poContinuedGoalAsPlanGoal, clsShortTermMemory<clsWordPresentationMeshMentalSituation> poSTM) {
		//eCondition oActionCondition = eCondition.EXECUTED_NONE;
	    
		//--- GET PREVIOUS MENTAL SITUATION ---//
		clsWordPresentationMeshMentalSituation oPreviousMentalSituation = poSTM.findPreviousSingleMemory();

		//Get the previous action
		clsWordPresentationMeshPossibleGoal oPreviousActionMesh = oPreviousMentalSituation.getPlanGoal(); //clsMentalSituationTools.getAction(oPreviousMentalSituation);
		
		log.debug("Previous action: " + oPreviousActionMesh);
		eAction oPreviousAction = eAction.NULLOBJECT;
		try {
		    oPreviousAction = eAction.valueOf(clsActionTools.getAction(oPreviousActionMesh.getAssociatedPlanAction()));
		} catch (Exception e) {
		    log.error("Could not set enum constant", e);
		}
		
		
		eCondition oActionCondition=null;
        try {
            oActionCondition = getPreconditionFromAction(oPreviousAction);
        } catch (Exception e) {
            log.error("You have to add the action {} to the codelets clsCC_EXECUTE_STATIC_ACTION or clsCC_EXECUTE_MOVEMENT and to this function getPreconditionFromAction.", oPreviousAction, e);
        }
		
		
		if (oActionCondition.equals(eCondition.NULLOBJECT)==false) {
		    poContinuedGoalAsPlanGoal.setCondition(oActionCondition);
		}
		
		log.debug("Append previous action, goal: Action: " + oActionCondition + ", goal: " + poContinuedGoalAsPlanGoal.toString());
		
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
		} 
//		if (poGoal.checkIfConditionExists(eCondition.IS_CONTEXT_SOURCE)) {
//            
//
//        }
		
		else if (poGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
			if (poGoal.checkIfConditionExists(eCondition.SET_BASIC_ACT_ANALYSIS)) {
				//There are only the acts: Check the act confidence. If it is low, then lower the pleasure value
				nResult += clsImportanceTools.getEffortValueOfActConfidence(clsActDataStructureTools.getIntention(poGoal.getSupportiveDataStructure()));
			}
			
		}
		
		return nResult;
	}
	
    public static double calucateAimImportance(clsWordPresentationMeshPossibleGoal poGoal, eAction poAim, double prGoalImpact) {
        double rResult = 0;
        //use different local logger for this function
        Logger log = clsLogger.getLog("DecisionPreparation");
        
        //get the supportive data structure for the goal
        clsWordPresentationMesh oSuppDataStructure = poGoal.getSupportiveDataStructure();
        
        if(oSuppDataStructure != null && !oSuppDataStructure.isNullObject()) {
            log.debug("Supportive data structure " + oSuppDataStructure + " has type " + poGoal.getSupportiveDataStructureType());
    
            //if goal has no intention -> do not evaluate anything else, because poGoal has no act associated
            clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oSuppDataStructure);
            if(oIntention != null && !oIntention.isNullObject()) {
                log.debug("Goal " + poGoal + " has intention " + oIntention);
        
                eAction oAction = clsActTools.getRecommendedAction(oIntention);
                
                //check NONE and NULLOBJECT, just in case the usage changes somehow - in both cases there should be no impact increment
                if(oAction != eAction.NONE && oAction != eAction.NULLOBJECT) {
                    log.debug("Intention " + oIntention + " has action " + oAction);
            
                    //if the images action fits, increase the importance value
                    if(poAim == oAction) {
                        rResult += prGoalImpact * poGoal.getDriveDemandImportance();
//                        rResult += 0.2;
                        log.info("(in " + poGoal.getContent() + ") Act " + oSuppDataStructure.getContent() + " has importance increase by " + rResult + " due to action match for " + oAction);
                    }
                }
            }
        } 
        log.debug("clsImportanceTools::getImpactOfAim(...) -> " + rResult);
          
        return rResult;
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
    public static ArrayList<clsWordPresentationMeshPossibleGoal> removeNonReachableGoals(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList, clsShortTermMemory<clsWordPresentationMeshMentalSituation> shortTermMemory) {
        //ListIterator<clsWordPresentationMeshSelectableGoal> Iter = poGoalList.listIterator();
        ArrayList<clsWordPresentationMeshPossibleGoal> result = new ArrayList<clsWordPresentationMeshPossibleGoal>();
        ArrayList<clsWordPresentationMeshPossibleGoal> oRemoveList = new ArrayList<clsWordPresentationMeshPossibleGoal>();
        
        //Get all goals from STM
        ArrayList<clsPair<Integer, clsWordPresentationMeshMentalSituation>> oSTMList = shortTermMemory.getMoShortTimeMemory();
        for (clsPair<Integer, clsWordPresentationMeshMentalSituation> oSTM : oSTMList) {
            //Check if precondition GOAL_NOT_REACHABLE_EXISTS and Goal type != DRIVE_SOURCE
            ArrayList<clsWordPresentationMeshPossibleGoal> oTEMPLIST = oSTM.b.getExcludedSelectableGoals();  //clsMentalSituationTools.getExcludedGoal(oSTM.b);
            //ArrayList<clsWordPresentationMeshSelectableGoal> oExcludedGoalList = new ArrayList<clsWordPresentationMeshSelectableGoal>();
            
//            for (clsWordPresentationMeshSelectableGoal oWPM : oTEMPLIST) {
//                oExcludedGoalList.add((clsWordPresentationMeshSelectableGoal) oWPM);
//            }
            boolean skip=false;
            for(clsWordPresentationMeshPossibleGoal oTemp : oTEMPLIST)
            {
                
                if( oTemp.getSupportiveDataStructure().getContent().contains("FLEE_CARL"))
                {
                    skip=true;
                }
            }
            if(!skip)
            {
                oRemoveList.addAll(oTEMPLIST);
            }
        }
        //logFim.info("Excluded Goals: "+oRemoveList.toString());
        
        for (clsWordPresentationMeshPossibleGoal goal : poGoalList) { 
            boolean isFound=false;
            for (clsWordPresentationMeshPossibleGoal removeGoal : oRemoveList){
                if (goal.isEquivalentDataStructure(removeGoal)==true) {
                    isFound=true;
                    log.debug("Non reachable goal removed: " + removeGoal.toString());
                    break; 
                }     
                        
            }
            
            if (isFound==false) {
                result.add(goal);
            } else {
                
            }
        }
        
        return result;
                        
//        //Find all unreachable goals from STMList
//        while (Iter.hasNext()) {
//            clsWordPresentationMeshGoal oGoal = Iter.next();
//            
//            //Check if this is one of the STM goals, which shall be removed
//            for (clsWordPresentationMeshGoal oRemoveGoal : oRemoveList) {
//                if (oGoal.getGoalContentIdentifier().equals(oRemoveGoal.getGoalContentIdentifier())==true) {
//                    //if yes, remove this goal      
//                    try {
//                        Iter.remove();
//                        log.debug("Non reachable goal removed: " + oRemoveGoal.toString());
//                    } catch (IllegalStateException e) {
//                        log.error("Cannot remove goal {}", oRemoveGoal, e);
//                        System.exit(-1);
//                    }
//                   
//                }
//            }
//            
//        }
        
    }
    
    
    /**
     * Apply drive demand on a goal
     *
     * @author wendt
     * @since 03.10.2013 11:47:27
     *
     * @param selectableGoal
     * @param aimOfDrive
     * @return
     */
    private static boolean applyAimOfDriveOnGoal(clsWordPresentationMeshPossibleGoal selectableGoal, clsWordPresentationMeshAimOfDrive aimOfDrive, double prDriveImpact) {
        boolean goalMatch = false;
        
        //1. If the drive is the same
        if (selectableGoal.getGoalName().equals(aimOfDrive.getGoalName())) {
            goalMatch=true;
            //2. use the quota of affect of as far as the selectable goal can fulfill it
            if (selectableGoal.getPotentialDriveFulfillmentImportance()<=aimOfDrive.getTotalImportance()) {
                selectableGoal.setDriveDemandImportance(prDriveImpact * selectableGoal.getPotentialDriveFulfillmentImportance());
                DataCollector.goal(selectableGoal).putDriveFulfillmentImportance_F26(prDriveImpact * selectableGoal.getPotentialDriveFulfillmentImportance(), prDriveImpact * aimOfDrive.getTotalImportance());
            } else {
                selectableGoal.setDriveDemandImportance(prDriveImpact * aimOfDrive.getTotalImportance());
                DataCollector.goal(selectableGoal).putDriveFulfillmentImportance_F26(prDriveImpact * aimOfDrive.getTotalImportance(), prDriveImpact * aimOfDrive.getTotalImportance());
            }
        }
        
        return goalMatch;
    }
    
    /**
     * Apply drive demand sort order correction on goal
     *
     * @author wendt
     * @since 03.10.2013 11:47:41
     *
     * @param selectableGoal
     * @param aimOfDrive
     */
    private static void applyDriveDemandCorrections(clsWordPresentationMeshPossibleGoal selectableGoal, clsWordPresentationMeshAimOfDrive aimOfDrive) {
        //Get the correction for drive aims, which are important due to their order
        double driveOrderCorrection = GoalArrangementTools.getDriveDemandCorrectionFactorFromDriveOrder(aimOfDrive);
        selectableGoal.setDriveDemandCorrectionImportance(driveOrderCorrection);
    }
    
    
    
    /**
     * 
     * (wendt)
     *
     * @since 19.02.2012 18:04:40
     *
     * @param poSortedPossibleGoalList
     * @param poSortedFilterList
     * @param pnNumberOfGoalsToPass
     * @return
     */
    public static void applyDriveDemandsOnDriveGoal (ArrayList<clsWordPresentationMeshPossibleGoal> poSelectableGoalList, ArrayList<clsWordPresentationMeshAimOfDrive> poAimOfDriveListGoalList, double prDriveImpact) {
        
        //ArrayList<clsWordPresentationMeshSelectableGoal> oRetVal = new ArrayList<clsWordPresentationMeshSelectableGoal>();
        
        //1. Go through each drive above a certain threshold
        for (clsWordPresentationMeshAimOfDrive oAimOfDrive : poAimOfDriveListGoalList) {
            boolean selectableGoalFound = false;
            
            //Apply effect of drive to selectable goal on each possible goal
            for (clsWordPresentationMeshPossibleGoal selectableGoal : poSelectableGoalList) {
                boolean goalMatch = applyAimOfDriveOnGoal(selectableGoal, oAimOfDrive, prDriveImpact);
                
                //If at least one goal was found, set true
                if (goalMatch==true) {
                    applyDriveDemandCorrections(selectableGoal, oAimOfDrive);
                    log.trace("For aim of drive {}, the following selectable goal was found: {}", oAimOfDrive, selectableGoal);
                    selectableGoalFound=true;
                }
            }
            
            if (selectableGoalFound==false) {
                try {
                    throw new Exception("There is no goal for this aim of drive. There must be one, which is created in F23. " + oAimOfDrive);
                } catch (Exception e) {
                    log.error("No goal found", e);
                }
            }
//            
//            //If an aim of drive is above a threshold and there is no reachable goal, a new goal is created out of the drive goal
//            if (selectableGoalFound==false && oAimOfDrive.getTotalImportance()>=pnAimOfDriveImportanceThreshold) {
//                //Create a new goal, which is added to the list
//                clsWordPresentationMeshSelectableGoal generatedSelectableGoal = createDriveSourceGoal(oAimOfDrive);
//                
//                poSelectableGoalList.add(generatedSelectableGoal);
//                log.trace("No selectable goals were found for aim of drive {} and therefore the selectable goal {} was created", oAimOfDrive, generatedSelectableGoal);
//            }
        }

    }
    

    
    
//    /**
//     * Filter drive goals from image goals image
//     * 
//     * No other goals are extracted. Emotions, which form goals are separately extracted
//     * 
//     * Parameter SortOrder
//     * 0: Lowest order 4: Highest
//     * 
//     * 0: No Drivegoal present in the image
//     * +1: Drive is the same but the drive goal is different
//     * +1: Drivegoal is the same
//     * +1: The goal can be found in the perception
//     * 
//     * 
//     * 
//     * (wendt)
//     *
//     * @since 25.05.2012 18:10:06
//     *
//     * @param poDriveGoal
//     * @param poSortedPossibleGoalList
//     * @param pnAffectLevelThreshold
//     * @return
//     */
//    private static ArrayList<clsPair<Double, clsWordPresentationMeshSelectableGoal>> filterDriveGoalsFromImageGoals(clsWordPresentationMeshAimOfDrive poDriveGoal, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList, ArrayList<clsWordPresentationMeshSelectableGoal> poSortedPossibleGoalList, double prAffectLevelThreshold) {
//        ArrayList<clsPair<Double, clsWordPresentationMeshSelectableGoal>> oRetVal = new ArrayList<clsPair<Double, clsWordPresentationMeshSelectableGoal>>();
//        
//        //boolean bGoalObjectFound = false;
//        
//        //Find those potential goals, which could fulfill the goal from the drive
//        for (clsWordPresentationMeshSelectableGoal oPossibleGoal : poSortedPossibleGoalList) {
//            
//            //Get the level of affect for the object in the image of the potential goals
//            //TODO SM: Implement logic from task setting
//            double rImpactOfFeelings = clsImportanceTools.getConsequencesOfFeelingsOnGoalAsImportance(oPossibleGoal, poFeltFeelingList);
//            oPossibleGoal.setFeelingsImportance(rImpactOfFeelings);
//            //TODO SM: Set IMPACTOFFEELING on this goal
//            double rCurrentAffectLevel = oPossibleGoal.getTotalImportance();
//            
//            if (rCurrentAffectLevel>=prAffectLevelThreshold) {
//                //This is the sort order for the goal and it has to be fulfilled at any time
//                
//                //If the content is equal
//                if (poDriveGoal.getGoalName().equals(oPossibleGoal.getGoalName()) && poDriveGoal.getGoalObject().getMoDS_ID()==oPossibleGoal.getGoalObject().getMoDS_ID()) {
//                    double rCurrentPISortOrder = 0.01;      //Initialize as the drive content is the same => +1
//                    
//                    //Compare drive objects
//                    //FIXME AW: The goal objects are always true!!!! This should be corrected
//                    if (poDriveGoal.getGoalObject().getMoDS_ID()==oPossibleGoal.getGoalObject().getMoDS_ID()) {
//                        rCurrentPISortOrder += 0.01;    //same drive object => +1
//                        //bGoalObjectFound=true;
//                    }
//                    
//                    //Check if it exists in perception
//                    //if (clsGoalTools.getSupportiveDataStructureType(oPossibleGoal) == eContentType.PI) {
//                    //  nCurrentPISortOrder++;  //Object exists in the perception => +1 because it is nearer
//                    //}
//
//                    //Sort goals
//                    double rTotalCurrentAffectLevel = Math.abs(rCurrentAffectLevel * 10 + rCurrentPISortOrder);
//                    oRetVal.add(new clsPair<Double, clsWordPresentationMeshSelectableGoal>(rTotalCurrentAffectLevel, oPossibleGoal));
//                }
//            }               
//        }
//        
//        return oRetVal;
//    }
    
    
    

}