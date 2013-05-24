/**
 * CHANGELOG
 *
 * 23.05.2013 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import pa._v38.decisionpreparation.actioncodeletes.clsAC_EXECUTE_EXTERNAL_ACTION;
import pa._v38.decisionpreparation.actioncodeletes.clsAC_FLEE;
import pa._v38.decisionpreparation.actioncodeletes.clsAC_FOCUS_MOVEMENT;
import pa._v38.decisionpreparation.actioncodeletes.clsAC_FOCUS_ON;
import pa._v38.decisionpreparation.actioncodeletes.clsAC_PERFORM_BASIC_ACT_ANALYSIS;
import pa._v38.decisionpreparation.actioncodeletes.clsAC_SEND_TO_PHANTASY;
import pa._v38.decisionpreparation.consequencecodelets.clsCC_EXECUTE_MOVEMENT;
import pa._v38.decisionpreparation.consequencecodelets.clsCC_FOCUS_MOVEMENT;
import pa._v38.decisionpreparation.consequencecodelets.clsCC_FOCUS_ON;
import pa._v38.decisionpreparation.consequencecodelets.clsCC_PERFORM_BASIC_ACT_ANALYSIS;
import pa._v38.decisionpreparation.consequencecodelets.clsCC_SEND_TO_PHANTASY;
import pa._v38.decisionpreparation.decisioncodelets.clsDCComposed_Goto;
import pa._v38.decisionpreparation.decisioncodelets.clsDC_ActAnalysisToRecAction;
import pa._v38.decisionpreparation.decisioncodelets.clsDC_ExeMovementToNull;
import pa._v38.decisionpreparation.decisioncodelets.clsDC_InitAction;
import pa._v38.decisionpreparation.decisioncodelets.clsDC_SET_NEED_MOVEMENT_FOCUS;
import pa._v38.decisionpreparation.decisioncodelets.clsDC_SetIntInfoToActAnalysis;
import pa._v38.decisionpreparation.decisioncodelets.clsDC_XToMoveFocus;
import pa._v38.decisionpreparation.initcodelets.clsIC_CheckSetFocus;
import pa._v38.decisionpreparation.initcodelets.clsIC_InitContinuedGoal;
import pa._v38.decisionpreparation.initcodelets.clsIC_InitUnprocessedAct;
import pa._v38.decisionpreparation.initcodelets.clsIC_InitUnprocessedDrive;
import pa._v38.decisionpreparation.initcodelets.clsIC_InitUnprocessedPerception;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.05.2013, 22:15:05
 * 
 */
public class clsDecisionEngine {
    
    private final clsCodeletHandler moCodeletHandler;
    private static Logger log = Logger.getLogger("pa._v38.decisionpreparation");

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
    public void initIncomingGoals(ArrayList<clsWordPresentationMeshGoal> poGoalList) throws Exception {
        
        for (clsWordPresentationMeshGoal oGoal : poGoalList) {
            //Set the correct goal type as condition
            try {
                clsDecisionPreparationTools.setConditionFromGoalType(oGoal);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            
            //Add condition that they are new incoming goals
            oGoal.setCondition(eCondition.IS_UNPROCESSED_GOAL);
            
            //Execute matching codelets
            this.moCodeletHandler.executeMatchingCodelets(this, oGoal, eCodeletType.INIT, -1);
            
            
        }
         
        
        
    }
    
    public clsWordPresentationMeshGoal initContinuedGoal(ArrayList<clsWordPresentationMeshGoal> poGoalList, clsShortTermMemory poSTM) {
        //clsWordPresentationMeshGoal oResult = clsGoalTools.getNullObjectWPM();
        
        clsWordPresentationMeshGoal oResult = clsDecisionPreparationTools.getContinuedGoal(poSTM, poGoalList);
        if (oResult.isNullObject()==false) {
            //Set new continued goal
            oResult.setCondition(eCondition.IS_CONTINUED_GOAL);
        }
        
        
        //Apply init codelets on the continued goal
        this.moCodeletHandler.executeMatchingCodelets(this, oResult, eCodeletType.INIT, -1);
        
        // --- APPEND PREVIOUS PERFORMED ACTIONS AS CONDITIONS --- //
        clsDecisionPreparationTools.appendPreviousActionsAsPreconditions(oResult, poSTM);
        
        log.debug("Continued goal:" + oResult.toString());
        
        return oResult;
        
    }
    
//    public void addEffectOfFeelings(clsWordPresentationMeshGoal poGoal) {
//        
//    }
    
//    public void addEffectOfFeelings(ArrayList<clsWordPresentationMeshGoal> poGoalList) {
//        
//    }
//    
//    public void addEffort(clsWordPresentationMeshGoal poGoal) {
//        
//    }
//    
//    public void addEffort(ArrayList<clsWordPresentationMeshGoal> poGoalList) {
//        
//    }
    
    public void analyzeContinuedGoal(clsWordPresentationMeshGoal poContinuedGoal) {
        this.moCodeletHandler.executeMatchingCodelets(this, poContinuedGoal, eCodeletType.CONSEQUENCE, 1);
        log.debug("Append consequence, goal:" + poContinuedGoal.toString());
    }
    
    public void generatePlan(clsWordPresentationMeshGoal poContinuedGoal) {
        //Execute codelets, which decide what the next action in F52 will be
        this.moCodeletHandler.executeMatchingCodelets(this, poContinuedGoal, eCodeletType.DECISION, 1);
        
        //TODO AW: Execute Actionplanning here 
        
        
        log.debug("New decision, goal:" + poContinuedGoal.toString());
    }
    
    private void registerCodelets() {
        //Decision codelets
        clsIC_CheckSetFocus oCheckFocus = new clsIC_CheckSetFocus(moCodeletHandler);
        clsIC_InitContinuedGoal oContinousAnalysis = new clsIC_InitContinuedGoal(moCodeletHandler);
        clsIC_InitUnprocessedAct oInitUnprocessedAct = new clsIC_InitUnprocessedAct(moCodeletHandler);
        clsIC_InitUnprocessedDrive oInitUnprocessedDrive = new clsIC_InitUnprocessedDrive(moCodeletHandler);
        clsIC_InitUnprocessedPerception oInitUnprocessedPerception = new clsIC_InitUnprocessedPerception(moCodeletHandler);
        
        
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
