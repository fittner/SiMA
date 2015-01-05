/**
 * CHANGELOG
 *
 * 08.02.2014 Kollmann - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation;

import java.security.InvalidParameterException;

import org.slf4j.Logger;

import base.datatypes.clsWordPresentationMesh;
import logger.clsLogger;

/**
 * DOCUMENT (Kollmann) - Very simple reasoner class for orientation reasoning.
 *                       Current usage:
 *                          - get the instance of the class
 *                          - call getActionToEntity to get a WPM that contains the neccessary step(s)
 *                            required to orient towards the target
 *                            (What these steps look like depens on the clsOrientation class used, for
 *                             different kind of orientation reasoning, use different clsOrientation
 *                             implementations) 
 * 
 * @author Kollmann
 * 08.02.2014, 20:39:37
 * 
 */
public class clsOrientationReasoner {
    private static clsOrientationReasoner moInstance = new clsOrientationReasoner();
    private static final Logger moLogger = clsLogger.getLog("OrientationReasoner");
    
    //remove access to constructor and copy constructor (singleton pattern)
    private clsOrientationReasoner() {}
    private clsOrientationReasoner(clsOrientationReasoner poOrientationReasoner) {}
    
    /**
     * DOCUMENT - Get the current instance of the class
     *
     * @author Kollmann
     * @since 10.02.2014 20:54:23
     *
     * @return
     */
    public static synchronized clsOrientationReasoner getInstance() {
        if(moInstance == null) {
            moLogger.error("Could not find instance");
            throw new RuntimeException("Orientation Reasoner broken: instance missing");
        }
        
        return moInstance;
    }
    
    /**
     * DOCUMENT - Create and return an orientation object for the given target entity
     *
     * @author Kollmann
     * @since 10.02.2014 20:54:54
     *
     * @param poTargetEntity: the entity the agent should orient to
     * @return
     */
    protected clsOrientation extractOrientation(clsWordPresentationMesh poTargetEntity) {
        return new clsOrientation(poTargetEntity);
    }
    
    /**
     * DOCUMENT - Use the provided orientation class to create a WPM containing the necessary
     *            information to orient towards an entity (which entity is decided during
     *            creation of the orientation object, which happens in method
     *            extractOrientation() )   
     *
     * @author Kollmann
     * @since 10.02.2014 20:55:55
     *
     * @param poOrientation: orientation object that can provide the neccessary steps for
     *                       orienting towards a (previously defined) entity
     * @return
     */
    protected clsWordPresentationMesh calculateChange(clsOrientation poOrientation) {
        return poOrientation.towards();
    }
    
    /**
     * DOCUMENT - Create and return a WPM containing the necessary action(s) to orient
     *            an agent towards the entity provided in poTargetEntity
     *
     * @author Kollmann
     * @since 10.02.2014 20:58:50
     *
     * @param poTargetEntity: WPM of the entity the agent should orient to
     * @return
     */
    public clsWordPresentationMesh getActionToEntity(clsWordPresentationMesh poTargetEntity) {
        if(poTargetEntity == null || poTargetEntity.isNullObject()) {
            throw new InvalidParameterException("WPM parameter for method clsOrientationReasoner::getActionToEntity() is null or NULLOBJECT");
        }
        
        clsOrientation oPosition = extractOrientation(poTargetEntity);
        
        
        return calculateChange(oPosition);
    }
    
    /**
     * DOCUMENT - Create and return a WPM containing the necessary action(s) to orient
     *            an agent away from the entity provided in poTargetEntity
     *
     * @author Kollmann
     * @since 05.01.2015 13:28:00
     *
     * @param poTargetEntity: WPM of the entity the agent should orient away from
     * @return
     */
    public clsWordPresentationMesh getActionAwayFromEntity(clsWordPresentationMesh poTargetEntity) {
        if(poTargetEntity == null || poTargetEntity.isNullObject()) {
            throw new InvalidParameterException("WPM parameter for method clsOrientationReasoner::getActionToEntity() is null or NULLOBJECT");
        }
        
        clsOrientation oPosition = extractOrientation(poTargetEntity);
        
        
        return oPosition.away();
    }
}
