/**
 * G02_PrimaryInformationMgmt.java: DecisionUnits - pa.informationrepresentation.modules
 * 
 * @author zeilinger
 * 23.05.2010, 21:37:54
 */
package pa.informationrepresentation.modules;

import java.util.ArrayList;

import pa.informationrepresentation.datatypes.clsDataStructureComposition;
import pa.informationrepresentation.datatypes.clsDatastructure;
import pa.informationrepresentation.datatypes.clsHomeostaticRepresentation;
import pa.informationrepresentation.datatypes.clsPhysicalRepresentation;

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
	public ArrayList<clsDataStructureComposition> searchDataStructure(ArrayList<clsDatastructure> poSearchPattern) {
		if(((clsDatastructure)poSearchPattern.get(0))instanceof clsHomeostaticRepresentation)	return moKB02InternalPerceptionMgmt.searchDataStructure(poSearchPattern);
		if(((clsDatastructure)poSearchPattern.get(0))instanceof clsPhysicalRepresentation)	return moKB03ExternalPerceptionMgmt.searchDataStructure(poSearchPattern);
		
		throw new NullPointerException("clsDataStructure unknown ");
	}
}
