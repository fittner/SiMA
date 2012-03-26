/**
 * CHANGELOG
 *
 * 26.03.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.03.2012, 20:45:57
 * 
 */
public class clsGoalTools {

	public static clsWordPresentationMesh createGoal(String poDriveContent, eAffectLevel poAffectLevel, clsWordPresentationMesh poDriveObject) {
		//Create identifiyer. All goals must have the content type "GOAL"
		clsTriple<Integer, eDataType, String> oDataStructureIdentifier = new clsTriple<Integer, eDataType, String>(-1, eDataType.WPM, eContentType.GOAL.toString());
		
		//Create the basic goal structure
		clsWordPresentationMesh oRetVal = new clsWordPresentationMesh(oDataStructureIdentifier, new ArrayList<clsAssociation>(), poDriveContent);
		
		//Create a WP for the affectlevel
		clsTriple<Integer, eDataType, String> oDataStructureIdentifier2 = new clsTriple<Integer, eDataType, String>(-1, eDataType.WP, eContentType.AFFECTLEVEL.toString());
		clsWordPresentation oAffectLevelWP = new clsWordPresentation(oDataStructureIdentifier2, poAffectLevel.toString());
		
		//Add WP to the mesh
		clsDataStructureTools.createAssociationSecondary(oRetVal, 1, oAffectLevelWP, 0, 1.0, eContentType.AFFECTLEVEL.toString(), ePredicate.HASAFFECTLEVEL.toString(), false);
		
		//Add Goalobject to the mesh
		clsDataStructureTools.createAssociationSecondary(oRetVal, 1, poDriveObject, 2, 1.0, eContentType.DRIVEOBJECT.toString(), ePredicate.HASDRIVEOBJECT.toString(), false);
		
		return oRetVal;
	}
	
	/**
	 * Get the drive object from a goal mesh
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:22:03
	 *
	 * @param poGoal
	 * @return
	 */
	public static clsWordPresentationMesh getGoalObject(clsWordPresentationMesh poGoal) {
		clsWordPresentationMesh oRetVal = null;
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poGoal.findDataStructure(ePredicate.HASDRIVEOBJECT, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the affectlevel from a goal
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:25:11
	 *
	 * @param poGoal
	 * @return
	 */
	public static eAffectLevel getAffectLevel(clsWordPresentationMesh poGoal) {
		eAffectLevel oRetVal = null;
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poGoal.findDataStructure(ePredicate.HASAFFECTLEVEL, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = eAffectLevel.valueOf(oFoundStructures.get(0).getMoContent());
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the goal content
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:29:45
	 *
	 * @param poGoal
	 * @return
	 */
	public static String getGoalContent(clsWordPresentationMesh poGoal) {
		String oRetVal = poGoal.getMoContent();
		
		return oRetVal;
	}
}
