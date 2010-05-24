/**
 * clsInformationRepresentationModuleBase.java: DecisionUnits - pa.informationrepresentation.modules
 * 
 * @author zeilinger
 * 23.05.2010, 21:36:06
 */
package pa.informationrepresentation.modules;

import pa.informationrepresentation.datatypes.clsDataStructureContainer;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:36:06
 * 
 */
public abstract class clsInformationRepresentationModuleBase {
	protected clsInformationRepresentationModuleContainer moInformationRepresentationModulesContainer; 
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.05.2010, 22:45:28
	 *
	 */
	public clsInformationRepresentationModuleBase(clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer) {
		// TODO (zeilinger) - Auto-generated constructor stub
		moInformationRepresentationModulesContainer = poInformationRepresentationModulesContainer; 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 09:40:10
	 *
	 * @param poSearchPattern
	 * @return
	 */
	public abstract clsDataStructureContainer searchDataStructure(clsDataStructureContainer poSearchPatternContainer);
}
