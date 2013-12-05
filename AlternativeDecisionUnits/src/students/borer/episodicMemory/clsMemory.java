// File clsepisodicMemory.java
// November 11, 2006
//

// Belongs to package
package students.borer.episodicMemory;

import students.borer.LocalizationOrientation.clsStep;
import students.borer.episodicMemory.tempframework.clsActionContainer;
import students.borer.episodicMemory.tempframework.clsContainerCompareResults;
import students.borer.episodicMemory.tempframework.clsContainerDrive;
import students.borer.episodicMemory.tempframework.clsContainerEmotion;
import students.borer.episodicMemory.tempframework.clsImagePerception;
import students.borer.episodicMemory.tempframework.clsRecognitionProcessResult;

// all System.out.print() have to be replaced by Engine.log.print() when porting to AnyLogic
/**
 * The main class of the package pkgepisodicMemory. It provides the interfaces (Store API, Retrieve API) to use the fundamental memory functionalities $Revision: 682 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-07-11 16:48:22 +0200 (Mi, 11 Jul 2007) $: Date of last commit
 */
public class clsMemory {
	public clsContainerMemory moMemory;
	public clsAlarmSystem moAlarmSystem;
	
//	 only for testing
	private static boolean mnFirstPsycho = false;
	private String moPsychoId;
	

//-------------------------------------------------------------	  
	@Override
  public String toString() 
//-------------------------------------------------------------	  
  {
    String oResult = "";

    oResult += "moPsychoId: "+moPsychoId+"; mnFirstPsycho: "+mnFirstPsycho+"; moAlarmSystem: "+moAlarmSystem+"; moMemory: "+moMemory;

    return oResult;
  }

/**
 * Standard constructor:
 * Generates the memory container and the alarm system
 */
  public clsMemory() {
	  moMemory = new clsContainerMemory();
	  moAlarmSystem = new clsAlarmSystem( moMemory );
	  
// FOR TESTING (logging)--------------------------------------
	  if(!mnFirstPsycho) {
		  mnFirstPsycho=true;
		  moPsychoId = "\nPSYCHO 1\n----------------------------------------";
	  }
	  else moPsychoId = "\nPSYCHO 2\n--------------------------------------";
//	  moAlarmSystem.moPsychoId = moPsychoId;
//-------------------------------------------------------------	  
  }
   
/**
 * Represents the StoreAPI. The data about the currently perceived situation (drives, emotions,
 * TI-matches , actions and the scenario recognition processes which are in progress) is
 * indicated in the parameter. If a spontaneous retrieval occurs, the container with the
 * retrieval results (clsContainerRetrievalResults) should be returned.
 * 
 * @param poPerceivedImage Contains the drives and emotions (this parameter should subsequently be
 * replaced by two individual container "clsContainerDrives" and "clsContainerEmotions")
 * @param poImageCompareResults Contains the TI-matches
 * @param poActionList Contains the actions currently executed
 * @param poScenarioRecogProcesses Contains the initialized, recognized and aborted scenario recognition processes
 */
  public void addData(
                      clsImagePerception poPerceivedImage,
                      clsContainerCompareResults poImageCompareResults,
                      clsRecognitionProcessResult poScenarioRecogProcesses,
                      clsActionContainer poActionList,
                      clsStep Step
                      ) 
  {
	  clsContainerDrive oDrives = (clsContainerDrive)poPerceivedImage.moDriveList.clone();
	  clsContainerEmotion oEmotions = (clsContainerEmotion)poPerceivedImage.moEmotionList.clone();
	  clsContainerCompareResults oImageCompareResults = (clsContainerCompareResults)poImageCompareResults.clone();
	  clsActionContainer oActions = (clsActionContainer)poActionList.clone();
	
	  clsContainerRetrievalResults oRetrievalResults = null;
	  oRetrievalResults = moAlarmSystem.perform(oDrives, oEmotions, oImageCompareResults, oActions,poScenarioRecogProcesses,Step);
	  
// FOR TESTING (logging)-----------------------------------------------------------------------------
	  if(oRetrievalResults != null) {
//		System.out.println(oRetrievalResults.moRetrievalCue.toString());
		clsRecallAPI oRecallAPI = oRetrievalResults.retrieveFirstResult();
		if(oRecallAPI != null){
			@SuppressWarnings("unused")
			clsRetrievalResult oResult = oRetrievalResults.getFirstResult();
			oRecallAPI.getSituation(); // to simulate boost
		}

	  }
//-----------------------------------------------------------------------------	  
	  // return oRetrievalResults;  
  }
  
/**
 * Represents the RetrieveAPI.
 * @param poRetrievalCue Retrieval cue with for the deliberate retrieval
 * @return The container with the retrieval results
 */  
  public clsContainerRetrievalResults retrieve ( clsRetrievalCue poRetrievalCue) { 
	  return moMemory.retrieve(poRetrievalCue);
  }

  

  
/**
 * 
 * @return The size of the memory container (the number of the stored events)
 */
  public int getMemSize() {
	  return moMemory.size();
  }
  
  /**
   * The movement information between the current and the latest Area encoded within the eventstream 
   * is updated withing the latest event
   * @param the current step taken
   */

};