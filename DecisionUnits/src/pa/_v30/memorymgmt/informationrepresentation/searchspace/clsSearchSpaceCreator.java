/**
 * clsSearchSpaceCreator.java: DecisionUnits - pa.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 */
package pa._v30.memorymgmt.informationrepresentation.searchspace;

import java.util.HashMap;

import pa._v30.memorymgmt.datatypes.clsDataStructurePA;

/**
 *
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 * 
 */
public class clsSearchSpaceCreator {
		
	public static clsSearchSpaceBase createSearchSpace(String poSourceName){
		HashMap<String, clsDataStructurePA> oDataStructureTable = new HashMap<String, clsDataStructurePA>(); 
		clsOntologyLoader.loadOntology(oDataStructureTable, poSourceName); 
		
		return new clsSearchSpaceOntologyLoader(oDataStructureTable);	
	}
}
