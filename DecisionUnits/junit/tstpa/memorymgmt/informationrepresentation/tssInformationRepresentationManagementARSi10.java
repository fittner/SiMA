/**
 * tssInformationRepresentationManagementARSi10.java: DecisionUnits - tstpa.memorymgmt.informationrepresentation
 * 
 * @author zeilinger
 * 15.07.2010, 22:32:45
 */
package tstpa.memorymgmt.informationrepresentation;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import config.clsBWProperties;

import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationAttribute;
import pa.memorymgmt.datatypes.clsAssociationTime;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsTemplateImage;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.informationrepresentation.clsInformationRepresentationManagementARSi10;
import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceOntologyLoader;
import pa.tools.clsPair;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 15.07.2010, 22:32:45
 * 
 */
public class tssInformationRepresentationManagementARSi10 {
	
	@Test
	public void tssInitMemorySearch(){
		ArrayList<clsPair<Integer, clsDataStructureContainer>> oSearchPatternList = null;
		List<ArrayList<clsPair<Double,clsDataStructureContainer>>> oResultList = null; 
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsInformationRepresentationManagementARSi10.getDefaultProperties(""));
		oProp.setProperty(clsKnowledgeBaseHandler.P_SOURCE_NAME, ""); 
		clsKnowledgeBaseHandler oKnowledgeBaseTEST = new clsInformationRepresentationManagementARSi10("", oProp); 
		clsSearchSpaceOntologyLoader oSearchSpace =  (clsSearchSpaceOntologyLoader)tssTestSearchSpace.createTestSearchSpace();
		
		((clsInformationRepresentationManagementARSi10)oKnowledgeBaseTEST).moSearchSpaceHandler.setSearchSpace(oSearchSpace);
		oSearchPatternList = createSearchPattern();		
		oResultList = oKnowledgeBaseTEST.initMemorySearch(oSearchPatternList);
		
		toString(oSearchPatternList, oResultList); 
		//HZ: Up to now the results are manually evaluated 
		assertTrue(true); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.07.2010, 22:11:37
	 *
	 * @return
	 */
	private ArrayList<clsPair<Integer, clsDataStructureContainer>> createSearchPattern() {
		ArrayList<clsPair<Integer, clsDataStructureContainer>> oList = new ArrayList<clsPair<Integer, clsDataStructureContainer>>(); 
		
		/*
		 * 	ACT						(0x000001),
			ASSOCIATIONTEMP 		(0x000000),
			ASSCOCIATIONATTRIBUTE	(0x000000),
			ASSOCIATIONWP			(0x000000),
			ASSOCIATIONDM			(0x000000),
			DM						(0x000010),
			TI						(0x000100),
			TP						(0x001000),
			TPM						(0x010000),
			WP						(0x100000); 
		 * */
		oList.add(new clsPair<Integer, clsDataStructureContainer>(0x101000, new clsPrimaryDataStructureContainer(defineTP(),null)));
		oList.add(new clsPair<Integer, clsDataStructureContainer>(0x101110, new clsPrimaryDataStructureContainer(defineTPM(),null)));
		oList.add(new clsPair<Integer, clsDataStructureContainer>(0x111111, new clsPrimaryDataStructureContainer(defineTI(),null)));
		oList.add(new clsPair<Integer, clsDataStructureContainer>(0x111000, new clsPrimaryDataStructureContainer(defineDM(),null)));
		oList.add(new clsPair<Integer, clsDataStructureContainer>(0x000000, new clsPrimaryDataStructureContainer(defineWP(),null)));
		oList.add(new clsPair<Integer, clsDataStructureContainer>(0x111111, new clsPrimaryDataStructureContainer(defineWP(),null)));
		return oList;
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
		clsThingPresentation oThingPresentation = new clsThingPresentation("drivesource:aggressivity", eDataType.TP, "drivesource", "aggressivity");
		ArrayList<clsAssociation> oDriveAssociation2 = new ArrayList<clsAssociation>();
		clsDriveMesh oDriveMesh2 = new clsDriveMesh("drive:hunger", eDataType.DM, oDriveAssociation2);
		clsAssociationAttribute oAssociationAttribute11 = new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oDriveMesh2, oThingPresentation);
		oDriveAssociation2.add(oAssociationAttribute11);
		
		return oDriveMesh2;
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
		return new clsWordPresentation("wp:cake", eDataType.WP, "cake");
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
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh1, new clsThingPresentation(null, eDataType.TP, "shape", "circle")));
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh1, new clsThingPresentation(null, eDataType.TP, "color", "red")));
		oAssociatedDataStructureList1.add(new clsAssociationAttribute(null, eDataType.ASSCOCIATIONATTRIBUTE, oThingPresentationMesh1, new clsThingPresentation("taste:sweet", eDataType.TP, "taste", "sweet")));
		oThingPresentationMesh1 = new clsThingPresentationMesh(null, eDataType.TPM, oAssociatedDataStructureList1);
	
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

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 21.07.2010, 14:57:55
	 *
	 * @param oSearchPatternList
	 * @param oResultList
	 */
	private void toString(
			ArrayList<clsPair<Integer, clsDataStructureContainer>> oSearchPatternList,
			List<ArrayList<clsPair<Double, clsDataStructureContainer>>> oResultList) {
		
		String oOutput = ""; 
		oOutput += "Search-pattern: \n";
		for(clsPair<Integer, clsDataStructureContainer> oEntry : oSearchPatternList){
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
