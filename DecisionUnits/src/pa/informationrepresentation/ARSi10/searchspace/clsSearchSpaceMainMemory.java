/**
 * clsSearchSpaceMainMemory.java: DecisionUnits - pa.informationrepresentation.ARSi10.searchspace
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 */
package pa.informationrepresentation.ARSi10.searchspace;

import java.util.ArrayList;
import java.util.Hashtable;

import pa.informationrepresentation.ARSi10.enums.eSearchSpace;
import pa.informationrepresentation.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 * 
 */
public class clsSearchSpaceMainMemory extends clsSearchSpaceBase{
	Hashtable <eSearchSpace, clsSearchSpaceBase> moSearchSpaceList;
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 31.05.2010, 08:21:35
	 * 
	 * @see pa.informationrepresentation.ARSi10.searchspace.clsSearchSpaceBase#loadSearchSpace()
	 */
	@Override
	protected void loadSearchSpace() {
		for (Object element : eSearchSpace.values()){
			eSearchSpace enumerator = (eSearchSpace)element; 
			
			if(enumerator.equals(eSearchSpace.THINGPRESENTATION))moSearchSpaceList.put(enumerator, new clsSubSearchSpaceTPM()); 
			else if(enumerator.equals(eSearchSpace.THINGPRESENTATIONMESH))moSearchSpaceList.put(enumerator,new clsSubSearchSpaceTPM());
			else if(enumerator.equals(eSearchSpace.TEMPLATEIMAGE))moSearchSpaceList.put(enumerator,new clsSubSearchSpaceTI());
			else if(enumerator.equals(eSearchSpace.DRIVEMESH))moSearchSpaceList.put(enumerator,new clsSubSearchSpaceDM());
			else if(enumerator.equals(eSearchSpace.WORDPRESENTATION))moSearchSpaceList.put(enumerator, new clsSubSearchSpaceWP());
			else throw new java.lang.NullPointerException("unkown searchspace type: " + enumerator.toString());
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 31.05.2010, 08:52:42
	 * 
	 * @see pa.informationrepresentation.ARSi10.searchspace.clsSearchSpaceBase#returnSearchSpace(java.lang.String)
	 */
	@Override
	public ArrayList<clsDataStructurePA> returnSearchSpace(String poSearchSpaceType) {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}

}
