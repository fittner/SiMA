/**
 * clsBlockedContentStorage.java: DecisionUnits - pa.storage
 * 
 * @author deutsch
 * 09.03.2011, 17:12:46
 */
package pa._v38.storage;

import java.util.ArrayList;
import java.util.Arrays;

import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsPrimarySpatialTools;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.modules.D2_1_receive;
import pa._v38.interfaces.modules.D2_2_send;
import pa._v38.interfaces.modules.D2_3_receive;
import pa._v38.interfaces.modules.D2_4_receive;
import pa._v38.interfaces.modules.D2_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationTime;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;


/**
 * Blocked content buffer
 * 
 * @author wendt
 * 09.03.2011, 17:12:46
 * 
 */
public class DT2_BlockedContentStorage implements itfInspectorInternalState, itfInterfaceDescription, D2_1_receive, D2_2_send, D2_4_send, D2_4_receive, D2_3_receive {
	/** Blocked content buffer */
	private ArrayList<clsDataStructurePA> moBlockedContent;
	
	/** Input/Output, perception. This variable is changed as new content is added to the image */
	private clsThingPresentationMesh moPerceptionalMesh;
	/** Input/Output, associated memories. This variable is changed as new content is added to the image */
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories;
	
	
	/**
	 * Minimum match factor a blocked content must achieve to be activated. 
	 * 
	 * @author Marcus Zottl (e0226304)
	 * 28.06.2011, 20:22:42
	 */
	private double mrActivationThreshold = 0.5;
	
	/**
	 * Limit to adjust the number of activated blocked contents. 
	 * 
	 * PERSONALITY PARAMETER
	 * 
	 * @author Marcus Zottl (e0226304)
	 * 28.06.2011, 20:21:28
	 */
	private int mnActivationLimit = 3;
	
	/**
	 * Constructor of the blocked content. It instanciates the storage and fills it with test data 
	 * 
	 * @author Wendt
	 * @since 03.08.2011 09:19:15
	 *
	 */
	public DT2_BlockedContentStorage() {		
		// The storage consists of an ArrayList of clsPair, in each pair, the element A is the DataStructure and
    	// the element B contains the AssociatedDataStructures from the PrimaryDataStructureContainer that has been blocked.
    	moBlockedContent = new ArrayList<clsDataStructurePA>();
    	// Fill with initial test data
    	//fillWithTestData();
    }

	
//	/**
//	 * Cerates a few DriveMeshes and TemplateImages and puts them in the BlockedContentStorage
//	 * to demonstrate the functionality of the matching algorithm.
//	 *
//	 * @author Marcus Zottl (e0226304),
//	 * 05.07.2011, 21:07:02
//	 *
//	 */
//	@SuppressWarnings("unchecked")
//	private void fillWithTestData() {
//    	ArrayList<ArrayList<Object>> oList = new ArrayList<ArrayList<Object>>();
//    	oList.add( new ArrayList<Object>( Arrays.asList("PUNCH", "BITE", 0.0, 0.0, 0.8, 0.2, -0.5) ) );
//    	oList.add( new ArrayList<Object>( Arrays.asList("GREEDY", "NOURISH", 0.8, 0.2, 0.0, 0.0, -0.3) ) );
//    	oList.add( new ArrayList<Object>( Arrays.asList("DIRTY", "DEPOSIT", 0.0, 0.7, 0.3, 0.0, -0.7) ) );
//
//    	for (ArrayList<Object> oData:oList) {
//			clsThingPresentation oTP = clsDataStructureGenerator.generateTP(new clsPair<String, Object>((String)oData.get(0), oData.get(0))); 
//			clsDriveMesh oDM = clsDataStructureGenerator.generateDM(new clsTriple<String, ArrayList<clsThingPresentation>, Object>((String)oData.get(1), 
//																	   new ArrayList<clsThingPresentation>(Arrays.asList(oTP)),
//																	   oData.get(0)));
//			oDM.setCategories( (Double)oData.get(2), (Double)oData.get(3), (Double)oData.get(4), (Double)oData.get(5) );
//			oDM.setPleasure( (Double)oData.get(6) ); 
//	    	moBlockedContent.add(new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(oDM, new ArrayList<clsAssociation>()));    	
//    	}
//    	
//    	// TestData: TemplateImage with a cake exactly like the one in the simulator
//    	// create a new TemplateImage
//    	clsTemplateImage newTI = new clsTemplateImage(new clsTriple<Integer, eDataType, String>(-1, eDataType.TI, "IMAGE:REPRESSED"), new ArrayList<clsAssociation>(), "REPRESSED_IMAGE");
//    	// create a cake to put into the TI
//    	clsThingPresentationMesh newTPM = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, String>(33, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "CAKE");
//    	clsThingPresentation newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(34, eDataType.TP, "TASTE"), "SWEET");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(35, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(36, eDataType.TP, "Color"), "#FFAFAF");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(37, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(38, eDataType.TP, "ShapeType"), "CIRCLE");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(39, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(40, eDataType.TP, "INTENSITY"), "MEDIUM");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(41, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	
//    	// add cake to TI 
//    	newTI.assignDataStructure(new clsAssociationTime(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
//    	
//    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(340, eDataType.TP, "Alive"), false);
//    	clsAssociationAttribute newAssocAttr = new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
//    	ArrayList<clsAssociation> newAssocDSs = new  ArrayList<clsAssociation>();
//    	newAssocDSs.add(newAssocAttr);
//    	
//    	// add a DriveMesh to the cake to demonstrate how this DM gets added to the cake
//    	// in the perception if this blocked content is matched in the perception
//    	oList = new ArrayList<ArrayList<Object>>();
//    	oList.add( new ArrayList<Object>( Arrays.asList("DIRTY", "DEPOSIT", 0.0, 0.7, 0.3, 0.0, -0.7) ) );
//
//    	for (ArrayList<Object> oData:oList) {
//			clsThingPresentation oTP = clsDataStructureGenerator.generateTP(new clsPair<String, Object>((String)oData.get(0), oData.get(0))); 
//			clsDriveMesh oDM = clsDataStructureGenerator.generateDM(new clsTriple<String, ArrayList<clsThingPresentation>, Object>((String)oData.get(1), 
//																	   new ArrayList<clsThingPresentation>(Arrays.asList(oTP)),
//																	   oData.get(0)));
//			oDM.setCategories( (Double)oData.get(2), (Double)oData.get(3), (Double)oData.get(4), (Double)oData.get(5) );
//			oDM.setPleasure( (Double)oData.get(6) );
//			newAssocDSs.add(new clsAssociationDriveMesh(new clsTriple<Integer, eDataType, String>(10400, eDataType.DM, "ASSOCIATIONDM"), oDM, newTPM));
//    	}
//
//    	// add TI and associatedDataSructures to blockedContent
//    	moBlockedContent.add(new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(newTI, newAssocDSs));
//    	
//    	// TestData: TemplateImage with a cake that differs from the one in the simulator
//    	// create a new TemplateImage
//    	newTI = new clsTemplateImage(new clsTriple<Integer, eDataType, String>(-1, eDataType.TI, "IMAGE:REPRESSED"), new ArrayList<clsAssociation>(), "REPRESSED_IMAGE");
//    	// create a cake to put into the TI
//    	newTPM = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, String>(11033, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "CAKE");
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(34, eDataType.TP, "TASTE"), "SWEET");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(11035, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(11036, eDataType.TP, "Color"), "#61210B");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(11037, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(11038, eDataType.TP, "ShapeType"), "SQARE");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(11039, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(40, eDataType.TP, "INTENSITY"), "MEDIUM");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(11041, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	// add cake to TI 
//    	newTI.assignDataStructure(new clsAssociationTime(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
//    	
//    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(340, eDataType.TP, "Alive"), false);
//    	newAssocAttr = new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
//    	newAssocDSs = new  ArrayList<clsAssociation>();
//    	newAssocDSs.add(newAssocAttr);
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(11350, eDataType.TP, "LOCATION"), "RIGHT");
//    	newAssocAttr = new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
//    	newAssocDSs.add(newAssocAttr);
//
//    	// add TI and associatedDataSructures to blockedContent
//    	moBlockedContent.add(new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(newTI, newAssocDSs));
//    	
//    	// TestData: TemplateImage with cake and a wall that is far away (Location FAR)
//    	// create a new TemplateImage
//    	newTI = new clsTemplateImage(new clsTriple<Integer, eDataType, String>(-1, eDataType.TI, "IMAGE:REPRESSED"), new ArrayList<clsAssociation>(), "REPRESSED_IMAGE");
//    	// create a cake to put into the TI
//    	newTPM = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, String>(12033, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "CAKE");
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(34, eDataType.TP, "TASTE"), "SWEET");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(12035, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(36, eDataType.TP, "Color"), "#FFAFAF");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(12037, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(38, eDataType.TP, "ShapeType"), "CIRCLE");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(12039, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(40, eDataType.TP, "INTENSITY"), "MEDIUM");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(12041, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	// add cake to TI 
//    	newTI.assignDataStructure(new clsAssociationTime(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
//    	
//    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(340, eDataType.TP, "Alive"), false);
//    	newAssocAttr = new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
//    	newAssocDSs = new  ArrayList<clsAssociation>();
//    	newAssocDSs.add(newAssocAttr);
//    	    	
//    	// create a wall to put into the TI
//    	newTPM = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, String>(12050, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "WALL");
//    	// add wall to TI 
//    	newTI.assignDataStructure(new clsAssociationTime(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
//    	
//    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(120350, eDataType.TP, "LOCATION"), "FAR");
//    	newAssocAttr = new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
//    	newAssocDSs.add(newAssocAttr);
//
//    	// add TI and associatedDataSructures to blockedContent
//    	moBlockedContent.add(new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(newTI, newAssocDSs));
//    	
//    	// TestData: TemplateImage with cake and an agent that is far away (location FAR)
//    	// create a new TemplateImage
//    	newTI = new clsTemplateImage(new clsTriple<Integer, eDataType, String>(-1, eDataType.TI, "IMAGE:REPRESSED"), new ArrayList<clsAssociation>(), "REPRESSED_IMAGE");
//    	// create a cake to put into the TI
//    	newTPM = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, String>(13033, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "CAKE");
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(34, eDataType.TP, "TASTE"), "SWEET");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(13035, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(36, eDataType.TP, "Color"), "#FFAFAF");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(13037, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(38, eDataType.TP, "ShapeType"), "CIRCLE");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(13039, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(40, eDataType.TP, "INTENSITY"), "MEDIUM");
//    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(13041, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
//    	// add cake to TI 
//    	newTI.assignDataStructure(new clsAssociationTime(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
//    	
//    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(340, eDataType.TP, "Alive"), false);
//    	newAssocAttr = new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
//    	newAssocDSs = new  ArrayList<clsAssociation>();
//    	newAssocDSs.add(newAssocAttr);
//    	    	
//    	// create a agent to put into the TI
//    	newTPM = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, String>(13050, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "ARSIN");
//    	// FIXME (zottl) add properties to Agent! look at properties of a perceived Agent to find out how to construct one
//    	// add agent to TI 
//    	newTI.assignDataStructure(new clsAssociationTime(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
//    	
//    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(130340, eDataType.TP, "Alive"), true);
//    	newAssocAttr = new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
//    	newAssocDSs.add(newAssocAttr);
//    	newTPMProperty = new clsThingPresentation(new clsTriple<Integer, eDataType, String>(120350, eDataType.TP, "LOCATION"), "FAR");
//    	newAssocAttr = new clsAssociationAttribute(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
//    	newAssocDSs.add(newAssocAttr);
//
//    	// add TI and associatedDataSructures to blockedContent
//    	moBlockedContent.add(new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(newTI, newAssocDSs));
//    }

	/**
	 * Stores a single DataStructurePA directly into the blocked content.<br>
	 * <br>
	 * This method is just for convenience to simplify storage of single
	 * DataStructures that are normally not passed around in a container.
	 *
	 * @author Zottl Marcus (e0226304),
	 * 20.06.2011, 09:54:19
	 *
	 * @param poNewDS	- the item to be put into the blockedContentStorage
	 */
	public void storeBlockedDataStructure(clsDataStructurePA poNewDS) {
		//Only TPM or DM are allowed
		moBlockedContent.add(poNewDS);		
	}
	
	/**
	 * Acquires a list of matching items from the blocked content storage and enriches the incoming
	 * perception according to the following rules:
	 * <ul>
	 * <li>If the match contains a TemplateImage and is a full match (match value = 1) then all the
	 * DriveMeshes associated with elements in the TemplateImage are added to the perception by
	 * associating them with their matching "partner"-elements in the perceived TemplateImage.</li>
	 * <li>If the match contains a TemplateImage and is a partial match (match value < 1) then
	 * the whole TemplateImage is added to the moAssociatedMemories.</li>
	 * <li>If the match contains an "independent" DriveMesh, it is added to the perceived TemplateImage
	 * by associating it with the element that it matched with.</li>
	 * </ul>
	 * 
	 * <b>Variables read by this method:</b><br>
	 * {@link #mrActivationThreshold}	- defines a minimum quality for matches<br>
	 * {@link #mnActivationLimit}		- limits the number of considered matches<br> 
	 * <br>
	 * <b>Variables modified by this method:</b><br>
	 * {@link #moAssociatedMemories_OUT}	- partially matching TIs are added to this list<br>
	 * <br>
	 *
	 * @author Zottl Marcus
	 * 22.06.2011, 18:47:30
	 *
	 * @param poPerception - the perceived input with attached DriveMeshes and adapted categories for
	 * which you want to find matches in the blocked content storage. 
	 */
	private void matchBlockedContentPerception(clsThingPresentationMesh poPerception) {
		ArrayList<clsTriple<clsDataStructurePA, Double, ArrayList<clsAssociationDriveMesh>>> oMatchedContent;
		
		// look up matching content
		oMatchedContent = getMatchesForPerception(poPerception, mrActivationThreshold);
		// now pick the topmost matches and process them accordingly
		int i = 0;
		for (clsTriple<clsDataStructurePA, Double, ArrayList<clsAssociationDriveMesh>> matchedItem : oMatchedContent) {
			i++;
			if (i > mnActivationLimit) break;
			
			//case 1: the item is a TemplateImage
			if (matchedItem.a instanceof clsThingPresentationMesh) {
				// case 1a: full match (matchValue = 1)
				if (matchedItem.b == 1.0) {
					//attach all DMs in result to the input TI, integrate all dm
					//1. from the list with DM, find the correct root element in the image by comparing the ID
					for (clsAssociationDriveMesh oDMAssociation : matchedItem.c) {
						//Get a list of all found items
						ArrayList<clsThingPresentationMesh> oFoundObjects = clsMeshTools.searchDataStructureTypesInMesh(poPerception, (clsThingPresentationMesh) oDMAssociation.getRootElement() ,1);
						//2. If found, create a new association with this dm and the found root element. This association shall be added to all these objects
						for (clsThingPresentationMesh oObject : oFoundObjects) {
							//3. create a new association
							clsAssociationDriveMesh oNewMesh = new clsAssociationDriveMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONDM, eContentType.ASSOCIATIONDM), (clsDriveMeshOLD) oDMAssociation.getLeafElement(), oObject);
							//4. Add the association to the external associations of the root element
							oObject.getExternalMoAssociatedContent().add(oNewMesh);
						}
					}
				}
				// case 1b: partial match (matchValue < 1)
				else {
					// add complete result to associated memories by creating
					clsAssociationPrimary oNewPriAss = (clsAssociationPrimary) clsDataStructureGenerator.generateASSOCIATIONPRI(eContentType.ASSOCIATIONPRI, poPerception, (clsThingPresentationMesh) matchedItem.a, matchedItem.b);
					poPerception.getExternalMoAssociatedContent().add(oNewPriAss);
					((clsThingPresentationMesh)matchedItem.a).getExternalMoAssociatedContent().add(oNewPriAss);
					
				}
				// activated content has to be deleted from the blocked content storage
				this.removeBlockedContent(matchedItem.a);
			}
			// case 2: the item is a DriveMesh
			else if (matchedItem.a instanceof clsDriveMeshOLD) {
				// attach all DMs in result to the input TI
				//Here, only one association exists
				for (clsAssociationDriveMesh oAssDM : matchedItem.c) {
					//In the method getMatchWithPerception, the instance from the perception is linked with the DM
					//Add this association the the object
					clsThingPresentationMesh oNewSourceObject = (clsThingPresentationMesh) oAssDM.getRootElement();
					oNewSourceObject.getExternalMoAssociatedContent().add(oAssDM);
				}
				//clsDataStructureTools.findDataStructureTypesInMesh(poPerception, (clsThingPresentationMesh) oDMAssociation.getRootElement() ,1);
				//poPerception.getMoAssociatedDataStructures().addAll(matchedItem.c);
				// activated content has to be deleted from the blocked content storage
				this.removeBlockedContent(matchedItem.a);
			}
		}
	}
	
	/**
	 * (FG) This method was copied from AW's method: public clsDriveMesh getBestMatchCONVERTED(clsPrimaryDataStructureContainer poInput, boolean boRemoveAfterActivate)	
	 * finds best match in list of clsDriveMeshes of repressed content
	 * Function overloading
	 *
	 * @since 03.08.2011 09:21:27
	 *
	 * @param poInput
	 * @return
	 */
	public clsDriveMeshOLD matchBlockedContentDrives(ArrayList<clsDriveMeshOLD> poInput) {
		// get only free drives
		
		clsDriveMeshOLD oRetVal = null;
		double rHighestMatch = 0.0;

		for(clsDriveMeshOLD oDrivePair : poInput){ 
			clsDriveMeshOLD oInputDrive = oDrivePair;
			clsPair<Double, clsDriveMeshOLD> oMatch = null;
			if ((getMatchesForDrives(oInputDrive, 0).isEmpty()==false)) {
				oMatch =  getMatchesForDrives(oInputDrive, 0).get(0); //You could use mrActivationThreshold			
				double rMatchValue = oMatch.a; 
			
				if(rMatchValue > rHighestMatch) {
						rHighestMatch = rMatchValue;
						oRetVal = oMatch.b;
				}
				if(rHighestMatch >= 1) { break;	} //do the doublebreak to abort search --> first come first serve
			}
		}
		
		// activated content has to be deleted from the blocked content storage
		if (oRetVal!=null) {
			this.removeBlockedContent(oRetVal);
		}
		
		return oRetVal;
	}
	
	
	/**
	 * Compares each item in the blockedContentStorage with the input and returns
	 * matching items as a list sorted by the match quality.<br>
	 * <br>
	 * Calculates the match for each item in the blockedContentStorage and returns
	 * those which have a match quality equal or above the provided threshold
	 * along with their match quality and a list for each matched item that
	 * contains associations between any DMs in the matched item and the matching
	 * items in the input. The last part of the result is done here because it is
	 * way more efficient to create those associations "on the fly" while
	 * comparing the items than to later extract the DMs from the results and find
	 * their correct targets in the input - which would essentially require a
	 * second match algorithm.
	 *
	 * @author Zottl Marcus (e0226304),
	 * 19.06.2011, 22:48:09
	 *
	 * @param poPerception	- the input for which matches have to be found.
	 * @param poThreshold		- a number between 0 and 1 to filter out matches below
	 * a desired quality.
	 * @return							- a list of clsTripples with A = the matching item 
	 * (clsPrimaryDataStructureContainer), B = the quality of the match (double)
	 * and C = a list of Associations between DMs in A and their matching "partners"
	 * in the perception (ArrayList&lt;clsAssociationDriveMesh&gt;)
	 */
	private ArrayList<clsTriple<clsDataStructurePA, Double, ArrayList<clsAssociationDriveMesh>>> getMatchesForPerception(clsThingPresentationMesh poImage, double poThreshold) {
		
		//Return 
		//1: The repressed image with the match
		//2: The match value
		//3: The associated drive meshes from that image
		ArrayList<clsTriple<clsDataStructurePA, Double,ArrayList<clsAssociationDriveMesh>>> oMatchValues = new ArrayList<clsTriple<clsDataStructurePA, Double, ArrayList<clsAssociationDriveMesh>>>();
		clsDataStructurePA oBlockedCont;

		//For comparison with DMs, get all DM in the mesh
		ArrayList<clsAssociationDriveMesh> oDMFromMesh = clsMeshTools.getAllDMInMesh(poImage);
		
		// compare each element from moBlockedContent with the input
		for (clsDataStructurePA oEntry : moBlockedContent) {
			if (oEntry instanceof clsThingPresentationMesh) {
				// if item is a TI, then calculate match with input TI
				oBlockedCont = oEntry;
				//oBlockedCont = new clsPrimaryDataStructureContainer(oEntry.a, oEntry.b);
				//clsPair<Double, ArrayList<clsAssociationDriveMesh>> oMatchResult = calculateTIMatch(oBlockedCont, poPerception);
				//Use structures from data structure comparison
				//clsPair<Double, ArrayList<clsAssociationDriveMesh>> oMatchResult = clsDataStructureComparison.compareTIContainerInclDM(oBlockedCont, poPerception, true);

				double oMatchResult = clsPrimarySpatialTools.getImageMatch(poImage, (clsThingPresentationMesh) oBlockedCont);
				// ignore matches below threshold
				if (oMatchResult < poThreshold)
					continue;
				// ensure that the list of results is sorted by the matchValues, with the highest matchValues on top of the list.
				int i = 0;
				while ((i + 1 < oMatchValues.size()) && oMatchResult < oMatchValues.get(i).b) {
					i++;
				}
				
				//Get all drive meshes in the image
				ArrayList<clsAssociationDriveMesh> oDMList = clsMeshTools.getAllDMInMesh((clsThingPresentationMesh) oBlockedCont);
				
				// add the blocked content to results
				oMatchValues.add(i, new clsTriple<clsDataStructurePA, Double, ArrayList<clsAssociationDriveMesh>>((clsThingPresentationMesh) oBlockedCont, oMatchResult, oDMList));
				
			}
			else if (oEntry instanceof clsDriveMeshOLD) {
				oBlockedCont = oEntry;
				
				// if item is a DM, then compare with all associated DMs of the input
				for(clsAssociationDriveMesh oInputAssociation : oDMFromMesh) {
					//if(oInputAssociation instanceof clsAssociationDriveMesh){
					clsDriveMeshOLD oData = ((clsAssociationDriveMesh)oInputAssociation).getDM(); 
					if(oEntry.getMoContentType().equals(oData.getMoContentType())) {
						// calculate match between drive matches
						double rMatchValue = ((clsDriveMeshOLD)oEntry).matchCathegories(oData);
						// ignore matches below threshold
						if (rMatchValue < poThreshold)
							continue;

						// add the association with the matching element from the input to the return values
						ArrayList<clsAssociationDriveMesh> newDMAssociations = new ArrayList<clsAssociationDriveMesh>();
						clsThingPresentationMesh newRoot = (clsThingPresentationMesh) ((clsAssociationDriveMesh)oInputAssociation).getRootElement();
						newDMAssociations.add(new clsAssociationDriveMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONDM, eContentType.ASSOCIATIONDM), (clsDriveMeshOLD)oEntry, newRoot));
						//oBlockedCont = new clsPrimaryDataStructureContainer(oEntry.a, oEntry.b);
						// ensure that the list of results is sorted by the matchValues, with the highest matchValues on top of the list.
						int i = 0;
						while ((i + 1 < oMatchValues.size()) && rMatchValue < oMatchValues.get(i).b) {
							i++;
						}
						// add to results
						oMatchValues.add(i, new clsTriple<clsDataStructurePA, Double, ArrayList<clsAssociationDriveMesh>>((clsDriveMeshOLD) oEntry, rMatchValue, newDMAssociations));
					}
					//}
				}
			}
			// no other types of data have to be considered yet
		}
		return oMatchValues;
	}
	
	/**
	 * With the input of a DM and a threshold, the corresponding drive meshes is loaded from the storage
	 *
	 * @since 03.08.2011 09:21:55
	 *
	 * @param poDM
	 * @param poThreshold
	 * @return
	 */
	private ArrayList<clsPair<Double, clsDriveMeshOLD>> getMatchesForDrives(clsDriveMeshOLD poDM, double poThreshold) {
		ArrayList<clsPair<Double, clsDriveMeshOLD>> oRetVal = new ArrayList<clsPair<Double, clsDriveMeshOLD>>();	
		
		for (clsDataStructurePA oEntry : moBlockedContent) {
			if (oEntry instanceof clsDriveMeshOLD) { 
				if(oEntry.getMoContentType().equals(poDM.getMoContentType())) {
					// calculate match between drive matches
					double rMatchValue = ((clsDriveMeshOLD)oEntry).matchCathegories(poDM);
					// ignore matches below threshold
					if (rMatchValue < poThreshold)
						continue;
					
					int i = 0;
					while ((i + 1 < oRetVal.size()) && rMatchValue < oRetVal.get(i).a) {
						i++;
					}
					// add to results
					oRetVal.add(i, 
							new clsPair<Double, clsDriveMeshOLD>(rMatchValue, (clsDriveMeshOLD)oEntry));
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Removes the specified item from the blocked content storage.<br>
	 * <br>
	 * <b>NOTE</b><br>
	 * Comparison between the argument and the blocked contents is done on the
	 * basis of equality of objects. This only works if the input for this method
	 * is taken directly from the output of getMatchesForPerception() without any
	 * clone() or deepCopy() operations performed on the item in the meantime. 
	 *
	 * @author Zottl Marcus (e0226304),
	 * 04.07.2011, 12:14:10
	 *
	 * @param poRemoveContent - the Item to be removed from the blocked content.
	 */
	public void removeBlockedContent(clsDataStructurePA poRemoveContent) {
		int i = 0;
		for (clsDataStructurePA entry : moBlockedContent) {
			if (entry.equals(poRemoveContent)) {
				moBlockedContent.remove(i);
				break;
			}
			i++;
		}
	}
    
//	/* (non-Javadoc)
//	 *
//	 * @author gelbard
//	 * 30.06.2011, 14:40:39
//	 * 
//	 * This method is used by "F06: defense mechanisms for drives"
//	 * 
//	 */ 
//	/**
//	 * Add DMs to repressed content storage
//	 *
//	 * @since 12.07.2011 16:09:03
//	 *
//	 * @param poDM
//	 */
//	public void add(clsDriveMesh poDM){
//		//Input koennte dann ein Container sein
//		clsPair<clsDataStructurePA, ArrayList<clsAssociation>> oAddDM = new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(poDM, new ArrayList<clsAssociation>());
//		moBlockedContent.add(oAddDM);
//    }
//	
//	/**
//	 * Add TPMs, TI and TPs to the repressed content storage
//	 *
//	 * @since 12.07.2011 16:08:32
//	 *
//	 * @param poDS
//	 */
//	public void add(clsPhysicalRepresentation poDS) {
//		
//		if ((poDS instanceof clsTemplateImage) == false) {
//			clsTemplateImage newTI = new clsTemplateImage(new clsTriple<Integer, eDataType, String>(-1, eDataType.TI, "TI"), new ArrayList<clsAssociation>(), "REPRESSEDDRIVEOBJECT");
//			newTI.assignDataStructure(new clsAssociationTime(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, poDS));
//			
//			clsPair<clsDataStructurePA, ArrayList<clsAssociation>> oAddDS = new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(newTI, new ArrayList<clsAssociation>());
//			moBlockedContent.add(oAddDS);
//		} else {
//			clsPair<clsDataStructurePA, ArrayList<clsAssociation>> oAddDS = new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(poDS, new ArrayList<clsAssociation>());
//			moBlockedContent.add(oAddDS);
//		}
//	}
	
	/**
	 * 
	 * checks whether there is a already a similar entry in blocked content storage.
	 * if there is a similar entry  -> returns true
	 *
	 * @author gelbard
	 * 16.09.2011, 22:47:33
	 *
	 * @param return - existsMatch() returns true if there is a similar entry in blocked content storage (otherwise false)
	 *
	 */
	public boolean existsMatch (clsThingPresentationMesh poDS, clsDriveMeshOLD poDM) {
		
		clsThingPresentationMesh poDSC = buildExtendedTPM (poDS, poDM);
		
		if (getMatchesForPerception(poDSC, 1).isEmpty())
			return false;
		
		return true;
	}
	
	
	
	/* (non-Javadoc)
	 * 
	 * builds a template image from drive object (clsPhysicalRepresentation) and drive aim (clsDriveMesh)
	 *
	 * @author gelbard
	 * 16.09.2011, 22:47:33
	 *
	 */
	private clsThingPresentationMesh buildExtendedTPM (clsThingPresentationMesh poDS, clsDriveMeshOLD poDM) {
		clsThingPresentationMesh oRetVal = null;
		
		//Create container from physical representation
		
		//if ((poDS instanceof clsTemplateImage) == false) {
			//New TI
			//clsTemplateImage newTI = new clsTemplateImage(new clsTriple<Integer, eDataType, String>(-1, eDataType.TI, "TI"), new ArrayList<clsAssociation>(), "REPRESSEDIMAGE");
			//Assign physical representation
			//newTI.assignDataStructure(new clsAssociationTime(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, poDS));
			//Add DM as association
			clsAssociationDriveMesh oAddDM = new clsAssociationDriveMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.DM, eContentType.ASSOCIATIONDM), poDM, poDS);
			//Create ass list
			//ArrayList<clsAssociation> oContainerAssList = new ArrayList<clsAssociation>();
			//oContainerAssList.add(oAddDM);
			//Create container
			
			poDS.getExternalMoAssociatedContent().add(oAddDM);
			
			clsThingPresentationMesh oNewImage = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.TPM, eContentType.RI), new ArrayList<clsAssociation>(), "REPRESSEDIMAGE");
			oNewImage.assignDataStructure(new clsAssociationTime(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONTEMP, eContentType.ASSOCIATIONTEMP), oNewImage, poDS));

			oRetVal = oNewImage;
			
			//clsPrimaryDataStructureContainer poNewBlockedContent = new clsPrimaryDataStructureContainer(newTI, oContainerAssList);
			
			return oRetVal;
		//}
	}
	
	/**
	 * Creates a container (template image) from physical representation and drive mesh
	 * Stores the new template image into the blocked content.
	 *
	 * @author gelbard
	 * 16.09.2011, 22:47:33
	 *
	 */
	public void add(clsThingPresentationMesh poDS, clsDriveMeshOLD poDM) {
		add (buildExtendedTPM (poDS, poDM));
		
	}
	
	/**
	 * Stores a new item into the blocked content.
	 *
	 * @author Zottl Marcus (e0226304),
	 * 19.06.2011, 22:47:33
	 *
	 * @param poNewBlockedContent - the new item to be put into the
	 * blockedContentStorage
	 */
	public void add(clsThingPresentationMesh poEnhancedNewBlockedContent) {
		//clsDataStructurePA newItem = 	newItem = new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(
		//		poNewBlockedContent.getMoDataStructure(),
		//		poNewBlockedContent.getMoAssociatedDataStructures());
		moBlockedContent.add(poEnhancedNewBlockedContent);
	}
	
	/**
	 * Add a list of containers
	 * 
	 * (wendt)
	 *
	 * @since 24.10.2011 09:45:10
	 *
	 * @param poNewBlockedContent
	 */
	public void addAll(ArrayList<clsThingPresentationMesh> poNewBlockedContent) {
		for (clsThingPresentationMesh oImageContainer : poNewBlockedContent) {
			this.add(oImageContainer);
		}
	}
	


	/**
	 *
	 * @author wendt
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.receive._v38.D2_1_receive#receive_D2_1(java.util.ArrayList)
	 */
	/*@Override
	public void receive_D2_1(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}*/
	
	@Override
	public void receive_D2_1(clsThingPresentationMesh poData) {
		//AW: This IF goes to F35
		//Here, an input image is received from F35, where matching is performed
		matchBlockedContentPerception(poData);
		moPerceptionalMesh = poData;
		//moAssociatedMemories = poAssociatedMemories;
	}
	
	/**
	 *
	 * @author wendt
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.send._v38.D2_2_send#send_D2_2(java.util.ArrayList)
	 */
	@Override
	public clsThingPresentationMesh send_D2_2() {
		//AW: This IF goes to F35
		if (moPerceptionalMesh!=null) {
			return moPerceptionalMesh;
			
		} else {
			return null;
		}
		
	}

	
	/**
	 *
	 * @author gelbard
	 * 15.09.2011, 17:15:13
	 * 
	 * @see pa.interfaces.receive._v38.D2_3_receive#receive_D2_3(java.util.ArrayList)
	 * 
	 * Interface from F6
	 * gets drive object and drive aim from F6
	 */
	@Override
	public void receive_D2_3(clsThingPresentationMesh poDS, clsDriveMeshOLD poDM) {
		// store drive object (clsPhysicalRepresentation) and drive aim (clsDriveMesh) in blocked content storage
		add(poDS, poDM);
	}

	/**
	 *
	 * @author wendt
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.send._v38.D2_4_send#send_D2_4(java.util.ArrayList)
	 */
	@Override
	public clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPrimaryDataStructureContainer>> send_D2_4() {
		//TODO FG: Use this IF
		
		
		return null;
	}
	
	@Override
	public void receive_D2_4(clsPrimaryDataStructureContainer poData, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		//TODO FG
	}


	@Override
	public String toString() {
		return moBlockedContent.toString();
	}

	/**
	 *
	 * @author wendt
	 * 21.04.2011, 15:02:51
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToHTML()
	 */
	@Override
	public String stateToTEXT() {
		String text = "";	
		text += toText.h1("Blocked Content Storage");
		text += toText.listToTEXT("moBlockedContent", moBlockedContent);
		return text;
	}

	/**
	 *
	 * @author wendt
	 * 21.04.2011, 15:47:22
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Module {E36} retrieves blocked content from the defense mechanisms. This content tries to be become unblocked again by emerging from {E36} and {E35} into the flow of the functional model. A special storage containing these blocked contents is necessary. The stored data is of type thing presentations with attached quota. Figure \ref{fig:model:functional:repressed_content} shows that the two modules are connected to this special type of storage with a read ({D2.2} and {D2.4}) and a write ({D2.1} and {D2.3}) interface. The two incoming interfaces into module {E36} are {I4.1} and {I4.2}. The first one transports blocked drives in the form of thing presentations plus attached quota of affects. The other one transports blocked thing presentations representing incoming perceptions in the same format. Both incoming information are stored into the memory via interface {D2.3}. Depending on future results of the functions of the module {E36}, drives pushed into this storage try to pass the defense mechanisms. Thus, drives in the form of thing presentations and attached quota of affects are sent via interface {I4.3} back to {E6}. The alternative possibility to reappear for blocked contents is module {E35}. Incoming perceptions in the form of thing presentations (transfered through interface {I2.14}) are compared with stored blocked content. If matching content is found it is attached to the incoming perception. The stored thing presentation plus attached quota of affects can be used as a whole or it can be split and only the thing presentation or the quota of affect are attached. The enriched thing presentation of the perception is forwarded via interface {I2.15} to the next module.";
	}

	/**
	 *
	 * @author deutsch
	 * 21.04.2011, 15:47:22
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D2_1, eInterfaces.D2_3) );
	}

	/**
	 *
	 * @author wendt
	 * 21.04.2011, 15:47:22
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D2_2, eInterfaces.D2_4) );
	}
}
