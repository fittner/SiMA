/**
 * CHANGELOG
 *
 * 23.05.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation;

import java.util.ArrayList;

import org.slf4j.Logger;

import base.datatypes.clsWordPresentationMeshGoal;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.tools.ElementNotFoundException;
import logger.clsLogger;
import memorymgmt.enums.eCondition;
import memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.storage.DT1_PsychicIntensityBuffer;
import secondaryprocess.functionality.decisionpreparation.GoalInitiationProcessor.GoalInitiatorInterface;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_EXECUTE_EXTERNAL_ACTION;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_FLEE;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_FOCUS_MOVEMENT;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_FOCUS_ON;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_PERFORM_BASIC_ACT_ANALYSIS;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_SEND_TO_PHANTASY;
import secondaryprocess.functionality.decisionpreparation.consequencecodelets.clsCC_END_OF_ACT;
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

    public DecisionEngine(clsEnvironmentalImageMemory poEnvironmentalImageStorage, clsShortTermMemory<clsWordPresentationMeshMentalSituation> poShortTimeMemory, DT1_PsychicIntensityBuffer libidoBuffer, GoalInitiatorInterface goalInitiator) {
        //Init codelethandler
        stm = poShortTimeMemory;
        moCodeletHandler = new clsCodeletHandler(poEnvironmentalImageStorage, poShortTimeMemory, libidoBuffer);
        
        //Register codelets
        this.registerCodelets();
        
        this.goalInitiator = goalInitiator;
    }
    
    @Override
    public ArrayList<clsWordPresentationMeshPossibleGoal> initialzeGoals(ArrayList<clsWordPresentationMeshPossibleGoal> incomingGoals) {
        
        return this.goalInitiator.initiateIncomingGoals(incomingGoals, this.stm.findPreviousSingleMemory());
    }

    
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
   public void declareGoalAsContinuedGoal(ArrayList<clsWordPresentationMeshPossibleGoal> moDecidedGoalList_OUT) throws Exception {
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
    public void declareGoalAsPlanGoal(clsWordPresentationMeshPossibleGoal poGoal) throws Exception {
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
    public void removeGoalAsPlanGoal(ArrayList<clsWordPresentationMeshPossibleGoal> moSelectableGoals) throws ElementNotFoundException {
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
   public ArrayList<clsWordPresentationMeshPossibleGoal> getContinuedGoals(ArrayList<clsWordPresentationMeshPossibleGoal> goalList) {
       ArrayList<clsWordPresentationMeshPossibleGoal> result = new ArrayList<clsWordPresentationMeshPossibleGoal>();
       for (clsWordPresentationMeshPossibleGoal goal : goalList) {
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
   public clsWordPresentationMeshPossibleGoal getPlanGoal(ArrayList<clsWordPresentationMeshPossibleGoal> goalList) {
       clsWordPresentationMeshPossibleGoal result = clsWordPresentationMeshPossibleGoal.getNullObject();
       for (clsWordPresentationMeshPossibleGoal goal : goalList) {
           if (goal.checkIfConditionExists(eCondition.IS_CONTINUED_PLANGOAL)==true) {
               result =goal;
               break;
           }
       }
       
       return result;
   }
    
    /**
     * Run init codelets
     *
     * @author wendt
     * @since 16.10.2013 12:08:28
     *
     * @param poGoal
     */
    @Override
    public void setInitialSettings(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList) {
        for (clsWordPresentationMeshPossibleGoal goal: poGoalList) {
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
    public void applyConsequencesOfActionOnContinuedGoal(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList) {
        for (clsWordPresentationMeshPossibleGoal goal: poGoalList) {
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
    public void generateDecision(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList) {
        //Execute codelets, which decide what the next action in F52 will be
        for (clsWordPresentationMeshPossibleGoal goal: poGoalList) {
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
    public void generatePlanFromDecision(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList) {
        for (clsWordPresentationMeshPossibleGoal goal: poGoalList) {
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
        clsCC_END_OF_ACT oCC_END_OF_ACT = new clsCC_END_OF_ACT(moCodeletHandler);
        
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
