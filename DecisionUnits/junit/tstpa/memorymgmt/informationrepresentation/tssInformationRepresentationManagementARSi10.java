/**
 * tssInformationRepresentationManagementARSi10.java: DecisionUnits - tstpa.memorymgmt.informationrepresentation
 * 
 * @author zeilinger
 * 15.07.2010, 22:32:45
 */
package tstpa.memorymgmt.informationrepresentation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import config.clsBWProperties;

import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.clsKnowledgeBaseHandler;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v30.memorymgmt.datatypes.clsAssociationTime;
import pa._v30.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsTemplateImage;
import pa._v30.memorymgmt.datatypes.clsThingPresentation;
import pa._v30.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v30.memorymgmt.datatypes.clsWordPresentation;
import pa._v30.memorymgmt.enums.eDataType;
import pa._v30.memorymgmt.informationrepresentation.clsInformationRepresentationManagement;
import pa._v30.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceOntologyLoader;


/**
 * 
 * 
 * @author zeilinger
 * 15.07.2010, 22:32:45
 * 
 */
public class tssInformationRepresentationManagementARSi10 {
	int oID = 0; 
	
	@Test
	public void tssInitMemorySearch(){
		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPatternList = null;
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oResultList = null; 
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsInformationRepresentationManagement.getDefaultProperties(""));
		oProp.setProperty(clsKnowledgeBaseHandler.P_SOURCE_NAME, ""); 
		clsKnowledgeBaseHandler oKnowledgeBaseTEST = new clsInformationRepresentationManagement("", oProp); 
		clsSearchSpaceOntologyLoader oSearchSpace =  (clsSearchSpaceOntologyLoader)tssTestSearchSpace.createTestSearchSpace();
		
		((clsInformationRepresentationManagement)oKnowledgeBaseTEST).moSearchSpaceHandler.setSearchSpace(oSearchSpace);
		oSearchPatternList = createSearchPattern();		
		oResultList = oKnowledgeBaseTEST.initMemorySearch(oSearchPatternList);
		
		toString(oSearchPatternList, oResultList); 
		//HZ: Up to now the results are manually evaluated 
		assertTrue(true); 
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 20.07.2010, 22:11:37
	 *
	 * @return
	 */
	private ArrayList<clsPair<Integer, clsDataStructurePA>> createSearchPattern() {
		ArrayList<clsPair<Integer, clsDataStructurePA>> oList = new ArrayList<clsPair<Integer, clsDataStructurePA>>(); 
		
		/*
		 * 	ACT						(0x000001),
			ASSOCIATIONTEMP 		(0x000000),
			ASSOCIATIONATTRIBUTE	(0x000000),
			ASSOCIATIONWP			(0x000000),
			ASSOCIATIONDM			(0x000000),
			DM						(0x000010),
			TI						(0x000100),
			TP						(0x001000),
			TPM						(0x010000),
			WP						(0x100000); 
		 * */
		oList.add(new clsPair<Integer, clsDataStructurePA>(0x101000, defineTP()));
		oList.add(new clsPair<Integer, clsDataStructurePA>(0x101110, defineTPM()));
		oList.add(new clsPair<Integer, clsDataStructurePA>(0x111111, defineTI()));
		oList.add(new clsPair<Integer, clsDataStructurePA>(0x111000, defineDM()));
		oList.add(new clsPair<Integer, clsDataStructurePA>(0x000000, defineWP()));
		oList.add(new clsPair<Integer, clsDataStructurePA>(0x111111, defineWP()));
		return oList;
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 15.07.2010, 22:52:52
	 *
	 * @return
	 */
	private clsDataStructurePA defineDM() {
		clsThingPresentation oThingPresentation = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "drivesource"), "aggressivity");
		ArrayList<clsAssociation> oDriveAssociation2 = new ArrayList<clsAssociation>();
		clsDriveMesh oDriveMesh2 = new clsDriveMesh(new clsTripple<Integer, eDataType, String>(oID++, eDataType.DM,""),0.0,new double[]{0.0,0.0,0.0,0.0}, oDriveAssociation2, "test1");
		clsAssociationAttribute oAssociationAttribute11 = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), oDriveMesh2, oThingPresentation);
		oDriveAssociation2.add(oAssociationAttribute11);
		
		return oDriveMesh2;
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 15.07.2010, 22:52:37
	 *
	 * @return
	 */
	private clsDataStructurePA defineWP() {
		return new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.WP,""), "cake");
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 15.07.2010, 22:52:23
	 *
	 * @return
	 */
	private clsDataStructurePA defineTI() {
		clsTemplateImage oTemplateImage = null; 
		ArrayList<clsAssociation> oAssociatedTemporalStructures = new ArrayList<clsAssociation>();
		
		clsThingPresentationMesh oThingPresentationMesh1 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList1 = new ArrayList<clsAssociation>();
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.TP, "shape"), "circle")));
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "blue")));
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.TP, "taste"), "rodden")));
		oThingPresentationMesh1 = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(null, eDataType.TPM,""), oAssociatedDataStructureList1, "test1"); 
				
		clsThingPresentationMesh oThingPresentationMesh2 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList2 = new ArrayList<clsAssociation>();
		oAssociatedDataStructureList2.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh2, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "shape"), "rectangle")));
		oAssociatedDataStructureList2.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh2, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "black")));
		oAssociatedDataStructureList2.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh2, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "taste"), "sweet")));
		oThingPresentationMesh2 = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(null, eDataType.TPM,""), oAssociatedDataStructureList2, "test2"); 
				
		clsThingPresentationMesh oThingPresentationMesh3 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList3 = new ArrayList<clsAssociation>();
		oAssociatedDataStructureList3.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh3, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.TP, "shape"), "circle")));
		oAssociatedDataStructureList3.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh3, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "red")));
		oAssociatedDataStructureList3.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh3, oThingPresentationMesh1));
		oThingPresentationMesh3 = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(null, eDataType.TPM,""), oAssociatedDataStructureList3, "test3"); 
		
		oAssociatedTemporalStructures.add(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,""), oTemplateImage, oThingPresentationMesh1)); 
		oAssociatedTemporalStructures.add(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,""), oTemplateImage, oThingPresentationMesh2)); 
		oAssociatedTemporalStructures.add(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,""), oTemplateImage, oThingPresentationMesh3)); 
		
		oTemplateImage = new clsTemplateImage(new clsTripple<Integer, eDataType, String>(null, eDataType.TI,""), oAssociatedTemporalStructures, "test4"); 
		
		return oTemplateImage;
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 15.07.2010, 22:52:11
	 *
	 * @return
	 */
	private clsDataStructurePA defineTPM() {
		clsThingPresentationMesh oThingPresentationMesh = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList = new ArrayList<clsAssociation>();

		//Generation of associated attributes
		oAssociatedDataStructureList.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.TP, "shape"), "circle")));
		oAssociatedDataStructureList.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "red")));
	
		clsThingPresentationMesh oThingPresentationMesh1 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList1 = new ArrayList<clsAssociation>();
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.TP, "shape"), "circle")));
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.TP, "color"), "red")));
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh1, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "taste"), "sweet")));
		oThingPresentationMesh1 = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(null, eDataType.TPM,""), oAssociatedDataStructureList1, "test5");
	
		oAssociatedDataStructureList.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,""), oThingPresentationMesh, oThingPresentationMesh1));

		//TPM generation
		oThingPresentationMesh =
			new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(null, eDataType.TPM,""), oAssociatedDataStructureList, "test6"); 
		
		return oThingPresentationMesh;
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 15.07.2010, 22:51:29
	 *
	 * @return
	 */
	private clsDataStructurePA defineTP() {
		clsThingPresentation oThingPresentation = 
			new clsThingPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.TP, "color"), "blue");
		return oThingPresentation;
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 21.07.2010, 14:57:55
	 *
	 * @param oSearchPatternList
	 * @param oResultList
	 */
	private void toString(
			ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPatternList,
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> oResultList) {
		
		String oOutput = ""; 
		oOutput += "Search-pattern: \n";
		for(clsPair<Integer, clsDataStructurePA> oEntry : oSearchPatternList){
			oOutput += "ReturnValue " + oEntry.a +" DataStructure " + oEntry.b.toString() +"\n"; 
		}
		
		oOutput += "Result:";
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntryList : oResultList){
			for(clsPair<Double, clsDataStructureContainer> oEntry : oEntryList){
				oOutput += "\nMatchfactor:"+oEntry.a + "\n:DataStructureContainer:" + oEntry.b.toString(); 
			}
		}
		System.out.println(oOutput); 
	}
}
