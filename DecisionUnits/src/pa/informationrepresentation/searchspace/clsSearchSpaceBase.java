/**
 * clsSearchSpaceBase.java: DecisionUnits - pa.informationrepresentation.searchspaces
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 */
package pa.informationrepresentation.searchspace;

import java.util.ArrayList;

import pa.informationrepresentation.datatypes.clsDataStructureComposition;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:29:05
 * 
 */
public abstract class clsSearchSpaceBase {
	protected ArrayList<clsDataStructureComposition> moSearchSpaceEntries;  
	
	public clsSearchSpaceBase(){
		moSearchSpaceEntries = new ArrayList<clsDataStructureComposition>(); 
		this.loadSearchSpace(); 
	}
	
	protected abstract void loadSearchSpace(); 
	protected abstract void returnSearchSpace();  
}
