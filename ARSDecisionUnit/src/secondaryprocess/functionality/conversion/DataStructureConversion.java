/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.conversion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import logger.clsLogger;
import memorymgmt.interfaces.itfModuleMemoryAccess;

import org.slf4j.Logger;

import base.datahandlertools.clsDataStructureConverter;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import secondaryprocess.algorithm.conversion.DataStructureConversionTools;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import general.datamanipulation.IDComparator;
import testfunctions.clsTester;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2013, 11:04:19
 * 
 */
public class DataStructureConversion {
    
    private static Logger log = clsLogger.getLog("SecondaryProcessFunctionality");
    /**
     * For the TPM as input, assign all of them with WPM images Return a pair of
     * 1) Peception, 2) A list of memories. This function extracts all acts and
     * other memories from the primary process data structures. The list of
     * memories is categorized in acts from the images
     * 
     * @since 25.01.2012 13:55:04
     * 
     * @param poPerceivedImage
     * @return
     */
    public static clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentationMesh>> getWordPresentationsForImages(itfModuleMemoryAccess ltm, clsThingPresentationMesh poPerceivedImage) {
        clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentationMesh>> oRetVal = null;
        // The input image is the perceived image (defined from the position).
        // Therefore, this image is taken to get the secondary process image
        //long start = System.currentTimeMillis();
        clsWordPresentationMesh oPIWPM = DataStructureConversionTools.convertCompleteTPMtoWPMRoot(ltm, poPerceivedImage);
        
        //System.out.println("Time11: " + (System.currentTimeMillis()- start));
        
        log.debug("converted PI toWPM. \n>Perceived image WPM Part:\n" + oPIWPM.toString() + "\n>Perceived Image TPM part:\n" + poPerceivedImage.toString());
        // Search for all images from the primary process in the memory

        // Input: TPM
        // 1. Get all Images of the Mesh
        //start = System.currentTimeMillis();
        ArrayList<clsThingPresentationMesh> oRITPMList = clsMeshTools.getAllTPMMemories(poPerceivedImage, 4);
        
        //System.out.println("Time12: " + (System.currentTimeMillis()- start));
        //HackMethods.reduceImageListTPM("A01", oRITPMList);
        
        // 2. Search for WPM for the image and add the found image to a list.
        // The WPM is connected with the TPM by an associationWP
        //ArrayList<clsWordPresentationMesh> oRIWPMList = new ArrayList<clsWordPresentationMesh>();
        //start = System.currentTimeMillis();
        ArrayList<clsWordPresentationMesh> oEnhancedRIWPMList = convertRITPMtoWPM(ltm, oRITPMList);
        
        //System.out.println("Time13: " + (System.currentTimeMillis()- start));
        //Enhance all subimages of intentions with all secondary process associations
        ArrayList<clsWordPresentationMesh> enhancedRIWPMListWithIntentions = enhanceIntentions(oEnhancedRIWPMList, ltm);
        
        //System.out.println("Time14: " + (System.currentTimeMillis()- start));
        // Create a List of all loaded acts and other memories
        //start = System.currentTimeMillis();
        ArrayList<clsWordPresentationMesh> oCategorizedRIWPMList = clsActTools.organizeImagesInActs(enhancedRIWPMListWithIntentions);
        log.debug("Found acts: {}", oCategorizedRIWPMList);
        //System.out.println("Time15: " + (System.currentTimeMillis()- start));
        //ArrayList<clsWordPresentationMesh> oCategorizedRIWPMList = clsActTools.processMemories(oEnhancedRIWPMList);

        // Output: ArrayList<WPM> for each TPM-Image. The WPM are already
        // assigned their acts here
        oRetVal = new clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentationMesh>>(oPIWPM, oCategorizedRIWPMList);
        
        return oRetVal;
    }
    
    private static ArrayList<clsWordPresentationMesh> enhanceIntentions(ArrayList<clsWordPresentationMesh> oEnhancedRIWPMList, itfModuleMemoryAccess ltm) {
        ArrayList<clsWordPresentationMesh> result = new ArrayList<clsWordPresentationMesh>(oEnhancedRIWPMList);
        
        ArrayList<clsWordPresentationMesh> newWPMList = new ArrayList<clsWordPresentationMesh>();
        IDComparator oIDCompare = new IDComparator();
        
        //for easier searching, create a cloned, ordered array list
        @SuppressWarnings("unchecked")
        ArrayList<clsWordPresentationMesh> oOrderedWPMList = (ArrayList<clsWordPresentationMesh>) oEnhancedRIWPMList.clone(); //this only clones the LIST, not the elements in the list
        Collections.sort(oOrderedWPMList, oIDCompare);
        
        for (clsWordPresentationMesh riwpm : oEnhancedRIWPMList) {
            boolean isIntention = clsActTools.isIntention(riwpm);
            
            if (isIntention==true) {
                //Get all subimages
                ArrayList<clsWordPresentationMesh> events = clsActTools.getAllSubImages(riwpm);
                for (clsWordPresentationMesh event : events) {
                    //Quickfix Kollmann: the associations between the intention and the sub images are pointing to the wrong instances of the sub-images
                    //                   so, not all (or possibly none) of the sub-images have primary data structures associated and would therefore be
                    //                   added to the newWPMList - even though the images are allready contained in original WPM list.
                    //                   As a quick fix, I base the dicission not upon the existance of a primary data structure, but actually check for
                    //                   every image if it is already contained in the original list via a LOOSE check (DS_ID und DSInstance_ID)
                    
                    //former code of the check:
                    //clsThingPresentationMesh tpm = clsMeshTools.getPrimaryDataStructureOfWPM(event);
                    //If the structure has a TPM, then it has been treated already and do not need to be used to find additional associations
                    
                    //if (tpm.isNullObject()==true) {
                    
                    //new code of the check:
                    //if the original list contains a corresponding element (a.DS_ID == b.DS_ID)
                    if(Collections.binarySearch(oOrderedWPMList, event, oIDCompare) < 0) { // binarySearch returns the index of the found element
                        //ArrayList<clsPair<Double, clsDataStructurePA>> extraWPM = ltm.searchMesh(event, event.getMoContentType(), 1.0, 1);
                        clsWordPresentationMesh enhancedMesh = (clsWordPresentationMesh) ltm.searchCompleteMesh(event, 1);
                        
                        
                        //=== Perform system tests ===//
                        clsTester.getTester().setActivated(false);
                        if (clsTester.getTester().isActivated()) {
                            try {
                                clsTester.getTester().exeTestCheckLooseAssociations(enhancedMesh); 
                            } catch (Exception e) {
                                log.error("Systemtester has an error in " + clsDataStructureConverter.class.getSimpleName(), e);
                            }
                        }
                        newWPMList.add(enhancedMesh);
                    }
                    
                }
            }
        }
        
        result.addAll(newWPMList);
        return result;
        
    }
    
    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 14.11.2013 21:11:55
     *
     * @param ltm
     * @param oRITPMList
     * @return
     */
    private static ArrayList<clsWordPresentationMesh> convertRITPMtoWPM(itfModuleMemoryAccess ltm, ArrayList<clsThingPresentationMesh> oRITPMList) {
        ArrayList<clsWordPresentationMesh> oEnhancedRIWPMList = new ArrayList<clsWordPresentationMesh>();
        for (clsThingPresentationMesh oRITPM : oRITPMList) {
            // Convert the complete image to WPM
            clsWordPresentationMesh oRIWPM = DataStructureConversionTools.convertCompleteTPMtoWPM(ltm, oRITPM, new ArrayList<clsThingPresentationMesh>(), 1, 3, oRITPM.getContentType());
            
            // 3. Search for WPM for all internal objects in the WPM if they are
            // available
            //oRIWPMList.add(oRIWPM);
        //}

        // 4. For each WPM, search for more SP-WPM-Parts and connect them
        // ArrayList<clsWordPresentationMesh> oCompleteLoadedWPMObjectList = new
        // ArrayList<clsWordPresentationMesh>();
        // Add all already loaded objects to the list of activated WPM
        // oCompleteLoadedWPMObjectList.addAll(oRIWPMList);
        // This is a list of single independent WPM
        //for (clsWordPresentationMesh oRIWPM : oRIWPMList) {
            // Get the complete WPM-Object including all WPM associations until
            // level 2. Input is a WPM, therefore, only WPM is returned
            // FIXME AW: As the intention is loaded, all other connected
            // containers are loaded here. This is too specialized
            clsWordPresentationMesh oEnhancedWPM = (clsWordPresentationMesh) ltm.searchCompleteMesh(oRIWPM, 3);
            
            
            //1. Add all PI-Matches as WP to each image
            clsActTools.setPIMatchToWPM(oEnhancedWPM); //The adding is done hier at the first place as in this module only images are processed and not entities.
            
            //=== Perform system tests ===//
            clsTester.getTester().setActivated(false);
            if (clsTester.getTester().isActivated()) {
                log.warn("Tester activated");
                try {
                    clsTester.getTester().exeTestCheckPIMatch(new ArrayList<clsWordPresentationMesh>(Arrays.asList(oEnhancedWPM)));
                } catch (Exception e) {
                    log.error("Systemtester has an error in " + DataStructureConversion.class.getSimpleName(), e);
                }
            }
            
            //2. Delete all primary process external connections
            clsMeshTools.removeAllExternalAssociationsTPM(clsMeshTools.getPrimaryDataStructureOfWPM(oEnhancedWPM));
            
            //Delete primary process structures
            //clsMeshTools.removeTPMPartOfWPM(oEnhancedWPM);
            
            // Add the enhanced WPM to a new list, as the enhanced WPM are
            // complete and the former RI are not.
            oEnhancedRIWPMList.add(oEnhancedWPM);
            log.debug("converted Image: \n>Remembered image WPM:\n" + oRIWPM.toString() + "\n>Remembered image TPM:\n" + oRITPM.toString());

            
            
            // Check if all the loaded structures can be added by getting all
            // WPM as a list
            // ArrayList<clsWordPresentationMesh> oEnhancedList =
            // clsMeshTools.getAllWPMImages(oEnhancedWPM, 6);
            // Go through all new found entities
            // for (clsWordPresentationMesh oWPM : oEnhancedList) {
            // if (oEnhancedWPM!=oWPM) {
            // clsMeshTools.mergeMesh(oEnhancedWPM,
            // (clsWordPresentationMesh)oWPM);
            // }
            // }
            // System.out.println("oEnhancedList Size: " +
            // oEnhancedList.size());
        }
        
        return oEnhancedRIWPMList;
    }
}
