package students.borer.episodicMemory;


import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

import students.borer.episodicMemory.tempframework.cls0to1;

/**
 * The class clsEvent represents an event which is an extension of a situation (it inherits the class clsSituation). An event is the prototypical memory unit $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsEvent extends clsSituation{
	public cls0to1 moSalience;
	public cls0to1 moEmotionalTone;
	public cls0to1 moActivation;  // the activation level
	public clsEpisodeContainer moRelatedEpisodes; // Container of the episodes, the event is related to
	public Integer moMemoryKey = null;  // the Memory Key, where the Event is stored
	
	/**
	 * Represents the initial internal value for the parameter T0 of the activation function. The parameter T0 further depends
	 * on the emotional tone (ET), the salience (S) and the relatedness to episodes (rel) of the event.
	 * (T0 = ACTIVATION_T0 + ACTIVATION_T0emot*ET + ACTIVATION_T0sal*S + ACTIVATION_T0rel)
	 */
	public static int ACTIVATION_T0 = 2000;
	/**
	 * Represents the parameter for the influence of the emotional tone on the activation function (dT0/dET). This parameter
	 * is multiplied by the value of the emotional tone (moEmotionalTone) and added to the internal parameter T0.
	 */
	public static int ACTIVATION_T0emot = 2000;
	/**
	 * Represents the parameter for the influence of the salience on the activation function (dT0/dS). This parameter
	 * is multiplied by the value of the salience (moSalience) and added to the internal parameter T0.
	 */
	public static int ACTIVATION_T0sal = 1000;
	/**
	 * Represents the parameter for the influence of the relatedness to episode on the activation function (dT0/drel). If
	 * this event belongs to one episode, a value of ACTIVATION_T0rel is added to the internal parameter T0. For each further
	 * episode this event is related to, a part (e.g. a fifth) of this ACTIVATION_T0rel is additionally added to T0.
	 */
	public static int ACTIVATION_T0rel = 5000;
	/**
	 * The activation level A0, with which a encoded event is initialized.
	 */	
	public static float ACTIVATION_INIT = 0.6f;
	/**
	 * The value, with which the activation of an event is boosted (Ab0) if it is recalled. The events of related episodes 
	 * are further strengthened with a share of this value (e.g. Ab1 = Ab0 / 3)
	 */
	public static float ACTIVATION_BOOST = 0.06f;
	private int mnAct_T0;      // the parameter T0 of the activation function
	private int mnIntActValue; // the internal activation value
//FOR VISUALIZATION OF THE ACTIVATION LEVEL------------------	
	/**
	 * FOR VISUALIZATION OF THE ACTIVATION LEVEL
	 * if set true: the history of the activation level is captured by the Vector moVisActHistory
	 */
	public static boolean mnVisualizeActivation; 
	@SuppressWarnings("rawtypes")
	public Vector moVisActHistory = new Vector();
	public int mnVisActHistorySize = 130; // the size of the activation history vector
	public int mnVisSampleRate = 40; // Record the Activation level every <mnVisSampleRate> simulation steps
	public int mnVisCounter = 0;
//	----------------------------------------------------	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public clsEvent (Vector poFeatures) {
		super(poFeatures);
		moSalience = new cls0to1();
		moEmotionalTone = new cls0to1(); // -> maximum level of 
		moActivation = new cls0to1();		
		moRelatedEpisodes = new clsEpisodeContainer();
		
// FOR VISUALIZATION OF THE ACTIVATION LEVEL --------------------
		if(mnVisualizeActivation) {
			for(int i=0; i<mnVisActHistorySize; i++) {
				moVisActHistory.add(new cls0to1());
			}
		}
//-------------------------------------------------------		
	}
	
	/**
	 * Determines the salience, the emotional tone and the initial activation ot the event
	 * @param pnMemKey The memory key of the memory container, where the event has been encoded
	 */
	public void setEvent(int pnMemKey) {
		moMemoryKey = new Integer(pnMemKey);
		float rSalience = 0;
		int nFeatCount = 0;
		for(int i=0; i<moFeatures.size(); i++) {
			clsFeature oFeature = (clsFeature)moFeatures.get(i);
			// Determine the emotional tone of the entity to the event from the feature emotions
			if( oFeature instanceof clsFeatureEmotions) {
				clsFeatureEmotions oEmotions = (clsFeatureEmotions)oFeature;
				moEmotionalTone = oEmotions.getEmotionalTone(); // derives the emotional tone of the event
			}
			// Determine the salience of the event
			if(oFeature.mnTriggerEncodingEnabled) { // check if the salience of this feature was determined when triggering encoding
				// each feature contributes the same share to the salience of the event
				rSalience+= oFeature.moSalience.get();
				nFeatCount++;
			}
		}
		if(nFeatCount != 0) moSalience.set( rSalience / (float)nFeatCount );
		//System.out.println("Encoded Event Salience: " + moSalience.get());
		
		// Initialize the internal parameter of the activation function
		float dT0_emot = ACTIVATION_T0emot * moEmotionalTone.get();
		float dT0_sal = ACTIVATION_T0sal * moSalience.get();
		mnAct_T0 = ACTIVATION_T0 + (int)dT0_emot + (int)dT0_sal;
		mnIntActValue =(int)(-mnAct_T0 * Math.log(ACTIVATION_INIT)); // determine initial internal value
	}
	
	/**
	 * Relates this event to the episode indicated in the parameter, and adapts the parameter T0 of the activation function 
	 * (this event is now related to more episodes)
	 * @param poEpisode The episode to which this event belongs to
	 */
	public void relateToEpisode(clsEpisode poEpisode) {
		moRelatedEpisodes.addEpisode(poEpisode);
		int nSize = moRelatedEpisodes.getEpisodeSize();
		int nAct_T0_old = mnAct_T0;
		if(nSize == 1) mnAct_T0+=ACTIVATION_T0rel;
		if(nSize == 2) mnAct_T0+=ACTIVATION_T0rel/5;
		if(nSize == 3) mnAct_T0+=ACTIVATION_T0rel/5;
		// change of the parameter T0 -> internal activation value has to be adapted!
		mnIntActValue = mnIntActValue * mnAct_T0 / nAct_T0_old;
	}
	
	/**
	 * Determines the match of this event to the salient features of the cue event in the paramter. This function is
	 * optimized by making use of the same structure (the same order) of the features and feature elements of this
	 * event and the cue event. Each feature for which mnSpontaneousRetrievalEnabled is set will be checked.
	 * @param poCueEvent The event with which a retrieval is cued (the salient features are used as cue) 
	 * @return An object of the class clsMatch representing the matching of features of this event to the cue event
	 */
	public clsMatch getMatch(clsEvent poCueEvent) {
		clsMatch oMatch = new clsMatch();
		for(int i=0; i<moFeatures.size(); i++) {
			clsFeature oFeature = (clsFeature)moFeatures.get(i);
			clsFeature oCueFeat = (clsFeature)poCueEvent.getFeatures().get(i);
			if(oFeature.mnSpontaneousRetrievalEnabled) {
				if(oFeature instanceof clsFeatureEmotions){
					// check for the emotions anyway (to implement the mood-state dependency)
					clsMatchFeature oFeatureMatch = oFeature.getMatch( oCueFeat );
					oMatch.addFeatureMatch(oFeatureMatch);
				}
				else{
					// only traverse the salient features of the cue event and the stored event
					if(oCueFeat.checkIfSalient()){ // check if this feature of the cue is salient
						if(oFeature.checkIfSalient()) { // check if the same feature of the memory is salient too
							clsMatchFeature oFeatureMatch = oFeature.getMatch( oCueFeat ); // calculate the match of this feature
							oMatch.addFeatureMatch(oFeatureMatch);
						}
						else {
							oMatch.addFeatureMatch( new clsMatchFeature( oCueFeat )); // adds a feature match with match 0
						}
					}
				}
			}
		}
		return oMatch;
	}
//	public clsMatch getMatch(clsSituation poCueSituation) {
//		// this match function is only works correctly if features and feature elements of the
//		// memory and the cue have the same structure (same order)
//		clsMatch oMatch = new clsMatch();
//		for(int i=0; i<moFeatures.size(); i++) {
//			clsFeature oFeature = (clsFeature)moFeatures.get(i);
//			clsFeature oCueFeat = (clsFeature)poCueSituation.getFeatures().get(i);
//			clsMatchFeature oFeatureMatch = oFeature.getMatch( oCueFeat ); // redirects to the derived featurematch object
//			oMatch.addFeatureMatch(oFeatureMatch);
//		}
//		return oMatch;
//	}
	/**
	 * Determines the match of this event to an arbitrary selection of due features indicated in the paramter.
	 * @param poCueFeatures An arbitrary selection of cue features 
	 * @return An object of the class clsMatch representing the matching of features of this event to the cue features
	 */
	@SuppressWarnings("rawtypes")
	public clsMatch getMatch(Vector poCueFeatures) {
		clsMatch oMatch = new clsMatch();
		for(int i=0; i<poCueFeatures.size(); i++) {
			clsFeature oCueFeat = (clsFeature)poCueFeatures.get(i);
			clsFeature oFeature = null;
			for(int j=0; j<moFeatures.size(); j++) {
				oFeature = (clsFeature)moFeatures.get(j);
				if(oFeature.checkIfSameType(oCueFeat)) {
					// oFeature and the oCueFeature are of the same type (e.g. emotions)
					clsMatchFeature oFeatureMatch = oFeature.getDeliberateMatch( oCueFeat ); // calculate the match of this feature
					oMatch.addFeatureMatch(oFeatureMatch);
					break;
				}
			}
		}
		return oMatch;		
	}
	
	/**
	 * Calculates the activation level
	 * @return The activation level
	 */
	public cls0to1 getActivation() {
		float rActivation = (float)Math.exp(-(float)mnIntActValue / mnAct_T0);
		moActivation.set(rActivation);
		return moActivation;
	}
	
	/**
	 * Boosts the activation of this event with the value defined by the parameter ACTIVATION_BOOST.
	 * @param poBoostRelatedEpisodes If this parameter is set true, all episodes this event belongs to are further strenghtened
	 */
	@SuppressWarnings("rawtypes")
	public void boost(boolean poBoostRelatedEpisodes) {
		mnIntActValue-= getIntBoost();
		if(mnIntActValue < 0) mnIntActValue=0;
		
		if(poBoostRelatedEpisodes) {
			// boost as well all episodes the event belongs to
			Iterator oIt = moRelatedEpisodes.keySet().iterator();
			while(oIt.hasNext()) {
				// traverse all Episodes of all Scenarios
				Integer oScenarioId = (Integer)oIt.next();
				TreeSet oEpisodes = (TreeSet)moRelatedEpisodes.getContainer().get(oScenarioId);
				Iterator oItEp = oEpisodes.iterator();
				while( oItEp.hasNext() ) {
					clsEpisode oEpisode = (clsEpisode)oItEp.next();
					oEpisode.boost();
				}
			}
		}
	}
	
	/**
	 * Boosts this event by a part (e.g. a third) of the parameter ACTIVATION_BOOST. Is called, when boosting related episodes
	 */
	public void boostRelated() {
		mnIntActValue-= getIntBoost()/3;
		if(mnIntActValue < 0) mnIntActValue=0;		
	}
	
	/**
	 * Updates the activation of the event. Represents the decay of the activation level
	 *
	 */
	@SuppressWarnings("unchecked")
	public void updateActivation() {
		if(mnIntActValue <= 30000) {
			mnIntActValue+=2;
		}
		
// FOR VISUALIZATION OF THE ACTIVATION LEVEL------------------
		if(mnVisualizeActivation) {
			mnVisCounter++;
			if(mnVisCounter == mnVisSampleRate) { // record the activation level every <mnVisSampleRate> simulation steps
				mnVisCounter = 0;
				moVisActHistory.add(0, new cls0to1(getActivation().get()) );
				moVisActHistory.remove(mnVisActHistorySize);
			}
		}
//---------------------------------------------------------------		
	}
	
	private int getIntBoost() {
		// calculates the boost level, by which the internal activation level has to be incremented (depends on the current
		// activation level and the parameter T0) 
		float rAct = getActivation().get();
		float rBoost = (float)mnIntActValue +  (float)mnAct_T0 *(float)Math.log(ACTIVATION_BOOST + rAct);
		return (int)rBoost;
	}
	
	@Override
	public String toString() {
		return "Event " + moMemoryKey.intValue();		
	}
	
}
