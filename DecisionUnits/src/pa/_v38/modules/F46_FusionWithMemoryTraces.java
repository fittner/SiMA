/**
 * E46_FusionWithMemoryTraces.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;

import bfg.tools.clsMutableDouble;
import pa._v38.storage.clsShortTimeMemory;
import pa._v38.tools.clsDataStructureTools;
import pa._v38.tools.clsAffectTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I5_6_receive;
import pa._v38.interfaces.modules.I5_6_send;
import pa._v38.interfaces.modules.I2_6_receive;
import pa._v38.interfaces.modules.I5_19_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureConverter;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eSupportDataType;

import config.clsProperties;
import du.enums.eDistance;
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
	private ArrayList<clsPrimaryDataStructureContainer> moReturnedTPMemory_IN; 
	/** Input from perception */
	private ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalPerception_IN;
	
	/* Output */
	/** A Perceived image incl. DMs */
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_OUT;
	
	private clsPrimaryDataStructureContainer moEnhancedPerception;
	/** Activated memories together with their DMs */
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;
	
	/** Threshold for matching for associated images */
	private double mrMatchThreshold = 0.1;
	
	/** (wendt) Localitzation of things for the primary process. With the localization, memories can be triggered; @since 15.11.2011 16:23:43 */
	private clsShortTimeMemory moTempLocalizationStorage;

	/* Module-Parameters */
	
	/**
	 * Association of TPMs (TP + Emotion, fantasies) with thing presentations 
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
	public F46_FusionWithMemoryTraces(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, 
								clsKnowledgeBaseHandler poKnowledgeBaseHandler, clsShortTimeMemory poTempLocalizationStorage) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		applyProperties(poPrefix, poProp);
		moTempLocalizationStorage = poTempLocalizationStorage;
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
		text += toText.valueToTEXT("moReturnedTPMemory_IN", moReturnedTPMemory_IN);
		text += toText.valueToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);
		text += toText.valueToTEXT("moEnhancedPerception", moEnhancedPerception);
		text += toText.valueToTEXT("moAssociatedMemories_OUT", moAssociatedMemories_OUT);
		text += toText.valueToTEXT("mrMatchThreshold", mrMatchThreshold);
		text += toText.listToTEXT("moTempLocalizationStorage", moTempLocalizationStorage.getMoShortTimeMemory());
		
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
		
		//Convert LOCATION to DISTANCE and POSITION
		
		
		//Workaround of Bug Eatable/Manipulatable sensors bug
		//TODO CM: Remove this function, as the eatable area objects are working.
		solveBUGFIXEATABLEAREA(moEnvironmentalPerception_IN);
		
		/* Construction of perceived images*/
		/* Assign objects from storage to perception */
		oContainerWithTypes = retrieveImages(moEnvironmentalPerception_IN);
		//Convert all objects to enhanced TPMs 
		
		ArrayList<clsThingPresentationMesh> oCompleteThingPresentationMeshList = retrieveImagesTPM(oContainerWithTypes);
		
		clsThingPresentationMesh oPerceivedImage = clsDataStructureTools.createTPMImage(oCompleteThingPresentationMeshList, eContentType.PERCEPTION.toString(), eContent.PI.toString());
		
		//IMPORTANT NOTE: As the same instance is used in all loaded containers, a deepcopy has to be made in order to 
		//get separate instances for all loaded containers. These instances are then given an instanceID. Until then, the 
		//instanceID has to be 0.
		//@SuppressWarnings("unchecked")
		//ArrayList<clsPrimaryDataStructureContainer> oCopyOfCWT = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(oContainerWithTypes); 
		//IMPORTANT NOTE: Here the instance IDs are set for all TPM-Containers, which will form the perceived image
		//Here the instance values are reset, else there is a provlem that e. g. all WALL have the same instanceID
		//clsDataStructureTools.createInstanceFromTypeList(oCopyOfCWT, true);
		
		//oEnvPerceptionNoDM = clsDataStructureConverter.convertTPMContToTICont(clsDataStructureTools.createInstanceFromTypeList(oCopyOfCWT)); //TD 2011/07/20 - added deepCopy to parameter of function call. deepCopy is removed from createInstanceFromType
		//oEnvPerceptionNoDM = clsDataStructureConverter.convertTPMContToTICont(oCopyOfCWT);
		
		/* Assign drive meshes and adapt categories */
		//Assign drivemeshes to the loaded images
		//assignDriveMeshes(oEnvPerceptionNoDM);
		
		//TODO AW: Remove, as nobody uses this functionality
		//adaptCategories(oEnvPerceptionNoDM);
		
		//Set InstanceIDs for the PI and all its associations
		//clsDataStructureTools.createInstanceFromType(oEnvPerceptionNoDM, false);
		
		//Set Perceived image on the output
		//moEnvironmentalPerception_OUT = oEnvPerceptionNoDM;	//The output is a perceived image
		
		//Create EMPTYSPACE objects
		
		//Add distance relations to the container
		//clsSpatialTools.addRelationAssociations(oEnvPerceptionNoDM, false);
		
		ArrayList<clsThingPresentationMesh> oEmptySpaceList = createEmptySpaceObjects(oPerceivedImage);
		
		//Add those to the PI
		//clsDataStructureTools.addContainersToImage(oEmptySpaceList, oEnvPerceptionNoDM);
		clsDataStructureTools.addTPMToImage(oEmptySpaceList, oPerceivedImage);
		
		ArrayList<clsPrimaryDataStructureContainer> oTPMList = clsDataStructureConverter.convertTPMImageToTPMContainer(oPerceivedImage);
		clsDataStructureTools.createInstanceFromTypeList(oTPMList, true);
		
		oEnvPerceptionNoDM = clsDataStructureConverter.convertTPMContToTICont(oTPMList);
		
		moEnvironmentalPerception_OUT = oEnvPerceptionNoDM;	//The output is a perceived image
		
		/* Perception - Activation of associated memories */
		//FIXME AW This is a hack
		clsPrimaryDataStructureContainer oBestPhantasyInput = null;
		if (moReturnedTPMemory_IN != null) {
			if (moReturnedTPMemory_IN.isEmpty()==false) {
				oBestPhantasyInput = moReturnedTPMemory_IN.get(0);	//This input shall be sorted in F47
			}
		}
		
		//TestIF
		//ArrayList<clsPrimaryDataStructureContainer> x = clsDataStructureConverter.convertTIContToTPMCont(oEnvPerceptionNoDM);
		//Get activated content
		//moAssociatedMemories_OUT = retrieveActivatedMemories(moEnvironmentalPerception_OUT, oBestPhantasyInput);
		//Clone the Output Perception and add knowledge about other objects
		 moEnhancedPerception = enhancePerceptionWithLocalization(moEnvironmentalPerception_OUT, moTempLocalizationStorage);
		
		moAssociatedMemories_OUT = retrieveActivatedMemories(moEnhancedPerception, oBestPhantasyInput);
		//moAssociatedMemories_OUT = retrieveActivatedMemories(moEnvironmentalPerception_OUT, oBestPhantasyInput);
		
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
	 * HACK by AW: This function converts all locations to either DISTANCE or POSITION
	 * 
	 * (wendt)
	 *
	 * @since 01.12.2011 13:36:22
	 *
	 * @param poEnvironmentalPerception
	 */
	private void convertLOCATIONtoPOSITIONandDISTANCE(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalPerception) {
		ArrayList<String> oDistance = new ArrayList<String>();
		oDistance.addAll(Arrays.asList("FAR","MEDIUM","NEAR","MANIPULATEABLE","EATABLE"));
		ArrayList<String> oPosition = new ArrayList<String>();
		oPosition.addAll(Arrays.asList("RIGHT","MIDDLE_RIGHT","CENTER","MIDDLE_LEFT","LEFT"));
		
		/*for (clsPrimaryDataStructureContainer oContainer : poEnvironmentalPerception) {
			for (clsAssociation oAss : oContainer.getMoAssociatedDataStructures()) {
				if (oAss.getLeafElement() instanceof clsThingPresentation) {
					if ()
					
					
					
					
				}
			}
		}*/
		
	}
	
	/**
	 * This is a temporary bugfix for the problem if there are objects in the eatable area. They are not loaded with all attributes and are recognized false
	 * 
	 * (wendt)
	 *
	 * @since 21.11.2011 16:30:06
	 *
	 * @param poEnvironmentalPerception_IN
	 */
	private void solveBUGFIXEATABLEAREA(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalPerception_IN) {
		//Exchange all objects in the EATABLE AREA with the objects in the MANIPULATEABLE AREA
		ArrayList<clsPrimaryDataStructureContainer> oEatableList = new ArrayList<clsPrimaryDataStructureContainer>();
		ArrayList<clsPrimaryDataStructureContainer> oManipulatableList = new ArrayList<clsPrimaryDataStructureContainer>();
		
		//Search in the input for an object with location EATABLE and add them to a new list
		for (clsPrimaryDataStructureContainer oContainer : poEnvironmentalPerception_IN) {
			if (oContainer.getMoDataStructure() instanceof clsThingPresentationMesh) {
				//Go through all associated structures
				for (clsAssociation oAss : ((clsThingPresentationMesh)oContainer.getMoDataStructure()).getMoAssociatedContent()) {
					if (oAss.getLeafElement().getMoContentType().equals("LOCATION")==true && ((clsThingPresentation)oAss.getLeafElement()).getMoContent().equals(eDistance.EATABLE)==true) {
						oEatableList.add(oContainer);
					}
				}
				
			}
		}
		
		//Search for all objects with the area MANIPULATABLE and add them to a new list
		//for (clsPrimaryDataStructureContainer oContainer : poEnvironmentalPerception_IN) {
		//	if (oContainer.getMoDataStructure() instanceof clsThingPresentationMesh) {
		//		//Go through all associated structures
		//		for (clsAssociation oAss : ((clsThingPresentationMesh)oContainer.getMoDataStructure()).getMoAssociatedContent()) {
		//			if (oAss.getLeafElement().getMoContentType().equals("LOCATION")==true && ((clsThingPresentation)oAss.getLeafElement()).getMoContent().equals("MANIPULATABLE")==true) {
		//				oManipulatableList.add(oContainer);
		//			}
		//		}
		//		
		//	}
		//}
		
		//Search for all elements in the EATABLE area for the same content in the MANIPULATABLE area
		for (clsPrimaryDataStructureContainer oEContainer : oEatableList) {
			boolean bFound=false;
			for (clsPrimaryDataStructureContainer oMContainer : poEnvironmentalPerception_IN) {
				//When found, add all TP, which are not location to the EATABLE area
				if (oMContainer.getMoDataStructure() instanceof clsThingPresentationMesh) {
					if (((clsThingPresentationMesh)oEContainer.getMoDataStructure()).getMoContent().equals(((clsThingPresentationMesh)oMContainer.getMoDataStructure()).getMoContent())) {
						for (clsAssociation oAss : ((clsThingPresentationMesh)oMContainer.getMoDataStructure()).getMoAssociatedContent()) {
							if (oAss.getLeafElement().getMoContentType().equals("Color")) {
								((clsThingPresentationMesh)oEContainer.getMoDataStructure()).getMoAssociatedContent().add(oAss);
								bFound=true;
								break;
							}
						}
						
						
					}
				}
				
				if (bFound==true) {
					break;
				}
			}
		}
	}
	
	private clsThingPresentationMesh rotateMesh(clsThingPresentationMesh poInput) {
		return (clsThingPresentationMesh) poInput.getMoAssociatedContent().get(0).getMoAssociationElementB();
	}
	
	
	/**
	 * The PI is enhanced with all objects from the localization, which cannot be seen in the image.
	 * 
	 * (wendt)
	 *
	 * @since 15.11.2011 16:42:41
	 *
	 * @param poPI
	 * @param poTempLocalizationStorage
	 * @return
	 */
	private clsPrimaryDataStructureContainer enhancePerceptionWithLocalization(clsPrimaryDataStructureContainer poPI, clsShortTimeMemory poTempLocalizationStorage) {
		//Clone the PI
		clsPrimaryDataStructureContainer oRetVal = (clsPrimaryDataStructureContainer) poPI.clone();
		
		//Get all objects from the localization
		ArrayList<clsPair<Integer, Object>> oInvisibleObjects = poTempLocalizationStorage.findMemoriesDataType(eSupportDataType.CONTAINERPAIR);
		ArrayList<clsPrimaryDataStructureContainer> oPContainerList = new ArrayList<clsPrimaryDataStructureContainer>();
		
		for (clsPair<Integer, Object> oPair : oInvisibleObjects) {
			//Get the Primary data structure container from that pair
			clsPrimaryDataStructureContainer oPContainer = ((clsDataStructureContainerPair)oPair.b).getPrimaryComponent();
			//Check if this object can be found in the perception
			clsPrimaryDataStructure oFoundObject = (clsPrimaryDataStructure) oRetVal.containsInstanceType(oPContainer.getMoDataStructure());
			//If no such object was found, then add the object to the template image
			if (oFoundObject==null) {
				//Get the associations
				ArrayList<clsAssociation> oAllAssociationAttributes = oPContainer.getAnyAssociatedDataStructures(oPContainer.getMoDataStructure());
				ArrayList<clsAssociation> oSelectedAssociationAttributes = new ArrayList<clsAssociation>();
				for (clsAssociation oAss : oAllAssociationAttributes) {
					//Add no localization attributes
					if (oAss instanceof clsAssociationDriveMesh) {
						oSelectedAssociationAttributes.add(oAss);
					}
				}
				
				//Replate container associations, i. e. remove the locations associations
				oPContainer.setMoAssociatedDataStructures(oSelectedAssociationAttributes);
				//Add the container to the list
				oPContainerList.add(oPContainer);
			}
		}
		
		//Add the containerlist to the PI
		clsDataStructureTools.addContainersToImage(oPContainerList, oRetVal);		
		
		return oRetVal;
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
		
		//Assign TP to the identified object in PerceivedImage_IN
		search(eDataType.TP, oPerceivedImage_IN, oSearchResult); 
		//Take the best match for object
		oRetVal = createImage(oSearchResult);
		
		//Assign drive meshes to each found image
		
		assignDriveMeshes(oRetVal);
		
		return oRetVal;
	}	
	
	private ArrayList<clsThingPresentationMesh> retrieveImagesTPM(ArrayList<clsPrimaryDataStructureContainer> oPerceivedImage_IN) {
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		//ArrayList<clsPrimaryDataStructureContainer> oContainerList  = retrieveImages(oPerceivedImage_IN);
		
		for (clsPrimaryDataStructureContainer oContainer : oPerceivedImage_IN) {
			if (oContainer.getMoDataStructure() instanceof clsThingPresentationMesh) {
				clsThingPresentationMesh oTPM = (clsThingPresentationMesh) oContainer.getMoDataStructure();
				oTPM.setMoExternalAssociatedContent(oContainer.getMoAssociatedDataStructures());
				oRetVal.add(oTPM);
			}
		}
		
		return oRetVal;
	}
	
	
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
	/*private void assignDriveMeshes(clsPrimaryDataStructureContainer poPerception) {
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
	
		//oSearchResult = search(eDataType.DM, poPerception);
		
		search(eDataType.DM, poPerception, oSearchResult);
		addAssociations(oSearchResult, poPerception);
	}*/
	
	/**
	 * Add associations 
	 * wendt
	 *
	 * @since 18.08.2011 11:22:36
	 *
	 * @param poPerception
	 */
	private void assignDriveMeshes(ArrayList<clsPrimaryDataStructureContainer> poPerception) {
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
	
		//oSearchResult = search(eDataType.DM, poPerception);
		
		search(eDataType.DM, poPerception, oSearchResult);
		//for (ArrayList<clsPair<Double,clsDataStructureContainer>> oRes : oSearchResult) {
		addAssociations(oSearchResult, poPerception);
		//}
		//addAssociations(oSearchResult, poPerception);
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
	/*public ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> search(
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
		/*for (ArrayList<clsPair<Double, clsDataStructureContainer>> oStructure : poSearchResult) {
			for (clsPair<Double, clsDataStructureContainer> oMatchingData : oStructure) {
				int iInstID = oMatchingData.b.getMoDataStructure().getMoDSInstance_ID();
				if (iInstID != 0) {
					for (clsAssociation oAss : oMatchingData.b.getMoAssociatedDataStructures()) {
						oAss.getRootElement().setMoDSInstance_ID(iInstID);
					}
				}
			}
		}*/
		
		/*return poSearchResult;
	}*/
	
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
			ArrayList<clsPrimaryDataStructureContainer> poPerception) {

		//oEntry: Data structure with a double association weight and an object e. g. CAKE with its associated DM.
		if (poSearchResult.size()!=poPerception.size()) {
			try {
				throw new Exception("F46: addAssociations, Error, different Sizes");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i=0;i<poSearchResult.size();i++) {
			ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchPair = poSearchResult.get(i);
			clsPrimaryDataStructureContainer oPC = poPerception.get(i);
			if (oSearchPair.size()>0) {
				poPerception.get(i).getMoAssociatedDataStructures().addAll(oSearchPair.get(0).b.getMoAssociatedDataStructures());
			}
			
		}
		
		/*for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult) {
			if(oEntry.size() > 0){
				//get associated DM from a the object e. g. CAKE
				ArrayList<clsAssociation> oAssociationList = oEntry.get(0).b.getMoAssociatedDataStructures();
				//Add associated DM to the input list. Now the list moAssociatedDataStructures contains DM and ATTRIBUTES
				poPerception.getMoAssociatedDataStructures().addAll(oAssociationList);
			}
		}*/
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
	 * Set categories, where the categories of the input DM are multiplicated with a factor context value
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
	 * TODO HZ or IH: This function does nothing
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
	 * Either the perceived image or the input image from the secondary process are put on the input for searching for experiences (type IMAGE)
	 * in the storage. The total amount of mrPleasure decides which image is put on the input. In that way content from the secondary process
	 * can activate phantasies, if the perception is not so important (subjective). The function returns a list of activated images, which are
	 * not perception.
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
			if (clsAffectTools.calculateAbsoluteAffect(oPerceptionInput) < clsAffectTools.calculateAbsoluteAffect(oReturnedMemory)) {
				blUsePerception = false;
			}
		}
		
		ArrayList<clsPair<Double,clsDataStructureContainer>> oSearchResultContainer = new ArrayList<clsPair<Double,clsDataStructureContainer>>();
		if (blUsePerception==true) {
			//Use perceived image as input of spread activation
			//TODO AW: Only the first
			//Search for matches
			searchContainer(oPerceptionInput, oSearchResultContainer, "IMAGE", mrMatchThreshold);
			//Create associations between the PI and those matches
			for (clsPair<Double,clsDataStructureContainer> oPair : oSearchResultContainer) {
				clsDataStructureTools.createAssociationPrimary(oPerceptionInput, oPair.b, oPair.a);
				//Now all matched images are linked with the PI
			}
			//TODO AW: All activated matches are added to the list. Here, spread activation shall be used
		} else {
			//Use action-plan image as input of spread activation
			//TODO: This is only the first basic implementation of activation of phantsies
			
			searchContainer(oReturnedMemory, oSearchResultContainer, "IMAGE", mrMatchThreshold);
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
	 * Add corresponding attribute associations to a container
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
				clsAssociation oAssociation = new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(
							-1, eDataType.ASSOCIATIONATTRIBUTE, eDataType.ASSOCIATIONATTRIBUTE.name()), 
							(clsPrimaryDataStructure)poNewImage.getMoDataStructure(), 
							(clsPrimaryDataStructure)oBestMatch.getMoDataStructure());
				poNewImage.getMoAssociatedDataStructures().add(oAssociation);
			}
		}
	}
	
	/**
	 * Add Empty-Space-Objects to perception for all floor elements, which are not visible
	 * 
	 * (wendt)
	 *
	 * @since 28.11.2011 14:35:09
	 *
	 * @param poImage
	 * @return
	 */
	private ArrayList<clsThingPresentationMesh> createEmptySpaceObjects(clsThingPresentationMesh poImage) {
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		//Get all positions in the image
		ArrayList<clsTriple<clsThingPresentationMesh, String, String>> oExistingPositions = clsDataStructureTools.getImageContentPositions(poImage);
	
		//Generate a matrix of all possible positions
		ArrayList<String> oDistance = new ArrayList<String>();
		//oDistance.addAll(Arrays.asList("FAR","MEDIUM","NEAR","MANIPULATEABLE","EATABLE"));
		oDistance.addAll(Arrays.asList("FAR","MEDIUM","NEAR"));	//Use only the things, which are relevant
		ArrayList<String> oPosition = new ArrayList<String>();
		oPosition.addAll(Arrays.asList("RIGHT","MIDDLE_RIGHT","CENTER","MIDDLE_LEFT","LEFT"));
		
		ArrayList<clsTriple<clsThingPresentationMesh, String, String>> oAllPositions = new ArrayList<clsTriple<clsThingPresentationMesh, String, String>>();
		for (int i=0; i< oDistance.size();i++) {
			for (int j=0; j< oPosition.size();j++) {
				oAllPositions.add(new clsTriple<clsThingPresentationMesh, String, String>(null, oPosition.get(j), oDistance.get(i)));
			}
		}
		
		//Find all Objects in the oAllPositions and add them to oRemovePositions
		ArrayList<clsTriple<clsThingPresentationMesh, String, String>> oNewPositions = new ArrayList<clsTriple<clsThingPresentationMesh, String, String>>();
		for (clsTriple<clsThingPresentationMesh, String, String> oAllPosPair: oAllPositions) {
			boolean bFound = false;
			for (clsTriple<clsThingPresentationMesh, String, String> oExistPosPair : oExistingPositions) {
				if ((oExistPosPair.b.equals(oAllPosPair.b)) && (oExistPosPair.c.equals(oAllPosPair.c))) {
					bFound = true;
					break;
				}
			}
			if (bFound==false) {
				oNewPositions.add(oAllPosPair);
			}
		}
		
		
		//Search for one "Nothingobject"
		//Create the TP
		clsThingPresentationMesh oGeneratedTPM = clsDataStructureGenerator.generateTPM(new clsTriple<String, ArrayList<clsPhysicalRepresentation>, Object>
			("ENTITY", new ArrayList<clsPhysicalRepresentation>(),"EMPTYSPACE"));
		
		ArrayList<clsPrimaryDataStructureContainer> oSearchStructure = new ArrayList<clsPrimaryDataStructureContainer>();
		oSearchStructure.add(new clsPrimaryDataStructureContainer(oGeneratedTPM, new ArrayList<clsAssociation>()));
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		
		search(eDataType.TPM, oSearchStructure, oSearchResult); 
		//If nothing is found, cancel
		if (oSearchResult.get(0).isEmpty()==true) {
			return oRetVal;
		}
		//Create "Nothing"-objects for each position
		clsPrimaryDataStructureContainer oEmptySpaceContainer = (clsPrimaryDataStructureContainer) oSearchResult.get(0).get(0).b;
		//for each position, fill it with a container
		clsThingPresentationMesh oEmptySpaceTPM;
		for (clsTriple<clsThingPresentationMesh, String, String> oPosPair : oNewPositions) {
			//Create a new TP-Container
			try {
				oEmptySpaceTPM = (clsThingPresentationMesh) ((clsThingPresentationMesh) oEmptySpaceContainer.getMoDataStructure()).clone();
			
			
				clsThingPresentation oPositionTP = clsDataStructureGenerator.generateTP(new clsPair<String, Object>(eContentType.POSITION.toString(), oPosPair.b));
				clsThingPresentation oDistanceTP = clsDataStructureGenerator.generateTP(new clsPair<String, Object>(eContentType.DISTANCE.toString(), oPosPair.c));
			
				//clsTriple<Integer, eDataType, String> poDataStructureIdentifier,
				//clsPrimaryDataStructure poAssociationElementA, 
		    	//clsPrimaryDataStructure poAssociationElementB)
				clsTriple<Integer, eDataType, String> poIdentifier = new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE");
				clsAssociationAttribute oPositionAss = new clsAssociationAttribute(poIdentifier, oEmptySpaceTPM, oPositionTP);
				clsAssociationAttribute oDistanceAss = new clsAssociationAttribute(poIdentifier, oEmptySpaceTPM, oDistanceTP);
			
				oEmptySpaceTPM.getExternalMoAssociatedContent().add(oPositionAss);
				oEmptySpaceTPM.getExternalMoAssociatedContent().add(oDistanceAss);
			
			
				oRetVal.add(oEmptySpaceTPM);
			
			} catch (CloneNotSupportedException e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//Set new instance IDs
		//clsDataStructureTools.createInstanceFromTypeList(oRetVal, true);
		
		return oRetVal;
	}
	

	/**
	 * Get the first element of the input arraylist
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
	public void receive_I5_19(ArrayList<clsPrimaryDataStructureContainer> poReturnedMemory) {
		moReturnedTPMemory_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poReturnedMemory);
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
