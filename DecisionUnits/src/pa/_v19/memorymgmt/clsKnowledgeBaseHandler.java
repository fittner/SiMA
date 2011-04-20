/**
 * clsInformationRepresentationMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:56:25
 */
package pa._v19.memorymgmt;

import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:56:25
 * 
 */
public abstract class clsKnowledgeBaseHandler implements itfKnowledgeBaseHandler{
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
	public clsKnowledgeBaseHandler (String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);
	}
}
