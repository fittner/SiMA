/**
 * CHANGELOG
 *
 * 30.09.2013 wendt - File created
 *
 */
package secondaryprocess.algorithm.goals;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshAimOfDrive;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.memorymgmt.enums.eGoalType;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 30.09.2013, 14:16:11
 * 
 */
public class GoalGenerationTools {
    /**
  * Extract possible feeling goals from an act.
  * 
  * The supportive data structure has to be provided here
  * 
  * (wendt)
  *
  * @since 29.07.2011 14:16:29
  *
  * @param poWPInput
  * @param poContainer
  * @return
  * @throws Exception 
  */
    public static ArrayList<clsWordPresentationMeshSelectableGoal> generateSelectableGoalsFromFeelingsWPM(clsWordPresentationMesh poIntention, clsWordPresentationMesh poSupportiveDataStructureAsAct) {
        ArrayList<clsWordPresentationMeshSelectableGoal> oRetVal = new ArrayList<clsWordPresentationMeshSelectableGoal>();
     
        //Get feelings from WPM
        ArrayList<clsWordPresentationMeshFeeling> oFeelingsList = clsGoalManipulationTools.getFeelingsFromImage(poIntention);
     
        for (clsWordPresentationMeshFeeling oFeeling : oFeelingsList) {
            //Goal content
            String oFeelingcontent = oFeeling.getMoContent();
         
            //Goal Importance
            //TODO AW: Check if this is correct. Intensity is here not used at all. 
            double oImportance = Math.abs(oFeeling.getPleasure() + oFeeling.getUnpleasure());
         
            eGoalType oGoalType = eGoalType.MEMORYFEELING;
         
            clsWordPresentationMesh oGoalObject = poIntention;
         
            clsWordPresentationMeshSelectableGoal oGoal = clsGoalManipulationTools.createSelectableGoal(oFeelingcontent, oGoalType, oImportance, oGoalObject);
         
            //Set supportive datastructure
            oGoal.setSupportiveDataStructure(poSupportiveDataStructureAsAct);
            
            //Set list of Feelings
            oGoal.addFeelings(oFeelingsList);
            
            oRetVal.add(oGoal);
        }
        return oRetVal;
    }
    
    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 29.09.2013 13:16:43
     *
     * @param reachableGoalList
     * @param currentFeelings
     * @param activateEmotionalInfluence
     */
    public static void TEMP_METHOD_generatePanicGoal(ArrayList<clsWordPresentationMeshSelectableGoal> reachableGoalList, ArrayList<clsWordPresentationMeshFeeling> currentFeelings, boolean activateEmotionalInfluence) {
        //Process emotions
        //TODO SM: Remove this function. Panic goals are generated from an act (BODO-act)
        clsWordPresentationMeshSelectableGoal oPanicGoal = GoalGenerationTools.generatePanicGoalFromFeeling(currentFeelings);
        //TODO SM: Remove Panicgoal and replace with real goal generation
        if (oPanicGoal.isNullObject()==false && activateEmotionalInfluence==true) {
            reachableGoalList.add(oPanicGoal);
            //log.trace("Added panic goal" + oPanicGoal);
        }
    }
    
    /**
     * Search the perception or memories for goals with very strong negative affect. These object are converted to drive demands
     * and put on the to of the priolist. This forces the agent to avoid these objects
     * (wendt)
     *
     * @since 17.09.2011 08:27:41
     *
     * @param poPotentialGoalList
     * @return
     */
    private static clsWordPresentationMeshSelectableGoal generatePanicGoalFromFeeling(ArrayList<clsWordPresentationMeshFeeling> poFeelingList) {
        clsWordPresentationMeshSelectableGoal oResult = clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
     
        if (poFeelingList.isEmpty()==false) {
            if (eEmotionType.valueOf(poFeelingList.get(0).getMoContent()).equals(eEmotionType.ANXIETY) ||
                 eEmotionType.valueOf(poFeelingList.get(0).getMoContent()).equals(eEmotionType.CONFLICT)) {
                oResult = clsGoalManipulationTools.createSelectableGoal("PANIC", eGoalType.EMOTIONSOURCE, -1,clsMeshTools.getNullObjectWPM());
                oResult.setAssociatedPlanAction(clsActionTools.createAction(eAction.FLEE));
                oResult.setCondition(eCondition.PANIC);
            }   
        }
     
        return oResult;
    }
    
    /**
     * Create a selectable goal based on the aim of drives
     *
     * @author wendt
     * @since 04.10.2013 22:29:42
     *
     * @param oAimOfDrive
     * @return
     */
    public static clsWordPresentationMeshSelectableGoal createDriveSourceGoal(clsWordPresentationMeshAimOfDrive oAimOfDrive) {
        //Get the potential pleasure of the drivemesh
        clsWordPresentationMesh oGoalObject = oAimOfDrive.getGoalObject();
        //Get potential satisfaction of goalobject
        //FIXME Here, the potential fulfillment by the driveobject should be taken. Because this value is hard to receive, a temp value is taken here
        double potentialImportance = oAimOfDrive.getQuotaOfAffectAsImportance()/2;
        
        
        
        clsWordPresentationMeshSelectableGoal generatedSelectableGoal = clsGoalManipulationTools.createSelectableGoal(oAimOfDrive.getGoalName(), eGoalType.DRIVESOURCE, potentialImportance, oAimOfDrive.getGoalObject());
        
        //Apply the same as the other goals
        //applyAimOfDriveOnGoal(generatedSelectableGoal, oAimOfDrive);
        //applyDriveDemandCorrections(generatedSelectableGoal, oAimOfDrive);
        
        //Apply effort values of conditions
        
        
        
        return generatedSelectableGoal;
    }
}
