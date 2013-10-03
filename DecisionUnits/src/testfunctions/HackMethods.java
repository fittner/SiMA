/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package testfunctions;

import java.util.ArrayList;
import java.util.ListIterator;

import logger.clsLogger;

import org.slf4j.Logger;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
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
    
    private static final Logger log = clsLogger.getLog("Test");
    
    
    public static ArrayList<clsWordPresentationMeshGoal> JACKBAUERHACKReduceGoalList(ArrayList<clsWordPresentationMeshGoal> moReachableGoalList_IN) {
        log.warn("HACKMETHOD JACKBAUERHACKReduceGoalList ACTIVATED");
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
    
    /**
     * Remove all images, which do not match a certain content
     *
     * @author wendt
     * @since 02.10.2013 21:14:05
     *
     * @param contentToMatch
     * @param images
     */
    public static void reduceImageListWPM(String contentToMatch, ArrayList<clsWordPresentationMesh> images) {
        log.warn("HACKMETHOD reduceImageListWPM ACTIVATED");
        ListIterator<clsWordPresentationMesh> iter = images.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getMoContent().contains(contentToMatch)==false) {
                iter.remove();
            }
        }
    }
    
    /**
     * Remove all images, which do not match a certain content
     *
     * @author wendt
     * @since 02.10.2013 21:14:05
     *
     * @param contentToMatch
     * @param images
     */
    public static void reduceImageListTPM(String contentToMatch, ArrayList<clsThingPresentationMesh> images) {
        log.warn("HACKMETHOD reduceImageListTPM ACTIVATED");
        ListIterator<clsThingPresentationMesh> iter = images.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getMoContent().contains(contentToMatch)==false) {
                iter.remove();
            }
        }
    }
    
}
