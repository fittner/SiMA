/**
 * CHANGELOG
 *
 * 31.08.2011 wendt - File created
 *
 */
package pa._v38.memorymgmt.shorttermmemory;

import java.util.ArrayList;

import pa._v38.interfaces.itfGraphData;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.tools.clsPair;
import pa._v38.tools.datastructures.clsMeshTools;

/**
 * This is a short time memory, which is used in the secondary process. 
 * 
 * @author wendt
 * 31.08.2011, 07:12:10
 * 
 */
public class clsShortTermMemory implements itfGraphData,itfInspectorInternalState{
	/** The variable for the short time memory */
	protected ArrayList<clsPair<Integer, clsWordPresentationMesh>> moShortTimeMemory;
	
	/** A value for how long content is saved in the short time memory */
	protected int mnMaxTimeValue; // = 60;
	/** Number of objects, which can be saved in the short time memory */
	protected int mnMaxMemorySize; // = 7;

	protected clsPair<Integer, clsWordPresentationMesh> moNullMemoryObjectWPM;
	
	/**
	 * Constructor, Init short time Memory with an empty arraylist
	 * 
	 * The values for the memory are set in clsPsychicApparatus
	 * 
	 * @author (wendt)
	 *
	 * @since 31.08.2011 07:15:02
	 *
	 */
	public clsShortTermMemory(int pnMaxTimeValue, int pnMaxMemorySize) {
		moShortTimeMemory = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		mnMaxTimeValue = pnMaxTimeValue;
		mnMaxMemorySize = pnMaxMemorySize;
		
		moNullMemoryObjectWPM = new clsPair<Integer, clsWordPresentationMesh>(-1, clsMeshTools.getNullObjectWPM());
		
	}
	
	/**
	 * @since 16.11.2011 10:43:20
	 * 
	 * @return the moShortTimeMemory
	 */
	public ArrayList<clsPair<Integer, clsWordPresentationMesh>> getMoShortTimeMemory() {
		return moShortTimeMemory;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 31.08.2011 07:29:39
	 *
	 */
	public void updateTimeSteps() {
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oRemoveObjects = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		for (clsPair<Integer, clsWordPresentationMesh> oSingleMemory : moShortTimeMemory) {
			//Update the step count
			oSingleMemory.a++;
			//Remove memories, which are too old and haven´t been transferred to the long time memory
			if (oSingleMemory.a>mnMaxTimeValue) {
				oRemoveObjects.add(oSingleMemory);
			}
		}
		//Remove the overdue objects
		for (clsPair<Integer, clsWordPresentationMesh> oSingleRemoveObject : oRemoveObjects) {
			removeMemory(oSingleRemoveObject);
		}
	}
	
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 31.08.2011 07:31:20
	 *
	 * @param poRemoveMemory
	 */
	protected void removeMemory(clsPair<Integer, clsWordPresentationMesh> poRemoveMemory) {
		moShortTimeMemory.remove(poRemoveMemory);
	}
	
	/**
	 * Remove a memory by knowing the hashcode of the WPM, which shall be removed
	 * 
	 * (wendt)
	 *
	 * @since 17.07.2012 21:16:59
	 *
	 * @param poRemoveMemory
	 */
	protected void removeMemory(clsWordPresentationMesh poRemoveMemory) {
		clsPair<Integer, clsWordPresentationMesh> oRemoveMemoryEntry = this.findMemory(poRemoveMemory);
		
		moShortTimeMemory.remove(oRemoveMemoryEntry);
	}
	
	/**
	 * Add new memory to the end of the list
	 * (wendt)
	 *
	 * @since 31.08.2011 08:48:01
	 *
	 * @param poRemoveMemory
	 */
	private void addMemory(clsPair<Integer, clsWordPresentationMesh> poAddMemory) {
		moShortTimeMemory.add(poAddMemory);
	}
	
	/**
	 * Save new structure to the short time memory if it does not exist already. Forced save is disable here
	 * (wendt)
	 *
	 * @since 31.08.2011 07:19:42
	 *
	 * @param poInput
	 */
	public void saveToShortTimeMemory(clsWordPresentationMesh poInput) {
		saveToShortTimeMemory(poInput, false);
	}
	
	/**
	 * Save a data structure to the short time memory. Options: Forced save, which saves the structure in any case or save only if the 
	 * data structure does not exist
	 * (wendt)
	 *
	 * @since 19.08.2011 14:32:56
	 *
	 * @param poInput
	 */
	public void saveToShortTimeMemory(clsWordPresentationMesh poInput, boolean forceSave) {
		//save only if this moID is not already saved
		
		//Check if this memory already exists
		clsPair<Integer, clsWordPresentationMesh> oFoundMemory  = findMemory(poInput);
		//Memory found and forced save true
		if ((oFoundMemory!=null) && (forceSave==true)) {
			//Here, the memory is replaced by the new memory, which may have some changed values
			//try {
				removeMemory(oFoundMemory);
				oFoundMemory.a = 0;
				oFoundMemory.b = poInput;
				clsWordPresentationMesh oAddPair = (clsWordPresentationMesh) poInput;
				addMemory(new clsPair<Integer, clsWordPresentationMesh>(0, oAddPair));
				
				
			//} catch (CloneNotSupportedException e) {
				// TODO (wendt) - Auto-generated catch block
			//	e.printStackTrace();
			//}
		//If there is free space in the short time memory, add the new memory
		} else if (moShortTimeMemory.size()<mnMaxMemorySize) {
			//try {
				clsWordPresentationMesh oAddPair = (clsWordPresentationMesh) poInput;
				addMemory(new clsPair<Integer, clsWordPresentationMesh>(0, oAddPair));
			//} catch (CloneNotSupportedException e) {
				// TODO (wendt) - Auto-generated catch block
			//	e.printStackTrace();
			//}
		//If there is no space in the short time memory, delete the oldest one
		} else if (moShortTimeMemory.size()>=mnMaxMemorySize) {
			clsPair<Integer, clsWordPresentationMesh> oObsoluteMemory = getMostObsoleteMemory();
			removeMemory(oObsoluteMemory);
			//try {
				clsWordPresentationMesh oAddPair = (clsWordPresentationMesh) poInput;
				addMemory(new clsPair<Integer, clsWordPresentationMesh>(0, oAddPair));
			//} catch (CloneNotSupportedException e) {
			//	// TODO (wendt) - Auto-generated catch block
			//	e.printStackTrace();
			//}
		}
		
	}

	/**
	 * Find a memory in the short time memory and return the whole data structure incl. step
	 * 
	 * Compare method used: java std object comparison
	 * 
	 * (wendt)
	 *
	 * @since 31.08.2011 08:09:45
	 *
	 * @param oToBeFound
	 * @return
	 */
	public clsPair<Integer, clsWordPresentationMesh> findMemory(clsWordPresentationMesh oToBeFound) {
		clsPair<Integer, clsWordPresentationMesh> oRetVal = this.moNullMemoryObjectWPM;
			
		for (clsPair<Integer, clsWordPresentationMesh> oMemory : moShortTimeMemory) {
			if (oToBeFound.equals(oMemory.b)) {
				oRetVal = oMemory;
				break;
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the memory from a certain step;
	 * 
	 * (wendt)
	 *
	 * @since 04.07.2012 11:18:36
	 *
	 * @param pnStep
	 * @return
	 */
	public ArrayList<clsPair<Integer, clsWordPresentationMesh>> findMemory(int pnStep) {
		
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oResult = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		
		for (clsPair<Integer, clsWordPresentationMesh> oMemory : moShortTimeMemory) {
			if (oMemory.a == pnStep) {
				oResult.add(oMemory);
				break;
			}
		}
		return oResult;
	}
	
	private clsWordPresentationMesh findSingleMemoryFromStep(int pnStep) {
		clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
		
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oMemories = findMemory(pnStep);
		if (oMemories.isEmpty()==false) {
			oResult = oMemories.get(0).b;
		}
		
		return oResult;	
	}
	
	public clsWordPresentationMesh findPreviousSingleMemory() {
		return findSingleMemoryFromStep(1);
	}
	
	public clsWordPresentationMesh findCurrentSingleMemory() {
		return findSingleMemoryFromStep(0);
	}
	
	
//	/**
//	 * Find all data in the short time memory, which have a certain datatype
//	 * (wendt)
//	 *
//	 * @since 09.09.2011 21:29:09
//	 *
//	 * @param oDataType
//	 * @return
//	 */
//	public ArrayList<clsPair<Integer, Object>> findMemoriesDataType (eSupportDataType oDataType) {
//		ArrayList<clsPair<Integer, Object>> oRetVal = new ArrayList<clsPair<Integer, Object>>();
//		
//		for (clsPair<Integer, Object> oMemoryPair : moShortTimeMemory) {
//			if ((oDataType == eSupportDataType.CONTAINERPAIR) && (oMemoryPair.b instanceof clsDataStructureContainerPair)) {
//				oRetVal.add(oMemoryPair);
//			} else if ((oDataType == eSupportDataType.PREDICTION) && (oMemoryPair.b instanceof clsPrediction)) {
//				oRetVal.add(oMemoryPair);
//			} else if ((oDataType == eSupportDataType.MOMENT) && (oMemoryPair.b instanceof clsPrediction)) {
//				if (((clsPrediction)oMemoryPair.b).getMoment().getSecondaryComponent()!=null) {
//					oRetVal.add(oMemoryPair);
//				}
//			} else if ((oDataType == eSupportDataType.INTENTION) && (oMemoryPair.b instanceof clsPrediction)) {
//				if (((clsPrediction)oMemoryPair.b).getIntention().getSecondaryComponent()!=null) {
//					oRetVal.add(oMemoryPair);
//				}
//			} else if ((oDataType == eSupportDataType.EXPECTATION) && (oMemoryPair.b instanceof clsPrediction)) {
//				if (((clsPrediction)oMemoryPair.b).getExpectations().isEmpty()==false) {
//					oRetVal.add(oMemoryPair);
//				}
//			}
//		}
//		return oRetVal;
//	}
	
//	/**
//	 * Get all container pairs of a certain classification
//	 * (wendt)
//	 *
//	 * @since 31.08.2011 13:50:56
//	 *
//	 * @param oRefCPair
//	 * @param poAttribute
//	 * @return
//	 */
//	public ArrayList<clsPair<Integer, Object>> findMemoriesClassification(String poClassification) {
//		ArrayList<clsPair<Integer, Object>> oRetVal = new ArrayList<clsPair<Integer, Object>>();
//		//Case known Intention
//		//1. Get Intention
//		
//		//2. Check all moments in the short time memory, if the have this intention associated
//		for (clsPair<Integer, Object> oMemoryPair : moShortTimeMemory) {
//			
//			if (oMemoryPair.b instanceof clsDataStructureContainerPair) {
//				//Check if 1. Attribute is OK
//				clsWordPresentation oClassWP = clsDataStructureTools.getClassification(((clsDataStructureContainerPair)oMemoryPair.b).getSecondaryComponent());
//				if (oClassWP!=null) {
//					if (oClassWP.getMoContent().equals(poClassification)) {
//						oRetVal.add(oMemoryPair);
//					}
//				}
//			} else if (oMemoryPair.b instanceof clsPrediction) {
//				//Give back the whole Object, clsPrediction, if this classification is found
//				//Check Intention
//				
//				if (((clsPrediction)oMemoryPair.b).getIntention().getSecondaryComponent() != null) {
//					//IMPORTANT: It is not allowed to save a Prediction without intention
//					clsWordPresentation oClassWP = clsDataStructureTools.getClassification(((clsPrediction)oMemoryPair.b).getIntention().getSecondaryComponent());
//					if (oClassWP!=null) {
//						if (oClassWP.getMoContent().equals(poClassification)) {
//							oRetVal.add(oMemoryPair);
//							//If found, then break
//							break;
//						}
//					}
//				} 
//				
//				//Check Moment
//				if (((clsPrediction)oMemoryPair.b).getMoment().getSecondaryComponent() != null) {
//					
//					if (((clsPrediction)oMemoryPair.b).getIntention().getSecondaryComponent() != null) {
//						//IMPORTANT: It is not allowed to save a Prediction without intention
//						clsWordPresentation oClassWP = clsDataStructureTools.getClassification(((clsPrediction)oMemoryPair.b).getMoment().getSecondaryComponent());
//						if (oClassWP!=null) {
//							if (oClassWP.getMoContent().equals(poClassification)) {
//								oRetVal.add(oMemoryPair);
//								//If found, then break
//								break;
//							}
//						}
//					}
//				}
//				
//				//Check Expectations
//				if (((clsPrediction)oMemoryPair.b).getExpectations().isEmpty()==false) {
//					boolean oFoundMatch = false;
//					for (clsDataStructureContainerPair oP : ((clsPrediction)oMemoryPair.b).getExpectations()) {
//						clsWordPresentation oClassWP = clsDataStructureTools.getClassification(oP.getSecondaryComponent());
//						if (oClassWP!=null) {
//							if (oClassWP.getMoContent().equals(poClassification)) {
//								oRetVal.add(oMemoryPair);
//								oFoundMatch = true;
//								//If found, then break
//								break; //The first loop
//								
//							}
//						}
//					}
//					
//					if (oFoundMatch == true) {
//						break;
//					}
//				}
//			}
//		}
//		
//		return oRetVal;
//	}
	
	/**
	 * Get the most obsolete memory. There are 2 criteria: 1) The oldest memory. If there are more than one memories
	 * with the same time step, then the memory with the lowest affect is selected
	 * (wendt)
	 *
	 * @since 31.08.2011 07:55:14
	 *
	 * @return
	 */
	private clsPair<Integer, clsWordPresentationMesh> getMostObsoleteMemory() {
		clsPair<Integer, clsWordPresentationMesh> oRetVal = this.moNullMemoryObjectWPM;	//This value is only null, if the memory is empty
		
		//Variables, which are used to get the oldest memory. If there are several memories, which are the oldest ones,
		//then, the memory with the lowest total affect value is selected
		
		if (moShortTimeMemory.isEmpty()==false) {
			oRetVal = moShortTimeMemory.get(0);
		}
		
		for (clsPair<Integer, clsWordPresentationMesh> oMemory : moShortTimeMemory) {
						
			if (oMemory.a>oRetVal.a) {
				oRetVal = oMemory;
			} 
		}
		
		return oRetVal;
	}

	/**
	 * Get the most recent saved memory from the STM
	 * 
	 * (wendt)
	 *
	 * @since 04.07.2012 09:52:51
	 *
	 * @return
	 */
	public clsPair<Integer, clsWordPresentationMesh> getNewestMemory() {
		clsPair<Integer, clsWordPresentationMesh> oRetVal = this.moNullMemoryObjectWPM;	//This value is only null, if the memory is empty
		
		if (moShortTimeMemory.isEmpty()==false) {
			oRetVal = moShortTimeMemory.get(0);
		}
		
		for (clsPair<Integer, clsWordPresentationMesh> oMemory : moShortTimeMemory) {
						
			if (oMemory.a<oRetVal.a) {
				oRetVal = oMemory;
			} 
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all WPMs from the STM, i. e. all mental images
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 16:31:15
	 *
	 * @return
	 */
	public ArrayList<clsWordPresentationMesh> getAllWPMFromSTM() {
		ArrayList<clsWordPresentationMesh> oResult = new ArrayList<clsWordPresentationMesh>();
		
		for (clsPair<Integer, clsWordPresentationMesh> oPair : this.moShortTimeMemory) {
			oResult.add(oPair.b);
		}
		
		return oResult;
		
	}
	
	
	
	@Override
	public String toString(){
		if (moShortTimeMemory.isEmpty()==false) {
			return moShortTimeMemory.toString();// + //"|" + moShortTimeMemory.get(0).b.toString();
		} else {
			return "empty";
		}
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 31, 2012 10:59:07 AM
	 * 
	 * @see pa._v38.interfaces.itfGraphData#getGraphData()
	 */
	@Override
	public ArrayList<Object> getGraphData() {
		ArrayList<Object> oRetVal = new ArrayList<Object>();
		for(clsPair<Integer, clsWordPresentationMesh> oClsPair :moShortTimeMemory){
			oRetVal.add(oClsPair.b);
		}
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 31, 2012 11:26:51 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		return moShortTimeMemory.toString();
	}

}
