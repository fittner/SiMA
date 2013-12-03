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
    
    public static eAction extractActionFromAimOfDrive(clsWordPresentationMeshAimOfDrive oAimOfDrive) {
        clsWordPresentationMesh oRawAction = null;
        eAction oAimAction = null;
        
        oRawAction = oAimOfDrive.getAssociatedDriveAimAction();
        if(oRawAction != null && oRawAction.isNullObject() == false) {
            if(oRawAction.getContentType() == eContentType.ACTION) {
                try {
                    oAimAction = eAction.valueOf(oRawAction.getContent());
                } catch (IllegalArgumentException e) {
                    log.error("Extracted action mesh {} could not be converted to eAction", e);
                }
            } else {
                log.warn(oAimOfDrive.toString() + " returned an aim action that is not of content type eContentType.ACTION.");
            }
        }
        
        return oAimAction;
    }
    
    public static eAction getAimOfDriveActionByName(ArrayList<clsWordPresentationMeshAimOfDrive> poAimOfDrives, String oName) {
        //Variables for each lookup step might be slower, but are more comfortable for debugging
        log.debug("getAimOfDriveActionByName({}, {})", poAimOfDrives, oName);
        String oAimGoalName;
        eAction oAimAction = null;
        
        for(clsWordPresentationMeshAimOfDrive oAim : poAimOfDrives) {
            log.debug("\tchecking aim {}", oAim);
            oAimGoalName = oAim.getGoalName();
            if(oAimGoalName.equals(oName)) {
                oAimAction = extractActionFromAimOfDrive(oAim);
                log.debug("\t\tmatch found - has action {}", oAimAction);
            }
        }
        
        return oAimAction;
    }
    
    public static ArrayList<eAction> extractActionsFromAimOfDrives(ArrayList<clsWordPresentationMeshAimOfDrive> poAimOfDrives) {
        
        log.debug("extractActionsFromAimOfDrives({})", poAimOfDrives);
        
        ArrayList<eAction> oActions = new ArrayList<eAction>();
        
        for(clsWordPresentationMeshAimOfDrive oAim : poAimOfDrives) {
            log.debug("\tExtracting action from " + oAim.toString());
            eAction oAimAction = extractActionFromAimOfDrive(oAim);
            log.debug("\t\tExtraced action: " + oAimAction);
            oActions.add(oAimAction);
        }
        
        return oActions;
    }
}
