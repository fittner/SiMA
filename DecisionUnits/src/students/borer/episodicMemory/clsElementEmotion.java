package students.borer.episodicMemory;

import students.borer.episodicMemory.tempframework.clsEmotion;
/**
 * The class clsElementEmotion represents a feature element emotion. It represents one emotion (clsElementEmotion) and implements the additional functionalities inherited from the abstract class clsFeatureElement $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsElementEmotion extends clsFeatureElement {
	private clsEmotion moEmotion;
	private float mrDeltaToPrevSituation;
	private float mrDeltaToPrevEvent;
	private boolean mnTrigger; // Trigger: set if this emotion caused a Encoding

	/**
	 * The parameter Delta0 of the gaussian matching function
	 */
	private static float MATCH_DELTA0 = 0.13f;
	/**
	 * The parameter S_E0 of the salience function (default: 0.05) ( salience = 1 - exp( -(bias + absLevel) * |delta| / S_E0) )
	 */
	public static float SALIENCE_SE0 = 0.05f;
	/**
	 * Determines the threshold, the salience value of this feature element must exceed to trigger the encoding of an event
	 * on the basis of this element
	 */
	public static float SALIENCE_TRIGGER_THRESHOLD = 0.5f;

	public clsElementEmotion(clsEmotion poEmotion) {
		super();
		moEmotion = poEmotion;
		mnTrigger = false;
		mrDeltaToPrevSituation = 0;
		mrDeltaToPrevEvent = 0;
	}
	public clsEmotion getEmotion() {
		return moEmotion;
	}
	private float functionSalience(float prDelta) {
		// salience = 1 - exp( -(bias + absLevel) * |delta| / S_E0)
		// the bias is because if there is a high negative change, this would not be recognized if the
		// absolut value was 0
		float rBias = 0;
		float rAbsValue = moEmotion.getInternalValue();
		if (prDelta < 0) {
			rBias = Math.abs( prDelta ) / 2; // add bias if negative change is very high
		}
		float rArg = (rBias +  rAbsValue) * Math.abs( prDelta );
		float rSalience = 1 - (float)Math.exp((-1) * rArg / SALIENCE_SE0);
		return rSalience;
	}
	
	/**
	 * Sets the salience and checks, if the encoding of an event on the basis of this emotion should be triggered. 
	 * Currently: trigger encoding on the basis of a salience level (change to the current situation and absolut level) and
	 * further checks slow emotional changes over longer time periods 
	 */
	@Override
	public boolean triggerEncoding(clsFeatureElement poPrevEmotion) {
		clsElementEmotion oPrevEmotion = (clsElementEmotion)poPrevEmotion;
		//calculates difference to previous Emotion
		mrDeltaToPrevSituation = moEmotion.getInternalValue() - oPrevEmotion.getEmotion().getInternalValue();
		moSalience.set( functionSalience(mrDeltaToPrevSituation) );
		
		triggerOnSalience();
		//triggerOnThreshold();
		//triggerOnPrevSituation();
		triggerOnPrevEvent(oPrevEmotion); // to detect slow changes

		if(mnTrigger) {
//			System.out.println("Triggering Emotion: "+moEmotion.getType() + "  - Salience = " + moSalience.get());
//			System.out.println("       Level: "+moEmotion.getInternalValue().getValue()+ "  dSit: "+mrDeltaToPrevSituation+ "  dEv: "+mrDeltaToPrevEvent);
//			System.out.println("  Delta Sit: "+mrDeltaToPrevSituation+"  Delta Ev: "+ mrDeltaToPrevEvent);
			mrDeltaToPrevEvent = 0;
		}
		decaySalience(poPrevEmotion);
		return mnTrigger;
	}
	private void triggerOnSalience(){
		if(moSalience.get() > SALIENCE_TRIGGER_THRESHOLD) {
			mnTrigger = true;
		}
	}
	@SuppressWarnings("unused")
	private void triggerOnThreshold() {
		// trigger on change event...
		float rThreshold = 0.5f;
		if( moEmotion.getInternalValue() >= rThreshold ) {  // trigger encoding of this emotion if emotional level is above a certain threshold
			// check if emotional level was below the threshold in the previous situation and changed to be above)
			if(moEmotion.getInternalValue() - mrDeltaToPrevSituation < rThreshold) {
				mnTrigger = true;
			}
		}
		else { // Value < Threshold
			// check if emotional level was above the threshold in the previous situation and changed to be below)
			if( moEmotion.getInternalValue() - mrDeltaToPrevSituation >= rThreshold ){
				mnTrigger = true;
			}
		}
	}
	@SuppressWarnings("unused")
	private void triggerOnPrevSituation(){
		if(Math.abs( mrDeltaToPrevSituation ) > 0.02) {
			mnTrigger = true;
		}
	}
	private void triggerOnPrevEvent(clsElementEmotion poPrevEmotion){
		// Triggers encoding, if the emotional level keeps on monotonically increasing (tracks the emotional level and
		// triggers, if the change - measured by a salience value - is high enough)
		mrDeltaToPrevEvent = poPrevEmotion.mrDeltaToPrevEvent + mrDeltaToPrevSituation;  // sums up the difference to the last such event
		if(mrDeltaToPrevSituation > 0 && poPrevEmotion.mrDeltaToPrevSituation <= 0) {
			// initiate new tracking
			mrDeltaToPrevEvent = mrDeltaToPrevSituation;
			// maybe: trigger encoding of starting event
			//mnTrigger = true;
		}
		if(mrDeltaToPrevSituation <= 0 && poPrevEmotion.mrDeltaToPrevSituation > 0) {
			// end of tracking (emotional level does not increase anymore), check if salience is high enough
			float rSalience = functionSalience(mrDeltaToPrevEvent);
			if( rSalience > SALIENCE_TRIGGER_THRESHOLD) {
				moSalience.set(rSalience);
				mnTrigger = true;
			}
		}		
	}
	/**
	 * Calculates the match of this emotion to the one inidicated in the parameter on the basis of
	 * a gaussian matching function
	 * @param poEmotion The emotion of the retrieval cue (the cue element)
	 * @return A clsMatchFeatureElement object representing the match of this element to the cue element
	 */
	@Override
	public clsMatchFeatureElement getMatch(clsFeatureElement poEmotion) {
		clsElementEmotion oEmotionCue = (clsElementEmotion)poEmotion;
		float rDiff = Math.abs( moEmotion.getInternalValue() - oEmotionCue.getEmotion().getInternalValue() );
		float rMatch = (float)Math.exp(-0.5 * Math.pow(rDiff/MATCH_DELTA0, 2));
		return new clsMatchFeatureElement(oEmotionCue, rMatch);
	}
	@Override
	public boolean checkIfSameType(clsFeatureElement poFeatElem) {
		if(poFeatElem instanceof clsElementEmotion) {
			clsElementEmotion oEmotion = (clsElementEmotion)poFeatElem;
			if(moEmotion.getType() == oEmotion.getEmotion().getType()) {
				return true;
			}
		}
		return false;
	}
}
