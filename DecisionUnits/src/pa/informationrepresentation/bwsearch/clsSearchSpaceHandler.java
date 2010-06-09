/**
 * clsSearchSpaceHandler.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 */
package pa.informationrepresentation.bwsearch;

import java.util.List;

import pa.informationrepresentation.bwsearch.searchspace.clsSearchSpaceBase;
import pa.informationrepresentation.bwsearch.searchspace.clsSearchSpaceCreator;
import pa.informationrepresentation.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 * 
 */
public class clsSearchSpaceHandler {
	private clsSearchSpaceBase moSearchSpace; 
	
	public clsSearchSpaceHandler(String poDatabaseSource){
		createSearchSpace(poDatabaseSource);
	}
	
	public void createSearchSpace(String poDatabaseSource){
		clsSearchSpaceCreator.createSearchSpace(poDatabaseSource); 
	}
	
	public List<clsDataStructurePA> returnSearchSpace(String poSearchSpaceType){
		/*TODO HZ 
		 * Introduce searchspace search */
		return moSearchSpace.returnSearchSpace(poSearchSpaceType); 
	}
}
