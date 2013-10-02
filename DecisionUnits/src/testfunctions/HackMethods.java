/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package testfunctions;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eGoalType;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2013, 21:32:58
 * 
 */
public class HackMethods {
    
    
    public static ArrayList<clsWordPresentationMeshGoal> JACKBAUERHACKReduceGoalList(ArrayList<clsWordPresentationMeshGoal> moReachableGoalList_IN) {
        //Keep only Libidonous stomach with cake
        boolean bPerceivedFound = false;
        boolean bActFound = false;
        boolean bDriveFound = false;
        
        ArrayList<clsWordPresentationMeshGoal> oReplaceList = new ArrayList<clsWordPresentationMeshGoal>();
        
        for (clsWordPresentationMeshGoal oG : moReachableGoalList_IN) {
            eGoalType oGoalType = oG.getGoalType();
            
            if (bPerceivedFound==false && oGoalType.equals(eGoalType.PERCEPTIONALDRIVE) && oG.getGoalObject().getMoContent().equals("CAKE") && oG.getGoalName().equals("LIBIDINOUSSTOMACH")) {
                oReplaceList.add(oG);
                bPerceivedFound=true;
            } else if (bActFound==false && oGoalType.equals(eGoalType.MEMORYDRIVE) && oG.getGoalObject().getMoContent().equals("CAKE") && oG.getGoalName().equals("LIBIDINOUSSTOMACH")) {
                oReplaceList.add(oG);
                bActFound=true;
            } else if (bDriveFound==false && oGoalType.equals(eGoalType.DRIVESOURCE) && oG.getGoalObject().getMoContent().equals("CAKE") && oG.getGoalName().equals("LIBIDINOUSSTOMACH")) {
                oReplaceList.add(oG);
                bDriveFound=true;
            }

        }
        
        //moReachableGoalList_IN = oReplaceList;
        return oReplaceList;
    }
}
