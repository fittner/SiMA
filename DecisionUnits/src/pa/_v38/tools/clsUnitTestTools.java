/**
 * CHANGELOG
 *
 * 25.10.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.10.2012, 12:03:58
 * 
 */
public class clsUnitTestTools {
	
	
	public static boolean debugFindErroneousLinks(clsThingPresentationMesh poTPM) {
		boolean bResult = false;
		
		for (clsAssociation oAss : poTPM.getExternalMoAssociatedContent()) {
			clsDataStructurePA oDS = oAss.getTheOtherElement(poTPM);
			
			if (oDS==null) {
				String oErrorMessage = "Error: " + poTPM.toString() + " has an erroneous EXTERNAL ASSOCIATION: " + oAss.toString() + ". None of the elements is the origin structure";
				try {
					throw new Exception(oErrorMessage);
				} catch (Exception e) {
					clsLogger.jlog.error(e.getMessage());
					bResult=true;
				}
			}
		}
		
		for (clsAssociation oAss : poTPM.getMoInternalAssociatedContent()) {
			clsDataStructurePA oDS = oAss.getTheOtherElement(poTPM);
			
			if (oDS==null) {
				String oErrorMessage = "Error: " + poTPM.toString() + " has an erroneous INTERNAL ASSOCIATION: " + oAss.toString() + ". None of the elements is the origin structure";
				try {
					throw new Exception(oErrorMessage);
				} catch (Exception e) {
					clsLogger.jlog.error(e.getMessage());
					bResult=true;
				}
			}
		}
		
		return bResult;
	}
	
	
	public static boolean debugFindErroneousLinks(clsWordPresentationMesh poTPM) {
		boolean bResult = false;
		
		for (clsAssociation oAss : poTPM.getExternalAssociatedContent()) {
			clsDataStructurePA oDS = oAss.getTheOtherElement(poTPM);
			
			if (oDS==null) {
				String oErrorMessage = "Error: " + poTPM.toString() + " has an erroneous EXTERNAL ASSOCIATION: " + oAss.toString() + ". None of the elements is the origin structure";
				try {
					throw new Exception(oErrorMessage);
				} catch (Exception e) {
					clsLogger.jlog.error(e.getMessage());
					bResult=true;
				}
			}
		}
		
		for (clsAssociation oAss : poTPM.getMoInternalAssociatedContent()) {
			clsDataStructurePA oDS = oAss.getTheOtherElement(poTPM);
			
			if (oDS==null) {
				String oErrorMessage = "Error: " + poTPM.toString() + " has an erroneous INTERNAL ASSOCIATION: " + oAss.toString() + ". None of the elements is the origin structure";
				try {
					throw new Exception(oErrorMessage);
				} catch (Exception e) {
					clsLogger.jlog.error(e.getMessage());
					bResult=true;
				}
			}
		}
		
		return bResult;
	}
	
	public static void debugFindAllErroneousLinksInImage(clsThingPresentationMesh poImage) {
		
		ArrayList<clsThingPresentationMesh> oTPMList = clsMeshTools.getAllTPMObjects(poImage, 4);
		
		for (clsThingPresentationMesh oTPM : oTPMList) {
			clsUnitTestTools.debugFindErroneousLinks(oTPM);
		}
		
	}
	
}
