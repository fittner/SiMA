/**
 * clsSearchSpaceMainMemory.java: DecisionUnits - pa.informationrepresentation.ARSi10.searchspace
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 */
package pa.knowledgebasehandler.infrepmgmt.searchspace;

import java.util.List;
import java.util.Hashtable;

import pa.knowledgebasehandler.datatypes.clsDataStructurePA;
import pa.knowledgebasehandler.infrepmgmt.enums.eSearchSpaceType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 * 
 */
public class clsSearchSpaceMainMemory extends clsSearchSpaceBase{
	Hashtable <eSearchSpaceType, clsSearchSpaceBase> moSearchSpaceList;
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 31.05.2010, 08:21:35
	 * 
	 * @see pa.informationrepresentation.ARSi10.searchspace.clsSearchSpaceBase#loadSearchSpace()
	 */
	@Override
	protected void loadSearchSpace() {
		for (Object element : eSearchSpaceType.values()){
			eSearchSpaceType enumerator = (eSearchSpaceType)element; 
			
			if(enumerator.equals(eSearchSpaceType.THINGPRESENTATION))moSearchSpaceList.put(enumerator, new clsSearchSpaceTPM()); 
			else if(enumerator.equals(eSearchSpaceType.THINGPRESENTATIONMESH))moSearchSpaceList.put(enumerator,new clsSearchSpaceTPM());
			else if(enumerator.equals(eSearchSpaceType.TEMPLATEIMAGE))moSearchSpaceList.put(enumerator,new clsSearchSpaceTI());
			else if(enumerator.equals(eSearchSpaceType.DRIVEMESH))moSearchSpaceList.put(enumerator,new clsSearchSpaceDM());
			else if(enumerator.equals(eSearchSpaceType.WORDPRESENTATION))moSearchSpaceList.put(enumerator, new clsSearchSpaceWP());
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
	public List<clsDataStructurePA> returnSearchSpace(String poSearchSpaceType) {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}

}
