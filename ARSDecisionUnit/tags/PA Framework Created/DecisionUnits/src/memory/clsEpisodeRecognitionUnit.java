//package memory;
//
//import memory.tempframework.clsRecognitionProcess;
//import memory.tempframework.clsRecognitionProcessResult;
//import java.util.Vector;
//
///**
// * The class clsEpisodeRecognitionUnit handles the progress of the scenario recognition processes initialized, recognized and aborted and determines, when an episode has to be encoded. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
// */
//public class clsEpisodeRecognitionUnit {
//	/**
//	 * Contains the not yet completed episodes to the scenario recognition processes
//	 */
//	public Vector moEpisodesInProgress; 
//	private clsRecognitionProcessResult moScenarioRecogProcesses; // temporally stores the delivered scenario recog processes
//	private int mnCurrEpisodeId = 0;
//	
//	public clsEpisodeRecognitionUnit(){
//		moEpisodesInProgress = new Vector();
//	}
//	
//	/**
//	 * Sets the progress of the current scenario recognition processes. Has to be called each process cycle
//	 * @param poScenRecogProc The structure with the scenario recognition processes initialized, recognized and aborted
//	 */
//	public void setScenarioRecogProcesses(clsRecognitionProcessResult poScenRecogProc){
//		moScenarioRecogProcesses = poScenRecogProc;
//	}
//	
//	/**
//	 * Triggers the encoding of an event, if a new scenario recognition process has been initialized or recognized. This is
//	 * because a TI for a scenario transition may be matching before the respective scenario is in the state before this
//	 * transition. An event would not be recorded (no change in the feature TI-matches) when a scenario is recognized and thus
//	 * no episode would be encoded.
//	 * @return true, if a scenario has been initialized or completely recognized; otherwise false
//	 */
//	public boolean trigger() {
//		if(moScenarioRecogProcesses.moInitialized.size() > 0) {
//			return true;
//		}		
//		for(int i=0; i < moScenarioRecogProcesses.moRecognized.size(); i++) {
//			// check whether the recognized process really exists in the Vector EpisodesInProgress
//			clsRecognitionProcess oRecogProc = (clsRecognitionProcess)moScenarioRecogProcesses.moRecognized.get(i);
//			for(int j=0; j<moEpisodesInProgress.size(); j++){
//				// search for the completed Recognitionprocesses
//				clsEpisode oEpisode = (clsEpisode)moEpisodesInProgress.get(j);
//				if( oRecogProc.equals(oEpisode.moRecognitionProcess) ) {
//					return true;
//				}
//			}
//		}		
//		return false;
//	}
//	
//	/**
//	 * Checks whether a new recognition process has been initialized and appropriate instantiated a new episode
//	 * @param poEvent The currently encoded event (will be attached as start event if an episode is instantiated)
//	 */
//	public void checkForInitialized(clsEvent poEvent) {
//		for(int i=0; i < moScenarioRecogProcesses.moInitialized.size(); i++) {
//			clsRecognitionProcess oRecogProc = (clsRecognitionProcess)moScenarioRecogProcesses.moInitialized.get(i);
//			moEpisodesInProgress.add( new clsEpisode(oRecogProc, poEvent, mnCurrEpisodeId++) );
////			 while there is a bug with the RecognitionProcesses:
//			//-----------------------------------------------------------------------			
////			boolean nExists = false;
////			for(int j=0; j<moEpisodesInProgress.size(); j++){
////				clsEpisode oEpisode = (clsEpisode)moEpisodesInProgress.get(j);
////				if(oRecogProc.moRelativeScenario.mnId == oEpisode.getScenarioId()) {
////					nExists = true;
////					break;
////				}
////			}
////			if(!nExists) {
////				moEpisodesInProgress.add( new clsEpisode(oRecogProc, pnMemKey) );
////			}
//			//-----------------------------------------------------------------------			
//		}		
//	}
//	
//	/**
//	 * Checks, whether one or more scenario recognition process have been recognized
//	 * @param poEvent The currently encoded event -- will be attached to the episode as end event, if an episode has
//	 * been completed
//	 * @return An unsorted list (java.util.Vector) of the episodes, that have currently been completed
//	 */
//	public Vector checkForRecognized(clsEvent poEvent){
//		// return the recognized Episodes
//		Vector oRecognizedEpisodes = new Vector();
//		for(int i=0; i < moScenarioRecogProcesses.moRecognized.size(); i++) {
//			clsRecognitionProcess oRecogProc = (clsRecognitionProcess)moScenarioRecogProcesses.moRecognized.get(i);
//			int j=0;
//			int nSize = moEpisodesInProgress.size();
//			for(j=0; j<nSize; j++){
//				// search for the completed Recognitionprocesses
//				clsEpisode oEpisode = (clsEpisode)moEpisodesInProgress.get(j);
//				if( oRecogProc.equals(oEpisode.moRecognitionProcess) ) {
//					oEpisode.setEndEvent( poEvent);
//					oRecognizedEpisodes.add(oEpisode);
//					moEpisodesInProgress.remove(j); // remove this recognition process from the list
//					break; // the corresponding RecognitionProcess is found
//				}
//			}
//			if(j==nSize) {
//				// recognition process not found in episodesInProgress -> encode episode as single event
//				clsEpisode oEpisode = new clsEpisode(oRecogProc, poEvent, mnCurrEpisodeId++);
//				oEpisode.setEndEvent( poEvent);
//				oRecognizedEpisodes.add(oEpisode);
//			}
//		}
//		return oRecognizedEpisodes;
//	}
//	
//	/**
//	 * Checks, whether scenario recognition processes have been aborted an appropriatley removes them from the list
//	 * moEpisodesInProgress
//	 */
//	public void checkForAborted() {
//		for(int i=0; i < moScenarioRecogProcesses.moAborted.size(); i++) {
//			clsRecognitionProcess oRecogProc = (clsRecognitionProcess)moScenarioRecogProcesses.moAborted.get(i);
//			for(int j=0; j<moEpisodesInProgress.size(); j++){
//				// search for the aborted Recognition processes
//				clsEpisode oEpisode = (clsEpisode)moEpisodesInProgress.get(j);
//				if( oRecogProc.equals(oEpisode.moRecognitionProcess) ) {
//					moEpisodesInProgress.remove(j);
//					break; // the corresponding RecognitionProcess is found and removed
//				}
//			}
//		}		
//	}
//	
//	/**
//	 * Determines, which scenarios are currently in progress
//	 * @return A java.util.Vector with the sceanrios currently in progress
//	 */
//	public Vector getScenariosInProgress() {
//		Vector oScenarios=null;
//		for(int i=0; i<moEpisodesInProgress.size(); i++){
//			oScenarios=new Vector();
//			clsEpisode oEp = (clsEpisode)moEpisodesInProgress.get(i);
//			Integer oScenId = new Integer(oEp.moScenario.mnId);
//			if (oScenarios.contains(oScenId) ==false) {
//				oScenarios.add(oScenId);
//			}
//			
////			boolean nContains=false;
////			for(int j=0; j<oScenarios.size(); j++){
////				Integer oId = (Integer)oScenarios.get(j);
////				if(oId.intValue() == nScenId) nContains=true;
////			}
////			if(!nContains) oScenarios.add(new Integer());
//		}
//		return oScenarios;
//	}
//}
