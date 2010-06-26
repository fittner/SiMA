/**
 * clsSearchSpaceHandler.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 */
package pa.memorymgmt.informationrepresentation;

import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.informationrepresentation.enums.eDataSources;
import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase;
import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceCreator;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 * 
 */
public class clsSearchSpaceHandler {
	clsSearchSpaceBase moSearchSpace; 
	
	public clsSearchSpaceHandler(String poDatabaseSource){
		createSearchSpace(poDatabaseSource);
	}
	
	private void createSearchSpace(String poDatabaseSource){
		if(poDatabaseSource.equals(eDataSources.MAINMEMORY)){moSearchSpace = clsSearchSpaceCreator.createSearchSpace();}
		else if(poDatabaseSource.equals(eDataSources.DATABASE)){/*TODO define database creator access */ ;}
		else {throw new NullPointerException("database source not found " + poDatabaseSource);}
	}
	
	public clsSearchSpaceBase returnSearchSpace(eDataType poDataStructureType){
		return moSearchSpace;
	}
}
