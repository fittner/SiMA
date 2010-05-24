/**
 * clsSearchSpaceCreator.java: DecisionUnits - pa.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 */
package pa.informationrepresentation.searchspace;

import pa.informationrepresentation.enums.eSearchSpace;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:42:48
 * 
 */
public class clsSearchSpaceCreator {
		
	public static clsSearchSpaceBase createSearchSpace(eSearchSpace peSearchSpaceType){
		if(peSearchSpaceType.equals(eSearchSpace.ThingPresentation))return new clsSearchSpaceTP();
		if(peSearchSpaceType.equals(eSearchSpace.ThingPresentationMesh))return new clsSearchSpaceTPM();
		if(peSearchSpaceType.equals(eSearchSpace.DriveMesh))return new clsSearchSpaceDM();
		if(peSearchSpaceType.equals(eSearchSpace.WordPresentation))return new clsSearchSpaceWP();
		
		throw new java.lang.NullPointerException("unkown searchspace type: " + peSearchSpaceType.toString()); 
	}
}
