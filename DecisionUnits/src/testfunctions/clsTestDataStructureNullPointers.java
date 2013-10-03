/**
 * CHANGELOG
 *
 * 14.02.2013 wendt - File created
 *
 */
package testfunctions;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 14.02.2013, 14:49:42
 * 
 */
public class clsTestDataStructureNullPointers {
	private static void findNullPointerAssociationsInTPM(clsThingPresentationMesh poLocalTPM) throws Exception {
		//get all TPMS
		for (clsAssociation oEntry : poLocalTPM.getMoInternalAssociatedContent()) {
			try {
				clsDataStructurePA oLeafElement = oEntry.getLeafElement(); 
				if (oLeafElement==null) {
					throw new Exception("The leaf element of the association " + oEntry + " in the internal structures of " + poLocalTPM.getMoContent() + " is NULL");
				}
				
				clsDataStructurePA oRootElement = oEntry.getRootElement(); 
				if (oRootElement==null) {
					throw new Exception("The root element of the association " + oEntry + " in the internal structures of " + poLocalTPM.getMoContent() + " is NULL");
				}
			} catch (Exception e1) {
				throw new Exception(e1.getMessage());
			}
			
		}
		
		for (clsAssociation oEntry : poLocalTPM.getExternalMoAssociatedContent()) {
			try {
				clsDataStructurePA oLeafElement = oEntry.getLeafElement(); 
				if (oLeafElement==null) {
					throw new Exception("The leaf element of the association " + oEntry + " in the external structures of " + poLocalTPM.getMoContent() + " is NULL");
				}
				
				clsDataStructurePA oRootElement = oEntry.getRootElement(); 
				if (oRootElement==null) {
					throw new Exception("The root element of the association " + oEntry + " in the external structures of " + poLocalTPM.getMoContent() + " is NULL");
				}
			} catch (Exception e1) {
				throw new Exception(e1.getMessage());
			}
			
		}
		
		

	}
	
	public static void findNullPointerAssociationsInImage(clsThingPresentationMesh poImage) throws Exception {
		ArrayList<clsThingPresentationMesh> oTPMList = clsMeshTools.getAllTPMObjects(poImage, 4);
		
		for (clsThingPresentationMesh oTPM : oTPMList) {
			findNullPointerAssociationsInTPM(oTPM);
		}
	}
}
