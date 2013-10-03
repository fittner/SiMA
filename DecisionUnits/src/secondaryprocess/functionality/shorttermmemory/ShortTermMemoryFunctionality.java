/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.shorttermmemory;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshMentalSituation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsMentalSituationTools;
import secondaryprocess.datamanipulation.clsMeshTools;

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
    public static void addGoalToMentalSituation(clsWordPresentationMeshGoal poGoal, clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm) {
        //get the ref of the current mental situation
        clsWordPresentationMesh oCurrentMentalSituation = stm.findCurrentSingleMemory();
        
        clsMentalSituationTools.setGoal(oCurrentMentalSituation, poGoal);
    }
    
    /**
     * Add an action to the STM. This is supposed to be the action, which is going to be executed.
     *
     * @author wendt
     * @since 02.10.2013 13:47:15
     *
     * @param stm
     * @param poAction
     */
    public static void addActionToMentalSituation(clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm, clsWordPresentationMesh poAction) {
        //get the ref of the current mental situation
        clsWordPresentationMesh oCurrentMentalSituation = stm.findCurrentSingleMemory();
        
        //Get the real connection from the reference for the action
        clsWordPresentationMesh oSupportiveDataStructure = clsActionTools.getSupportiveDataStructure(poAction);
        if (oSupportiveDataStructure.getMoContent().equals(eContentType.NULLOBJECT.toString())==false) {
            //get WPMRef
            ArrayList<clsDataStructurePA> oFoundStructures = clsMeshTools.searchDataStructureOverAssociation(poAction, ePredicate.HASSUPPORTIVEDATASTRUCTURE, 0, true, true);
            
            if (oFoundStructures.isEmpty()==false) {
                clsAssociation oAss = (clsAssociation) oFoundStructures.get(0); 
                clsMeshTools.moveAssociation(oSupportiveDataStructure, (clsWordPresentationMesh)oAss.getLeafElement(), oAss, true);
            }
        }
        
        //Add the action to the memory
        clsMentalSituationTools.setAction(oCurrentMentalSituation, poAction);
    }
    
    /**
     * Extract the last applicable planned goal.
     * 
     * (wendt)
     *
     * @since 24.06.2012 09:25:22
     *
     * @return
     */
    public static clsWordPresentationMesh extractPlannedActionFromSTM(clsShortTermMemory<clsWordPresentationMeshMentalSituation> stm) {
        clsWordPresentationMesh oRetVal = null;
        
        clsWordPresentationMesh oLastMentalImage = stm.findPreviousSingleMemory();
        
        if (oLastMentalImage!=null) {
            oRetVal = clsMentalSituationTools.getAction(oLastMentalImage);
        }
        
        return oRetVal;
    }
    
    /**
     * Create a mental situation in the short term memory
     *
     * @author wendt
     * @since 01.10.2013 12:04:51
     *
     * @param stm
     */
    public static void createNewMentalSituation(clsShortTermMemory stm) {
        updateTimeStepsShortTermMemory(stm);
        
        stm.saveToShortTimeMemory(clsMentalSituationTools.createMentalSituation());
        
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
    private static void updateTimeStepsShortTermMemory(clsShortTermMemory shortTermMemory) {
        shortTermMemory.updateTimeSteps();
    }
    
    /**
     * Add all goals with the condition not reachable to the STM
     *
     * @since 12.02.2013 11:41:42
     *
     * @param poGoalList
     */
    public static void addNonReachableGoalsToSTM(clsShortTermMemory<clsWordPresentationMeshMentalSituation> shortTermMemory, ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) {
        for (clsWordPresentationMeshGoal oGoal : poGoalList) {
            if (oGoal.checkIfConditionExists(eCondition.GOAL_NOT_REACHABLE)==true) {
                clsWordPresentationMesh oMentalSituation = shortTermMemory.findCurrentSingleMemory();
                clsMentalSituationTools.setExcludedGoal(oMentalSituation, oGoal);
                
                log.debug("Added non reachable goal to STM : " + oGoal.toString());
            }
        }
    }
    

    
}
