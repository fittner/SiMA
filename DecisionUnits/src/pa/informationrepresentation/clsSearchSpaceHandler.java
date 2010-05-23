/**
 * clsSearchSpaceHandler.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 */
package pa.informationrepresentation;

import java.util.Hashtable;

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
	private Hashtable <SearchSpaceEnums, clsSearchSpaceBase> moSearchSpaceList; 
	
	public clsSearchSpaceHandler(){
		moSearchSpaceList = new Hashtable<SearchSpaceEnums, clsSearchSpaceBase>(); 
	}
	
	public void createSearchSpaceList(){
		for (Object element : SearchSpaceEnums.values()){
			SearchSpaceEnums enumerator = (SearchSpaceEnums)element; 
			moSearchSpaceList.put(enumerator, clsSearchSpaceCreator.createSearchSpace(enumerator)); 
		}
	}
	
	public clsSearchSpaceBase returnSearchSpace(SearchSpaceEnums peSearchSpaceEnum){
		/*TODO HZ 
		 * Introduce searchspace search */
		return moSearchSpaceList.get(peSearchSpaceEnum); 
	}
}
