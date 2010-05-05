package students.borer.episodicMemory;


import java.util.Vector;

import students.borer.episodicMemory.tempframework.clsRecognitionProcess;
import students.borer.episodicMemory.tempframework.clsScenario;

/**
 * The class clsEpisode represents an episode as a realization of a scenario template. It consists of the series of events (the reference to the respective events in the memory container is stored) within which the respective scenario recognition process has been recognized. It implements the interface Comparable -- episodes are compared and may further be sorted according to the moment of their termination i.e. the occurence of the end event. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsEpisode implements Comparable {
	public Integer moEpisodeId;
	public clsEvent moStartEvent; 
	public clsEvent moEndEvent;
	/**
	 * The content of the episode (the series of events within the recognition of a scenario)
	 */
	public Vector moEventList;
	/**
	 * The context of the episode (the scenario template, the episode is a realization of)
	 */
	public clsScenario moScenario;
	public clsRecognitionProcess moRecognitionProcess;
		
/**
 * Initializes a new episode
 * @param poRecognitionProcess The scenario recognition process that has been initialized
 * @param poStartEvent The respective event that has been encoded when the scenario has been initialized
 * @param pnEpId The id of the episode (unique)
 */
	public clsEpisode(clsRecognitionProcess poRecognitionProcess, clsEvent poStartEvent, int pnEpId){
		moEpisodeId = new Integer(pnEpId);
		moRecognitionProcess = poRecognitionProcess;
		moScenario = moRecognitionProcess.moRelativeScenario;
		setStartEvent(poStartEvent);
		moEventList = new Vector();
	}
	private void setStartEvent(clsEvent poStartEvent) {
		moStartEvent = poStartEvent;		
	}
	public void setEndEvent(clsEvent poEndEvent) {
		moEndEvent = poEndEvent;
	}
	public void addEvent(clsEvent poEvent) {
		moEventList.add(poEvent);
		poEvent.relateToEpisode(this);
	}
	public int getScenarioId(){
		return moScenario.mnId;
	}
	
	/**
	 * To get the emotional state of the agent at the start of the episode
	 * @return The entire feature emotions that represents the emotional state
	 */
	public clsFeatureEmotions getEmotionalStateStart() {
		return moStartEvent.getFeatureEmotions();
	}

	/**
	 * To get the emotional state of the agent at the end of the episode
	 * @return The entire feature emotions that represents the emotional state
	 */
	public clsFeatureEmotions getEmotionalStateEnd() {
		return moEndEvent.getFeatureEmotions();
	}
	
	/**
	 * To get the impact of the episode. This method may be adapted when adding the feature complex emotions to the EM module
	 * @return An container clsContainerEmotions representing the emotional state of the end event of the episode
	 */
	public Object getImpact() {
		return getEmotionalStateEnd().getContainer();
	}
	
	/**
	 * Boosts the activation of all events that belong to that episode when the episode is recalled
	 */
	public void boost() {
		for(int i=0; i<moEventList.size(); i++) {
			clsEvent oEvent = (clsEvent)moEventList.get(i);
			oEvent.boostRelated();
		}
	}
	
	/**
	 * Determines the best retrieval result of the episode to the indicated retrieval cue (if a retrieval is stimulated
	 * with an scenario indicated in the cue)
	 * @param poCue The retrieval cue
	 * @return The best retrieval result of the episode
	 */
	public clsRetrievalResult retrieve(clsRetrievalCue poCue){
		Vector oCueFeatures = null;
		if(poCue.moEvent==null){
			// the cue is constructed by an arbitrary selection of features
			oCueFeatures = poCue.getArbitraryCue();
		}
		clsContainerRetrievalResults oResultList = new clsContainerRetrievalResults(poCue);
		for(int i=0; i<moEventList.size(); i++){
			clsEvent oEvent = (clsEvent)moEventList.get(i);
			clsMatch oMatch = null;
			if(poCue.moEvent != null) {
				// the cue is constructed by the salient features of an event
				oMatch = oEvent.getMatch( poCue.moEvent ); 
			}
			else {
				// calculate match with an arbitrary selection of features
				oMatch = oEvent.getMatch( oCueFeatures );
			}
			clsRetrievalResult oResult = new clsRetrievalResult(oEvent, oMatch);
			oResultList.add(oResult);			
		}
		return oResultList.getFirstResult();
	}
	
	/**
	 * Implemented method of the interface Compareable: Compares episodes according to the occurence of the end event
	 */
	public int compareTo(Object o1) {
		clsEpisode oEpisode1 = (clsEpisode)o1;
//		if(oResult1.moMemKey.intValue() == this.moMemKey.intValue()) return 0;
		if(this.equals(oEpisode1)) return 0;
		if(moEndEvent.moMemoryKey.intValue() > oEpisode1.moEndEvent.moMemoryKey.intValue()) return -1; // for descending order
		return 1;
	}
	@Override
	public String toString() {
		return "    Ep"+moEpisodeId.intValue()+" ("+ toStringContent() +")\n";
	}
	public String toString3() {
		return "Ep"+moEpisodeId.intValue() + ": "+toStringContext()+"\n";
	}
	public String toStringContent() {
		return "Ev "+moStartEvent.moMemoryKey.intValue()+ "-"+moEndEvent.moMemoryKey.intValue();
	}
	public String toStringContext() {
		return moScenario.moName;
	}
}
