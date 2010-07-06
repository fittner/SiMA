/**
 * clsSearchSpaceBase.java: DecisionUnits - pa.informationrepresentation.searchspaces
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 */
package pa.memorymgmt.informationrepresentation.searchspace;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
	protected Hashtable <eDataType, List<clsDataStructurePA>> moDataStructureTable;
	
	public clsSearchSpaceBase(Hashtable <eDataType, List<clsDataStructurePA>> poDataStructureTable){
		moDataStructureTable = poDataStructureTable; 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 09.06.2010, 22:04:04
	 *
	 */
	public abstract Hashtable<clsDataStructurePA, ArrayList<clsAssociation>> returnSearchSpaceTable(eDataType poDataStructureType);  
}
