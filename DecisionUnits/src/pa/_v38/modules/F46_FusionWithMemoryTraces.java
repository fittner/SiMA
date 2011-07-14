/**
 * E46_FusionWithMemoryTraces.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;

import bfg.tools.clsMutableDouble;
import pa._v38.tools.clsGlobalFunctions;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I5_6_receive;
import pa._v38.interfaces.modules.I5_6_send;
import pa._v38.interfaces.modules.I2_6_receive;
import pa._v38.interfaces.modules.I5_19_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureConverter;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eDataType;

import config.clsBWProperties;
import du.enums.pa.eContext;

/**
 * Association of TPMs (TP + Emotion, fantasies) with thing presentations raw data (from external perception). 
 * In a first step these are attached with a value to get a meaning. Secondly the fantasies are added from 
 * the TPMs to the thing presentations 
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 * 
 */
public class F46_FusionWithMemoryTraces extends clsModuleBaseKB implements
					I2_6_receive, I5_19_receive, I5_6_send {
	public static final String P_MODULENUMBER = "46";
	
	/* Inputs */
	/** Here the associated memory from the planning is put on the input to this module */
	private clsPrimaryDataStructureContainer moReturnedTPMemory_IN; 
	/** Input from perception */
	private ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalPerception_IN;
	
	/* Output */
	/** A Perceived image incl. DMs */
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_OUT;
	/** Activated memories together with their DMs */
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;

	/* Module-Parameters */
	
	/**
	 * DOCUMENT (HINTERLEITNER) - Association of TPMs (TP + Emotion, fantasies) with thing presentations 
	 * raw data (from external perception). In a first step these are attached with a value to get a meaning. 
	 * Secondly the fantasies are added from the TPMs to the thing presentations
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:16:50
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F46_FusionWithMemoryTraces(String poPrefix, clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, 
								clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		applyProperties(poPrefix, poProp);	
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.valueToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);		
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);		
		
		return text;
	}		
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
		//Variables
		clsPrimaryDataStructureContainer oEnvPerceptionNoDM;
		ArrayList<clsPrimaryDataStructureContainer> oContainerWithTypes;
		
		/* Construction of perceived images*/
		/* Assign objects from storage to perception */
		
		oContainerWithTypes = retrieveImages(moEnvironmentalPerception_IN);
		oEnvPerceptionNoDM = clsDataStructureConverter.convertTPMContToTICont(clsGlobalFunctions.createInstanceFromType(oContainerWithTypes));
		/* Assign drive meshes and adapt categories */
		//Assign drivemeshes to the loaded images
		assignDriveMeshes(oEnvPerceptionNoDM);
		adaptCategories(oEnvPerceptionNoDM);
		
		
		moEnvironmentalPerception_OUT = oEnvPerceptionNoDM;	//The output is a perceived image
		
		/* Perception - Activation of associated memories */
		moAssociatedMemories_OUT = retrieveActivatedMemories(moEnvironmentalPerception_OUT, moReturnedTPMemory_IN);
		

		ArrayList<clsPrimaryDataStructureContainer> oContainerList = new ArrayList<clsPrimaryDataStructureContainer>(); 
 		//oContainerList = clsDataStructureConverter.convertTIContToTPMCont(moEnvironmentalPerception_IN); 
		
	    addValues(oContainerList);			
		attachFantasies(oContainerList);	//Siehe retrieveActivatedMemories
		//Fantasiertes wird an die TP (Sachvorstellungen) angeh�ngt
		
	}
	
	

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 28.06.2011 14:32:48
	 *
	 * @param oContainerList
	 */
	private void attachFantasies(
			ArrayList<clsPrimaryDataStructureContainer> oContainerList) {
		//This function is alread done in retrieveActivatedMemories
		// TODO (hinterleitner) - Auto-generated method stub
		
	}

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 28.06.2011 14:32:45
	 *
	 * @param oContainerList
	 */
	private void addValues(
			ArrayList<clsPrimaryDataStructureContainer> oContainerList) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (HINTERLEITNER) - Auto-generated method stub
		}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (HINTERLEITNER) - Auto-generated method stub

	}
	
	/**
	 * DOCUMENT (zeilinger) - retrieves memory matches for environmental input; objects are identified and exchanged by their 
	 * stored correspondance.
	 *
	 * @author zeilinger
	 * 14.03.2011, 14:27:30
	 *
	 */
	private ArrayList<clsPrimaryDataStructureContainer> retrieveImages(ArrayList<clsPrimaryDataStructureContainer> oPerceivedImage_IN) {
		ArrayList<clsPrimaryDataStructureContainer> oRetVal;
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		
		search(eDataType.TP, oPerceivedImage_IN, oSearchResult ); 
		oRetVal = createImage(oSearchResult);
		
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 28.06.2011 14:18:54
	 *
	 * @param oContainerList
	 */
	/**
	 * Find DMs for objects in perception and assign them through associations.
	 * The elements in the perception then have AttributeAssociations and
	 * DriveMeshAssociations.
	 *
	 * @author Marcus Zottl (e0226304),
	 * 22.06.2011, 18:28:52
	 *
	 * @param poPerception - the perception to which DMs should be assigned
	 * 
	 * @see F35_EmersionOfBlockedContent#search(eDataType, clsPrimaryDataStructureContainer)
	 * @see F35_EmersionOfBlockedContent#addAssociations(ArrayList, clsPrimaryDataStructureContainer)
	 */
	private void assignDriveMeshes(clsPrimaryDataStructureContainer poPerception) {
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
	
		oSearchResult = search(eDataType.DM, poPerception);

		addAssociations(oSearchResult, poPerception);
	}
	
	/**
	 * Searches the KnowledgeBase for associated elements of DataType
	 * for the perception.
	 *
	 * @author Marcus Zottl (e0226304)
	 * 15.06.2011, 18:49:27
	 *
	 * @param poDataType		- the DataType you are looking for
	 * @param poPerception	- the perception for which you want to find something 
	 * @return							- the result of a MemorySearch in the KnowledgeBase
	 */
	public ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> search(
			eDataType poDataType, 
			clsPrimaryDataStructureContainer poPerception) {
		
		ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult =
			new ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>>();
		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern =
			new ArrayList<clsPair<Integer,clsDataStructurePA>>(); 

		oSearchPattern = createSearchPattern(poDataType, poPerception);
		poSearchResult.addAll(
				moKnowledgeBaseHandler.initMemorySearch(oSearchPattern));
		
		//Set Instance values
		for (ArrayList<clsPair<Double, clsDataStructureContainer>> oStructure : poSearchResult) {
			for (clsPair<Double, clsDataStructureContainer> oMatchingData : oStructure) {
				int iInstID = oMatchingData.b.getMoDataStructure().getMoDSInstance_ID();
				if (iInstID != 0) {
					for (clsAssociation oAss : oMatchingData.b.getMoAssociatedDataStructures()) {
						oAss.getRootElement().setMoDSInstance_ID(iInstID);
					}
				}
			}
		}
		
		return poSearchResult;
	}
	
	/**
	 * Creates a search pattern for a perception and a DataType that is used to
	 * access the KnowledgeBase to find content.
	 *
	 * @author Marcus Zottl (e0226304)
	 * 15.06.2011, 16:54:48
	 *
	 * @param poDataType		- the DataType you are looking for
	 * @param poPerception	- the perception for which you need a search pattern
	 * @return							- a search pattern to access the KnowledgeBase
	 */
	public ArrayList<clsPair<Integer, clsDataStructurePA>> createSearchPattern(
			eDataType poDataType,
			clsPrimaryDataStructureContainer poPerception) {
		
		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern =
			new ArrayList<clsPair<Integer, clsDataStructurePA>>();
		
		// if the moDataStructure of the Container is not a TI there is something really wrong!
		clsTemplateImage oDS = (clsTemplateImage) poPerception.getMoDataStructure();
		// add each element of the TI to the searchPattern
			for (clsAssociation oEntry : oDS.getMoAssociatedContent()) {
				oSearchPattern.add(
						new clsPair<Integer, clsDataStructurePA>(
						poDataType.nBinaryValue, oEntry.getLeafElement()));								
			}
		return oSearchPattern;
	}
	
	/**
	 * This method adds the associated items from the search result to the
	 * associatedDataStructures of the (perception) container.
	 *
	 * @author Marcus Zottl (e0226304)
	 * 22.06.2011, 18:29:38
	 *
	 * @param poSearchResult	- the result of a MemorySearch in the KnowledgeBase 
	 * @param poPerception		- the perception to which the items in the search
	 * result should be added.
	 */
	private void addAssociations(
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult,
			clsPrimaryDataStructureContainer poPerception) {

		//oEntry: Data structure with a double association weight and an object e. g. CAKE with its associated DM.
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult) {
			if(oEntry.size() > 0){
				//get associated DM from a the object e. g. CAKE
				ArrayList<clsAssociation> oAssociationList = oEntry.get(0).b.getMoAssociatedDataStructures();
				//Add associated DM to the input list. Now the list moAssociatedDataStructures contains DM and ATTRIBUTES
				poPerception.getMoAssociatedDataStructures().addAll(oAssociationList);
			}
		}
	}
	
	/**
	 * Adapts the categories of DriveMeshes in the perception according to a
	 * context.
	 *
	 * @author Marcus Zottl (e0226304)
	 * 17.06.2011, 19:00:31
	 *
	 * @param poPerception_IN	- the perception that needs its DriveMesh categories
	 * adjusted
	 */
	private void adaptCategories(clsPrimaryDataStructureContainer poPerception_IN) {
		HashMap<clsPrimaryDataStructureContainer, clsMutableDouble> oContextResult;

		oContextResult = getContext();
		for(Map.Entry<clsPrimaryDataStructureContainer, clsMutableDouble> oContextPrim : oContextResult.entrySet()) {
				calculateNewCategories(oContextPrim, poPerception_IN); 
		}
	}
	
	/**
	 * The context of a certain drive or object is loaded.<br>
	 * <br>
	 * In the case of CAKE it is NOURISH. If the drive NOURISH is found in the
	 * objects, the categories (anal, oral...) are multiplied with a category
	 * factor <= 1.
	 *
	 * @author Marcus Zottl (e0226304)
	 * 17.06.2011, 19:03:27
	 *
	 * @param poContextPrim
	 */
	private void calculateNewCategories(
			Entry<clsPrimaryDataStructureContainer, clsMutableDouble> poContextPrim,
			clsPrimaryDataStructureContainer poPerception) {

		//Get the context of the object. In the case of the CAKE, NOURISH
		eContext oContext =
			eContext.valueOf(
					poContextPrim.getKey().getMoDataStructure().getMoContentType());

		for(clsAssociation oAssociation : poPerception.getMoAssociatedDataStructures()) {
			clsDataStructurePA oData = oAssociation.getLeafElement();
			//only process DMs
			if(oData instanceof clsDriveMesh){
				//If the drive is equal to the drive in the context then...
				if(eContext.valueOf(oData.getMoContentType()).equals(oContext)){
					/*setCathegories has the following Parameters:
					 * The DM with their categories anal, oral, phallic and genital and the context value
					 * For the CAKE with the drive NOURISH, the context value is = 1.0 as the thing can be 
					 * eaten. The categories are multiplied with the context value.
					 * This function reduces not 100%-Matching context with the context factor. If the 
					 * context matches to 100%, then the context factor is = 1.0
					 */
					setCategories((clsDriveMesh)oData, poContextPrim.getValue().doubleValue()); 
				}
			}
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 18:13:00
	 *
	 * @param oDM
	 * @param doubleValue
	 */
	private void setCategories(clsDriveMesh poDM, double prContextValue) {
		poDM.setAnal(poDM.getAnal() * prContextValue); 
		poDM.setGenital(poDM.getGenital() * prContextValue);
		poDM.setOral(poDM.getOral() * prContextValue); 
		poDM.setPhallic(poDM.getPhallic() * prContextValue);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 26.08.2010, 12:02:45
	 *
	 * @return
	 */
	private HashMap<clsPrimaryDataStructureContainer, clsMutableDouble> getContext() {
		//return moMemory.moCurrentContextStorage.getContextRatiosPrimCONVERTED(mrContextSensitivity);
		return new HashMap<clsPrimaryDataStructureContainer, clsMutableDouble>();
	}
	
	
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 14.07.2011 15:15:31
	 *
	 * @param oPerceptionInput
	 * @param oReturnedMemory
	 * @return
	 */
	private ArrayList<clsPrimaryDataStructureContainer> retrieveActivatedMemories(clsPrimaryDataStructureContainer oPerceptionInput, 
			clsPrimaryDataStructureContainer oReturnedMemory) {
		
		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();
		boolean blUsePerception = true;
		
		//Associated memories
		//Decide which image will be the input for spread activation
		if (oReturnedMemory!=null) {
			if (clsGlobalFunctions.calculateAbsoluteAffect(oPerceptionInput) < clsGlobalFunctions.calculateAbsoluteAffect(oReturnedMemory)) {
				blUsePerception = false;
			}
		}
		
		ArrayList<clsPair<Double,clsDataStructureContainer>> oSearchResultContainer = new ArrayList<clsPair<Double,clsDataStructureContainer>>();
		if (blUsePerception==true) {
			//Use perceived image as input of spread activation
			//TODO AW: Only the first
			searchContainer(oPerceptionInput, oSearchResultContainer);
			//
			//oRetVal.add(new clsPrimaryDataStructureContainer(clsDataStructureGenerator.generateTI(oContent), new ArrayList<clsAssociation>()));
			//TODO AW: All activated matches are added to the list. Here, spread activation shall be used
		} else {
			//Use action-plan image as input of spread activation
			//TODO: This is only the first basic implementation of activation of phantsies
			
			searchContainer(oReturnedMemory, oSearchResultContainer);
			
			//clsTripple<String, ArrayList<clsPhysicalRepresentation>, Object> oContent = new clsTripple<String, ArrayList<clsPhysicalRepresentation>, Object>("DummyMemory", new ArrayList<clsPhysicalRepresentation>(), "DummyMemory");
			//oRetVal.add(new clsPrimaryDataStructureContainer(clsDataStructureGenerator.generateTI(oContent), new ArrayList<clsAssociation>()));
		}
		
		for (clsPair<Double,clsDataStructureContainer> oAss : oSearchResultContainer) {
			oRetVal.add((clsPrimaryDataStructureContainer)oAss.b);
		}
		
		return oRetVal;
	}

	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 14.03.2011, 23:06:52
	 *
	 */
	private ArrayList<clsPrimaryDataStructureContainer> createImage(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult) {
		
		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
		
				int nEntryIndex = poSearchResult.indexOf(oEntry); 
				clsPrimaryDataStructureContainer oNewImage;
				clsPrimaryDataStructureContainer oPerceptionEntry =  moEnvironmentalPerception_IN.get(nEntryIndex); 
				
				if(oEntry.size() > 0){
					oNewImage = (clsPrimaryDataStructureContainer)extractBestMatch(oEntry); 
					oNewImage.setMoAssociatedDataStructures(oPerceptionEntry.getMoAssociatedDataStructures()); 
					mergePerceptionAndKnowledge(oNewImage, oPerceptionEntry);
					
					oRetVal.add(oNewImage);
				}
			}
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 20:39:09
	 *
	 * @param oSearchResult
	 * @param poEnvironmentalInput
	 */
	private void mergePerceptionAndKnowledge(clsPrimaryDataStructureContainer poNewImage,
												   clsPrimaryDataStructureContainer poPerceptionEntry) {

		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		ArrayList<clsDataStructurePA> oAssociatedElements = new ArrayList<clsDataStructurePA>(); 
		
		if(poNewImage.getMoDataStructure() instanceof clsThingPresentationMesh 
			&& poPerceptionEntry.getMoDataStructure() instanceof clsThingPresentationMesh){

			extractUnknownData(oAssociatedElements, poPerceptionEntry, poNewImage); 
			search(eDataType.UNDEFINED, oAssociatedElements, oSearchResult); 
		 	addAttributeAssociations(oSearchResult, poNewImage); 
		 }
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.03.2011, 10:27:04
	 *
	 * @param oUnknownData
	 * @param poPerceptionEntry
	 * @param poNewImage 
	 */
	private void extractUnknownData(ArrayList<clsDataStructurePA> poUnknownData,
			clsPrimaryDataStructureContainer poPerceptionEntry, 
			clsPrimaryDataStructureContainer poNewImage) {
		
		for(clsAssociation oEntry : ((clsThingPresentationMesh)poPerceptionEntry.getMoDataStructure()).getMoAssociatedContent()){
	 		
	 		if( !((clsThingPresentationMesh)poNewImage.getMoDataStructure()).contain(oEntry.getMoAssociationElementB())){
	 			poUnknownData.add(oEntry.getMoAssociationElementB()); 
	 		}
	 	}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.03.2011, 10:25:24
	 *
	 * @param oSearchResult
	 * @param poNewImage
	 */
	private void addAttributeAssociations(ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult,
						clsPrimaryDataStructureContainer poNewImage) {
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
			if(oEntry.size() > 0){
				clsPrimaryDataStructureContainer oBestMatch = (clsPrimaryDataStructureContainer)extractBestMatch(oEntry); 
				clsAssociation oAssociation = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(
							-1, eDataType.ASSOCIATIONATTRIBUTE, eDataType.ASSOCIATIONATTRIBUTE.name()), 
							(clsPrimaryDataStructure)poNewImage.getMoDataStructure(), 
							(clsPrimaryDataStructure)oBestMatch.getMoDataStructure());
				poNewImage.getMoAssociatedDataStructures().add(oAssociation);
			}
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 14.03.2011, 23:08:28
	 *
	 * @param oEntry
	 * @return
	 */
	private clsDataStructureContainer extractBestMatch(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry) {
		
		clsDataStructureContainer oBestMatch = oEntry.get(0).b;; 
		
		return oBestMatch; 
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_6(moEnvironmentalPerception_OUT, moAssociatedMemories_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v38.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v38.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.send._v38.I2_20_send#receive_I2_20(java.util.ArrayList)
	 */
	@Override
	public void send_I5_6(clsPrimaryDataStructureContainer poEnvironmentalTP, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		/* The inputs and outputs can be changed if the following parameters are changed:
		 * clsModuleBase.deepcopy
		 * this function
		 * the receive function in the following module
		 * I5_6_send.java
		 * I5_6_receive.java
		 */
		//Give output to input of F37
		((I5_6_receive)moModuleList.get(37)).receive_I5_6(poEnvironmentalTP, poAssociatedMemories);
		putInterfaceData(I5_6_send.class, poEnvironmentalTP, poAssociatedMemories);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.receive._v38.I7_7_receive#receive_I7_7(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_19(clsPrimaryDataStructureContainer poReturnedMemory) {
		moReturnedTPMemory_IN = (clsPrimaryDataStructureContainer)poReturnedMemory.clone();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.receive._v38.I2_5_receive#receive_I2_5(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_6(
			ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalPerception) {
		
		moEnvironmentalPerception_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poEnvironmentalPerception); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Association of TPMs (TP + Emotion, fantasies) with thing presentations raw data (from external perception). In a first step these are attached with a value to get a meaning. Secondly the fantasies are added from the TPMs to the thing presentations";
	}		
}
