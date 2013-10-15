/**
 * CHANGELOG
 *
 * 15.10.2013 Kollmann - File created
 *
 */
package secondaryprocess.algorithm.aimofdrives;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshAimOfDrive;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eContentType;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 15.10.2013, 13:06:14
 * 
 */
public class clsAimOfDrivesTools {
    private static Logger log = clsLogger.getLog("DecisionPreparation");
    
    public static ArrayList<eAction> extractActionsFromAimOfDrives(ArrayList<clsWordPresentationMeshAimOfDrive> poAimOfDrives) {
        
        log.debug("extractActionsFromAimOfDrives({})", poAimOfDrives);
        
        ArrayList<eAction> oActions = new ArrayList<eAction>();
        
        for(clsWordPresentationMeshAimOfDrive oAim : poAimOfDrives) {
            log.debug("\tExtracting action from " + oAim.toString());
            clsWordPresentationMesh oRawAction = oAim.getAssociatedDriveAimAction();
            if(oRawAction != null && oRawAction.isNullObject() == false) {
                if(oRawAction.getMoContentType() == eContentType.ACTION) {
                    try {
                        eAction oAimAction = eAction.valueOf(oRawAction.getMoContent());  
                        log.debug("\t\tExtraced action: " + oAimAction);
                        oActions.add(oAimAction);
                    } catch (IllegalArgumentException e) {
                        log.error("Extracted action mesh {} could not be converted to eAction", e);
                    }
                } else {
                    log.warn(oAim.toString() + " returned an aim action that is not of content type eContentType.ACTION.");
                }
            } else {
                log.debug(oAim.toString() + " has no associated aim action");
            }
        }
        
        return oActions;
    }
}
