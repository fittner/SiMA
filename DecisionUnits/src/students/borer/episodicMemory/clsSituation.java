package students.borer.episodicMemory;

// Imports
import java.util.Vector;

/**
 * The class clsSituation represents a situation that can be composed of various features. The features of a situation are
 * stored in a java.util.Vector
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *  
 */
public class clsSituation {
	protected Vector<clsFeature> moFeatures;

	public clsSituation() {
		moFeatures = new Vector<clsFeature>();  
	}	
	public clsSituation(Vector<clsFeature> poFeatures) {
		moFeatures = poFeatures;  
	}
	
	/**
	 * Adds a feature to the situation
	 * @param poFeature The feature to be added
	 */
	public void addFeature(clsFeature poFeature) {
		moFeatures.add(poFeature);
	}
	public Vector<clsFeature> getFeatures() {
		return moFeatures;
	}
	
	/**
	 * Returns the situational data originally delivered to the EM module 
	 * @return A java.util.Vector with the situational informations. The elements of the Vector
	 * have the following ordering:
	 * [clsContainerDrives | clsContainerEmotions | clsContainerCompareResults | clsActionContainer]
	 */
	public Vector<Object> getSituationalData() {
		Vector<Object> oSitData = new Vector<Object>();
		for(int i=0; i<moFeatures.size(); i++) {
			clsFeature oFeat = (clsFeature)moFeatures.get(i);
			oSitData.add( oFeat.getContainer() );
		}
		return oSitData;
	}

	/**
	 * @return Returns the feature emotions of the situation
	 */
	public clsFeatureEmotions getFeatureEmotions(){
		for(int i=0; i<moFeatures.size(); i++) {
			if(moFeatures.get(i) instanceof clsFeatureEmotions) {
				return (clsFeatureEmotions)moFeatures.get(i);
			}
		}
		return null;
	}

	/**
	 * @return Returns the feature TI-matches of the situation
	 */
	public clsFeatureCompareResults getFeatureCompareResults(){
		for(int i=0; i<moFeatures.size(); i++) {
			if(moFeatures.get(i) instanceof clsFeatureCompareResults) {
				return (clsFeatureCompareResults)moFeatures.get(i);
			}
		}
		return null;
	}
	/**
	 * @return Returns the feature Actions of the situation
	 */
	public clsFeatureActions getFeatureActions(){
		for(int i=0; i<moFeatures.size(); i++) {
			if(moFeatures.get(i) instanceof clsFeatureActions) {
				return (clsFeatureActions)moFeatures.get(i);
			}
		}
		return null;
	}
	
	
	public clsFeatureStep getFeatureStep(){
		for(int i=0; i<moFeatures.size(); i++) {
			if(moFeatures.get(i) instanceof clsFeatureStep) {
				return (clsFeatureStep)moFeatures.get(i);
			}
		}
		return null;
	}
	
	
	/**
	 * Determines the salient features in this situation and checks, whether this situation should be encoded as an event.
	 * Each feature for which mnTriggerEncodingEnabled is set will be checked.
	 * @return true, if something significant happens in this situation and an event should be encoded; otherwise fals
	 */
	public boolean triggerEncoding(clsSituation poPrevSituation){
		boolean nTrigger = false;
		for(int i=0; i<moFeatures.size(); i++) {
			// traverse all features of the situation
			clsFeature oFeature = (clsFeature)moFeatures.get(i);
			if(oFeature.mnTriggerEncodingEnabled) {
				if( oFeature.triggerEncoding( (clsFeature)poPrevSituation.getFeatures().get(i)) ){
					nTrigger = true;
				}
			}
		}		
		return nTrigger;
	}
}
