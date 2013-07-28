package students.borer.episodicMemory;


import java.util.Vector;

import students.borer.episodicMemory.tempframework.clsAction;
import students.borer.episodicMemory.tempframework.clsActionContainer;
import students.borer.episodicMemory.tempframework.clsContainerCompareResults;
import students.borer.episodicMemory.tempframework.clsContainerDrive;
import students.borer.episodicMemory.tempframework.clsContainerEmotion;
import students.borer.episodicMemory.tempframework.clsDrive;
import students.borer.episodicMemory.tempframework.clsEmotion;
import students.borer.episodicMemory.tempframework.clsRuleCompareResult;

/**
 * The class clsRetrievalCue represents the retrieval cue with which a retrieval is initiated. The cue either consists of the salient features of an indicated event or of an arbitrary compostion of the features. Additionally scenarios might be indicated. Then only episodes that are realizations of these scenarios are traversed to get a retrieval. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsRetrievalCue {
	// if an event is set, the event is used as cue
	public clsEvent moEvent = null;
	
	// the corresponding features used for an arbitrary cue selection have to be set
	public clsContainerDrive moDriveList = null;
	public clsContainerEmotion moEmotionList = null;
	public clsContainerCompareResults moImageCompareResults = null;
	public clsActionContainer moActionList = null;
	
	// use optionally an scenario as cue -> only the episodes that are realizations of the scenario are traversed
	public Vector<Integer> moScenarios = null;
	
	public clsRetrievalCue() {		
	}
	
	/**
	 * To add a scenario to the cue
	 * @param poId The id of the scenario
	 */
	public void addScenarioId(Integer poId) {
		if(moScenarios == null) moScenarios = new Vector<Integer>();
		moScenarios.add(poId);
	}
	
	/**
	 * To add an action to the cue (for arbitrary cue selection)
	 * @param poAction The respective action that should be indicated in the cue
	 */
	public void addAction(clsAction poAction){
		if(moActionList == null) moActionList = new clsActionContainer();
		moActionList.add(poAction);
	}

	/**
	 * To add a TI-match to the cue (for arbitrary cue selection)
	 * @param poTI The respective TI-match that should be indicated in the cue
	 */
	public void addTImatch(clsRuleCompareResult poTI){
		if(moImageCompareResults == null) moImageCompareResults = new clsContainerCompareResults();
		moImageCompareResults.add(poTI);
	}
	
	/**
	 * To add a drive to the cue (for arbitrary cue selection)
	 * @param poDrive The respective drive that should be indicated in the cue
	 */	
	public void addDrive(clsDrive poDrive){
		if(moDriveList == null) moDriveList = new clsContainerDrive();
		moDriveList.add(poDrive);
	}

	/**
	 * To add an emotion to the cue (for arbitrary cue selection)
	 * @param poEmotion The respective emotion that should be indicated in the cue
	 */	
	public void addEmotion(clsEmotion poEmotion){
		if(moEmotionList == null) moEmotionList = new clsContainerEmotion();
		moEmotionList.add(poEmotion);
	}
	
	/**
	 * Returns the arbitrary constructed retrieval cue
	 * @return The features used as cue in an unsorted list (java.util.Vector)
	 */
	public Vector<clsFeature> getArbitraryCue() {
		Vector<clsFeature> oFeatures = null; 
		if(moEvent == null) {
			// cue with arbitrary feature selection
			oFeatures = new Vector<clsFeature>();
			if(moDriveList != null) {
				// should retrieval for drives be supported???
				if(moDriveList.size() > 0) {
					oFeatures.add( new clsFeatureDrives(moDriveList) );
				}
			}
			if(moEmotionList != null) {
				if(moEmotionList.size() > 0) {
					oFeatures.add( new clsFeatureEmotions(moEmotionList) );
				}
			}
			if(moImageCompareResults != null) {
				if(moImageCompareResults.size() > 0) {
					oFeatures.add( new clsFeatureCompareResults(moImageCompareResults) );
				}
			}
			if(moActionList != null) {
				if(moActionList.size() > 0) {
					oFeatures.add( new clsFeatureActions(moActionList) );
				}			
			}
		}		
		return oFeatures;
	}
	
	/**
	 * Returns the retrieval cue as a String for logging
	 * @return The content of the retrieval cue as a String
	 */
	@Override
	public String toString() {
		String oRet = "Cue Features: ";
		if(moEvent != null) {
			for(int i=0; i<moEvent.moFeatures.size(); i++){
				clsFeature oFeature = (clsFeature)moEvent.moFeatures.get(i);
				if(oFeature.checkIfSalient()) {
					oRet+=" - " + oFeature.toString();
				}
			}
		}
		if(moScenarios !=null) {
			oRet+=" |ScenIds: ";
			for(int i=0; i<moScenarios.size(); i++) {
				Integer oId = (Integer)moScenarios.get(i);
				oRet+=" "+oId.intValue();
			}
		}
		return oRet;
	}
}
