/**
 * clsSearchSpaceTP.java: DecisionUnits - pa.informationrepresentation.searchspaces
 * 
 * @author zeilinger
 * 23.05.2010, 18:30:20
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
 * 23.05.2010, 18:30:20
 * 
 */
public class clsSearchSpaceTP extends clsSearchSpaceBase{
	
	public clsSearchSpaceTP(Hashtable <eDataType, List<clsDataStructurePA>> poDataStructureList){
		super(poDataStructureList); 
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.05.2010, 20:26:04
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
	 * 31.05.2010, 08:52:03
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
