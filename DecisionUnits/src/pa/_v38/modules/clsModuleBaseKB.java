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
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsPair;


/**
 * The module base class for all functional modules that need to access the knowledgebase. 
 * 
 * @see clsModuleBase 
 * 
 * @author deutsch
 * 13.07.2011, 13:46:24
 * 
 */
public abstract class clsModuleBaseKB extends clsModuleBase {
	/** The knowledgebasehandler (aka the memory); @since 13.07.2011 13:46:56 */
	protected clsKnowledgeBaseHandler moKnowledgeBaseHandler;
	
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
	
	/**
	 * Search function for whole containers. It will always return a container list and a match factor. The specialized use is if
	 * the data structure is a template image.
	 *
	 * @since 29.06.2011 13:54:58
	 *
	 * @param <E>
	 * @param poDataType
	 * @param poPattern
	 * @param poSearchResult
	 * 
	 */
	public void searchContainer(
			clsDataStructureContainer poPattern,
			ArrayList<clsPair<Double, clsDataStructureContainer>> poSearchResult, String poSearchContentType, double prThreshold) {

		//createSearchPattern(poPattern, oSearchPattern);	//Create a pattern, search for type, poDataType 4096=TP, Input-Container
		if (poPattern!=null)  {
			
			//FIXME AW: Make a better solution than renaming the content types at the search
			String oInputContentType = poPattern.getMoDataStructure().getMoContentType();
			//Set the new content type, in order to get matches from it, e. g. IMAGE or LIBIDOIMAGE. This content type is the first filter
			//in the search
			poPattern.getMoDataStructure().setMoContentType(poSearchContentType);
			
			clsPair<Integer, clsDataStructureContainer> oSearchPattern = new clsPair<Integer, clsDataStructureContainer>(eDataType.UNDEFINED.nBinaryValue, poPattern); 
			accessKnowledgeBaseContainer(poSearchResult, oSearchPattern, prThreshold); 
			
			//Set the old content type again...this is hack dirty bastard shit
			poPattern.getMoDataStructure().setMoContentType(oInputContentType);
			
			//Set IDs
			//for (clsPair<Double, clsDataStructureContainer> oPair : poSearchResult) {
			//	clsDataStructureTools.createInstanceFromType(oPair.b);
			//}
			
		} else {
			poSearchResult = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
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
	
	/**
	 * Access knowledgebase with a container and not a clsDataStructurePA
	 * 
	 * @since 13.07.2011 13:47:08
	 *
	 * @param poSearchResult
	 * @param poSearchPattern
	 */
	public void accessKnowledgeBaseContainer(ArrayList<clsPair<Double,clsDataStructureContainer>> poSearchResult,
			clsPair<Integer, clsDataStructureContainer> poSearchPattern, double prThreshold) {
		//AW 20110629: New function for searching one total container
		poSearchResult.addAll(moKnowledgeBaseHandler.initMemorySearchContainer(poSearchPattern, prThreshold));
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
}
