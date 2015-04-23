/**
 * CHANGELOG
 *
 * 10.02.2014 Kollmann - File created
 *
 */
package secondaryprocess.algorithm.planning;

import java.security.InvalidParameterException;

import logger.clsLogger;
import memorymgmt.enums.eAction;
import memorymgmt.enums.ePredicate;

import org.slf4j.Logger;

import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import secondaryprocess.functionality.decisionpreparation.clsOrientationReasoner;
import base.datatypes.clsAssociation;
import base.datatypes.clsWordPresentationMesh;


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
public class clsActionRefinementHardcoded_FLEE implements itfActionRefinement {
    private static final Logger moLogger = clsLogger.getLog("planning");

    clsWordPresentationMesh moEnvironmentalImage = null;
    
    public clsActionRefinementHardcoded_FLEE(clsWordPresentationMesh poEnvironmentalImage) {
        moEnvironmentalImage = poEnvironmentalImage;
    }
    
    private clsWordPresentationMesh findActionObjectInstance(clsWordPresentationMesh poActionObjectType) {
        clsWordPresentationMesh oActionObjectInstance = null;
        clsWordPresentationMesh oEnvironmentalEntity = null;
        
        for(clsAssociation oEnvironmentalEntityAssociation : moEnvironmentalImage.getInternalAssociatedContent()) {
            if(oEnvironmentalEntityAssociation.getLeafElement() instanceof clsWordPresentationMesh) {
                oEnvironmentalEntity = (clsWordPresentationMesh) oEnvironmentalEntityAssociation.getLeafElement();
    
                //Kollmann: lazy compare
                if(oEnvironmentalEntity.getDS_ID() == poActionObjectType.getDS_ID()) {
                    oActionObjectInstance = oEnvironmentalEntity;
                }
            } else {
                moLogger.error("Environmental image contains association that does not point to WPM: {}", oEnvironmentalEntityAssociation);
            }
        }
        
        return oActionObjectInstance;
    }
    
    private clsWordPresentationMesh getActionObjectType(clsWordPresentationMesh poActionWPM) {
        return clsActionTools.getActionObject(poActionWPM);
    }
    
    /**
     * DOCUMENT - Return the target object for the GOTO action
     *
     * @author Kollmann
     * @since 10.02.2014 21:22:46
     *
     * @param poActionWPM: the WPM of the GOTO action
     * @return the WPM of the target object
     */
    private clsWordPresentationMesh getActionObject(clsWordPresentationMesh poActionWPM) {
        return findActionObjectInstance(getActionObjectType(poActionWPM));
    }
    
    
    /**
     * DOCUMENT - Replace the old action with a new one - currently this method only changes the content string
     *            of the action WPM (this means that the new action, like TURN_LEFT, still has a association to
     *            an action object on it)
     *            
     *            Later versions could completely remove the action WPM from its neighbors an replace it with
     *            a completely new mesh.
     *
     * @author Kollmann
     * @since 10.02.2014 21:23:31
     *
     * @param poOldActionWPM
     * @param poNewActionWPM
     */
    private void replaceAction(clsWordPresentationMesh poOldActionWPM, clsWordPresentationMesh poNewActionWPM) {
        eAction oNewAction = eAction.valueOf(poNewActionWPM.getContent().toString());
        
        clsMeshTools.setUniquePredicateWPM(poOldActionWPM, ePredicate.HASACTIONREFINEMENT, poNewActionWPM, false);
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
            
            clsWordPresentationMesh oActionObjectType = getActionObjectType(poActionWPM); 
            clsWordPresentationMesh oActionObjectInstance = findActionObjectInstance(oActionObjectType);
            if(oActionObjectInstance == null) {
                if(oActionObjectType == null) {
                    moLogger.error("Action " + poActionWPM + " can not be refined via FLEE refinement, as the action has no action object");
                } else
                {
                    moLogger.info("The action object " + oActionObjectType + " for action " + poActionWPM + " could not be found in list of focused objects - possibly search for it");
                    oRefinedAction = clsActionTools.createAction(eAction.SEARCH1);
                }
            } else { 
                oRefinedAction = clsOrientationReasoner.getInstance().getActionToEntity(oActionObjectInstance);
            }
    
            replaceAction(poActionWPM, oRefinedAction);
        } catch (InvalidParameterException e) {
            moLogger.error("Could not refine action " + poActionWPM + " because the target entity has no position associated");
        }
    }
    
}
