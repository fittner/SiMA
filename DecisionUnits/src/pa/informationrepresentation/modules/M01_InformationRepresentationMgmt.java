/**
 * G01_InformationRepresentationManagement.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:54
 */
package pa.informationrepresentation.modules;

import pa.informationrepresentation.clsSearchSpaceHandler;
import pa.informationrepresentation.datatypes.clsDataStructureContainer;
import pa.informationrepresentation.datatypes.clsSecondaryInformation;
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
	
	public M01_InformationRepresentationMgmt(clsSearchSpaceHandler poSearchSpaceHandler){
		super(null, poSearchSpaceHandler);
		moKB01SecondaryDataStructureMgmt = new KB01_SecondaryDataStructureMgmt(this, poSearchSpaceHandler); 
		moM02PrimaryInformationMgmt = new M02_PrimaryInformationMgmt(this, poSearchSpaceHandler); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.05.2010, 23:21:26
	 * @param poSearchPattern 
	 * @return 
	 *
	 * @return
	 */
	public clsDataStructureContainer searchDataStructure(clsDataStructureContainer poSearchPatternContainer) {
		
			if(poSearchPatternContainer instanceof clsSecondaryInformation)return moKB01SecondaryDataStructureMgmt.searchDataStructure(poSearchPatternContainer); 
			if(poSearchPatternContainer instanceof clsPrimaryInformation)return moM02PrimaryInformationMgmt.searchDataStructure(poSearchPatternContainer); 
						
		throw new NullPointerException("clsDataStructure unknown ");
	}
}
