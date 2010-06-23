/**
 * clsSearchSpaceCreator.java: DecisionUnits - pa.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 */
package pa.memorymgmt.informationrepresentation.searchspace;

import java.util.Hashtable;
import java.util.List;

import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.informationrepresentation.enums.eSearchSpaceType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 * 
 */
public class clsSearchSpaceCreator {
		
	public static Hashtable <eSearchSpaceType, clsSearchSpaceBase> createSearchSpace(){
		Hashtable <eSearchSpaceType, clsSearchSpaceBase> oSearchSpaceList = new Hashtable<eSearchSpaceType, clsSearchSpaceBase>();
		Hashtable <eDataType, List<clsDataStructurePA>> oDataStructureList = new Hashtable<eDataType, List<clsDataStructurePA>>(); 
		
		clsOntologyLoader.loadOntology(oDataStructureList); 
		
		for (Object element : eSearchSpaceType.values()){
				eSearchSpaceType enumerator = (eSearchSpaceType)element; 
				
				if(enumerator.equals(eSearchSpaceType.THINGPRESENTATION))oSearchSpaceList.put(enumerator, new clsSearchSpaceTPM(oDataStructureList)); 
				else if(enumerator.equals(eSearchSpaceType.THINGPRESENTATIONMESH))oSearchSpaceList.put(enumerator,new clsSearchSpaceTPM(oDataStructureList));
				else if(enumerator.equals(eSearchSpaceType.TEMPLATEIMAGE))oSearchSpaceList.put(enumerator,new clsSearchSpaceTI(oDataStructureList));
				else if(enumerator.equals(eSearchSpaceType.DRIVEMESH))oSearchSpaceList.put(enumerator,new clsSearchSpaceDM(oDataStructureList));
				else if(enumerator.equals(eSearchSpaceType.WORDPRESENTATION))oSearchSpaceList.put(enumerator, new clsSearchSpaceWP(oDataStructureList));
				else throw new java.lang.NullPointerException("unkown searchspace type: " + enumerator.toString());
			}
				
		return oSearchSpaceList;	
	}
}
