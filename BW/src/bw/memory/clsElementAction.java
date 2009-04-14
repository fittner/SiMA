package bw.memory;

import bw.memory.tempframework.*;
import bw.memory.tempframework.enumTypeBrainAction;
import bw.memory.tempframework.enumTypeTrippleState;

/**
 * The class clsElementAction represents the feature element action. It represents one action (clsAction) and implements the additional functionalities inherited from the abstract class clsFeatureElement $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsElementAction extends clsFeatureElement  {
	private clsAction moAction;
	private boolean mnTrigger; // Trigger: is set if this Action caused trigger encoding
	
	public clsElementAction(clsAction poAction) {
		super();
		moAction = poAction;
		mnTrigger = false;
	}
	public clsAction getAction() {
		return moAction;
	}
	
	public void presetTrigger() {
		mnTrigger = true;
		moSalience.set(1);
	}
	/**
	 * Checks, whether this action is the same as the one indicated in the parameter. Is called with every previous action.
	 * If the same action was executed in the previous situation, encoding is not triggered!!!
	 */
	public boolean triggerEncoding(clsFeatureElement poPrevAction){
		// this ElementAction is called with every previous Action
		clsElementAction oPrevElemAction = (clsElementAction)poPrevAction;		
		if(mnTrigger){
			if(moAction.mnId == oPrevElemAction.getAction().mnId) {
				// this action did occur in the previous situtaion -> no trigger and not salient
				mnTrigger = false;
				moSalience.set(0);
				decaySalience(poPrevAction);
			}
		}
		if(moAction.meAccomplished == enumTypeTrippleState.TTRIPPLE_TRUE) {
//			moSalience.set(1);
//			mnTrigger = true;
				  // if Action is accomplished
				  // hier ist keine Action accomplished (außer call for help...) -> immer -1; 
				  // z.B bei promenade ist jeweils LastAction accomplished.
		}
		if(mnTrigger) {
			//System.out.println("Triggering Action: "+moAction.mnId+ "  - Salience = " + moSalience.get());
		}
		return mnTrigger;
	}
	public clsMatchFeatureElement getMatch(clsFeatureElement poCueAction) {
		// this ElementAction is called with every previous Action
		if( checkIfSameType(poCueAction) ) {
			// this action did occur in the cue 
			return new clsMatchFeatureElement(poCueAction, 1);
		}
		return null;
	}
	
	/**
	 * Checks whether this action is of the same type as the one indicated in the parameter
	 */
	public boolean checkIfSameType(clsFeatureElement poFeatElem) {
		if(poFeatElem instanceof clsElementAction) {
			clsElementAction oAction = (clsElementAction)poFeatElem;
			if(moAction.mnId == oAction.getAction().mnId) {
				return true;
			}
		}
		return false;
	}
	public String toString() {
		return enumTypeBrainAction.getString( moAction.mnId ) + "\n";
	}
}
