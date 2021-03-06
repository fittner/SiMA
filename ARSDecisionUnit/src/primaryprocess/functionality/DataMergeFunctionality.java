/**
 * CHANGELOG
 *
 * 05.10.2013 wendt - File created
 *
 */
package primaryprocess.functionality;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import base.datatypes.clsThingPresentationMesh;
import primaryprocess.algorithm.EntityAlgorithmTools;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 05.10.2013, 13:51:56
 * 
 */
public class DataMergeFunctionality {
    
    private static Logger log = clsLogger.getLog("Tools");
    /**
     * Merge DMs are done for all associated memories too
     *
     * @since 12.07.2011 16:15:47
     *
     * @param oInput
     * @return
     */
    public static clsThingPresentationMesh mergeDriveMeshesOfMesh(clsThingPresentationMesh poInput) {
        clsThingPresentationMesh oRetVal = poInput;
        
        //RetVal is changed and returned. The images of oImages are for oRetVal
        ArrayList<clsThingPresentationMesh> oEntityList = clsMeshTools.getAllTPMEntities(oRetVal, 4);
        
        for (clsThingPresentationMesh oEntity: oEntityList) {
            log.trace("Entity before merge {}. Number of associations {}", oEntity, oEntity.getExternalAssociatedContent().size());
            EntityAlgorithmTools.mergeDriveMeshesForObject(oEntity);
            log.trace("Entity after merge {}. Number of associations {}", oEntity, oEntity.getExternalAssociatedContent().size());
        }
        return oRetVal;
    }
}
