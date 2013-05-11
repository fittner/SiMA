package memory;

import memory.tempframework.cls0to1;

/**
 * The class clsMatchFeatureElement represents a Match of a feature element $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsMatchFeatureElement {
	/**
	 * The cue element to which this match is determined
	 */
	public clsFeatureElement moFeatureElement;
	public cls0to1 moMatch;
	
	public clsMatchFeatureElement(clsFeatureElement poFeatureElement, float prMatch) {
		moFeatureElement = poFeatureElement;
		moMatch = new cls0to1(prMatch);
	}
	public float getMatch() {
		return moMatch.get();
	}
}
