/**
 * CHANGELOG
 *
 * 27.09.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionmaking;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import datatypes.helpstructures.clsPair;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshAimOfDrive;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import secondaryprocess.algorithm.goals.GoalGenerationTools;
import secondaryprocess.algorithm.goals.clsGoalAlgorithmTools;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2013, 21:26:28
 * 
 */
public class GoalHandlingFunctionality {
    private static Logger log = clsLogger.getLog("SecondaryProcessFunctionality");
    
    /**
     * Removes goals, which have been declared as non reachable in the STM
     *
     * @author wendt
     * @since 29.09.2013 13:18:53
     *
     * @param reachableGoalList
     * @param shortTermMemory
     */
    public static void removeNonReachableGoals(ArrayList<clsWordPresentationMeshSelectableGoal> reachableGoalList, clsShortTermMemory shortTermMemory) {
        clsGoalAlgorithmTools.removeNonReachableGoals(reachableGoalList, shortTermMemory);
    }
    
    /**
     * Apply the drivedemands on all incoming goals
     *
     * Created by: wendt
     *
     * @since 29.09.2013 12:55:38
     */
    public static void applyDriveDemandsOnReachableGoals(ArrayList<clsWordPresentationMeshSelectableGoal> selectableGoalList, ArrayList<clsWordPresentationMeshAimOfDrive> poDriveDemandList, double thresholdForDriveAimToSelectableGoal) {
        clsGoalAlgorithmTools.applyDriveDemandsOnDriveGoal(selectableGoalList, poDriveDemandList, thresholdForDriveAimToSelectableGoal);
        
        //ArrayList<clsWordPresentationMeshGoal> oSortedReachableGoalList = clsGoalTools.sortAndEnhanceGoals(selectableGoalList, poDriveDemandList, currentFeelingsList, affectThreashold);
    }
    
    /**
     * Apply feelings on all incoming goals
     *
     * @author wendt
     * @since 29.09.2013 13:09:09
     *
     * @param reachableGoalList
     * @param currentFeelingsList
     */
    public static void applyFeelingsOnReachableGoals(ArrayList<clsWordPresentationMeshSelectableGoal> reachableGoalList, ArrayList<clsWordPresentationMeshFeeling> currentFeelings, boolean activateEmotionalInfluence) {
        GoalGenerationTools.TEMP_METHOD_generatePanicGoal(reachableGoalList, currentFeelings, activateEmotionalInfluence);
        
        
    }
    
    /**
     * Apply social rules on reachable goals.
     *
     * @author wendt
     * @since 29.09.2013 13:07:53
     *
     * @param reachableGoalList
     * @param poRuleList
     */
    public static void applySocialRulesOnReachableGoals(ArrayList<clsWordPresentationMeshSelectableGoal> reachableGoalList, ArrayList<clsAct> poRuleList) {
        //TODO FG and/or SSch!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
    
    /**
     * Select the goals with the highest importance for further processing. This function is intended to be used in F26
     *
     * @author wendt
     * @since 29.09.2013 13:09:44
     *
     * @param reachableGoalList
     */
    public static ArrayList<clsWordPresentationMeshSelectableGoal> selectSuitableReachableGoals(ArrayList<clsWordPresentationMeshSelectableGoal> reachableGoalList, int numberOfGoalsToPass) {
        ArrayList<clsWordPresentationMeshSelectableGoal> result = clsGoalManipulationTools.sortAndFilterGoalsByTotalImportance(reachableGoalList, numberOfGoalsToPass);
        
        return result;
    }
    
    /**
     * Select the goal with the highest importance from a list. This function is intended to be used in F29
     *
     * @author wendt
     * @since 29.09.2013 13:09:44
     *
     * @param reachableGoalList
     */
    public static clsWordPresentationMeshSelectableGoal selectPlanGoal(ArrayList<clsWordPresentationMeshSelectableGoal> reachableGoalList) {
        ArrayList<clsWordPresentationMeshSelectableGoal> resultList = clsGoalManipulationTools.sortAndFilterGoalsByTotalImportance(reachableGoalList, 1);
        
        clsWordPresentationMeshSelectableGoal result = clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
        
        if (resultList.size()>0) {
            result = resultList.get(0);
        }
        
        return result;
    }
    
    /**
     * Sort aim of drives in either F26 or in F8
     *
     * @author wendt
     * @since 30.09.2013 21:44:09
     *
     * @param aimOfDrivesList
     * @return
     */
    public static ArrayList<clsWordPresentationMeshAimOfDrive> sortAimOfDrives(ArrayList<clsWordPresentationMeshAimOfDrive> aimOfDrivesList) {
        ArrayList<clsWordPresentationMeshAimOfDrive> result = clsGoalManipulationTools.sortAndFilterGoalsByTotalImportance(aimOfDrivesList, -1);
        
        return result;
    }
    
    /**
     * FIXME: THIS FUNCTION IS NOT USED AT ALL
     * 
     * 
     * Extract goals from the perception, which are emotions and are high negative in order to be 
     * able to react faster.
     * 
     * (wendt)
     *
     * @since 24.06.2012 09:24:38
     *
     * @param poReachableGoalList
     * @return
     */
    private static ArrayList<clsPair<Double, clsWordPresentationMeshGoal>> extractStrongestPerceptiveGoals(ArrayList<clsWordPresentationMeshGoal> poReachableGoalList, double strongestGoalThreshold) {
        ArrayList<clsPair<Double, clsWordPresentationMeshGoal>> oRetVal = new ArrayList<clsPair<Double, clsWordPresentationMeshGoal>>();
        
        for (clsWordPresentationMeshGoal oReachableGoal : poReachableGoalList) {
            
            //React on goals in the perception, which are emotion and are HIGH NEGATIVE
            if (oReachableGoal.getSupportiveDataStructureType() == eContentType.PI && 
                    oReachableGoal.getGoalType() == eGoalType.PERCEPTIONALEMOTION &&
                    oReachableGoal.getTotalImportance() == strongestGoalThreshold) {
                oRetVal.add(new clsPair<Double, clsWordPresentationMeshGoal>(oReachableGoal.getTotalImportance(), oReachableGoal));
            }
        }
        
        return oRetVal;
    }
    
    /**
     * Extract all possible goal from acts from their descriptions
     * 
     * (wendt)
     *
     * @since 25.05.2012 18:52:53
     *
     * @param moActList
     * @return
     */
    public static ArrayList<clsWordPresentationMeshSelectableGoal> extractPossibleGoalsFromActs(ArrayList<clsWordPresentationMesh> moActList) {
        ArrayList<clsWordPresentationMeshSelectableGoal> oRetVal = new ArrayList<clsWordPresentationMeshSelectableGoal>();
    
        for (clsWordPresentationMesh oAct : moActList) {
            oRetVal.addAll(clsGoalManipulationTools.extractPossibleGoalsFromAct(oAct));
        }
        
        return oRetVal;
    }
    
    /**
     * Extract all goals from perception
     *
     * @author wendt
     * @since 01.10.2013 11:51:15
     *
     * @param poImage
     * @return
     */
    public static ArrayList<clsWordPresentationMeshSelectableGoal> extractPossibleGoalsFromPerception(clsWordPresentationMesh poImage) {
        return clsGoalManipulationTools.extractPossibleGoalsFromPerception(poImage);
        
    }
    
    /**
     * FIXME NOT USED
     * 
     * Check all extracted goals for goals, which can emerge from emotions
     * 
     * (wendt)
     *
     * @since 25.05.2012 20:47:31
     *
     * @param poGoalList
     * @return
     */
    private ArrayList<clsWordPresentationMeshGoal> extractEmergentGoalsFromEmotions(ArrayList<clsWordPresentationMeshGoal> poGoalList) {
        ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
        
        for (clsWordPresentationMeshGoal oGoal : poGoalList) {
            if (oGoal.getGoalName().equals(eContent.UNKNOWN_GOAL.toString())) {
                oRetVal.add(oGoal);
            }
        }
        
        return oRetVal;
    }
    

    
//    public static ArrayList<clsWordPresentationMeshGoal> decideDriveDemandGoals(
//            ArrayList<clsWordPresentationMeshGoal> poPossibleGoalInputs, 
//            ArrayList<clsWordPresentationMeshGoal> poDriveList, 
//            ArrayList<clsAct> poRuleList,
//            ArrayList<clsWordPresentationMeshFeeling> currentFeelingsList,
//            clsShortTermMemory shortTermMemory,
//            boolean bActivateEmotionalInfluence,
//            int numberOfGoalsToPass,
//            double affectThreashold) {
//        ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
//        
//        int nAddedGoals = 0;
//        
//        //Process emotions
//        //TODO SM: Remove this function. Panic goals are generated from an act (BODO-act)
//        clsWordPresentationMeshGoal oPanicGoal = clsGoalAlgorithmTools.generatePanicGoalFromFeeling(currentFeelingsList);
//        //TODO SM: Remove Panicgoal 
//        if (oPanicGoal.isNullObject()==false && bActivateEmotionalInfluence==true) {
//            oRetVal.add(oPanicGoal);
//            log.trace("Added panic goal" + oPanicGoal);
//        } else {
//            //1. Process goals with Superego???
//
//            //2. Sort the goals to get the most important goal first
//            
//            //3. Remove unreachable goals direct from the list
//            //TODO: Make this fuction like a value funtion and not just remove if precondition
//            clsGoalAlgorithmTools.removeNonReachableGoals(poPossibleGoalInputs, shortTermMemory);
//            
//            //=== Sort and evaluate them === //
//            //TODO SM: Extend clsWordPresentationMeshGoal with Unique Property HASFEELINGIMPACT, which is eqivalent to HASIMPORTANCE and HASEFFORTLEVEL
//            ArrayList<clsWordPresentationMeshGoal> oSortedReachableGoalList = clsGoalTools.sortAndEnhanceGoals(poPossibleGoalInputs, poDriveList, currentFeelingsList, affectThreashold);
//            
//            //Add all goals to this list
//            for (clsWordPresentationMeshGoal oReachableGoal : oSortedReachableGoalList) {
//                if (nAddedGoals<numberOfGoalsToPass) {
//                    oRetVal.add(oReachableGoal);
//                    nAddedGoals++;
//                } else {
//                    break;
//                }
//
//            }
//        }
//        
//        return oRetVal;
//    }
}