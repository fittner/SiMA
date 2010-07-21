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

import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationAttribute;
import pa.memorymgmt.datatypes.clsAssociationTime;
import pa.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsTemplateImage;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.07.2010, 21:46:16
 * 
 */
public class tssDataStructureClone {
	//TEST TP
	clsThingPresentation moThingPresentationTest = new clsThingPresentation("color:black", eDataType.TP, "color", "black"); 
	
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
		moThingPresentationMeshTPM1 = new clsThingPresentationMesh("TPM:TEST1", eDataType.TPM, moAssociatedDataStructureListTPM1);
		moAssociatedDataStructureListTPM1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM1, new clsThingPresentation("shape:circle", eDataType.TP, "shape", "circle")));
		moAssociatedDataStructureListTPM1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM1, new clsThingPresentation("color:red", eDataType.TP, "color", "red")));
		moAssociatedDataStructureListTPM1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM1, new clsThingPresentation("taste:sweet", eDataType.TP, "taste", "sweet")));
				
		moThingPresentationMeshTPM2 = new clsThingPresentationMesh("TPM:TEST2", eDataType.TPM, moAssociatedDataStructureListTPM2);
		moAssociatedDataStructureListTPM2.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM2, new clsThingPresentation("shape:rectangle", eDataType.TP, "shape", "rectangle")));
		moAssociatedDataStructureListTPM2.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM2, new clsThingPresentation("color:black", eDataType.TP, "color", "black")));
		moAssociatedDataStructureListTPM2.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM2, new clsThingPresentation("taste:sweet", eDataType.TP, "taste", "sweet")));
				
		moThingPresentationMeshTPM3 = new clsThingPresentationMesh("TPM:TEST3", eDataType.TPM, moAssociatedDataStructureListTPM3); 
		moAssociatedDataStructureListTPM3.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM3, new clsThingPresentation("shape:circle", eDataType.TP, "shape", "circle")));
		moAssociatedDataStructureListTPM3.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM3, new clsThingPresentation("color:red", eDataType.TP, "color", "red")));
		moAssociatedDataStructureListTPM3.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM3, moThingPresentationMeshTPM1));
		
		moTemplateImage = new clsTemplateImage("TI:TEST1", eDataType.TI,  moAssociatedTemporalStructuresTI);
		moAssociatedTemporalStructuresTI.add(new clsAssociationTime(null, eDataType.ASSOCIATIONTEMP, moTemplateImage, moThingPresentationMeshTPM1)); 
		moAssociatedTemporalStructuresTI.add(new clsAssociationTime(null, eDataType.ASSOCIATIONTEMP, moTemplateImage, moThingPresentationMeshTPM2)); 
		moAssociatedTemporalStructuresTI.add(new clsAssociationTime(null, eDataType.ASSOCIATIONTEMP, moTemplateImage, moThingPresentationMeshTPM3));
		
		moWordPresentation = new clsWordPresentation("WP:TEST", eDataType.WP, "test"); 
		
		oPrimaryDataStructureContainer = new clsPrimaryDataStructureContainer(moThingPresentationMeshTPM1, moAssociatedTemporalStructuresPDSC);
		moAssociatedTemporalStructuresPDSC.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM1, moThingPresentationMeshTPM2)); 
		moAssociatedTemporalStructuresPDSC.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM1, moThingPresentationMeshTPM3));
		moAssociatedTemporalStructuresPDSC.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, moThingPresentationMeshTPM1, moTemplateImage));
		
		oSecondaryDataStructureContainer = new clsSecondaryDataStructureContainer(moWordPresentation, moAssociatedTemporalStructuresSDSC);
		moAssociatedTemporalStructuresSDSC.add(new clsAssociationWordPresentation(null, eDataType.ASSOCIATIONWP, moWordPresentation, moThingPresentationMeshTPM2)); 
		moAssociatedTemporalStructuresSDSC.add(new clsAssociationWordPresentation(null, eDataType.ASSOCIATIONWP, moWordPresentation, moThingPresentationMeshTPM3));
		moAssociatedTemporalStructuresSDSC.add(new clsAssociationWordPresentation(null, eDataType.ASSOCIATIONWP, moWordPresentation, moTemplateImage));
	}
	
	@Test
	public void tssCloneTP() throws CloneNotSupportedException{
		clsThingPresentation oClone = (clsThingPresentation)moThingPresentationTest.clone();
		oClone.moContent= "test_change"; 
		
		if(!oClone.moContent.equals(moThingPresentationTest.moContent)){
			assertTrue(true);
		}
	}
	
	@Test
	public void tssCloneTPM() throws CloneNotSupportedException{
		clsThingPresentationMesh oClone = (clsThingPresentationMesh)moThingPresentationMeshTPM3.clone();
		
		oClone.oDataStructureID = "test_change_ID"; 
		oClone.moContent.remove(0); 
		
		if(!oClone.oDataStructureID.equals(moThingPresentationMeshTPM3.oDataStructureID) &&
					oClone.moContent.size()== moThingPresentationMeshTPM3.moContent.size()-1){
			assertTrue(true);
		}
	}
	
	@Test
	public void tssCloneTI() throws CloneNotSupportedException{
		clsTemplateImage oClone = (clsTemplateImage)moTemplateImage.clone();
		
		oClone.oDataStructureID = "test_change_ID"; 
		oClone.moContent.remove(0); 
		
		if(!oClone.oDataStructureID.equals(moTemplateImage.oDataStructureID) &&
					oClone.moContent.size()== moTemplateImage.moContent.size()-1){
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
		oClone.oDataStructureID= "test_change_ID"; 
		
		if(!oClone.oDataStructureID.equals(moWordPresentation.oDataStructureID)){
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
		oClone.moAssociatedDataStructures.clear(); 
		oClone.moDataStructure.oDataStructureID = "test_change_ID"; 
		
		if(oClone.moAssociatedDataStructures.size() < oPrimaryDataStructureContainer.moAssociatedDataStructures.size()&&
				!oPrimaryDataStructureContainer.moDataStructure.oDataStructureID.equals("test_change_ID")){
			assertTrue(true);
		}
	}
	
	@Test
	public void tssCloneSecondaryDataStructureContainer() throws CloneNotSupportedException{
		clsSecondaryDataStructureContainer oClone = (clsSecondaryDataStructureContainer)oSecondaryDataStructureContainer.clone(); 
		oClone.moAssociatedDataStructures.clear(); 
		oClone.moDataStructure.oDataStructureID = "test_change_ID"; 
		
		if(oClone.moAssociatedDataStructures.size() < oSecondaryDataStructureContainer.moAssociatedDataStructures.size()&&
				!oSecondaryDataStructureContainer.moDataStructure.oDataStructureID.equals("test_change_ID")){
			assertTrue(true);
		}
	}
	
}
