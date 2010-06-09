/**
 * clsSearchSpaceDB.java: DecisionUnits - pa.informationrepresentation.ARSi10.searchspace
 * 
 * @author zeilinger
 * 31.05.2010, 16:13:24
 */
package pa.informationrepresentation.bwsearch.searchspace;

import java.util.List;

import pa.informationrepresentation.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 31.05.2010, 16:13:24
 * 
 */
public class clsSearchSpaceDB extends clsSearchSpaceBase{

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 31.05.2010, 16:20:26
	 * 
	 * @see pa.informationrepresentation.ARSi10.searchspace.clsSearchSpaceBase#loadSearchSpace()
	 */
	@Override
	protected void loadSearchSpace() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 31.05.2010, 16:20:26
	 * 
	 * @see pa.informationrepresentation.ARSi10.searchspace.clsSearchSpaceBase#returnSearchSpace(java.lang.String)
	 */
	@Override
	public List<clsDataStructurePA> returnSearchSpace(
			String poSearchSpaceType) {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}
	/*TODO zeilinger: In case required data is read from the database, the connection is initialized
		   here*/
}
