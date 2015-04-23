/**
 * CHANGELOG
 *
 * 03.10.2013 wendt - File created
 *
 */
package secondaryprocess.algorithm.planning;

import memorymgmt.enums.eActionType;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import base.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 03.10.2013, 14:40:43
 * 
 */
public class ActionPlanAlgorithm {
    public static clsWordPresentationMesh getActionOfType(clsWordPresentationMesh action, eActionType actionType) {
        clsWordPresentationMesh result = clsMeshTools.getNullObjectWPM();
        
        eActionType currentActionType = clsActionTools.getActionType(action);
        
        if (currentActionType.equals(actionType)==true) {
            result = action;
        }
        
        return result;
    }
}
