/**
 * itfÍnformationRepresentationManagement.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 */
package pa._v30.memorymgmt;

import java.util.ArrayList;

import pa._v30.tools.clsPair;
import pa._v30.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 * 
 */
public interface itfKnowledgeBaseHandler {
	public ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> initMemorySearch(ArrayList<clsPair<Integer,clsDataStructurePA>> poSearchPatternContainer);
}
