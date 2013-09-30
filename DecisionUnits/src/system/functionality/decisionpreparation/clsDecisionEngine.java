/**
 * CHANGELOG
 *
 * 23.05.2013 wendt - File created
 *
 */
package system.functionality.decisionpreparation;

import java.util.ArrayList;









import org.slf4j.Logger;

import logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.tools.ElementNotFoundException;
import system.algorithm.goals.clsDecisionPreparationTools;
import system.datamanipulation.clsGoalTools;
import system.datamanipulation.clsMentalSituationTools;
import system.functionality.decisionpreparation.actioncodeletes.clsAC_EXECUTE_EXTERNAL_ACTION;
import system.functionality.decisionpreparation.actioncodeletes.clsAC_FLEE;
import system.functionality.decisionpreparation.actioncodeletes.clsAC_FOCUS_MOVEMENT;
import system.functionality.decisionpreparation.actioncodeletes.clsAC_FOCUS_ON;
import system.functionality.decisionpreparation.actioncodeletes.clsAC_PERFORM_BASIC_ACT_ANALYSIS;
import system.functionality.decisionpreparation.actioncodeletes.clsAC_SEND_TO_PHANTASY;
import system.functionality.decisionpreparation.consequencecodelets.clsCC_EXECUTE_MOVEMENT;
import system.functionality.decisionpreparation.consequencecodelets.clsCC_FOCUS_MOVEMENT;
import system.functionality.decisionpreparation.consequencecodelets.clsCC_FOCUS_ON;
import system.functionality.decisionpreparation.consequencecodelets.clsCC_PERFORM_BASIC_ACT_ANALYSIS;
import system.functionality.decisionpreparation.consequencecodelets.clsCC_SEND_TO_PHANTASY;
import system.functionality.decisionpreparation.decisioncodelets.clsDCComposed_Goto;
import system.functionality.decisionpreparation.decisioncodelets.clsDC_ActAnalysisToRecAction;
import system.functionality.decisionpreparation.decisioncodelets.clsDC_ExeMovementToNull;
import system.functionality.decisionpreparation.decisioncodelets.clsDC_InitAction;
import system.functionality.decisionpreparation.decisioncodelets.clsDC_SET_NEED_MOVEMENT_FOCUS;
import system.functionality.decisionpreparation.decisioncodelets.clsDC_SetIntInfoToActAnalysis;
import system.functionality.decisionpreparation.decisioncodelets.clsDC_XToMoveFocus;
import system.functionality.decisionpreparation.initcodelets.clsIC_CheckSetFocus;
import system.functionality.decisionpreparation.initcodelets.clsIC_GetContinuedGoal;
import system.functionality.decisionpreparation.initcodelets.clsIC_InitContinuedGoalAct;
import system.functionality.decisionpreparation.initcodelets.clsIC_InitContinuedGoalDrive;
import system.functionality.decisionpreparation.initcodelets.clsIC_InitContinuedGoalPerception;
import system.functionality.decisionpreparation.initcodelets.clsIC_InitUnprocessedAct;
import system.functionality.decisionpreparation.initcodelets.clsIC_InitUnprocessedDrive;
import system.functionality.decisionpreparation.initcodelets.clsIC_InitUnprocessedPerception;

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
    public void initIncomingGoals(ArrayList<clsWordPresentationMeshGoal> poGoalList) throws Exception {
        
        for (clsWordPresentationMeshGoal oGoal : poGoalList) {
            initIncomingGoal(oGoal);
        }
    }
    
    public void initIncomingGoal(clsWordPresentationMeshGoal poGoal) throws Exception {
        //Set the correct goal type as condition
        try {
            clsDecisionPreparationTools.setConditionFromGoalType(poGoal);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        
        //Add condition that they are new incoming goals
        poGoal.setCondition(eCondition.IS_UNPROCESSED_GOAL);
        
        //Execute matching codelets
        this.moCodeletHandler.executeMatchingCodelets(this, poGoal, eCodeletType.INIT, -1);
    }
    
    public clsWordPresentationMeshGoal initContinuedGoal(ArrayList<clsWordPresentationMeshGoal> poGoalList, clsShortTermMemory poSTM) throws ElementNotFoundException {
        
        clsWordPresentationMeshGoal oContinuedGoal = clsGoalTools.getNullObjectWPM();
        
        //--- GET PREVIOUS MENTAL SITUATION ---//
        clsWordPresentationMesh oPreviousMentalSituation = poSTM.findPreviousSingleMemory();
        //Get the previous goal
        clsWordPresentationMeshGoal oPreviousGoal = clsMentalSituationTools.getGoal(oPreviousMentalSituation);
        log.debug("Previous goal from STM: " + oPreviousGoal);
        
//        try {
//            oContinuedGoal = (clsWordPresentationMeshGoal) oPreviousGoal.clone();
//        } catch (CloneNotSupportedException e) {
//            // TODO (wendt) - Auto-generated catch block
//            e.printStackTrace();
//        }
        
        //Get the new continued goal
        //this.moCodeletHandler.executeMatchingCodelets(this, oContinuedGoal, eCodeletType.INIT, 1);
        
        if (oPreviousGoal.checkIfConditionExists(eCondition.IS_CONTINUED_GOAL)==true) {
            oContinuedGoal = getContinuedGoalFromPreviousGoal(oPreviousGoal, poGoalList);
            if (oContinuedGoal.isNullObject()==false) {
                //Goal type is the only condition set
                
                //Set new continued goal
                oContinuedGoal.setCondition(eCondition.IS_CONTINUED_GOAL);
                
                //Set condition is unprocessed, in order to process the continued goal
                oContinuedGoal.setCondition(eCondition.IS_UNPROCESSED_GOAL);
            }
        }
        
        

        
//        //Get the current incoming goal, which correpsonds to the last goal
//        clsWordPresentationMeshGoal oResult = clsDecisionPreparationTools.getContinuedGoal(poSTM, poGoalList);
//        if (oResult.isNullObject()==false) {
//            
//            //Set new continued goal
//            oResult.setCondition(eCondition.IS_CONTINUED_GOAL);
//        }
        
        log.trace("Continued goal: " + oContinuedGoal);
        
        //Apply init codelets on the continued goal
        this.moCodeletHandler.executeMatchingCodelets(this, oContinuedGoal, eCodeletType.INIT, 1);
        
        // --- APPEND PREVIOUS PERFORMED ACTIONS AS CONDITIONS --- //
        clsDecisionPreparationTools.appendPreviousActionsAsPreconditions(oContinuedGoal, poSTM);
        
        log.debug("Continued goal:" + oContinuedGoal.toString());
        
        return oContinuedGoal;
        
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
     * @return the previous continued goal or the continued goal from the incoming goallist
     */
    private clsWordPresentationMeshGoal getContinuedGoalFromPreviousGoal(clsWordPresentationMeshGoal poPreviousGoal, ArrayList<clsWordPresentationMeshGoal> poGoalList) {
        clsWordPresentationMeshGoal oResult = clsGoalTools.getNullObjectWPM();
        
        //Check if goal exists in the goal list
        ArrayList<clsWordPresentationMeshGoal> oEquivalentGoalList = clsGoalTools.getEquivalentGoalFromGoalList(poGoalList, poPreviousGoal);
        
        //If the goal could not be found
        if (oEquivalentGoalList.isEmpty()==true) {
            //--- COPY PREVIOUS GOAL ---//
            if (poPreviousGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)==false) {
                clsWordPresentationMeshGoal oNewGoalFromPrevious = clsGoalTools.copyGoalWithoutTaskStatusAndAction(poPreviousGoal);
                
                oResult = oNewGoalFromPrevious;  
            }

        } else {
            //Assign the right spatially nearest goal from the previous goal if the goal is from the perception
            //eCondition oPreviousGoalType = poPreviousGoal.getc.getGoalType();
            
            if (poPreviousGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)==true) {
                oResult = clsGoalTools.getSpatiallyNearestGoalFromPerception(oEquivalentGoalList, poPreviousGoal);
            } else {
                oResult = oEquivalentGoalList.get(0);   //drive or memory is always present
            }
            
            //Remove all conditions, in order not to use the init conditions on continued goals
            oResult.removeAllConditions();
            
        }
        
        //This method sets the condition for the goal type from reading the goal.
        try {
            clsDecisionPreparationTools.setConditionFromGoalType(oResult);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        
        return oResult;
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
    public void addContinuedGoalToGoalList(ArrayList<clsWordPresentationMeshGoal> poGoalList, clsWordPresentationMeshGoal poContinuedGoal) {
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
    public void analyzeContinuedGoal(clsWordPresentationMeshGoal poContinuedGoal) {
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
    public void generatePlan(clsWordPresentationMeshGoal poContinuedGoal) {
        //Execute codelets, which decide what the next action in F52 will be
        this.moCodeletHandler.executeMatchingCodelets(this, poContinuedGoal, eCodeletType.DECISION, 1);
        
        //TODO AW: Execute Actionplanning here 
        
        
        log.debug("New decision, goal:" + poContinuedGoal.toString());
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
