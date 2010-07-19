/**
 * clsInformationRepresentationManagementARSi10.java: DecisionUnits - pa.informationrepresentation.ARSi10
 * 
 * @author zeilinger
 * 30.05.2010, 11:33:35
 */
package pa.memorymgmt.informationrepresentation;

import java.util.ArrayList;
import java.util.List;

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
public class clsInformationRepresentationManagementARSi10 extends clsKnowledgeBaseHandler{
		
	public String moDatabaseSource; 
	public String moSearchMethod; 
	public M01_InformationRepresentationMgmt moM01InformationRepresentationMgmt;
	public clsSearchSpaceHandler moSearchSpaceHandler; 
	public List<List<clsPair<Double,clsDataStructureContainer>>> moSearchResult;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 30.05.2010, 11:34:00
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsInformationRepresentationManagementARSi10(String poPrefix,
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
		moSearchSpaceHandler = new clsSearchSpaceHandler(moDatabaseSource); 
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
		moDatabaseSource = poProp.getProperty(pre+P_DATABASE_SOURCE);
		moSearchMethod = poProp.getProperty(pre+P_SEARCH_METHOD);
	}
	
	 public static clsBWProperties getDefaultProperties(String poPrefix) {
	    	String pre = clsBWProperties.addDot(poPrefix);
	    	clsBWProperties oProp = new clsBWProperties();
			
	    	oProp.setProperty(pre+P_DATABASE_SOURCE, eDataSources.MAINMEMORY.toString());
	    	oProp.setProperty(pre+P_SEARCH_METHOD, eSearchMethod.LISTSEARCH.toString());
	    	return oProp;
	 }
	 
	 /* (non-Javadoc)
		 * THis method initializes the search process. The method receives an ArrayList that is
		 * assembled of the search pattern that is represented by a data structure and an integer 
		 * (binary number) that introduces the filter-mechanism for special eDataTypes. The binaries 
		 * are set in the enum eDataType.
		 * 
		 * 
		 *
		 * @author zeilinger
		 * 28.06.2010, 20:41:07
		 * 
		 * @see pa.memorymgmt.itfKnowledgeBaseHandler#searchDataStructure(java.util.ArrayList)
		 */
		@Override
		public List<List<clsPair<Double,clsDataStructureContainer>>> initMemorySearch(
				ArrayList<clsPair<Integer, clsDataStructureContainer>> poSearchPatternContainer) {
			
			moSearchResult.clear(); 
			
			for(clsPair<Integer, clsDataStructureContainer> element:poSearchPatternContainer){
				triggerInformationRepresentationManagementModules((int)element.a, element.b.moDataStructure);
			}
				
			return moSearchResult;
		}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 16:51:15
	 *
	 * @param next
	 */
	private void triggerInformationRepresentationManagementModules(Integer poReturnType, clsDataStructurePA poDataStructure) {
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
		
	public void testSearch(){
		testSearchTP(); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 30.06.2010, 07:09:30
	 *
	 */
	private void testSearchTP() {
		//clsThingPresentation oTestTP =(clsThingPresentation)((ArrayList <clsDataStructurePA>) moSearchSpaceHandler.moSearchSpace.returnSearchSpace(eDataType.TP).keySet()).get(0);
		 
	}

}
