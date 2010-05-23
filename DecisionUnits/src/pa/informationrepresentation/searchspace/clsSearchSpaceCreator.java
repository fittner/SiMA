/**
 * clsSearchSpaceCreator.java: DecisionUnits - pa.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 */
package pa.informationrepresentation.searchspace;

import pa.informationrepresentation.SearchSpaceEnums;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 * 
 */
public class clsSearchSpaceCreator {
		
	public static clsSearchSpaceBase createSearchSpace(SearchSpaceEnums peSearchSpaceType){
		if(peSearchSpaceType.equals(SearchSpaceEnums.ThingPresentation))return new clsSearchSpaceTP();
		if(peSearchSpaceType.equals(SearchSpaceEnums.ThingPresentationMesh))return new clsSearchSpaceTPM();
		if(peSearchSpaceType.equals(SearchSpaceEnums.DriveMesh))return new clsSearchSpaceDM();
		if(peSearchSpaceType.equals(SearchSpaceEnums.WordPresentation))return new clsSearchSpaceWP();
		
		throw new java.lang.NullPointerException("unkown searchspace type: " + peSearchSpaceType.toString()); 
	}
}
