/**
 * tssTestSearchSpace.java: DecisionUnits - tstpa.memorymgmt.informationrepresentation
 * 
 * @author zeilinger
 * 20.07.2010, 21:02:11
 */
package tstpa.memorymgmt.informationrepresentation;

import java.util.ArrayList;
import java.util.HashMap;

import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v30.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v30.memorymgmt.datatypes.clsAssociationTime;
import pa._v30.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsTemplateImage;
import pa._v30.memorymgmt.datatypes.clsThingPresentation;
import pa._v30.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v30.memorymgmt.datatypes.clsWordPresentation;
import pa._v30.memorymgmt.enums.eDataType;
import pa._v30.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase;
import pa._v30.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceOntologyLoader;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.07.2010, 21:02:11
 * 
 */

public class tssTestSearchSpace {
	public static clsSearchSpaceBase createTestSearchSpace(){
		int oID = 0; 
		HashMap <String, clsDataStructurePA> oDataStructureTable = new HashMap<String, clsDataStructurePA>();
		
		//ACT
		//-tbd

		//Thing-Presentation Test
		
		clsThingPresentation oThingPresentation1 = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "blue");
		oDataStructureTable.put(oThingPresentation1.getMoDataStructureType().toString(), oThingPresentation1);
		clsThingPresentation oThingPresentation2 = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "black");
		oDataStructureTable.put(Integer.toString(oID), oThingPresentation2);
		clsThingPresentation oThingPresentation3 = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "red");
		oDataStructureTable.put(Integer.toString(oID), oThingPresentation3);		
		clsThingPresentation oThingPresentation4 = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "taste"), "sweet");
		oDataStructureTable.put(Integer.toString(oID), oThingPresentation4);
		clsThingPresentation oThingPresentation5 = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "shape"), "circle");
		oDataStructureTable.put(Integer.toString(oID), oThingPresentation5);
		clsThingPresentation oThingPresentation6 = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "shape"), "rectangle");
		oDataStructureTable.put(Integer.toString(oID), oThingPresentation6);
		clsThingPresentation oThingPresentation7 = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "drivesource"), "hunger");
		oDataStructureTable.put(Integer.toString(oID), oThingPresentation7);
		clsThingPresentation oThingPresentation8 = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "drivesource"), "aggressivity");
		oDataStructureTable.put(Integer.toString(oID), oThingPresentation8);
		
		//DM Test 
		ArrayList<clsAssociation> oDriveAssociation1 = new ArrayList<clsAssociation>();
		clsDriveMesh oDriveMesh1 = new clsDriveMesh(new clsTripple<Integer, eDataType, String>(oID++, eDataType.DM,""),0.0,new double[]{0.0,0.0,0.0,0.0}, oDriveAssociation1, "test1"); 
		clsAssociationAttribute oAssociationAttribute10 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oDriveMesh1, oThingPresentation8);
		oDriveAssociation1.add(oID, oAssociationAttribute10);
		oDataStructureTable.put(Integer.toString(oID++), oDriveMesh1);
		
		ArrayList<clsAssociation> oDriveAssociation2 = new ArrayList<clsAssociation>();
		clsDriveMesh oDriveMesh2 = new clsDriveMesh(new clsTripple<Integer, eDataType, String>(oID++, eDataType.DM,""),0.0,new double[]{0.0,0.0,0.0,0.0}, oDriveAssociation2, "test2");
		clsAssociationAttribute oAssociationAttribute11 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oDriveMesh2, oThingPresentation7);
		oDriveAssociation2.add(oID, oAssociationAttribute11);
		oDataStructureTable.put(Integer.toString(oID++), oDriveMesh2);
		
		//Thing-Presentation-Mesh Test
			
		clsThingPresentationMesh oThingPresentationMesh1 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList1 = new ArrayList<clsAssociation>();
		oThingPresentationMesh1 = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TPM,""), oAssociatedDataStructureList1, "test3"); 
		clsAssociationAttribute oAssociationAttribute1 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, oThingPresentation5);
		oAssociatedDataStructureList1.add(oID, oAssociationAttribute1);
		clsAssociationAttribute oAssociationAttribute2 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, oThingPresentation3);
		oAssociatedDataStructureList1.add(oID, oAssociationAttribute2);
		clsAssociationAttribute oAssociationAttribute3 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, oThingPresentation4);
		oAssociatedDataStructureList1.add(oID, oAssociationAttribute3);
		oDataStructureTable.put(Integer.toString(oID++), oThingPresentationMesh1);
		
		clsThingPresentationMesh oThingPresentationMesh2 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList2 = new ArrayList<clsAssociation>();
		oThingPresentationMesh2 = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TPM,""), oAssociatedDataStructureList2, "test4"); 
		clsAssociationAttribute oAssociationAttribute4 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh2, oThingPresentation6); 
		oAssociatedDataStructureList2.add(oID, oAssociationAttribute4);
		clsAssociationAttribute oAssociationAttribute5 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh2, oThingPresentation2);
		oAssociatedDataStructureList2.add(oID, oAssociationAttribute5);
		clsAssociationAttribute oAssociationAttribute6 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh2, oThingPresentation4);
		oAssociatedDataStructureList2.add(oID, oAssociationAttribute6);
		oDataStructureTable.put(Integer.toString(oID++), oThingPresentationMesh2);
		
		clsThingPresentationMesh oThingPresentationMesh3 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList3 = new ArrayList<clsAssociation>();
		oThingPresentationMesh3 = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TPM,""), oAssociatedDataStructureList3, "test5"); 
		clsAssociationAttribute oAssociationAttribute7 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh3, oThingPresentation5);
		oAssociatedDataStructureList3.add(oID, oAssociationAttribute7);
		clsAssociationAttribute oAssociationAttribute8 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh3, oThingPresentation3);
		oAssociatedDataStructureList3.add(oID, oAssociationAttribute8);
		clsAssociationAttribute oAssociationAttribute9 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh3, oThingPresentationMesh1);
		oAssociatedDataStructureList3.add(oID, oAssociationAttribute9);
		oDataStructureTable.put(Integer.toString(oID++), oThingPresentationMesh3);
		
		//TI Test 
		clsTemplateImage oTemplateImage1 = null; 
		ArrayList<clsAssociation> oAssociatedTemporalStructures = new ArrayList<clsAssociation>();
		oTemplateImage1 = new clsTemplateImage(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TI,""),oAssociatedTemporalStructures, "test6");
		
		clsAssociationTime oAssociationTime1 = new clsAssociationTime(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,""), oTemplateImage1, oThingPresentationMesh1);
		oAssociatedTemporalStructures.add(oID, oAssociationTime1); 
		clsAssociationTime oAssociationTime2 = new clsAssociationTime(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,""), oTemplateImage1, oThingPresentationMesh2);
		oAssociatedTemporalStructures.add(oID, oAssociationTime2); 
		clsAssociationTime oAssociationTime3 = new clsAssociationTime(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,""), oTemplateImage1, oThingPresentationMesh3);
		oAssociatedTemporalStructures.add(oID, oAssociationTime3); 
		
		oDataStructureTable.put(Integer.toString(oID++), oTemplateImage1);		
			
		//WP Test 
		clsWordPresentation oWordPresentation1 = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.WP,""), "blue");
		oDataStructureTable.put(Integer.toString(oID), oWordPresentation1);
		clsWordPresentation oWordPresentation2 = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.WP,""), "red");
		oDataStructureTable.put(Integer.toString(oID), oWordPresentation2);
		clsWordPresentation oWordPresentation3 = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.WP,""), "cake");
		oDataStructureTable.put(Integer.toString(oID), oWordPresentation3);
		clsWordPresentation oWordPresentation4 = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.WP,""), "bubble");
		oDataStructureTable.put(Integer.toString(oID), oWordPresentation4);
		clsWordPresentation oWordPresentation5 = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.WP,""), "aggressive");
		oDataStructureTable.put(Integer.toString(oID), oWordPresentation5);
		clsWordPresentation oWordPresentation6 = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.WP,""), "hungry");
		oDataStructureTable.put(Integer.toString(oID), oWordPresentation6);
		clsWordPresentation oWordPresentation7 = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.WP,""), "testTI1");
		oDataStructureTable.put(Integer.toString(oID), oWordPresentation7);
		clsWordPresentation oWordPresentation8 = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.WP,""), "testTI2");
		oDataStructureTable.put(Integer.toString(oID), oWordPresentation8);
		
		
		//ASSOCIATION Test 
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute1);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute2);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute3);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute4);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute5);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute6);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute7);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute8);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute9);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute10);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationAttribute11);
		
		oDataStructureTable.put(Integer.toString(oID++), new clsAssociationDriveMesh(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONDM,""), oDriveMesh1, oThingPresentationMesh2)); 
		oDataStructureTable.put(Integer.toString(oID++),new clsAssociationDriveMesh(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONDM,""), oDriveMesh2, oThingPresentationMesh1)); 
		
		oDataStructureTable.put(Integer.toString(oID++), oAssociationTime1);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationTime2);
		oDataStructureTable.put(Integer.toString(oID++), oAssociationTime3);
		
		oDataStructureTable.put(Integer.toString(oID++),new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation1, oThingPresentation1)); 
		oDataStructureTable.put(Integer.toString(oID++),new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation2, oThingPresentation3)); 
		oDataStructureTable.put(Integer.toString(oID++),new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation3, oThingPresentationMesh1)); 
		oDataStructureTable.put(Integer.toString(oID++),new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation4, oThingPresentationMesh2)); 
		oDataStructureTable.put(Integer.toString(oID++),new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation5, oDriveMesh1)); 
		oDataStructureTable.put(Integer.toString(oID++),new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation6, oDriveMesh2)); 
		oDataStructureTable.put(Integer.toString(oID++),new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation7, oTemplateImage1)); 
		oDataStructureTable.put(Integer.toString(oID++),new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation8, oThingPresentation1)); 

		
		return new clsSearchSpaceOntologyLoader(oDataStructureTable); 
	}
}
