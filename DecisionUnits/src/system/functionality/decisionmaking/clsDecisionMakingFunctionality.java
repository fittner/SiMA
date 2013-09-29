/**
 * CHANGELOG
 *
 * 27.09.2013 wendt - File created
 *
 */
package system.functionality.decisionmaking;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import system.algorithm.goals.clsDecisionPreparationTools;
import system.datamanipulation.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2013, 21:26:28
 * 
 */
public class clsDecisionMakingFunctionality {
    private static Logger log = clsLogger.getLog("Tools");
    
    /**
     * DOCUMENT - insert description
     *
     * Created by: wendt
     *
     * @since 29.09.2013 12:55:38
     */
    public static void applyDriveDemandsOnGoals(ArrayList<clsWordPresentationMeshGoal> goalWPMList) {
        
    }
    
    public static void applyFeelingsOnGoals() {
        
    }
    
    public static void selectSuitableGoals() {
        
    }
    
    public static ArrayList<clsWordPresentationMeshGoal> decideDriveDemandGoals(
            ArrayList<clsWordPresentationMeshGoal> poPossibleGoalInputs, 
            ArrayList<clsWordPresentationMeshGoal> poDriveList, 
            ArrayList<clsAct> poRuleList,
            ArrayList<clsWordPresentationMeshFeeling> currentFeelingsList,
            clsShortTermMemory shortTermMemory,
            boolean bActivateEmotionalInfluence,
            int numberOfGoalsToPass,
            double affectThreashold) {
        ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
        
        int nAddedGoals = 0;
        
        //Process emotions
        //TODO SM: Remove this function. Panic goals are generated from an act (BODO-act)
        clsWordPresentationMeshGoal oPanicGoal = clsDecisionPreparationTools.generatePanicGoalFromFeeling(currentFeelingsList);
        //TODO SM: Remove Panicgoal 
        if (oPanicGoal.isNullObject()==false && bActivateEmotionalInfluence==true) {
            oRetVal.add(oPanicGoal);
            log.trace("Added panic goal" + oPanicGoal);
        } else {
            //1. Process goals with Superego???

            //2. Sort the goals to get the most important goal first
            
            //3. Remove unreachable goals direct from the list
            //TODO: Make this fuction like a value funtion and not just remove if precondition
            clsDecisionPreparationTools.removeNonReachableGoals(poPossibleGoalInputs, shortTermMemory);
            
            //=== Sort and evaluate them === //
            //TODO SM: Extend clsWordPresentationMeshGoal with Unique Property HASFEELINGIMPACT, which is eqivalent to HASIMPORTANCE and HASEFFORTLEVEL
            ArrayList<clsWordPresentationMeshGoal> oSortedReachableGoalList = clsGoalTools.sortAndEnhanceGoals(poPossibleGoalInputs, poDriveList, currentFeelingsList, affectThreashold);
            
            //Add all goals to this list
            for (clsWordPresentationMeshGoal oReachableGoal : oSortedReachableGoalList) {
                if (nAddedGoals<numberOfGoalsToPass) {
                    oRetVal.add(oReachableGoal);
                    nAddedGoals++;
                } else {
                    break;
                }

            }
        }
        
        return oRetVal;
    }
}
