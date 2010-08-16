/**
 * tssTestSearchSpace.java: DecisionUnits - tstpa.memorymgmt.informationrepresentation
 * 
 * @author zeilinger
 * 20.07.2010, 21:02:11
 */
package tstpa.memorymgmt.informationrepresentation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationAttribute;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsAssociationTime;
import pa.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsTemplateImage;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase;
import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceOntologyLoader;
import pa.tools.clsTripple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.07.2010, 21:02:11
 * 
 */

public class tssTestSearchSpace {
	public static clsSearchSpaceBase createTestSearchSpace(){
Hashtable <eDataType, List<clsDataStructurePA>> oDataStructureTable = new Hashtable<eDataType, List<clsDataStructurePA>>();
		
		//ACT Test 
		oDataStructureTable.put(eDataType.ACT, new ArrayList<clsDataStructurePA>()); 
		//tbd
		
		//Thing-Presentation Test
		oDataStructureTable.put(eDataType.TP, new ArrayList<clsDataStructurePA>()); 
		clsThingPresentation oThingPresentation1 = new clsThingPresentation(new clsTripple<String, eDataType, String>("color:blue", eDataType.TP, "color"), "blue");
		oDataStructureTable.get(eDataType.TP).add(oThingPresentation1);
		clsThingPresentation oThingPresentation2 = new clsThingPresentation(new clsTripple<String, eDataType, String>("color:black", eDataType.TP, "color"), "black");
		oDataStructureTable.get(eDataType.TP).add(oThingPresentation2);
		clsThingPresentation oThingPresentation3 = new clsThingPresentation(new clsTripple<String, eDataType, String>("color:red", eDataType.TP, "color"), "red");
		oDataStructureTable.get(eDataType.TP).add(oThingPresentation3);		
		clsThingPresentation oThingPresentation4 = new clsThingPresentation(new clsTripple<String, eDataType, String>("taste:sweet", eDataType.TP, "taste"), "sweet");
		oDataStructureTable.get(eDataType.TP).add(oThingPresentation4);
		clsThingPresentation oThingPresentation5 = new clsThingPresentation(new clsTripple<String, eDataType, String>("shape:circle", eDataType.TP, "shape"), "circle");
		oDataStructureTable.get(eDataType.TP).add(oThingPresentation5);
		clsThingPresentation oThingPresentation6 = new clsThingPresentation(new clsTripple<String, eDataType, String>("shape:rectangle", eDataType.TP, "shape"), "rectangle");
		oDataStructureTable.get(eDataType.TP).add(oThingPresentation6);
		clsThingPresentation oThingPresentation7 = new clsThingPresentation(new clsTripple<String, eDataType, String>("drivesource:hunger", eDataType.TP, "drivesource"), "hunger");
		oDataStructureTable.get(eDataType.TP).add(oThingPresentation7);
		clsThingPresentation oThingPresentation8 = new clsThingPresentation(new clsTripple<String, eDataType, String>("drivesource:aggressivity", eDataType.TP, "drivesource"), "aggressivity");
		oDataStructureTable.get(eDataType.TP).add(oThingPresentation8);
		
		//DM Test 
		oDataStructureTable.put(eDataType.DM, new ArrayList<clsDataStructurePA>()); 
		
		ArrayList<clsAssociation> oDriveAssociation1 = new ArrayList<clsAssociation>();
		clsDriveMesh oDriveMesh1 = new clsDriveMesh(new clsTripple<String, eDataType, String>("drive:angry", eDataType.DM,""),0.0,new double[]{0.0,0.0,0.0,0.0}, oDriveAssociation1, "test1"); 
		clsAssociationAttribute oAssociationAttribute10 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oDriveMesh1, oThingPresentation8);
		oDriveAssociation1.add(oAssociationAttribute10);
		oDataStructureTable.get(eDataType.DM).add(oDriveMesh1);
		
		ArrayList<clsAssociation> oDriveAssociation2 = new ArrayList<clsAssociation>();
		clsDriveMesh oDriveMesh2 = new clsDriveMesh(new clsTripple<String, eDataType, String>("drive:hunger", eDataType.DM,""),0.0,new double[]{0.0,0.0,0.0,0.0}, oDriveAssociation2, "test2");
		clsAssociationAttribute oAssociationAttribute11 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oDriveMesh2, oThingPresentation7);
		oDriveAssociation2.add(oAssociationAttribute11);
		oDataStructureTable.get(eDataType.DM).add(oDriveMesh2);
		
		//Thing-Presentation-Mesh Test
		oDataStructureTable.put(eDataType.TPM, new ArrayList<clsDataStructurePA>()); 
		
		clsThingPresentationMesh oThingPresentationMesh1 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList1 = new ArrayList<clsAssociation>();
		oThingPresentationMesh1 = new clsThingPresentationMesh(new clsTripple<String, eDataType, String>("TPM:CAKE", eDataType.TPM,""), oAssociatedDataStructureList1, "test3"); 
		clsAssociationAttribute oAssociationAttribute1 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, oThingPresentation5);
		oAssociatedDataStructureList1.add(oAssociationAttribute1);
		clsAssociationAttribute oAssociationAttribute2 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, oThingPresentation3);
		oAssociatedDataStructureList1.add(oAssociationAttribute2);
		clsAssociationAttribute oAssociationAttribute3 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, oThingPresentation4);
		oAssociatedDataStructureList1.add(oAssociationAttribute3);
		oDataStructureTable.get(eDataType.TPM).add(oThingPresentationMesh1);
		
		clsThingPresentationMesh oThingPresentationMesh2 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList2 = new ArrayList<clsAssociation>();
		oThingPresentationMesh2 = new clsThingPresentationMesh(new clsTripple<String, eDataType, String>("TPM:BUBBLE", eDataType.TPM,""), oAssociatedDataStructureList2, "test4"); 
		clsAssociationAttribute oAssociationAttribute4 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh2, oThingPresentation6); 
		oAssociatedDataStructureList2.add(oAssociationAttribute4);
		clsAssociationAttribute oAssociationAttribute5 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh2, oThingPresentation2);
		oAssociatedDataStructureList2.add(oAssociationAttribute5);
		clsAssociationAttribute oAssociationAttribute6 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh2, oThingPresentation4);
		oAssociatedDataStructureList2.add(oAssociationAttribute6);
		oDataStructureTable.get(eDataType.TPM).add(oThingPresentationMesh2);
		
		clsThingPresentationMesh oThingPresentationMesh3 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList3 = new ArrayList<clsAssociation>();
		oThingPresentationMesh3 = new clsThingPresentationMesh(new clsTripple<String, eDataType, String>("TPM:TEST3", eDataType.TPM,""), oAssociatedDataStructureList3, "test5"); 
		clsAssociationAttribute oAssociationAttribute7 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh3, oThingPresentation5);
		oAssociatedDataStructureList3.add(oAssociationAttribute7);
		clsAssociationAttribute oAssociationAttribute8 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh3, oThingPresentation3);
		oAssociatedDataStructureList3.add(oAssociationAttribute8);
		clsAssociationAttribute oAssociationAttribute9 = new clsAssociationAttribute(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh3, oThingPresentationMesh1);
		oAssociatedDataStructureList3.add(oAssociationAttribute9);
		oDataStructureTable.get(eDataType.TPM).add(oThingPresentationMesh3);
		
		//TI Test 
		oDataStructureTable.put(eDataType.TI, new ArrayList<clsDataStructurePA>()); 
		
		clsTemplateImage oTemplateImage1 = null; 
		ArrayList<clsAssociation> oAssociatedTemporalStructures = new ArrayList<clsAssociation>();
		oTemplateImage1 = new clsTemplateImage(new clsTripple<String, eDataType, String>("TI:TEST1", eDataType.TI,""),oAssociatedTemporalStructures, "test6");
		
		clsAssociationTime oAssociationTime1 = new clsAssociationTime(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,""), oTemplateImage1, oThingPresentationMesh1);
		oAssociatedTemporalStructures.add(oAssociationTime1); 
		clsAssociationTime oAssociationTime2 = new clsAssociationTime(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,""), oTemplateImage1, oThingPresentationMesh2);
		oAssociatedTemporalStructures.add(oAssociationTime2); 
		clsAssociationTime oAssociationTime3 = new clsAssociationTime(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,""), oTemplateImage1, oThingPresentationMesh3);
		oAssociatedTemporalStructures.add(oAssociationTime3); 
		
		oDataStructureTable.get(eDataType.TI).add(oTemplateImage1);		
			
		//WP Test 
		oDataStructureTable.put(eDataType.WP, new ArrayList<clsDataStructurePA>()); 

		clsWordPresentation oWordPresentation1 = new clsWordPresentation(new clsTripple<String, eDataType, String>("wp:blue", eDataType.WP,""), "blue");
		oDataStructureTable.get(eDataType.WP).add(oWordPresentation1);
		clsWordPresentation oWordPresentation2 = new clsWordPresentation(new clsTripple<String, eDataType, String>("wp:red", eDataType.WP,""), "red");
		oDataStructureTable.get(eDataType.WP).add(oWordPresentation2);
		clsWordPresentation oWordPresentation3 = new clsWordPresentation(new clsTripple<String, eDataType, String>("wp:cake", eDataType.WP,""), "cake");
		oDataStructureTable.get(eDataType.WP).add(oWordPresentation3);
		clsWordPresentation oWordPresentation4 = new clsWordPresentation(new clsTripple<String, eDataType, String>("wp:bubble", eDataType.WP,""), "bubble");
		oDataStructureTable.get(eDataType.WP).add(oWordPresentation4);
		clsWordPresentation oWordPresentation5 = new clsWordPresentation(new clsTripple<String, eDataType, String>("wp:aggressive", eDataType.WP,""), "aggressive");
		oDataStructureTable.get(eDataType.WP).add(oWordPresentation5);
		clsWordPresentation oWordPresentation6 = new clsWordPresentation(new clsTripple<String, eDataType, String>("wp:hungry", eDataType.WP,""), "hungry");
		oDataStructureTable.get(eDataType.WP).add(oWordPresentation6);
		clsWordPresentation oWordPresentation7 = new clsWordPresentation(new clsTripple<String, eDataType, String>("wp:testTI1", eDataType.WP,""), "testTI1");
		oDataStructureTable.get(eDataType.WP).add(oWordPresentation7);
		clsWordPresentation oWordPresentation8 = new clsWordPresentation(new clsTripple<String, eDataType, String>("wp:testTI2", eDataType.WP,""), "testTI2");
		oDataStructureTable.get(eDataType.WP).add(oWordPresentation8);
		
		
		//ASSOCIATION Test 
		oDataStructureTable.put(eDataType.ASSOCIATIONATTRIBUTE, new ArrayList<clsDataStructurePA>()); 
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute1);
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute2);
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute3);
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute4);
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute5);
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute6);
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute7);
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute8);
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute9);
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute10);
		oDataStructureTable.get(eDataType.ASSOCIATIONATTRIBUTE).add(oAssociationAttribute11);
		
		oDataStructureTable.put(eDataType.ASSOCIATIONDM, new ArrayList<clsDataStructurePA>()); 
		oDataStructureTable.get(eDataType.ASSOCIATIONDM).add(new clsAssociationDriveMesh(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONDM,""), oDriveMesh1, oThingPresentationMesh2)); 
		oDataStructureTable.get(eDataType.ASSOCIATIONDM).add(new clsAssociationDriveMesh(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONDM,""), oDriveMesh2, oThingPresentationMesh1)); 
		
		oDataStructureTable.put(eDataType.ASSOCIATIONTEMP, new ArrayList<clsDataStructurePA>()); 
		oDataStructureTable.get(eDataType.ASSOCIATIONTEMP).add(oAssociationTime1);
		oDataStructureTable.get(eDataType.ASSOCIATIONTEMP).add(oAssociationTime2);
		oDataStructureTable.get(eDataType.ASSOCIATIONTEMP).add(oAssociationTime3);
		
		oDataStructureTable.put(eDataType.ASSOCIATIONWP, new ArrayList<clsDataStructurePA>()); 
		oDataStructureTable.get(eDataType.ASSOCIATIONWP).add(new clsAssociationWordPresentation(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation1, oThingPresentation1)); 
		oDataStructureTable.get(eDataType.ASSOCIATIONWP).add(new clsAssociationWordPresentation(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation2, oThingPresentation3)); 
		oDataStructureTable.get(eDataType.ASSOCIATIONWP).add(new clsAssociationWordPresentation(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation3, oThingPresentationMesh1)); 
		oDataStructureTable.get(eDataType.ASSOCIATIONWP).add(new clsAssociationWordPresentation(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation4, oThingPresentationMesh2)); 
		oDataStructureTable.get(eDataType.ASSOCIATIONWP).add(new clsAssociationWordPresentation(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation5, oDriveMesh1)); 
		oDataStructureTable.get(eDataType.ASSOCIATIONWP).add(new clsAssociationWordPresentation(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation6, oDriveMesh2)); 
		oDataStructureTable.get(eDataType.ASSOCIATIONWP).add(new clsAssociationWordPresentation(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation7, oTemplateImage1)); 
		oDataStructureTable.get(eDataType.ASSOCIATIONWP).add(new clsAssociationWordPresentation(new clsTripple<String, eDataType, String>(null, eDataType.ASSOCIATIONWP,""), oWordPresentation8, oThingPresentation1)); 

		
		return new clsSearchSpaceOntologyLoader(oDataStructureTable); 
	}
}
