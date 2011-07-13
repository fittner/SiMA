/**
 * clsModuleBaseKB.java: DecisionUnits - pa._v38.modules
 * 
 * @author deutsch
 * 21.04.2011, 11:50:00
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsBWProperties;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
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
 	 * @param poProp The property file in form of an instance of clsBWProperties.
	 * @param poModuleList A reference to an empty map that is filled with references to the created modules. Needed by the clsProcessor.
	 * @param poInterfaceData A reference to an empty map that is filled with data that is transmitted via the interfaces each step.
	 * @param poKnowledgeBaseHandler A reference to the knowledgebase handler.
	 * @throws Exception
	 */
	public clsModuleBaseKB(String poPrefix, clsBWProperties poProp,
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
	 * Search function for whole containers. It will always return a containerlist. The specialized use is if
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
	public void search(
			clsDataStructureContainer poPattern,
			ArrayList<clsPair<Double, clsDataStructureContainer>> poSearchResult) {

		//createSearchPattern(poPattern, oSearchPattern);	//Create a pattern, search for type, poDataType 4096=TP, Input-Container
		if (poPattern!=null)  {
			clsPair<Integer, clsDataStructureContainer> oSearchPattern = new clsPair<Integer, clsDataStructureContainer>(eDataType.UNDEFINED.nBinaryValue, poPattern); 
			accessKnowledgeBase(poSearchResult, oSearchPattern); 
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
	 * DOCUMENT (zeilinger) - insert description
	 * FIXME - remove the overloaded function from itfKnowledgeBaseHandler and add a (new ArraList(poSearchPattern)) instead ...
	 * 
	 * @since 13.07.2011 13:47:08
	 *
	 * @param poSearchResult
	 * @param poSearchPattern
	 */
	public void accessKnowledgeBase(ArrayList<clsPair<Double,clsDataStructureContainer>> poSearchResult,
			clsPair<Integer, clsDataStructureContainer> poSearchPattern) {
		//AW 20110629: New function for searching one total container
		
		poSearchResult.addAll(moKnowledgeBaseHandler.initMemorySearch(poSearchPattern));
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
}
