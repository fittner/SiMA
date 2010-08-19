/**
 * clsSearchSpaceBase.java: DecisionUnits - pa.informationrepresentation.searchspaces
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 */
package pa.memorymgmt.informationrepresentation.searchspace;

import java.util.ArrayList;
import java.util.HashMap;

import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 * 
 */
public abstract class clsSearchSpaceBase {
	protected HashMap<String, clsDataStructurePA> moDataStructureTable;
	
	public clsSearchSpaceBase(HashMap<String, clsDataStructurePA> poDataStructureTable){
		moDataStructureTable = poDataStructureTable; 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 09.06.2010, 22:04:04
	 *
	 */
	public abstract HashMap<eDataType,HashMap<String, HashMap<clsDataStructurePA, ArrayList<clsAssociation>>>> returnSearchSpaceTable();  
}
