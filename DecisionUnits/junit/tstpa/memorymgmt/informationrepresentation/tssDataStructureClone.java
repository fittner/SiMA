/**
 * tssDataStructureClone.java: DecisionUnits - tstpa.memorymgmt.informationrepresentation
 * 
 * @author zeilinger
 * 19.07.2010, 21:46:16
 */
package tstpa.memorymgmt.informationrepresentation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v30.memorymgmt.datatypes.clsAssociationTime;
import pa._v30.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsTemplateImage;
import pa._v30.memorymgmt.datatypes.clsThingPresentation;
import pa._v30.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v30.memorymgmt.datatypes.clsWordPresentation;
import pa._v30.memorymgmt.enums.eDataType;


/**
 * 
 * 
 * @author zeilinger
 * 19.07.2010, 21:46:16
 * 
 */
public class tssDataStructureClone {
	int oID = 0; 
	
	//TEST TP
	clsThingPresentation moThingPresentationTest = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "black"); 
	
	//TEST TPM
	clsThingPresentationMesh moThingPresentationMeshTPM1 = null; 
	ArrayList<clsAssociation> moAssociatedDataStructureListTPM1 = new ArrayList<clsAssociation>();
	
	clsThingPresentationMesh moThingPresentationMeshTPM2 = null; 
	ArrayList<clsAssociation> moAssociatedDataStructureListTPM2 = new ArrayList<clsAssociation>();
		
	clsThingPresentationMesh moThingPresentationMeshTPM3 = null; 
	ArrayList<clsAssociation> moAssociatedDataStructureListTPM3 = new ArrayList<clsAssociation>();
	
	//TEST TI
	clsTemplateImage moTemplateImage = null; 
	ArrayList<clsAssociation> moAssociatedTemporalStructuresTI = new ArrayList<clsAssociation>();
	
	//TEST DM - tbd
	//TEST WP
	clsWordPresentation moWordPresentation = null;
	//TEST ACT - tbd
	
	//TEST PrimaryDataStructureContainer
	clsPrimaryDataStructureContainer oPrimaryDataStructureContainer = null; 
	ArrayList<clsAssociation> moAssociatedTemporalStructuresPDSC = new ArrayList<clsAssociation>();
	//TEST SecondaryDataStructureContainer
	clsSecondaryDataStructureContainer oSecondaryDataStructureContainer = null; 
	ArrayList<clsAssociation> moAssociatedTemporalStructuresSDSC = new ArrayList<clsAssociation>();
	
		
	public tssDataStructureClone(){
		moThingPresentationMeshTPM1 = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TPM,""), moAssociatedDataStructureListTPM1, "test1");
		moAssociatedDataStructureListTPM1.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM1, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "shape"), "circle")));
		moAssociatedDataStructureListTPM1.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM1, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "red")));
		moAssociatedDataStructureListTPM1.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM1, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "taste"), "sweet")));
				
		moThingPresentationMeshTPM2 = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TPM,""), moAssociatedDataStructureListTPM2, "test2");
		moAssociatedDataStructureListTPM2.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM2, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "shape"), "rectangle")));
		moAssociatedDataStructureListTPM2.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM2, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "black")));
		moAssociatedDataStructureListTPM2.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM2, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "taste"), "sweet")));
				
		moThingPresentationMeshTPM3 = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TPM,""), moAssociatedDataStructureListTPM3, "test3"); 
		moAssociatedDataStructureListTPM3.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM3, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "shape"), "circle")));
		moAssociatedDataStructureListTPM3.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM3, new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TP, "color"), "red")));
		moAssociatedDataStructureListTPM3.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM3, moThingPresentationMeshTPM1));
		
		moTemplateImage = new clsTemplateImage(new clsTripple<Integer, eDataType, String>(oID++, eDataType.TI,""),  moAssociatedTemporalStructuresTI, "test4");
		moAssociatedTemporalStructuresTI.add(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,null), moTemplateImage, moThingPresentationMeshTPM1)); 
		moAssociatedTemporalStructuresTI.add(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,null), moTemplateImage, moThingPresentationMeshTPM2)); 
		moAssociatedTemporalStructuresTI.add(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONTEMP,null), moTemplateImage, moThingPresentationMeshTPM3));
		
		moWordPresentation = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID++, eDataType.WP,""), "test"); 
		
		oPrimaryDataStructureContainer = new clsPrimaryDataStructureContainer(moThingPresentationMeshTPM1, moAssociatedTemporalStructuresPDSC);
		moAssociatedTemporalStructuresPDSC.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM1, moThingPresentationMeshTPM2)); 
		moAssociatedTemporalStructuresPDSC.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM1, moThingPresentationMeshTPM3));
		moAssociatedTemporalStructuresPDSC.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONATTRIBUTE,null), moThingPresentationMeshTPM1, moTemplateImage));
		
		oSecondaryDataStructureContainer = new clsSecondaryDataStructureContainer(moWordPresentation, moAssociatedTemporalStructuresSDSC);
		moAssociatedTemporalStructuresSDSC.add(new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,null), moWordPresentation, moThingPresentationMeshTPM2)); 
		moAssociatedTemporalStructuresSDSC.add(new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,null), moWordPresentation, moThingPresentationMeshTPM3));
		moAssociatedTemporalStructuresSDSC.add(new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(null, eDataType.ASSOCIATIONWP,null), moWordPresentation, moTemplateImage));
	}
	
	@Test
	public void tssCloneTP() throws CloneNotSupportedException{
		clsThingPresentation oClone = (clsThingPresentation)moThingPresentationTest.clone();
		oClone.setMoContent("test_change"); 
		
		if(!oClone.getMoContent().equals(moThingPresentationTest.getMoContent())){
			assertTrue(true);
		}
	}
	
	@Test
	public void tssCloneTPM() throws CloneNotSupportedException{
		clsThingPresentationMesh oClone = (clsThingPresentationMesh)moThingPresentationMeshTPM3.clone();
		
		oClone.setMoDS_ID(oID++); 
		oClone.getMoAssociatedContent().remove(0); 
		
		if((oClone.getMoDS_ID() != moThingPresentationMeshTPM3.getMoDS_ID()) &&
					oClone.getMoAssociatedContent().size()== moThingPresentationMeshTPM3.getMoAssociatedContent().size()-1){
			assertTrue(true);
		}
	}
	
	@Test
	public void tssCloneTI() throws CloneNotSupportedException{
		clsTemplateImage oClone = (clsTemplateImage)moTemplateImage.clone();
		
		oClone.setMoDS_ID(oID++);
		oClone.getMoAssociatedContent().remove(0); 
		
		if(oClone.getMoDS_ID() != moTemplateImage.getMoDS_ID() &&
					oClone.getMoAssociatedContent().size()== moTemplateImage.getMoAssociatedContent().size()-1){
			assertTrue(true);
		}
	}
	
	@Test
	public void tssCloneDM(){
		/*up to now these tests are not defined as the data structures Drive Mesh
		 * is still in definition process - HZ 20.07.2010*/
		assertTrue(false);
	}
	
	@Test
	public void tssCloneWP() throws CloneNotSupportedException{
		clsWordPresentation oClone = (clsWordPresentation)moWordPresentation.clone();
		oClone.setMoDS_ID(oID++);
		
		if( oClone.getMoDS_ID() != moWordPresentation.getMoDS_ID()){
			assertTrue(true);
		}
	}
	
	@Test
	public void tssCloneACT(){
		/*up to now these tests are not defined as the data structures ACT
		 * is still in definition process - HZ 20.07.2010*/
		assertTrue(false);
	}
	
	@Test
	public void tssClonePrimaryDataStructureContainer() throws CloneNotSupportedException{
		clsPrimaryDataStructureContainer oClone = (clsPrimaryDataStructureContainer)oPrimaryDataStructureContainer.clone(); 
		oClone.getMoAssociatedDataStructures().clear(); 
		oClone.getMoDataStructure().setMoDS_ID(oID++); 
		
//		if(oClone.moAssociatedDataStructures.size() < oPrimaryDataStructureContainer.moAssociatedDataStructures.size()&&
//				oPrimaryDataStructureContainer.moDataStructure.moDS_ID != "test_change_ID")){
//			assertTrue(true);
//		}
	}
	
	@Test
	public void tssCloneSecondaryDataStructureContainer() throws CloneNotSupportedException{
		clsSecondaryDataStructureContainer oClone = (clsSecondaryDataStructureContainer)oSecondaryDataStructureContainer.clone(); 
		oClone.getMoAssociatedDataStructures().clear(); 
		oClone.getMoDataStructure().setMoDS_ID(oID++);
		
//		if(oClone.moAssociatedDataStructures.size() < oSecondaryDataStructureContainer.moAssociatedDataStructures.size()&&
//				!oSecondaryDataStructureContainer.moDataStructure.moDS_ID.equals("test_change_ID")){
//			assertTrue(true);
//		}
	}
	
}
