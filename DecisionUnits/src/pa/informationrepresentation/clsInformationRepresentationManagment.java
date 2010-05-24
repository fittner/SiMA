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
		moM01InformationRepresentationMgmt = new M01_InformationRepresentationMgmt(); 
		moSearchSpaceHandler.createSearchSpaceList();
	}
	
	public ArrayList<clsDataStructureContainer> searchDataStructure(ArrayList<clsDataStructureContainer> poSearchPatternContainer){
		moSearchResult.clear(); 
		for(Iterator <clsDataStructureContainer> i = poSearchPatternContainer.iterator();i.hasNext();){
			 moSearchResult.add(moM01InformationRepresentationMgmt.searchDataStructure(i.next())); 
		}
		
		return moSearchResult;  
	}
}
