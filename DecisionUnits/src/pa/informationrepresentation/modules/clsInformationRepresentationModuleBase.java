/**
 * clsInformationRepresentationModuleBase.java: DecisionUnits - pa.informationrepresentation.modules
 * 
 * @author zeilinger
 * 23.05.2010, 21:36:06
 */
package pa.informationrepresentation.modules;

import java.util.ArrayList;

import pa.informationrepresentation.datatypes.clsDataStructureComposition;
import pa.informationrepresentation.datatypes.clsDatastructure;

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
	
	public abstract ArrayList<clsDataStructureComposition> searchDataStructure(ArrayList<clsDatastructure> poSearchPattern); 
}
