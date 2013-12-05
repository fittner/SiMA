/**
 * CHANGELOG
 *
 * 25.10.2012 wendt - File created
 *
 */
package testfunctions.meshtester;

import java.util.ArrayList;

import org.slf4j.Logger;

import datatypes.helpstructures.clsPair;
import logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import secondaryprocess.datamanipulation.clsMeshTools;
import testfunctions.TestException;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.10.2012, 12:03:58
 * 
 */
public class clsTestDataStructureConsistency {
    
    private static final Logger log = clsLogger.getLog("Test");
	
	
	private static boolean debugFindErroneousLinks(clsThingPresentationMesh poTPM) throws Exception {
		boolean bResult = false;
		
		for (clsAssociation oAss : poTPM.getExternalAssociatedContent()) {
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
		
		for (clsAssociation oAss : poTPM.getInternalAssociatedContent()) {
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
	
	
	private static boolean debugFindErroneousLinks(clsWordPresentationMesh poWPM) throws Exception {
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
		
		for (clsAssociation oAss : poWPM.getInternalAssociatedContent()) {
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
	
	private static void debugFindAllErroneousLinksInImage(clsThingPresentationMesh poImage) throws Exception {
		
		ArrayList<clsThingPresentationMesh> oTPMList = clsMeshTools.getAllTPMObjects(poImage, 4);
		
		for (clsThingPresentationMesh oTPM : oTPMList) {
			clsTestDataStructureConsistency.debugFindErroneousLinks(oTPM);
		}
		
	}
	
	private static void debugFindAllErroneousLinksInImage(clsWordPresentationMesh poImage) throws Exception {
		
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
	
	public static void testCheckIfMeshHasErroneousID(clsWordPresentationMesh poImage) throws TestException {
	    ArrayList<clsDataStructurePA> testStructure = new ArrayList<clsDataStructurePA>();
	    
	    testStructure.addAll(clsMeshTools.getAllWPMObjects(poImage, 10));
	    
	    for (clsDataStructurePA test : testStructure) {
	        if (test.getContentType().equals(eContentType.RI)==true && test.getDS_ID()==-1) {
	            throw new TestException("Data structure " + test + " in mesh " + poImage + " has ID -1");
	        }
	    }
	}
	
	public static void testCheckIfMultipleIDsLoaded(clsWordPresentationMesh poImage) throws TestException {
	    ArrayList<clsDataStructurePA> testStructure = new ArrayList<clsDataStructurePA>();
        
        testStructure.addAll(clsMeshTools.getAllWPMObjects(poImage, 10));
        for (clsDataStructurePA test : testStructure) {
            for (clsDataStructurePA test2 : testStructure) {
                if (test.getDS_ID()!=-1 && test.equals(test2)==false && test.getDS_ID()==test2.getDS_ID() && test.getContentType().equals(eContentType.RI)) {
                    throw new TestException("Data structure " + test + " and " + test2 + " in mesh " + poImage + " has the same ID although it is not allowed");
                }
            }
            
        }
	}
	
	public static void testNoLooseAssociations(clsWordPresentationMesh poImage) throws TestException {
	    for (clsAssociationSecondary oAss : clsMeshTools.getAllAssociationSecondaryInMesh(poImage, 10)) {
	        if (oAss.getRootElement() instanceof clsWordPresentationMesh) {
	            clsWordPresentationMesh root = (clsWordPresentationMesh) oAss.getRootElement();
	            if (root.getExternalAssociatedContent().contains(oAss)==false && root.getInternalAssociatedContent().contains(oAss)==false && root.getDS_ID()!=-1 && root.getContentType().equals(eContentType.RI)==false && root.getContentType().equals(eContentType.ENTITY)==false && root.getContentType().equals(eContentType.ACTION)==false) {
	                throw new TestException("The association " + oAss + " does not exist in data structure " + root);
	            }
	        }
	        
	        if (oAss.getLeafElement() instanceof clsWordPresentationMesh) {
                clsWordPresentationMesh leaf = (clsWordPresentationMesh) oAss.getLeafElement();
                if (leaf.getExternalAssociatedContent().contains(oAss)==false && leaf.getInternalAssociatedContent().contains(oAss)==false && leaf.getDS_ID()!=-1 && leaf.getContentType().equals(eContentType.RI)==false && leaf.getContentType().equals(eContentType.ENTITY)==false && leaf.getContentType().equals(eContentType.ACTION)==false) {
                    throw new TestException("The association " + oAss + " does not exist in data structure " + leaf);
                }
            }
	            
	    }
	}
	
	
}
