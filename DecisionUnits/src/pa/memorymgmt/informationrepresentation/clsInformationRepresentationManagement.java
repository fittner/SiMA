/**
 * clsInformationRepresentationManagementARSi10.java: DecisionUnits - pa.informationrepresentation.ARSi10
 * 
 * @author zeilinger
 * 30.05.2010, 11:33:35
 */
package pa.memorymgmt.informationrepresentation;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import config.clsBWProperties;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsHomeostaticRepresentation;
import pa.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa.memorymgmt.informationrepresentation.enums.eDataSources;
import pa.memorymgmt.informationrepresentation.enums.eSearchMethod;
import pa.memorymgmt.informationrepresentation.modules.M01_InformationRepresentationMgmt;
import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 11:33:35
 * 
 */
public class clsInformationRepresentationManagement extends clsKnowledgeBaseHandler{
		
	public String moDatabaseSource; 
	public String moSearchMethod; 
	public String moSourceName; 
	public M01_InformationRepresentationMgmt moM01InformationRepresentationMgmt;
	public clsSearchSpaceHandler moSearchSpaceHandler; 
	public ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> moSearchResult;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 30.05.2010, 11:34:00
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsInformationRepresentationManagement(String poPrefix,
			clsBWProperties poProp) {

		super(poPrefix, poProp); 	
		applyProperties(poPrefix, poProp);
		
		initSearchSpace(); 
		initModules(); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 29.05.2010, 16:40:35
	 *
	 */
	private void initSearchSpace() {
		moSearchSpaceHandler = new clsSearchSpaceHandler(moDatabaseSource, moSourceName); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 30.05.2010, 16:59:09
	 *
	 */
	private void initModules() {
		moM01InformationRepresentationMgmt = new M01_InformationRepresentationMgmt(moSearchSpaceHandler, moSearchMethod); 
	}

	private void applyProperties(String poPrefix, clsBWProperties poProp){		
		String pre = clsBWProperties.addDot(poPrefix);
		poProp.putAll(super.getDefaultProperties(poPrefix));
		
		moDatabaseSource = poProp.getProperty(pre+P_DATABASE_SOURCE);
		moSearchMethod = poProp.getProperty(pre+P_SEARCH_METHOD);
		moSourceName = poProp.getProperty(pre+P_SOURCE_NAME);
	}
	
	 public static clsBWProperties getDefaultProperties(String poPrefix) {
	    	String pre = clsBWProperties.addDot(poPrefix);
	    	clsBWProperties oProp = new clsBWProperties();
	    	oProp.putAll(clsKnowledgeBaseHandler.getDefaultProperties(pre) );
	    	oProp.setProperty(pre+P_DATABASE_SOURCE, eDataSources.MAINMEMORY.toString());
	    	oProp.setProperty(pre+P_SEARCH_METHOD, eSearchMethod.LISTSEARCH.toString());
	    	//TODO HZ: Make the project file-path configurable
	    	oProp.setProperty(pre+P_SOURCE_NAME, "/DecisionUnits/config/bw/pa.memory/AGENT_BASIC/BASIC.pprj");
	    	return oProp;
	 }
	 
	 /* (non-Javadoc)
		 * THis method initializes the search process. The method receives an ArrayList that is
		 * assembled of the search pattern that is represented by a data structure and an integer 
		 * (binary number) that introduces the filter-mechanism for special eDataTypes. The binaries 
		 * are set in the enum eDataType.
		 * 
		 * 
		 * @author zeilinger
		 * 28.06.2010, 20:41:07
		 * 
		 * @see pa.memorymgmt.itfKnowledgeBaseHandler#searchDataStructure(java.util.ArrayList)
		 */
		@Override
		public ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> initMemorySearch( ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPatternList){
			moSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
			
			for(clsPair<Integer, clsDataStructurePA> element:poSearchPatternList){
				ArrayList<clsPair<Double,clsDataStructureContainer>> oSearchPatternMatch = triggerModuleSearch((int)element.a, element.b);
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
	 * @author zeilinger
	 * 24.05.2010, 16:51:15
	 *
	 * @param next
	 * @return 
	 */
	private ArrayList<clsPair<Double, clsDataStructureContainer>> triggerModuleSearch(Integer poReturnType, clsDataStructurePA poDataStructure) {
			
			ArrayList<clsPair<Double,clsDataStructureContainer>> oSearchResult = new ArrayList<clsPair<Double,clsDataStructureContainer>>(); 
			
			if(poDataStructure instanceof clsSecondaryDataStructure){
				oSearchResult = moM01InformationRepresentationMgmt.moKB01SecondaryDataStructureMgmt.searchDataStructure(poReturnType, poDataStructure);
			}
			else if(poDataStructure instanceof clsPhysicalRepresentation){
				oSearchResult = moM01InformationRepresentationMgmt.moM02PrimaryInformationMgmt.moKB02InternalPerceptionMgmt.searchDataStructure(poReturnType, poDataStructure);
			}
			else if(poDataStructure instanceof clsHomeostaticRepresentation){
				oSearchResult = moM01InformationRepresentationMgmt.moM02PrimaryInformationMgmt.moKB03ExternalPerceptionMgmt.searchDataStructure(poReturnType, poDataStructure);
			}
			else{ throw new IllegalArgumentException("DataStructureContainerUnknown unknown ");}
			
			return oSearchResult;
	}
	

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.07.2010, 16:58:22
	 *
	 * @param moSearchResult2
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> cloneResult(
			ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult) throws CloneNotSupportedException {
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oClone = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oListEntry : poSearchResult){
			ArrayList<clsPair<Double, clsDataStructureContainer>> oClonedList = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
			
			for(clsPair<Double, clsDataStructureContainer> oPairEntry : oListEntry){
				oClonedList.add((clsPair<Double, clsDataStructureContainer>) oPairEntry.clone()); //suppressed Warning
			}
			
			oClone.add(oClonedList); 
		}
		return oClone;
	}
}
