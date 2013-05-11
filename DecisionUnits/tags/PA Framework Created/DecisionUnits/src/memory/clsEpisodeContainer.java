//package memory;
//
//// Imports
//import memory.tempframework.clsContainerBaseTreeMap;
//import java.util.Iterator;
//import java.util.TreeSet;
//import java.util.Vector;
//
///**
// * The class clsEpisodeContainer represents the episode container which deposits the stored episodes according to the
// * context of the episodes (the scenario template the episode is a realization of). The episodes are stored in a 
// * java.util.TreeMap, which is inherited from the class clsContainerBaseTreeMap. Each entry of the TreeMap maps a sorted list
// * (java.util.TreeSet) of episodes to a key which corresponds to the respective scenario id.
// *
// * $Revision: 627 $:  Revision of last commit
// * $Author: deutsch $: Author of last commit
// * $Date: 2007-06-25 12:20:22 +0200 (Mo, 25 Jun 2007) $: Date of last commit
// * 
// */
//public class clsEpisodeContainer  extends clsContainerBaseTreeMap{
//	
//	public clsEpisodeContainer() {
//		super();
//	}
//	
//	/**
//	 * Adds an episode to the episode container according to the scenario it is a realization of
//	 * @param poEpisode The episode to be stored to the container
//	 */
//	public void addEpisode(clsEpisode poEpisode) {
//		Integer oScenarioId = new Integer( poEpisode.getScenarioId() );
//		if( getKeySet().contains(oScenarioId) ) {
//			// there still exist episodes for this scenario
//			TreeSet oEpisodes = (TreeSet)this.getObject(oScenarioId);
//			oEpisodes.add(poEpisode);
//		}
//		else {
//			// no Episode for this scenario exist -> generate new Set
//			TreeSet oEpisodes = new TreeSet();
//			oEpisodes.add(poEpisode);
//			
//			this.add(oEpisodes, oScenarioId);
//		}
//	}
//	
//	/**
//	 * Performs a retrieval, if a scenario template is indicated in the cue. 
//	 * @param poCue The retrieval cue with an indicated scenario
//	 * @return The retrieval results container to this retrieval. For each episode that is a realization
//	 * of this scenario template one retrieval result (the best) is determined and stored in the retrieval results container
//	 */
//	public clsContainerRetrievalResults retrieve(clsRetrievalCue poCue){
//		clsContainerRetrievalResults oResultList = new clsContainerRetrievalResults(poCue); // maps the match of an Event to its MemoryElement entry-key
//		for(int i=0; i<poCue.moScenarios.size(); i++) {
//			Integer oScenarioId = (Integer)poCue.moScenarios.get(i);
//			TreeSet oEpisodes = (TreeSet)getContainer().get(oScenarioId);
//			if(oEpisodes == null) break; // no episodes of that scenarioId are in the episode container
//			Iterator oItEp = oEpisodes.iterator();
//			
//			while( oItEp.hasNext() ) {
//				clsEpisode oEpisode = (clsEpisode)oItEp.next();
//				clsRetrievalResult oResult = oEpisode.retrieve(poCue);
//				// adds the best retrieval result of each episode to the result list
//				oResultList.add(oResult);
//			}
//		}
//		return oResultList;
//	}
//	
////	public clsEpisodeContainer retrieve(Integer poMemKey){
////		// return an episode container with the episodes containing the event of the indicated memory key
////		clsEpisodeContainer oRetrievedEpisodes = null;
////		Iterator oIt = this.keySet().iterator(); 
////		while( oIt.hasNext() ){
////			// traverse all Episodes of all Scenarios
////			Integer oScenarioId = (Integer)oIt.next();
////			TreeSet oEpisodes = (TreeSet)getContainer().get(oScenarioId);
////			Iterator oItEp = oEpisodes.iterator();
////			
////			while( oItEp.hasNext() ) {
////				clsEpisode oEpisode = (clsEpisode)oItEp.next();
////				if(oEpisode.moEndEvent.moMemoryKey.intValue() < poMemKey.intValue()) {
////					// the remaining Episodes ended before the Event to be searched for occurs
////					break;
////				}
////				else {
////					// check if poMemKey is within Episode
////					if(oEpisode.moStartEvent.moMemoryKey.intValue() <= poMemKey.intValue() ) {
////						// poMemKey is within Start and End of this episode -> episode found!
////						if(oRetrievedEpisodes == null) oRetrievedEpisodes = new clsEpisodeContainer();
////						oRetrievedEpisodes.addEpisode(oEpisode);
////					}
////				}
////			}
////		}
////		return oRetrievedEpisodes;
////	}
//	
//	/**
//	 * Determines the total number of episodes in the episode container
//	 */
//	public int getEpisodeSize(){
//		int nSize = 0;
//		Iterator oIt = this.keySet().iterator(); 
//		while( oIt.hasNext() ){
//			// traverse all Episodes of all Scenarios
//			Integer oScenarioId = (Integer)oIt.next();
//			TreeSet oEpisodes = (TreeSet)getContainer().get(oScenarioId);
//			nSize+=oEpisodes.size();
//		}
//		return nSize;
//	}
//	
//	/**
//	 * Returns the list of episodes (the sub-container) that are realizations of the indicated scenario Id
//	 * @param poScenarioId The scenario Id
//	 * @return A TreeSet with the episodes of that scenario
//	 */
//	public TreeSet getEpisodesOfScenario(Integer poScenarioId){
//		if( getKeySet().contains(poScenarioId) ) {
//			// there exist episodes for this scenario
//			TreeSet oEpisodes = (TreeSet)this.getObject(poScenarioId);
//			return oEpisodes;
//		}
//		return null; // no Episodes exist to this scenario id
//	}
//	/**
//	 * Returns the episode with the Id indicated in the parameter
//	 * @param pnEpId The id of the episode
//	 * @return A clsEpisode object if the episode exists, otherwise null
//	 */
//	public clsEpisode getEpisode(int pnEpId) {
//		Iterator oIt = this.keySet().iterator(); 
//		while( oIt.hasNext() ){
//			// traverse all Episodes of all Scenarios
//			Integer oScenarioId = (Integer)oIt.next();
//			TreeSet oEpisodes = (TreeSet)getContainer().get(oScenarioId);
//			Iterator oItEp = oEpisodes.iterator();
//			
//			while( oItEp.hasNext() ) {
//				clsEpisode oEpisode = (clsEpisode)oItEp.next();
//				if(oEpisode.moEpisodeId.intValue() == pnEpId) {
//					return oEpisode;  // the respective episode is found
//				}
//			}
//		}
//		return null; // the episode with the indicated id is not found
//	}
//	/**
//	 * Transforms the stored episodes into a vector indicated in the parameter
//	 * @param poVec The Vector, where the episodes of this container should be stored to
//	 */
//	public void getEpisodesInVector(Vector poVec) {
//		Iterator oIt = this.keySet().iterator(); 
//		while( oIt.hasNext() ){
//			// traverse all Episodes of all Scenarios
//			Integer oScenarioId = (Integer)oIt.next();
//			TreeSet oEpisodes = (TreeSet)getContainer().get(oScenarioId);
//			Iterator oItEp = oEpisodes.iterator();
//			
//			while( oItEp.hasNext() ) {
//				clsEpisode oEpisode = (clsEpisode)oItEp.next();
//				poVec.add(oEpisode);
//			}
//		}
//		//return oVec;
//	}
//	public String gettoString(Object poObject) {
//	    return ((clsEpisode)poObject).toString();	
//	}
//	
//	/**
//	 * Prints the content of the episode container in a String
//	 */
//	public String toString() {
//		String oRetString = "";
//		Iterator oIt = keySet().iterator(); 
//		while( oIt.hasNext() ){
//			Integer oScenarioId = (Integer)oIt.next();
//			TreeSet oEpisodes = (TreeSet)getContainer().get(oScenarioId);
//			Iterator oItEp = oEpisodes.iterator();
//			String oScenName = null;
//			while( oItEp.hasNext() ) {
//				clsEpisode oEpisode = (clsEpisode)oItEp.next();
//				if(oScenName==null){
//					oScenName = oEpisode.moScenario.moName;
//					oRetString+= " ScID "+ oScenarioId.intValue()+ " ("+oScenName + ")\n";
//				}
//				oRetString+= oEpisode.toString();
//			}
//		}
//		return oRetString;
//	}
//}
