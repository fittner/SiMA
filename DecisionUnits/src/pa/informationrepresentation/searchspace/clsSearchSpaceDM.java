/**
 * clsSearchSpaceDM.java: DecisionUnits - pa.informationrepresentation.searchspaces
 * 
 * @author zeilinger
 * 23.05.2010, 18:30:43
 */
package pa.informationrepresentation.searchspace;

import java.util.ArrayList;

import pa.informationrepresentation.datatypes.clsDataStructureComposition;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:30:43
 * 
 */
public class clsSearchSpaceDM extends clsSearchSpaceBase{

	public clsSearchSpaceDM(){
		moSearchSpaceEntries = new ArrayList<clsDataStructureComposition>(); 
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.05.2010, 20:25:53
	 * 
	 * @see pa.informationrepresentation.searchspace.clsSearchSpaceBase#loadSearchSpace()
	 */
	@Override
	protected void loadSearchSpace() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.05.2010, 21:11:30
	 * 
	 * @see pa.informationrepresentation.searchspace.clsSearchSpaceBase#returnSearchSpace()
	 */
	@Override
	protected void returnSearchSpace() {
		// TODO (zeilinger) - Auto-generated method stub
	}
}
