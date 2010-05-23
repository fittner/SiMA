/**
 * clsInformationRepresentationMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:56:25
 */
package pa.informationrepresentation;

import java.util.ArrayList;

import pa.informationrepresentation.datatypes.clsDataStructureComposition;
import pa.informationrepresentation.datatypes.clsDatastructure;
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
	
	public clsInformationRepresentationManagment(){
		moM01InformationRepresentationMgmt = new M01_InformationRepresentationMgmt(); 
		moSearchSpaceHandler.createSearchSpaceList();
	}
	
	public ArrayList<clsDataStructureComposition> searchDataStructure(ArrayList<clsDatastructure> poSearchPattern){
		return moM01InformationRepresentationMgmt.searchDataStructure(poSearchPattern); 
		//TODO HZ: extend Exception by DataStructureType
	}
}
