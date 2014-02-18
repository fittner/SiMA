/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.actioncodeletes;

import java.util.ArrayList;

import memorymgmt.enums.eAction;
import memorymgmt.enums.ePredicate;
import base.datatypes.clsWordPresentationMesh;
import secondaryprocess.algorithm.planning.clsTEMPPlannerAW;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import secondaryprocess.functionality.decisionpreparation.clsCodelet;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:05:38
 * 
 */
public abstract class clsActionCodelet extends clsCodelet {

	protected clsWordPresentationMesh moAction;
	protected clsTEMPPlannerAW moExternalActionPlanner;
	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 23.09.2012 11:55:29
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsActionCodelet(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		
		 moExternalActionPlanner = clsTEMPPlannerAW.getPlanner();
		
	}
	
	protected void generateAction(eAction poAction) {
		moAction = clsActionTools.createAction(poAction);
	}
	
	
	/**
	 * DOCUMENT - Generate a new action WPM and store it in the codelet - based on an existing action WPM.
	 *            The codelet may NOT hold an instance of the original action WPM, since that could make all changes on the action appear
	 *            in the next processing cycle as part of the image (e.g. if you change action of an image from GOTO to TURN_LEFT, in the
	 *            next cycle you will get TURN_LEFT as associated action for the image)
	 *            
	 *            Therefore, create a new action WPM, based in the action WPM provided as parameter
	 *
	 * @author Kollmann
	 * @since 18.02.2014 12:09:34
	 *
	 * @param poAction: the original action WPM
	 */
	protected void generateAction(clsWordPresentationMesh poAction) {
	    //Kollmann: steps:
	    // 1) if action has refinement, extract it
	    // 2) if action has object, extract it
	    // 3) create new action WPM

	    eAction oAction = eAction.valueOf(poAction.getContent());
	    
	    //use action refinement if possible
	    clsWordPresentationMesh oActionRefinement = clsMeshTools.getUniquePredicateWPM(poAction, ePredicate.HASACTIONREFINEMENT);
	    if(oActionRefinement != null && !oActionRefinement.isNullObject()) {
	        oAction = eAction.valueOf(oActionRefinement.getContent());
	    }
	    
	    ArrayList<clsWordPresentationMesh> oActionObjects = clsMeshTools.getNonUniquePredicateWPM(poAction, ePredicate.HASACTIONOBJECT);
	    if(oActionObjects.size() > 0) {
	        moAction = clsActionTools.createAction(oAction, oActionObjects.get(0));
	    } else {
	        moAction = clsActionTools.createAction(oAction);
	    }
	}
	
	protected void setActionAssociationInGoal() {
	    this.moGoal.setAssociatedPlanAction(this.moAction);
		
	}

	
}
