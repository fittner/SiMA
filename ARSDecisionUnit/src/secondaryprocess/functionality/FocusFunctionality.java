/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality;

import general.datamanipulation.GeneralSortingTools;

import java.util.ArrayList;

import logger.clsLogger;
import memorymgmt.enums.eAction;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.ePhiPosition;
import memorymgmt.enums.eRadius;

import org.slf4j.Logger;

import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import secondaryprocess.algorithm.conversion.DataExtractionTools;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsSecondarySpatialTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2013, 11:37:25
 * 
 */
public class FocusFunctionality {

    private static Logger log = clsLogger.getLog("SecondaryProcessFunctionality");
    
    /**
     * Depending on the previous action, all entities in certain fields are extracted
     *
     * @author wendt
     * @since 01.10.2013 11:40:44
     *
     * @param poPerceivedImage
     * @param poActionWPM
     * @return
     * @throws Exception 
     */
    public static ArrayList<clsPair<Double, clsWordPresentationMesh>> extractFilterEntitiesFromAction(clsWordPresentationMesh poPerceivedImage, clsWordPresentationMesh poActionWPM) throws Exception {
        ArrayList<clsPair<Double, clsWordPresentationMesh>> oResult  = new ArrayList<clsPair<Double, clsWordPresentationMesh>>();
        
        if (poActionWPM.isNullObject()==false) {
            //Extract action
            eAction oAction = eAction.valueOf(poActionWPM.getContent());
            
            if (oAction.equals(eAction.FOCUS_MOVE_FORWARD)) {
                //Set focus area in front of the agent, i. e. 
                //1. all entities in CENTER NEAR have the highest priority, add 1000 points. Only a new high negative can overrule this
                //2. all entities in MIDDLE_LEFT NEAR and MIDDLE_RIGHT NEAR have the second highest priority 100 
                //3. all entities in CENTER MEDIUM and CENTER FAR have high priority 
                
                //No supportive data structure is needed
                
                //Extract entities from the single fields and assign them
                oResult.addAll(clsSecondarySpatialTools.extractEntitiesInArea(poPerceivedImage, eRadius.NEAR, ePhiPosition.CENTER, 80));
                oResult.addAll(clsSecondarySpatialTools.extractEntitiesInArea(poPerceivedImage, eRadius.NEAR, ePhiPosition.MIDDLE_LEFT, 60));
                oResult.addAll(clsSecondarySpatialTools.extractEntitiesInArea(poPerceivedImage, eRadius.NEAR, ePhiPosition.MIDDLE_RIGHT, 60));
                oResult.addAll(clsSecondarySpatialTools.extractEntitiesInArea(poPerceivedImage, eRadius.MEDIUM, ePhiPosition.CENTER, 40));
                oResult.addAll(clsSecondarySpatialTools.extractEntitiesInArea(poPerceivedImage, eRadius.FAR, ePhiPosition.CENTER, 20));
                
                
            } else if (oAction.equals(eAction.FOCUS_TURN_LEFT)) {
                //Set focus area left of the agent
                //1. MIDDLE_LEFT NEAR
                //2. LEFT NEAR
                //3. MIDDLE_LEFT MEDIUM
                //4. LEFT MEDIUM
                
                //No supportive data structure is needed
                
            
            } else if (oAction.equals(eAction.FOCUS_ON)||oAction.equals(eAction.EAT)) {
                //All entities in an image get the highest priority
                
                //Use the supportive data structure
                ArrayList<clsWordPresentationMesh> suportiveDataStructures = clsActionTools.getSupportiveDataStructureForAction(poActionWPM);
                clsWordPresentationMesh oFocusImage;
                if (suportiveDataStructures.size()==1) {
                    oFocusImage = suportiveDataStructures.get(0);
                } else {
                    log.warn("Cannot focus, non or more than one image are sent to focus on. SupportiveDataStructures: {}", suportiveDataStructures);
                    throw new Exception("Cannot focus, non or more than one image are sent to focus on");
                }
                
                
                //If there is a supportive data structure
                try {
                    if (oFocusImage.getContent().equals(eContentType.NULLOBJECT)==true) {
                        throw new Exception ("F23: Focused action was chosen but no supportive data structure exists");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                //Get all found entities in the perceived image, which matches the entities in the supportive image
                oResult.addAll(DataExtractionTools.getPerceivedImageEntitiesFromImage(poPerceivedImage, oFocusImage, 70));      //Why 70? because 80 is very high importance.   
            }
        }
        
        
        return oResult;
    }
    
    /**
     * All drives within the perceived images are extracted and sorted. The drive goal list
     * and the psychic energy decides how many elements of the PI are passed.
     * (wendt)
     *
     * @since 07.08.2011 23:05:42
     *
     * @param poPerceptionSeondary
     * @return
     */
    public static void focusPerception(clsWordPresentationMesh poPerception, ArrayList<clsPair<Double, clsWordPresentationMesh>> poPrioritizedEntityList, int  pnNumberOfAllowedObjects) {
        
        //Sort incoming special goals
        ArrayList<clsWordPresentationMesh> oPrioritizedEntityListSorted = GeneralSortingTools.sortAndFilterRatedStructures(poPrioritizedEntityList, pnNumberOfAllowedObjects);
        
        //Focus only the select
        DataExtractionTools.filterImageElementsBasedOnPrioritizedGoals(poPerception, oPrioritizedEntityListSorted);
        
    }
}
