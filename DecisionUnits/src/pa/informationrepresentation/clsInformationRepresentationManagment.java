/**
 * clsInformationRepresentationMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:56:25
 */
package pa.informationrepresentation;

import java.util.ArrayList;
import java.util.Iterator;

import pa.informationrepresentation.datatypes.clsDataStructureContainer;
import pa.informationrepresentation.datatypes.clsPrimaryInformation;
import pa.informationrepresentation.datatypes.clsSecondaryInformation;
import pa.informationrepresentation.modules.M01_InformationRepresentationMgmt;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:56:25
 * 
 */
public class clsInformationRepresentationManagment {
	public M01_InformationRepresentationMgmt moM01InformationRepresentationMgmt;
	public clsSearchSpaceHandler moSearchSpaceHandler; 
	public ArrayList<clsDataStructureContainer> moSearchResult; 
	
	public clsInformationRepresentationManagment(){
		moSearchSpaceHandler.createSearchSpaceList();
		moM01InformationRepresentationMgmt = new M01_InformationRepresentationMgmt(moSearchSpaceHandler); 
	}
	
	public ArrayList<clsDataStructureContainer> searchDataStructure(ArrayList<clsDataStructureContainer> poSearchPatternContainer){
		moSearchResult.clear(); 
		for(Iterator <clsDataStructureContainer> i = poSearchPatternContainer.iterator();i.hasNext();){
			triggerInformationRepresentationManagementModules(i.next());  
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
	private void triggerInformationRepresentationManagementModules(clsDataStructureContainer poSearchPatternContainer) {
		
		if(poSearchPatternContainer instanceof clsSecondaryInformation) moSearchResult.add(moM01InformationRepresentationMgmt.moKB01SecondaryDataStructureMgmt.searchDataStructure(poSearchPatternContainer)); 
		else if(poSearchPatternContainer instanceof clsPrimaryInformation){
			if(((clsPrimaryInformation)poSearchPatternContainer).moInternalRepresentationDataStructure != null) moSearchResult.add(moM01InformationRepresentationMgmt.moM02PrimaryInformationMgmt.moKB02InternalPerceptionMgmt.searchDataStructure(poSearchPatternContainer));
			if(((clsPrimaryInformation)poSearchPatternContainer).moExternalRepresetnationDataStructure != null) moSearchResult.add(moM01InformationRepresentationMgmt.moM02PrimaryInformationMgmt.moKB03ExternalPerceptionMgmt.searchDataStructure(poSearchPatternContainer));
		}
		else{ throw new NullPointerException("DataStructureContainerUnknown unknown ");}
	}
}
