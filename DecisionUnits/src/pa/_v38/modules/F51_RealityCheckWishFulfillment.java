/**
 * E24_RealityCheck.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:49:09
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.SortedMap;

import config.clsProperties;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_7_receive;
import pa._v38.interfaces.modules.I6_7_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.enums.eSupportDataType;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.storage.clsOLDShortTimeMemory;
import pa._v38.tools.clsDataStructureTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsSecondarySpatialTools;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

/**
 * The external world is evaluated regarding the available possibilities for drive satisfaction and which requirements arise. This is done by utilization of semantic knowledge provided by {E25} and incoming word and things presentations from {E23}. The result influences the generation of motives in {E26}. 
 * 
 * @author wendt
 * 07.05.2012, 14:49:09
 * 
 */
public class F51_RealityCheckWishFulfillment extends clsModuleBaseKB implements I6_6_receive, I6_7_send, I6_3_receive {
	public static final String P_MODULENUMBER = "51";
	
	/** Perception IN */
	private clsWordPresentationMesh moPerceptionalMesh_IN;
	/** Associated Memories IN; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;
	/** Perception OUT */
	//private clsWordPresentationMesh moPerceptionalMesh_OUT;
	/** Associated Memories OUT; @since 07.02.2012 15:54:51 */
	//private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
	
	private ArrayList<clsWordPresentationMesh> moReachableGoalList_IN;
	
	private ArrayList<clsWordPresentationMesh> moReachableGoalList_OUT;
	/** List of drive goals OUT; @since 07.05.2012 19:10:20 */
	//private ArrayList<clsWordPresentationMesh> moGoalList_OUT;
	/** List of drive goals IN; @since 07.02.2012 19:10:20 */
	private ArrayList<clsWordPresentationMesh> moDriveGoalList_IN;
	
	
	
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:45 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_IN;  
//	/** Container of activated associated memories */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemoriesSecondary_IN;
//	/** Associated memories out */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemoriesSecondary_OUT;
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:49 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_OUT; 
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:50 */
//	//private ArrayList<clsSecondaryDataStructureContainer> moDriveList;  //removed by HZ - not required now
//	/** A construction of an Intention, an arraylist with expectations and the current situation */
//	private ArrayList<clsPrediction> moExtractedPrediction_OUT;
	
	/** A threshold for images, which are only set moment if the match factor is higher or equal this value */
	private double mrMomentActivationThreshold = 1.0;
	/** DOCUMENT (wendt) - insert description; @since 10.09.2011 16:40:06 */
	private double mrMomentMinRelevanceThreshold = 0.2;
	
	/** If a third of an act is gone though, it is considered as confirmed */
	private int mnConfirmationParts = 3;
	
	/** This factor detemines how much the drive can be reduced in an intention. If the value is 0.5, this is the minimum value of the drive, which can be reduced */
	private double mrReduceFactorForDrives = 1.0;
	
	private int mnAffectThresold = -3;
	
	
	/** Short time memory */
	//private clsOLDShortTimeMemory moShortTimeMemory;
	
	/** (wendt) Goal memory; @since 24.05.2012 15:25:09 */
	private clsShortTermMemory moShortTimeMemory;
	
	/** This is the storage for the localization; @since 15.11.2011 14:41:03 */
	private clsShortTermMemory moTempLocalizationStorage;
	
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:50:46
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poGoalMemory 
	 * @throws Exception
	 */
	public F51_RealityCheckWishFulfillment(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler, clsShortTermMemory poShortTimeMemory, clsShortTermMemory poTempLocalizationStorage) throws Exception {
	//public F51_RealityCheckWishFulfillment(String poPrefix, clsProperties poProp,
	//		HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		//super(poPrefix, poProp, poModuleList, poInterfaceData);
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		applyProperties(poPrefix, poProp);	
		
		//Get short time memory
		moShortTimeMemory = poShortTimeMemory;
		//moGoalMemory = poGoalMemory;
		moTempLocalizationStorage = poTempLocalizationStorage;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
//		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
//		text += toText.listToTEXT("moAssociatedMemoriesSecondary_IN", moAssociatedMemoriesSecondary_IN);
//		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_OUT);
//		text += toText.listToTEXT("moExtractedPrediction_OUT", moExtractedPrediction_OUT);
		text += toText.listToTEXT("moDriveGoalList_IN", moDriveGoalList_IN);
		
		return text;
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:49:45
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_6(clsWordPresentationMesh poPerception, 
			ArrayList<clsWordPresentationMesh> poReachableGoalList, 
			ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
		try {
			moPerceptionalMesh_IN = (clsWordPresentationMesh)poPerception.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		moReachableGoalList_IN = (ArrayList<clsWordPresentationMesh>) deepCopy(poReachableGoalList);
		moAssociatedMemories_IN = (ArrayList<clsWordPresentationMesh>)deepCopy(poAssociatedMemoriesSecondary);
	}
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_3(ArrayList<clsWordPresentationMesh> poDriveList) {
		moDriveGoalList_IN = (ArrayList<clsWordPresentationMesh>)this.deepCopy(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:25
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		//=== Create the mental image ===
		//Test AW: Relational Meshes
		clsSecondarySpatialTools.createRelationalObjectMesh(moPerceptionalMesh_IN);
		
		//Add perception to localization
		moTempLocalizationStorage.saveToShortTimeMemory(moPerceptionalMesh_IN);
		
		if (moAssociatedMemories_IN.isEmpty()==false) {
			clsSecondarySpatialTools.createRelationalObjectMesh(moAssociatedMemories_IN.get(0));
		}
		
		//Process all goals individually without sorting them. Sorting is done in the decision making
		
		processGoals(moReachableGoalList_IN);
		
		//=== Sort and evaluate them === //
		
		
		moReachableGoalList_OUT = moReachableGoalList_IN;
		
		//=== Process acts ===//
		//The act is accessed through the goal.
		//Take the first act in the list and process it
		//FIXME AW: In the first step, perform only simple processing
		
		
		
		
		
		
		
		//moPerceptionalMesh_OUT = moPerceptionalMesh_IN;
		//moAssociatedMemories_OUT = moAssociatedMemories_IN;
		//moExtractedPrediction_OUT = new ArrayList<clsPrediction>();
		//moGoalList_OUT = moGoalList_IN;
		
		
		/*
		//Update short time memory from last step
		if (moShortTimeMemory==null) {
			try {
				throw new Exception("moShortTimeMemory==null");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//System.out.print("ShortTimeMemory: " + moShortTimeMemory.toString());
		moShortTimeMemory.updateTimeSteps();
		//FIXME AW: Should anything be done with the perception here?
		
		try {
			moEnvironmentalPerception_OUT = (clsDataStructureContainerPair)moEnvironmentalPerception_IN.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		//Perception, add objects to the localization storage
		updateLocalization(moEnvironmentalPerception_OUT, moTempLocalizationStorage);
		
		//Check if some expectations are confirmed
		confirmExpectations(moAssociatedMemoriesSecondary_IN, mrMomentActivationThreshold, moShortTimeMemory);
		
		//Get the new predictions
		moExtractedPrediction_OUT = extractPredictions(moAssociatedMemoriesSecondary_IN);
		
		//Pass the associated memories forward
		moAssociatedMemoriesSecondary_OUT = (ArrayList<clsDataStructureContainerPair>)deepCopy((ArrayList<clsDataStructureContainerPair>)moAssociatedMemoriesSecondary_IN);
		
		//printImageText(moExtractedPrediction_OUT);*/
	}
	
	private void processGoals(ArrayList<clsWordPresentationMesh> poGoalList) {
		
		for (clsWordPresentationMesh oGoal : poGoalList) {
			//1. For each goal, check, which type it is
			eContentType oType = clsGoalTools.getSupportDataStructureType(oGoal);
						
			//If no supportive datastructure, create one from the goal object
			if (oType==null) {
				try {
					clsWordPresentationMesh oGoalObject = clsGoalTools.getGoalObject(oGoal);
					ArrayList<clsSecondaryDataStructure> oInputList = new ArrayList<clsSecondaryDataStructure>();
					oInputList.add(oGoalObject);
					
					clsWordPresentationMesh oNewSupportiveDataStructure = clsMeshTools.createWPMImage(oInputList, eContentType.SUPPORTIVEDATASTRUCTURE, "GoalObject");
					
					clsGoalTools.setSupportiveDataStructure(oGoal, oNewSupportiveDataStructure);
					
				} catch (NullPointerException e) {
					System.out.println("Error in F51, processGoals: The goal does not have a valid goal object");
					e.printStackTrace();
				}
				
				
			} 
			
			
		}
		
	}
	
//	private void updateLocalization(clsDataStructureContainerPair poPerception, clsOLDShortTimeMemory poMemory) {
//		//1. Update Memory with the time updates
//		poMemory.updateTimeSteps();
//		
//		//2. Extract all Objects from Perception as a containerPairlist
//		ArrayList<clsDataStructureContainerPair> oObjectList = clsDataStructureTools.getObjectContainerPairsFromImage(poPerception);
//		
//		//2. Go through each object and check if it can be found in the memory. The comparison must be with the primary part as the secondary parts do not have any IDs
//		for (clsDataStructureContainerPair oCPair : oObjectList) {
//			//3. Save the objects with forced save (sets the counter to delete on 0 
//			poMemory.saveToShortTimeMemory(oCPair, true);
//		}
//	}
	
	private void processMemories(ArrayList<clsWordPresentationMesh> poMemoryList) {
		//Intention already exists
		
		for (clsWordPresentationMesh oAct : poMemoryList) {
			//Get the moment
			
			
			
			//Get the expectation			
			
		}
	}
	
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 09.09.2011 22:41:36
	 *
	 * @param poInput
	 * @param prMomentActivationThreshold
	 * @param poShortTimeMemory
	 */
	private void confirmExpectations(ArrayList<clsDataStructureContainerPair> poInput, double prMomentActivationThreshold, clsOLDShortTimeMemory poShortTimeMemory) {
		//Get all expectations from the Short time memory
		ArrayList<clsPair<Integer, Object>> oExpectationList = poShortTimeMemory.findMemoriesDataType(eSupportDataType.EXPECTATION);
		//For each found expectation
		for (clsPair<Integer, Object> oMemoryExpectation : oExpectationList) {
			clsPrediction oPrediction = (clsPrediction)oMemoryExpectation.b;
			//Intention shall only be updated once
			boolean bIntentionProgressUpdated = false;
			//Go through all associated memories
			for (clsDataStructureContainerPair oActivatedDataContainerPair : poInput) {
				//If a secondary data structure container
				//if (oActivatedDataContainer instanceof clsSecondaryDataStructureContainer) {
				clsSecondaryDataStructureContainer oSContainer = oActivatedDataContainerPair.getSecondaryComponent();
				//If they are equal
				//FIXME: What if there are more expectations to one moment???? This should be considered
				for (clsDataStructureContainerPair oExpectation : oPrediction.getExpectations()) {
					boolean bCheckProcessedExpectation = clsActTools.getExpectationAlreadyConfirmed(oExpectation.getSecondaryComponent());
					if (bCheckProcessedExpectation==false) {
						if (oExpectation.getSecondaryComponent().getMoDataStructure().getMoDS_ID() == oSContainer.getMoDataStructure().getMoDS_ID()) {
							//Get the primary container
							//clsPrimaryDataStructureContainer oC = clsDataStructureTools.extractPrimaryContainer(oSContainer, poInput);
							clsPrimaryDataStructureContainer oC = oActivatedDataContainerPair.getPrimaryComponent();
							if (oC != null) {
								//Get match to PI
								double rMatch = clsDataStructureTools.getMatchValueToPI(oC);
								//If it is more than the activation threshold
								if (rMatch >= prMomentActivationThreshold) {
									//Update the progress bar
									if (bIntentionProgressUpdated == false) {
										//Update the temporal and the confirmation progress of that perception act
										updateTotalProgress(oPrediction.getIntention().getSecondaryComponent());
										bIntentionProgressUpdated = true;
									}
									//Add WP that the expectation has been already used. An expectation must only be confirmed once
									clsActTools.setExpectationAlreadyConfirmed(oExpectation.getSecondaryComponent(), true);
								}
							}
							break;
					
						}
					}
					//}
				}
			}
		}
		//Check if the expectation is fullfilled -> Match >= Threshold
		
		//Update the intention
	}
	
	
//	/**
//	 * For each Perception-Act, extract 1. the current situation, the expectation and the intention
//	 * (wendt)
//	 *
//	 * @since 22.07.2011 15:24:21
//	 *
//	 * @param oInput
//	 * @return
//	 */
//	private ArrayList<clsPrediction> extractPredictions(ArrayList<clsDataStructureContainerPair> poInput) {
//		ArrayList<clsPrediction> oRetVal = new ArrayList<clsPrediction>();
//		//ArrayList<clsPrediction> oPredictionList = new ArrayList<clsPrediction>();
//		
//		//Varaible to control how to save things in the short time memory
//		
//		//1. Find the intention in each Perception-act
//		//Do it with clsAssociationSecondary and ISA
//		//FIXME AW: Only the first "parent" is taken. It should be expanded to multiple parents
//		ArrayList<clsTriple<Integer, Integer, clsPrediction>> oIntentionList = getIntention(poInput, moShortTimeMemory);
//		//Now, all acts are assigned and all intentions are known
//		
//		//2. Find the current situation in each Perception-act
//		//Do it hasAssociation, which is instanceof clsAssociationPrimary with Perceived Image and 
//		//with the highest association weight
//		//2.a Get all current situations, which are correspsonding to the intentions
//		/* 2.b A current situation has the following:
//		 * - For an intention, the highest match value of the associationPrimary
//		 */
//		
//		//ArrayList<clsPair<Integer, clsPrediction>> oExtentedPredictionList = addSaveModeToPrediction(oPredictionList);
//		
//		ArrayList<clsTriple<Integer, Integer, clsPrediction>> oIntentionMomentList = getMoment(oIntentionList, poInput, mrMomentActivationThreshold);
//		//2.c Check extrapolation of values
//		//XXXX
//		
//		//4. Find the expectation in each Perception-act
//		//Do it with clsAssociationSecondary and ISA Intention and HASNEXT as leaf element of the association with the
//		//current situation
//		ArrayList<clsTriple<Integer, Integer, clsPrediction>> oIntentionMomentExpectationList = getExpectations(oIntentionMomentList, poInput, moShortTimeMemory);
//		
//		
//		//3. Remove all predictions, where there is no current moment for an intention
//		cleanPredictions(oIntentionMomentExpectationList);
//		
//		//Set all Progress settings to the act
//		//If the act is new, then new progress settings shall be added, else, they shall be updated
//		addTotalProgress(oIntentionMomentExpectationList, poInput, mnConfirmationParts);
//		addAffectReduceValues(oIntentionMomentExpectationList, mrReduceFactorForDrives);
//		
//		//5. Save prediction in short time memory
//		savePredictionToSMemory(oIntentionMomentExpectationList);
//		
//		oRetVal = removeSaveModeFromPrediction(oIntentionMomentExpectationList); 
//		
//		return oRetVal;
//	}
//	
//	/**
//	 * Save prediction to short time memory depending on save mode:
//	 * 0: do nothing
//	 * 1: save if not existing
//	 * 2: replace existing content
//	 * (wendt)
//	 *
//	 * @since 09.09.2011 11:39:38
//	 *
//	 * @param pnSavePredictionMode
//	 */
//	private void savePredictionToSMemory(ArrayList<clsTriple<Integer, Integer, clsPrediction>> poPredictionList) {
//		for (clsTriple<Integer, Integer, clsPrediction> oP : poPredictionList) {
//			if (oP.b==1) {
//				moShortTimeMemory.saveToShortTimeMemory(oP.c, false);
//			} else if (oP.b==2) {
//				moShortTimeMemory.saveToShortTimeMemory(oP.c, true);
//			} else if (oP.b!=0) {
//				try {
//					throw new Exception("Error in F51, savePredictionToMemory: Only the pnSavePredictionMode values 0, 1 and 2 are allowed");
//				} catch (Exception e) {
//					// TODO (wendt) - Auto-generated catch block
//					e.printStackTrace();
//					System.exit(0);
//				}
//			}
//		}
//	}
//	
//	
//	/**
//	 * Remove an integer from a prediction pair
//	 * (wendt)
//	 *
//	 * @since 09.09.2011 12:09:11
//	 *
//	 * @param poInput
//	 * @return
//	 */
//	private ArrayList<clsPrediction> removeSaveModeFromPrediction(ArrayList<clsTriple<Integer, Integer, clsPrediction>> poInput) {
//		ArrayList<clsPrediction> oRetVal = new ArrayList<clsPrediction>();
//		
//		for (clsTriple<Integer, Integer, clsPrediction> oP : poInput) {
//			oRetVal.add(oP.c);
//		}
//		
//		return oRetVal;
//	}
//	
//	/**
//	 * Get the last moment for an intention from the short time memory
//	 *(wendt)
//	 *
//	 * @since 31.08.2011 14:58:38
//	 *
//	 * @param poPrediction
//	 * @param poShortTimeMemory
//	 * @return
//	 */
//	private clsDataStructureContainerPair getLastMomentFromShortTimeMemory(clsPrediction poPrediction, clsOLDShortTimeMemory poShortTimeMemory) {
//		clsDataStructureContainerPair oRetVal = null;
//		
//		//Get all moments in the short time memory
//		//Predictions are received as they are saved as predictions
//		//ArrayList<clsPair<Integer, Object>> oMomentsInShortTimeMemory = poShortTimeMemory.findMemoriesClassification(moObjectClassMOMENT);
//		ArrayList<clsPair<Integer, Object>> oMomentsInShortTimeMemory = poShortTimeMemory.findMemoriesDataType(eSupportDataType.MOMENT);
//		
//		int nMinTimeStep=1000;	//FIXME AW: This shall not be a hard coded number
//		
//		if (poPrediction.getIntention().getSecondaryComponent()!=null) {
//			//Get Intention
//			clsSecondaryDataStructureContainer oSIntention = poPrediction.getIntention().getSecondaryComponent();
//			//Check if the moment is a substructure of the intention
//			for (clsPair<Integer, Object> oCPair : oMomentsInShortTimeMemory) {
//				if (oCPair.b instanceof clsPrediction) {
//					clsPrediction oPrediction = (clsPrediction) oCPair.b;
//					int nCurrentTimeStep = oCPair.a;
//					
//					//Use this only if there are several intentions for an image
//					//ArrayList<clsSecondaryDataStructure> oPossibleIntentionList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oPrediction.getMoment().getSecondaryComponent(), moPredicateHierarchical, false);
//					
//					//Go through the intentions to find a match
//					//for (clsSecondaryDataStructure oPossibleIntention : oPossibleIntentionList) {
//						//If the "ISA" association of the moment in the short time memory was found, set the moment as the last moment from the memory
//					if (oPrediction.getIntention().getSecondaryComponent().getMoDataStructure().getMoDS_ID() == oSIntention.getMoDataStructure().getMoDS_ID()) {
//						//Set first time step
//						if (oRetVal==null) {
//							nMinTimeStep = nCurrentTimeStep;
//						} 
//							
//						//Check if it is the newest moment
//						if (nCurrentTimeStep <= nMinTimeStep) {
//							oRetVal = oPrediction.getMoment();
//							nMinTimeStep = nCurrentTimeStep;
//						}
//					}
//				}
//			}
//		}
//		
//		return oRetVal;
//	}
	
	/**
	 * Extract a list of all secondary data structure containers, which are defined as INTENTION from a containerlist
	 * (wendt)
	 *
	 * @since 31.08.2011 13:27:30
	 *
	 * @param poInputList
	 * @return
	 */
	private ArrayList<clsDataStructureContainerPair> getIntentionsFromList(ArrayList<clsDataStructureContainerPair> poInputList) {
		ArrayList<clsDataStructureContainerPair> oRetVal = new ArrayList<clsDataStructureContainerPair>();
		
		//Go through all elements
		for (clsDataStructureContainerPair oContainerPair : poInputList) {
			clsSecondaryDataStructureContainer oSContainer = oContainerPair.getSecondaryComponent();
			//if (oContainer instanceof clsSecondaryDataStructureContainer) {
				for (clsAssociation oAss : oSContainer.getMoAssociatedDataStructures()) {
					//If this container is a leaf element of an associationsecondary with the predicate ISA
					if (oAss instanceof clsAssociationSecondary) {
						//An intention is recognized if the image is the Leaf element of a Hierarchical association (ISA)
						if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.ISA.toString()) && (oAss.getLeafElement().getMoDS_ID() == oSContainer.getMoDataStructure().getMoDS_ID())) {
							//The secondary data structure container found
							clsSecondaryDataStructureContainer oSIntention = (clsSecondaryDataStructureContainer) oSContainer;
							
							//Get the primary data structure container if they exist
							//clsPrimaryDataStructureContainer oPIntention = clsDataStructureTools.extractPrimaryContainer((clsSecondaryDataStructureContainer)oContainer, poInputList);
							clsPrimaryDataStructureContainer oPIntention = oContainerPair.getPrimaryComponent(); //clsDataStructureTools.extractPrimaryContainer((clsSecondaryDataStructureContainer)oContainer, poInputList);
							
							//Create container
							clsDataStructureContainerPair oIntentionCPair = new clsDataStructureContainerPair(oSIntention, oPIntention);
							
							//Check if it already exists and if it does not, then add the result
							if (clsDataStructureTools.containerPairExists(oIntentionCPair, oRetVal)==null) {
								//Add the intention also, if the primary data structure container was not found
								oRetVal.add(oIntentionCPair);
							}
						}
					}
				//}
			}
		}
		
		return oRetVal;
	}
	
	
	/**
	 * Delete all predictions without a moment, because they cannot be interpreted
	 * (wendt)
	 *
	 * @since 16.08.2011 21:16:29
	 *
	 * @param poPredictionList
	 */
	private void cleanPredictions(ArrayList<clsTriple<Integer, Integer, clsPrediction>> poPredictionList) {
		ListIterator<clsTriple<Integer, Integer, clsPrediction>> liMainList = poPredictionList.listIterator();
		
		while (liMainList.hasNext()) {
			clsTriple<Integer, Integer, clsPrediction> oPred = liMainList.next();
			//If the secondary component of the prediction is null, then delete
			if (oPred.c.getMoment().getSecondaryComponent()==null) {
				liMainList.remove();
			}
		}
	}
	
	
	/**
	 * Extract the intention from a containerlist and return it as in an ordered act structure
	 * wendt
	 *
	 * @since 22.07.2011 17:46:45
	 *
	 * @param poInput
	 * @return
	 */
	private ArrayList<clsTriple<Integer, Integer, clsPrediction>> getIntention(ArrayList<clsDataStructureContainerPair> poInput, clsOLDShortTimeMemory poShortTimeMemory) {
		
		ArrayList<clsTriple<Integer, Integer, clsPrediction>> oRetVal = new ArrayList<clsTriple<Integer, Integer, clsPrediction>>();
		
		//Get intentions, secondary and primary structures from the input list
		ArrayList<clsDataStructureContainerPair> oIntentionList = getIntentionsFromList(poInput);
		//Set intention classification
		//for (clsDataStructureContainerPair oIntentionCPair : oIntentionList) {
		//	clsDataStructureTools.setClassification((clsSecondaryDataStructureContainer)oIntentionCPair.getSecondaryComponent(), this.moObjectClassINTENTION);
		//}
		
		//Add Intentions to predictions
		for (clsDataStructureContainerPair oIntentionCPair : oIntentionList) {
			//Add intention ONLY if also a primary data structure container is found
			//DOCUMENT AW: This fact should be documented or removed
			if (oIntentionCPair.getPrimaryComponent()!=null) {
				//Intention has been found and grounded by the primary container
				
				//Create the act triple
				clsPrediction oFoundMemory = checkDuplicatePrediction(oIntentionCPair, poShortTimeMemory);
				//If it is a new intention, the value is 1, else 0. This is done, in order to add attributes to the intention
				//in a later function
				int oNewIntention = 0;
				//Create the new tripple
				clsPrediction oActTripple;
				if (oFoundMemory!=null) {
					oNewIntention = 0;
					oActTripple = new clsPrediction(oFoundMemory.getIntention(), null, null);
				} else {
					oNewIntention = 1;
					//Create new prediction
					oActTripple = new clsPrediction(oIntentionCPair, null, null);
					
				}
			
				oRetVal.add(new clsTriple<Integer, Integer, clsPrediction>(oNewIntention, 0, oActTripple));
			}
		}
	
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 09.09.2011 22:05:41
	 *
	 * @param poIntention
	 * @param poShortTimeMemory
	 * @return
	 */
	private clsPrediction checkDuplicatePrediction(clsDataStructureContainerPair poIntention, clsOLDShortTimeMemory poShortTimeMemory) {
		clsPrediction oRetVal = null;
			
		clsPair<Integer, Object> oMemory = poShortTimeMemory.findMemory(new clsPrediction(poIntention, null, null));
		if ((oMemory!=null) && (oMemory.b instanceof clsPrediction)) {
			if (((clsPrediction)oMemory.b).getIntention().getSecondaryComponent().getMoDataStructure().getMoDS_ID() == poIntention.getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
				oRetVal = (clsPrediction)oMemory.b;
			}
		}
		
		return oRetVal;
	}
	// ============== TOTAL COMMON PROGRESS FUNCTIONS BEGIN ==============================//
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 10.09.2011 16:33:27
	 *
	 * @param poIntention
	 */
	private void updateTotalProgress(clsSecondaryDataStructureContainer poIntention) {
		//Get temporal progress
		double rTemporalProgressFactor = clsActTools.getTemporalProgressFactor(poIntention);
		double rTemporalCurrentProgress = clsActTools.getTemporalProgress(poIntention);
		double rTemporalNewProgress = rTemporalProgressFactor + rTemporalCurrentProgress;
		
		//Set the new temporal progress
		clsActTools.setTemporalProgress(poIntention, rTemporalNewProgress);
		
		//Get confirmation progress
		double rConfirmProgressFactor = clsActTools.getConfirmFactor(poIntention);
		double rConfirmCurrentProgress = clsActTools.getConfirmProgress(poIntention);
		
		double rConfirmNewProgress = rConfirmProgressFactor + rConfirmCurrentProgress;
		//Correct values if the reinforcement is already complete
		if ((rConfirmNewProgress<=1) && (rConfirmNewProgress>0)) {
			//Set the new confirmation progress
			//NOTE: The confirmation progress factor cannot be > 1 as at 1.0 the agent believes that it is confirmed
			clsActTools.setConfirmProgress(poIntention, rConfirmNewProgress);
		} 		
		
	}
	
	/**
	 * For each intention, add the confirmation and the temporal factors as well as the first progress steps of them both
	 * (wendt)
	 *
	 * @since 12.09.2011 09:49:54
	 *
	 * @param poExtendedInputList
	 * @param poAssociatedInputs
	 */
	private void addTotalProgress(ArrayList<clsTriple<Integer, Integer, clsPrediction>> poExtendedInputList, ArrayList<clsDataStructureContainerPair> poAssociatedInputs, int pnConfirmationParts) {
		for (clsTriple<Integer, Integer, clsPrediction> oExtPrediction :  poExtendedInputList) {
			//If the intention is new
			if (oExtPrediction.a>0) {
				//Add Progress factor
				addAllFactors(oExtPrediction.c, poAssociatedInputs, pnConfirmationParts);
				//Set the first progress step
				setFirstTemporalProgress(oExtPrediction.c);
				setFirstConfirmProgress(oExtPrediction.c);
				
			}
		}
	}
	
	/**
	 * Add the factors: temporal progress and confirmation progress factors to the intention at the first time, where the intention is loaded
	 * (wendt)
	 *
	 * @since 09.09.2011 22:31:55
	 *
	 * @param poIntention
	 */
	private void addAllFactors(clsPrediction poPrediction, ArrayList<clsDataStructureContainerPair> poAssociatedInputs, int pnConfirmationParts) {
		//pnConfirmationParts is the number of parts, in which a perception-act is split into. As soon as the first part is confirmed, the whole 
		//perception act is evaluated as confirmed
		
		//Count the number of structure until the end of the perception-act
		int nNumberOfStructuresToEnd = clsActTools.countSubStructuresToActEnd(poPrediction.getMoment().getSecondaryComponent(), poAssociatedInputs)+1;
		//Calculate the temporal increment factor
		double rTemporalIncreasementFactor = 1 / (double)nNumberOfStructuresToEnd;
		
		//Set the default progress factor, which is only set once
		clsActTools.setTemporalProgressFactor(poPrediction.getIntention().getSecondaryComponent(), rTemporalIncreasementFactor);
		
		//Calculate the confirmation increment factor
		//Get the number of images, which shall be confirmed, to get a complete confirmation
		int nImagesForConfirmCount = (int)(nNumberOfStructuresToEnd/pnConfirmationParts);
		double rConfirmIncreasementFactor = 0.0;
		if (nImagesForConfirmCount==0) {
			rConfirmIncreasementFactor = 1;
		} else {
			rConfirmIncreasementFactor = 1/(double)nImagesForConfirmCount;
		}
		
		//Set the default progress factor, which is only set once
		clsActTools.setConfirmFactor(poPrediction.getIntention().getSecondaryComponent(), rConfirmIncreasementFactor);
		
	}
	
	// ============== TOTAL COMMON PROGRESS FUNCTIONS END ==============================//
	
	// ============== TEMPORAL PROGRESS FUNCTIONS BEGIN ==============================//	
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 11.09.2011 14:31:23
	 *
	 * @param poIntention
	 */
	private void setFirstTemporalProgress(clsPrediction poPrediction) {
		double rProgressFactor = clsActTools.getTemporalProgressFactor(poPrediction.getIntention().getSecondaryComponent());
		clsActTools.setTemporalProgress(poPrediction.getIntention().getSecondaryComponent(), rProgressFactor);
	}
	
	// ============== PROGRESS FUNCTIONS END ==============================//
	
	// ============== CONFIRMATION FUNCTIONS START ==============================//
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 12.09.2011 10:20:14
	 *
	 * @param poPrediction
	 */
	private void setFirstConfirmProgress(clsPrediction poPrediction) {
		double rProgressFactor = clsActTools.getConfirmFactor(poPrediction.getIntention().getSecondaryComponent());
		clsActTools.setConfirmProgress(poPrediction.getIntention().getSecondaryComponent(), rProgressFactor);
	}
	

	// ============== CONFIRM FUNCTIONS END ==============================//
	
	
//	/**
//	 * Extract the current situation from an act
//	 * (wendt)
//	 *
//	 * @since 22.07.2011 17:51:03
//	 *
//	 * @param poActList
//	 * @param poInput
//	 */
//	private ArrayList<clsTriple<Integer, Integer, clsPrediction>> getMoment(ArrayList<clsTriple<Integer, Integer, clsPrediction>> poActList, ArrayList<clsDataStructureContainerPair> poInput, double prMomentActivationThreshold) {
//
//		ArrayList<clsTriple<Integer, Integer, clsPrediction>> oRetVal = new ArrayList<clsTriple<Integer, Integer, clsPrediction>>();
//		
//		for (clsTriple<Integer, Integer, clsPrediction> oActTriple : poActList) {
//			clsSecondaryDataStructureContainer oIntention = oActTriple.c.getIntention().getSecondaryComponent();
//			//Precondition: All structures are already loaded and can be found in the input list
//			
//			//Go through each association and search for all children, get the best matching image in the list
//			clsDataStructureContainerPair oMoment = getBestMatchSubImage(oIntention, poInput, prMomentActivationThreshold);
//			//Check if the last moment in the short time memory
//			clsDataStructureContainerPair oLastImageCPair = getLastMomentFromShortTimeMemory(oActTriple.c, moShortTimeMemory);
//			//Check temporal order
//			
//			clsPair<Integer,clsDataStructureContainerPair> oVerifiedPair = verifyTemporalOrder(oMoment, oLastImageCPair, poInput);
//			
//			//If nothing is found, then add the last moment, if available. The moment disappears from the memory after a couple of moments
//			clsPair<Integer,clsDataStructureContainerPair> oFinalMomentCandidate = oVerifiedPair;
//			if ((oVerifiedPair.b.getSecondaryComponent()==null) && (oLastImageCPair!=null)) {
//				oFinalMomentCandidate.b = oLastImageCPair;
//			}
//			
//			//Add the moment to the triple
//			oActTriple.c.setMoment(oVerifiedPair.b);
//			clsTriple<Integer, Integer, clsPrediction> oPredictionTriple = new clsTriple<Integer, Integer, clsPrediction>(oActTriple.a, oVerifiedPair.a, oActTriple.c);
//			
//			oRetVal.add(oPredictionTriple);
//		}
//		
//		return oRetVal;
//	}
	
	/**
	 * If an image shall be seen as probable, it has to have some connection with the previous image or an image, which has the "HASNEXT" attribute to this image, 
	 * else, the assignment of the moment maybe false interpreted by the agent
	 * AW: Make this docu better....
	 * 
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 19.08.2011 14:35:21
	 *
	 * @param oBestImageMatch
	 * @param oLastMomentInShortTimeMemory
	 * @return
	 */
	private clsPair<Integer, clsDataStructureContainerPair> verifyTemporalOrder (clsDataStructureContainerPair poBestImageMatch, clsDataStructureContainerPair poLastMomentInShortTimeMemory, ArrayList<clsDataStructureContainerPair> poTotalList) {
		//pnSavePredictionMode is a refence, which is modified in the function
		int nSavePredictionMode = 0;
		boolean bQuit = false;		
		clsPair<Integer, clsDataStructureContainerPair> oRetVal = new clsPair<Integer, clsDataStructureContainerPair>(0, new clsDataStructureContainerPair(null, null));	//This is set, in case nothing is done here
		
		//A correct moment is either same moment as now or a moment, which is connected somehow with the last moment.
		//FIXME AW: ********* In reinforcement of expectations, this topic has to be refactored *************
		
		//***** Check nulls ************
		//Check null and return an empty container pair
		if (poBestImageMatch==null) {
			//return new clsPair<Integer, clsDataStructureContainerPair>(0, null);
			//return oRetVal;
			bQuit = true;
		//Check null and pass through if no short time memory
		//If it is not null, then it must have a secondary container
		} else if (poLastMomentInShortTimeMemory==null) {
			//If a valid moment was found, set the moment
			//clsDataStructureTools.setClassification(poBestImageMatch.getSecondaryComponent(), this.moObjectClassMOMENT);
			//Set save mode for short time memory
			nSavePredictionMode = 1; //moShortTimeMemory.saveToShortTimeMemory(poBestImageMatch);
			oRetVal.a = nSavePredictionMode;
			oRetVal.b = poBestImageMatch;
			bQuit = true;
		} else {


			//Check if these moments are null
			if (poBestImageMatch.getSecondaryComponent()==null || poLastMomentInShortTimeMemory.getSecondaryComponent()==null) {
				bQuit = true;
			}
		}
		
		//*****************************

		if (bQuit==false) {
			//Get moment in the best match image
			clsSecondaryDataStructureContainer oProposedMoment = poBestImageMatch.getSecondaryComponent();
			//Get moment in the last moment in the memory
			clsSecondaryDataStructureContainer oLastMomentSecondary = poLastMomentInShortTimeMemory.getSecondaryComponent();
			
			//If there are no expectations, then this moment was the last moment and nothing from this act should be found any more
			//Return the best match image
			if (bQuit==false) {
				ArrayList<clsSecondaryDataStructure> oCurrentExpList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oProposedMoment, ePredicate.HASNEXT.toString(), false);
				if (oCurrentExpList.isEmpty()==true) {
					//Return oRetVal without anything
					//return oRetVal;
					nSavePredictionMode = 0;
					oRetVal.a = nSavePredictionMode;
					oRetVal.b = poBestImageMatch;
					bQuit = true;
				}
			}
			
			//If the best match was the saved moment in the short time memory, then return it with forced save
			if (bQuit==false) {	
				if (oProposedMoment.getMoDataStructure().getMoDS_ID() == oLastMomentSecondary.getMoDataStructure().getMoDS_ID()) {
					//If a valid moment was found, set the moment
					//clsDataStructureTools.setClassification(poBestImageMatch.getSecondaryComponent(), this.moObjectClassMOMENT);
					//Force to save the found image to the temporal memory
					//Set memory save mode
					nSavePredictionMode = 2;
					//moShortTimeMemory.saveToShortTimeMemory(poBestImageMatch, true);
					oRetVal.a = nSavePredictionMode;
					oRetVal.b = poBestImageMatch;
					//return oRetVal;
					bQuit = true;
				}
			} 
			
			//else if the best match is an expectation of the previous moment, then it is ok too
			if (bQuit==false) {
				//Get the expectations of the last moment
				ArrayList<clsSecondaryDataStructure> oPlausibleExpList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oLastMomentSecondary, ePredicate.HASNEXT.toString(), false);
				for (clsSecondaryDataStructure oPlausibleExp :  oPlausibleExpList) {
					if (oPlausibleExp.getMoDS_ID() == oProposedMoment.getMoDataStructure().getMoDS_ID()) {
						//If a valid moment was found, set the moment
						//clsDataStructureTools.setClassification(poBestImageMatch.getSecondaryComponent(), this.moObjectClassMOMENT);
						//Force to save the best match to the temporal memory
						//Set save mode
						nSavePredictionMode = 2;
						//moShortTimeMemory.saveToShortTimeMemory(poBestImageMatch, true);
						oRetVal.a = nSavePredictionMode;
						oRetVal.b = poBestImageMatch;
						//return oRetVal;
						bQuit = true;
					}
				}
			}
			
			//and if still nothing is found, then use the last possibility
			if (bQuit==false) {
				//Check if at least the last moment in the short time memory still has some matching
				boolean oPartiallyMatchesOK = checkMomentPreconditionMinMatch(poLastMomentInShortTimeMemory, poTotalList);
				if (oPartiallyMatchesOK==true) {
					nSavePredictionMode = 0;
					oRetVal.a = nSavePredictionMode;
					oRetVal.b = poLastMomentInShortTimeMemory;
					//return oRetVal;
					bQuit = true;
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Check if the last image or its expectations have a matc value > threshold. This is a precondition for using the saved
	 * last image
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 19.08.2011 22:35:19
	 *
	 * @param oLastMomentInShortTimeMemory
	 * @param poTotalList
	 * @return
	 */
	private boolean checkMomentPreconditionMinMatch(clsDataStructureContainerPair oLastMomentInShortTimeMemory, ArrayList<clsDataStructureContainerPair> poTotalList) {
		boolean oRetVal = false;
		
		//Get the Primary container in the list of current matches from the last known moment
		clsDataStructureContainerPair oLastMomentContainer = clsDataStructureTools.searchContainerPairList(oLastMomentInShortTimeMemory.getSecondaryComponent(), poTotalList);
		double oLastMomentMatch = 0.0;
		//If the container !=null, then get the match value with PI
		if (oLastMomentContainer.getPrimaryComponent()!=null) {
			oLastMomentMatch = clsDataStructureTools.getMatchValueToPI(oLastMomentContainer.getPrimaryComponent());
		}
		
		//If the last moment passes, the quit with true
		if (oLastMomentMatch > mrMomentMinRelevanceThreshold) {
			oRetVal = true;
			return oRetVal;
		}
		
		//else, check the expectations
		
		//Get the match values of the expectations of the last moment
		double oBestExpectationMatch = 0.0;
		if (oLastMomentInShortTimeMemory.getSecondaryComponent()!=null) {
			double oExpectationMatch = 0.0;
			ArrayList<clsSecondaryDataStructure> oPlausibleExpList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oLastMomentInShortTimeMemory.getSecondaryComponent(), ePredicate.HASNEXT.toString(), false);
			for (clsSecondaryDataStructure oS : oPlausibleExpList) {
				clsDataStructureContainerPair oContainerPair = clsDataStructureTools.getContainerFromList(poTotalList, oS);
				if (oContainerPair.getSecondaryComponent()!=null) {
					clsPrimaryDataStructureContainer oPrimaryContainer = oContainerPair.getPrimaryComponent();//clsDataStructureTools.extractPrimaryContainer(oSecondaryContainer, poTotalList);
					if (oPrimaryContainer!=null) {
						oExpectationMatch = clsDataStructureTools.getMatchValueToPI(oPrimaryContainer);
						if (oExpectationMatch>oBestExpectationMatch) {
							oBestExpectationMatch = oExpectationMatch;
						}
					}
				}
			}
		}
		
		
		if (oBestExpectationMatch > mrMomentMinRelevanceThreshold) {
			oRetVal = true;
			return oRetVal;
		}
		
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 04.08.2011 13:59:51
	 *
	 * @param poIntentionAssociations
	 * @param poSourceList
	 * @return
	 */
	private clsDataStructureContainerPair getBestMatchSubImage(clsSecondaryDataStructureContainer poIntention, ArrayList<clsDataStructureContainerPair> poSourceList, double prMomentActivationThreshold) {
		clsDataStructureContainerPair oRetVal = null;
		double rMaxValue = 0.0;
				
		ArrayList<clsSecondaryDataStructure> oSubImages = clsDataStructureTools.getDSFromSecondaryAssInContainer(poIntention, ePredicate.ISA.toString(), true);
		
		for (clsDataStructurePA oDS : oSubImages) {
			//Get the Image DS
			//Find the corresponding WP-container
			clsSecondaryDataStructureContainer oCurrentSituationWPContainer = null;
			clsPrimaryDataStructureContainer oPContainer = null;
			for (clsDataStructureContainerPair oContainerPair : poSourceList) {
				clsSecondaryDataStructureContainer oSContainer = oContainerPair.getSecondaryComponent();
				if (oDS.getMoDS_ID() == oSContainer.getMoDataStructure().getMoDS_ID()) {
					oCurrentSituationWPContainer = oSContainer;
					oPContainer = oContainerPair.getPrimaryComponent();
					break;
				}
			}
			
			//If this container could be found, search for the primary Data structure container
			
//			if (oCurrentSituationWPContainer!=null) {
//				oPContainer = clsDataStructureTools.extractPrimaryContainer(oCurrentSituationWPContainer, poSourceList);
//			}
			
			//Get the Matchvalue to the Perceived Image 
			if (oPContainer != null) {
				double rMatchValue = clsDataStructureTools.getMatchValueToPI(oPContainer);
				
				//Make additions if logical order is given and all matches are 1.0
				//Conditions: There shall be something in the short time memory and the match shall be very good, i. e. >= 0.9
				//Cases:
				//1. The current situation is found => logical order adds 0.1 to the match value
				//2. The expectation is found => logical order adds 0.1 to the match value
				//Purpose: If there are 3 Images with 1.0 match, then the most probable shall be taken.
//				if ((moShortTimeMemory!=null) && (moShortTimeMemory.b.getSecondaryComponent() !=null ) && (rMatchValue >= 0.9)) {
//					double rBonusForOrderValue = 0.0;
//					if (moShortTimeMemory.b.getSecondaryComponent().getMoDataStructure().getMoDS_ID() == oCurrentSituationWPContainer.getMoDataStructure().getMoDS_ID()) {
//						//FIXME AW: This bonus value is not necessary
//						//rBonusForOrderValue = 0.1;
//					} else {
//						ArrayList<clsSecondaryDataStructure> oExpectationElement = clsDataStructureTools.getDSFromSecondaryAssInContainer(moShortTimeMemory.b.getSecondaryComponent(), moPredicateTemporal, false);
//						for (clsSecondaryDataStructure oSDS : oExpectationElement) {
//							if (oSDS.getMoDS_ID() == oCurrentSituationWPContainer.getMoDataStructure().getMoDS_ID()) {
//								//FIXME AW: This bonus value is not necessary
//								//rBonusForOrderValue = 0.1;
//							}
//						}
//					}
//					rMatchValue += rBonusForOrderValue;
//					 
//				}
				
				//If the new value is the highest value and it is higher than the threshold value
				if ((rMatchValue > rMaxValue) && (rMatchValue >= prMomentActivationThreshold)) {
					rMaxValue = rMatchValue;
					try {
						oRetVal = new clsDataStructureContainerPair((clsSecondaryDataStructureContainer) oCurrentSituationWPContainer.clone(), (clsPrimaryDataStructureContainer) oPContainer.clone());
						//oRetVal = (clsSecondaryDataStructureContainer) oCurrentSituationWPContainer.clone();
					} catch (CloneNotSupportedException e) {
						// TODO (wendt) - Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return oRetVal;
	}

	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 04.08.2011 14:00:02
	 *
	 * @param poActList
	 * @param poInput
	 */
	private ArrayList<clsTriple<Integer, Integer, clsPrediction>> getExpectations(ArrayList<clsTriple<Integer, Integer, clsPrediction>> poActList, ArrayList<clsDataStructureContainerPair> poInput, clsOLDShortTimeMemory poShortTimeMemory) {
		ArrayList<clsTriple<Integer, Integer, clsPrediction>> oRetVal = new ArrayList<clsTriple<Integer, Integer, clsPrediction>>();
		
		for (clsTriple<Integer, Integer, clsPrediction> oActTripple : poActList) {
			clsSecondaryDataStructureContainer oIntention = oActTripple.c.getIntention().getSecondaryComponent();
			clsSecondaryDataStructureContainer oCurrentSituation = null;
			if (oIntention!=null) {
				//A prediction always have set a moment, intention and expectationlist. If the secondary component of the 
				//moment is null, then do nothing
				oCurrentSituation = oActTripple.c.getMoment().getSecondaryComponent();
			}
			
			//3. Find the expectation in each Perception-act
			//Do it with clsAssociationSecondary and ISA Intention and HASNEXT as leaf element of the association with the
			//current situation
			if (oCurrentSituation!=null) {
				//Get all associations with "HASNEXT" from the current situation
				ArrayList<clsSecondaryDataStructure> oExpectationElement = clsDataStructureTools.getDSFromSecondaryAssInContainer(oCurrentSituation, ePredicate.HASNEXT.toString(), false);
				
				for (clsSecondaryDataStructure oSecDS : oExpectationElement) {
					clsPair<Integer, Object> oExpectationMemory = poShortTimeMemory.findMemory(oSecDS);
					
					clsDataStructureContainerPair oPossibleExpectation = null;
					
					if (oExpectationMemory==null) {
						oPossibleExpectation = clsDataStructureTools.getContainerFromList(poInput, oSecDS);
					} else {
						oPossibleExpectation = ((clsDataStructureContainerPair)oExpectationMemory.b);
					}
					
					if (oPossibleExpectation.getSecondaryComponent()==null) {
						try {
							throw new Exception("Code/Protege error in F51_RealityCheckWishFulfillment, setCurrentExpectation: " +
									"No expectation found, although it should be found. This error occurs if the intention " +
									"does not have any of the objects, which is contained in one expectation, e. g. a current situation contains a WALL and " + 
									"the intention does not" + "Images: oCurrentSituation: " + oCurrentSituation.getMoDataStructure().toString() + "oSecDS: " + oSecDS);
						} catch (Exception e) {
							// TODO (wendt) - Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					//Get all DS connected with a "ISA" from the expectation
					ArrayList<clsSecondaryDataStructure> oIntentionFromPossibleExp = clsDataStructureTools.getDSFromSecondaryAssInContainer(oCurrentSituation, ePredicate.ISA.toString(), false);
					
					for (clsSecondaryDataStructure oIntentionInAss : oIntentionFromPossibleExp) {
						if (oIntentionInAss.getMoDS_ID() == oIntention.getMoDataStructure().getMoDS_ID()) {
							//Get the primary structure for this expectation
							//clsPrimaryDataStructureContainer oPExpectation = oPossibleExpectation.getPrimaryComponent(); //clsDataStructureTools.extractPrimaryContainer(oPossibleExpectation, poInput);
							//The expectation has been found and confirmed
							//Set the classification
							//if (oPossibleExpectation!=null) {
							//	clsDataStructureTools.setClassification(oPossibleExpectation, this.moObjectClassEXPECTATION);
							//}
							
							//Add the secondary and the primary (if available) to the expectations
							clsDataStructureContainerPair oExpectation = oPossibleExpectation; //new clsDataStructureContainerPair(oPossibleExpectation, oPExpectation);
							
							oActTripple.c.getExpectations().add(oExpectation);
						}
					}
					
				}
			}
			oRetVal.add(oActTripple);
		}
		
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 12.09.2011 10:39:41
	 *
	 * @param poPrediction
	 */
	private void addAffectReduceValues(ArrayList<clsTriple<Integer, Integer, clsPrediction>> poPredictionList, double prReduceFactorForDrives) {
		
		for (clsTriple<Integer, Integer, clsPrediction> oP : poPredictionList) {
			//0. Check if the structure shall be processed with reduce value 
			boolean bReduceAffect = clsActTools.getActivateReduceFactor(oP.c.getIntention().getSecondaryComponent());
			
			if (bReduceAffect==true) {
				//1. Remove old associations in order to replace them
				clsDataStructureTools.removeAssociation(oP.c.getIntention().getSecondaryComponent(), ePredicate.HASREALITYAFFECT);
				//2. Get progress and confirmation factors
				double rTemporalProgress = clsActTools.getTemporalProgress(oP.c.getIntention().getSecondaryComponent()); 
				double rConfirmationProgress = clsActTools.getConfirmProgress(oP.c.getIntention().getSecondaryComponent());
				//3. Get all affects from the intention
				ArrayList<clsSecondaryDataStructureContainer> oDriveGoalList = new ArrayList<clsSecondaryDataStructureContainer>(); //FIXME AW: This part was excluded because of the mesh structure//clsAffectTools.getDriveGoalsFromPrediction(oP.c);
				//4. For each affect from the intention, calculate a reduction affect
				for (clsSecondaryDataStructureContainer oGoalContainer : oDriveGoalList) {
					//5. Get the drive intensity of the drive connected to the goal
					clsActTools.setReduceAffect(oP.c.getIntention().getSecondaryComponent(), oGoalContainer, prReduceFactorForDrives, rTemporalProgress, rConfirmationProgress);
				}
				//TODO: As WPs are replaced by WPMs, the affects can be attached like in the PP
			}
		}
	}
	

	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:25
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the bjects of the type pa._v38.memorymgmt.datatypes
		send_I6_7(moReachableGoalList_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:51:11
	 * 
	 * @see pa.interfaces.send.I2_13_send#send_I2_13(java.util.ArrayList)
	 */
	@Override
	public void send_I6_7(ArrayList<clsWordPresentationMesh> poReachableGoalList)
			{
		((I6_7_receive)moModuleList.get(26)).receive_I6_7(poReachableGoalList);
		
		putInterfaceData(I6_7_send.class, poReachableGoalList);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:25
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:25
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 03.03.2011, 16:50:53
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "The external world is evaluated regarding the available possibilities for drive satisfaction and which requirements arise. This is done by utilization of semantic knowledge provided by {E25} and incoming word and things presentations from {E23}. The result influences the generation of motives in {E26}.";
	}		
	
}

