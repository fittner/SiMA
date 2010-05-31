/**
 * clsSearchSpaceHandler.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 */
package pa.informationrepresentation.ARSi10;

import java.util.ArrayList;

import pa.informationrepresentation.ARSi10.searchspace.clsSearchSpaceBase;
import pa.informationrepresentation.ARSi10.searchspace.clsSearchSpaceCreator;
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
	
	public ArrayList<clsDataStructurePA> returnSearchSpace(String poSearchSpaceType){
		/*TODO HZ 
		 * Introduce searchspace search */
		return moSearchSpace.returnSearchSpace(poSearchSpaceType); 
	}
}
