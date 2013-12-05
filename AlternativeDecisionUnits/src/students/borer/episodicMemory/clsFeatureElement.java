package students.borer.episodicMemory;

import students.borer.episodicMemory.tempframework.cls0to1;

/**
 * The class clsFeatureElement defines the basic functionalities, which have to be implemented in each type of feature element. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
abstract public class clsFeatureElement {
	public cls0to1 moSalience;
	/**
	 * If the salience is above this threshold, this feature element is defined to be salient.
	 */
	public static float SALIENCE_THRESHOLD = 0;
	/**
	 * The decay rate of the salience of this feature element (Salience = SALIENCE_DECAY * SalienceOfPrevSituaion)
	 */
	public static float SALIENCE_DECAY = 0.8f;
	
	public clsFeatureElement() {
		moSalience = new cls0to1(0);
	}

	/**
	 * Checks, whether this feature element is salient or not. It is defined that a feature element is salient, if its salience value
	 * is greater than clsFeatureElement.SALIENCE_THRESHOLD . This method may be defined abstract and implemented separately for 
	 * each feature element (each feature element may have a different salience threshold)
	 * @return true, if the feature is salient, otherwise false
	 */
	public boolean checkIfSalient() {
		if(moSalience.get() > SALIENCE_THRESHOLD) return true;
		return false;
	}
	protected void decaySalience(clsFeatureElement poPrevFeatElem) {
		// implements decay of salience 
		// (-> to indicate that this feature element was recently salient
		//  and to make the feature element available for spontaneous retrieval if it was recently salient 
		// => "activation decay" of recently salient features
		if(poPrevFeatElem.checkIfSalient()) { // if this feature element was salient in the previous situation
			float rSal = SALIENCE_DECAY * poPrevFeatElem.moSalience.get(); // decay of the salience
			if(rSal > 0.2f && moSalience.get() < rSal) { // if the salience does not exceed a specific threshold -> remains 0
				moSalience.set(rSal);
			}
		}
	}
	/**
	 * Determines whether the encoding of an event should be triggered on the basis of this feature element. Has to be
	 * implemented seperately for each feature element
	 * @param poFeatureElement The feature element of the same type of the previous situation
	 * @return true, if encoding is triggered; otherwise false
	 */
	abstract public boolean triggerEncoding(clsFeatureElement poFeatureElement);
	/**
	 * Determines the match of this feature element to the one indicated in the parameter
	 * @param poFeatureElement The feature element of the same type like this, to which the match has to be determined
	 * @return An object of type clsMatchFeatureElement that represents the match
	 */
	abstract public clsMatchFeatureElement getMatch(clsFeatureElement poFeatureElement);
	/**
	 * Checks wheter this feature element is of the same type as the feature element in the parameter. Determined in
	 * the respective feature element.
	 * @return true, if this feature element is of the same type as the feature in the parameter
	 */
	abstract public boolean checkIfSameType(clsFeatureElement poFeatElem);
}
