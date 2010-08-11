/**
 * clsInformationRepresentationManagementARSi10.java: DecisionUnits - pa.informationrepresentation.ARSi10
 * 
 * @author zeilinger
 * 30.05.2010, 11:33:35
 */
package pa.memorymgmt.informationrepresentation;

import java.util.ArrayList;
import java.util.List;
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
	public List<ArrayList<clsPair<Double,clsDataStructureContainer>>> moSearchResult;
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
		public List<ArrayList<clsPair<Double,clsDataStructureContainer>>> initMemorySearch(
				ArrayList<clsPair<Integer, clsDataStructureContainer>> poSearchPatternContainer){
			moSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
			
			for(clsPair<Integer, clsDataStructureContainer> element:poSearchPatternContainer){
				triggerModuleSearch((int)element.a, element.b.moDataStructure);
			}
			
			try {
				return this.cloneResult(moSearchResult);
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
	 */
	private void triggerModuleSearch(Integer poReturnType, clsDataStructurePA poDataStructure) {
			if(poDataStructure instanceof clsSecondaryDataStructure){
				moSearchResult.add(moM01InformationRepresentationMgmt.moKB01SecondaryDataStructureMgmt.searchDataStructure(poReturnType, poDataStructure));
			}
			else if(poDataStructure instanceof clsPhysicalRepresentation){
				moSearchResult.add(moM01InformationRepresentationMgmt.moM02PrimaryInformationMgmt.moKB02InternalPerceptionMgmt.searchDataStructure(poReturnType, poDataStructure));
			}
			else if(poDataStructure instanceof clsHomeostaticRepresentation){
				moSearchResult.add(moM01InformationRepresentationMgmt.moM02PrimaryInformationMgmt.moKB03ExternalPerceptionMgmt.searchDataStructure(poReturnType, poDataStructure));
			}
			else{ throw new IllegalArgumentException("DataStructureContainerUnknown unknown ");}
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
	private List<ArrayList<clsPair<Double, clsDataStructureContainer>>> cloneResult(
				List<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult) throws CloneNotSupportedException {
		List<ArrayList<clsPair<Double, clsDataStructureContainer>>> oClone = new ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>>(); 
		
		for(List<clsPair<Double, clsDataStructureContainer>> oListEntry : poSearchResult){
			ArrayList<clsPair<Double, clsDataStructureContainer>> oClonedList = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
			for(clsPair<Double, clsDataStructureContainer> oPairEntry : oListEntry){
				oClonedList.add((clsPair<Double, clsDataStructureContainer>) oPairEntry.clone()); //suppressed Warning
			}
			oClone.add(oClonedList); 
		}
		return oClone;
	}
}
