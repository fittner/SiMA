package episodicMemory;

import episodicMemory.tempframework.clsRuleCompareResult;
import episodicMemory.tempframework.enumTypeAbstractImageRating;

/**
 * The class clsElementCompareResult represents a feature element TI-match. It represents one TI-match (clsRuleCompareResult) and implements the additional functionalities inherited from the abstract class clsFeatureElement $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsElementCompareResult extends clsFeatureElement {
	private clsRuleCompareResult moRuleCompareResult;
	private float mrDeltaToPrevSituation;
	private float mrDeltaToPrevEvent;
	private float mrAIthreshold;
	private boolean mnTrigger; // Trigger: set if this CompareResult caused a Encoding
	/**
	 * The parameter Delta0 of the gaussian matching function
	 */
	public static float MATCH_DELTA0 = 0.13f;
	/**
	 * The parameter S_TI0 of the salience function (default: 0.32) ( salience = 1 - exp( -(bias + absLevel) * |delta| / S_TI0) )
	 */
	public static float SALIENCE_STI0 = 0.32f;
	/**
	 * Determines the threshold, the salience value of this feature element must exceed to trigger the encoding of an event
	 * on the basis of this element
	 */
	public static float SALIENCE_TRIGGER_THRESHOLD = 0.5f;
	
	public clsElementCompareResult(clsRuleCompareResult poRuleCompareResult) {
		super();
		moRuleCompareResult = poRuleCompareResult;
		mnTrigger = false;
		mrDeltaToPrevSituation = 0;
		mrDeltaToPrevEvent = 0;

		// determines the threshold, the TI-match must exceed for triggerOnThreshold
		mrAIthreshold = (float)enumTypeAbstractImageRating.get0to1( enumTypeAbstractImageRating.TAIRATING_HIGH );
	}
	public clsRuleCompareResult getRuleCompareResult() {
		return moRuleCompareResult;
	}
	private float functionSalience(float prDelta) {
		// salience = 1 - exp( -(bias + absLevel) * |delta| / S_TI0)
		// the bias is because if there is a high negative change, this would not be recognized if the
		// absolut value was 0
		float rBias = 0;
		float rAbsMatch = moRuleCompareResult.getMatch().get();
		if (prDelta < 0) {
			rBias = Math.abs( prDelta ) / 2; // add bias if negative change is very high
		}
		float rArg = (rBias +  rAbsMatch) * Math.abs( prDelta );
		float rSalience = 1 - (float)Math.exp((-1) * rArg / SALIENCE_STI0);
		return rSalience;
	}
	/**
	 * Sets the salience and checks, if the encoding of an event on the basis of this drive should be triggered
	 * Currently: trigger encoding on the basis of a salience level (change to the current situation and absolut level)
	 */
	@Override
	public boolean triggerEncoding(clsFeatureElement poPrevCompResult) {
		clsElementCompareResult oPrevCompResult = (clsElementCompareResult)poPrevCompResult;
		//calculates difference to RuleCompareResult of the previous situation
		mrDeltaToPrevSituation = moRuleCompareResult.getMatch().get() - oPrevCompResult.getRuleCompareResult().getMatch().get();
		
		moSalience.set( functionSalience(mrDeltaToPrevSituation) );
		
		triggerOnSalience();
		//triggerOnThreshold(oPrevCompResult);
		//triggerOnPrevSituation(oPrevCompResult);
		//triggerOnPrevEvent(oPrevCompResult);
		if(mnTrigger) {
//			System.out.println("Triggering TI: "+moRuleCompareResult.moAbstractImageId.intValue() + "  - Salience = " + moSalience.get());
//			System.out.println("       TI-match: "+moRuleCompareResult.getMatch().get()+ "  delta: "+mrDeltaToPrevSituation);
//			System.out.println("  Delta Sit: "+mrDeltaToPrevSituation+"  Delta Ev: "+ mrDeltaToPrevEvent);
		}
		decaySalience(oPrevCompResult);
		return mnTrigger;
	}
	private void triggerOnSalience(){
		if(moSalience.get() > SALIENCE_TRIGGER_THRESHOLD) {
			mnTrigger = true;
		}
	}
	private void triggerOnThreshold(clsElementCompareResult poPrevCompResult) {
		// trigger on change event...
		if( moRuleCompareResult.getMatch().get() >= mrAIthreshold ) {  // trigger encoding of this TI if TI match is above a certain threshold
			// check if TI-match was below the threshold in the previous situation and changed to be above)
			if(mrDeltaToPrevSituation >= 0.2f) {
				mnTrigger = true;
			}
		}
		else { // match < mrAIthreshold
			// check if TI-match was above the threshold in the previous situation and changed to be below)
			if( moRuleCompareResult.getMatch().get() - mrDeltaToPrevSituation >= mrAIthreshold ){
				if(mrDeltaToPrevSituation <= -0.2f) {
					mnTrigger = true;
				}
			}
		}
	}
	private void triggerOnPrevSituation(clsElementCompareResult poPrevCompResult){
		if(mrDeltaToPrevSituation >= 0.5f) {
			mnTrigger = true;
		}
	}
	private void triggerOnPrevEvent(clsElementCompareResult poPrevCompResult){
		//calculates difference to the previous occurance of this type of RuleCompareResult that triggered an event
		if(mnTrigger) {
			mrDeltaToPrevEvent = 0; // this TI caused encoding
		}
		else {
			mrDeltaToPrevEvent = poPrevCompResult.mrDeltaToPrevEvent + mrDeltaToPrevSituation;  // sums up the difference to the last such event
			if(mrDeltaToPrevEvent >= 0.5f) {
				mnTrigger = true;
			}
		}
		
	}
	/**
	 * Calculates the match of this TI-match to the one inidicated in the parameter on the basis of
	 * a gaussian matching function
	 * @param poCompareResult The TI-match of the retrieval cue (the cue element)
	 * @return A clsMatchFeatureElement object representing the match of this element to the cue element
	 */
	@Override
	public clsMatchFeatureElement getMatch(clsFeatureElement poCompareResult) {
		// compares this  and returns the match
		clsElementCompareResult oCompResultCue = (clsElementCompareResult)poCompareResult;
		float rDiff = Math.abs( moRuleCompareResult.getMatch().get() - oCompResultCue.getRuleCompareResult().getMatch().get() );
		float rMatch = (float)Math.exp(-0.5 * Math.pow(rDiff/MATCH_DELTA0, 2));
		return new clsMatchFeatureElement(oCompResultCue, rMatch);
	}
	@Override
	public boolean checkIfSameType(clsFeatureElement poFeatElem) {
		if(poFeatElem instanceof clsElementCompareResult) {
			clsElementCompareResult oCompResult = (clsElementCompareResult)poFeatElem;
			if(moRuleCompareResult.moAbstractImageId.intValue() == 
				oCompResult.getRuleCompareResult().moAbstractImageId.intValue()) {
				return true;
			}
		}
		return false;
	}
}
