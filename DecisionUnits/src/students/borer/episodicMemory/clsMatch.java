package students.borer.episodicMemory;


import java.util.Vector;

import students.borer.episodicMemory.tempframework.cls0to1;

/**
 * The class clsMatch represents the match of features of a stored event to a retrieval cue. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsMatch {
	/**
	 * The feature matches of the respective features that are indicated in the retrieval cue
	 */
	public Vector<clsMatchFeature> moFeatureMatches;
	public cls0to1 moMatch;
	/**
	 * true: the value of clsMatch.moMatch is valid, otherwise the method calculateMatch() has to be called to derive a match
	 */
	public boolean mnValid;

	public clsMatch() {
		moFeatureMatches = new Vector<clsMatchFeature>();
		moMatch = new cls0to1();
		mnValid = false;
	}
	/**
	 * Adds the match of a feature indicated in the retrieval cue
	 * @param poFeatureMatch The feature match object to be added
	 */
	public void addFeatureMatch (clsMatchFeature poFeatureMatch){
		if(poFeatureMatch != null) {
			moFeatureMatches.add(poFeatureMatch);
		}
	}
	/**
	 * Calculates the match of this event on the basis of the individual feature matches of the features indicated in the
	 * retrieval cue
	 * @return The match
	 */
	public cls0to1 calculateMatch() {
		float rMatch = 0;
		int nSize = moFeatureMatches.size();
		if(nSize == 0) {
			moMatch.set(0);
			return moMatch;
		}
		for(int i=0; i<nSize; i++) {
			clsMatchFeature oFeatureMatch = (clsMatchFeature)moFeatureMatches.get(i);
			oFeatureMatch.calculateMatch();
			rMatch+= oFeatureMatch.getMatch();
		}
		moMatch.set(rMatch / (float)nSize);
		mnValid = true;
		return moMatch;
	}
	public float getMatch() {
		return moMatch.get();
	}
}
