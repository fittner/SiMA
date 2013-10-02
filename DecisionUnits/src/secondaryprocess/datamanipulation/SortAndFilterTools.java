/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package secondaryprocess.datamanipulation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2013, 11:56:38
 * 
 */
public class SortAndFilterTools {
    /**
     * Removes all entities from the image, which are not in the input list
     * 
     * (wendt)
     *
     * @since 09.07.2012 14:43:03
     *
     * @param poImage
     * @param poEntitiesToKeepInPI
     */
    public static void removeNonFocusedEntities(clsWordPresentationMesh poImage, ArrayList<clsWordPresentationMesh> poEntitiesToKeepInPI) {
        //Remove all entities from the PI, which are not part of the input list
        ArrayList<clsWordPresentationMesh> oRemoveEntities  = clsMeshTools.getOtherInternalImageAssociations(poImage, poEntitiesToKeepInPI);
        for (clsWordPresentationMesh oE : oRemoveEntities) {
            clsMeshTools.removeAssociationInObject(poImage, oE);        //Use this instead as it ONLY removes the association of the entity with the PI
            clsMeshTools.removeAssociationInObject(oE, poImage);
            //clsMeshTools.deleteObjectInMesh(oE);
            //Remove for the primary process too, i. e remove all associationtime in the external associations
            clsThingPresentationMesh oTPM = clsMeshTools.getPrimaryDataStructureOfWPM(oE);
            clsMeshTools.removeAllTemporaryAssociationsTPM(oTPM);
        }
    }
}
