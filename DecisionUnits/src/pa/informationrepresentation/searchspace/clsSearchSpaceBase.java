/**
 * clsSearchSpaceBase.java: DecisionUnits - pa.informationrepresentation.searchspaces
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 */
package pa.informationrepresentation.searchspace;

import java.util.ArrayList;

import pa.informationrepresentation.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 * 
 */
public abstract class clsSearchSpaceBase {
	protected ArrayList<clsDataStructurePA> moSearchSpaceEntries;  
	
	public clsSearchSpaceBase(){
		moSearchSpaceEntries = new ArrayList<clsDataStructurePA>(); 
		this.loadSearchSpace(); 
	}
	
	protected abstract void loadSearchSpace(); 
	protected abstract void returnSearchSpace();  
}
