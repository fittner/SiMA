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
	private ArrayList<clsDataStructureContainer> moFocusedPerception_Input;  
	/** Container of activated associated memories */
	private ArrayList<clsDataStructureContainer> moAssociatedMemoriesSecondary_IN;
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:49 */
	private ArrayList<clsDataStructureContainer> moRealityPerception_Output; 
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:50 */
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList;  //removed by HZ - not required now
	/** A construction of an Intention, an arraylist with expectations and the current situation */
	private ArrayList<clsPrediction> moExtractedPrediction_OUT;
	
	/** A threshold for images, which are only set moment if the match factor is higher or equal this value */
	private double mrMomentActivationThreshold = 0.8;
	private double mrMomentMinRelevanceThreshold = 0.2;
	private int mnMaxTimeValue = 8;
	
	
	private String moPredicateTemporal = "HASNEXT";
	private String moPredicateHierarchical = "ISA";
	//private String moPredicateTimeValue = "HASTIMEVALUE";	//not necessary now
	
	
	//FIXME AW: This is a cheat function, which is used until a short time memory is defined. Here, expectations are put
	//TODO: Create real short time memory
	private clsPair<Integer, clsDataStructureContainerPair> moShortTimeMemory;
	
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
		
		text += toText.listToTEXT("moFocusedPerception_Input", moFocusedPerception_Input);
		text += toText.listToTEXT("moAssociatedMemoriesSecondary_IN", moAssociatedMemoriesSecondary_IN);
		text += toText.listToTEXT("moRealityPerception_Output", moRealityPerception_Output);
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
	public void receive_I6_6(ArrayList<clsDataStructureContainer> poFocusedPerception, ArrayList<clsSecondaryDataStructureContainer> poDriveList, 
			ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary) {
		moFocusedPerception_Input = (ArrayList<clsDataStructureContainer>)deepCopy(poFocusedPerception);
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
		updateShortTimeMemory();
		
		//FIXME AW: Should anything be done with the perception here?
		moRealityPerception_Output = (ArrayList<clsDataStructureContainer>)deepCopy(moFocusedPerception_Input);
		
		moExtractedPrediction_OUT = extractPredictions(moAssociatedMemoriesSecondary_IN);
		
	}
	
	/**
	 * Update the time value of the short time memory
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 19.08.2011 21:42:29
	 *
	 */
	private void updateShortTimeMemory() {
		if (moShortTimeMemory!=null) {
			moShortTimeMemory.a++;
			if (moShortTimeMemory.a>mnMaxTimeValue) {
				clearShortTimeMemory();
			}
		}
	}
	
	/**
	 * Save a data structure to the short time memory
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 19.08.2011 14:32:56
	 *
	 * @param poInput
	 */
	private void saveToShortTimeMemory(clsDataStructureContainerPair poInput, boolean forceSave) {
		//save only if this moID is not already saved
		
		if (((moShortTimeMemory!=null) && 
				(moShortTimeMemory.b.getSecondaryComponent()!=null) &&
				(poInput.getSecondaryComponent().getMoDataStructure().getMoDS_ID() != moShortTimeMemory.b.getSecondaryComponent().getMoDataStructure().getMoDS_ID())) ||
				(moShortTimeMemory==null) || 
				(forceSave==true)) {
					//Replace structure and set the time value
					try {
						clsDataStructureContainerPair oAddPair = (clsDataStructureContainerPair) poInput.clone();
						moShortTimeMemory = new clsPair<Integer, clsDataStructureContainerPair>(0, oAddPair);
					} catch (CloneNotSupportedException e) {
						// TODO (wendt) - Auto-generated catch block
						e.printStackTrace();
					}
		}
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 19.08.2011 14:32:58
	 *
	 */
	private void clearShortTimeMemory() {
		moShortTimeMemory = null;
	}
	
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
		
		//If there is no intention, then the short time memory is cleared
		manageIntentionEvents(oRetVal);
		
		//2. Find the current situation in each Perception-act
		//Do it hasAssociation, which is instanceof clsAssociationPrimary with Perceived Image and 
		//with the highest association weight
		//2.a Get all current situations, which are correspsonding to the intentions
		/* 2.b A current situation has the following:
		 * - For an intention, the highest match value of the associationPrimary
		 */
		setCurrentSituation(oRetVal, poInput, mrMomentActivationThreshold);
		//2.c Check extrapolation of values
		//XXXX
		
		extrapolateCurrentSituation(oRetVal, moShortTimeMemory);
		
		
		//3. Remove all predictions, where there is no current moment for an intention
		cleanPredictions(oRetVal);
		
		//4. Find the expectation in each Perception-act
		//Do it with clsAssociationSecondary and ISA Intention and HASNEXT as leaf element of the association with the
		//current situation
		setCurrentExpectation(oRetVal, poInput);
		
		return oRetVal;
	}
	
	/**
	 * Temporary short time memory function, which shall clear the short time memory, i. e. the last moment if the intention
	 * cannot be found. It shall prevent the acting upon expectation, if the intention has been lost.
	 * 
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 19.08.2011 13:34:31
	 *
	 * @param poInput
	 */
	private void manageIntentionEvents(ArrayList<clsPrediction> poInput) {
		if (poInput.isEmpty()) {
			clearShortTimeMemory();
		}	
	}
	
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
	private void extrapolateCurrentSituation (ArrayList<clsPrediction> poPrediction, clsPair<Integer, clsDataStructureContainerPair> poShortTimeMemory) {
		for (clsPrediction oP : poPrediction) {
			if (oP.getMoment().getSecondaryComponent()==null) {
				if (poShortTimeMemory!=null) {
					oP.getMoment().setSecondaryComponent(poShortTimeMemory.b.getSecondaryComponent());
				}
			}
		}
		
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
			if (oPred.getMoment().getSecondaryComponent()==null) {
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
		clsDataStructureContainerPair oIntention = null;	//Declare it here, in order to use it to find the primary part of it
		
		//Add the secondary data structures
		for (clsDataStructureContainer oContainer : poInput) {
			if (oContainer instanceof clsSecondaryDataStructureContainer) {
				for (clsAssociation oAss : oContainer.getMoAssociatedDataStructures()) {
					//If this container is a leaf element of an associationsecondary with the predicate ISA
					if (oAss instanceof clsAssociationSecondary) {
						if (((clsAssociationSecondary)oAss).getMoPredicate().equals("ISA") && (oAss.getLeafElement().getMoDS_ID() == oContainer.getMoDataStructure().getMoDS_ID())) {
							
							//Add the belonging primary data structure container
							clsPrimaryDataStructureContainer oPriContainer = clsDataStructureTools.extractPrimaryContainer((clsSecondaryDataStructureContainer)oContainer, poInput);
							
							//The Perception-act is added
							//Add intention ONLY if also a primary data structure container is found
							//DOCUMENT AW: This fact should be documented
							if (oPriContainer!=null) {
								oIntention = new clsDataStructureContainerPair((clsSecondaryDataStructureContainer)oContainer, oPriContainer);
								//Create the act triple							
								clsPrediction oActTripple = new clsPrediction(oIntention, new ArrayList<clsDataStructureContainerPair>(), new clsDataStructureContainerPair(null, null));
								//((new clsPair<clsSecondaryDataStructureContainer)oContainer, clsPrimaryDataStructureContainer>, new ArrayList<clsPair<clsSecondaryDataStructureContainer, clsPrimaryDataStructureContainer>>(), null);
								oRetVal.add(oActTripple);
								break;
							}
						}
					}
				}
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
	private void setCurrentSituation(ArrayList<clsPrediction> poActList, ArrayList<clsDataStructureContainer> poInput, double prMomentActivationThreshold) {
		
		for (clsPrediction oActTripple : poActList) {
			clsSecondaryDataStructureContainer oIntention = oActTripple.getIntention().getSecondaryComponent();
			//Precondition: All structures are already loaded and can be found in the input list
			//Go through each association and search for all children
			ArrayList<clsAssociation> oSubImageAss = getSubImages(oIntention);
			clsDataStructureContainerPair oMoment = getBestMatchSubImage(oSubImageAss, poInput, prMomentActivationThreshold);
			//Check if the last image or the 
						
			//Check temporal order
			clsDataStructureContainerPair oVerifiedMoment = verifyTemporalOrder(oMoment, moShortTimeMemory, poInput);
		
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
	private clsDataStructureContainerPair verifyTemporalOrder (clsDataStructureContainerPair poBestImageMatch, clsPair<Integer, clsDataStructureContainerPair> oLastMomentInShortTimeMemory, ArrayList<clsDataStructureContainer> poTotalList) {
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
		if (oLastMomentInShortTimeMemory==null) {
			saveToShortTimeMemory(poBestImageMatch, false);
			oRetVal = poBestImageMatch;
			return oRetVal;
		}
		
		//*****************************
		
		//Get preconditions
		
		clsSecondaryDataStructureContainer oLastMomentSecondary = oLastMomentInShortTimeMemory.b.getSecondaryComponent();

		//If the last image is found again, it is passed
		if (oProposedMoment!=null && oLastMomentSecondary!=null) {
			if (oProposedMoment.getMoDataStructure().getMoDS_ID() == oLastMomentSecondary.getMoDataStructure().getMoDS_ID()) {
				oRetVal = poBestImageMatch;
				//Force to save the found image to the temporal memory
				saveToShortTimeMemory(poBestImageMatch, true);
				return oRetVal;
			}
		
			//else if the best match is an expectation of the previous moment, then it is ok too
			ArrayList<clsSecondaryDataStructure> oPlausibleExpList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oLastMomentSecondary, moPredicateTemporal, false);
			for (clsSecondaryDataStructure oPlausibleExp :  oPlausibleExpList) {
				if (oPlausibleExp.getMoDS_ID() == oProposedMoment.getMoDataStructure().getMoDS_ID()) {
					oRetVal = poBestImageMatch;
					//Force to save the best match to the temporal memory
					saveToShortTimeMemory(poBestImageMatch, true);
					return oRetVal;
				}
			}
		}
		
		//and if still nothing is found, then use the last possibility
		boolean oPartiallyMatchesOK = checkMomentPreconditionMinMatch(oLastMomentInShortTimeMemory.b, poTotalList);
		if ((oPartiallyMatchesOK==true) && (oLastMomentInShortTimeMemory.a<=mnMaxTimeValue)) {
			oRetVal = oLastMomentInShortTimeMemory.b;
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
	 * Get sub images of a certain intention
	 * (wendt)
	 *
	 * @since 22.07.2011 17:58:37
	 *
	 * @param poIntention
	 * @return
	 */
	private ArrayList<clsAssociation> getSubImages(clsSecondaryDataStructureContainer poIntention) {
		ArrayList<clsAssociation> oRetVal = new ArrayList<clsAssociation>();
		
		for (clsAssociation oAss : poIntention.getMoAssociatedDataStructures()) {
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals("ISA") && (poIntention.getMoDataStructure().getMoDS_ID() == oAss.getLeafElement().getMoDS_ID())) {
					oRetVal.add(oAss);
				}
			}
		}
		//comment
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
	private clsDataStructureContainerPair getBestMatchSubImage(ArrayList<clsAssociation> poIntentionAssociations, ArrayList<clsDataStructureContainer> poSourceList, double prMomentActivationThreshold) {
		clsDataStructureContainerPair oRetVal = new clsDataStructureContainerPair(null, null);
		double rMaxValue = 0.0;
		
		for (clsAssociation oAss : poIntentionAssociations) {
			//Get the Image DS
			clsDataStructurePA oDS = oAss.getRootElement();
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
	private void setCurrentExpectation(ArrayList<clsPrediction> poActList, ArrayList<clsDataStructureContainer> poInput) {
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
									"the intention does not");
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
		send_I6_7(moRealityPerception_Output, moExtractedPrediction_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:51:11
	 * 
	 * @see pa.interfaces.send.I2_13_send#send_I2_13(java.util.ArrayList)
	 */
	@Override
	public void send_I6_7(ArrayList<clsDataStructureContainer> poRealityPerception,
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

