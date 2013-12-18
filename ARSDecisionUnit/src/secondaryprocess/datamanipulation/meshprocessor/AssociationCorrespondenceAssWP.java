/**
 * CHANGELOG
 *
 * 08.10.2013 wendt - File created
 *
 */
package secondaryprocess.datamanipulation.meshprocessor;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import base.datatypes.clsAssociationWordPresentation;
import base.datatypes.clsSecondaryDataStructure;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 08.10.2013, 09:45:20
 * 
 */
public class AssociationCorrespondenceAssWP {

    private final clsAssociationWordPresentation fromAss;
    private final clsWordPresentationMesh fromKeeperElement;
    
    private clsSecondaryDataStructure toKeeperElement;
    
    private int score;
    
    private static final Logger log = clsLogger.getLog("Tools");
    
    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 08.10.2013 09:54:14
     *
     * @param fromAss
     */
    public AssociationCorrespondenceAssWP(clsAssociationWordPresentation fromAss) {
        this.fromAss = fromAss;
        this.fromKeeperElement = (clsWordPresentationMesh) this.fromAss.getLeafElement();
        
    }
    
    private clsWordPresentationMesh findWPMInList(clsWordPresentationMesh searchWPM, ArrayList<clsWordPresentationMesh> correspondigMeshList) {
        clsWordPresentationMesh result = clsMeshTools.getNullObjectWPM();
        
        for (clsWordPresentationMesh wpm :  correspondigMeshList) {
            if (searchWPM.isEquivalentDataStructure(wpm)==true) {
                result = wpm;
                break;
            }
        }
        
        return result;
    }

    /**
     * @since 08.10.2013 10:10:21
     * 
     * @return the score
     */
    public int getScore() {
        return score;
    }

    public void transferElements(ArrayList<clsWordPresentationMesh> correspondigMeshList) {
        //Renew corresponding list
        this.toKeeperElement = findWPMInList(fromKeeperElement, correspondigMeshList);
        if (this.toKeeperElement==null) {
            //The secondary structure, which should keep the association does not exist
            log.warn("Corresponding element for {} does not exist", fromKeeperElement);
        } else {
            //Check if the toKeeperElement have an association WP
            //If yes, do nothing, if no transfer association
            clsThingPresentationMesh connectedTPM = clsMeshTools.getPrimaryDataStructureOfWPM((clsWordPresentationMesh) toKeeperElement);
            if (connectedTPM.isNullObject()==true) {
                clsMeshTools.moveAssociation((clsWordPresentationMesh)toKeeperElement, (clsWordPresentationMesh)fromKeeperElement, this.fromAss, true);
            }
        }
    }
    
    
    
    
}
