package students.borer.episodicMemory;

import students.borer.episodicMemory.tempframework.clsAction;
import students.borer.episodicMemory.tempframework.clsActionContainer;

/**
 * The class clsFeatureActions represents the feature actions. It inherites the basic functionalities which are equal for each feature and implements specific functionalities for the feature actions. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsFeatureActions extends clsFeature{
	private clsActionContainer moContainerActions;
	
	/**
	 * Instantiates a new feature actions with the respective elements indicated in the container in the parameter. If
	 * triggering encoding or spontaneous retrieval for this feature should be disabled, mnTriggerEncodingEnabled respectively
	 * mnSpontaneousRetrievalEnabled have to be set false.
	 * @param poActionList The container with the individual actions of this situation
	 */
	@SuppressWarnings("unchecked")
	public clsFeatureActions(clsActionContainer poActionList) {
		super();
		moContainerActions = poActionList;
		for(int i=0; i<poActionList.size(); i++){
			clsAction oAction = (clsAction)poActionList.get(i);
			moFeatureElements.add( new clsElementAction( oAction ));
		}
	}
	
	/**
	 * Returns the initially delivered actions in a container (clsActionContainer)
	 */
	@Override
	public Object getContainer() {
		return moContainerActions;
	}
	
	/**
	 * Overloaded method from clsFeature (the ordering of actions is different in each situation). Triggers encoding if
	 * any of the current actions did not occur in the previous situation
	 * @param poPrevFeature The feature actions of the previous situation
	 * @return true, if encoding is triggered; otherwise false
	 */
	@Override
	public boolean triggerEncoding(clsFeature poPrevFeature) {
		clsFeatureActions oPrevActions = (clsFeatureActions)poPrevFeature;
		boolean nTrigger = false;
		// checks every current Action with every previous action
		for(int i=0; i<moFeatureElements.size(); i++) {
			clsElementAction oAction = (clsElementAction)moFeatureElements.get(i);
			// checks if this action did occur in the previous actionlist
			// if it occured previously -> mnTrigger is set false!!!
			oAction.presetTrigger();
			nTrigger = false;
			for(int j=0; j<oPrevActions.getFeatureElements().size(); j++) {
				clsElementAction oPrevAction = (clsElementAction)oPrevActions.getFeatureElements().get(j);
				nTrigger = oAction.triggerEncoding( oPrevAction );
			}
			// The salience of the feature is the maximum salience value of the consisting elements
			float rSalience = oAction.moSalience.get();
			if(moSalience.get() < rSalience) moSalience.set(rSalience);			  
		}
		return nTrigger;
	}
	
	/**
	 * Overloaded method from clsFeature (the ordering of actions is different in each situation). Checks, whether the
	 * actions indicated in the cue appear in this feature actions
	 * @return A clsMatchFeature object with the matching result	 
	 */
	@Override
	public clsMatchFeature getMatch(clsFeature poCueFeature) {
		clsFeatureActions oCueActions = (clsFeatureActions)poCueFeature;
		clsMatchFeature oFeatureMatch = new clsMatchFeature(oCueActions);
		
		int nCueSize = oCueActions.getFeatureElements().size();
		for(int i=0; i<nCueSize; i++) {
			clsElementAction oCueAction = (clsElementAction)oCueActions.getFeatureElements().get(i);
			clsMatchFeatureElement oElementMatch = null;
			for(int j=0; j<moFeatureElements.size(); j++) {
				//checks whether the oCueAction appears in the feature actions
				clsElementAction oAction = (clsElementAction)moFeatureElements.get(j);
				oElementMatch = oAction.getMatch( oCueAction );
				if(oElementMatch != null) {
					oFeatureMatch.addElementMatch(oElementMatch);
					break; // action from the cue found in the feature -> break search
				}
			}
			if(oElementMatch == null){
				// action from the cue not found in the actions list of this feature
				oFeatureMatch.addElementMatch( new clsMatchFeatureElement(oCueAction, 0) );
			}
			
		}
		return oFeatureMatch;
	}
	
	/**
	 * Overloaded method from clsFeature. See clsAction::getMatch()
	 */
	@Override
	public clsMatchFeature getDeliberateMatch(clsFeature poCueFeature) {
		return getMatch(poCueFeature);
	}
	
	@Override
	public boolean checkIfSameType(clsFeature poFeature) {
		if(poFeature instanceof clsFeatureActions) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		String oRet = "";
		for (int i=0; i<moFeatureElements.size(); i++) {
			clsElementAction oAction = (clsElementAction)moFeatureElements.get(i);
			oRet+=oAction.toString();
		}
		return oRet;
//		return "Actions";
	}

}
