/**
 * itfÍnformationRepresentationManagement.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 */
package pa.memorymgmt;

import java.util.ArrayList;
import java.util.List;

import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 * 
 */
public interface itfKnowledgeBaseHandler {
	public List<List<clsPair<Double,clsDataStructureContainer>>>  initMemorySearch(ArrayList<clsPair<Integer,clsDataStructureContainer>> poSearchPatternContainer);
}
