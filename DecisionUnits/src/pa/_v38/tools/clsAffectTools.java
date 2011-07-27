/**
 * CHANGELOG
 *
 * 25.06.2011 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.06.2011, 09:29:24
 * 
 */
public class clsAffectTools {
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 20.07.2011 13:58:37
	 *
	 * @param poImage
	 * @return
	 */
	public static double calculateAbsoluteAffect(clsPrimaryDataStructureContainer poImage) {
		double rAbsoluteAffect;
		
		rAbsoluteAffect = 0;
		
		for (clsAssociation oAss: poImage.getMoAssociatedDataStructures()) {
			if (oAss instanceof clsAssociationDriveMesh) {
				rAbsoluteAffect += java.lang.Math.abs(((clsDriveMesh)oAss.getLeafElement()).getPleasure());
			}
		}
		return rAbsoluteAffect;
	}
	
	/**
	 * Create a list of affects from a list of drives
	 * (wendt)
	 *
	 * @since 27.07.2011 09:45:00
	 *
	 * @param poDriveList_Input
	 * @return
	 */
	public static ArrayList<clsDataStructurePA> extractAffect(ArrayList<clsDriveMesh> poDriveList_Input) {
		ArrayList<clsDataStructurePA> oPattern = new ArrayList<clsDataStructurePA>();
		for(clsDriveMesh oEntry : poDriveList_Input){
			clsDataStructurePA oAffect = clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, 
					new clsPair<String, Object>(eDataType.AFFECT.toString(), oEntry.getPleasure()));
			
			oPattern.add(oAffect);
		}
		
		return oPattern;
	}
	
	/**
	 * Get Associated content from input drives
	 * (wendt)
	 *
	 * @since 27.07.2011 09:50:29
	 *
	 * @param poDriveList_Input
	 * @return
	 */
	public static ArrayList<clsDataStructurePA> extractAssociationsFromDriveMeshList(ArrayList<clsDriveMesh> poDriveList_Input) {
		ArrayList<clsDataStructurePA> oPattern = new ArrayList<clsDataStructurePA>();
		for(clsDriveMesh oEntry : poDriveList_Input){
			for(clsAssociation oAssociation : oEntry.getMoAssociatedContent()){
				oPattern.add(oAssociation.getMoAssociationElementB()); 
			}
		}
		
		return oPattern;
	}
	

	
}


