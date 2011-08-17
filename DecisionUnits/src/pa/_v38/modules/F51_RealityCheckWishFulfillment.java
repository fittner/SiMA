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
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.tools.clsDataStructureTools;
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
	@Override
	protected void process_basic() {
		//moRealityPerception_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
		
		//FIXME HZ 2010.08.24 Functionality of old code is taken; however I am rather sure that it has to be
		//adapted
		//for(clsSecondaryDataStructureContainer oCon : moFocusedPerception_Input){
		//	moRealityPerception_Output.add(oCon); 
		//}
		
		//FIXME AW: Should anything be done with the perception here?
		moRealityPerception_Output = (ArrayList<clsDataStructureContainer>)deepCopy(moFocusedPerception_Input);
		
		moExtractedPrediction_OUT = extractPredictions(moAssociatedMemoriesSecondary_IN);
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
		
		
		//3. Remove all predictions, where there is no current moment for an intention
		cleanPredictions(oRetVal);
		
		//4. Find the expectation in each Perception-act
		//Do it with clsAssociationSecondary and ISA Intention and HASNEXT as leaf element of the association with the
		//current situation
		setCurrentExpectation(oRetVal, poInput);
		
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
			clsSecondaryDataStructureContainer oSMoment = getBestMatchSubImage(oSubImageAss, poInput, prMomentActivationThreshold);
			clsPrimaryDataStructureContainer oPMoment = clsDataStructureTools.extractPrimaryContainer(oSMoment, poInput);
			oActTripple.getMoment().setSecondaryComponent(oSMoment);
			oActTripple.getMoment().setPrimaryComponent(oPMoment);
			//System.out.print("");
		}
		
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
	private clsSecondaryDataStructureContainer getBestMatchSubImage(ArrayList<clsAssociation> poIntentionAssociations, ArrayList<clsDataStructureContainer> poSourceList, double prMomentActivationThreshold) {
		clsSecondaryDataStructureContainer oRetVal = null;
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
				double rMatchValue = getMatchValueToPI(oPContainer);
				//If the new value is the highest value and it is higher than the threshold value
				if ((rMatchValue > rMaxValue) && (rMatchValue >= prMomentActivationThreshold)) {
					oRetVal = oCurrentSituationWPContainer;
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 04.08.2011 13:59:56
	 *
	 * @param poImageContainer
	 * @return
	 */
	private double getMatchValueToPI(clsPrimaryDataStructureContainer poImageContainer) {
		String oContent = "PERCEPTION";
		double rRetVal = 0.0;
		
		for (clsAssociation oAss : poImageContainer.getMoAssociatedDataStructures()) {
			if (oAss instanceof clsAssociationPrimary) {
				if ((oAss.getMoAssociationElementA() instanceof clsTemplateImage) || (oAss.getMoAssociationElementB() instanceof clsTemplateImage)) {
					if ((((clsTemplateImage)oAss.getMoAssociationElementA()).getMoContent() == oContent) || (((clsTemplateImage)oAss.getMoAssociationElementB()).getMoContent() == oContent)) {
						rRetVal = oAss.getMrWeight();
					}
				}
					
			}
		}
		return rRetVal;
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
		String oPredicateTemporal = "HASNEXT";
		//String oPredicateIsA = "ISA";
		
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
				for (clsAssociation oCSAss : oCurrentSituation.getMoAssociatedDataStructures()) {
					if (oCSAss instanceof clsAssociationSecondary) {
						if (oCSAss.getRootElement().getMoDS_ID() == oCurrentSituation.getMoDataStructure().getMoDS_ID() && 
								((clsAssociationSecondary)oCSAss).getMoPredicate() == oPredicateTemporal) {
							//Get the purposed container of the expectation
							clsSecondaryDataStructureContainer oPossibleExpectation = (clsSecondaryDataStructureContainer) clsDataStructureTools.getContainerFromList(poInput, oCSAss.getLeafElement());
							//Confirm, that this expectation belongs to this perception-act
							if (oPossibleExpectation==null) {
								try {
									throw new Exception("Code/Protege error in F51_RealityCheckWishFulfillment, setCurrentExpectation: No expectation found, although it should be found.");
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
					
				}
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

