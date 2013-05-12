/**
 * CHANGELOG
 *
 * 18.02.2013 wendt - File created
 *
 */
package pa._v38.systemtest;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsMeshTools;

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
}


