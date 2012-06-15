/**
 * CHANGELOG
 *
 * 31.08.2011 wendt - File created
 *
 */
package pa._v38.storage;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.tools.clsPair;

/**
 * This is a short time memory, which is used in the secondary process. 
 * 
 * @author wendt
 * 31.08.2011, 07:12:10
 * 
 */
public class clsGoalMemory {
	/** The variable for the short time memory */
	private ArrayList<clsPair<Integer, clsWordPresentationMesh>> moShortTimeMemory;
	
	/** A value for how long content is saved in the short time memory */
	private int mnMaxTimeValue; // = 60;
	/** Number of objects, which can be saved in the short time memory */
	private int mnMaxMemorySize; // = 7;
	
	
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
	public clsGoalMemory(int pnMaxTimeValue, int pnMaxMemorySize) {
		moShortTimeMemory = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		mnMaxTimeValue = pnMaxTimeValue;
		mnMaxMemorySize = pnMaxMemorySize;
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
	private void removeMemory(clsPair<Integer, clsWordPresentationMesh> poRemoveMemory) {
		moShortTimeMemory.remove(poRemoveMemory);
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
			try {
				removeMemory(oFoundMemory);
				oFoundMemory.a = 0;
				oFoundMemory.b = poInput;
				clsWordPresentationMesh oAddPair = (clsWordPresentationMesh) poInput.clone();
				addMemory(new clsPair<Integer, clsWordPresentationMesh>(0, oAddPair));
				
				
			} catch (CloneNotSupportedException e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		//If there is free space in the short time memory, add the new memory
		} else if (moShortTimeMemory.size()<mnMaxMemorySize) {
			try {
				clsWordPresentationMesh oAddPair = (clsWordPresentationMesh) poInput.clone();
				addMemory(new clsPair<Integer, clsWordPresentationMesh>(0, oAddPair));
			} catch (CloneNotSupportedException e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		//If there is no space in the short time memory, delete the oldest one
		} else if (moShortTimeMemory.size()>=mnMaxMemorySize) {
			clsPair<Integer, clsWordPresentationMesh> oObsoluteMemory = getMostObsoleteMemory();
			removeMemory(oObsoluteMemory);
			try {
				clsWordPresentationMesh oAddPair = (clsWordPresentationMesh) poInput.clone();
				addMemory(new clsPair<Integer, clsWordPresentationMesh>(0, oAddPair));
			} catch (CloneNotSupportedException e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * Find a memory in the short time memory and return the whole data structure incl. step
	 * (wendt)
	 *
	 * @since 31.08.2011 08:09:45
	 *
	 * @param oToBeFound
	 * @return
	 */
	public clsPair<Integer, clsWordPresentationMesh> findMemory(clsWordPresentationMesh oToBeFound) {
		clsPair<Integer, clsWordPresentationMesh> oRetVal = null;
		
		//if (oToBeFound instanceof clsSecondaryDataStructure) {
		clsWordPresentationMesh oCompareStructure = oToBeFound;
			
		for (clsPair<Integer, clsWordPresentationMesh> oMemory : moShortTimeMemory) {
			if (oCompareStructure.getMoDS_ID() == oMemory.b.getMoDS_ID()) {
				oRetVal = oMemory;
				break;
			}

				
				
				
//				if (oMemory.b instanceof clsDataStructureContainerPair) {
//					if ((oCompareStructure!=null) && (((clsDataStructureContainerPair)oMemory.b).getSecondaryComponent()!=null)) {
//						if (oCompareStructure.getMoDS_ID() == ((clsDataStructureContainerPair)oMemory.b).getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
//							oRetVal = oMemory;
//							break;
//						}
//					}
//				} else if (oMemory.b instanceof clsPrediction) {
//					//Go through the prediction to find a pair
//					clsPrediction oPrediction = (clsPrediction) oMemory.b;
//					if (oCompareStructure.getMoDS_ID() == oPrediction.getIntention().getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
//						oRetVal = new clsPair<Integer, Object>(oMemory.a, oPrediction.getIntention());
//						break;
//					} else if (oCompareStructure.getMoDS_ID() == oPrediction.getMoment().getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
//						oRetVal = new clsPair<Integer, Object>(oMemory.a, oPrediction.getMoment());
//						break;
//					} else {
//						for (clsDataStructureContainerPair oExpectation : oPrediction.getExpectations()) {
//							if (oCompareStructure.getMoDS_ID() == oExpectation.getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
//								oRetVal = new clsPair<Integer, Object>(oMemory.a, oExpectation);
//								break;
//							}
//						}
//					}
//				}
			//}
//		} else if (oToBeFound instanceof clsDataStructureContainerPair) {
//			//Check CPair
//			clsSecondaryDataStructureContainer oSCompareContainer = ((clsDataStructureContainerPair)oToBeFound).getSecondaryComponent();
//			clsPrimaryDataStructureContainer oPCompareContainer = ((clsDataStructureContainerPair)oToBeFound).getPrimaryComponent();
//			
//			for (clsPair<Integer, Object> oMemory : moShortTimeMemory) {
//				if (oMemory.b instanceof clsDataStructureContainerPair) {
//					if ((oSCompareContainer!=null) && (((clsDataStructureContainerPair)oMemory.b).getSecondaryComponent()!=null)) {
//						//If the secondary structure does not have an ID, which means that the ID is -1, it shall be looked for the primary structure
//						if ((oSCompareContainer.getMoDataStructure().getMoDS_ID() == -1) && (oPCompareContainer.getMoDataStructure().getMoDS_ID() == ((clsDataStructureContainerPair)oMemory.b).getPrimaryComponent().getMoDataStructure().getMoDS_ID())) {
//							oRetVal = oMemory;
//							break;
//						} else if ((oSCompareContainer.getMoDataStructure().getMoDS_ID() != -1) && oSCompareContainer.getMoDataStructure().getMoDS_ID() == ((clsDataStructureContainerPair)oMemory.b).getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
//							oRetVal = oMemory;
//							break;
//						}
//					}
//				} else if (oMemory.b instanceof clsPrediction) {
//					//Go through the prediction to find a pair
//					clsPrediction oPrediction = (clsPrediction) oMemory.b;
//					if (oSCompareContainer.getMoDataStructure().getMoDS_ID() == oPrediction.getIntention().getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
//						oRetVal = new clsPair<Integer, Object>(oMemory.a, oPrediction.getIntention());
//						break;
//					} else if (oSCompareContainer.getMoDataStructure().getMoDS_ID() == oPrediction.getMoment().getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
//						oRetVal = new clsPair<Integer, Object>(oMemory.a, oPrediction.getMoment());
//						break;
//					} else {
//						for (clsDataStructureContainerPair oExpectation : oPrediction.getExpectations()) {
//							if (oSCompareContainer.getMoDataStructure().getMoDS_ID() == oExpectation.getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
//								oRetVal = new clsPair<Integer, Object>(oMemory.a, oExpectation);
//								break;
//							}
//						}
//					}
//				}
//			}
//			
//		} else if (oToBeFound instanceof clsPrediction) {
//			//Check CPair
//			clsSecondaryDataStructureContainer oCompareContainer = null;
//			oCompareContainer = ((clsPrediction)oToBeFound).getIntention().getSecondaryComponent();
//			
//			for (clsPair<Integer, Object> oMemory : moShortTimeMemory) {
//				if (oMemory.b instanceof clsPrediction) {
//					if ((oCompareContainer!=null) && (((clsPrediction)oMemory.b).getIntention().getSecondaryComponent()!=null)) {
//						if (oCompareContainer.getMoDataStructure().getMoDS_ID() == ((clsPrediction)oMemory.b).getIntention().getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
//							oRetVal = oMemory;
//						}
//					}
//				}
//			}
		}		
		return oRetVal;
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
		clsPair<Integer, clsWordPresentationMesh> oRetVal = null;	//This value is only null, if the memory is empty
		
		//Variables, which are used to get the oldest memory. If there are several memories, which are the oldest ones,
		//then, the memory with the lowest total affect value is selected
		
		int nCurrentStep = 0;
		int nMaxStep = 0;
		
		//int nMaxAffect = 0;
		//int nCurrentAffect = 0;
		
		for (clsPair<Integer, clsWordPresentationMesh> oMemory : moShortTimeMemory) {
			nCurrentStep = oMemory.a;
			
			if (nCurrentStep>nMaxStep) {
				oRetVal = oMemory;
			} 
			
			//TODO AW: Implement the case, where the steps are equal. There the min affect is selected
			//else if (nCurrentStep==nMaxStep) {
			//	if (oMemory.b.getSecondaryComponent()!=null) {
					
					
					//The secondary compoent shall always be available
			//		nCurrentAffect = clsAffectTools.calculateMaxAffectSecondary(oMemory.b.getSecondaryComponent());
			//		if (nCurrentStep>nMaxStep) {
			//			oRetVal = oMemory;
			//		}
			//	}
				
		}
		
		return oRetVal;
	}
	
	@Override
	public String toString(){
		if (moShortTimeMemory.isEmpty()==false) {
			return moShortTimeMemory.toString();// + //"|" + moShortTimeMemory.get(0).b.toString();
		} else {
			return "empty";
		}
	}

}
