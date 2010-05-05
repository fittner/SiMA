package students.borer.episodicMemory;


import java.util.Vector;
import students.borer.LocalizationOrientation.clsStep;
import students.borer.Mason.Bag;
import students.borer.episodicMemory.tempframework.clsActionContainer;
import students.borer.episodicMemory.tempframework.clsContainerCompareResults;
import students.borer.episodicMemory.tempframework.clsContainerDrive;
import students.borer.episodicMemory.tempframework.clsContainerEmotion;
import students.borer.episodicMemory.tempframework.clsRecognitionProcessResult;

/**
 * The class clsAlarmSystem detects when events or episodes have to be encoded and triggers spontaneous retrieval. Therefore, the currently perceived situation has to be handed over as parameter of the method perform(). The respective situation is temporally stored to detect changes. It further contains an episode recognition  unit to detect when a scenario recognition process has finished and should be encoded as an episode. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */ 
public class clsAlarmSystem {
	/**
	 * A link to the memory container to call the respective methods for encoding events and episodes
	 */
	public clsContainerMemory moMemory;
	/**
	 * Episode Recognition unit to detect when a scenario recognition process has finished and should be encoded as an episode
	 */
	public clsEpisodeRecognitionUnit moEpisodeRecognition;
	/**
	 * Temporally stores the previous situation to detect changes
	 */
	public clsSituation moPreviousSituation;
	
	// THE FOLLOWING ATTRIBUTES ARE DEFINED FOR VISUALIZATION -----------------
	/**
	 * FOR VISUALIZATION OF THE EPISODIC MEMORIES
	 * if set true: the episodic data is captured and accessible for visualization by the variables
	 * declared with "Vis"
	 */
	public boolean mnVisualization = true; // true: for visualization in AnyLogic
	public boolean mnVisEncodedEpisode = false;
	public boolean mnVisSpontRetrieval = false;
	public Vector moVisEpisodes = new Vector();
	public Vector moVisEncodedEpisodes = new Vector();
	public clsEvent moVisEvent = null;
	public boolean mnVisTrackRetrieval = true; // live mode/manual mode
	public boolean nTrigger = false; // defined public for visualization (otherwise declared in method perform())
//Debug stepMemory	
	public Bag stepHistory = new Bag();
//-----------------------------------------------------------------	
	/**
	 * Standard Konstruktor
	 * @param poMemory A link to the memory container has to be handed over to to
	 * call the respective methods for encoding events and episodes
	 */
	public clsAlarmSystem(clsContainerMemory poMemory) {
		moMemory = poMemory;
		moPreviousSituation = null;
		moEpisodeRecognition = new clsEpisodeRecognitionUnit();
		
// FOR VISUALIZATION -------------------------------------------
		if(mnVisualization) {
			clsEvent.mnVisualizeActivation = true;
		}
		else {
			clsEvent.mnVisualizeActivation = false;			
		}
//-------------------------------------------------------------------		
	}
	/**
	 * Has to be called for each new perceived situation. Processes the incoming data and
	 * calls the respective methods to trigger the encoding of an event or epiosde and a possibly spontaneous retrieval
	 * 
	 * @param poDrives A container with the drives of the current situation
	 * @param poEmotions A container with the emotions of the current situation
	 * @param poImageCompareResults A container with the TI-matches of the current situation
	 * @param poActions A container with the actions of the current situation
	 * @param poScenarioRecogProcesses The scenario recognition process that are currently initialized, recognized or aborted
	 * @return A container with the retrieval results if a spontaneous retrieval has been triggered; otherwise null
	 */
	public clsContainerRetrievalResults perform(clsContainerDrive poDrives,
			  clsContainerEmotion poEmotions,
			  clsContainerCompareResults poImageCompareResults,
			  clsActionContainer poActions,
			  clsRecognitionProcessResult poScenarioRecogProcesses,
			  clsStep currentStep) {
			
		  // set current Situation
		  clsSituation oSituation= new clsSituation();
		  oSituation.addFeature( new clsFeatureDrives( poDrives ));
		  oSituation.addFeature( new clsFeatureEmotions( poEmotions ));
		  oSituation.addFeature( new clsFeatureCompareResults(poImageCompareResults) );
		  oSituation.addFeature( new clsFeatureActions(poActions) );
		  oSituation.addFeature( new clsFeatureStep(currentStep) );
		  

		  
		  clsContainerRetrievalResults oRetrievalResults = null;
// FOR VISUALIZATION -------------------------------------------
		  //boolean nTrigger = false; // for visualization: this variable is defined as public of this class
		  nTrigger=false;
		  if(mnVisualization) {
				mnVisEncodedEpisode = false;
				mnVisSpontRetrieval = false;
		  }
// -------------------------------------------------------------		  
		  if(moPreviousSituation != null){
			nTrigger = oSituation.triggerEncoding(moPreviousSituation); // checks whether an event should be encoded 
			moEpisodeRecognition.setScenarioRecogProcesses(poScenarioRecogProcesses);		
			moEpisodeRecognition.checkForAborted();
			if (!nTrigger ) {
				// check if an event should be encoded if it was not detected by oSituation.triggerEncoding()
				// (if a scenario has been initialized or completed)
				nTrigger = moEpisodeRecognition.trigger(); 
			} 
			//encode if triggered or if it is the first entry.
			if(nTrigger == true || (moMemory.size()==0)) {
				
				clsEvent oEvent = new clsEvent( oSituation.getFeatures() );
				// RETRIEVE
				// first retrieve because otherwise the now encoded Event would be retrieved!
				moEpisodeRecognition.checkForInitialized(oEvent);
// 				maybe spontaneous retrieval only when an episode has been recognized -> cue: scenario id of episode
//				Vector oScenarios = moEpisodeRecognition.getScenariosInProgress();
//				if(oScenarios !=null){
					// trigger spontaneous retrieval if some episodes are in progress
					clsRetrievalCue oCue = new clsRetrievalCue();
					oCue.moEvent = oEvent;
//					oCue.moScenarios=oScenarios;
					oRetrievalResults = moMemory.retrieve(oCue);
//				}
				
				// ENCODING
				moMemory.encode(oEvent);
				Vector oRecognizedEpisodes = moEpisodeRecognition.checkForRecognized(oEvent);
				
				for(int i=0; i<oRecognizedEpisodes.size(); i++) {
					// encode the recognized episodes
					clsEpisode oEpisode = (clsEpisode)oRecognizedEpisodes.get(i);
					moMemory.encodeEpisode(oEpisode);
					// print informations about the encoded episode
					//System.out.print("\nEncoded episode (Ep"+oEpisode.moEpisodeId.intValue()+") of scenario: " + oEpisode.moScenario.moName);
					
				}
				
// FOR VISUALIZATION -------------------------------------------
				if(mnVisualization) {
					addToVisEncodedEpisodes(oRecognizedEpisodes);
					if(mnVisTrackRetrieval && oRetrievalResults!=null) {
						clsRetrievalResult oRes = oRetrievalResults.getFirstResult();
						if(oRes!=null) {
							mnVisSpontRetrieval = true;
							moVisEvent = oRes.moEvent;
							createVisEpVector();
						}
					}
				}
//------------------------------------------------------------	
				
//				debug stepMemory if this is not used the steps  from the momemory have to extracted in the pathplanning
//				stepHistory.add(((clsElementStep) ((clsEvent)this.moepisodicMemory.getLatestObject()).getFeatureStep().getFeatureElements().get(0) ).getStep());
				
				
				
			}
		  }
		  moMemory.decay();
		  moPreviousSituation = oSituation;
		  return oRetrievalResults;
	}
	
// METHODS FOR ANYLOGIC VISUALIZATION --------------------------------
	/**
	 * FOR VISUALIZATION OF THE RETRIEVED EPISODES
	 */
	public Vector createVisEpVector() {
		moVisEpisodes.clear();
		if(moVisEvent != null) {
			moVisEvent.moRelatedEpisodes.getEpisodesInVector( moVisEpisodes );
		}
		return moVisEpisodes;
	}
	private void addToVisEncodedEpisodes(Vector poRecognizedEpisodes){
		for(int i=0; i<poRecognizedEpisodes.size(); i++) {
			moVisEncodedEpisodes.add( poRecognizedEpisodes.get(i) );
			mnVisEncodedEpisode = true;			
		}
		//if(moVisEncodedEpisodes.size() > 3){ delete...
	}
	public String toStringEncodedEpisodes() {
		String oRet = "";
		int nSize = moVisEncodedEpisodes.size();
		if (nSize > 0){
			for(int i=nSize-1; i>=nSize-3; i--){
				if(i>=0) {
					clsEpisode oEp = (clsEpisode)moVisEncodedEpisodes.get(i);
					oRet+=oEp.toString3();
				}
			}
		}
		return oRet;
	}
	public String toStringRetrievedEpisodes() {
		String oRet = "";
		for(int i=0; i<moVisEpisodes.size(); i++){
			clsEpisode oEp = (clsEpisode)moVisEpisodes.get(i);
			oRet+=oEp.toString3();
		}
		return oRet;
	}
// ------------------------------------------------------------------------	
}
