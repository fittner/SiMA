package episodicMemory;

import episodicMemory.tempframework.clsContainerBaseVector;

import java.util.Vector;

/**
 * The class clsContainerMemory represents the memory container that deposits all stored events chronologically ordered. The events are organized in a java.util.Vector data structure which is inherited from the class clsContainerBaseVector. It further incorporates an episode container which contains all stored episode which are stored according to its context. $Revision: 627 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-06-25 12:20:22 +0200 (Mo, 25 Jun 2007) $: Date of last commit
 */
public class clsContainerMemory extends clsContainerBaseVector {
	private int mnCurrentMemKey;
	/**
	 * The container with the stored episodes
	 */
	public clsEpisodeContainer moEpisodes;
	
	/**
	 * Indicates the minimal number of events, that have to be in the memory, that a retrieval will be performed
	 * (Default value: 10)
	 */
	public static int MIN_EVENTS_FOR_RETRIEVAL = 10;
	/**
	 * Indicates the number of the most recently encoded events, that are not checked for retrieval.
	 * (Default value: 5)
	 */
	public static int NOT_CHECKED_FOR_RETRIEVAL = 5;
	
	public clsContainerMemory() {
		super();
		mnCurrentMemKey = -1;
		moEpisodes = new clsEpisodeContainer();
	}
	
/**
 * Encodes an event into the memory container
 * @param poEvent The event to be stored 
 */	
	public void encode(clsEvent poEvent){
		mnCurrentMemKey++;
		poEvent.setEvent(mnCurrentMemKey);
		add( mnCurrentMemKey, poEvent );
	}
	
/**
 * Encodes an episode into the episode container. The events of the memory container that are part of the
 * to be stored episode are further related to the episode.
 * @param poEpisode The episode to be stored 
 */	
	public void encodeEpisode(clsEpisode poEpisode) {
		int nStart = poEpisode.moStartEvent.moMemoryKey.intValue();
		int nEnd = poEpisode.moEndEvent.moMemoryKey.intValue();
		// relates each events that belong to the episode to the episode
		for(int i=nStart; i<=nEnd; i++ ) {
			clsEvent oEvent = (clsEvent)getObject( i );
			poEpisode.addEvent(oEvent);
		}
		moEpisodes.addEpisode(poEpisode);
	}
	
/**
 * Realizes the decay of all stored events
 *
 */	
	public void decay() {
		for (int i=0; i<this.size()-1; i++) {
			// traverse all stored events
				// do not decay the most recent encoded element (size()-1)
				// -> the longer the most recent encoded event is active, the "higher"
				// its activation in reference to the others!!!
				clsEvent oEvent = (clsEvent)getObject(i);
				oEvent.updateActivation();
		}
		
	}
	
/**
 * Performs a retrieval to a corresponding retrieval cue
 * @param poCue The retrieval cue that containing the retrieval information
 * @return A retrieval results container (clsContainerRetrievalResults)
 */	
	public clsContainerRetrievalResults retrieve(clsRetrievalCue poCue) {
//		 make retrieval possible when at #MIN_EVENTS_FOR_RETRIEVAL events are in the memory
		if (this.size() < MIN_EVENTS_FOR_RETRIEVAL) return null; 
		
		if(poCue.moScenarios != null) {  // checks whether a scenario is indicated in the cue
			clsContainerRetrievalResults oResultList = moEpisodes.retrieve(poCue);
			oResultList.setAPI(this); // initializes the recall API via which the events of the respective retrieval results can be recalled.
			return oResultList;
		}
		Vector oCueFeatures = null;
		if(poCue.moEvent==null){
			// the cue is constructed by an arbitrary selection of features	
			oCueFeatures = poCue.getArbitraryCue();
		}
		clsContainerRetrievalResults oResultList = new clsContainerRetrievalResults(poCue); // maps the match of an Event to its MemoryElement entry-key
		for (int i=0; i<this.size()-NOT_CHECKED_FOR_RETRIEVAL; i++) {
			// traverse all stored events execpt the #NOT_CHECKED_FOR_RETRIEVAL most recently encoded events
			clsEvent oEvent = (clsEvent)getObject(i);
			clsMatch oMatch = null;
			// use the event as cue if it is indicated
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
		oResultList.setAPI(this); // initializes the recall API via which the events of the respective retrieval results can be recalled.
		return oResultList;
		
	}
	
	/**
	 * Returns the object at the specified position
	 */
	@Override
	public Object getObject(int pnPos) {
		if (moContainer.isEmpty()) return null;
		Object oResult = null;
		if(pnPos >= 0 && pnPos < moContainer.size()) {
		  	oResult = moContainer.get(pnPos);
		}
		return oResult;
	}
	
	public Object getLatestObject(){
		if (moContainer.size()>0){
			return moContainer.get(moContainer.size()-1);
		}else{
			return null;
		}
	}

	@Override
	public String gettoString(Object poObject) {
	    return "";	
	}
	
}
