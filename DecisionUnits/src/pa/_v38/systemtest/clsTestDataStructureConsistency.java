/**
 * CHANGELOG
 *
 * 25.10.2012 wendt - File created
 *
 */
package pa._v38.systemtest;

import java.util.ArrayList;

import org.slf4j.Logger;

import logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.tools.clsPair;
import pa._v38.tools.datastructures.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.10.2012, 12:03:58
 * 
 */
public class clsTestDataStructureConsistency {
    
    private static final Logger log = clsLogger.getLog("Test");
	
	
	public static boolean debugFindErroneousLinks(clsThingPresentationMesh poTPM) throws Exception {
		boolean bResult = false;
		
		for (clsAssociation oAss : poTPM.getExternalMoAssociatedContent()) {
			clsDataStructurePA oDS = oAss.getTheOtherElement(poTPM);
			
			if (oDS==null) {
				String oErrorMessage = "Error: " + poTPM.toString() + " has an erroneous EXTERNAL ASSOCIATION: " + oAss.toString() + ". None of the elements is the origin structure";
				//try {
				bResult=true;
				throw new Exception(oErrorMessage);
					
				//} 
				//catch (Exception e) {
				//	//clsLogger.jlog.error(e.getMessage());
				//	bResult=true;
				//}
			}
		}
		
		for (clsAssociation oAss : poTPM.getMoInternalAssociatedContent()) {
			clsDataStructurePA oDS = oAss.getTheOtherElement(poTPM);
			
			if (oDS==null) {
				String oErrorMessage = "Error: " + poTPM.toString() + " has an erroneous INTERNAL ASSOCIATION: " + oAss.toString() + ". None of the elements is the origin structure";
				//try {
				bResult=true;
				throw new Exception(oErrorMessage);
					
				//} 
				//catch (Exception e) {
				//	clsLogger.jlog.error(e.getMessage());
				//	bResult=true;
				//}
			}
		}
		
		return bResult;
	}
	
	
	public static boolean debugFindErroneousLinks(clsWordPresentationMesh poWPM) throws Exception {
		boolean bResult = false;
		
		for (clsAssociation oAss : poWPM.getExternalAssociatedContent()) {
			clsDataStructurePA oDS = oAss.getTheOtherElement(poWPM);
			
			if (oDS==null) {
				String oErrorMessage = "Error: " + poWPM.toString() + " has an erroneous EXTERNAL ASSOCIATION: " + oAss.toString() + ". None of the elements is the origin structure";
				//try {
				bResult=true;
				throw new Exception(oErrorMessage);
				//} catch (Exception e) {
				//	clsLogger.jlog.error(e.getMessage());
					
				//}
			}
		}
		
		for (clsAssociation oAss : poWPM.getMoInternalAssociatedContent()) {
			clsDataStructurePA oDS = oAss.getTheOtherElement(poWPM);
			
			if (oDS==null) {
				String oErrorMessage = "Error: " + poWPM.toString() + " has an erroneous INTERNAL ASSOCIATION: " + oAss.toString() + ". None of the elements is the origin structure";
				try {
					throw new Exception(oErrorMessage);
				} catch (Exception e) {
					log.error(e.getMessage());
					bResult=true;
				}
			}
		}
		
		return bResult;
	}
	
	public static void debugFindAllErroneousLinksInImage(ArrayList<clsThingPresentationMesh> poImageList) throws Exception {
		
		for (clsThingPresentationMesh oTPM : poImageList) {
			ArrayList<clsThingPresentationMesh> oTPMList = clsMeshTools.getAllTPMObjects(oTPM, 4);
			
			for (clsThingPresentationMesh oTPM2 : oTPMList) {
				clsTestDataStructureConsistency.debugFindErroneousLinks(oTPM2);
			}
		}
		
		
	}
	
	public static void debugFindAllErroneousLinksInImage(clsThingPresentationMesh poImage) throws Exception {
		
		ArrayList<clsThingPresentationMesh> oTPMList = clsMeshTools.getAllTPMObjects(poImage, 4);
		
		for (clsThingPresentationMesh oTPM : oTPMList) {
			clsTestDataStructureConsistency.debugFindErroneousLinks(oTPM);
		}
		
	}
	
	public static void debugFindAllErroneousLinksInImage(clsWordPresentationMesh poImage) throws Exception {
		
		ArrayList<clsWordPresentationMesh> oTPMList = clsMeshTools.getAllWPMObjects(poImage, 4);
		
		for (clsWordPresentationMesh oTPM : oTPMList) {
			clsTestDataStructureConsistency.debugFindErroneousLinks(oTPM);
		}
		
	}
	
	public static void debugFindAllErroneousLinksInDataStructure(clsDataStructurePA poDS) throws Exception {
		
		if (poDS instanceof clsThingPresentationMesh) {
			debugFindAllErroneousLinksInImage((clsThingPresentationMesh) poDS);
		} else if (poDS instanceof clsWordPresentationMesh) {
			debugFindAllErroneousLinksInImage((clsWordPresentationMesh)poDS);
		}
	}
	
	public static void debugFindAllErroneousLinksInDataStructure(ArrayList<clsPair<Double, clsDataStructurePA>> poStructure) throws Exception {
		for (clsPair<Double, clsDataStructurePA> oDS : poStructure) {
			debugFindAllErroneousLinksInDataStructure(oDS.b);
		}
	}
	
	public static void debugCheckDMQoAGrowing(clsThingPresentationMesh poTPM) throws Exception {
		
		
		for (clsThingPresentationMesh oTPM : clsMeshTools.getAllTPMObjects(poTPM, 5)) {
			ArrayList<clsAssociationDriveMesh> oDMList = new ArrayList<clsAssociationDriveMesh>();
			oDMList.addAll(clsMeshTools.getAllDMInMesh(oTPM));
			for (clsAssociationDriveMesh oDM : oDMList) {
				if (oDM.getDM().getQuotaOfAffect()>1.0) {
					throw new Exception("Error in " + oTPM.getMoContent() + " in Drive mesh " + oDM.getRootElement() + ". QoA=" + oDM.getDM().getQuotaOfAffect() + " which is higher than allowed, i. e. the reference of the DM is bad");
				}
			}
		}
		
		
		
	}
	
	
}
