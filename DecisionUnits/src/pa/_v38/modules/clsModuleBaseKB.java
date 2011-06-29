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
import pa._v38.interfaces.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 21.04.2011, 11:50:00
 * 
 */
/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 29.06.2011, 13:40:58
 * 
 */
public abstract class clsModuleBaseKB extends clsModuleBase {
	protected clsKnowledgeBaseHandler moKnowledgeBaseHandler;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 21.04.2011, 11:50:32
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
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
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 29.06.2011 13:54:58
	 *
	 * @param <E>
	 * @param poDataType
	 * @param poPattern
	 * @param poSearchResult
	 * 
	 * Search function for whole containers. It will always return a containerlist. The specialized use is if
	 * the data structure is a template image.
	 */
	public void search(
			clsDataStructureContainer poPattern,
			ArrayList<clsPair<Double, clsDataStructureContainer>> poSearchResult) {
		
		clsPair<Integer, clsDataStructureContainer> oSearchPattern = new clsPair<Integer, clsDataStructureContainer>(eDataType.UNDEFINED.nBinaryValue, poPattern); 
		//createSearchPattern(poPattern, oSearchPattern);	//Create a pattern, search for type, poDataType 4096=TP, Input-Container
		accessKnowledgeBase(poSearchResult, oSearchPattern); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 14.03.2011, 22:34:44
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(pa.tools.clsPair)
	 */
	
	public void accessKnowledgeBase(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult,
									ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {		
		poSearchResult.addAll(moKnowledgeBaseHandler.initMemorySearch(poSearchPattern));
	}
	
	//AW 20110629: New function for searching one total container
	public void accessKnowledgeBase(ArrayList<clsPair<Double,clsDataStructureContainer>> poSearchResultx,
			clsPair<Integer, clsDataStructureContainer> poSearchPattern) {		
		//poSearchResultx.addAll(moKnowledgeBaseHandler.initMemorySearch(poSearchPattern));
	}

	public clsKnowledgeBaseHandler getKnowledgeBaseHandler() {
		return moKnowledgeBaseHandler;
	}
}
