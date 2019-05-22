/**
 * CHANGELOG
 *
 * 25.02.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt.searchspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.slf4j.Logger;

import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsPrimaryDataStructureContainer;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.interfaces.itfSearchSpaceAccess;
import pa._v38.memorymgmt.framessearchspace.clsSearchSpaceHandler;
import pa._v38.memorymgmt.framessearchspace.enums.eDataSources;
import pa._v38.memorymgmt.framessearchspace.enums.eSearchMethod;
import pa._v38.memorymgmt.framessearchspace.tools.clsDataStructureComparisonTools;
import properties.clsProperties;
import testfunctions.clsTester;

/**
 * DOCUMENT (wendt) - insert description
 * 
 * @author wendt 25.02.2013, 15:06:14
 * 
 */
public class clsSearchSpaceManager implements itfSearchSpaceAccess {
	public static final String P_DATABASE_SOURCE = "database_source";
	public static final String P_SEARCH_METHOD = "database_search";
	public static final String P_SOURCE_NAME = "source_name";
	private static final String P_DB_URL = "/ARSMemory/config/_v38/bw/pa.memory/AGENT_BASIC/BASIC.pprj";
	// IH
	// private static final String P_DB_URL =
	// "/ARSMemory/config/_v38/bw/pa.memory/AGENT_IH/BASIC_IH.pprj";
	public String moDatabaseSource;
	public String moSearchMethod;
	public String moSourceName;

	private clsSearchSpaceHandler moSearchSpaceHandler;

	private Logger log = clsLogger.getLog("Memory");

	/**
	 * DOCUMENT (zeilinger) - insert description
	 * 
	 * @author zeilinger 30.05.2010, 12:33:06
	 *
	 */
	public clsSearchSpaceManager(String poPrefix, clsProperties poProp) {
		log.info("Initialize " + this.getClass().getName());
		applyProperties(poPrefix, poProp);

		// Init search space handler
		moSearchSpaceHandler = new clsSearchSpaceHandler(moDatabaseSource, moSourceName);
	}

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		// poProp.putAll(super.getDefaultProperties(poPrefix));

		moDatabaseSource = poProp.getProperty(pre + P_DATABASE_SOURCE);
		moSearchMethod = poProp.getProperty(pre + P_SEARCH_METHOD);
		moSourceName = poProp.getProperty(pre + P_SOURCE_NAME);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();
		// oProp.putAll(clsKnowledgeBaseHandler.getDefaultProperties(pre) );
		oProp.setProperty(pre + P_DATABASE_SOURCE, eDataSources.MAINMEMORY.toString());
		oProp.setProperty(pre + P_SEARCH_METHOD, eSearchMethod.LISTSEARCH.toString());
		// TODO HZ: Make the project file-path configurable
		oProp.setProperty(pre + P_SOURCE_NAME, P_DB_URL);
		return oProp;
	}

	// === SEARCH METHODS ===//

	// === SEARCH ENTITIES ===//

	/*
	 * (non-Javadoc)
	 *
	 * @since 25.02.2013 15:40:05
	 * 
	 * @see
	 * pa._v38.memorymgmt.itfSearchSpaceAccess#searchEntity(java.util.ArrayList)
	 */
	@Override
	public ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> searchEntity(
			ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPatternList) {
		ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> moSearchResult = new ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>>();

		for (clsPair<Integer, clsDataStructurePA> element : poSearchPatternList) {
			ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchPatternMatch = searchSingleEntity(
					(int) element.a, element.b);
			moSearchResult.add(oSearchPatternMatch);
		}

		if (moSearchResult.size() != poSearchPatternList.size()) {
			throw new NullPointerException(
					"Missing search result: search pattern and search result not from the same size");
		}

		try {

			return (ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>>) this.cloneResult(moSearchResult);

		} catch (CloneNotSupportedException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		}
		throw new NoSuchElementException("No return value defined");
	}
	//=== SEARCH METHODS ===//

	//=== SEARCH ENTITIES ===//
	
	/* (non-Javadoc)
	 *
	 * @since 25.02.2013 15:40:05
	 * 
	 * @see pa._v38.memorymgmt.itfSearchSpaceAccess#searchEntity(java.util.ArrayList)
	 */
	@Override
	public ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> searchEntityWrite(ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPatternList, double weight, double learning) {
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> moSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		
		for(clsPair<Integer, clsDataStructurePA> element:poSearchPatternList){
			ArrayList<clsPair<Double,clsDataStructureContainer>> oSearchPatternMatch = searchSingleEntityWrite((int)element.a, element.b, weight, learning);
			moSearchResult.add(oSearchPatternMatch);
		}
		
		if(moSearchResult.size() != poSearchPatternList.size()){
			throw new NullPointerException("Missing search result: search pattern and search result not from the same size"); 
		}
		
		try {
			
			return (ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>) this.cloneResult(moSearchResult);
		
		} catch (CloneNotSupportedException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		}
		throw new NoSuchElementException("No return value defined"); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger 24.05.2010, 16:51:15
	 *
	 * @param next
	 * @return
	 */
	private ArrayList<clsPair<Double, clsDataStructureContainer>> searchSingleEntity(int poReturnType,
			clsDataStructurePA poDataStructureUnknown) {

		ArrayList<clsPair<Double, clsDataStructureContainer>> oDataStructureContainerList = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
		ArrayList<clsPair<Double, clsDataStructurePA>> oMatchedDataStructures = new ArrayList<clsPair<Double, clsDataStructurePA>>();
//		System.out.println(moSearchSpaceHandler.toString());

		if (poDataStructureUnknown.getDS_ID() > -1) { // If the data structure already has an ID, no matching is
														// necessary and it has found itself
			//oMatchedDataStructures.add(new clsPair<Double, clsDataStructurePA>(1.0, poDataStructureUnknown));
			oMatchedDataStructures = clsDataStructureComparisonTools.compareDataStructures(poDataStructureUnknown, moSearchSpaceHandler.returnSearchSpace());
		} else {
			oMatchedDataStructures = clsDataStructureComparisonTools.compareDataStructures(poDataStructureUnknown, moSearchSpaceHandler.returnSearchSpace());; 
		}
				
		for(clsPair<Double, clsDataStructurePA> oPatternElement : oMatchedDataStructures){
			clsDataStructureContainer oDataStructureContainer = getDataContainer(poReturnType, oPatternElement.b);	//Get container from a certain data value
			oDataStructureContainerList.add(new clsPair<Double, clsDataStructureContainer>(oPatternElement.a, oDataStructureContainer));
		}
		return oDataStructureContainerList;
		
	}
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 16:51:15
	 *
	 * @param next
	 * @return 
	 */
	private ArrayList<clsPair<Double, clsDataStructureContainer>> searchSingleEntityWrite(int poReturnType, clsDataStructurePA poDataStructureUnknown, double weight, double learning) {
			
		ArrayList<clsPair<Double,clsDataStructureContainer>> oDataStructureContainerList = new ArrayList<clsPair<Double,clsDataStructureContainer>>(); 
		ArrayList<clsPair<Double,clsDataStructurePA>> oMatchedDataStructures = new ArrayList<clsPair<Double,clsDataStructurePA>>();
					moSearchSpaceHandler.returnSearchSpace());
		
		if(poDataStructureUnknown.getDS_ID() > -1 ){	//If the data structure already has an ID, no matching is necessary and it has found itself
			//oMatchedDataStructures.add(new clsPair<Double, clsDataStructurePA>(1.0, poDataStructureUnknown));
			oMatchedDataStructures = clsDataStructureComparisonTools.compareDataStructuresWrite(poDataStructureUnknown, moSearchSpaceHandler.returnSearchSpace(), weight, learning);
		else{
			// CREATE ASSOZIATION
		}

		for (clsPair<Double, clsDataStructurePA> oPatternElement : oMatchedDataStructures) {
			clsDataStructureContainer oDataStructureContainer = getDataContainer(poReturnType, oPatternElement.b); // Get
																													// container
																													// from
																													// a
																													// certain
																													// data
																													// value
			oDataStructureContainerList
					.add(new clsPair<Double, clsDataStructureContainer>(oPatternElement.a, oDataStructureContainer));
		}
		return oDataStructureContainerList;

	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger 12.07.2010, 12:58:02
	 *
	 * @param poReturnType
	 * @param oPatternElement
	 * @return
	 */
	private clsDataStructureContainer getDataContainer(int poReturnType, clsDataStructurePA poDataStructure) {

		clsPrimaryDataStructureContainer oDataStructureContainer = new clsPrimaryDataStructureContainer(null, null);
		oDataStructureContainer.setMoDataStructure(poDataStructure);

		ArrayList<clsAssociation> oExternalAssociationList = moSearchSpaceHandler.readOutSearchSpace(poReturnType,
				poDataStructure);
		oDataStructureContainer.setMoAssociatedDataStructures(oExternalAssociationList);

		return oDataStructureContainer;
	}

	// === SEARCH IMAGES ===//

	/*
	 * (non-Javadoc)
	 *
	 * @since 25.02.2013 15:40:05
	 * 
	 * @see
	 * pa._v38.memorymgmt.itfSearchSpaceAccess#searchMesh(pa._v38.tools.clsPair,
	 * double, int)
	 */
	
	
	  public ArrayList<clsPair<Double, clsDataStructurePA>> searchMesh(
	  clsPair<Integer, clsDataStructurePA> poSearchPattern, double prThreshold, int
	  pnLevel) { 
		  
	  ArrayList<clsPair<Double, clsDataStructurePA>> oSearchPatternMatch= new ArrayList<clsPair<Double, clsDataStructurePA>>();
	  ArrayList<clsPair<Double, clsDataStructurePA>> oResult = new
	  ArrayList<clsPair<Double, clsDataStructurePA>>();
	  
	  // Search for all template images in the store
	  
	  oSearchPatternMatch =listSearchMesh((int) poSearchPattern.a, poSearchPattern.b, prThreshold, pnLevel);
	  
	 try { 
	//This part must be cloned, in order not to interfer with the memory // 
		 oResult = (ArrayList<clsPair<Double,clsDataStructurePA>>)oSearchPatternMatch.clone();
	 
	 } catch (Exception e) { 
	e.printStackTrace(); // } 
	oResult = oSearchPatternMatch;
	 } 
	  return oResult; 
	  }
	 
	//@Override
	
	/*
	public ArrayList<clsTriple<Double, Double, clsDataStructurePA>> searchMesh(
			clsPair<Integer, clsDataStructurePA> poSearchPattern, double prThreshold, int pnLevel) {
		ArrayList<clsTriple<Double,Double, clsDataStructurePA>> oSearchPatternMatch = new ArrayList<clsTriple<Double, Double,clsDataStructurePA>>();
		ArrayList<clsTriple<Double, Double, clsDataStructurePA>> oResult = new ArrayList<clsTriple<Double, Double, clsDataStructurePA>>();

		// Search for all template images in the store
		oSearchPatternMatch = listSearchMesh((int) poSearchPattern.a, poSearchPattern.b, prThreshold, pnLevel);

		try {
			//This part must be cloned, in order not to interfer with the memory
			oResult = (ArrayList<clsPair<Double,clsDataStructurePA>>)oSearchPatternMatch.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		oResult = oSearchPatternMatch;
		//ArrayList<clsTriple<Double,Double, clsDataStructurePA>> oSearchPatternMatch = new ArrayList<clsTriple<Double, Double, clsDataStructurePA>>();
		return oResult;
	}
	
	*/

	/*
	 * (non-Javadoc)
	 *
	 * @since 25.02.2013 17:07:47
	 * 
	 * @see pa._v38.memorymgmt.itfSearchSpaceAccess#searchMesh(pa._v38.memorymgmt.
	 * datatypes.clsDataStructurePA, pa._v38.memorymgmt.enums.eContentType, double,
	 * int)
	 */
	@Override
	public ArrayList<clsPair<Double,clsDataStructurePA>> searchMesh(clsDataStructurePA poPattern,
			eContentType poSearchContentType, double prThreshold, int pnLevel) {
		ArrayList<clsPair<Double, clsDataStructurePA>> oResult = new ArrayList<clsPair<Double,clsDataStructurePA>>();

		if (poPattern != null) {

			// FIXME AW: Make a better solution than renaming the content types at the
			// search
			eContentType oInputContentType = poPattern.getContentType();
			// Set the new content type, in order to get matches from it, e. g. IMAGE or
			// LIBIDOIMAGE. This content type is the first filter
			// in the search
			poPattern.setMoContentType(poSearchContentType);

			clsPair<Integer, clsDataStructurePA> oSearchPattern = new clsPair<Integer, clsDataStructurePA>(
					eDataType.UNDEFINED.nBinaryValue, poPattern);
			oResult = this.searchMesh(oSearchPattern, prThreshold, pnLevel);
			// accessKnowledgeBaseMesh(poSearchResult, oSearchPattern, prThreshold,
			// pnLevel);

			// Set the old content type again...this is hack dirty bastatkrd shit
			poPattern.setMoContentType(oInputContentType);

		} else {
			oResult = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		}

		// === Perform system tests ===//
		if (clsTester.getTester().isActivated()) {
			try {
				//clsTester.getTester().exeTestAssociationAssignment(oResult);
			} catch (Exception e) {
				log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
			}
		}

		return oResult;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @since 05.12.2011 16:35:26
	 * 
	 * @see pa._v38.memorymgmt.informationrepresentation.modules.
	 * clsInformationRepresentationModuleBase#listSearchMesh(int,
	 * pa._v38.memorymgmt.datatypes.clsDataStructurePA, double, int)
	 */

	
	  public ArrayList<clsPair<Double, clsDataStructurePA>> listSearchMesh(int
	  poReturnType, clsDataStructurePA poDataStructureUnknown, double prThreshold,
	  int pnLevel) 
	  {
		  
	//In this function, the container content is compared with saved template images, which are the data structure in generated containers
	  ArrayList<clsPair<Double,clsDataStructureContainer>>oDataStructureContainerList = new ArrayList<clsPair<Double, clsDataStructureContainer>>(); 
	  
	  //Result
	  
	  //Parameters: - Boolean Strong match: Default=true. If one compared TP belonging to a TI is false, the whole match is = 0 - boolean Compare drives:
	  //Default=false. Compare DMs with each other - String DataType: Default:
	  //MEMORY. TIs with this type are searched for - Boolean AddItself:
	  //Default=true: If the structure itself is found in the memory, it adds itself
	  //to the result - double Threshold: Default=0.0 Association threshold for
	  //adding the image as a match
	  
	  
	  //Steps 
	  ArrayList<clsPair<Double, clsDataStructurePA>> oMatchedDataStructures = new ArrayList<clsPair<Double, clsDataStructurePA>>();
    //2b. Set the Content type of oDS oMatchedDataStructures =
	  compareElementsMesh(poDataStructureUnknown, prThreshold, pnLevel); 
	 //Get a List of all matching structures in the memory
	  
	  //=== Perform system tests ===//
	  
	  if (clsTester.getTester().isActivated()) {
	  try {
	  clsTester.getTester().exeTestAssociationAssignment(oMatchedDataStructures);
	  }
	  catch (Exception e) { 
	  log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
	  }
	  }
	  
	  return oMatchedDataStructures; 
	  
	}
	 
	
	//public ArrayList<clsTriple<Double, Double, clsDataStructurePA>> listSearchMesh(int poReturnType,
		//	clsDataStructurePA poDataStructureUnknown, double prThreshold, int pnLevel) {
		// In this function, the container content is compared with saved template
		// images, which are the data structure in generated containers
		// ArrayList<clsPair<Double,clsDataStructureContainer>>
		// oDataStructureContainerList = new
		// ArrayList<clsPair<Double,clsDataStructureContainer>>(); //Result

		/*
		 * Parameters: - Boolean Strong match: Default=true. If one compared TP
		 * belonging to a TI is false, the whole match is = 0 - boolean Compare drives:
		 * Default=false. Compare DMs with each other - String DataType: Default:
		 * MEMORY. TIs with this type are searched for - Boolean AddItself:
		 * Default=true: If the structure itself is found in the memory, it adds itself
		 * to the result - double Threshold: Default=0.0 Association threshold for
		 * adding the image as a match
		 */

		// Steps
		//ArrayList<clsTriple<Double,Double, clsDataStructurePA>> oMatchedDataStructures = new ArrayList<clsTriple<Double, Double, clsDataStructurePA>>();
		// 2b. Set the Content type of oDS
		//oMatchedDataStructures = compareElementsMesh(poDataStructureUnknown, prThreshold, pnLevel); // Get a List of all
																									// matching
																									// structures in the
																									// memory

		// === Perform system tests ===//
		//if (clsTester.getTester().isActivated()) {
		//	try {
				//clsTester.getTester().exeTestAssociationAssignment(oMatchedDataStructures);
		//	} catch (Exception e) {
				//log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
		//	}
	//	}

	//	return oMatchedDataStructures;
	//}

	/**
	 * Search for the whole content of a container in the memory and compare the
	 * found structures with the input structure
	 *
	 * @since 08.07.2011 11:53:35
	 *
	 * @param poContainerUnknown
	 * @return
	 */
	
	private ArrayList<clsPair<Double, clsDataStructurePA>> compareElementsMesh(clsDataStructurePA poUnknown,
			double prThreshold, int pnLevel) {
		ArrayList<clsPair<Double, clsDataStructurePA>> oRetVal = new ArrayList<clsPair<Double, clsDataStructurePA>>();

		oRetVal = clsDataStructureComparisonTools.compareDataStructuresMesh(moSearchSpaceHandler, poUnknown,
				prThreshold, pnLevel);

		return oRetVal;
	}
	 
	
	 //delacruz: convert dataStructures to clsTriple
	  
//	 private ArrayList<clsTriple<Double, Double, clsDataStructurePA>> compareElementsMesh(clsDataStructurePA poUnknown,
//			double prThreshold, int pnLevel) {
//		ArrayList<clsTriple<Double, Double, clsDataStructurePA>> oRetVal = new ArrayList<clsTriple<Double, Double, clsDataStructurePA>>();
//
//		oRetVal = clsDataStructureComparisonTools.compareDataStructuresMesh(moSearchSpaceHandler, poUnknown,
//				prThreshold, pnLevel);
//
//		return oRetVal;
//	}

	/*
	 * (non-Javadoc)
	 *
	 * @since 25.02.2013 16:13:59
	 * 
	 * @see
	 * pa._v38.memorymgmt.itfSearchSpaceAccess#getMesh(pa._v38.memorymgmt.datatypes.
	 * clsDataStructurePA, int)
	 */
	@Override
	public clsDataStructurePA getCompleteMesh(clsDataStructurePA poInput, int pnLevel) {
		clsDataStructurePA oRetVal = null;

		if (poInput instanceof clsWordPresentationMesh) {
			oRetVal = getMesh((clsWordPresentationMesh) poInput, pnLevel);
		} else if (poInput instanceof clsThingPresentationMesh) {
			oRetVal = getMesh((clsThingPresentationMesh) poInput, pnLevel);
		} else {
			throw new IllegalArgumentException("DataStructure unknown ");
		}

		return oRetVal;
	}

	public clsThingPresentationMesh getMesh(clsThingPresentationMesh poInput, int pnLevel) {
		try {
			clsDataStructureComparisonTools.getCompleteMesh(poInput, moSearchSpaceHandler, pnLevel);
		} catch (Exception e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		return poInput;
	}

	public clsWordPresentationMesh getMesh(clsWordPresentationMesh poInput, int pnLevel) {
		return clsDataStructureComparisonTools.getCompleteMesh(poInput, moSearchSpaceHandler, pnLevel);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger 20.07.2010, 16:58:22
	 *
	 * @param moSearchResult2
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> cloneResult(
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult)
			throws CloneNotSupportedException {

		ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> oClone = new ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>>();

		for (ArrayList<clsPair<Double, clsDataStructureContainer>> oListEntry : poSearchResult) {
			ArrayList<clsPair<Double, clsDataStructureContainer>> oClonedList = new ArrayList<clsPair<Double, clsDataStructureContainer>>();

			for (clsPair<Double, clsDataStructureContainer> oPairEntry : oListEntry) {
				oClonedList.add((clsPair<Double, clsDataStructureContainer>) oPairEntry.clone()); // suppressed Warning
			}

			oClone.add(oClonedList);
		}
		return oClone;
	}

	@Override
	public void complementMesh(clsDataStructurePA poInput, int pnLevel) {
		try {
			if (poInput instanceof clsThingPresentationMesh) {
				clsDataStructureComparisonTools.complementMesh((clsThingPresentationMesh) poInput, moSearchSpaceHandler,
						pnLevel, new HashMap<Integer, clsThingPresentationMesh>());
			} else {
				throw new IllegalArgumentException("DataStructure unknown ");
			}
		} catch (Exception e) {
			log.error("Error in clsSearchSpacemanager::complementMesh(...) with + " + poInput.toString());
		}
	}

}
