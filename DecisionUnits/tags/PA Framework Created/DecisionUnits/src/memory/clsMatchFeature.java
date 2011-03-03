package memory;

import memory.tempframework.cls0to1;
import java.util.Vector;

/**
 * The class clsMatchFeature represents a Match of a feature to a cue feature  $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsMatchFeature {
	/**
	 * The cue feature, to which this feature match is derived
	 */
	public clsFeature moFeature;
	/**
	 * The individual matches of the feature elements indicated in the cue feature
	 */
	public Vector moElementMatches;
	public cls0to1 moMatch;
	/**
	 * true: the value of clsMatchFeature.moMatch is valid, otherwise the method calculateMatch() has to be called to derive a match
	 */
	public boolean mnValid;

	public clsMatchFeature(clsFeature poFeature) {
		moFeature = poFeature;
		moElementMatches = new Vector();
		moMatch = new cls0to1();
		mnValid = false;
	}
	/**
	 * Adds a match of a feature element to this feature match
	 */
	public void addElementMatch(clsMatchFeatureElement poElementMatch) {
		if(poElementMatch != null){
			moElementMatches.add(poElementMatch);
		}
	}
	/**
	 * Calculates the match of this feature on the basis of the individual feature element matches
	 * @return The match of this feature
	 */
	public cls0to1 calculateMatch() {
		float rMatch = 0;
		int nSize = moElementMatches.size();
		if(nSize == 0) {
			moMatch.set(0);
			return moMatch;
		}
		for(int i=0; i<nSize; i++) {
			clsMatchFeatureElement oElementMatch = (clsMatchFeatureElement)moElementMatches.get(i);
			rMatch+= oElementMatch.getMatch();
		}
		moMatch.set(rMatch / (float)nSize);
		mnValid = true;
//		toString();
		return moMatch;
	}

	public float getMatch() {
		return moMatch.get();
	}

	@Override
	public String toString() {
		String oFeatName = moFeature.toString();
		return "  Feature "+oFeatName+" Match: " + moMatch.get();
	}
}
