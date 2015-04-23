/**
 * clsSearchSpaceBase.java: DecisionUnits - pa.informationrepresentation.searchspaces
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 */
package pa._v38.memorymgmt.framessearchspace;

import inspector.interfaces.itfInspectorInternalState;

import java.util.ArrayList;
import java.util.HashMap;

import memorymgmt.enums.eDataType;
import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructurePA;
import base.datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 * 
 */
public abstract class clsSearchSpaceBase implements itfInspectorInternalState {
	protected HashMap<String, clsDataStructurePA> moDataStructureTable;
	
	public clsSearchSpaceBase(HashMap<String, clsDataStructurePA> poDataStructureTable){
		moDataStructureTable = poDataStructureTable; 
	}
	
	public HashMap<String, clsDataStructurePA> getDataStructureTable() {
		return moDataStructureTable;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 09.06.2010, 22:04:04
	 *
	 */
	public abstract HashMap<eDataType,HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>>> returnSearchSpaceTable();  
}
