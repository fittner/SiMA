/**
 * clsInformationRepresentationModuleContainer.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:02
 */
package pa._v30.memorymgmt.informationrepresentation.modules;

import pa._v30.interfaces.itfInspectorInternalState;
import pa._v30.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
import pa._v30.tools.toText;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:45:02
 * 
 */
public class clsInformationRepresentationModuleContainer implements itfInspectorInternalState {
	//protected clsModuleContainer moEnclosingContainer;
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
	public clsInformationRepresentationModuleContainer(//clsModuleContainer poModuleContainer, 
								clsSearchSpaceHandler poSearchSpaceHandler, String poSearchMethod) {
		//moEnclosingContainer = poModuleContainer;
		moSearchSpaceHandler = poSearchSpaceHandler; 
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 16:45:04
	 * 
	 * @see pa._v30.interfaces.itfInspectorInternalState#stateToHTML()
	 */
	@Override
	public String stateToTEXT() {
		String html = "";
		
		html += toText.h1("clsInformationRepresentationModuleContainer");
		html += toText.newline+moSearchSpaceHandler.stateToTEXT();
		
		return html;
	}

}
