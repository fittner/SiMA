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
	protected List<clsDataStructurePA> moSearchSpaceEntries; 
	protected Hashtable <eDataType, List<clsDataStructurePA>> moDataStructureList;
	
	public clsSearchSpaceBase(Hashtable <eDataType, List<clsDataStructurePA>> poDataStructureList){
		moSearchSpaceEntries = new ArrayList<clsDataStructurePA>(); 
		moDataStructureList = poDataStructureList; 
		this.loadSearchSpace(); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 09.06.2010, 22:04:04
	 *
	 */
	protected abstract void loadSearchSpace(); 
	public abstract List<clsDataStructurePA> returnSearchSpace(String poSearchSpaceType);  
}
