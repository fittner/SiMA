package bw.memory;

import bw.memory.tempframework.cls0to1;
import java.util.Vector;

/**
 * The class clsFeature suppports the functionalities which ar the same for each feature. It consists of various feature elements, which have to be of the same type as the respective feature. E.g. the feature elements for the feature emotions (clsFeatureEmotions) have to be of the type clsElementEmotion. The feature is rated by a salience value, which determines whether this feature is relevant in a situation. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
abstract public class clsFeature {
	protected Vector moFeatureElements;  // contains the FeatureContent Objects
	public cls0to1 moSalience;
	/**
	 * Defines, whether trigger encoding of an event on the basis of this feature should be enabled (default: true).
	 */
	public boolean mnTriggerEncodingEnabled = true;
	/**
	 * Defines, whether this feature should be checked for spontaneous retrieval (default: true).
	 */
	public boolean mnSpontaneousRetrievalEnabled = true;
	/**
	 * If the salience is above this threshold, this feature is defined to be salient.
	 */
	public static float SALIENCE_THRESHOLD = 0.2f;

	public clsFeature() {
		moFeatureElements = new Vector();
		moSalience = new cls0to1(0);
	}
	public Vector getFeatureElements() {
		return moFeatureElements;
	}
	/**
	 * Checks, whether this feature is salient or not. Currently, it is defined that a feature is salient, if its salience value
	 * is greater than clsFeature.SALIENCE_THRESHOLD . This method may be defined abstract and implemented separately for 
	 * each feature (each feature may have a different salience threshold)
	 * @return true, if the feature is salient, otherwise false
	 */
	public boolean checkIfSalient() { // may be abstract -> implemented separately for each feature
		if(moSalience.get() >= SALIENCE_THRESHOLD) return true;
		return false;
	}
	
	/**
	 * Triggers the encoding of a situation as an event and determines the salience of this feature. If
	 * mnTriggerEncodingEnabled is set for this feature, it will be checked. NOTE: Has to be overloaded for features,
	 * which feature elements do not have a fixed order in each situation (e.g. feature actions)
	 * @param poPrevFeature The feature of the same type of the previous situation. Encoding is triggered, if a significant
	 * change of any feature is detected.
	 * @return true, if encoding of an event is triggered; otherwise false
	 */
	public boolean triggerEncoding(clsFeature poPrevFeature) {
		boolean nTrigger = false;
		for(int i=0; i<moFeatureElements.size(); i++){
			  clsFeatureElement oPrevFeatElem = (clsFeatureElement)poPrevFeature.getFeatureElements().get(i);
			  clsFeatureElement oFeatElem = (clsFeatureElement)moFeatureElements.get(i);

			  if( oFeatElem.triggerEncoding(oPrevFeatElem) ) {  
				  nTrigger = true;
			  }
			  // The salience of the feature is the maximum salience value of the constituent elements
			  float rSalience = oFeatElem.moSalience.get();
			  if(moSalience.get() < rSalience) moSalience.set(rSalience);			  
		}
		return nTrigger;
	}
	
	/**
	 * Determines the match of this feature to the cue feature of the same type in the parameter. This method is optimized
	 * for speed by making use of the fixed composition of the feature elements, thus the feature elements of this feature
	 * must have the same structure (the same order); otherwise it has to be overloaded (e.g. for the feature actions)
	 * @param poCueFeature The cue feature which is of the same type like this feature
	 * @return A clsMatchFeature object with the matching result
	 */
	public clsMatchFeature getMatch( clsFeature poCueFeature) {
		clsMatchFeature oFeatureMatch = new clsMatchFeature(poCueFeature);
		for(int i=0; i<moFeatureElements.size(); i++) {
			clsFeatureElement oFeatElem = (clsFeatureElement)moFeatureElements.get(i);
			clsFeatureElement oFeatElemCue = (clsFeatureElement)poCueFeature.getFeatureElements().get(i);
			oFeatureMatch.addElementMatch( oFeatElem.getMatch( oFeatElemCue ) );
		}
		return oFeatureMatch;
	}
	
	/**
	 * Determines the match of this feature to the cue feature of the same type in the parameter. The composition of the
	 * feature elements may be arbitrary -> used for deliberate retrieval with an arbitrary selection of features as cue.
	 * @param poCueFeature The cue feature which is of the same type like this feature
	 * @return A clsMatchFeature object with the matching result
	 */
	public clsMatchFeature getDeliberateMatch( clsFeature poCueFeature) {
		clsMatchFeature oFeatureMatch = new clsMatchFeature(poCueFeature);
		for(int i=0; i<poCueFeature.getFeatureElements().size(); i++) {
			clsFeatureElement oFeatElemCue = (clsFeatureElement)poCueFeature.getFeatureElements().get(i);
			clsFeatureElement oFeatElem = null;
			for(int j=0; j<moFeatureElements.size(); j++) {
				oFeatElem = (clsFeatureElement)moFeatureElements.get(j);				
				if(oFeatElem.checkIfSameType(oFeatElemCue)) {
					// oFeatElem and the oCueFeatureElement are of the same type (e.g. emotion)
					clsMatchFeatureElement oElementMatch = oFeatElem.getMatch( oFeatElemCue ); // redirects to the derived featureElementMatch object
					oFeatureMatch.addElementMatch(oElementMatch);
					break;
				}
			}
		}
		return oFeatureMatch;
	}
	
	/**
	 * Checks wheter this feature is of the same type as the feature in the parameter. Determined in the respective feature.
	 * @return true, if this feature is of the same type as the feature in the parameter
	 */
	abstract public boolean checkIfSameType(clsFeature poFeature); 

	/**
	 * Get a container with the constituent feature elements
	 * @return the initially delivered feature elements in a container (e.g. the container clsContainerEmotions for
	 * the feature emotions)
	 */
	abstract public Object getContainer();
	
	public String toString() {
		return "";
	}
}
