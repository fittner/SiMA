/**
 * clsInformationRepresentationFactory.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:44:07
 */
package pa.informationrepresentation;

import pa.informationrepresentation.ARSi10.clsInformationRepresentationManagementARSi10;
import pa.informationrepresentation.enums.eInformationRepresentationManagementType;
import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 12:44:07
 * 
 */
public class clsInformationRepresentationManagementFactory implements itfInformationRepresentationManagementFactory{

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.05.2010, 13:14:17
	 * 
	 * @see pa.informationrepresentation.itfInformationRepresentationManagementFactory#createInformationRepresentationManagement(pa.informationrepresentation.enums.eInformationRepresentationManagementType, java.lang.String, config.clsBWProperties)
	 */
	@Override
	public itfÍnformationRepresentationManagement createInformationRepresentationManagement(
			eInformationRepresentationManagementType pnInformationRepresentationManagementType,
			String poPrefix, clsBWProperties poProp)throws IllegalArgumentException {
		//TODO HZ intitialize the informationrepresentation in G00
		return createInformationRepresentationManagement_static(pnInformationRepresentationManagementType, poPrefix, poProp);
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
	private static itfÍnformationRepresentationManagement createInformationRepresentationManagement_static(
			eInformationRepresentationManagementType pnInformationRepresentationManagementType,
			String poPrefix, clsBWProperties poProp) {
		
		clsInformationRepresentationManagement oInformationRepresentationManagement = null; 
		
		switch(pnInformationRepresentationManagementType){
			case ARSI09_MGMT:
				//TODO adapt ARSI09 to the actual InformationReprsentation Management 
				break;
			case ARSI10_MGMT:
				oInformationRepresentationManagement = new clsInformationRepresentationManagementARSi10(poPrefix, poProp);  
				break; 
			default:
				throw new java.lang.IllegalArgumentException("eInformationRepresentationManagement."+pnInformationRepresentationManagementType.name());
		}
		return (itfÍnformationRepresentationManagement) oInformationRepresentationManagement;
	}
	
	
	public static clsBWProperties getDefaultProperties(eInformationRepresentationManagementType pnInformationRepresentationManagementType, String poPrefix) throws java.lang.IllegalArgumentException {
		clsBWProperties oProps = null;
		//TODO (zeilinger) set default properties
		return oProps; 
	}
}
