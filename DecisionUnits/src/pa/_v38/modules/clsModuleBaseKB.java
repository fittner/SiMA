/**
 * clsModuleBaseKB.java: DecisionUnits - pa._v38.modules
 * 
 * @author deutsch
 * 21.04.2011, 11:50:00
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAffect;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.psychicspreadactivation.clsPsychicSpreadActivation;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;


/**
 * The module base class for all functional modules that need to access the knowledgebase. clsModuleBaseKB introduces a number of methods which manage the definition of the search pattern as well as the request for and the retrieval of search result. 
 * 
 * In case a module must be equipped with a knowledge base interface following steps must be done:
 * 		1.	It must be checked if the module inherits from clsModuleBaseKB
 * 		2.	The list of data structures serving as patterns must be defined. 
 * 
 * @see clsModuleBase 
 * 
 * Public <E> void search(eDataType poPattern poSearchResult):	Is used to trigger the search method; hence it is invoked within the modules in order to start a request for stored knowledge. Thereby, it requires the pattern itself (poPattern) which should be searched for and an empty list which serves as container for the search results (poSerchResult); In case a specific type of associated data structures should be retrieved, eDataType must be defined
 * Public <E> void createSearchPattern(searchList, searchpattern)	This method extracts the search patterns depending if they come as instance of clsDataStructurePa or in case they are summarized in a clsPrimaryDataStructureContainer. As it is not an isolated case that search patterns are represented in one or the other type, the extraction is sourced out to one file - clsModuleBaseKB
 * Public void accessKnowledgeBase(searchResult, searchPattern)	Represents the access to the knowledgebase; the create search pattern as well as an empty list used for the searchresult is attached as parameter.
 * 
 * @author deutsch
 * 13.07.2011, 13:46:24
 * 
 */
public abstract class clsModuleBaseKB extends clsModuleBase {
	/** The knowledgebasehandler (aka the memory); @since 13.07.2011 13:46:56 */
	protected clsKnowledgeBaseHandler moKnowledgeBaseHandler;
	protected clsPsychicSpreadActivation moSpreadActivationHandler;
	
	/**
	 * This constructor creates all functional modules with the provided properties. Additionally the provided reference
	 * to the knowledgebasehandler is assigned to the internal member variable.
	 * 
	 * @since 13.07.2011 14:59:39
	 *
 	 * @param poPrefix Prefix for the property-entries in the property file.
 	 * @param poProp The property file in form of an instance of clsProperties.
	 * @param poModuleList A reference to an empty map that is filled with references to the created modules. Needed by the clsProcessor.
	 * @param poInterfaceData A reference to an empty map that is filled with data that is transmitted via the interfaces each step.
	 * @param poKnowledgeBaseHandler A reference to the knowledgebase handler.
	 * @throws Exception
	 */
	public clsModuleBaseKB(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		moKnowledgeBaseHandler = poKnowledgeBaseHandler;
		
		moSpreadActivationHandler = new clsPsychicSpreadActivation(this);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.03.2011, 08:36:59
	 *
	 * @param undefined
	 * @param poDS
	 * @param oSearchResult
	 */
	public <E> void search(
			eDataType poDataType,
			ArrayList<E> poPattern,
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult) {
		
		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>(); 

		createSearchPattern(poDataType, poPattern, oSearchPattern);	//Create a pattern, search for type, poDataType 4096=TP, Input-Container
		accessKnowledgeBase(poSearchResult, oSearchPattern); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.03.2011, 19:04:29
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#createSearchPattern(pa._v38.memorymgmt.enums.eDataType, java.lang.Object, java.util.ArrayList)
	 */
	/*public <E> void createSearchPattern(eDataType poDataType, ArrayList<E> poList,
			ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {
		createSearchPattern(poDataType, poList, false, poSearchPattern);
	}*/
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @since 13.07.2011 13:47:03
	 *
	 * @param <E>
	 * @param poDataType
	 * @param poList
	 * @param poSearchPattern
	 */
	public <E> void createSearchPattern(eDataType poDataType, ArrayList<E> poList, 
			ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {
		
		for (E oEntry : poList){
				if(oEntry instanceof clsDataStructurePA){
					poSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poDataType.nBinaryValue, (clsDataStructurePA)oEntry));
				}
				else if (oEntry instanceof clsPrimaryDataStructureContainer){
					poSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poDataType.nBinaryValue, ((clsPrimaryDataStructureContainer)oEntry).getMoDataStructure()));
				}
			}
	}
	
//	/**
//	 * Search function for whole containers. It will always return a container list and a match factor. The specialized use is if
//	 * the data structure is a template image.
//	 *
//	 * @since 29.06.2011 13:54:58
//	 *
//	 * @param <E>
//	 * @param poDataType
//	 * @param poPattern
//	 * @param poSearchResult
//	 * 
//	 */
//	public void searchContainer(
//			clsDataStructureContainer poPattern,
//			ArrayList<clsPair<Double, clsDataStructureContainer>> poSearchResult, String poSearchContentType, double prThreshold) {
//
//		//createSearchPattern(poPattern, oSearchPattern);	//Create a pattern, search for type, poDataType 4096=TP, Input-Container
//		if (poPattern!=null)  {
//			
//			//FIXME AW: Make a better solution than renaming the content types at the search
//			String oInputContentType = poPattern.getMoDataStructure().getMoContentType();
//			//Set the new content type, in order to get matches from it, e. g. IMAGE or LIBIDOIMAGE. This content type is the first filter
//			//in the search
//			poPattern.getMoDataStructure().setMoContentType(poSearchContentType);
//			
//			clsPair<Integer, clsDataStructureContainer> oSearchPattern = new clsPair<Integer, clsDataStructureContainer>(eDataType.UNDEFINED.nBinaryValue, poPattern); 
//			accessKnowledgeBaseContainer(poSearchResult, oSearchPattern, prThreshold); 
//			
//			//Set the old content type again...this is hack dirty bastard shit
//			poPattern.getMoDataStructure().setMoContentType(oInputContentType);
//			
//			//Set IDs
//			//for (clsPair<Double, clsDataStructureContainer> oPair : poSearchResult) {
//			//	clsDataStructureTools.createInstanceFromType(oPair.b);
//			//}
//			
//		} else {
//			poSearchResult = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
//		}
//			
//	}
	
	/**
	 * Search function for datastructures, which matches the input structure. It will always return a list of data structures and a match factor. The specialized use is if
	 * the data structure is a thing presentation mesh, which is a PI or RI.
	 *
	 * @since 29.06.2011 13:54:58
	 *
	 * @param <E>
	 * @param poDataType
	 * @param poPattern
	 * @param poSearchResult
	 * 
	 */
	public void searchMesh(clsDataStructurePA poPattern, ArrayList<clsPair<Double, clsDataStructurePA>> poSearchResult, eContentType poSearchContentType, double prThreshold, int pnLevel) {

		//createSearchPattern(poPattern, oSearchPattern);	//Create a pattern, search for type, poDataType 4096=TP, Input-Container
		if (poPattern!=null)  {
			
			//FIXME AW: Make a better solution than renaming the content types at the search
			eContentType oInputContentType = poPattern.getMoContentType();
			//Set the new content type, in order to get matches from it, e. g. IMAGE or LIBIDOIMAGE. This content type is the first filter
			//in the search
			poPattern.setMoContentType(poSearchContentType);
			
			clsPair<Integer, clsDataStructurePA> oSearchPattern = new clsPair<Integer, clsDataStructurePA>(eDataType.UNDEFINED.nBinaryValue, poPattern); 
			accessKnowledgeBaseMesh(poSearchResult, oSearchPattern, prThreshold, pnLevel); 
			
			//Set the old content type again...this is hack dirty bastard shit
			poPattern.setMoContentType(oInputContentType);
			
			//Set IDs
			//for (clsPair<Double, clsDataStructureContainer> oPair : poSearchResult) {
			//	clsDataStructureTools.createInstanceFromType(oPair.b);
			//}
			
		} else {
			poSearchResult = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		}
			
	}
	

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @since 13.07.2011 13:47:40
	 *
	 * @param poSearchResult
	 * @param poSearchPattern
	 */
	public void accessKnowledgeBase(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult,
									ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {		
		poSearchResult.addAll(moKnowledgeBaseHandler.initMemorySearch(poSearchPattern));
	}
	
//	/**
//	 * Access knowledgebase with a container and not a clsDataStructurePA
//	 * 
//	 * @since 13.07.2011 13:47:08
//	 *
//	 * @param poSearchResult
//	 * @param poSearchPattern
//	 */
//	public void accessKnowledgeBaseContainer(ArrayList<clsPair<Double,clsDataStructureContainer>> poSearchResult,
//			clsPair<Integer, clsDataStructureContainer> poSearchPattern, double prThreshold) {
//		//AW 20110629: New function for searching one total container
//		poSearchResult.addAll(moKnowledgeBaseHandler.initMemorySearchContainer(poSearchPattern, prThreshold));
//	}
	
	/**
	 * Access knowledgebase with a container and not a clsDataStructurePA
	 * 
	 * @since 13.07.2011 13:47:08
	 *
	 * @param poSearchResult
	 * @param poSearchPattern
	 */
	public void accessKnowledgeBaseMesh(ArrayList<clsPair<Double,clsDataStructurePA>> poSearchResult,
			clsPair<Integer, clsDataStructurePA> poSearchPattern, double prThreshold, int pnLevel) {
		//AW 20110629: New function for searching one total container
		poSearchResult.addAll(moKnowledgeBaseHandler.initMemorySearchMesh(poSearchPattern, prThreshold, pnLevel));
	}
	
	
	/**
	 * Purpose: Put any data structure here and this function searches the memory for all associated assoications. 
	 * Difference to search: This function only gets the container and not any other matches.
	 * 
	 * AW
	 * @since 18.07.2011 16:07:35
	 *
	 * @param poInput
	 * @return
	 */
	public clsDataStructureContainer searchCompleteContainer(clsDataStructurePA poInput) {
		clsDataStructureContainer oRetVal = null;
		
		oRetVal = moKnowledgeBaseHandler.initContainerRetrieval(poInput);
		
		return oRetVal;
	}
	
	/**
	 * Purpose: Put any data structure here and this function searches the memory for all associated assoications. 
	 * Difference to search: This function only gets the structure without comparison
	 * 
	 * Only for TPM and WPM
	 * 
	 * AW
	 * @since 18.07.2011 16:07:35
	 *
	 * @param poInput
	 * @return
	 */
	public clsDataStructurePA searchCompleteMesh(clsDataStructurePA poInput, int pnLevel) {
		clsDataStructurePA oRetVal = null;
		
		oRetVal = moKnowledgeBaseHandler.initMeshRetrieval(poInput, pnLevel);
		
		if (oRetVal==null) {
			try {
				throw new Exception("Error in searchMesh: the returned function for " + poInput + " is null. This always occurs if images of TPM does not have any WPM");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//Transfer all meshes of the same ID to the same instance, i. e. all associations do contain this particular structure
//		if (poInput instanceof clsWordPresentationMesh) {
//			clsDataStructureTools.correctFalseInstancesInAssWPM((clsWordPresentationMesh)oRetVal);
//		}
		
//		//Move all associations from the input to the output
//		if (poInput instanceof clsWordPresentationMesh) {
//			clsDataStructureTools.moveAllAssociations((clsWordPresentationMesh)oRetVal, (clsWordPresentationMesh)poInput);
//		} else if (poInput instanceof clsThingPresentationMesh) {
//			clsDataStructureTools.moveAllAssociations((clsThingPresentationMesh)oRetVal, (clsThingPresentationMesh)poInput);
//		}
		
		//Move all associations from the found structure to the original structure of the input. This is used in Spreadactivation where the mesh is "growing"
		if (poInput instanceof clsWordPresentationMesh) {
			clsMeshTools.moveAllAssociations((clsWordPresentationMesh)poInput, (clsWordPresentationMesh)oRetVal);
		} else if (poInput instanceof clsThingPresentationMesh) {
			clsMeshTools.moveAllAssociations((clsThingPresentationMesh)poInput, (clsThingPresentationMesh)oRetVal);
		}
		
		
		return poInput;
	}
	
//	/**
//	 * If only a part of a mesh is available, e. g. no external associations, this function is used to retrieve the rest of the
//	 * mesh as well as associated structures until a certain depth decided by the level. level=1 means all of the own structure
//	 * but no direct associated other structures. Level 2 means all directly associated structures
//	 * 
//	 * (wendt)
//	 *
//	 * @since 30.01.2012 19:54:53
//	 *
//	 * @param poInput
//	 * @param pnLevel
//	 * @return
//	 */
//	public clsDataStructurePA searchCompleteMesh(clsDataStructurePA poInput, int pnLevel) {
//		clsDataStructurePA oRetVal = null;
//		
//		ArrayList<clsPair<Double, clsDataStructurePA>> oSearchResult = new ArrayList<clsPair<Double, clsDataStructurePA>>();
//		
//		searchMesh(poInput, oSearchResult, poInput.getMoContentType(), 1.0, pnLevel);
//		
//		if (oSearchResult.isEmpty()==false) {
//			oRetVal = oSearchResult.get(0).b;
//		}
//		
//		return oRetVal;
//	}

	/**
	 * Getter for moKnowledgeBaseHandler.
	 *
	 * @since 13.07.2011 13:47:12
	 *
	 * @return
	 */
	public clsKnowledgeBaseHandler getKnowledgeBaseHandler() {
		return moKnowledgeBaseHandler;
	}
	
	/**
	 * Extracts associated memories (images) from a certain container, by creating new containers for the 
	 * associated structures and filling them with the content of the root/leaf elements of the associations
	 * DOCUMENT (wendt)
	 *
	 * @since 19.07.2011 23:43:49
	 *
	 * @param poInput
	 * @return
	 */
	protected ArrayList<clsDataStructureContainer> extractAssociatedContainers(clsDataStructureContainer poInput) {
		ArrayList<clsDataStructureContainer> oRetVal = new ArrayList<clsDataStructureContainer>();
		
		//Go through all associated content of the containers and use only the AssociationSecondary and Primary
		if (poInput instanceof clsPrimaryDataStructureContainer) {
			for (clsAssociation oAss : poInput.getMoAssociatedDataStructures()) {
				if (oAss instanceof clsAssociationPrimary) {
					//As there is no direction, if the data structure is equal the leaf, then the root is chosen for the association
					oRetVal.addAll(extractContainerList(oAss, poInput));
				}
			}
		//Almost the same, if the container is of secondary structure
		} else if (poInput instanceof clsSecondaryDataStructureContainer) {
			for (clsAssociation oAss : poInput.getMoAssociatedDataStructures()) {
				if (oAss instanceof clsAssociationSecondary) {
					//As there is no direction, if the data structure is equal the leaf, then the root is chosen for the association
					oRetVal.addAll(extractContainerList(oAss, poInput));
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Help function for extractAssociatedContainers. From an association, where the root or leaf element are different from the data
	 * structure in the source container, search the complete container and return it.
	 * (wendt)
	 *
	 * @since 20.07.2011 13:47:16
	 *
	 * @param poAss
	 * @param poSourceContainer
	 * @return
	 */
	private ArrayList<clsDataStructureContainer> extractContainerList(clsAssociation poAss, clsDataStructureContainer poSourceContainer) {
		ArrayList<clsDataStructureContainer> oRetVal = new ArrayList<clsDataStructureContainer>();
		
		if (poAss.getLeafElement().getMoDS_ID() == poSourceContainer.getMoDataStructure().getMoDS_ID()) {
			clsDataStructureContainer oContainer = searchCompleteContainer(poAss.getRootElement());
			if (oContainer!=null) {
				oRetVal.add(oContainer);	
			}
		} else {
			clsDataStructureContainer oContainer = searchCompleteContainer(poAss.getLeafElement());
			if (oContainer!=null) {
				oRetVal.add(oContainer);
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * This function extracts the primary structure part of a secondary structure via the clsAssociationWP
	 * DOCUMENT (wendt)
	 *
	 * @since 19.07.2011 23:58:08
	 *
	 * @param poInput
	 * @return
	 */
	protected clsPrimaryDataStructureContainer extractPrimaryContainer(clsSecondaryDataStructureContainer poInput) {
		clsPrimaryDataStructureContainer oRetVal = null;
		
		//Go through the container and search for associationWP
		for (clsAssociation oAss : poInput.getMoAssociatedDataStructures()) {
			if (oAss instanceof clsAssociationWordPresentation) {
				//Check if the primary data structure is a part of the root or the leaf element
				if (oAss.getLeafElement() instanceof clsPrimaryDataStructure) {
					oRetVal = (clsPrimaryDataStructureContainer) searchCompleteContainer(oAss.getLeafElement());
				} else if (oAss.getRootElement() instanceof clsPrimaryDataStructure) {
					oRetVal = (clsPrimaryDataStructureContainer) searchCompleteContainer(oAss.getRootElement());
				}
			}
		}
		
		return oRetVal;
	}
	
	protected clsSecondaryDataStructureContainer extractSecondaryContainer(clsPrimaryDataStructure poInput) {
		//Get the WP
		clsSecondaryDataStructureContainer oRetVal = null;
		
		clsAssociation oAss = getWP(poInput);
		
		if (oAss!=null) {
			if (oAss.getLeafElement() instanceof clsWordPresentation) {
				oRetVal = (clsSecondaryDataStructureContainer) searchCompleteContainer(oAss.getLeafElement());
			} else if (oAss.getRootElement() instanceof clsWordPresentation) {
				oRetVal = (clsSecondaryDataStructureContainer) searchCompleteContainer(oAss.getRootElement());
			}
		}
		
		return oRetVal;
	}
	
	
	/**
	 * Get the WP for a TP
	 * (wendt)
	 *
	 * @since 27.07.2011 09:39:49
	 *
	 * @param poDataStructure
	 * @return
	 */
	protected clsAssociation getWP(clsPrimaryDataStructure poDataStructure){
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		clsAssociation oRetVal = null; 
		
		//First check if WP can be found 
		search(eDataType.WP, new ArrayList<clsDataStructurePA>(Arrays.asList(poDataStructure)), oSearchResult);
		
		//If no WP can be found, try with WPM
		if (oSearchResult.get(0).size() > 0 && oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures().size() == 0) {
			oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
			search(eDataType.WPM, new ArrayList<clsDataStructurePA>(Arrays.asList(poDataStructure)), oSearchResult);
		}
		
		if(oSearchResult.get(0).size() > 0 && oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures().size() > 0){
			oRetVal = (clsAssociation)oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures().get(0);
		}
		
		return oRetVal;  
	}
	
	/**
	 * Get the WP for a TP
	 * (wendt)
	 *
	 * @since 27.07.2011 09:39:49
	 *
	 * @param poDataStructure
	 * @return
	 */
	protected clsAssociationWordPresentation getWPMesh(clsPrimaryDataStructure poDataStructure, double prThreshold){
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		clsAssociationWordPresentation oRetVal = null; 
		
		//If the input is a TPM, then search for a WPM
		if (poDataStructure instanceof clsThingPresentationMesh) {
			search(eDataType.WPM, new ArrayList<clsDataStructurePA>(Arrays.asList(poDataStructure)), oSearchResult);
			//If the input is a TP or DM, then search for the WP
		} else if (poDataStructure instanceof clsAffect || poDataStructure instanceof clsThingPresentation) {
			search(eDataType.WP, new ArrayList<clsDataStructurePA>(Arrays.asList(poDataStructure)), oSearchResult);
		}
		
		//If something was found
		if(oSearchResult.isEmpty() == false && oSearchResult.get(0).size() > 0 && oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures().size() > 0){
			//Get the best match if higher than the threshold
			if (oSearchResult.get(0).get(0).a >= prThreshold) {
				oRetVal = (clsAssociationWordPresentation)oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures().get(0);
			}	
		}
		
		return oRetVal;  
	}
	
	/**
	 * Convert a DM into an Affect, which is then converted into a word presentation affect
	 * (wendt)
	 *
	 * @since 30.01.2012 16:30:20
	 *
	 * @param poDM
	 * @return
	 */
	public clsWordPresentation convertDriveMeshToWP(clsDriveMeshOLD poDM) {
		clsWordPresentation oRetVal = null;
		
		//Generate the instance of the class affect
		clsAffect oAffect = (clsAffect) clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<String, Object>(eDataType.AFFECT.name(), poDM.getPleasure()));
		//Search for the WP of the affect
		clsAssociationWordPresentation oWPAss = getWPMesh(oAffect, 1.0);
		
		//Get drive Content String
		String oDriveContent = poDM.getMoContent();
		//Get the content of the affect strength
		//the word presentation in an associationWP is ALWAYS the leaf element
		String oAffectContent = ((clsWordPresentation)oWPAss.getLeafElement()).getMoContent();	//Get the content from the WP part
		
		//Construct WP
		String oWPContent = oDriveContent + ":" + oAffectContent;
		
		//Create the new WP for that drive
		clsWordPresentation oResWP = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>(eContentType.AFFECT.toString(), oWPContent));
		
		oRetVal = oResWP;
		
		return oRetVal;
	}
	
	/**
	 * Execute spread activation on an input image. set the available psychic energy
	 * 
	 * (wendt)
	 *
	 * @since 01.04.2012 13:23:57
	 *
	 * @param poInput
	 * @param prPsychicEnergyIn
	 * @return
	 */
	public void executePsychicSpreadActivation(clsThingPresentationMesh poInput, double prPsychicEnergyIn, ArrayList<clsDriveMeshOLD> poDriveMeshFilter) {
		
		//Add the activated image to the already processed list
		ArrayList<clsThingPresentationMesh> oAlreadyActivatedImages = new ArrayList<clsThingPresentationMesh>();
		oAlreadyActivatedImages.add(poInput);
		moSpreadActivationHandler.startSpreadActivation(poInput, prPsychicEnergyIn, oAlreadyActivatedImages, poDriveMeshFilter);
	
	}
}


