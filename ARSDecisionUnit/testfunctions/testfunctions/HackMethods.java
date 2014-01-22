/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package testfunctions;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Set;

import logger.clsLogger;
import memorymgmt.enums.eGoalType;
import memorymgmt.interfaces.itfModuleMemoryAccess;

import org.slf4j.Logger;

import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshGoal;
import base.datatypes.helpstructures.clsPair;
import du.enums.eShapeType;

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
            eGoalType oGoalType = oG.getGoalSource();
            
            if (bPerceivedFound==false && oGoalType.equals(eGoalType.PERCEPTIONALDRIVE) && oG.getGoalObject().getContent().equals("CAKE") && oG.getGoalName().equals("LIBIDINOUSSTOMACH")) {
                oReplaceList.add(oG);
                bPerceivedFound=true;
            } else if (bActFound==false && oGoalType.equals(eGoalType.MEMORYDRIVE) && oG.getGoalObject().getContent().equals("CAKE") && oG.getGoalName().equals("LIBIDINOUSSTOMACH")) {
                oReplaceList.add(oG);
                bActFound=true;
            } else if (bDriveFound==false && oGoalType.equals(eGoalType.DRIVESOURCE) && oG.getGoalObject().getContent().equals("CAKE") && oG.getGoalName().equals("LIBIDINOUSSTOMACH")) {
                oReplaceList.add(oG);
                bDriveFound=true;
            }

        }
        
        //moReachableGoalList_IN = oReplaceList;
        return oReplaceList;
    }
    
    /**
     * Remove all search results, which do not match any of the provided strings
     *
     * @author wendt
     * @since 02.10.2013 21:14:05
     *
     * @param poAcceptedContent List of strings that are matched against the content type of the search results (with .contains(...) on each content
     * @param poSearchResults List of TPM search results that will be filtered (any nonTPM entries will be ignored and therefore remain in the list)
     */
    public static void filterTPMSearchResultList(Set<String> poAcceptedContent, ArrayList<clsPair<Double,clsDataStructurePA>> poSearchResults) {
        log.warn("HACKMETHOD filterSearchResultList ACTIVATED");
        clsDataStructurePA poItem = null;
        
        for(String oAcceptedContent : poAcceptedContent) {
            ListIterator<clsPair<Double, clsDataStructurePA>> oResultIterator = poSearchResults.listIterator();
            
            while(oResultIterator.hasNext()) {
                poItem = oResultIterator.next().b;
                if(poItem instanceof clsThingPresentationMesh) {
                    if(!((clsThingPresentationMesh)poItem).getContent().contains(oAcceptedContent)) {
                        oResultIterator.remove();
                    }
                }
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
    public static void reduceImageListWPM(String contentToMatch, ArrayList<clsWordPresentationMesh> images) {
        log.warn("HACKMETHOD reduceImageListWPM ACTIVATED");
        ListIterator<clsWordPresentationMesh> iter = images.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getContent().contains(contentToMatch)==false) {
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
            if (iter.next().getContent().contains(contentToMatch)==false) {
                iter.remove();
            }
        }
    }
    
    public static ArrayList<clsDriveMesh> JACKBAUERHASHACKEDHERETOGETTHENOURISHCAKEDRIVEASASINGLEDRIVE(ArrayList<clsDriveMesh> driveList_Input, itfModuleMemoryAccess memoryAccess) {
        log.warn("HACKMETHOD JACKBAUERHASHACKEDHERETOGETTHENOURISHCAKEDRIVEASASINGLEDRIVE ACTIVATED");
        //FIXME AW .::::::: FAKE Prepare Drive input
                ArrayList<clsDriveMesh> oOnlyDriveMesh = new ArrayList<clsDriveMesh>();
                for (clsDriveMesh oDM : driveList_Input) {
                    //if (oDM.getActualDriveObject().getMoContent().equals("BODO")) {
                        //Change to cake
                        
                        clsThingPresentationMesh oTPM = memoryAccess.searchExactEntityFromInternalAttributes("CAKE", eShapeType.CIRCLE.toString(), "FFAFAF");
                        //clsThingPresentationMesh oTPM = this.debugGetThingPresentationMeshEntity("CARROT", eShapeType.CIRCLE.toString(), "FFC800");
                        
                        
                        try {
                            if (oDM.getDebugInfo().equals("nourish")) {
                                oDM.setActualDriveObject(oTPM, 1.0);
                                oDM.setQuotaOfAffect(1.0);
                                
                                oOnlyDriveMesh.add(oDM);
                            } 
                            
                        } catch (Exception e) {
                            // TODO (wendt) - Auto-generated catch block
                            e.printStackTrace();
                        }

                    
                }
                
                //moDriveList_Input.clear();
                //moDriveList_Input.add(oOnlyDriveMesh);
                return oOnlyDriveMesh;
    }
    
}
