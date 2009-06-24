package memory;

import memory.tempframework.clsContainerEmotion;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

/**
 * The class clsRecallAPI represents the interface to recall the features of a retrieved event and to reconstruct epiodes $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsRecallAPI {
	private clsRetrievalResult moResult = null;
	
	private clsContainerMemory moMemory; // 
	private int mnCurrMemKey=0;
	private clsEpisode moCurrEpisode = null;
	private Iterator moIt = null;
	
	/**
	 * Standard constructor
	 * @param poMemory A reference to the memory container is needed for the recall API to arbitrary recall various
	 * events originating from the retrieved event
	 */
	public clsRecallAPI(clsContainerMemory poMemory) {
		moMemory = poMemory;
	}
	
	/**
	 * Sets the Recall API to the indicated retrieval result
	 * @param poResult The retrieval result to which the recall API should be set
	 */
	public void setRetrievalResult(clsRetrievalResult poResult) {
		moResult = poResult;
		mnCurrMemKey = moResult.moEvent.moMemoryKey.intValue();
	}
	
	/**
	 * To recall the situational data of the retrieved event (the retrieval result, where the recall API points to)
	 * @return A java.util.Vector with the situational informations of the retrieved event. The elements of the Vector
	 * have the following ordering:
	 * [clsContainerDrives | clsContainerEmotions | clsContainerCompareResults | clsActionContainer]
	 */
	public Vector getSituation() {
		mnCurrMemKey = moResult.moEvent.moMemoryKey.intValue();
		moResult.moEvent.boost(true); // event is recalled -> true: boost events that belong to the same episode as well
		return moResult.moEvent.getSituationalData();
	}
	
	/**
	 * To recall the situational data of the succeeding event
	 * @return A java.util.Vector with the situational informations of the event. The elements of the Vector
	 * have the following ordering:
	 * [clsContainerDrives | clsContainerEmotions | clsContainerCompareResults | clsActionContainer]
	 */
	public Vector getNextSituation() {
		mnCurrMemKey++;
		clsEvent oEvent = (clsEvent)moMemory.getObject(mnCurrMemKey);
		oEvent.boost(false); // event is recalled -> boost but do not boost related episodes
		return oEvent.getSituationalData();
		}

	/**
	 * To recall the situational data of the previous event
	 * @return A java.util.Vector with the situational informations of the event. The elements of the Vector
	 * have the following ordering:
	 * [clsContainerDrives | clsContainerEmotions | clsContainerCompareResults | clsActionContainer]
	 */
	public Vector getPrevSituation() {
		mnCurrMemKey--;
		clsEvent oEvent = (clsEvent)moMemory.getObject(mnCurrMemKey);
		oEvent.boost(false); // event is recalled -> boost but do not boost related episodes
		return oEvent.getSituationalData();
	}
	
	/**
	 * Returns the scenario id's of the episodes, the retrieved event is related to. 
	 * @return A java.util.Vector with the respective scenario id's.
	 */
	public Vector getScenarios() {
		Vector oScenarios = new Vector();
		Iterator oIt = moResult.moEvent.moRelatedEpisodes.keySet().iterator(); 
		while( oIt.hasNext() ){
			// traverse all Episodes of all Scenarios
			Integer oScenarioId = (Integer)oIt.next();
			oScenarios.add(oScenarioId);
		}
		return oScenarios;
	}
	
	/**
	 * Sets the internally handled episode to the next episode that is a realization of the indicated scenario. (An episode
	 * has to be selected to use the subsequent episode operations)
	 * @param poScenarioId The scenario id of the episode that should be recalled
	 * @return true, if the retrieved event is related to an(other) episode of this scenario and the internally handled
	 * episode is succussfully set; otherwise false 
	 */
	public boolean nextEpisode(Integer poScenarioId){
		// sets the moCurrEpisode to the next episode of the scenario indicated in the parameter
		clsEpisodeContainer oEpisodeList = moResult.moEvent.moRelatedEpisodes;
		TreeSet oEpisodes = (TreeSet)oEpisodeList.getContainer().get(poScenarioId);
		if(oEpisodes == null)  return false; // does not contain any episode of that scenario
		
		if(moCurrEpisode == null){
			moIt = oEpisodes.iterator();
		}
		else if(moCurrEpisode.moScenario.mnId != poScenarioId.intValue()){
			// the current episode is of an other scenario Id
			moIt = oEpisodes.iterator();			
		}
	
		if( moIt.hasNext() ) {
			moCurrEpisode = (clsEpisode)moIt.next();
			// resets the memory key to the original episode
			mnCurrMemKey = moResult.moEvent.moMemoryKey.intValue();
			return true;
		}
		moCurrEpisode=null;
		return false;
	}
	
	/**
	 * To recall the emotional impact of the episode.
	 * @return The emotional state in the end of the episode. The return value is null, if no episode was selected before
	 * by the method nextEpisode()
	 */
	public clsContainerEmotion getImpactOfEpisode() {
		if(moCurrEpisode==null) return null;
		return (clsContainerEmotion)moCurrEpisode.getImpact();		
	}	
	
	/**
	 * To recall the situational data of the succeeding event of the internal handled episode. An episode has to be selected before
	 * by calling the method nextEpisode()
	 * @return A java.util.Vector with the situational informations of the event. The elements of the Vector
	 * have the following ordering:
	 * [clsContainerDrives | clsContainerEmotions | clsContainerCompareResults | clsActionContainer].
	 * The return value is null, if the last event of the episode is reached
	 */
	public Vector getNextSituationOfEpisode() {
		if(moCurrEpisode==null) return null;
		mnCurrMemKey++;
		if( mnCurrMemKey > moCurrEpisode.moEndEvent.moMemoryKey.intValue() ) {
			// last event of episode reached
			return null;
		}
		clsEvent oEvent = (clsEvent)moMemory.getObject(mnCurrMemKey);
		oEvent.boost(false); // event is recalled -> boost but do not boost related episodes
		return oEvent.getSituationalData();
	}

	/**
	 * To recall the situational data of the previous event of the internal handled episode. An episode has to be selected before
	 * by calling the method nextEpisode()
	 * @return A java.util.Vector with the situational informations of the event. The elements of the Vector
	 * have the following ordering:
	 * [clsContainerDrives | clsContainerEmotions | clsContainerCompareResults | clsActionContainer].
	 * The return value is null, if the first event of the episode is reached
	 */
	public Vector getPrevSituationOfEpisode() {
		if(moCurrEpisode==null) return null;
		mnCurrMemKey--;
		if( mnCurrMemKey < moCurrEpisode.moStartEvent.moMemoryKey.intValue() ) {
			// first event of episode reached
			return null;
		}
		clsEvent oEvent = (clsEvent)moMemory.getObject(mnCurrMemKey);
		oEvent.boost(false); // event is recalled -> boost but do not boost related episodes
		return oEvent.getSituationalData();
	}

	/**
	 * To recall the situational data of the last event of the internally handled episode.
	 * @return A java.util.Vector with the situational informations of the event. The elements of the Vector
	 * have the following ordering:
	 * [clsContainerDrives | clsContainerEmotions | clsContainerCompareResults | clsActionContainer].
	 * The return value is null, no episode has been selected before by calling the method nextEpisode()
	 */
	public Vector getLastSituationOfEpisode() {
		if(moCurrEpisode==null) return null;
		clsEvent oEvent = moCurrEpisode.moEndEvent;
		mnCurrMemKey = oEvent.moMemoryKey.intValue();
		oEvent.boost(false); // event is recalled -> boost but do not boost related episodes
		return oEvent.getSituationalData();
	}

	/**
	 * To recall the situational data of the first event of the internally handled episode.
	 * @return A java.util.Vector with the situational informations of the event. The elements of the Vector
	 * have the following ordering:
	 * [clsContainerDrives | clsContainerEmotions | clsContainerCompareResults | clsActionContainer].
	 * The return value is null, no episode has been selected before by calling the method nextEpisode()
	 */
	public Vector getFirstSituationOfEpisode() {
		if(moCurrEpisode==null) return null;
		clsEvent oEvent = moCurrEpisode.moStartEvent;
		mnCurrMemKey = oEvent.moMemoryKey.intValue();
		oEvent.boost(false); // event is recalled -> boost but do not boost related episodes
		return oEvent.getSituationalData();		
	}

}
