/**
 * CHANGELOG
 *
 * 08.02.2014 Kollmann - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation;

import java.security.InvalidParameterException;

import org.slf4j.Logger;

import logger.clsLogger;
import memorymgmt.enums.eAction;
import memorymgmt.enums.ePhiPosition;
import memorymgmt.enums.eRadius;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsEntityTools;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (Kollmann) - Very simple orientation implementation.
 *                       Orientation objects should provide information on actions neccessary
 *                       to "aim" an agent towards an entity.
 *                       
 *                       This orientation object uses discrete steps (LEFT|MIDDLE_LEFT|CENTER|MIDDLE_RIGHT|RIGHT)
 *                       as possible orientations and discrete actions (TURN_LEFT|MOVE_FORWARD|TURN_RIGHT) as
 *                       solutions.
 *                       Other orientations might use other models.
 * 
 * @author Kollmann
 * 08.02.2014, 22:19:58
 * 
 */
public class clsOrientation {
    private Logger moLogger = clsLogger.getLog("OrientationReasoner");
    clsPair<ePhiPosition, eRadius> moPosition;
    
    public void setLogger(Logger poLogger) { moLogger = poLogger; }
    
    /**
     * DOCUMENT - The constructor requires the entity the orientation should "aim" for 
     *
     * @author Kollmann
     * @since 10.02.2014 21:02:27
     *
     * @param poEntity: A WPM of the entity the orientation should aim for.
     *                  The entity needs a position as returned by the method
     *                  clsEntityTools.getPosition() (neither dimension must be null).
     *                  If the entity has no such position, an InvalidParameterException
     *                  is thrown during construction
     * @throws InvalidParameterException: thrown if ePhiPosition or eRadius of the result
     *                                    triple returned by clsEntityTools.getPosition()
     *                                    is null.
     */
    public clsOrientation(clsWordPresentationMesh poEntity) throws InvalidParameterException{
        clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oPosition = clsEntityTools.getPosition(poEntity);
        
        if(oPosition.b == null | oPosition.c == null) {
            throw new InvalidParameterException("WPM " + poEntity + " has no position.");
        }
        
        moPosition = new clsPair<ePhiPosition, eRadius>(oPosition.b, oPosition.c);
    }
    
    //disable default constructor
    private clsOrientation() {}
    
    /**
     * DOCUMENT - Create and return a WPM containing the information on how to "aim" towards the entity
     *
     * @author Kollmann
     * @since 10.02.2014 21:06:47
     *
     * @return
     */
    public clsWordPresentationMesh difference() {
        eAction oChange = eAction.NONE;
        
        switch(moPosition.a) {
            case LEFT:
            case MIDDLE_LEFT:
                oChange = eAction.TURN_LEFT;
            case RIGHT:
            case MIDDLE_RIGHT:
                oChange = eAction.TURN_RIGHT;
            case CENTER:
                oChange = eAction.MOVE_FORWARD;
            case UNKNOWNPOSITION:
                //TODO(Kollmann): add code for searching
            default:
                moLogger.warn("relative position of target entity invalid.");
        }
        
        clsWordPresentationMesh oDifferenceWPM = clsActionTools.createAction(oChange);
        
        return oDifferenceWPM;
    }
}
