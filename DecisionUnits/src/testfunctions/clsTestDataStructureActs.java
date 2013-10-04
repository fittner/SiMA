/**
 * CHANGELOG
 *
 * 18.02.2013 wendt - File created
 *
 */
package testfunctions;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eGoalType;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 18.02.2013, 16:36:01
 * 
 */
public class clsTestDataStructureActs {
	private static void checkIfPIMatchingCorrectnessOneImage(clsWordPresentationMesh poImage) throws Exception {
		//Get PP Match
		double rPIMatchPri = clsActTools.getPrimaryMatchValueToPI(clsMeshTools.getPrimaryDataStructureOfWPM(poImage));
		
		//Get SP Match
		double rPIMatchSec = clsActTools.getPIMatch(poImage);
		
		if (rPIMatchPri!=rPIMatchSec) {
			throw new Exception("PI Match in PP and SP different for: " + poImage.getMoContent() + " PIMatch primary=" + rPIMatchPri + ", PIMatch secondary=" + rPIMatchSec);
		}
	}
	
	public static void checkIfPIMatchingCorrectnessMeshArray(ArrayList<clsWordPresentationMesh> poImageArray) throws Exception {
		for (clsWordPresentationMesh oWPMArray : poImageArray) {
			ArrayList<clsWordPresentationMesh> poMeshList = clsMeshTools.getAllWPMImages(oWPMArray, 5);
			
			for (clsWordPresentationMesh oWPM : poMeshList) {
				checkIfPIMatchingCorrectnessOneImage(oWPM);
			}
		}
	}
	
	public static ArrayList<clsWordPresentationMeshGoal> exeTestReduceGoalList(ArrayList<clsWordPresentationMeshGoal> moReachableGoalList_IN) {
	    //Keep only Libidonous stomach with cake
	    boolean bPerceivedFound = false;
	    boolean bActFound = false;
	    boolean bDriveFound = false;
	    
	    ArrayList<clsWordPresentationMeshGoal> oReplaceList = new ArrayList<clsWordPresentationMeshGoal>();
	    
	    for (clsWordPresentationMeshGoal oG : moReachableGoalList_IN) {
	        eGoalType oGoalType = oG.getGoalType();
	        
	        if (bPerceivedFound==false && oGoalType.equals(eGoalType.PERCEPTIONALDRIVE) && oG.getGoalObject().getMoContent().equals("CAKE")) {
	            oReplaceList.add(oG);
	            bPerceivedFound=true;
	        } else if (bActFound==false && oGoalType.equals(eGoalType.MEMORYDRIVE) && oG.getGoalObject().getMoContent().equals("CAKE")) {
                oReplaceList.add(oG);
                bActFound=true;
            } else if (bDriveFound==false && oGoalType.equals(eGoalType.DRIVESOURCE) && oG.getGoalObject().getMoContent().equals("CAKE")) {
                oReplaceList.add(oG);
                bDriveFound=true;
            }

	    }
	    
	    //moReachableGoalList_IN = oReplaceList;
	    return oReplaceList;
	    
	}
	
	public static void exeTestIfErroneousSupportiveDataStructureIsAssigned(clsWordPresentationMeshGoal goal) throws Exception {
	        if (goal.getGoalType().equals(eGoalType.MEMORYDRIVE) && goal.getSupportiveDataStructure().getMoContent().equals("ENTITY2IMAGE")) {
	            throw new Exception("Erroneous Supportive Datastructure assigned");
	        }

	}
}


