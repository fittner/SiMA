/**
 * clsSearchSpaceBase.java: DecisionUnits - pa.informationrepresentation.searchspaces
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 */
package pa.memorymgmt.informationrepresentation.searchspace;

import java.util.ArrayList;
import java.util.List;

import pa.memorymgmt.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 * 
 */
public abstract class clsSearchSpaceBase {
	protected List<clsDataStructurePA> moSearchSpaceEntries;  
	
	public clsSearchSpaceBase(){
		moSearchSpaceEntries = new ArrayList<clsDataStructurePA>(); 
		this.loadSearchSpace(); 
	}
	
	protected abstract void loadSearchSpace(); 
	public abstract List<clsDataStructurePA> returnSearchSpace(String poSearchSpaceType);  
}
