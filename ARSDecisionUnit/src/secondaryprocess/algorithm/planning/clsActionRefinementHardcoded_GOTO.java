/**
 * CHANGELOG
 *
 * 10.02.2014 Kollmann - File created
 *
 */
package secondaryprocess.algorithm.planning;

import java.security.InvalidParameterException;

import org.slf4j.Logger;

import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.functionality.decisionpreparation.clsOrientationReasoner;
import base.datatypes.clsWordPresentationMesh;
import logger.clsLogger;
import memorymgmt.enums.eAction;


/**
 * DOCUMENT (Kollmann) - Simple subclass of clsActionRefinement that provides a hardcoded solution
 *                       for refining the action GOTO to either TURN_LEFT, MOVE_FORWARD or TURN_RIGHT
 *                       
 *                       In later versions this could be replaced by refinements loaded from the agents
 *                       memory.
 * 
 * @author Kollmann
 * 10.02.2014, 20:15:28
 * 
 */
public class clsActionRefinementHardcoded_GOTO extends clsActionRefinement {
    private static final Logger moLogger = clsLogger.getLog("planning");
    
    /**
     * DOCUMENT - Return the target object for the GOTO action
     *
     * @author Heinrich Kemmler
     * @since 10.02.2014 21:22:46
     *
     * @param poActionWPM: the WPM of the GOTO action
     * @return the WPM of the target object
     */
    private clsWordPresentationMesh getActionObject(clsWordPresentationMesh poActionWPM) {
        return clsActionTools.getActionObject(poActionWPM);
    }
    
    
    /**
     * DOCUMENT - Replace the old action with a new one - currently this method only changes the content string
     *            of the action WPM (this means that the new action, like TURN_LEFT, still has a association to
     *            an action object on it)
     *            
     *            Later versions could completely remove the action WPM from its neighbors an replace it with
     *            a completely new mesh.
     *
     * @author Heinrich Kemmler
     * @since 10.02.2014 21:23:31
     *
     * @param poOldActionWPM
     * @param poNewActionWPM
     */
    private void replaceAction(clsWordPresentationMesh poOldActionWPM, clsWordPresentationMesh poNewActionWPM) {
        eAction oNewAction = eAction.valueOf(poNewActionWPM.getContent().toString());
        
        if(!oNewAction.equals(eAction.NULLOBJECT) && !oNewAction.equals(eAction.NONE)) {
            clsActionTools.setAction(poOldActionWPM, oNewAction);
        }
    }
    
    /* (non-Javadoc)
     *
     * @since 10.02.2014 21:25:22
     * 
     * @see secondaryprocess.algorithm.planning.clsActionRefinement#refine(base.datatypes.clsWordPresentationMesh)
     */
    @Override
    public void refine(clsWordPresentationMesh poActionWPM) {
        try
        {
            clsWordPresentationMesh oRefinedAction = null;
            
            clsWordPresentationMesh oActionObject = getActionObject(poActionWPM);
            oRefinedAction = clsOrientationReasoner.getInstance().getActionToEntity(oActionObject);
    
            replaceAction(poActionWPM, oRefinedAction);
        } catch (InvalidParameterException e) {
            moLogger.error("Could not refine action " + poActionWPM + " because the target entity has no position associated");
        }
    }
    
}
