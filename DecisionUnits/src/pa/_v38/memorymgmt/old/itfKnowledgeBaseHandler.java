/**
 * itfÍnformationRepresentationManagement.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 */
package pa._v38.memorymgmt.old;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 * @deprecated
 */
public interface itfKnowledgeBaseHandler {
	public ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> initMemorySearch(ArrayList<clsPair<Integer,clsDataStructurePA>> poSearchPatternContainer);
	//public ArrayList<clsPair<Double,clsDataStructureContainer>> initMemorySearchContainer(clsPair<Integer, clsDataStructureContainer> poSearchPattern, double prThreshold);
	public ArrayList<clsPair<Double,clsDataStructurePA>> initMemorySearchMesh(clsPair<Integer, clsDataStructurePA> poSearchPattern, double prThreshold, int pnLevel);
	//public clsDataStructureContainer initContainerRetrieval(clsDataStructurePA poInput);
	public clsDataStructurePA initMeshRetrieval(clsDataStructurePA poInput, int pnLevel);
}
