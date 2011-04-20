/**
 * itfKnowledgeBaseHandler.java: DecisionUnits - pa.interfaces.knowledgebase
 * 
 * @author zeilinger
 * 12.08.2010, 20:45:36
 */
package pa._v19.interfaces.knowledgebase;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsDataStructurePA;
import pa._v19.memorymgmt.enums.eDataType;
import pa._v19.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 12.08.2010, 20:45:36
 * 
 */
public interface itfKnowledgeBaseAccess {
	//public ArrayList<clsPair<Integer, clsDataStructurePA>> moSearchPattern = new ArrayList<clsPair<Integer, clsDataStructurePA>>();
	public abstract <E> void search(eDataType poDataType, ArrayList<E> poPattern,
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult);
	public abstract <E> void createSearchPattern(eDataType poDataType, ArrayList<E> poList,
			ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern);
	public abstract void accessKnowledgeBase(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult, 
			ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern);
}
