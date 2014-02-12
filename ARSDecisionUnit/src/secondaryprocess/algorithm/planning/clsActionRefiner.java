/**
 * CHANGELOG
 *
 * 10.02.2014 Kollmann - File created
 *
 */
package secondaryprocess.algorithm.planning;

import logger.clsLogger;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import org.slf4j.Logger;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import secondaryprocess.algorithm.planning.itfActionRefinement;

/**
 * DOCUMENT (Kollmann) - This class can be used to refine actions.
 *                       For example the action GOTO can be refined into either TURN_LEFT,
 *                       MOVE_FORWARD or TURN_RIGHT, depending on the action object and the
 *                       orientation reasoning used.
 *                       
 *                       The class looks up, or creates, a refinement object for a given action.
 *                       The current implementation only refines the action GOTO by using a
 *                       hardcoded refinement subclass. Later version could dynamically load
 *                       refinement strategies from the agents memory. 
 * 
 * @author Kollmann
 * 10.02.2014, 20:05:32
 * 
 */
public class clsActionRefiner {
    private Logger moLogger = clsLogger.getLog("planning");
    private clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTermMemory = null;
    private clsWordPresentationMesh moEnvironmentalImage = null;
    
    //remove access to constructor and copy constructor
    private clsActionRefiner() {}
    private clsActionRefiner(clsActionRefiner poOrientationReasoner) {}
    
    public clsActionRefiner(clsShortTermMemory<clsWordPresentationMeshMentalSituation> poShortTermMemory, clsWordPresentationMesh poEnvironmentalImage) {
        moShortTermMemory = poShortTermMemory;
        moEnvironmentalImage = poEnvironmentalImage;
    }
    
    public clsActionRefiner(clsShortTermMemory<clsWordPresentationMeshMentalSituation> poShortTermMemory, clsWordPresentationMesh poEnvironmentalImage, Logger poNewLogger) {
        this(poShortTermMemory, poEnvironmentalImage);
        replaceLogger(poNewLogger);
    }
    
    /**
     * DOCUMENT - By default the class uses the logger "planning", if required this logger can
     *            be replaced, for example by the callers logger.
     *
     * @author Heinrich Kemmler
     * @since 10.02.2014 21:11:24
     *
     * @param poNewLogger: the new logger
     * @return the old logger
     */
    public Logger replaceLogger(Logger poNewLogger) {
        Logger oOldLogger = moLogger;
        moLogger = poNewLogger;
        return oOldLogger;
    }
    
//    public static synchronized clsActionRefiner getInstance() {
//        if(moInstance == null) {
//            moInstance = new clsActionRefiner();
//            moLogger.info("Initializing clsRefiner with lazy initialization");
//        }
//        
//        return moInstance;
//    }
//    
    /**
     * DOCUMENT - Lookup or create a clsActionRefinement object that fits for the given action.
     * 
     *            Currently the class only refines the action GOTO by returning a clsActionRefinement
     *            sub class that has hardcoded how to refine GOTO.
     *
     * @author Heinrich Kemmler
     * @since 10.02.2014 21:12:21
     *
     * @param poActionWPM: the WPM of the action to be refined
     * @return a refinement that can be used to further refine the provided action 
     */
    public itfActionRefinement getActionRefinementFor(clsWordPresentationMesh poActionWPM) {
        itfActionRefinement oActionRefinement = null;
        
        if(poActionWPM.getContent().toString().equals("GOTO")) {
            oActionRefinement = new clsActionRefinementHardcoded_GOTO(moEnvironmentalImage); 
        } else {
         //   
        }
        
        return oActionRefinement; 
    }
    
    /**
     * DOCUMENT - Simple function to determine if there is a refinement available for a provided action WPM
     * 
     *            The refineAction() method should check the availability of a refinement itself, this function
     *            is intended to allow quick lookup for certain WPMs (for example via an index).
     *            Of course refineAction() can use this method if desired.
     *
     * @author Heinrich Kemmler
     * @since 10.02.2014 21:14:25
     *
     * @param poActionWPM: the action WPM in question
     * @return true if there is a refinement available, false otherwise
     */
    public boolean isRefineable(clsWordPresentationMesh poActionWPM) {
        return poActionWPM.getContent().toString().equals("GOTO");
    }
    
    /**
     * DOCUMENT - Refines the provided action if possible.
     *           
     *            The method refines the action 'in place', meaning that after this method, the action has either
     *            not changed at all (if there is no refinement available, or the refinement failed) or has been
     *            replaced by the refinement entirely. For example if the old action is part of a WPM, the old
     *            WPM has to be removed from the mesh completely and replaced by the new action - all meshes that
     *            can be reached from the given action WPM have to changed to use the new WPM.
     *
     * @author Heinrich Kemmler
     * @since 10.02.2014 21:16:54
     *
     * @param poActionWPM: the WPm of the action that should be refined, if possible
     */
    public void refineAction(clsWordPresentationMesh poActionWPM) {
        if(isRefineable(poActionWPM)) {
            itfActionRefinement oRefinementClass = getActionRefinementFor(poActionWPM);
            
            if(oRefinementClass != null) {
                oRefinementClass.refine(poActionWPM);
            } else {
                moLogger.warn("Could not refine action " + poActionWPM + " even thou it's considered refineable");
            }
        }
    }
}
