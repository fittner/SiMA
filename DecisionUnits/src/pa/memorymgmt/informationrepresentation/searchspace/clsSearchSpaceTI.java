/**
 * clsSearchSpaceTI.java: DecisionUnits - pa.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 24.05.2010, 12:13:46
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
 * 24.05.2010, 12:13:46
 * 
 */
public class clsSearchSpaceTI extends clsSearchSpaceBase{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:14:05
	 *
	 */
	public clsSearchSpaceTI(Hashtable <eDataType, List<clsDataStructurePA>> poDataStructureList) {
		super(poDataStructureList); 
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 24.05.2010, 12:14:00
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
	 * 31.05.2010, 08:51:45
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
