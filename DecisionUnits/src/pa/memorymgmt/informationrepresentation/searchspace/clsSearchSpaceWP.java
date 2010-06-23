/**
 * clsSearchSpaceWP.java: DecisionUnits - pa.informationrepresentation.searchspaces
 * 
 * @author zeilinger
 * 23.05.2010, 18:30:08
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
 * 23.05.2010, 18:30:08
 * 
 */
public class clsSearchSpaceWP extends clsSearchSpaceBase{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:13:10
	 *
	 */
	public clsSearchSpaceWP(Hashtable <eDataType, List<clsDataStructurePA>> poDataStructureList) {
		super(poDataStructureList); 
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.05.2010, 20:26:22
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
	 * 31.05.2010, 08:52:25
	 * 
	 * @see pa.informationrepresentation.ARSi10.searchspace.clsSearchSpaceBase#returnSearchSpace(java.lang.String)
	 */
	@Override
	public List<clsDataStructurePA> returnSearchSpace(
			String poSearchSpaceType) {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}
}
