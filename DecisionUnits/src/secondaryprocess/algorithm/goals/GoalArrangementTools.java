/**
 * CHANGELOG
 *
 * 30.09.2013 wendt - File created
 *
 */
package secondaryprocess.algorithm.goals;

import java.util.ArrayList;
import java.util.Arrays;

import datatypes.helpstructures.clsTriple;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.enums.eGoalType;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;
import secondaryprocess.datamanipulation.clsSecondarySpatialTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 30.09.2013, 14:26:25
 * 
 */
public class GoalArrangementTools {
    
    private static final ArrayList<String> moPossibleDriveGoals = new ArrayList<String>(Arrays.asList("LIBIDINOUSSTAMINA", "AGGRESSIVESTAMINA", "LIBIDINOUSSTOMACH", "AGGRESSIVESTOMACH", "LIBIDINOUSRECTUM", "AGGRESSIVERECTUM", "LIBIDINOUSLIBIDO")); //SLEEP first, as if there is no sleep, the agent cannot do anything
    
    /**
     * This function shall be used only on a compare goal from perception and a list of goals only from perception. The function compares
     * the compare goal with the goals from the goal list and returns the goal from the goal list with the shortest distance to the compare goal
     * This function shall actually find the previous goal in the new goal list. This is a first implementation of object constance.
     * 
     * (wendt)
     *
     * @since 09.09.2012 20:51:26
     *
     * @param poGoalList
     * @param poCompareGoal
     * @return
     */
    public static clsWordPresentationMeshSelectableGoal getSpatiallyNearestGoalFromPerception(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList, clsWordPresentationMeshSelectableGoal poCompareGoal) {
        clsWordPresentationMeshSelectableGoal oResult = clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
        
        eGoalType oCompareGoalType = poCompareGoal.getGoalType();
        
        if (oCompareGoalType.equals(eGoalType.PERCEPTIONALDRIVE)==false && oCompareGoalType.equals(eGoalType.PERCEPTIONALEMOTION)==false) {
            try {
                throw new Exception("Only Goal type from perception is allowed in this function");
            } catch (Exception e) {
                // TODO (wendt) - Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        //double rBestDistance = -1;
        
        ArrayList<clsWordPresentationMeshSelectableGoal> oZeroDistanceList = sortSpatiallyGoalList(poGoalList, poCompareGoal);
        
        //If the size of the list is >1 then there are several possibilites. Therefore correct the comparegoal with the movement
        if (oZeroDistanceList.size()>1) {
//          if (poLastAction.equals(eAction.TURN_LEFT)) {
//              
//          } else if (poLastAction.equals(eAction.TURN_RIGHT)) {
//              
//          } else if (poLastAction.equals(eAction.MOVE_FORWARD)) {
//              
//          }
            
            //TODO someone: Implement the cases above. At a movement, the goal search shall be corrected.
            
            oResult = oZeroDistanceList.get(0);
            
        } else if (oZeroDistanceList.size()==1){
            oResult = oZeroDistanceList.get(0);
        }
        
        return oResult;
    }
    
    
    /**
     * Compared to the compare goal, sort all goals, in oder that the spatially nearest goal is the best match
     * This method is used to create object constance.
     *
     * @author wendt
     * @since 30.09.2013 14:25:17
     *
     * @param poGoalList
     * @param poCompareGoal
     * @return
     */
    private static ArrayList<clsWordPresentationMeshSelectableGoal> sortSpatiallyGoalList(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList, clsWordPresentationMeshSelectableGoal poCompareGoal) {
        ArrayList<clsWordPresentationMeshSelectableGoal> oResultList = new ArrayList<clsWordPresentationMeshSelectableGoal>();
        clsWordPresentationMeshSelectableGoal oResult = clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
        
        double rBestDistance = -1;
        
        for (clsWordPresentationMeshSelectableGoal oGoal : poGoalList) {
            eGoalType oGoalTypeFromListGoal = oGoal.getGoalType();
            
            if (oGoalTypeFromListGoal.equals(eGoalType.PERCEPTIONALDRIVE) || oGoalTypeFromListGoal.equals(eGoalType.PERCEPTIONALEMOTION)) {
                double rCurrentDistance = clsSecondarySpatialTools.getDistance(oGoal.getGoalObject(), poCompareGoal.getGoalObject());
                
                if ((rBestDistance==-1 && rCurrentDistance>=0) || (rBestDistance>=0 && rCurrentDistance<rBestDistance)) {
                    rBestDistance=rCurrentDistance;
                    oResult = oGoal;
                    
                    //If the position is exact the same, then break
                    if (rCurrentDistance==0) {
                        oResultList.add(oGoal);
                    }
                }
            }
        }
        
        if (oResultList.isEmpty()==true) {
            oResultList.add(oResult);
        }
        
        return oResultList;
    }
    
    /**
     * Depending on the order of the aim of drive, if all of them are equally strong, some of the are still more important than 
     * others, i.e. if the stamina is empty, the agent cannot do anything. Therefore, this drive is always the most important drive
     *
     * @author wendt
     * @since 30.09.2013 20:30:43
     *
     * @param oDriveGoal
     * @return
     */
    public static double getDriveDemandCorrectionFactorFromDriveOrder(clsWordPresentationMeshGoal oDriveGoal) {
        double rDriveIndex = (moPossibleDriveGoals.size() - moPossibleDriveGoals.indexOf(oDriveGoal.getGoalName())-1)/100;   //The higher the better
        return rDriveIndex;
    }
        
    
    /**
     * Sort the input list of the drive demands according to max pleasure
     * DOCUMENT (wendt) - insert description
     *
     * @since 05.08.2011 22:16:51
     *
     * @param poDriveDemandsList
     */
    public static ArrayList<clsWordPresentationMeshGoal> sortGoals(ArrayList<clsWordPresentationMeshGoal> poDriveDemandsList) {
        ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
        
        //If the list is empty return
        if (poDriveDemandsList.isEmpty()) {
            return oRetVal; //nothing to do. either list is empty, or it consists of one lement only
        }
        
        //Set list of drives in the order of drive priority, FIXME KD: Which drives have priority and how is that changed if they have the same affect
        //FIXME CM: What drives do exist????
        //ArrayList<String> oKeyWords = new ArrayList<String>(Arrays.asList("NOURISH", "BITE", "REPRESS", "SLEEP", "RELAX", "DEPOSIT"));
        
        //TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>> oSortedList = new TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>>();
        
        ArrayList<clsTriple<Double, Double, clsWordPresentationMeshGoal>> oNewList = new ArrayList<clsTriple<Double, Double, clsWordPresentationMeshGoal>>();
        
        //Go through the original list
        for (int i=0; i<poDriveDemandsList.size();i++) {    //Go through each element in the list
            //The the content of each drive
            clsWordPresentationMeshGoal oDriveGoal = poDriveDemandsList.get(i);
            //Get the content of the datatype in the container
            //String oContent = ((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent();
            
            //Recognize if the string is a drive demand or a drive goal. If it is a drive demand, then do nothing, return the drive demand. If it is a drive goal, 
            //convert to drive demand
            
            //Sort first for affect
            double rAffectLevel = oDriveGoal.getTotalImportance();
            //int nEffortLevel = clsGoalTools.getEffortLevel(oGoal); //getDriveIntensityAsInt(oContent);
            double rTotalAffectValue = rAffectLevel;
            //Sort the affects for priority according to the order in the list in this class
            double rAffectSortOrder = Math.abs(rTotalAffectValue)*10; //Just set the absolute affect as the worst one//(moAffectSortOrder.size() -1 - moAffectSortOrder.indexOf(nTotalAffectValue)) * 10;
            //Important note: Sorting is made by setting the most significant value (*10), adding them and after that to sort.
            //Sort then for drive according to the order in the list 
            //String oDriveType = oDriveGoal.getGoalName(); //getDriveType(oContent);
            
            double rDriveIndex = GoalArrangementTools.getDriveDemandCorrectionFactorFromDriveOrder(oDriveGoal);    //The higher the better
            
            int nIndex = 0;
            //Increase index if the list is not empty
            while((oNewList.isEmpty()==false) && 
                    (nIndex<oNewList.size()) &&
                    (oNewList.get(nIndex).a + oNewList.get(nIndex).b > rAffectSortOrder + rDriveIndex)) {
                nIndex++;
            }
            
            oNewList.add(nIndex, new clsTriple<Double, Double, clsWordPresentationMeshGoal>(rAffectSortOrder, rDriveIndex, oDriveGoal));
        }
        
        //Add results to the new list
        for (int i=0; i<oNewList.size();i++) {
            oRetVal.add(i, oNewList.get(i).c);
        }
        
        return oRetVal;
    }
    
}
