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
import secondaryprocess.algorithm.goals.clsGoalAlgorithmTools;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_EXECUTE_EXTERNAL_ACTION;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_FLEE;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_FOCUS_MOVEMENT;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_FOCUS_ON;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_PERFORM_BASIC_ACT_ANALYSIS;
import secondaryprocess.functionality.decisionpreparation.actioncodeletes.clsAC_SEND_TO_PHANTASY;
import secondaryprocess.functionality.decisionpreparation.consequencecodelets.clsCC_EXECUTE_MOVEMENT;
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
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsIC_CheckSetFocus;
import secondaryprocess.functionality.decisionpreparation.initcodelets.clsIC_GetContinuedGoal;
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
public class clsDecisionEngine {
    
    private final clsCodeletHandler moCodeletHandler;
    private static Logger log = clsLogger.getLog("DecisionPreparation");

    public clsDecisionEngine(clsEnvironmentalImageMemory poEnvironmentalImageStorage, clsShortTermMemory poShortTimeMemory) {
        //Init codelethandler
        moCodeletHandler = new clsCodeletHandler(poEnvironmentalImageStorage, poShortTimeMemory);
        
        //Register codelets
        this.registerCodelets();
    }
    
    
    /**
     * Init all new goals 
     * 
     * (wendt)
     *
     * @since 23.05.2013 22:16:30
     *
     * @param poGoalList
     * @throws Exception 
     */
    public void initIncomingGoals(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) throws Exception {
        
        for (clsWordPresentationMeshSelectableGoal oGoal : poGoalList) {
            initIncomingGoal(oGoal);
        }
    }
    
    public void initIncomingGoal(clsWordPresentationMeshSelectableGoal poGoal) throws Exception {
        //Set the correct goal type as condition
        try {
            clsGoalAlgorithmTools.setConditionFromGoalType(poGoal);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        
        //Add condition that they are new incoming goals
        poGoal.setCondition(eCondition.IS_UNPROCESSED_GOAL);
        
        //Execute matching codelets
        this.moCodeletHandler.executeMatchingCodelets(this, poGoal, eCodeletType.INIT, -1);
    }
    
    public clsWordPresentationMeshSelectableGoal initContinuedGoal(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList, clsShortTermMemory<clsWordPresentationMeshMentalSituation> poSTM) throws ElementNotFoundException {
        
        clsWordPresentationMeshSelectableGoal oContinuedGoal = clsWordPresentationMeshSelectableGoal.getNullObject(); //clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
        
        //--- GET PREVIOUS MENTAL SITUATION ---//
        clsWordPresentationMeshMentalSituation oPreviousMentalSituation = poSTM.findPreviousSingleMemory();
        //Get the previous goal
        clsWordPresentationMeshSelectableGoal oPreviousGoal = oPreviousMentalSituation.getPlanGoal();  //clsMentalSituationTools.getGoal(oPreviousMentalSituation);
        log.debug("Previous goal from STM: " + oPreviousGoal);
        
        if (oPreviousGoal.checkIfConditionExists(eCondition.IS_CONTINUED_GOAL)==true) {
            oContinuedGoal = clsGoalManipulationTools.getContinuedGoalFromPreviousGoal(oPreviousGoal, poGoalList);
            if (oContinuedGoal.isNullObject()==false) {
                //Goal type is the only condition set
                
                //Set new continued goal
                oContinuedGoal.setCondition(eCondition.IS_CONTINUED_GOAL);
                
                //Set condition is unprocessed, in order to process the continued goal
                oContinuedGoal.setCondition(eCondition.IS_UNPROCESSED_GOAL);
            }
        }
        
        log.trace("Continued goal: " + oContinuedGoal);
        
        //Apply init codelets on the continued goal
        this.moCodeletHandler.executeMatchingCodelets(this, oContinuedGoal, eCodeletType.INIT, 1);
        
        // --- APPEND PREVIOUS PERFORMED ACTIONS AS CONDITIONS --- //
        clsGoalAlgorithmTools.appendPreviousActionsAsPreconditions(oContinuedGoal, poSTM);
        
        log.debug("Continued goal:" + oContinuedGoal.toString());
        
        return oContinuedGoal;
        
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
     */
    public void declareGoalAsContinued(clsWordPresentationMeshGoal poGoal) {
        //Set new continued goal
        poGoal.setCondition(eCondition.IS_CONTINUED_GOAL);
    }
    
    /**
     * Add the continued goal to the reachable goals if not already added
     * 
     * (wendt)
     *
     * @since 29.05.2013 00:09:43
     *
     * @param poGoalList
     * @param poContinuedGoal
     */
    public void addContinuedGoalToGoalList(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList, clsWordPresentationMeshSelectableGoal poContinuedGoal) {
        //Add the goal to the incoming goallist. In this way all goals are handled equally in F26
        if (poGoalList.contains(poContinuedGoal)==false && poContinuedGoal.isNullObject()==false) {
            poGoalList.add(poContinuedGoal);
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
    public void applyConsequencesOfActionOnContinuedGoal(clsWordPresentationMeshSelectableGoal poContinuedGoal) {
        this.moCodeletHandler.executeMatchingCodelets(this, poContinuedGoal, eCodeletType.CONSEQUENCE, 1);
        log.debug("Append consequence, goal:" + poContinuedGoal.toString());
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
    public void generateDecision(clsWordPresentationMeshSelectableGoal poContinuedGoal) {
        //Execute codelets, which decide what the next action in F52 will be
        this.moCodeletHandler.executeMatchingCodelets(this, poContinuedGoal, eCodeletType.DECISION, 1);       
        log.debug("New decision, goal:" + poContinuedGoal.toString());
    }
    
    /**
     * Generate plan based on decision conditions
     *
     * @author wendt
     * @since 02.10.2013 11:50:48
     *
     * @param poContinuedGoal
     */
    public void generatePlanFromDecision(clsWordPresentationMeshSelectableGoal poContinuedGoal) {
        this.getCodeletHandler().executeMatchingCodelets(this, poContinuedGoal, eCodeletType.ACTION, 1);
        log.debug("New action, goal:" + poContinuedGoal.toString());
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
        clsIC_CheckSetFocus oCheckFocus = new clsIC_CheckSetFocus(moCodeletHandler);
        clsIC_InitContinuedGoalAct oContinousAnalysis = new clsIC_InitContinuedGoalAct(moCodeletHandler);
        clsIC_InitContinuedGoalDrive oContinousAnalysisDrive = new clsIC_InitContinuedGoalDrive(moCodeletHandler);
        clsIC_InitContinuedGoalPerception oContinousAnalysisPerception = new clsIC_InitContinuedGoalPerception(moCodeletHandler);
        clsIC_InitUnprocessedAct oInitUnprocessedAct = new clsIC_InitUnprocessedAct(moCodeletHandler);
        clsIC_InitUnprocessedDrive oInitUnprocessedDrive = new clsIC_InitUnprocessedDrive(moCodeletHandler);
        clsIC_InitUnprocessedPerception oInitUnprocessedPerception = new clsIC_InitUnprocessedPerception(moCodeletHandler);
        clsIC_GetContinuedGoal oGetContinuedGoal = new clsIC_GetContinuedGoal(moCodeletHandler);
        
        
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
    public clsCodeletHandler getCodeletHandler() {
        return moCodeletHandler;
    }

}
