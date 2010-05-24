/**
 * G02_PrimaryInformationMgmt.java: DecisionUnits - pa.informationrepresentation.modules
 * 
 * @author zeilinger
 * 23.05.2010, 21:37:54
 */
package pa.informationrepresentation.modules;

import pa.informationrepresentation.clsSearchSpaceHandler;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:37:54
 * 
 */
public class M02_PrimaryInformationMgmt extends clsInformationRepresentationModuleContainer{
	
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
			clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer, clsSearchSpaceHandler poSearchSpaceHandler) {
		super(null, poSearchSpaceHandler);
		moKB02InternalPerceptionMgmt = new KB02_InternalPerceptionMgmt(poInformationRepresentationModulesContainer, poSearchSpaceHandler);
		moKB03ExternalPerceptionMgmt = new KB03_ExternalPerceptionMgmt(poInformationRepresentationModulesContainer, poSearchSpaceHandler); 
	}
		
}
