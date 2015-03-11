/**
 * CHANGELOG
 *
 * 27.09.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionmaking;

import java.util.ArrayList;

import logger.clsLogger;
import memorymgmt.enums.eAction;
import memorymgmt.enums.eCondition;
import memorymgmt.enums.eContent;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eGoalType;
import memorymgmt.shorttermmemory.clsShortTermMemory;

import org.slf4j.Logger;

import base.datatypes.clsAct;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshAimOfDrive;
import base.datatypes.clsWordPresentationMeshFeeling;
import base.datatypes.clsWordPresentationMeshGoal;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.datatypes.helpstructures.clsPair;
import secondaryprocess.algorithm.aimofdrives.clsAimOfDrivesTools;
import secondaryprocess.algorithm.feelings.FeelingAlgorithmTools;
import secondaryprocess.algorithm.goals.GoalAlgorithmTools;
import secondaryprocess.algorithm.goals.GoalGenerationTools;
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
    private static Logger moFeelingLog = clsLogger.getLog("Feelings");
    
    
    
    /**
     * Removes goals, which have been declared as non reachable in the STM
     *
     * @author wendt
     * @since 29.09.2013 13:18:53
     *
     * @param reachableGoalList
     * @param shortTermMemory
     */
    public static ArrayList<clsWordPresentationMeshPossibleGoal> removeNonReachableGoals(ArrayList<clsWordPresentationMeshPossibleGoal> reachableGoalList, clsShortTermMemory shortTermMemory) {
        return GoalAlgorithmTools.removeNonReachableGoals(reachableGoalList, shortTermMemory);
    }
    
    /**
     * Apply the drivedemands on all incoming goals
     *
     * Created by: wendt
     *
     * @since 29.09.2013 12:55:38
     */
    public static void applyDriveDemandsOnReachableGoals(ArrayList<clsWordPresentationMeshPossibleGoal> selectableGoalList, ArrayList<clsWordPresentationMeshAimOfDrive> poDriveDemandList) {
        GoalAlgorithmTools.applyDriveDemandsOnDriveGoal(selectableGoalList, poDriveDemandList);
        
        //ArrayList<clsWordPresentationMeshGoal> oSortedReachableGoalList = clsGoalTools.sortAndEnhanceGoals(selectableGoalList, poDriveDemandList, currentFeelingsList, affectThreashold);
    }
    
    static int mnStep = 0;
    
    
    /**
     * Apply feelings on all incoming goals
     *
     * @author wendt, schaat
     * @since 29.09.2013 13:09:09
     *
     * @param reachableGoalList
     * @param currentFeelingsList
     */
    public static void applyFeelingsOnReachableGoals(ArrayList<clsWordPresentationMeshPossibleGoal> reachableGoalList, ArrayList<clsWordPresentationMeshFeeling> currentFeelings,
            double prFeelingsMatchImpact, boolean activateEmotionalInfluence, double receivedPsychicIntensity) {
        //HOW DO WE INCLUDE HERE THE receivedPsychicIntensity? How does it influence the choice of ordering the goals by a given priority?
        //HOW DO WE REPORT THE QUANTITY OF PSYCHIC INTENSITY USED?
        
        //GoalGenerationTools.TEMP_METHOD_generatePanicGoal(reachableGoalList, currentFeelings, activateEmotionalInfluence);
        
        moFeelingLog.debug("Step {}:", mnStep++);
        moFeelingLog.debug("Current feelings: ");
        /**
         * Every goal has a threshold. The threshold are now hardcoded. It is so arranged that goalsByTriggeredFeeling needs less psychic intensity and
         * goalsByReservedFeeling more than goalsByTriggeredFeeling and goalsByExpectedFeelingThreshold.
         */
        double goalsByTriggeredFeelingThreshold = 0.1;
        double goalsByExpectedFeelingThreshold = 0.3;
        double goalsByReservedFeelingThreshold = 0.5;
        
        receivedPsychicIntensity = 0.35;
        
        for(clsWordPresentationMeshFeeling oFeeling : currentFeelings) {
            moFeelingLog.debug(oFeeling.toString());
        }
        
        for (clsWordPresentationMeshPossibleGoal goal : reachableGoalList) {
           
            if (goal.getFeelings().isEmpty()==false) {
                
                /**
                 * Priorities are compared here so the evaluation of the goal can be chosen accordingly. 
                 */
                
                if(receivedPsychicIntensity >= goalsByTriggeredFeelingThreshold /*&& receivedPsychicIntensity < goalsByExpectedFeelingThreshold*/ ){
                    goal.setFeelingsMatchImportance(prFeelingsMatchImpact * FeelingAlgorithmTools.evaluateGoalByTriggeredFeelings(goal, currentFeelings));                   
                }
                
                if (receivedPsychicIntensity>=goalsByExpectedFeelingThreshold /*&& receivedPsychicIntensity < goalsByReservedFeelingThreshold*/ ){
                    goal.setFeelingsExpactationImportance(prFeelingsMatchImpact * FeelingAlgorithmTools.evaluateGoalByExpectedFeelings(goal, currentFeelings));
                }
                
                if (receivedPsychicIntensity>=goalsByReservedFeelingThreshold) {
                    goal.setFeelingsMatchImportance(prFeelingsMatchImpact * FeelingAlgorithmTools.evaluateGoalByReservedFeelings(goal, currentFeelings));
                }
           }
        }
        
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
    public static void applySocialRulesOnReachableGoals(ArrayList<clsWordPresentationMeshPossibleGoal> reachableGoalList, ArrayList<clsAct> poRuleList) {
        //TODO FG and/or SSch!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
    
    /**
     * The available reachable goals, may have aims (like EAT or DIVIDE) associated that might, or might not, fit the aims originally spezified in the drives.
     * for example:
     *    in primary process: a drive LIBIDINOUS/STOMACH might have an object CAKE associated and an action EAT
     *                        then the super ego forces the action to be replaced by DVIDE
     *    in secondary process: two acts are extracted from memory that might be able to satisfy the drive: one about hitting the cake, the other one about dividing it
     *                        the act that contains information about dividing the cake should be preferred, since it corresponds with the original drive aim (DIVID)  
     *                        from the primary process.
     * 
     * To achieve this, the function iterates over all reachable goals and, if the goal is associated to an act in memory, tries to extract the action associated with
     * that act.
     * 
     * If an action is found, it is compared to the action associated to the aim of drive it is supposed to satisfy (e.g. LIBIDINOUSESTOMACH)
     * 
     * If no action is found, the importance is not changed
     * 
     * @author kollmann
     * @since 15.10.2013 00:00:00
     *
     * @param poReachableGoals list of reachable goals (coming from perception or memory)
     * @param poAimOfDrives list of relevant drive aims
     */
    public static void applyAimImportanceOnReachableGoals(ArrayList<clsWordPresentationMeshPossibleGoal> poReachableGoals, ArrayList<clsWordPresentationMeshAimOfDrive> poAimOfDrives, double prGoalImpact) {
        double nImportance;
        String oGoalName;
        eAction oAction;
        
        for(clsWordPresentationMeshPossibleGoal oGoal : poReachableGoals) {
            oGoalName = oGoal.getGoalName();
            oAction = clsAimOfDrivesTools.getAimOfDriveActionByName(poAimOfDrives, oGoalName);
            nImportance = GoalAlgorithmTools.calucateAimImportance(oGoal, oAction, prGoalImpact);
            oGoal.setDriveAimImportance(nImportance);
        }
    }
    
    
    /**
     * Select the goals with the highest importance for further processing. This function is intended to be used in F26
     *
     * @author wendt
     * @since 29.09.2013 13:09:44
     *
     * @param reachableGoalList
     */
    public static ArrayList<clsWordPresentationMeshPossibleGoal> selectSuitableReachableGoals(ArrayList<clsWordPresentationMeshPossibleGoal> reachableGoalList, int numberOfGoalsToPass) {
        ArrayList<clsWordPresentationMeshPossibleGoal> result = clsGoalManipulationTools.sortAndFilterGoalsByTotalImportance(reachableGoalList, numberOfGoalsToPass);
        
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
    public static clsWordPresentationMeshPossibleGoal selectPlanGoal(ArrayList<clsWordPresentationMeshPossibleGoal> reachableGoalList) {
        ArrayList<clsWordPresentationMeshPossibleGoal> resultList = clsGoalManipulationTools.sortAndFilterGoalsByTotalImportance(reachableGoalList, 1);
        
        clsWordPresentationMeshPossibleGoal result = clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
        
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
                    oReachableGoal.getGoalSource() == eGoalType.PERCEPTIONALEMOTION &&
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
    public static ArrayList<clsWordPresentationMeshPossibleGoal> extractSelectableGoalsFromActs(ArrayList<clsWordPresentationMesh> moActList) {
        ArrayList<clsWordPresentationMeshPossibleGoal> oRetVal = new ArrayList<clsWordPresentationMeshPossibleGoal>();
    
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
    public static ArrayList<clsWordPresentationMeshPossibleGoal> extractSelectableGoalsFromPerception(clsWordPresentationMesh poImage) {
        return clsGoalManipulationTools.extractPossibleGoalsFromPerception(poImage);
        
    }
    
    /**
     * Extract selectable goals from aim of drives
     *
     * @author wendt
     * @since 05.10.2013 14:15:02
     *
     * @param aimOfDrives
     * @return
     */
    public static ArrayList<clsWordPresentationMeshPossibleGoal> extractSelectableGoalsFromAimOfDrives(ArrayList<clsWordPresentationMeshAimOfDrive> aimOfDrives) {
        ArrayList<clsWordPresentationMeshPossibleGoal> result = new ArrayList<clsWordPresentationMeshPossibleGoal>();
        
        for (clsWordPresentationMeshAimOfDrive aimOfDrive : aimOfDrives) {
            result.add(GoalGenerationTools.createDriveSourceGoal(aimOfDrive));
        }
        
        return result;
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

    /**
     * DOCUMENT - insert description
     *
     * @author hinterleitner
     * @since 20.10.2013 18:03:16
     *
     * @param moAssociatedMemories_IN
     * @return 
     * @return
     */
   
    @SuppressWarnings("deprecation")
    public static ArrayList<clsWordPresentationMeshGoal> extractGoalFromContext(ArrayList<clsWordPresentationMeshPossibleGoal> moReachableGoalList_OUT) {
ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
        
        for (clsWordPresentationMeshGoal oAct : moReachableGoalList_OUT) {
            if (oAct.getInternalAssociatedContent().toString().contains("A07_SPEAK_EAT_L01")) {
                oAct.setCondition(eCondition.IS_CONTEXT_SOURCE);
               
            } 
        
       
      
    }
        return oRetVal;
    }



}

