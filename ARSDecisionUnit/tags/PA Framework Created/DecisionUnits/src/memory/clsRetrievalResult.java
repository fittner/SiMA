//package memory;
//
//import memory.tempframework.cls0to1;
//import java.util.Iterator;
//import java.util.TreeSet;
//
///**
// * The class clsRetrievalResult represents the retrieval result of a stored event to a certain retrieval cue. The retrieval result of an event is determined by matching of features to the cue and its activation level. This class implements the interface Comparable: retrieval results are compared according their result value. (Result = ALPHA*Match + BETA*Activation). $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
// */
//public class clsRetrievalResult implements Comparable{
//	public clsEvent moEvent;
//	public clsMatch moMatch;
//	public cls0to1 moResult;
//	/**
//	 * Indicates whether the result (moResult) is valid (true) or not (false)
//	 */
//	public boolean mnValid;
//	
//	/**
//	 * Coefficient for the weight of matching of features to the retrieval result
//	 * (Default: ALPHA=0.7)
//	 */
//	public static float ALPHA = 0.7f;
//	/**
//	 * Coefficient for the weight of the activation level to the retrieval result
//	 * (Default: BETA=0.3)
//	 */
//	public static float BETA = 0.3f;
//	
//	/**
//	 * Initiates a new retrieval result.
//	 * @param poEvent The stored event
//	 * @param poMatch The match determined by the matching of features procedure
//	 */
//	public clsRetrievalResult(clsEvent poEvent, clsMatch poMatch) {
//		moEvent = poEvent;
//		moMatch = poMatch;
//		moResult = new cls0to1();
//		mnValid = false;
//		
//	}
//	
//	/**
//	 * Calculates the retrieval results on the basis of matching of features and the activation level
//	 * @return A float value for the retrieval result (between 0 and 1)
//	 */
//	public float getResult() {
//		if(!mnValid) {
//			// derive a function that depends on the salience and emotional tone too.
//			float rResult = ALPHA*moMatch.calculateMatch().get() + BETA*moEvent.getActivation().get(); 
//							//+ 0.05f*moEvent.moEmotionalTone.get() + 0.05f*moEvent.moSalience.get();
//			moResult.set(rResult);
//			mnValid = true;
//		}
//		return moResult.get();
//	}
//	
//	/**
//	 * Retrieval results are compared according their result value.
//	 */
//	public int compareTo(Object o1) {
//		clsRetrievalResult oResult1 = (clsRetrievalResult)o1;
//		if(oResult1.moEvent.moMemoryKey.intValue() == this.moEvent.moMemoryKey.intValue()) return 0;
//		if(getResult() > oResult1.getResult()) return -1; // for descending order
//		return 1;
//	}
//	
//	/**
//	 * Converts the retrieval result in a String (for logging)
//	 */
//	public String toString() {
//		String oRet = "Event "+moEvent.moMemoryKey.intValue()+ ":  RetrievRes="+getResult() + 
//		"   (Act="+ moEvent.getActivation().get() + "  ,Match="+moMatch.getMatch()+")";
//		Iterator oIt = moEvent.moRelatedEpisodes.keySet().iterator(); 
//		while( oIt.hasNext() ){
//			// traverse all Episodes of all Scenarios
//			Integer oScenarioId = (Integer)oIt.next();
//			TreeSet oEpisodes = (TreeSet)moEvent.moRelatedEpisodes.getContainer().get(oScenarioId);
//			Iterator oItEp = oEpisodes.iterator();
//			
//			while( oItEp.hasNext() ) {
//				clsEpisode oEpisode = (clsEpisode)oItEp.next();
//				oRet+=", Ep"+oEpisode.moEpisodeId.intValue()+ " (Sc"+oScenarioId.intValue()+")";
//			}
//		}
//				
//		return oRet+"\n";
//	}
//}
