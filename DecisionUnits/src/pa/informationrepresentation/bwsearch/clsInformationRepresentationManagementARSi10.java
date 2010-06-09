/**
 * clsInformationRepresentationManagementARSi10.java: DecisionUnits - pa.informationrepresentation.ARSi10
 * 
 * @author zeilinger
 * 30.05.2010, 11:33:35
 */
package pa.informationrepresentation.bwsearch;

import java.util.List;

import config.clsBWProperties;
import pa.informationrepresentation.clsInformationRepresentationManagement;
import pa.informationrepresentation.bwsearch.enums.eDataSources;
import pa.informationrepresentation.bwsearch.enums.eSearchMethod;
import pa.informationrepresentation.bwsearch.modules.M01_InformationRepresentationMgmt;
import pa.informationrepresentation.datatypes.clsDataStructureContainer;
import pa.informationrepresentation.datatypes.clsPrimaryInformation;
import pa.informationrepresentation.datatypes.clsSecondaryInformation;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 11:33:35
 * 
 */
public class clsInformationRepresentationManagementARSi10 extends clsInformationRepresentationManagement{
		
	public String moDatabaseSource; 
	public String moSearchMethod; 
	public M01_InformationRepresentationMgmt moM01InformationRepresentationMgmt;
	public clsSearchSpaceHandler moSearchSpaceHandler; 
	public List<clsDataStructureContainer> moSearchResult;
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
		
	@Override
	public List<clsDataStructureContainer> searchDataStructure(String poReturnType, List<clsDataStructureContainer> poSearchPatternContainer){
		moSearchResult.clear(); 
		
		for(clsDataStructureContainer element:poSearchPatternContainer){
			triggerInformationRepresentationManagementModules(poReturnType, element);
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
	private void triggerInformationRepresentationManagementModules(String poReturnType, clsDataStructureContainer poSearchPatternContainer) {
		
		if(poSearchPatternContainer instanceof clsSecondaryInformation) moSearchResult.add(moM01InformationRepresentationMgmt.moKB01SecondaryDataStructureMgmt.searchDataStructure(poReturnType, poSearchPatternContainer)); 
		else if(poSearchPatternContainer instanceof clsPrimaryInformation){
			if(((clsPrimaryInformation)poSearchPatternContainer).moInternalRepresentationDataStructure != null) moSearchResult.add(moM01InformationRepresentationMgmt.moM02PrimaryInformationMgmt.moKB02InternalPerceptionMgmt.searchDataStructure(poReturnType, poSearchPatternContainer));
			if(((clsPrimaryInformation)poSearchPatternContainer).moExternalRepresetnationDataStructure != null) moSearchResult.add(moM01InformationRepresentationMgmt.moM02PrimaryInformationMgmt.moKB03ExternalPerceptionMgmt.searchDataStructure(poReturnType,poSearchPatternContainer));
		}
		else{ throw new IllegalArgumentException("DataStructureContainerUnknown unknown ");}
	}

}