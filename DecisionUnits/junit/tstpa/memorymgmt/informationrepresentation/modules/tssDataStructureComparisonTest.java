package tstpa.memorymgmt.informationrepresentation.modules;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationAttribute;
import pa.memorymgmt.datatypes.clsAssociationTime;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsTemplateImage;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.informationrepresentation.modules.clsDataStructureComparison;
import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase;
import pa.tools.clsPair;
import tstpa.memorymgmt.informationrepresentation.tssTestSearchSpace;

/**
 * tssDataStructureComparisonTest.java: DecisionUnits - tstpa.memorymgmt.informationrepresentation.modules
 * 
 * @author zeilinger
 * 15.07.2010, 22:34:20
 */


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 15.07.2010, 22:34:20
 * 
 */
public class tssDataStructureComparisonTest {
	@Test
	public void testcompareDataStructureTP_TESTSPACE(){
		clsDataStructurePA oDataStructureTestTP = defineTP();
		clsSearchSpaceBase oSearchSpaceTest = tssTestSearchSpace.createTestSearchSpace();; 
		ArrayList<clsPair<Double,clsDataStructurePA>> oTestResult = clsDataStructureComparison.compareDataStructures(oDataStructureTestTP, oSearchSpaceTest); 
		
		if(oTestResult.size()== 1 && oTestResult.get(0).a == 1.0){
			assertTrue(true);
		}
		else{
			assertTrue(false);
		}
	}
	
	@Test
	public void testcompareDataStructureTPM_TESTSPACE(){
		clsDataStructurePA oDataStructureTestTPM = defineTPM();
		clsSearchSpaceBase oSearchSpaceTest = tssTestSearchSpace.createTestSearchSpace();; 
		ArrayList<clsPair<Double,clsDataStructurePA>> oTestResult = clsDataStructureComparison.compareDataStructures(oDataStructureTestTPM, oSearchSpaceTest);
		
		if(oTestResult.size()== 2){
			if(oTestResult.get(0).a == 5.0 && oTestResult.get(1).a == 2.0) assertTrue(true);
		}
	}
	
	@Test
	public void testcompareDataStructureTI_TESTSPACE(){
		clsDataStructurePA oDataStructureTestTI = defineTI();
		clsSearchSpaceBase oSearchSpaceTest = tssTestSearchSpace.createTestSearchSpace();; 
		//clsSearchSpaceBase oSearchSpaceTest = clsSearchSpaceCreator.createSearchSpace(); 
		ArrayList<clsPair<Double,clsDataStructurePA>> oTestResult = clsDataStructureComparison.compareDataStructures(oDataStructureTestTI, oSearchSpaceTest); 
		
		if(oTestResult.size()==1 && oTestResult.get(0).a == 7.0){assertTrue(true);}
	}
	
	@Test
	public void testcompareDataStructureWP_TESTSPACE(){
//		clsDataStructurePA oDataStructureTestWP = defineWP();
//		clsSearchSpaceBase oSearchSpaceTest = this.createTestSearchSpace();; 
//		clsDataStructureComparison.compareDataStructures(oDataStructureTestWP, oSearchSpaceTest); 
		//testcase has to be defined
		assertTrue(false);
	}
	
	@Test
	public void testcompareDataStructureDM_TESTSPACE(){
//		clsDataStructurePA oDataStructureTestDM = defineDM();
//		clsSearchSpaceBase oSearchSpaceTest = this.createTestSearchSpace();; 
//		clsDataStructureComparison.compareDataStructures(oDataStructureTestDM, oSearchSpaceTest); 
		//testcase has to be defined		
		assertTrue(false);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.07.2010, 22:52:52
	 *
	 * @return
	 */
	private clsDataStructurePA defineDM() {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.07.2010, 22:52:37
	 *
	 * @return
	 */
	private clsDataStructurePA defineWP() {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
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
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh1, new clsThingPresentation(null, eDataType.TP, "shape", "circle")));
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh1, new clsThingPresentation("color:blue", eDataType.TP, "color", "blue")));
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh1, new clsThingPresentation(null, eDataType.TP, "taste", "rodden")));
		oThingPresentationMesh1 = new clsThingPresentationMesh(null, eDataType.TPM, oAssociatedDataStructureList1); 
				
		clsThingPresentationMesh oThingPresentationMesh2 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList2 = new ArrayList<clsAssociation>();
		oAssociatedDataStructureList2.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh2, new clsThingPresentation("shape:rectangle", eDataType.TP, "shape", "rectangle")));
		oAssociatedDataStructureList2.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh2, new clsThingPresentation("color:black", eDataType.TP, "color", "black")));
		oAssociatedDataStructureList2.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh2, new clsThingPresentation("taste:sweet", eDataType.TP, "taste", "sweet")));
		oThingPresentationMesh2 = new clsThingPresentationMesh(null, eDataType.TPM, oAssociatedDataStructureList2); 
				
		clsThingPresentationMesh oThingPresentationMesh3 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList3 = new ArrayList<clsAssociation>();
		oAssociatedDataStructureList3.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh3, new clsThingPresentation(null, eDataType.TP, "shape", "circle")));
		oAssociatedDataStructureList3.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh3, new clsThingPresentation("color:red", eDataType.TP, "color", "red")));
		oAssociatedDataStructureList3.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh3, oThingPresentationMesh1));
		oThingPresentationMesh3 = new clsThingPresentationMesh(null, eDataType.TPM, oAssociatedDataStructureList3); 
		
		oAssociatedTemporalStructures.add(new clsAssociationTime(null, eDataType.ASSOCIATIONTEMP, oTemplateImage, oThingPresentationMesh1)); 
		oAssociatedTemporalStructures.add(new clsAssociationTime(null, eDataType.ASSOCIATIONTEMP, oTemplateImage, oThingPresentationMesh2)); 
		oAssociatedTemporalStructures.add(new clsAssociationTime(null, eDataType.ASSOCIATIONTEMP, oTemplateImage, oThingPresentationMesh3)); 
		
		oTemplateImage = new clsTemplateImage(null, eDataType.TI,  oAssociatedTemporalStructures); 
		
		return oTemplateImage;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
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
		oAssociatedDataStructureList.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh, new clsThingPresentation(null, eDataType.TP, "shape", "circle")));
		oAssociatedDataStructureList.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh, new clsThingPresentation("color:red", eDataType.TP, "color", "red")));
	
		clsThingPresentationMesh oThingPresentationMesh1 = null; 
		ArrayList<clsAssociation> oAssociatedDataStructureList1 = new ArrayList<clsAssociation>();
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh1, new clsThingPresentation("shape:circle", eDataType.TP, "shape", "circle")));
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh1, new clsThingPresentation("color:red", eDataType.TP, "color", "red")));
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh1, new clsThingPresentation("taste:sweet", eDataType.TP, "taste", "sweet")));
		oThingPresentationMesh1 = new clsThingPresentationMesh("TPM:TEST1", eDataType.TPM, oAssociatedDataStructureList1);
	
		oAssociatedDataStructureList.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh, oThingPresentationMesh1));

		//TPM generation
		oThingPresentationMesh =
			new clsThingPresentationMesh(null, eDataType.TPM, oAssociatedDataStructureList); 
		
		return oThingPresentationMesh;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.07.2010, 22:51:29
	 *
	 * @return
	 */
	private clsDataStructurePA defineTP() {
		clsThingPresentation oThingPresentation = 
			new clsThingPresentation(null, eDataType.TP, "color", "blue");
		return oThingPresentation;
	}
}
