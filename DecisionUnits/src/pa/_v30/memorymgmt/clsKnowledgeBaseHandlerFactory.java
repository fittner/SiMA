/**
 * clsInformationRepresentationFactory.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:44:07
 */
package pa._v30.memorymgmt;

import pa._v30.memorymgmt.enums.eInformationRepresentationManagementType;
import pa._v30.memorymgmt.informationrepresentation.clsInformationRepresentationManagement;
import config.clsProperties;

/**
 *
 * 
 * @author zeilinger
 * 30.05.2010, 12:44:07
 * 
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
		// HZ intitialize the informationrepresentation in G00
		return createInformationRepresentationManagement_static(poInformationRepresentationManagementType, poPrefix, poProp);
	}

	/**
	 *
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
				// adapt ARSI09 to the actual InformationReprsentation Management 
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
		// (zeilinger) set default properties
		return oProps; 
	}
}
