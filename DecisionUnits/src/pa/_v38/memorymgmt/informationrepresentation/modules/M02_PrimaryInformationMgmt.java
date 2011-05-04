/**
 * G02_PrimaryInformationMgmt.java: DecisionUnits - pa.informationrepresentation.modules
 * 
 * @author zeilinger
 * 23.05.2010, 21:37:54
 */
package pa._v38.memorymgmt.informationrepresentation.modules;

import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
import pa._v38.tools.toText;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:37:54
 * 
 */
public class M02_PrimaryInformationMgmt extends clsInformationRepresentationModuleContainer  implements itfInspectorInternalState {
	
	public KB02_InternalPerceptionMgmt moKB02InternalPerceptionMgmt;
	public KB03_ExternalPerceptionMgmt moKB03ExternalPerceptionMgmt;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.05.2010, 22:58:39
	 *
	 * @param poInformationRepresentationModulesContainer
	 * @param poSearchSpaceHandler 
	 */
	public M02_PrimaryInformationMgmt(
			clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer, clsSearchSpaceHandler poSearchSpaceHandler, String poSearchMethod) {
//		super(null, poSearchSpaceHandler, poSearchMethod);
		super(poSearchSpaceHandler, poSearchMethod);
		moKB02InternalPerceptionMgmt = new KB02_InternalPerceptionMgmt(poInformationRepresentationModulesContainer, poSearchSpaceHandler, poSearchMethod);
		moKB03ExternalPerceptionMgmt = new KB03_ExternalPerceptionMgmt(poInformationRepresentationModulesContainer, poSearchSpaceHandler, poSearchMethod); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 16:45:04
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToHTML()
	 */
	@Override
	public String stateToTEXT() {
		String html = "";
		
		html += toText.h1("clsInformationRepresentationModuleContainer");
		html += toText.newline+moSearchSpaceHandler.stateToTEXT();
		html += toText.newline+moKB02InternalPerceptionMgmt.stateToTEXT();
		html += toText.newline+moKB03ExternalPerceptionMgmt.stateToTEXT();
		
		return html;
	}	
		
}
