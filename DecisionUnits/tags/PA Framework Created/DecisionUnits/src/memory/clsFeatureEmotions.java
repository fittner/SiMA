package memory;

import memory.tempframework.cls0to1;
import memory.tempframework.clsContainerEmotion;
import memory.tempframework.clsEmotion;

/**
 * The class clsFeatureEmotions represents the feature emotions. It inherites the basic functionalities which are equal for each feature and implements specific functionalities for the feature emotions. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsFeatureEmotions extends clsFeature  {
	private clsContainerEmotion moContainerEmotions;

	/**
	 * Instantiates a new feature emotions with the respective elements indicated in the container in the parameter. If
	 * triggering encoding or spontaneous retrieval for this feature should be disabled, mnTriggerEncodingEnabled respectively
	 * mnSpontaneousRetrievalEnabled have to be set false.
	 * @param poEmotions The container with the individual emotions of this situation
	 */
	public clsFeatureEmotions(clsContainerEmotion poEmotions) {
		super();
		moContainerEmotions = poEmotions;
		for(int i=0; i<poEmotions.size(); i++){
			clsEmotion oEmotion = (clsEmotion)poEmotions.get(i);
			moFeatureElements.add( new clsElementEmotion( oEmotion ));
		}
	}
	/**
	 * Returns the initially delivered actions in a container (clsContainerEmotions)
	 */
	@Override
	public Object getContainer() {
		return moContainerEmotions;
	}
	/**
	 * Determines the emotional tone of the entity to an encoded event. It is defined to be the maximum level of an
	 * individual emotion
	 * @return The emotional tone of the event
	 */
	public cls0to1 getEmotionalTone() {
		float rEmotionalTone = 0;
		for(int i=0; i<moFeatureElements.size(); i++) {
			clsElementEmotion oEmotion = (clsElementEmotion)moFeatureElements.get(i);
			float rEmotionVal = oEmotion.getEmotion().getInternalValue();
			if( rEmotionVal > rEmotionalTone) {
				rEmotionalTone = rEmotionVal;
			}
		}
		return new cls0to1(rEmotionalTone);
	}
	@Override
	public boolean checkIfSameType(clsFeature poFeature) {
		if(poFeature instanceof clsFeatureEmotions) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "Emotions";
	}
}
