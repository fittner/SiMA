/**
 * clsInformationRepresentationFactory.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:44:07
 */
package pa._v38.memorymgmt.old;

import pa._v38.memorymgmt.enums.eInformationRepresentationManagementType;
import config.clsProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 12:44:07
 * @deprecated
 */
public class clsKnowledgeBaseHandlerFactory {

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.05.2010, 13:14:17
	 * 
	 * @see pa.informationrepresentation.itfInformationRepresentationManagementFactory#createInformationRepresentationManagement(pa.informationrepresentation.enums.eInformationRepresentationManagementType, java.lang.String, config.clsProperties)
	 */
	
	public static clsKnowledgeBaseHandler createInformationRepresentationManagement(
			String poInformationRepresentationManagementType,
			String poPrefix, clsProperties poProp)throws IllegalArgumentException {
		//TODO HZ intitialize the informationrepresentation in G00
		return createInformationRepresentationManagement_static(poInformationRepresentationManagementType, poPrefix, poProp);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 30.05.2010, 13:16:58
	 *
	 * @param pnInformationRepresentationManagementType
	 * @param poPrefix
	 * @param poProp
	 * @return
	 */
	private static clsKnowledgeBaseHandler createInformationRepresentationManagement_static(
			String poInformationRepresentationManagementType,
			String poPrefix, clsProperties poProp) {
		
		clsKnowledgeBaseHandler oInformationRepresentationManagement = null; 
		
		switch(eInformationRepresentationManagementType.valueOf(poInformationRepresentationManagementType)){
			case ARSI09_MGMT:
				//TODO adapt ARSI09 to the actual InformationReprsentation Management 
				break;
			case ARSI10_MGMT:
				poProp.putAll(clsInformationRepresentationManagement.getDefaultProperties(poPrefix) );
				oInformationRepresentationManagement = new clsInformationRepresentationManagement(poPrefix, poProp);  
				break; 
			default:
				throw new java.lang.IllegalArgumentException("eInformationRepresentationManagement." + poInformationRepresentationManagementType);
		}
		return oInformationRepresentationManagement;
	}
	
	
	public static clsProperties getDefaultProperties(eInformationRepresentationManagementType pnInformationRepresentationManagementType, String poPrefix) throws java.lang.IllegalArgumentException {
		clsProperties oProps = null;
		//TODO (zeilinger) set default properties
		return oProps; 
	}
}
