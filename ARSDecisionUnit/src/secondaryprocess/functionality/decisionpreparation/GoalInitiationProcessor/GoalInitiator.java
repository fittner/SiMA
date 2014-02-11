/**
 * CHANGELOG
 *
 * 15.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.GoalInitiationProcessor;

import java.util.ArrayList;

import logger.clsLogger;
import memorymgmt.enums.eCondition;

import org.slf4j.Logger;

import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import secondaryprocess.algorithm.goals.GoalAlgorithmTools;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 15.10.2013, 21:00:08
 * 
 */
public class GoalInitiator implements GoalInitiatorInterface {
    
    private static Logger log = clsLogger.getLog("DecisionPreparation");
    
    public GoalInitiator() {
        
    }

    /* (non-Javadoc)
     *
     * @since 15.10.2013 21:03:34
     * 
     * @see secondaryprocess.functionality.decisionpreparation.GoalInitiationProcessor.GoalInitiatorInterface#initiateIncomingGoals(java.util.ArrayList, pa._v38.memorymgmt.datatypes.clsWordPresentationMeshMentalSituation)
     */
    @Override
    public ArrayList<clsWordPresentationMeshPossibleGoal> initiateIncomingGoals(ArrayList<clsWordPresentationMeshPossibleGoal> newGoals, clsWordPresentationMeshMentalSituation previousGoal) {
        ArrayList<clsWordPresentationMeshPossibleGoal> result = new ArrayList<clsWordPresentationMeshPossibleGoal>();
        
        //Get all continued goals or create new goals
        ArrayList<clsWordPresentationMeshPossibleGoal> continuedGoals = extractContinuedGoals(newGoals, previousGoal);
        this.processContinuedGoals(continuedGoals);
        
        ArrayList<clsWordPresentationMeshPossibleGoal> onlyNewGoals = extractNewGoals(continuedGoals, newGoals);
        this.processNewGoals(onlyNewGoals);
        
        result = this.mergeResultingGoals(continuedGoals, onlyNewGoals);
        
        return result;
    }
    
    /**
     * Process all new goals
     *
     * @author wendt
     * @since 16.10.2013 10:28:40
     *
     * @param onlyNewGoals
     */
    private void processNewGoals(ArrayList<clsWordPresentationMeshPossibleGoal> onlyNewGoals) {
        for (clsWordPresentationMeshPossibleGoal goal : onlyNewGoals) {
            try {
                processNewGoal(goal);
            } catch (Exception e) {
                log.error("Could not process new goal {}", goal, e);
            }
        }
    }
    
    /**
     * Process one new goal
     *
     * @author wendt
     * @since 16.10.2013 10:28:48
     *
     * @param goal
     * @throws Exception 
     */
    private void processNewGoal(clsWordPresentationMeshPossibleGoal goal) throws Exception {
        //Set goal type for new goal
        try {
            GoalAlgorithmTools.setConditionFromGoalType(goal);
        } catch (Exception e) {
            log.error("Could not set condition from goal type", e);
            throw new Exception(e.getMessage());
        }
        
        //Set condition new goal
        goal.setCondition(eCondition.IS_NEW_GOAL);
        goal.removeCondition(eCondition.IS_CONTINUED_GOAL);
        goal.removeCondition(eCondition.IS_CONTINUED_PLANGOAL);
        
    }
    
    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 16.10.2013 12:55:03
     *
     * @param continuedGoals
     */
    private void processContinuedGoals(ArrayList<clsWordPresentationMeshPossibleGoal> continuedGoals) {
        for (clsWordPresentationMeshPossibleGoal goal : continuedGoals) {
            processContinuedGoal(goal);
        }
    }
    
    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 16.10.2013 12:55:17
     *
     * @param goal
     */
    private void processContinuedGoal(clsWordPresentationMeshPossibleGoal goal) {
        //oResult.setCondition(eCondition.IS_CONTINUED_GOAL);
    }
    
    /**
     * Extract continued goals from incoming goals
     * If a goal is not equivalent in the incoming list, the goal is copied from the STM
     *
     * @author wendt
     * @since 16.10.2013 11:49:51
     *
     * @param incomingGoals
     * @param previousSituation
     * @return
     */
    private ArrayList<clsWordPresentationMeshPossibleGoal> extractContinuedGoals(ArrayList<clsWordPresentationMeshPossibleGoal> incomingGoals, clsWordPresentationMeshMentalSituation previousSituation) {
        ArrayList<clsWordPresentationMeshPossibleGoal> result = new ArrayList<clsWordPresentationMeshPossibleGoal>();
        
        //Get continued goals
        ArrayList<clsWordPresentationMeshPossibleGoal> previousGoals = previousSituation.getSelectableGoals();
        
        //Check with the incoming goals if they can be matched and extract the incoming goals without processing
        for (clsWordPresentationMeshPossibleGoal previousSelectableGoal : previousGoals) {
            clsWordPresentationMeshPossibleGoal oContinuedGoal = clsWordPresentationMeshPossibleGoal.getNullObject();
            oContinuedGoal = getContinuedGoal(previousSelectableGoal, incomingGoals);
            if (oContinuedGoal.isNullObject()==false) {
                result.add(oContinuedGoal);
            }
        }
        
        return result;
    }
    
    /**
     * Get continued goal in the incoming list from the previous situation
     *
     * @author wendt
     * @since 16.10.2013 11:50:29
     *
     * @param poPreviousGoal
     * @param poGoalList
     * @return
     */
    private clsWordPresentationMeshPossibleGoal getContinuedGoal(clsWordPresentationMeshPossibleGoal poPreviousGoal, ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList) {
        //Get a copy of the previous goal
        clsWordPresentationMeshPossibleGoal oContinuedGoal = clsGoalManipulationTools.getContinuedGoalFromPreviousGoal(poPreviousGoal, poGoalList);
        
        return oContinuedGoal;
    }
    
    /**
     * Extract all goals, which are not continued as new goals
     *
     * @author wendt
     * @since 16.10.2013 11:50:58
     *
     * @param continuedGoals
     * @param newGoals
     * @return
     */
    private ArrayList<clsWordPresentationMeshPossibleGoal> extractNewGoals(ArrayList<clsWordPresentationMeshPossibleGoal> continuedGoals, ArrayList<clsWordPresentationMeshPossibleGoal> newGoals) {
        ArrayList<clsWordPresentationMeshPossibleGoal> result = new ArrayList<clsWordPresentationMeshPossibleGoal>();
        
        for (clsWordPresentationMeshPossibleGoal newGoal : newGoals) {
            if (continuedGoals.contains(newGoal)==false) {
                result.add(newGoal);
            }
        }
        
        return result;
    }
    
    /**
     * Merge 2 lists and keep only one instance of an object
     *
     * @author wendt
     * @since 16.10.2013 11:51:16
     *
     * @param list1
     * @param list2
     * @return
     */
    private ArrayList<clsWordPresentationMeshPossibleGoal> mergeResultingGoals(ArrayList<clsWordPresentationMeshPossibleGoal> list1, ArrayList<clsWordPresentationMeshPossibleGoal> list2) {
        ArrayList<clsWordPresentationMeshPossibleGoal> result = new ArrayList<clsWordPresentationMeshPossibleGoal>(list1);
        
        for (clsWordPresentationMeshPossibleGoal goal : list2) {
            if (result.contains(goal)==false) {
                result.add(goal);
            }
        }
        
        return result;
    }
    
    
    
    
    
    
    
    
    
//    /**
//     * DOCUMENT - insert description
//     *
//     * @author wendt
//     * @since 15.10.2013 14:50:52
//     *
//     * @param poGoal
//     * @throws Exception
//     */
//    private void initIncomingGoal(clsWordPresentationMeshSelectableGoal poGoal) throws Exception {
//        //Set the correct goal type as condition
//        try {
//            clsGoalAlgorithmTools.setConditionFromGoalType(poGoal);
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        
//        //Add condition that they are new incoming goals
//        poGoal.setCondition(eCondition.IS_UNPROCESSED_GOAL);
//        
//        //Execute matching codelets
//        this.moCodeletHandler.executeMatchingCodelets(this, poGoal, eCodeletType.INIT, -1);
//    }
    
//    /**
//     * DOCUMENT - insert description
//     *
//     * @author wendt
//     * @since 15.10.2013 14:50:49
//     *
//     * @param poSTM
//     * @param poGoalList
//     * @return
//     */
//    public ArrayList<clsWordPresentationMeshSelectableGoal> initContinuedGoalList(clsShortTermMemory<clsWordPresentationMeshMentalSituation> poSTM, ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) {
//        ArrayList<clsWordPresentationMeshSelectableGoal> resultingContinuedGoal = new ArrayList<clsWordPresentationMeshSelectableGoal>(); //new ArrayList<clsWordPresentationMeshSelectableGoal>();
//        //clsWordPresentationMeshSelectableGoal resultingPlanGoal = clsWordPresentationMeshSelectableGoal.getNullObject();
//        
//        //--- GET PREVIOUS MENTAL SITUATION ---//
//        clsWordPresentationMeshMentalSituation oPreviousMentalSituation = poSTM.findPreviousSingleMemory();
//        //Get the previous goal
//        
//        //=== Perform system tests ===//
//        clsTester.getTester().setActivated(false);
//        if (clsTester.getTester().isActivated()) {
//            try {
//                log.warn("Systemtester active");
//                for (clsWordPresentationMeshSelectableGoal mesh : oPreviousMentalSituation.getSelectableGoals()) {
//                    clsTester.getTester().exeTestCheckLooseAssociations(mesh.getSupportiveDataStructure()); 
//                }
//            } catch (Exception e) {
//                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
//            }
//        }
//
//        //Get previous selectable, continued goals
//        ArrayList<clsWordPresentationMeshSelectableGoal> oPreviousSelectableGoals = oPreviousMentalSituation.getSelectableGoals();
//        
//        //Get previous plangoal
//        clsWordPresentationMeshSelectableGoal oPreviousPlanGoal = oPreviousMentalSituation.getPlanGoal();  //clsMentalSituationTools.getGoal(oPreviousMentalSituation);
//        log.debug("Previous goal from STM: " + oPreviousPlanGoal);
//        
//        for (clsWordPresentationMeshSelectableGoal previousSelectableGoal : oPreviousSelectableGoals) {
//            clsWordPresentationMeshSelectableGoal oContinuedGoal;
//            try {
//                oContinuedGoal = initContinuedGoal(previousSelectableGoal, poGoalList);
//                
//                
//                if (oContinuedGoal.isNullObject()==false) {
////                    if (previousSelectableGoal.isEquivalentDataStructure(oPreviousPlanGoal)==true) {
////                        //Append previous actions as preconditions on the previous plan goal
////                        appendPreviousActionsAsPreconditionsOnPlanGoal(poSTM, oContinuedGoal);
////                        resultingPlanGoal = oContinuedGoal;
////                    }
//                    resultingContinuedGoal.add(oContinuedGoal);
//                }
//                
//            } catch (ElementNotFoundException e) {
//                log.error("Error at the init of continued goals", e);
//            }
//            
//        }
//        
//        ArrayList<clsWordPresentationMeshSelectableGoal> result = resultingContinuedGoal;
//        
//        return result;
//    }
    

    
//    /**
//     * DOCUMENT - insert description
//     *
//     * @author wendt
//     * @since 15.10.2013 14:50:45
//     *
//     * @param poPreviousGoal
//     * @param poGoalList
//     * @return
//     * @throws ElementNotFoundException
//     */
//    private clsWordPresentationMeshSelectableGoal initContinuedGoal(clsWordPresentationMeshSelectableGoal poPreviousGoal, ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) throws ElementNotFoundException {
//        
//        clsWordPresentationMeshSelectableGoal oContinuedGoal = clsWordPresentationMeshSelectableGoal.getNullObject(); //clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
//        
//        if (poPreviousGoal.checkIfConditionExists(eCondition.IS_CONTINUED_GOAL)==true) {
//            oContinuedGoal = clsGoalManipulationTools.getContinuedGoalFromPreviousGoal(poPreviousGoal, poGoalList);
//            
//            if (oContinuedGoal.isNullObject()==false) {
//                //Goal type is the only condition set
//                
//                //Set new continued goal
//                oContinuedGoal.setCondition(eCondition.IS_CONTINUED_GOAL);
//                
//                //Set condition is unprocessed, in order to process the continued goal
//                oContinuedGoal.setCondition(eCondition.IS_UNPROCESSED_GOAL);
//                
//                if (poPreviousGoal.checkIfConditionExists(eCondition.IS_CONTINUED_PLANGOAL)==true) {
//                    oContinuedGoal.setCondition(eCondition.IS_CONTINUED_PLANGOAL);
//                }
//            }
//        }
//        
//        log.trace("Continued goal: " + oContinuedGoal);
//        
//        //Apply init codelets on the continued goal
//        this.moCodeletHandler.executeMatchingCodelets(this, oContinuedGoal, eCodeletType.INIT, -1);
//        
//        log.debug("Continued goal:" + oContinuedGoal.toString());
//        
//        return oContinuedGoal;
//    }
    
//    /**
//     * Add the continued goal to the reachable goals if not already added
//     * 
//     * (wendt)
//     *
//     * @since 29.05.2013 00:09:43
//     *
//     * @param poGoalList
//     * @param poContinuedGoal
//     */
//    public void addContinuedGoalToGoalList(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList, ArrayList<clsWordPresentationMeshSelectableGoal> poContinuedGoalList) {
//        for (clsWordPresentationMeshSelectableGoal oContinuedGoal : poContinuedGoalList) {
//            //Add the goal to the incoming goallist. In this way all goals are handled equally in F26
//            if (poGoalList.contains(oContinuedGoal)==false && oContinuedGoal.isNullObject()==false) {
//                poGoalList.add(oContinuedGoal);
//            }
//        }
//    }
    
//    /**
//     * Map the previous goal with a new goal from the goal list. The new goal is used, but enhanced with info from the previous step. 
//     * 
//     * (wendt)
//     *
//     * @since 27.09.2012 10:22:34
//     *
//     * @param poPreviousGoal
//     * @param poGoalList
//     * @return the previous continued goal or the continued goal from the incoming goallist
//     */
//    private clsWordPresentationMeshSelectableGoal getContinuedGoalFromPreviousGoal(clsWordPresentationMeshSelectableGoal poPreviousGoal, ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) {
//        clsWordPresentationMeshSelectableGoal oResult = clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
//        
//        //Check if goal exists in the goal list
//        ArrayList<clsWordPresentationMeshSelectableGoal> oEquivalentGoalList = clsGoalManipulationTools.getEquivalentGoalFromGoalList(poGoalList, poPreviousGoal);
//        
//        //If the goal could not be found
//        if (oEquivalentGoalList.isEmpty()==true) {
//            //--- COPY PREVIOUS GOAL ---//
//            clsWordPresentationMeshSelectableGoal oNewGoalFromPrevious = clsGoalManipulationTools.copyGoalWithoutTaskStatusAndAction(poPreviousGoal);
//            
//            oResult = oNewGoalFromPrevious;  
//
//        } else {
//            //Assign the right spatially nearest goal from the previous goal if the goal is from the perception
//            //eCondition oPreviousGoalType = poPreviousGoal.getc.getGoalType();
//            
//            if (poPreviousGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)==true) {
//                oResult = GoalArrangementTools.getSpatiallyNearestGoalFromPerception(oEquivalentGoalList, poPreviousGoal);
//            } else {
//                oResult = oEquivalentGoalList.get(0);   //drive or memory is always present
//            }
//            
//            //Remove all conditions, in order not to use the init conditions on continued goals
//            oResult.removeAllConditions();
//            
//        }
//        
//        //This method sets the condition for the goal type from reading the goal.
//        try {
//            clsGoalAlgorithmTools.setConditionFromGoalType(oResult);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//
//        
//        return oResult;
//    }
    

}
