/**
 * clsSecondaryDataStructureMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:47:02
 */
package pa.informationrepresentation.bwsearch.modules;

import pa.informationrepresentation.bwsearch.clsSearchSpaceHandler;
import pa.informationrepresentation.datatypes.clsDataStructureContainer;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:47:02
 * 
 */
public class KB01_SecondaryDataStructureMgmt extends clsInformationRepresentationModuleBase{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.05.2010, 22:47:52
	 *
	 * @param poInformationRepresentationModulesContainer
	 * @param poSearchSpaceHandler 
	 */
	public KB01_SecondaryDataStructureMgmt(
			clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer, 
												clsSearchSpaceHandler poSearchSpaceHandler, String poSearchMethod) {
		super(poInformationRepresentationModulesContainer, poSearchSpaceHandler, poSearchMethod);
		// TODO (zeilinger) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 31.05.2010, 16:06:40
	 * 
	 * @see pa.informationrepresentation.ARSi10.modules.clsInformationRepresentationModuleBase#listSearch(java.lang.String, pa.informationrepresentation.datatypes.clsDataStructureContainer)
	 */
	@Override
	public clsDataStructureContainer listSearch(String poReturnType,
			clsDataStructureContainer poSearchPatternContainer) {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}
}
