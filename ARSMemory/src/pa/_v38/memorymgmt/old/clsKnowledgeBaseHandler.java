/**
 * clsInformationRepresentationMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:56:25
 */
package pa._v38.memorymgmt.old;

import pa._v38.interfaces.itfInspectorInternalState;
import config.clsProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:56:25
 * @deprecated
 */
public abstract class clsKnowledgeBaseHandler implements itfKnowledgeBaseHandler, itfInspectorInternalState {
	public static final String P_DATABASE_SOURCE = "database_source";
	public static final String P_SEARCH_METHOD = "database_search";
	public static final String P_SOURCE_NAME = "source_name";
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 30.05.2010, 12:33:06
	 *
	 */
	public clsKnowledgeBaseHandler (String poPrefix, clsProperties poProp) {
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
//		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
//		String pre = clsProperties.addDot(poPrefix);
	}
}
