/**
 * clsSearchSpaceCreator.java: DecisionUnits - pa.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 */
package pa.memorymgmt.informationrepresentation.searchspace;

import java.util.Hashtable;
import java.util.List;

import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 * 
 */
public class clsSearchSpaceCreator {
		
	public static clsSearchSpaceBase createSearchSpace(){
		Hashtable <eDataType, List<clsDataStructurePA>> oDataStructureTable = new Hashtable<eDataType, List<clsDataStructurePA>>(); 
		
		clsOntologyLoader.loadOntology(oDataStructureTable); 
		return new clsSearchSpaceOntologyLoader(oDataStructureTable);	
	}
}
