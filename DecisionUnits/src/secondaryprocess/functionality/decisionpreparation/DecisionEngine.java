/**
 * CHANGELOG
 *
 * 23.05.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation;

import java.util.ArrayList;

import org.slf4j.Logger;

import logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshMentalSituation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.tools.ElementNotFoundException;
import secondaryprocess.functionality.decisionpreparation.GoalInitiationProcessor.GoalInitiatorInterface;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_EXECUTE_EXTERNAL_ACTION;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_FLEE;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_FOCUS_MOVEMENT;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_FOCUS_ON;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_PERFORM_BASIC_ACT_ANALYSIS;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_SEND_TO_PHANTASY;
import secondaryprocess.functionality.decisionpreparation.consequencecodelets.clsCC_EXECUTE_MOVEMENT;
import secondaryprocess.functionality.decisionpreparation.consequencecodelets.clsCC_EXECUTE_STATIC_ACTION;
import secondaryprocess.functionality.decisionpreparation.consequencecodelets.clsCC_FOCUS_MOVEMENT;
import secondaryprocess.functionality.decisionpreparation.consequencecodelets.clsCC_FOCUS_ON;
import secondaryprocess.functionality.decisionpreparation.consequencecodelets.clsCC_PERFORM_BASIC_ACT_ANALYSIS;
import secondaryprocess.functionality.decisionpreparation.consequencecodelets.clsCC_SEND_TO_PHANTASY;
import secondaryprocess.functionality.decisionpreparation.decisioncodelets.clsDCComposed_Goto;
import secondaryprocess.functionality.decisionpreparation.decisioncodelets.clsDC_ActAnalysisToRecAction;
import secondaryprocess.functionality.decisionpreparation.decisioncodelets.clsDC_ExeMovementToNull;
import secondaryprocess.functionality.decisionpreparation.decisioncodelets.clsDC_InitAction;
import secondaryprocess.functionality.decisionpreparation.decisioncodelets.clsDC_SET_NEED_MOVEMENT_FOCUS;
import secondaryprocess.functionality.decisionpreparation.decisioncodelets.clsDC_SetIntInfoToActAnalysis;
import secondaryprocess.functionality.decisionpreparation.decisioncodelets.clsDC_XToMoveFocus;
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsIC_ApplyPreviousActionOnPlanGoal;
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsIC_CheckStillSetFocus;
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsIC_InitContinuedGoalAct;
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsIC_InitContinuedGoalDrive;
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsIC_InitContinuedGoalPerception;
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsIC_InitUnprocessedAct;
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsIC_InitUnprocessedDrive;
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsIC_InitUnprocessedPerception;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.05.2013, 22:15:05
 * 
 */
public class DecisionEngine implements DecisionEngineInterface {
    
    private final clsCodeletHandler moCodeletHandler;
    private static Logger log = clsLogger.getLog("DecisionPreparation");
    private final GoalInitiatorInterface goalInitiator;
    private final clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm;

    public DecisionEngine(clsEnvironmentalImageMemory poEnvironmentalImageStorage, clsShortTermMemory<clsWordPresentationMeshMentalSituation> poShortTimeMemory, GoalInitiatorInterface goalInitiator) {
        //Init codelethandler
        stm = poShortTimeMemory;
        moCodeletHandler = new clsCodeletHandler(poEnvironmentalImageStorage, poShortTimeMemory);
        
        //Register codelets
        this.registerCodelets();
        
        this.goalInitiator = goalInitiator;
    }
    
    @Override
    public ArrayList<clsWordPresentationMeshSelectableGoal> initialzeGoals(ArrayList<clsWordPresentationMeshSelectableGoal> incomingGoals) {
        
        return this.goalInitiator.initiateIncomingGoals(incomingGoals, this.stm.findPreviousSingleMemory());
    }
    
    
//    /**
//     * Init all new goals 
//     * 
//     * (wendt)
//     *
//     * @since 23.05.2013 22:16:30
//     *
//     * @param poGoalList
//     * @throws Exception 
//     */
//    public void initIncomingGoals(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) throws Exception {
//        
//        for (clsWordPresentationMeshSelectableGoal oGoal : poGoalList) {
//            initIncomingGoal(oGoal);
//        }
//    }
//    
//    /**
//     * DOCUMENT - insert description
//     *
//     * @author wendt
//     * @since 15.10.2013 14:50:52
//     *
//     * @param poGoal
//     * @throws Exception
//     */
//    public void initIncomingGoal(clsWordPresentationMeshSelectableGoal poGoal) throws Exception {
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
    
//    private void appendPreviousActionsAsPreconditionsOnPlanGoal(clsShortTermMemory<clsWordPresentationMeshMentalSituation> poSTM, clsWordPresentationMeshSelectableGoal poContinuedGoal) {
//        // --- APPEND PREVIOUS PERFORMED ACTIONS AS CONDITIONS --- //
//        clsGoalAlgorithmTools.appendPreviousActionsAsPreconditions(poContinuedGoal, poSTM);
//    }
    
    
    
    /**
     * This method is used to declare a continued goal. This is the goal, which is decided to be followed. With this condition
     * in the next step it is clear what is the old continued goal.
     * 
     * (wendt)
     *
     * @since 28.05.2013 23:30:56
     *
     * @param poGoal
     * @throws ElementNotFoundException 
     */
    @Override
    public void declareGoalAsContinued(clsWordPresentationMeshGoal poGoal) throws ElementNotFoundException {
        //Set new continued goal
        try {
            poGoal.removeCondition(eCondition.IS_NEW_GOAL);
        } catch (ElementNotFoundException e) {
            log.error("Cannot remove condition {}", poGoal, e);
            throw new ElementNotFoundException(e.getMessage());
        }
        poGoal.setCondition(eCondition.IS_CONTINUED_GOAL);
    }
    
    /* (non-Javadoc)
    *
    * @since 16.10.2013 13:06:11
    * 
    * @see secondaryprocess.functionality.decisionpreparation.DecisionEngineInterface#declareGoalAsPlanGoal(java.util.ArrayList)
    */
   @Override
   public void declareGoalAsContinuedGoal(ArrayList<clsWordPresentationMeshSelectableGoal> moDecidedGoalList_OUT) throws Exception {
       for (clsWordPresentationMeshGoal g: moDecidedGoalList_OUT) {
           try {
               this.declareGoalAsContinued(g);
           } catch (Exception e) {
               throw new Exception(e.getMessage());
           }    
           
       }
       
   }
    
    /**
     * From a list of goals, remove all continued goal conditions
     *
     * @author wendt
     * @since 16.10.2013 12:06:28
     *
     * @param goalList
     * @throws ElementNotFoundException
     */
    @Override
    public void removeGoalAsContinued(ArrayList<clsWordPresentationMeshGoal> goalList) throws ElementNotFoundException {
        for (clsWordPresentationMeshGoal goal : goalList) {
            try {
                goal.removeCondition(eCondition.IS_CONTINUED_GOAL);
            } catch (ElementNotFoundException e) {
                log.error("Could not remove condition from goal {}", goal, e);
                throw new ElementNotFoundException(e.getMessage());
            }
        }
    }
    
    /**
     * Set this for the selected plan goal
     *
     * @author wendt
     * @since 15.10.2013 21:08:32
     *
     * @param poGoal
     * @throws Exception 
     */
    @Override
    public void declareGoalAsPlanGoal(clsWordPresentationMeshSelectableGoal poGoal) throws Exception {
        //Check if the goal is continued
        if (poGoal.checkIfConditionExists(eCondition.IS_CONTINUED_GOAL)==true) {
            poGoal.setCondition(eCondition.IS_CONTINUED_PLANGOAL);
        } else {
            log.error("Cannot set this goal as plan goal if it has not been selected yet");
            throw new Exception("Cannot set this goal as plan goal if it has not been selected yet");
        }
        
    }
    
    /**
     * From a list of goals, remove all plangoal conditions
     *
     * @author wendt
     * @since 16.10.2013 12:06:09
     *
     * @param moSelectableGoals
     * @throws ElementNotFoundException
     */
    @Override
    public void removeGoalAsPlanGoal(ArrayList<clsWordPresentationMeshSelectableGoal> moSelectableGoals) throws ElementNotFoundException {
        for (clsWordPresentationMeshGoal goal : moSelectableGoals) {
            try {
                goal.removeCondition(eCondition.IS_CONTINUED_PLANGOAL);
            } catch (ElementNotFoundException e) {
                log.error("Could not remove condition from goal {}", goal, e);
                throw new ElementNotFoundException(e.getMessage());
            }
        }
    }
    
    /* (non-Javadoc)
    *
    * @since 16.10.2013 12:50:57
    * 
    * @see secondaryprocess.functionality.decisionpreparation.DecisionEngineInterface#getContinuedGoals(java.util.ArrayList)
    */
   @Override
   public ArrayList<clsWordPresentationMeshSelectableGoal> getContinuedGoals(ArrayList<clsWordPresentationMeshSelectableGoal> goalList) {
       ArrayList<clsWordPresentationMeshSelectableGoal> result = new ArrayList<clsWordPresentationMeshSelectableGoal>();
       for (clsWordPresentationMeshSelectableGoal goal : goalList) {
           if (goal.checkIfConditionExists(eCondition.IS_CONTINUED_GOAL)==true) {
               result.add(goal);
           }
       }

       return result;
   }

   /* (non-Javadoc)
    *
    * @since 16.10.2013 12:50:57
    * 
    * @see secondaryprocess.functionality.decisionpreparation.DecisionEngineInterface#getPlanGoal(java.util.ArrayList)
    */
   @Override
   public clsWordPresentationMeshSelectableGoal getPlanGoal(ArrayList<clsWordPresentationMeshSelectableGoal> goalList) {
       clsWordPresentationMeshSelectableGoal result = clsWordPresentationMeshSelectableGoal.getNullObject();
       for (clsWordPresentationMeshSelectableGoal goal : goalList) {
           if (goal.checkIfConditionExists(eCondition.IS_CONTINUED_PLANGOAL)==true) {
               result =goal;
               break;
           }
       }
       
       return result;
   }
    
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
    
    /**
     * Run init codelets
     *
     * @author wendt
     * @since 16.10.2013 12:08:28
     *
     * @param poGoal
     */
    @Override
    public void setInitialSettings(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) {
        for (clsWordPresentationMeshSelectableGoal goal: poGoalList) {
            this.moCodeletHandler.executeMatchingCodelets(this, goal, eCodeletType.INIT, -1);
        }
        
    }
    
    /**
     * Execute consequence codelets
     * 
     * (wendt)
     *
     * @since 29.05.2013 00:10:20
     *
     * @param poContinuedGoal
     */
    @Override
    public void applyConsequencesOfActionOnContinuedGoal(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) {
        for (clsWordPresentationMeshSelectableGoal goal: poGoalList) {
            this.moCodeletHandler.executeMatchingCodelets(this, goal, eCodeletType.CONSEQUENCE, -1);
            log.debug("Append consequence, goal:" + goal.toString());
        }
    }
    
    /**
     * Execute Decisioncodelets
     * 
     * (wendt)
     *
     * @since 29.05.2013 00:10:35
     *
     * @param poContinuedGoal
     */
    @Override
    public void generateDecision(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) {
        //Execute codelets, which decide what the next action in F52 will be
        for (clsWordPresentationMeshSelectableGoal goal: poGoalList) {
            this.moCodeletHandler.executeMatchingCodelets(this, goal, eCodeletType.DECISION, 1);       
            log.debug("New decision, goal:" + goal.toString());
        }
    }
    
    /**
     * Generate plan based on decision conditions
     *
     * @author wendt
     * @since 02.10.2013 11:50:48
     *
     * @param poContinuedGoal
     */
    @Override
    public void generatePlanFromDecision(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) {
        for (clsWordPresentationMeshSelectableGoal goal: poGoalList) {
            this.getCodeletHandler().executeMatchingCodelets(this, goal, eCodeletType.ACTION, 1);
            log.debug("New action, goal:" + goal.toString());
        }   
    }
    
    /**
     * Register all codelets of the system to the decision engine
     * 
     * (wendt)
     *
     * @since 29.05.2013 00:11:03
     *
     */
    private void registerCodelets() {
        //Decision codelets
        clsIC_CheckStillSetFocus oCheckFocus = new clsIC_CheckStillSetFocus(moCodeletHandler);
        clsIC_InitContinuedGoalAct oContinousAnalysis = new clsIC_InitContinuedGoalAct(moCodeletHandler);
        clsIC_InitContinuedGoalDrive oContinousAnalysisDrive = new clsIC_InitContinuedGoalDrive(moCodeletHandler);
        clsIC_InitContinuedGoalPerception oContinousAnalysisPerception = new clsIC_InitContinuedGoalPerception(moCodeletHandler);
        clsIC_InitUnprocessedAct oInitUnprocessedAct = new clsIC_InitUnprocessedAct(moCodeletHandler);
        clsIC_InitUnprocessedDrive oInitUnprocessedDrive = new clsIC_InitUnprocessedDrive(moCodeletHandler);
        clsIC_InitUnprocessedPerception oInitUnprocessedPerception = new clsIC_InitUnprocessedPerception(moCodeletHandler);
        clsIC_ApplyPreviousActionOnPlanGoal applyPreviousGoalCodelet = new clsIC_ApplyPreviousActionOnPlanGoal(moCodeletHandler);
        
        //Consequence codelets
        clsCC_EXECUTE_STATIC_ACTION oDCStaticAction = new clsCC_EXECUTE_STATIC_ACTION(moCodeletHandler);
        clsCC_EXECUTE_MOVEMENT oDCActionMovement = new clsCC_EXECUTE_MOVEMENT(moCodeletHandler);
        clsCC_FOCUS_MOVEMENT oDCActionFocusMovement = new clsCC_FOCUS_MOVEMENT(moCodeletHandler);
        clsCC_FOCUS_ON oDCActionFocusOn = new clsCC_FOCUS_ON(moCodeletHandler);
        clsCC_PERFORM_BASIC_ACT_ANALYSIS oDCActionPerformBasicActAnalysis = new clsCC_PERFORM_BASIC_ACT_ANALYSIS(moCodeletHandler);
        clsCC_SEND_TO_PHANTASY oDCActionSendToPhantasy = new clsCC_SEND_TO_PHANTASY(moCodeletHandler);
        
        clsDC_ExeMovementToNull oDCTrans_ExeMovementToNull = new clsDC_ExeMovementToNull(moCodeletHandler);
        clsDC_ActAnalysisToRecAction oDCTrans_ActAnalysisToRecAction = new clsDC_ActAnalysisToRecAction(moCodeletHandler);
        clsDC_XToMoveFocus oDCTrans_FocusToMove = new clsDC_XToMoveFocus(moCodeletHandler);
        clsDC_SetIntInfoToActAnalysis oDCTrans_IntInfoToActAnalysis = new clsDC_SetIntInfoToActAnalysis(moCodeletHandler);
        clsDC_SET_NEED_MOVEMENT_FOCUS oDCTrans_SET_NEED_FOCUS = new clsDC_SET_NEED_MOVEMENT_FOCUS(moCodeletHandler);
        clsDC_InitAction oDCTrans_InitAction = new clsDC_InitAction(moCodeletHandler);

        clsDCComposed_Goto oDCComposed_Goto = new clsDCComposed_Goto(moCodeletHandler);
        
        //Action codelets
        clsAC_EXECUTE_EXTERNAL_ACTION oACExecuteExternalAction = new clsAC_EXECUTE_EXTERNAL_ACTION(moCodeletHandler);
        clsAC_FLEE oACFlee = new clsAC_FLEE(moCodeletHandler);
        clsAC_FOCUS_MOVEMENT oACFocusMovement = new clsAC_FOCUS_MOVEMENT(moCodeletHandler);
        clsAC_FOCUS_ON oACFocuOn = new clsAC_FOCUS_ON(moCodeletHandler);
        clsAC_PERFORM_BASIC_ACT_ANALYSIS oACPerformBasicActAnalysis = new clsAC_PERFORM_BASIC_ACT_ANALYSIS(moCodeletHandler);
        clsAC_SEND_TO_PHANTASY oACSendToPhantasy = new clsAC_SEND_TO_PHANTASY(moCodeletHandler);

    }
    
    /**
     * @since 23.05.2013 23:17:40
     * 
     * @return the moCodeletHandler
     */
    private clsCodeletHandler getCodeletHandler() {
        return moCodeletHandler;
    }


}
