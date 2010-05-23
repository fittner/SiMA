/**
 * G01_InformationRepresentationManagement.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:54
 */
package pa.informationrepresentation.modules;

import java.util.ArrayList;

import pa.informationrepresentation.datatypes.clsSecondaryInformation;
import pa.informationrepresentation.datatypes.clsDataStructureComposition;
import pa.informationrepresentation.datatypes.clsDatastructure;
import pa.informationrepresentation.datatypes.clsPrimaryInformation;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:54
 * 
 */
public class M01_InformationRepresentationMgmt extends clsInformationRepresentationModuleContainer{
	public KB01_SecondaryDataStructureMgmt moKB01SecondaryDataStructureMgmt; 
	public M02_PrimaryInformationMgmt moM02PrimaryInformationMgmt;
	
	public M01_InformationRepresentationMgmt(){
		moKB01SecondaryDataStructureMgmt = new KB01_SecondaryDataStructureMgmt(this); 
		moM02PrimaryInformationMgmt = new M02_PrimaryInformationMgmt(this); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.05.2010, 23:21:26
	 * @param poSearchPattern 
	 *
	 * @return
	 */
	public ArrayList<clsDataStructureComposition> searchDataStructure(ArrayList<clsDatastructure> poSearchPattern) {
		if(((clsDatastructure)poSearchPattern.get(0))instanceof clsPrimaryInformation)	return moKB01SecondaryDataStructureMgmt.searchDataStructure(poSearchPattern); 
		if(((clsDatastructure)poSearchPattern.get(0))instanceof clsSecondaryInformation)	return moM02PrimaryInformationMgmt.searchDataStructure(poSearchPattern); 
		
		throw new NullPointerException("clsDataStructure unknown ");
	}
}
