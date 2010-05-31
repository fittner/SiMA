/**
 * clsInformationRepresentationModuleContainer.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:02
 */
package pa.informationrepresentation.ARSi10.modules;

import pa.informationrepresentation.ARSi10.clsSearchSpaceHandler;
import pa.modules.clsModuleContainer;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:02
 * 
 */
public class clsInformationRepresentationModuleContainer {
	protected clsModuleContainer moEnclosingContainer;
	protected clsSearchSpaceHandler moSearchSpaceHandler; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:21:10
	 * @param moSearchSpaceHandler 
	 *
	 * @param object
	 */
	public clsInformationRepresentationModuleContainer(clsModuleContainer poModuleContainer, clsSearchSpaceHandler poSearchSpaceHandler) {
		moEnclosingContainer = poModuleContainer;
		moSearchSpaceHandler = poSearchSpaceHandler; 
	}

}
