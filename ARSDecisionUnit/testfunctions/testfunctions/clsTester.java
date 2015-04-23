/**
 * CHANGELOG
 *
 * 09.11.2012 wendt - File created
 *
 */
package testfunctions;

import java.util.ArrayList;

import memorymgmt.enums.eAction;
import testfunctions.meshtester.clsTestDataStructureConsistency;
import testfunctions.meshtester.clsTestDataStructureNullPointers;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshGoal;
import base.datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 09.11.2012, 11:49:18
 * 
 */
public class clsTester {
	private static clsTester moTesterInstance = null;
	
	private boolean mbActivated = false;
	
	private clsTester() {
		
	}
	
	public static clsTester getTester() {
		if (clsTester.moTesterInstance==null) {
			clsTester.moTesterInstance = new clsTester();
		}
		
		return clsTester.moTesterInstance;
	}
	

	/**
	 * @since 09.11.2012 11:52:55
	 * 
	 * @return the mbActivated
	 */
	public boolean isActivated() {
		return mbActivated;
	}

	/**
	 * @since 09.11.2012 11:52:55
	 * 
	 * @param mbActivated the mbActivated to set
	 */
	public void setActivated(boolean mbActivated) {
		this.mbActivated = mbActivated;
	}
	
//	public void exeTestAssociationAssignment(clsThingPresentationMesh poImage) throws Exception {
//		clsTestDataStructureConsistency.debugFindAllErroneousLinksInImage(poImage);
//	}
//	
	public void exeTestAssociationAssignmentTPMArray(ArrayList<clsThingPresentationMesh> poImageList) throws Exception {
		clsTestDataStructureConsistency.debugFindAllErroneousLinksInImage(poImageList);
	} 
	
	public void exeTestAssociationAssignment(clsDataStructurePA poImage) throws Exception {
		clsTestDataStructureConsistency.debugFindAllErroneousLinksInDataStructure(poImage);
	}
	
	public void exeTestAssociationAssignment(ArrayList<clsPair<Double, clsDataStructurePA>> poImage) throws Exception {
		clsTestDataStructureConsistency.debugFindAllErroneousLinksInDataStructure(poImage);
	}
	
	public void exeTestAction(ArrayList<clsWordPresentationMesh> poCurrentActionList, eAction poTestAction) {
		clsTestActions.replaceAction(poCurrentActionList, poTestAction);
	}
	
	public void exeTestNullPointer(clsThingPresentationMesh poMesh) throws Exception {
		clsTestDataStructureNullPointers.findNullPointerAssociationsInImage(poMesh);
	}
	
	public void exeTestCheckPIMatch(ArrayList<clsWordPresentationMesh> poImage) throws Exception {
		clsTestDataStructureActs.checkIfPIMatchingCorrectnessMeshArray(poImage);
	}
	
	public void exeTestDMReference(clsThingPresentationMesh poInput) throws Exception {
		TestValues.debugCheckDMQoAGrowing(poInput);
	}
	
	public void exeTestReduceGoalList(ArrayList<clsWordPresentationMeshGoal> yoReachableGoalList_IN) {
	    yoReachableGoalList_IN = clsTestDataStructureActs.exeTestReduceGoalList(yoReachableGoalList_IN);
	}
	
	public void exeTestCheckLooseAssociations(clsWordPresentationMesh input) throws TestException {
	    clsTestDataStructureConsistency.testNoLooseAssociations(input);
	}
}