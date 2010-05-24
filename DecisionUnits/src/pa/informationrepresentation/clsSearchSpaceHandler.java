/**
 * clsSearchSpaceHandler.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 */
package pa.informationrepresentation;

import java.util.Hashtable;

import pa.informationrepresentation.enums.eSearchSpace;
import pa.informationrepresentation.searchspace.clsSearchSpaceBase;
import pa.informationrepresentation.searchspace.clsSearchSpaceCreator;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 * 
 */
public class clsSearchSpaceHandler {
	private Hashtable <eSearchSpace, clsSearchSpaceBase> moSearchSpaceList; 
	
	public clsSearchSpaceHandler(){
		moSearchSpaceList = new Hashtable<eSearchSpace, clsSearchSpaceBase>(); 
	}
	
	public void createSearchSpaceList(){
		for (Object element : eSearchSpace.values()){
			eSearchSpace enumerator = (eSearchSpace)element; 
			moSearchSpaceList.put(enumerator, clsSearchSpaceCreator.createSearchSpace(enumerator)); 
		}
	}
	
	public clsSearchSpaceBase returnSearchSpace(eSearchSpace peSearchSpaceEnum){
		/*TODO HZ 
		 * Introduce searchspace search */
		return moSearchSpaceList.get(peSearchSpaceEnum); 
	}
}
