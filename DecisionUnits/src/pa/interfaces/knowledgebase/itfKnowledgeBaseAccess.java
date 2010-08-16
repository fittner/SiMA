/**
 * itfKnowledgeBaseHandler.java: DecisionUnits - pa.interfaces.knowledgebase
 * 
 * @author zeilinger
 * 12.08.2010, 20:45:36
 */
package pa.interfaces.knowledgebase;

import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 12.08.2010, 20:45:36
 * 
 */
public interface itfKnowledgeBaseAccess {
	public ArrayList<clsPair<Integer, clsDataStructurePA>> moSearchPattern = new ArrayList<clsPair<Integer, clsDataStructurePA>>() ; 
	public abstract ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> accessKnowledgeBase();
	public abstract void addToSearchPattern(eDataType oReturnType, clsDataStructurePA poSearchPattern); 
}
