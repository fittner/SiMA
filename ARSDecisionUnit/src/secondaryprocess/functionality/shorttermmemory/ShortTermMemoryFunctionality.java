/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.shorttermmemory;

import java.util.ArrayList;

import logger.clsLogger;
import memorymgmt.enums.eCondition;
import memorymgmt.shorttermmemory.clsShortTermMemory;

import org.slf4j.Logger;

import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshAimOfDrive;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2013, 10:47:51
 * 
 */
public class ShortTermMemoryFunctionality {
    
    private static Logger log = clsLogger.getLog("SecondaryProcessFunctionality");
    
    /**
     * Add a goal to STM
     *
     * @author wendt
     * @since 02.10.2013 13:46:56
     *
     * @param poGoal
     * @param stm
     */
    public static void addContinuedGoalsToMentalSituation(ArrayList<clsWordPresentationMeshPossibleGoal> poContinuedGoals, clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm) {
        //get the ref of the current mental situation
        clsWordPresentationMeshMentalSituation oCurrentMentalSituation = stm.findCurrentSingleMemory();
        
        for (clsWordPresentationMeshPossibleGoal goal : poContinuedGoals) {
            
            oCurrentMentalSituation.addSelectableGoal(goal);
        }

    }
    
    /**
     * Set the current plan goal to STM
     *
     * @author wendt
     * @since 02.10.2013 13:46:56
     *
     * @param poGoal
     * @param stm
     */
    public static void setPlanGoalInMentalSituation(clsWordPresentationMeshPossibleGoal planGoal, clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm) {
        //get the ref of the current mental situation
        clsWordPresentationMeshMentalSituation oCurrentMentalSituation = stm.findCurrentSingleMemory();
        
        oCurrentMentalSituation.setPlanGoal(planGoal);

    }
    
    /**
     * Get the previous plan goal
     *
     * @author wendt
     * @since 03.10.2013 21:22:46
     *
     * @param stm
     * @return
     */
    public static clsWordPresentationMeshPossibleGoal getPlanGoalFromPreviousMentalSituation(clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm) {
        clsWordPresentationMeshMentalSituation oPreviousMentalSituation = stm.findPreviousSingleMemory();
        
        return oPreviousMentalSituation.getPlanGoal();
    }
    
    /**
     * Add a goal to STM, add only the aim of drives which are used in the selectable goals.
     * 
     * For F26
     *
     * @author wendt
     * @since 02.10.2013 13:46:56
     *
     * @param poGoal
     * @param stm
     */
    public static void addUsableAimOfDrivesToMentalSituation(ArrayList<clsWordPresentationMeshAimOfDrive> aimOfDriveList, ArrayList<clsWordPresentationMeshPossibleGoal> decidedGoals, clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm) {
        //get the ref of the current mental situation
        clsWordPresentationMeshMentalSituation oCurrentMentalSituation = stm.findCurrentSingleMemory();
        
        for (clsWordPresentationMeshPossibleGoal decidedGoal : decidedGoals) {
            for (clsWordPresentationMeshAimOfDrive aimOfDrive : aimOfDriveList) {
                if (decidedGoal.getGoalName().equals(aimOfDrive.getGoalName())==true) {
                    oCurrentMentalSituation.addAimOfDrive(aimOfDrive);
                }
            }
        }

    }
    
    /**
     * Get the current aim of drives
     *
     * @author wendt
     * @since 03.10.2013 21:25:45
     *
     * @param stm
     * @return
     */
    public static ArrayList<clsWordPresentationMeshAimOfDrive> getCurrentAimOfDrivesFromMentalSituation(clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm) {
        return stm.findCurrentSingleMemory().getAimOfDrives();
    }
    
//    /**
//     * Add an action to the STM. This is supposed to be the action, which is going to be executed.
//     *
//     * @author wendt
//     * @since 02.10.2013 13:47:15
//     *
//     * @param stm
//     * @param poAction
//     */
//    public static void addActionToMentalSituation(clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm, clsWordPresentationMesh poAction) {
//        //get the ref of the current mental situation
//        clsWordPresentationMesh oCurrentMentalSituation = stm.findCurrentSingleMemory();
//        
//        //Get the real connection from the reference for the action
//        clsWordPresentationMesh oSupportiveDataStructure = clsActionTools.getSupportiveDataStructure(poAction);
//        if (oSupportiveDataStructure.getMoContent().equals(eContentType.NULLOBJECT.toString())==false) {
//            //get WPMRef
//            ArrayList<clsDataStructurePA> oFoundStructures = clsMeshTools.searchDataStructureOverAssociation(poAction, ePredicate.HASSUPPORTIVEDATASTRUCTURE, 0, true, true);
//            
//            if (oFoundStructures.isEmpty()==false) {
//                clsAssociation oAss = (clsAssociation) oFoundStructures.get(0); 
//                clsMeshTools.moveAssociation(oSupportiveDataStructure, (clsWordPresentationMesh)oAss.getLeafElement(), oAss, true);
//            }
//        }
//        
//        //Add the action to the memory
//        clsMentalSituationTools.setAction(oCurrentMentalSituation, poAction);
//    }
    
    /**
     * Extract the last applicable planned goal.
     * 
     * (wendt)
     *
     * @since 24.06.2012 09:25:22
     *
     * @return
     */
    public static clsWordPresentationMesh extractPreviousPlannedActionFromSTM(clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm) {
        clsWordPresentationMesh oRetVal = clsWordPresentationMesh.getNullObject();
        
        clsWordPresentationMeshMentalSituation oLastMentalImage = stm.findPreviousSingleMemory();
        
        if (oLastMentalImage.isNullObject()==false) {
            
            oRetVal = oLastMentalImage.getExecutedAction();
        }
        
        return oRetVal;
    }
    
    public static void setExecutedAction(clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm, clsWordPresentationMesh action) {
        stm.findCurrentSingleMemory().setExecutedAction(action);
    }
    
    /**
     * Create a mental situation in the short term memory
     *
     * @author wendt
     * @since 01.10.2013 12:04:51
     *
     * @param stm
     */
    public static void createNewMentalSituation(clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm) {
        updateTimeStepsShortTermMemory(stm);
        
        stm.saveToShortTimeMemory(clsWordPresentationMeshMentalSituation.createInstance());
        
    }
    
    /**
     * step the short term memory
     *
     * @author wendt
     * @since 01.10.2013 12:05:06
     *
     * @param shortTermMemory
     * @param environmentalImageMemory
     */
    private static void updateTimeStepsShortTermMemory(clsShortTermMemory<clsWordPresentationMeshMentalSituation> shortTermMemory) {
        shortTermMemory.updateTimeSteps();
    }
    
    /**
     * Add all goals with the condition not reachable to the STM
     *
     * @since 12.02.2013 11:41:42
     *
     * @param poGoalList
     */
    public static void addNonReachableGoalsToSTM(clsShortTermMemory<clsWordPresentationMeshMentalSituation> shortTermMemory, ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList) {
        clsWordPresentationMeshMentalSituation oMentalSituation = shortTermMemory.findCurrentSingleMemory();
        for (clsWordPresentationMeshPossibleGoal oGoal : poGoalList) {
            if( oGoal.getSupportiveDataStructure().getContent().contains("FLEE_CARL"))
            {
                int i=0;
                i++;
                if (i >2)
                { i=0;
                
                }
            }
            if (oGoal.checkIfConditionExists(eCondition.GOAL_NOT_REACHABLE)==true || oGoal.checkIfConditionExists(eCondition.GOAL_COMPLETED)==true) {
                
                oMentalSituation.addExcludedSelectableGoal(oGoal);
                
                log.info("Added non reachable goal to STM : " + oGoal.toString());
            }
        }
    }
    

    
}
