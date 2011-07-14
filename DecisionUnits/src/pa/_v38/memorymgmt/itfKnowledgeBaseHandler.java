/**
 * itf═nformationRepresentationManagement.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 */
package pa._v38.memorymgmt;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 * 
 */
public interface itfKnowledgeBaseHandler {
	public ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> initMemorySearch(ArrayList<clsPair<Integer,clsDataStructurePA>> poSearchPatternContainer);
	public ArrayList<clsPair<Double,clsDataStructureContainer>> initMemorySearchContainer(clsPair<Integer, clsDataStructureContainer> poSearchPattern);

}
