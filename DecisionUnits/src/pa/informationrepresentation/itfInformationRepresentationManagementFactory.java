/**
 * itfInformationRepresentationManagament.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:23:36
 */
package pa.informationrepresentation;

import pa.informationrepresentation.enums.eInformationRepresentationManagementType;
import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 12:23:36
 * 
 */
public interface itfInformationRepresentationManagementFactory {
	public itfÍnformationRepresentationManagement createInformationRepresentationManagement(eInformationRepresentationManagementType pnInformationRepresentationManagementType, 
			String poPrefix, clsBWProperties poProp) throws java.lang.IllegalArgumentException;
}
