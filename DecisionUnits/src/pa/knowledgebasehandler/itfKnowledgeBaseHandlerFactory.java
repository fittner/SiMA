/**
 * itfInformationRepresentationManagament.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:23:36
 */
package pa.knowledgebasehandler;

import pa.knowledgebasehandler.enums.eInformationRepresentationManagementType;
import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 12:23:36
 * 
 */
public interface itfKnowledgeBaseHandlerFactory {
	public itfKnowledgeBaseHandler createInformationRepresentationManagement(eInformationRepresentationManagementType pnInformationRepresentationManagementType, 
			String poPrefix, clsBWProperties poProp) throws java.lang.IllegalArgumentException;
}
