/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package secondaryprocess.algorithm.goals;

import java.util.ArrayList;
import java.util.ListIterator;

import org.slf4j.Logger;

import datatypes.helpstructures.clsPair;
import datatypes.helpstructures.clsTriple;
import logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshAimOfDrive;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshMentalSituation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsEntityTools;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;
import secondaryprocess.datamanipulation.clsImportanceTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 09:58:07
 * 
 */
public class clsGoalAlgorithmTools {

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
        eCondition oCondition = clsGoalAlgorithmTools.getConditionFromGoalType(poGoal.getGoalType());
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
	public static void appendPreviousActionsAsPreconditions(clsWordPresentationMeshGoal poContinuedGoalAsPlanGoal, clsShortTermMemory<clsWordPresentationMeshMentalSituation> poSTM) {
		//eCondition oActionCondition = eCondition.EXECUTED_NONE;
	    
		//--- GET PREVIOUS MENTAL SITUATION ---//
		clsWordPresentationMeshMentalSituation oPreviousMentalSituation = poSTM.findPreviousSingleMemory();

		//Get the previous action
		clsWordPresentationMeshSelectableGoal oPreviousActionMesh = oPreviousMentalSituation.getPlanGoal(); //clsMentalSituationTools.getAction(oPreviousMentalSituation);
		
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
            log.error(e.getMessage());
        }
		
		
		if (oActionCondition.equals(eCondition.NULLOBJECT)==false && oActionCondition.equals(eCondition.EXECUTED_NONE)==false) {
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

		} else if (poGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
			if (poGoal.checkIfConditionExists(eCondition.SET_BASIC_ACT_ANALYSIS)) {
				//There are only the acts: Check the act confidence. If it is low, then lower the pleasure value
				nResult = clsImportanceTools.getEffortValueOfActConfidence(clsActDataStructureTools.getIntention(poGoal.getSupportiveDataStructure()));
			}
			
		}
		
		return nResult;
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
    public static void removeNonReachableGoals(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList, clsShortTermMemory<clsWordPresentationMeshMentalSituation> shortTermMemory) {
        ListIterator<clsWordPresentationMeshSelectableGoal> Iter = poGoalList.listIterator();
        
        ArrayList<clsWordPresentationMeshGoal> oRemoveList = new ArrayList<clsWordPresentationMeshGoal>();
        
        //Get all goals from STM
        ArrayList<clsPair<Integer, clsWordPresentationMeshMentalSituation>> oSTMList = shortTermMemory.getMoShortTimeMemory();
        for (clsPair<Integer, clsWordPresentationMeshMentalSituation> oSTM : oSTMList) {
            //Check if precondition GOAL_NOT_REACHABLE_EXISTS and Goal type != DRIVE_SOURCE
            ArrayList<clsWordPresentationMeshSelectableGoal> oTEMPLIST = oSTM.b.getExcludedSelectableGoals();  //clsMentalSituationTools.getExcludedGoal(oSTM.b);
            ArrayList<clsWordPresentationMeshSelectableGoal> oExcludedGoalList = new ArrayList<clsWordPresentationMeshSelectableGoal>();
            for (clsWordPresentationMeshSelectableGoal oWPM : oTEMPLIST) {
                oExcludedGoalList.add((clsWordPresentationMeshSelectableGoal) oWPM);
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
    private static boolean applyAimOfDriveOnGoal(clsWordPresentationMeshSelectableGoal selectableGoal, clsWordPresentationMeshAimOfDrive aimOfDrive) {
        boolean goalMatch = false;
        
        //1. If the drive is the same
        if (selectableGoal.getGoalName().equals(aimOfDrive.getGoalName())) {
            goalMatch=true;
            //2. use the quota of affect of as far as the selectable goal can fulfill it
            if (selectableGoal.getPotentialDriveFulfillmentImportance()<=aimOfDrive.getTotalImportance()) {
                selectableGoal.setDriveDemandImportance(selectableGoal.getPotentialDriveFulfillmentImportance());
            } else {
                selectableGoal.setDriveDemandImportance(aimOfDrive.getTotalImportance());
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
    private static void applyDriveDemandCorrections(clsWordPresentationMeshSelectableGoal selectableGoal, clsWordPresentationMeshAimOfDrive aimOfDrive) {
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
    public static void applyDriveDemandsOnDriveGoal (ArrayList<clsWordPresentationMeshSelectableGoal> poSelectableGoalList, ArrayList<clsWordPresentationMeshAimOfDrive> poAimOfDriveListGoalList, double pnAimOfDriveImportanceThreshold) {
        
        //ArrayList<clsWordPresentationMeshSelectableGoal> oRetVal = new ArrayList<clsWordPresentationMeshSelectableGoal>();
        
        //1. Go through each drive above a certain threshold
        for (clsWordPresentationMeshAimOfDrive oAimOfDrive : poAimOfDriveListGoalList) {
            boolean selectableGoalFound = false;
            
            //Apply effect of drive to selectable goal on each possible goal
            for (clsWordPresentationMeshSelectableGoal selectableGoal : poSelectableGoalList) {
                boolean goalMatch = applyAimOfDriveOnGoal(selectableGoal, oAimOfDrive);
                applyDriveDemandCorrections(selectableGoal, oAimOfDrive);
                
                //If at least one goal was found, set true
                if (goalMatch==true) {
                    log.trace("For aim of drive {}, the following selectable goal was found: {}", oAimOfDrive, selectableGoal);
                    selectableGoalFound=true;
                }
            }
            
            //If an aim of drive is above a threshold and there is no reachable goal, a new goal is created out of the drive goal
            if (selectableGoalFound==false && oAimOfDrive.getTotalImportance()>=pnAimOfDriveImportanceThreshold) {
                //Create a new goal, which is added to the list
                clsWordPresentationMeshSelectableGoal generatedSelectableGoal = clsGoalManipulationTools.createSelectableGoal(oAimOfDrive.getGoalName(), eGoalType.DRIVESOURCE, oAimOfDrive.getTotalImportance(), oAimOfDrive.getGoalObject());
                //Apply the same as the other goals
                applyAimOfDriveOnGoal(generatedSelectableGoal, oAimOfDrive);
                applyDriveDemandCorrections(generatedSelectableGoal, oAimOfDrive);
                
                poSelectableGoalList.add(generatedSelectableGoal);
                log.trace("No selectable goals were found for aim of drive {} and therefore the selectable goal {} was created", oAimOfDrive, generatedSelectableGoal);
            }
        }
        
        
//        //1. Go through the list of drives, which are used as filter
//        for (int i=0; i<poSortedDriveGoalList.size();i++) {
//            ArrayList<clsPair<Double, clsWordPresentationMeshGoal>> oPreliminarySortList = new ArrayList<clsPair<Double, clsWordPresentationMeshGoal>>();
//            //Get drive goal
//            clsWordPresentationMeshAimOfDrive oDriveGoal = poSortedDriveGoalList.get(i);
//            
//            ArrayList<clsPair<Double, clsWordPresentationMeshSelectableGoal>> oPreliminaryGoalList = new ArrayList<clsPair<Double, clsWordPresentationMeshGoal>>();
//            
//            //Extract all remembered goals from the image, which match the drive goal
//            ArrayList<clsPair<Double,clsWordPresentationMeshSelectableGoal>> filterDriveGoalsFromImageGoals = filterDriveGoalsFromImageGoals(oDriveGoal, poFeltFeelingList, poSortedPossibleGoalList, pnAffectLevelThreshold);
//            oPreliminaryGoalList.addAll(filterDriveGoalsFromImageGoals);
//            log.trace("for drivegoal " + oDriveGoal.getGoalContentIdentifier() + " the following reachable goals were extracted: " + oPreliminaryGoalList);
//            
//            //Some goals are important although they are not in the perception. Therefore, the drive goals will be passed
//            if (oPreliminaryGoalList.isEmpty()==true && oDriveGoal.getTotalImportance()>=pnAffectLevelThreshold) {
//                //There is no current affect level
//                //This sort order shall have the last priority
//                
//                double rCurrentPISortOrder = 0;
//                double rTotalCurrentAffectLevel = Math.abs(0 * 10 + rCurrentPISortOrder);
//                oPreliminaryGoalList.add(new clsPair<Double, clsWordPresentationMeshSelectableGoal>(rTotalCurrentAffectLevel, oDriveGoal));
//            }
//            
//            //Sort reachable goals for each drive goal
//            for (clsPair<Double, clsWordPresentationMeshSelectableGoal> oPair : oPreliminaryGoalList) {
//                int nIndex = 0;
//                //Increase index if the list is not empty
//                while((oPreliminarySortList.isEmpty()==false) && 
//                        (nIndex<oPreliminarySortList.size()) &&
//                        (oPreliminarySortList.get(nIndex).a > oPair.a)) {
//                    nIndex++;
//                }
//                
//                oPreliminarySortList.add(nIndex, oPair);
//            }
//            
//            for (clsPair<Double, clsWordPresentationMeshGoal> oPair : oPreliminarySortList) {
//                oRetVal.add(oPair.b);
//            }
//        }   
//        
//        return oRetVal;
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