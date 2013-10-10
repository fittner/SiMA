/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package secondaryprocess.algorithm.conversion;

import java.util.ArrayList;
import java.util.ListIterator;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;
import secondaryprocess.datamanipulation.SortAndFilterTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import secondaryprocess.datamanipulation.clsSecondarySpatialTools;
import datatypes.helpstructures.clsPair;
import datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2013, 11:42:12
 * 
 */
public class DataExtractionTools {
    
    /**
     * Get all instances in one image (perceived image), which match the instances of another image (supportive image) and provide an importance for them
     *
     * @author wendt
     * @since 01.10.2013 11:43:28
     *
     * @param poPerceivedImage
     * @param poSupportiveImage
     * @param pnImportance
     * @return
     */
    public static ArrayList<clsPair<Double, clsWordPresentationMesh>> getPerceivedImageEntitiesFromImage(clsWordPresentationMesh poPerceivedImage, clsWordPresentationMesh poSupportiveImage, double pnImportance) {
        //The instances from the perception are added
        ArrayList<clsPair<Double, clsWordPresentationMesh>> oResult = new ArrayList<clsPair<Double, clsWordPresentationMesh>>();
        
        //Get all positions from the supportive image
        ArrayList<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> oSupportiveImageEntityList = clsSecondarySpatialTools.getEntityPositionsInImage(poSupportiveImage);
        
        //Get all positions from the perceived image
        ArrayList<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> oPerceivedImageEntityList = clsSecondarySpatialTools.getEntityPositionsInImage(poPerceivedImage);      
        
        ListIterator<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> oPerceivedImageIterator = oPerceivedImageEntityList.listIterator();
        //Find these entities in the perception
        for (clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oSupportiveImageEntity : oSupportiveImageEntityList) {
            
            while (oPerceivedImageIterator.hasNext()) {
                clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oPerceivedEntity = oPerceivedImageIterator.next();
                
                //Check if theses entities are the same
                if (oSupportiveImageEntity.a.getMoDS_ID() == oPerceivedEntity.a.getMoDS_ID()) {
                    //If yes, then the entity position has to be equal
                    double rDistance  = clsSecondarySpatialTools.getDistance(oSupportiveImageEntity.a, oPerceivedEntity.a);
                    
                    //Only if the focused entities are found, they are added to the list
                    if (rDistance==0.0) {
                        //Add to result list
                        oResult.add(new clsPair<Double, clsWordPresentationMesh>(pnImportance, oPerceivedEntity.a));
                        
                        //Delete from this list
                        oPerceivedImageIterator.remove();
                    }
                }
            }   
        }
        
        return oResult;
    }
    
    /**
     * Filter a PI for elements, which are associted with the goals in a list. 
     * Remove all entities, which are not selected 
     * 
     * 
     * 
     * (wendt)
     *
     * @since 15.08.2011 22:30:44
     *
     * @param poImage - The image, which shall be filtered
     * @param poGoalList  - All objects, which shall be kept in the perceived image, are put here. 
     * @return
     */
    public static void filterImageElementsBasedOnPrioritizedGoals(clsWordPresentationMesh poImage, ArrayList< clsWordPresentationMesh> poEntityList) {
        ArrayList<clsWordPresentationMesh> oEntitiesToKeepInPI = new ArrayList<clsWordPresentationMesh>();
        
        //2 cases: entities from PI, entities from complete images
        for (clsWordPresentationMesh oEntity : poEntityList) {
            //Check if the entity already exists and add it if not
            if (oEntitiesToKeepInPI.contains(oEntity)==false) {     
                oEntitiesToKeepInPI.add(oEntity);
            }
        }
        
        //Add the self to the image
        oEntitiesToKeepInPI.add(clsMeshTools.getSELF(poImage));
        
        //Remove all other entities
        SortAndFilterTools.removeNonFocusedEntities(poImage, oEntitiesToKeepInPI);
    }
    

}
