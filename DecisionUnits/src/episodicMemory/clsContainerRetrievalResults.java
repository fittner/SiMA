package episodicMemory;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * The class clsContainerRetrievalResults contains the results to a certain retrieval stimulated by a retrieval cue. The retrieval results are ordered according to its result value and accessible via the methods retrieveFirstResult() and retrieveNextResult(). Provides the Recall API to recall the retrieved events and episodes. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsContainerRetrievalResults {
	/**
	 * The retrieval cue to which the results of the container belong to
	 */
	public clsRetrievalCue moRetrievalCue;
	private TreeSet moRetrievalResults;
	private Iterator moIt;
	private clsRecallAPI moRecallAPI;
	/**
	 * The threshold a retrieval result must exceed to be accessible (Default value: 0.8) 
	 */	
	public static float RETRIEVAL_THRESHOLD = 0.8f;
	
	/**
	 * Constructor to initialize a new retrieval results container to a retrieval cue
	 * @param poRetrievalCue The cue the retrieval was stimulated
	 */
	public clsContainerRetrievalResults(clsRetrievalCue poRetrievalCue) {
		moRetrievalCue = poRetrievalCue;

		moRetrievalResults = new TreeSet();
		moIt = moRetrievalResults.iterator();
	}
	
	/**
	 * Initializes a new recall API via which the events of the respective retrieval results can be recalled.
	 * @param poMemory A reference to the memory container is needed for the recall API to arbitrary recall various
	 * events originating from the retrieved event
	 */
	public void setAPI(clsContainerMemory poMemory) {
		moRecallAPI = new clsRecallAPI(poMemory);		
	}
	
	/**
	 * Adds a retrieval result to the container
	 * @param poRetrievalResult The retrieval result to be added
	 */
	public void add(clsRetrievalResult poRetrievalResult) {
		if(poRetrievalResult != null){
			moRetrievalResults.add(poRetrievalResult);
		}
	}
	
	/**
	 * Checks whether the container is empty
	 * @return true if empty; otherwise false
	 */
	public boolean isEmpty() {
		return moRetrievalResults.isEmpty();
	}

	/**
	 * Retrieves the first (the best) result
	 * @return A reference to the recall API that points to the respective retrieval result.
	 */
	public clsRecallAPI retrieveFirstResult() {
		clsRetrievalResult oResult = getFirstResult();
		if(oResult == null) return null; // no more retrieval result available
		moRecallAPI.setRetrievalResult( oResult );
		return moRecallAPI;
	}
	
	/**
	 * Retrieves the next retrieval result
	 * @return A reference to the recall API that points to the respective retrieval result.
	 */
	public clsRecallAPI retrieveNextResult() {
		clsRetrievalResult oResult = getNextResult();
		if(oResult == null) return null; // no more retrieval result available
		moRecallAPI.setRetrievalResult( oResult );
		return moRecallAPI;
	}
	
	/**
	 * Returns the first retrieval result (mainly for internal use)
	 * @return The respective retrieval result
	 */
	public clsRetrievalResult getFirstResult() {
		moIt = moRetrievalResults.iterator();
		return getNextResult();
	}

	/**
	 * Returns the next retrieval result (mainly for internal use)
	 * @return The respective retrieval result
	 */
	public clsRetrievalResult getNextResult() {
		if( moIt.hasNext() ) {
			clsRetrievalResult oResult = (clsRetrievalResult)moIt.next();
			if(oResult.getResult() >= RETRIEVAL_THRESHOLD) {  // set a lower limit the result must exceed to be accesible
				return oResult;
			}
		}
		return null;
	}
	
	/**
	 * To visualize the retrieval result container
	 * @return A String for logging
	 */
	@Override
	public String toString() {
		String oRetString = "";
		int nNumber = 0;
		Iterator oIt = moRetrievalResults.iterator(); 
		while( oIt.hasNext() ){
			clsRetrievalResult oResult = (clsRetrievalResult)oIt.next();
			oRetString+= oResult.toString();
			if( ++nNumber == 3) break; // prints the top 3 results
		}
		return oRetString;
	}
	
}
