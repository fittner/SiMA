/**
 * G02_PrimaryInformationMgmt.java: DecisionUnits - pa.informationrepresentation.modules
 * 
 * @author zeilinger
 * 23.05.2010, 21:37:54
 */
package pa.informationrepresentation.modules;

import pa.informationrepresentation.datatypes.clsDataStructureContainer;
import pa.informationrepresentation.datatypes.clsPrimaryInformation;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:37:54
 * 
 */
public class M02_PrimaryInformationMgmt extends clsInformationRepresentationModuleBase{
	
	public KB02_InternalPerceptionMgmt moKB02InternalPerceptionMgmt;
	public KB03_ExternalPerceptionMgmt moKB03ExternalPerceptionMgmt;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.05.2010, 22:58:39
	 *
	 * @param poInformationRepresentationModulesContainer
	 */
	public M02_PrimaryInformationMgmt(
			clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer) {
		super(poInformationRepresentationModulesContainer);
		moKB02InternalPerceptionMgmt = new KB02_InternalPerceptionMgmt(poInformationRepresentationModulesContainer);
		moKB03ExternalPerceptionMgmt = new KB03_ExternalPerceptionMgmt(poInformationRepresentationModulesContainer); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.05.2010, 23:33:55
	 * 
	 * @see pa.informationrepresentation.modules.clsInformationRepresentationModuleBase#searchDataStructure(java.util.ArrayList)
	 */
	@Override
	public clsDataStructureContainer searchDataStructure(clsDataStructureContainer poSearchPatternContainer) {
		if(((clsPrimaryInformation)poSearchPatternContainer).moInternalRepresentationDataStructure != null)	return moKB02InternalPerceptionMgmt.searchDataStructure(poSearchPatternContainer);
		if(((clsPrimaryInformation)poSearchPatternContainer).moExternalRepresetnationDataStructure != null) return moKB03ExternalPerceptionMgmt.searchDataStructure(poSearchPatternContainer);
		
		throw new NullPointerException("clsDataStructure unknown ");
	}
}
