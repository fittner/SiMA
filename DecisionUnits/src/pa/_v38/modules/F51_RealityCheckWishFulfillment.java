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
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_7_receive;
import pa._v38.interfaces.modules.I6_7_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.storage.clsShortTimeMemory;
import pa._v38.tools.clsDataStructureTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;

/**
 * The external world is evaluated regarding the available possibilities for drive satisfaction and which requirements arise. This is done by utilization of semantic knowledge provided by {E25} and incoming word and things presentations from {E23}. The result influences the generation of motives in {E26}. 
 * 
 * @author kohlhauser
 * 11.08.2009, 14:49:09
 * 
 */
public class F51_RealityCheckWishFulfillment extends clsModuleBase implements I6_6_receive, I6_7_send {
	public static final String P_MODULENUMBER = "51";
	
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:45 */
	private clsDataStructureContainerPair moEnvironmentalPerception_IN;  
	/** Container of activated associated memories */
	private ArrayList<clsDataStructureContainer> moAssociatedMemoriesSecondary_IN;
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:49 */
	private clsDataStructureContainerPair moEnvironmentalPerception_OUT; 
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:50 */
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList;  //removed by HZ - not required now
	/** A construction of an Intention, an arraylist with expectations and the current situation */
	private ArrayList<clsPrediction> moExtractedPrediction_OUT;
	
	/** A threshold for images, which are only set moment if the match factor is higher or equal this value */
	private double mrMomentActivationThreshold = 1.0;
	private double mrMomentMinRelevanceThreshold = 0.2;
	
	private String moPredicateTemporal = "HASNEXT";
	private String moPredicateHierarchical = "ISA";
	
	private String moObjectClassMOMENT = "MOMENT";
	private String moObjectClassINTENTION = "INTENTION";
	private String moObjectClassEXPECTATION = "EXPECTATION";
	
	//private String moPredicateTimeValue = "HASTIMEVALUE";	//not necessary now
	
	
	/** Short time memory */
	private clsShortTimeMemory moShortTimeMemory;
	
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:50:46
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F51_RealityCheckWishFulfillment(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
		
		//Init short time memory
		moShortTimeMemory = new clsShortTimeMemory();
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
		
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.listToTEXT("moAssociatedMemoriesSecondary_IN", moAssociatedMemoriesSecondary_IN);
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_OUT);
		text += toText.listToTEXT("moExtractedPrediction_OUT", moExtractedPrediction_OUT);
		text += toText.listToTEXT("moDriveList", moDriveList);
		
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
	public void receive_I6_6(clsDataStructureContainerPair poPerception, ArrayList<clsSecondaryDataStructureContainer> poDriveList, 
			ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary) {
		try {
			moEnvironmentalPerception_IN = (clsDataStructureContainerPair)poPerception.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		moDriveList = (ArrayList<clsSecondaryDataStructureContainer>) deepCopy(poDriveList);
		moAssociatedMemoriesSecondary_IN = (ArrayList<clsDataStructureContainer>)deepCopy(poAssociatedMemoriesSecondary);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:25
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void process_basic() {
		//Update short time memory from last step
		if (moShortTimeMemory==null) {
			try {
				throw new Exception("moShortTimeMemory==null");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		moShortTimeMemory.updateTimeSteps();
		//FIXME AW: Should anything be done with the perception here?
		try {
			moEnvironmentalPerception_OUT = (clsDataStructureContainerPair)moEnvironmentalPerception_IN.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		moExtractedPrediction_OUT = extractPredictions(moAssociatedMemoriesSecondary_IN);
	}
	
//	/**
//	 * Update the time value of the short time memory
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 19.08.2011 21:42:29
//	 *
//	 */
//	private void updateShortTimeMemory() {
//		if (moShortTimeMemory!=null) {
//			moShortTimeMemory.a++;
//			if (moShortTimeMemory.a>mnMaxTimeValue) {
//				clearShortTimeMemory();
//			}
//		}
//	}
	
//	/**
//	 * Save a data structure to the short time memory
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 19.08.2011 14:32:56
//	 *
//	 * @param poInput
//	 */
//	private void saveToShortTimeMemory(clsDataStructureContainerPair poInput, boolean forceSave) {
//		//save only if this moID is not already saved
//		
//		if (((moShortTimeMemory!=null) && 
//				(moShortTimeMemory.b.getSecondaryComponent()!=null) &&
//				(poInput.getSecondaryComponent().getMoDataStructure().getMoDS_ID() != moShortTimeMemory.b.getSecondaryComponent().getMoDataStructure().getMoDS_ID())) ||
//				(moShortTimeMemory==null) || 
//				(forceSave==true)) {
//					//Replace structure and set the time value
//					try {
//						clsDataStructureContainerPair oAddPair = (clsDataStructureContainerPair) poInput.clone();
//						moShortTimeMemory = new clsPair<Integer, clsDataStructureContainerPair>(0, oAddPair);
//					} catch (CloneNotSupportedException e) {
//						// TODO (wendt) - Auto-generated catch block
//						e.printStackTrace();
//					}
//		}
//	}
	
	
	/**
	 * For each Perception-Act, extract 1. the current situation, the expectation and the intention
	 * (wendt)
	 *
	 * @since 22.07.2011 15:24:21
	 *
	 * @param oInput
	 * @return
	 */
	private ArrayList<clsPrediction> extractPredictions(ArrayList<clsDataStructureContainer> poInput) {
		ArrayList<clsPrediction> oRetVal = new ArrayList<clsPrediction>();
		
		//1. Find the intention in each Perception-act
		//Do it with clsAssociationSecondary and ISA
		//FIXME AW: Only the first "parent" is taken. It should be expanded to multiple parents
		oRetVal.addAll(getIntention(poInput));
		//Now, all acts are assigned and all intentions are known
		
		//2. Find the current situation in each Perception-act
		//Do it hasAssociation, which is instanceof clsAssociationPrimary with Perceived Image and 
		//with the highest association weight
		//2.a Get all current situations, which are correspsonding to the intentions
		/* 2.b A current situation has the following:
		 * - For an intention, the highest match value of the associationPrimary
		 */
		getMoment(oRetVal, poInput, mrMomentActivationThreshold);
		//2.c Check extrapolation of values
		//XXXX
		
		getPreviousMomentFromShortTimeMemory(oRetVal, moShortTimeMemory);
		
		
		//3. Remove all predictions, where there is no current moment for an intention
		cleanPredictions(oRetVal);
		
		//4. Find the expectation in each Perception-act
		//Do it with clsAssociationSecondary and ISA Intention and HASNEXT as leaf element of the association with the
		//current situation
		getExpectations(oRetVal, poInput);
		
		return oRetVal;
	}
	
//	/**
//	 * Temporary short time memory function, which shall clear the short time memory, i. e. the last moment if the intention
//	 * cannot be found. It shall prevent the acting upon expectation, if the intention has been lost.
//	 * 
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 19.08.2011 13:34:31
//	 *
//	 * @param poInput
//	 */
//	private void manageIntentionEvents(ArrayList<clsPrediction> poInput) {
//		if (poInput.isEmpty()) {
//			clearShortTimeMemory();
//		}	
//	}
	
	/**
	 * Cheat function, in order to have some type of short time memory. If no moment could be found, the last moment should be used
	 * in order to continue the action
	 * 
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 18.08.2011 22:11:55
	 *
	 * @param poPrediction
	 * @param poShortTimeMemory
	 */
	private void getPreviousMomentFromShortTimeMemory(ArrayList<clsPrediction> poPrediction, clsShortTimeMemory poShortTimeMemory) {
		//Modify poPrediction
		//Get moments
		//ArrayList<clsDataStructureContainerPair> oMomentsInShortTimeMemory = poShortTimeMemory.findMemoriesClassification(moObjectClassMOMENT);
		
		for (clsPrediction oP : poPrediction) {
			clsDataStructureContainerPair oMomentCPair = getLastMomentFromShortTimeMemory(oP, poShortTimeMemory);
			
			if (oMomentCPair!=null) {
				oP.setMoment(oMomentCPair);
			}
		}
	}
	
	/**
	 * Get the last moment for an intention from the short time memory
	 *(wendt)
	 *
	 * @since 31.08.2011 14:58:38
	 *
	 * @param poPrediction
	 * @param poShortTimeMemory
	 * @return
	 */
	private clsDataStructureContainerPair getLastMomentFromShortTimeMemory(clsPrediction poPrediction, clsShortTimeMemory poShortTimeMemory) {
		clsDataStructureContainerPair oRetVal = null;
		
		//Get all moments in the short time memory
		ArrayList<clsPair<Integer, Object>> oMomentsInShortTimeMemory = poShortTimeMemory.findMemoriesClassification(moObjectClassMOMENT);
		
		int iTimeStep=1000;	//FIXME AW: This shall not be a hard coded number
		
		if (poPrediction.getIntention().getSecondaryComponent()!=null) {
			//Get Intention
			clsSecondaryDataStructureContainer oSIntention = poPrediction.getIntention().getSecondaryComponent();
			//Check if the moment is a substructure of the intention
			for (clsPair<Integer, Object> oCPair : oMomentsInShortTimeMemory) {
				if (oCPair.b instanceof clsDataStructureContainerPair) {
					ArrayList<clsSecondaryDataStructure> oPossibleIntentionList = clsDataStructureTools.getDSFromSecondaryAssInContainer(((clsDataStructureContainerPair)oCPair.b).getSecondaryComponent(), moPredicateHierarchical, false);
					//Go through the intentions to find a match
					for (clsSecondaryDataStructure oPossibleIntention : oPossibleIntentionList) {
						//If the "ISA" association of the moment in the short time memory was found, set the moment as the last moment from the memory
						if (oPossibleIntention.getMoDS_ID() == oSIntention.getMoDataStructure().getMoDS_ID()) {
							//Set first time step
							if (oRetVal==null) {
								iTimeStep = oCPair.a;
							} 
							
							//Check if it is the newest moment
							if (oCPair.a <= iTimeStep) {
								oRetVal = (clsDataStructureContainerPair)oCPair.b;
								iTimeStep = oCPair.a;
							}
						}
					}
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Extract a list of all secondary data structure containers, which are defined as INTENTION from a containerlist
	 * (wendt)
	 *
	 * @since 31.08.2011 13:27:30
	 *
	 * @param poInputList
	 * @return
	 */
	private ArrayList<clsDataStructureContainerPair> getIntentionsFromList(ArrayList<clsDataStructureContainer> poInputList) {
		ArrayList<clsDataStructureContainerPair> oRetVal = new ArrayList<clsDataStructureContainerPair>();
		
		//Go through all elements
		for (clsDataStructureContainer oContainer : poInputList) {
			if (oContainer instanceof clsSecondaryDataStructureContainer) {
				for (clsAssociation oAss : oContainer.getMoAssociatedDataStructures()) {
					//If this container is a leaf element of an associationsecondary with the predicate ISA
					if (oAss instanceof clsAssociationSecondary) {
						//An intention is recognized if the image is the Leaf element of a Hierarchical association (ISA)
						if (((clsAssociationSecondary)oAss).getMoPredicate().equals(moPredicateHierarchical) && (oAss.getLeafElement().getMoDS_ID() == oContainer.getMoDataStructure().getMoDS_ID())) {
							//The secondary data structure container found
							clsSecondaryDataStructureContainer oSIntention = (clsSecondaryDataStructureContainer) oContainer;
							
							//Get the primary data structure container if they exist
							clsPrimaryDataStructureContainer oPIntention = clsDataStructureTools.extractPrimaryContainer((clsSecondaryDataStructureContainer)oContainer, poInputList);
							
							//Create container
							clsDataStructureContainerPair oIntentionCPair = new clsDataStructureContainerPair(oSIntention, oPIntention);
							
							//Check if it already exists and if it does not, then add the result
							if (containerPairExists(oIntentionCPair, oRetVal)==null) {
								//Add the intention also, if the primary data structure container was not found
								oRetVal.add(oIntentionCPair);
							}
						}
					}
				}
			}
		}
		
		return oRetVal;
	}
	
	private clsDataStructureContainerPair containerPairExists(clsDataStructureContainerPair poCompareCPair, ArrayList<clsDataStructureContainerPair> poTargetList) {
		clsDataStructureContainerPair oRetVal = null;
		
		for (clsDataStructureContainerPair oCPair : poTargetList) {
			if ((poCompareCPair!=null) && (poCompareCPair.getSecondaryComponent()!=null) && (oCPair.getSecondaryComponent()!=null)) {
				if (poCompareCPair.getSecondaryComponent().getMoDataStructure().getMoDS_ID() == oCPair.getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
					oRetVal = oCPair;
					break;
				}
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
	private void cleanPredictions(ArrayList<clsPrediction> poPredictionList) {
		ListIterator<clsPrediction> liMainList = poPredictionList.listIterator();
		
		while (liMainList.hasNext()) {
			clsPrediction oPred = liMainList.next();
			if (oPred.getMoment()==null) {
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
	private ArrayList<clsPrediction> getIntention(ArrayList<clsDataStructureContainer> poInput) {
		
		ArrayList<clsPrediction> oRetVal = new ArrayList<clsPrediction>();
		
		//Get intentions, secondary and primary structures from the input list
		ArrayList<clsDataStructureContainerPair> oIntentionList = getIntentionsFromList(poInput);
		//Set intention classification
		for (clsDataStructureContainerPair oIntentionCPair : oIntentionList) {
			clsDataStructureTools.setClassification((clsSecondaryDataStructureContainer)oIntentionCPair.getSecondaryComponent(), this.moObjectClassINTENTION);
		}
		
		
		//Add Intentions to predictions
		for (clsDataStructureContainerPair oIntentionCPair : oIntentionList) {
			//Add intention ONLY if also a primary data structure container is found
			//DOCUMENT AW: This fact should be documented or removed
			if (oIntentionCPair.getPrimaryComponent()!=null) {
				//Intention has been found and grounded by the primary container
				
				//Create the act triple							
				clsPrediction oActTripple = new clsPrediction(oIntentionCPair, null, null);
				//((new clsPair<clsSecondaryDataStructureContainer)oContainer, clsPrimaryDataStructureContainer>, new ArrayList<clsPair<clsSecondaryDataStructureContainer, clsPrimaryDataStructureContainer>>(), null);
				oRetVal.add(oActTripple);
			}
		}
	
		return oRetVal;
	}
	
	/**
	 * Extract the current situation from an act
	 * (wendt)
	 *
	 * @since 22.07.2011 17:51:03
	 *
	 * @param poActList
	 * @param poInput
	 */
	private void getMoment(ArrayList<clsPrediction> poActList, ArrayList<clsDataStructureContainer> poInput, double prMomentActivationThreshold) {
		
		for (clsPrediction oActTripple : poActList) {
			clsSecondaryDataStructureContainer oIntention = oActTripple.getIntention().getSecondaryComponent();
			//Precondition: All structures are already loaded and can be found in the input list
			//Go through each association and search for all children
			clsDataStructureContainerPair oMoment = getBestMatchSubImage(oIntention, poInput, prMomentActivationThreshold);
			//Check if the last image 
			clsDataStructureContainerPair oLastImageCPair = getLastMomentFromShortTimeMemory(oActTripple, moShortTimeMemory);
			//Check temporal order
			clsDataStructureContainerPair oVerifiedMoment = verifyTemporalOrder(oMoment, oLastImageCPair, poInput);
			
			//Add the moment to the triple
			oActTripple.setMoment(oVerifiedMoment);

		}
	}
	
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
	private clsDataStructureContainerPair verifyTemporalOrder (clsDataStructureContainerPair poBestImageMatch, clsDataStructureContainerPair oLastMomentInShortTimeMemory, ArrayList<clsDataStructureContainer> poTotalList) {
		clsDataStructureContainerPair oRetVal = new clsDataStructureContainerPair(null, null);	//This is set, in case nothing is done here
		
		//A correct moment is either same moment as now or a moment, which is connected somehow with the last moment.
		//FIXME AW: ********* In reinforcement of expectations, this topic has to be refactored *************
		
		//***** Checl nulls ************
		
		//Check null and return input
		if (poBestImageMatch==null) {
			return null;
		}
		
		clsSecondaryDataStructureContainer oProposedMoment = poBestImageMatch.getSecondaryComponent();
		//Check null and pass thorugh if no short time memory
		//If it is not null, then it must have a secondary container
		if (oLastMomentInShortTimeMemory==null) {
			//If a valid moment was found, set the moment
			if (poBestImageMatch!=null) {
				clsDataStructureTools.setClassification(poBestImageMatch.getSecondaryComponent(), this.moObjectClassMOMENT);
			}
			moShortTimeMemory.saveToShortTimeMemory(poBestImageMatch);
			oRetVal = poBestImageMatch;
			return oRetVal;
		}
		
		//*****************************
		
		//Get preconditions
		
		clsSecondaryDataStructureContainer oLastMomentSecondary = oLastMomentInShortTimeMemory.getSecondaryComponent();

		//If the last image is found again, it is passed
		if (oProposedMoment!=null && oLastMomentSecondary!=null) {
			
			//If there are no expectations, then this moment was the last moment and nothing from this act should be found any more
			ArrayList<clsSecondaryDataStructure> oCurrentExpList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oProposedMoment, moPredicateTemporal, false);
			if (oCurrentExpList.isEmpty()==true) {
				//Return oRetVal without anything
				return oRetVal;
			}
			
			//If the best match was the saved moment in the short time memory, then return it with forced save
			if (oProposedMoment.getMoDataStructure().getMoDS_ID() == oLastMomentSecondary.getMoDataStructure().getMoDS_ID()) {
				//If a valid moment was found, set the moment
				if (poBestImageMatch!=null) {
					clsDataStructureTools.setClassification(poBestImageMatch.getSecondaryComponent(), this.moObjectClassMOMENT);
				}
				//Force to save the found image to the temporal memory
				moShortTimeMemory.saveToShortTimeMemory(poBestImageMatch, true);
				oRetVal = poBestImageMatch;
				return oRetVal;
			}
		
			//else if the best match is an expectation of the previous moment, then it is ok too
			ArrayList<clsSecondaryDataStructure> oPlausibleExpList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oLastMomentSecondary, moPredicateTemporal, false);
			for (clsSecondaryDataStructure oPlausibleExp :  oPlausibleExpList) {
				if (oPlausibleExp.getMoDS_ID() == oProposedMoment.getMoDataStructure().getMoDS_ID()) {
					//If a valid moment was found, set the moment
					if (poBestImageMatch!=null) {
						clsDataStructureTools.setClassification(poBestImageMatch.getSecondaryComponent(), this.moObjectClassMOMENT);
					}
					//Force to save the best match to the temporal memory
					moShortTimeMemory.saveToShortTimeMemory(poBestImageMatch, true);
					oRetVal = poBestImageMatch;
					return oRetVal;
				}
			}
		}
		
		//and if still nothing is found, then use the last possibility
		boolean oPartiallyMatchesOK = checkMomentPreconditionMinMatch(oLastMomentInShortTimeMemory, poTotalList);
		if (oPartiallyMatchesOK==true) {
			oRetVal = oLastMomentInShortTimeMemory;
			return oRetVal;
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
	private boolean checkMomentPreconditionMinMatch(clsDataStructureContainerPair oLastMomentInShortTimeMemory, ArrayList<clsDataStructureContainer> poTotalList) {
		boolean oRetVal = false;
		
		//Get the Primary container in the list of current matches from the last known moment
		clsPrimaryDataStructureContainer oLastMomentPriContainer = clsDataStructureTools.extractPrimaryContainer(oLastMomentInShortTimeMemory.getSecondaryComponent(), poTotalList);
		double oLastMomentMatch = 0.0;
		//If the container !=null, then get the match value with PI
		if (oLastMomentPriContainer!=null) {
			oLastMomentMatch = clsDataStructureTools.getMatchValueToPI(oLastMomentPriContainer);
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
			ArrayList<clsSecondaryDataStructure> oPlausibleExpList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oLastMomentInShortTimeMemory.getSecondaryComponent(), moPredicateTemporal, false);
			for (clsSecondaryDataStructure oS : oPlausibleExpList) {
				clsSecondaryDataStructureContainer oSecondaryContainer = (clsSecondaryDataStructureContainer) clsDataStructureTools.getContainerFromList(poTotalList, oS);
				if (oSecondaryContainer!=null) {
					clsPrimaryDataStructureContainer oPrimaryContainer = clsDataStructureTools.extractPrimaryContainer(oSecondaryContainer, poTotalList);
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
	private clsDataStructureContainerPair getBestMatchSubImage(clsSecondaryDataStructureContainer poIntention, ArrayList<clsDataStructureContainer> poSourceList, double prMomentActivationThreshold) {
		clsDataStructureContainerPair oRetVal = null;
		double rMaxValue = 0.0;
				
		ArrayList<clsSecondaryDataStructure> oSubImages = clsDataStructureTools.getDSFromSecondaryAssInContainer(poIntention, moPredicateHierarchical, true);
		
		for (clsDataStructurePA oDS : oSubImages) {
			//Get the Image DS
			//Find the corresponding WP-container
			clsSecondaryDataStructureContainer oCurrentSituationWPContainer = null;
			for (clsDataStructureContainer oSContainer : poSourceList) {
				if ((oSContainer instanceof clsSecondaryDataStructureContainer) && (oDS.getMoDS_ID() == oSContainer.getMoDataStructure().getMoDS_ID())) {
					oCurrentSituationWPContainer = (clsSecondaryDataStructureContainer) oSContainer;
					break;
				}
			}
			
			//If this container could be found, search for the primary Data structure container
			clsPrimaryDataStructureContainer oPContainer = null;
			if (oCurrentSituationWPContainer!=null) {
				oPContainer = clsDataStructureTools.extractPrimaryContainer(oCurrentSituationWPContainer, poSourceList);
			}
			
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
	private void getExpectations(ArrayList<clsPrediction> poActList, ArrayList<clsDataStructureContainer> poInput) {
		for (clsPrediction oActTripple : poActList) {
			clsSecondaryDataStructureContainer oIntention = oActTripple.getIntention().getSecondaryComponent();
			clsSecondaryDataStructureContainer oCurrentSituation = null;
			if (oIntention!=null) {
				oCurrentSituation = oActTripple.getMoment().getSecondaryComponent();
			}
			
			//3. Find the expectation in each Perception-act
			//Do it with clsAssociationSecondary and ISA Intention and HASNEXT as leaf element of the association with the
			//current situation
			if (oCurrentSituation!=null) {
				//Get all associations with "HASNEXT" from the current situation
				ArrayList<clsSecondaryDataStructure> oExpectationElement = clsDataStructureTools.getDSFromSecondaryAssInContainer(oCurrentSituation, moPredicateTemporal, false);
				
				for (clsSecondaryDataStructure oSecDS : oExpectationElement) {
					clsSecondaryDataStructureContainer oPossibleExpectation = (clsSecondaryDataStructureContainer) clsDataStructureTools.getContainerFromList(poInput, oSecDS);
				
					if (oPossibleExpectation==null) {
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
					ArrayList<clsSecondaryDataStructure> oIntentionFromPossibleExp = clsDataStructureTools.getDSFromSecondaryAssInContainer(oCurrentSituation, moPredicateHierarchical, false);
					
					for (clsSecondaryDataStructure oIntentionInAss : oIntentionFromPossibleExp) {
						if (oIntentionInAss.getMoDS_ID() == oIntention.getMoDataStructure().getMoDS_ID()) {
							//Get the primary structure for this expectation
							clsPrimaryDataStructureContainer oPExpectation = clsDataStructureTools.extractPrimaryContainer(oPossibleExpectation, poInput);
							//The expectation has been found and confirmed
							//Set the classification
							if (oPossibleExpectation!=null) {
								clsDataStructureTools.setClassification(oPossibleExpectation, this.moObjectClassEXPECTATION);
							}
							
							//Add the secondary and the primary (if available) to the expectations
							oActTripple.getExpectations().add(new clsDataStructureContainerPair(oPossibleExpectation, oPExpectation));
						}
					}
					
				}
				
				//TODO AW: Remove if the module is working
				/*for (clsAssociation oCSAss : oCurrentSituation.getMoAssociatedDataStructures()) {
					if (oCSAss instanceof clsAssociationSecondary) {
						if (oCSAss.getRootElement().getMoDS_ID() == oCurrentSituation.getMoDataStructure().getMoDS_ID() && 
								((clsAssociationSecondary)oCSAss).getMoPredicate() == oPredicateTemporal) {
							//Get the purposed container of the expectation
							clsSecondaryDataStructureContainer oPossibleExpectation = (clsSecondaryDataStructureContainer) clsDataStructureTools.getContainerFromList(poInput, oCSAss.getLeafElement());
							//Confirm, that this expectation belongs to this perception-act
							if (oPossibleExpectation==null) {
								try {
									throw new Exception("Code/Protege error in F51_RealityCheckWishFulfillment, setCurrentExpectation: " +
											"No expectation found, although it should be found. This error occurs if the intention " +
											"does not have any of the objects, which is contained in one expectation, e. g. a current situation contains a WALL and " + 
											"the intention does not");
								} catch (Exception e) {
									// TODO (wendt) - Auto-generated catch block
									e.printStackTrace();
								}
							}
							
							
							for (clsAssociation oAss : oPossibleExpectation.getMoAssociatedDataStructures()) {
								if (oAss.getLeafElement().getMoDS_ID() == oIntention.getMoDataStructure().getMoDS_ID()) {
									//Get the primary structure for this expectation
									clsPrimaryDataStructureContainer oPExpectation = clsDataStructureTools.extractPrimaryContainer(oPossibleExpectation, poInput);
									//Add the secondary and the primary (if available) to the expectations
									oActTripple.getExpectations().add(new clsDataStructureContainerPair(oPossibleExpectation, oPExpectation));
								}
							}
							
						}
					}
					
				}*/
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
		send_I6_7(moEnvironmentalPerception_OUT, moExtractedPrediction_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:51:11
	 * 
	 * @see pa.interfaces.send.I2_13_send#send_I2_13(java.util.ArrayList)
	 */
	@Override
	public void send_I6_7(clsDataStructureContainerPair poRealityPerception,
			ArrayList<clsPrediction> poExtractedPrediction) {
		((I6_7_receive)moModuleList.get(26)).receive_I6_7(poRealityPerception, poExtractedPrediction);
		
		putInterfaceData(I6_7_send.class, poRealityPerception, poExtractedPrediction);
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

