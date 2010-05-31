/**
 * clsSearchSpaceCreator.java: DecisionUnits - pa.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 */
package pa.informationrepresentation.ARSi10.searchspace;

import pa.informationrepresentation.ARSi10.enums.eDataSources;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 * 
 */
public class clsSearchSpaceCreator {
	public static clsSearchSpaceBase createSearchSpace(String poDataBaseSource){
		if(poDataBaseSource.equals(eDataSources.MAINMEMORY)){return new clsSearchSpaceMainMemory();}
		else if(poDataBaseSource.equals(eDataSources.DATABASE)){/*TODO define database access*/}
		else {throw new NullPointerException("database source not found " + poDataBaseSource);}
		
		return null; 
	}
}
