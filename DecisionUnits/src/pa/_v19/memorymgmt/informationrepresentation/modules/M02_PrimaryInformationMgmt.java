/**
 * G02_PrimaryInformationMgmt.java: DecisionUnits - pa.informationrepresentation.modules
 * 
 * @author zeilinger
 * 23.05.2010, 21:37:54
 */
package pa._v19.memorymgmt.informationrepresentation.modules;

import pa._v19.memorymgmt.informationrepresentation.clsSearchSpaceHandler;

/**
 *
 * 
 * @author zeilinger
 * 23.05.2010, 21:37:54
 * 
 */
public class M02_PrimaryInformationMgmt extends clsInformationRepresentationModuleContainer{
	
	public KB02_InternalPerceptionMgmt moKB02InternalPerceptionMgmt;
	public KB03_ExternalPerceptionMgmt moKB03ExternalPerceptionMgmt;
	
	/**
	 *
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
		
}
