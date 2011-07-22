/**
 * clsBlockedContentStorage.java: DecisionUnits - pa.storage
 * 
 * @author deutsch
 * 09.03.2011, 17:12:46
 */
package pa._v38.storage;

import java.util.ArrayList;
import java.util.Arrays;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.modules.D2_2_send;
import pa._v38.interfaces.modules.D2_3_receive;
import pa._v38.interfaces.modules.D2_4_receive;
import pa._v38.interfaces.modules.D2_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsAssociationTime;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.informationrepresentation.modules.clsDataStructureComparison;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.03.2011, 17:12:46
 * 
 */
public class clsBlockedContentStorage implements itfInspectorInternalState, itfInterfaceDescription, D2_2_send, D2_4_send, D2_4_receive, D2_3_receive {
	//Blocked content buffer
	private ArrayList<clsPair<clsDataStructurePA, ArrayList<clsAssociation>>> moBlockedContent;
	
	//private ArrayList<clsTripple<clsPrimaryDataStructureContainer, Double, ArrayList<clsAssociationDriveMesh>>> oMatchedContent;
	private clsPrimaryDataStructureContainer moEnvironmentalPerception;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories;
	
	
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
	 * @author Marcus Zottl (e0226304)
	 * 28.06.2011, 20:21:28
	 */
	private int mnActivationLimit = 3;
	
	
	public clsBlockedContentStorage() {
    	// The storage consists of an ArrayList of clsPair, in each pair, the element A is the DataStructure and
    	// the element B contains the AssociatedDataStructures from the PrimaryDataStructureContainer that has been blocked.
    	moBlockedContent = new ArrayList<clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>();
    	fillWithTestData();
    }
	
	/**
	 * Cerates a few DriveMeshes and TemplateImages and puts them in the BlockedContentStorage
	 * to demonstrate the functionality of the matching algorithm.
	 *
	 * @author Marcus Zottl (e0226304),
	 * 05.07.2011, 21:07:02
	 *
	 */
	@SuppressWarnings("unchecked")
	private void fillWithTestData() {
    	ArrayList<ArrayList<Object>> oList = new ArrayList<ArrayList<Object>>();
    	oList.add( new ArrayList<Object>( Arrays.asList("PUNCH", "BITE", 0.0, 0.0, 0.8, 0.2, -0.5) ) );
    	oList.add( new ArrayList<Object>( Arrays.asList("GREEDY", "NOURISH", 0.8, 0.2, 0.0, 0.0, -0.3) ) );
    	oList.add( new ArrayList<Object>( Arrays.asList("DIRTY", "DEPOSIT", 0.0, 0.7, 0.3, 0.0, -0.7) ) );

    	for (ArrayList<Object> oData:oList) {
			clsThingPresentation oTP = clsDataStructureGenerator.generateTP(new clsPair<String, Object>((String)oData.get(0), oData.get(0))); 
			clsDriveMesh oDM = clsDataStructureGenerator.generateDM(new clsTripple<String, ArrayList<clsThingPresentation>, Object>((String)oData.get(1), 
																	   new ArrayList<clsThingPresentation>(Arrays.asList(oTP)),
																	   oData.get(0)));
			oDM.setCategories( (Double)oData.get(2), (Double)oData.get(3), (Double)oData.get(4), (Double)oData.get(5) );
			oDM.setPleasure( (Double)oData.get(6) ); 
	    	moBlockedContent.add(new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(oDM, new ArrayList<clsAssociation>()));    	
    	}
    	
    	// TestData: TemplateImage with a cake exactly like the one in the simulator
    	// create a new TemplateImage
    	clsTemplateImage newTI = new clsTemplateImage(new clsTripple<Integer, eDataType, String>(-1, eDataType.TI, "TI"), new ArrayList<clsAssociation>(), "REPRESSED_IMAGE");
    	// create a cake to put into the TI
    	clsThingPresentationMesh newTPM = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(33, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "CAKE");
    	clsThingPresentation newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(34, eDataType.TP, "TASTE"), "SWEET");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(35, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(36, eDataType.TP, "Color"), "#FFAFAF");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(37, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(38, eDataType.TP, "ShapeType"), "CIRCLE");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(39, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(40, eDataType.TP, "INTENSITY"), "MEDIUM");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(41, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	
    	// add cake to TI 
    	newTI.assignDataStructure(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
    	
    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(340, eDataType.TP, "Alive"), false);
    	clsAssociationAttribute newAssocAttr = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
    	ArrayList<clsAssociation> newAssocDSs = new  ArrayList<clsAssociation>();
    	newAssocDSs.add(newAssocAttr);
    	
    	// add a DriveMesh to the cake to demonstrate how this DM gets added to the cake
    	// in the perception if this blocked content is matched in the perception
    	oList = new ArrayList<ArrayList<Object>>();
    	oList.add( new ArrayList<Object>( Arrays.asList("DIRTY", "DEPOSIT", 0.0, 0.7, 0.3, 0.0, -0.7) ) );

    	for (ArrayList<Object> oData:oList) {
			clsThingPresentation oTP = clsDataStructureGenerator.generateTP(new clsPair<String, Object>((String)oData.get(0), oData.get(0))); 
			clsDriveMesh oDM = clsDataStructureGenerator.generateDM(new clsTripple<String, ArrayList<clsThingPresentation>, Object>((String)oData.get(1), 
																	   new ArrayList<clsThingPresentation>(Arrays.asList(oTP)),
																	   oData.get(0)));
			oDM.setCategories( (Double)oData.get(2), (Double)oData.get(3), (Double)oData.get(4), (Double)oData.get(5) );
			oDM.setPleasure( (Double)oData.get(6) );
			newAssocDSs.add(new clsAssociationDriveMesh(new clsTripple<Integer, eDataType, String>(10400, eDataType.DM, "ASSOCIATIONDM"), oDM, newTPM));
    	}

    	// add TI and associatedDataSructures to blockedContent
    	moBlockedContent.add(new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(newTI, newAssocDSs));
    	
    	// TestData: TemplateImage with a cake that differs from the one in the simulator
    	// create a new TemplateImage
    	newTI = new clsTemplateImage(new clsTripple<Integer, eDataType, String>(-1, eDataType.TI, "TI"), new ArrayList<clsAssociation>(), "REPRESSED_IMAGE");
    	// create a cake to put into the TI
    	newTPM = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(11033, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "CAKE");
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(34, eDataType.TP, "TASTE"), "SWEET");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(11035, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(11036, eDataType.TP, "Color"), "#61210B");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(11037, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(11038, eDataType.TP, "ShapeType"), "SQARE");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(11039, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(40, eDataType.TP, "INTENSITY"), "MEDIUM");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(11041, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	// add cake to TI 
    	newTI.assignDataStructure(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
    	
    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(340, eDataType.TP, "Alive"), false);
    	newAssocAttr = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
    	newAssocDSs = new  ArrayList<clsAssociation>();
    	newAssocDSs.add(newAssocAttr);
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(11350, eDataType.TP, "LOCATION"), "RIGHT");
    	newAssocAttr = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
    	newAssocDSs.add(newAssocAttr);

    	// add TI and associatedDataSructures to blockedContent
    	moBlockedContent.add(new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(newTI, newAssocDSs));
    	
    	// TestData: TemplateImage with cake and a wall that is far away (Location FAR)
    	// create a new TemplateImage
    	newTI = new clsTemplateImage(new clsTripple<Integer, eDataType, String>(-1, eDataType.TI, "TI"), new ArrayList<clsAssociation>(), "REPRESSED_IMAGE");
    	// create a cake to put into the TI
    	newTPM = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(12033, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "CAKE");
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(34, eDataType.TP, "TASTE"), "SWEET");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(12035, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(36, eDataType.TP, "Color"), "#FFAFAF");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(12037, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(38, eDataType.TP, "ShapeType"), "CIRCLE");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(12039, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(40, eDataType.TP, "INTENSITY"), "MEDIUM");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(12041, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	// add cake to TI 
    	newTI.assignDataStructure(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
    	
    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(340, eDataType.TP, "Alive"), false);
    	newAssocAttr = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
    	newAssocDSs = new  ArrayList<clsAssociation>();
    	newAssocDSs.add(newAssocAttr);
    	    	
    	// create a wall to put into the TI
    	newTPM = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(12050, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "WALL");
    	// add wall to TI 
    	newTI.assignDataStructure(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
    	
    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(120350, eDataType.TP, "LOCATION"), "FAR");
    	newAssocAttr = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
    	newAssocDSs.add(newAssocAttr);

    	// add TI and associatedDataSructures to blockedContent
    	moBlockedContent.add(new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(newTI, newAssocDSs));
    	
    	// TestData: TemplateImage with cake and an agent that is far away (location FAR)
    	// create a new TemplateImage
    	newTI = new clsTemplateImage(new clsTripple<Integer, eDataType, String>(-1, eDataType.TI, "TI"), new ArrayList<clsAssociation>(), "REPRESSED_IMAGE");
    	// create a cake to put into the TI
    	newTPM = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(13033, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "CAKE");
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(34, eDataType.TP, "TASTE"), "SWEET");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(13035, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(36, eDataType.TP, "Color"), "#FFAFAF");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(13037, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(38, eDataType.TP, "ShapeType"), "CIRCLE");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(13039, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(40, eDataType.TP, "INTENSITY"), "MEDIUM");
    	newTPM.assignDataStructure(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(13041, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty));
    	// add cake to TI 
    	newTI.assignDataStructure(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
    	
    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(340, eDataType.TP, "Alive"), false);
    	newAssocAttr = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
    	newAssocDSs = new  ArrayList<clsAssociation>();
    	newAssocDSs.add(newAssocAttr);
    	    	
    	// create a agent to put into the TI
    	newTPM = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(13050, eDataType.TPM, "ENTITY"), new ArrayList<clsAssociation>(), "BUBBLE");
    	// FIXME (zottl) add properties to Agent! look at properties of a perceived Agent to find out how to construct one
    	// add agent to TI 
    	newTI.assignDataStructure(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, newTPM));
    	
    	// create additional properties that are AssociatedDataStructures: properties that are not intrinsic to the object, like its location for example
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(130340, eDataType.TP, "Alive"), true);
    	newAssocAttr = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
    	newAssocDSs.add(newAssocAttr);
    	newTPMProperty = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(120350, eDataType.TP, "LOCATION"), "FAR");
    	newAssocAttr = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONATTRIBUTE, "ASSOCIATIONATTRIBUTE"), newTPM, newTPMProperty);
    	newAssocDSs.add(newAssocAttr);

    	// add TI and associatedDataSructures to blockedContent
    	moBlockedContent.add(new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(newTI, newAssocDSs));
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
	public void storeBlockedContent(
			clsPrimaryDataStructureContainer poNewBlockedContent) {
		clsPair<clsDataStructurePA, ArrayList<clsAssociation>> newItem;
		newItem = new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(
				poNewBlockedContent.getMoDataStructure(),
				poNewBlockedContent.getMoAssociatedDataStructures());
		moBlockedContent.add(newItem);
	}
	
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
		moBlockedContent.add(
				new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(
						poNewDS, new ArrayList<clsAssociation>()));		
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
	private void matchBlockedContentPerception(clsPrimaryDataStructureContainer poPerception, ArrayList<clsPrimaryDataStructureContainer> poMemories) {
		ArrayList<clsTripple<clsPrimaryDataStructureContainer, Double, ArrayList<clsAssociationDriveMesh>>> oMatchedContent;
		
		// look up matching content
		oMatchedContent = getMatchesForPerception(poPerception, mrActivationThreshold);
		// now pick the topmost matches and process them accordingly
		int i = 0;
		for (clsTripple<clsPrimaryDataStructureContainer, Double, ArrayList<clsAssociationDriveMesh>> matchedItem : oMatchedContent) {
			i++;
			if (i > mnActivationLimit) break;
			
			//case 1: the item is a TemplateImage
			if (matchedItem.a.getMoDataStructure() instanceof clsTemplateImage) {
				// case 1a: full match (matchValue = 1)
				if (matchedItem.b == 1) {
					// attach all DMs in result to the input TI
					poPerception.getMoAssociatedDataStructures().addAll(matchedItem.c);
				}
				// case 1b: partial match (matchValue < 1)
				else {
					// add complete result to associated memories
					poMemories.add(matchedItem.a);
				}
				// activated content has to be deleted from the blocked content storage
				this.removeBlockedContent(matchedItem.a.getMoDataStructure());
			}
			// case 2: the item is a DriveMesh
			else if (matchedItem.a.getMoDataStructure() instanceof clsDriveMesh) {
				// attach all DMs in result to the input TI
				poPerception.getMoAssociatedDataStructures().addAll(matchedItem.c);
				// activated content has to be deleted from the blocked content storage
				this.removeBlockedContent(matchedItem.a.getMoDataStructure());
			}
		}
	}
	
	// (FG) This method was copied from AW's method: public clsDriveMesh getBestMatchCONVERTED(clsPrimaryDataStructureContainer poInput, boolean boRemoveAfterActivate)	
	/*
	 * finds best match in list of clsDriveMeshes of repressed content
	 * Function overloading
	 */
	public clsDriveMesh matchBlockedContentDrives(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poInput) {
		// get only free drives
		
		clsDriveMesh oRetVal = null;
		double rHighestMatch = 0.0;

		for(clsPair<clsPhysicalRepresentation, clsDriveMesh> oDrivePair : poInput){ 
			clsDriveMesh oInputDrive = oDrivePair.b;
			clsPair<Double, clsDriveMesh> oMatch = null;
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
	private ArrayList<clsTripple<clsPrimaryDataStructureContainer, Double, ArrayList<clsAssociationDriveMesh>>> getMatchesForPerception(
			clsPrimaryDataStructureContainer poPerception,
			double poThreshold) {
		
		ArrayList<clsTripple<clsPrimaryDataStructureContainer, Double, ArrayList<clsAssociationDriveMesh>>> oMatchValues = 
			new ArrayList<clsTripple<clsPrimaryDataStructureContainer, Double, ArrayList<clsAssociationDriveMesh>>>();
		clsPrimaryDataStructureContainer oBlockedCont;

		// compare each element from moBlockedContent with the input
		for (clsPair<clsDataStructurePA, ArrayList<clsAssociation>> oEntry : moBlockedContent) {
			if (oEntry.a instanceof clsTemplateImage) {
				// if item is a TI, then calculate match with input TI
				oBlockedCont = new clsPrimaryDataStructureContainer(oEntry.a, oEntry.b);
				//clsPair<Double, ArrayList<clsAssociationDriveMesh>> oMatchResult = calculateTIMatch(oBlockedCont, poPerception);
				//Use structures from data structure comparison
				clsPair<Double, ArrayList<clsAssociationDriveMesh>> oMatchResult = clsDataStructureComparison.compareTIContainerInclDM(oBlockedCont, poPerception);
				// ignore matches below threshold
				if (oMatchResult.a < poThreshold)
					continue;
				// ensure that the list of results is sorted by the matchValues, with the highest matchValues on top of the list.
				int i = 0;
				while ((i + 1 < oMatchValues.size()) && oMatchResult.a < oMatchValues.get(i).b) {
					i++;
				}
				// add to results
				oMatchValues.add(i, 
						new clsTripple<clsPrimaryDataStructureContainer, Double, ArrayList<clsAssociationDriveMesh>>(
								oBlockedCont, oMatchResult.a, oMatchResult.b));
			}
			else if (oEntry.a instanceof clsDriveMesh) {
				// if item is a DM, then compare with all associated DMs of the input
				for(clsAssociation oInputAssociation : poPerception.getMoAssociatedDataStructures()) {
					if(oInputAssociation instanceof clsAssociationDriveMesh){
						clsDriveMesh oData = ((clsAssociationDriveMesh)oInputAssociation).getDM(); 
						if(oEntry.a.getMoContentType().equals(oData.getMoContentType())) {
							// calculate match between drive matches
							double rMatchValue = ((clsDriveMesh)oEntry.a).matchCathegories(oData);
							// ignore matches below threshold
							if (rMatchValue < poThreshold)
								continue;

							// add the association with the matching element from the input to the return values
							ArrayList<clsAssociationDriveMesh> newDMAssociations = new ArrayList<clsAssociationDriveMesh>();
							clsPrimaryDataStructure newRoot = (clsPrimaryDataStructure) ((clsAssociationDriveMesh)oInputAssociation).getRootElement();
							newDMAssociations.add( 
								new clsAssociationDriveMesh(
										new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONDM, "ASSOCIATIONDM"),
										(clsDriveMesh)oEntry.a,
										newRoot));
							oBlockedCont = new clsPrimaryDataStructureContainer(oEntry.a, oEntry.b);
							// ensure that the list of results is sorted by the matchValues, with the highest matchValues on top of the list.
							int i = 0;
							while ((i + 1 < oMatchValues.size()) && rMatchValue < oMatchValues.get(i).b) {
								i++;
							}
							// add to results
							oMatchValues.add(i, 
									new clsTripple<clsPrimaryDataStructureContainer, Double, ArrayList<clsAssociationDriveMesh>>(
											oBlockedCont, rMatchValue, newDMAssociations));
						}
					}
				}
			}
			// no other types of data have to be considered yet
		}
		return oMatchValues;
	}
	
	private ArrayList<clsPair<Double, clsDriveMesh>> getMatchesForDrives(clsDriveMesh poDM, double poThreshold) {
		ArrayList<clsPair<Double, clsDriveMesh>> oRetVal = new ArrayList<clsPair<Double, clsDriveMesh>>();	
		
		for (clsPair<clsDataStructurePA, ArrayList<clsAssociation>> oEntry : moBlockedContent) {
			if (oEntry.a instanceof clsDriveMesh) { 
				if(oEntry.a.getMoContentType().equals(poDM.getMoContentType())) {
					// calculate match between drive matches
					double rMatchValue = ((clsDriveMesh)oEntry.a).matchCathegories(poDM);
					// ignore matches below threshold
					if (rMatchValue < poThreshold)
						continue;
					
					int i = 0;
					while ((i + 1 < oRetVal.size()) && rMatchValue < oRetVal.get(i).a) {
						i++;
					}
					// add to results
					oRetVal.add(i, 
							new clsPair<Double, clsDriveMesh>(rMatchValue, (clsDriveMesh)oEntry.a));
				}
			}
		}
		
		return oRetVal;
	}
	
	//TODO AW: Remove as function is confirmed, moved to clsDataStructureComparison
	/**
	 * Calculates the match between two containers containing TemplateImages.<br>
	 * <br>
	 * Returns the quality of the match and a list that contains associations
	 * between any DMs in the blockedContent and the matching items in the
	 * perceivedContent. The second part of the result is done here because it is
	 * way more efficient to create those associations "on the fly" while
	 * comparing the items than to later extract the DMs from a matching content
	 * and find their correct "targets" in the perception - which would
	 * essentially require a second match algorithm.<br>
	 * <br>
	 * - Quality of the match is the sum of the quality of the matches of the items
	 * in the (blocked) TI divided by the total number of items.<br>
	 * - Quality of the match of ThingPresentationMeshes is determined by comparing
	 * the moContent (20%), intrinsic properties (moAssociatedData of the items)
	 * (40%) and extrinsic properties (moAssociatedDataStructures in the
	 * container) (40%).<br>
	 * - Quality of the match of ThingPresentations is either 1 or 0.
	 *
	 * @author Zottl Marcus (e0226304),
	 * 22.06.2011, 20:08:11
	 *
	 * @param poBlockedContent		- the item from the blockedContentStorage for
	 * which the match quality should be calculated.
	 * @param poPerceivedContent	- the perception to compare the
	 * <i>blockedContent</i> to
	 * @return									- a clsPair with A the quality of the match 
	 * (double) and B = a list of Associations between DMs in <i>blockedContent</i>
	 * and their matching "partners" in the perception 
	 * (ArrayList&lt;clsAssociationDriveMesh&gt;)
	 * 
	 * @see clsBlockedContentStorage#getAssocAttributeMatch(ArrayList, ArrayList, double)
	 * @see clsBlockedContentStorage#createNewDMAssociations(clsPrimaryDataStructure, ArrayList)
	 */
	/*private clsPair<Double, ArrayList<clsAssociationDriveMesh>> calculateTIMatch(
			clsPrimaryDataStructureContainer poBlockedContent,
			clsPrimaryDataStructureContainer poPerceivedContent) {

		clsTemplateImage oBlockedTI = (clsTemplateImage) poBlockedContent.getMoDataStructure();
		clsTemplateImage oPerceivedTI = (clsTemplateImage) poPerceivedContent.getMoDataStructure();
		double oMatchValueTI = 0.0;
		int oElemCountTI = oBlockedTI.getMoAssociatedContent().size();
		double oMatchSumElements = 0.0;
		ArrayList<clsAssociationDriveMesh> oNewDriveMeshAssociations = new ArrayList<clsAssociationDriveMesh>();
		
		// for each element of the blockedContent, find the !best! matching element
		// in the perceivedContent
		for (clsAssociation blockedTIContent : oBlockedTI.getMoAssociatedContent()) {
			double bestMatchValue = 0.0;
			ArrayList<clsAssociationDriveMesh> bestMatchDriveMeshAssociations = new ArrayList<clsAssociationDriveMesh>();
			for (clsAssociation perceivedTIContent : oPerceivedTI.getMoAssociatedContent()) {
				if ((blockedTIContent.getLeafElement() instanceof clsThingPresentationMesh) &&
						(perceivedTIContent.getLeafElement() instanceof clsThingPresentationMesh)) {
					clsThingPresentationMesh blockedTPM = (clsThingPresentationMesh)blockedTIContent.getLeafElement();
					clsThingPresentationMesh perceivedTPM = (clsThingPresentationMesh)perceivedTIContent.getLeafElement();
					if (perceivedTPM.getMoContentType() == blockedTPM.getMoContentType()) {
						double matchValContent = 0.0;
						// first see if the contents match
						if (perceivedTPM.getMoContent() == blockedTPM.getMoContent()) {
							matchValContent = 1.0;
						}
						// now check how well the intrinsic properties match
						double matchValIntrinsic = 
							getAssocAttributeMatch(blockedTPM.getMoAssociatedContent(), perceivedTPM.getMoAssociatedContent(), matchValContent);
						// only check the extrinsic properties if the content matches!
						double matchValExtrinsic = 0.0;
						if (matchValContent == 1.0){
							matchValExtrinsic = 
								getAssocAttributeMatch(
										poBlockedContent.getMoAssociatedDataStructures(blockedTPM), 
										poPerceivedContent.getMoAssociatedDataStructures(perceivedTPM), matchValContent);	
						}
						// combine values to calculate overall match of a TPM. Weights are arbitrary!
						double matchValCombined = ((0.2 * matchValContent) + (0.4 * matchValIntrinsic) + (0.4 * matchValExtrinsic));
						// store best element match so far, because we need to find the highest matching element
						if (matchValCombined > bestMatchValue) {
							bestMatchValue = matchValCombined;
							/* In case the overall matchValueTI is 1 we need to associate all
							 * DMs from the blockedContent with the perceivedContent. Since
							 * the DMs are not associated with the whole TI but with one of 
							 * its elements, we need to create new associations between the 
							 * DMs and the matching elements in the perceivedContent. If we do
							 * not want to find the correct new roots those DMs in an
							 * additional matching algorithm, we have to acquire them now! 
							 */
							/*bestMatchDriveMeshAssociations = 
								createNewDMAssociations(perceivedTPM, 
										poBlockedContent.getMoAssociatedDataStructures(blockedTPM));
						}
					}
				}
				else if ((blockedTIContent.getLeafElement() instanceof clsThingPresentation) &&
						(perceivedTIContent.getLeafElement() instanceof clsThingPresentation)) {
					clsThingPresentation blockedTP = (clsThingPresentation)blockedTIContent.getLeafElement();
					clsThingPresentation perceivedTP = (clsThingPresentation)perceivedTIContent.getLeafElement();

					if((perceivedTP.getMoContentType() == blockedTP.getMoContentType()) &&
							(perceivedTP.getMoContent() == blockedTP.getMoContent())) {
						// we have found a match! No need to check anything else since TPs don't have any associatedContents
						bestMatchValue = 1;
						break; // since a TP match is simply 1 or 0 we just take the first match we find
					}
				}
				else if ((blockedTIContent.getLeafElement() instanceof clsTemplateImage) &&
						(perceivedTIContent.getLeafElement() instanceof clsTemplateImage)) {
					//TODO matching of TIs nested inside a TI if this ever becomes necessary
				}
			}
			// best matchValue is added to the sum of matchValues and related
			// associations are added to the result
			oMatchSumElements += bestMatchValue;
			oNewDriveMeshAssociations.addAll(bestMatchDriveMeshAssociations);
		}
		// matchValue of two TIs is the average matchValue of their contents/elements or zero if the TI is empty (should not happen)
		if (oElemCountTI != 0) {
			oMatchValueTI = oMatchSumElements / oElemCountTI;
		}
		else {
			oMatchValueTI = 0;
		}
		return new clsPair<Double, ArrayList<clsAssociationDriveMesh>>(oMatchValueTI, oNewDriveMeshAssociations);
	}*/

	//TODO AW: Remove as function is confirmed, moved to clsDataStructureComparison
	/**
	 * Creates new AssociationDriveMeshes from the inputs.<br>
	 * <br>
	 * A list of new AssociationDriveMeshes with the argument newRoot as
	 * rootElement and the DMs found in oldAssociations as leafElements. 
	 *
	 * @author Zottl Marcus (e0226304),
	 * 28.06.2011, 20:08:58
	 *
	 * @param poNewRoot					- the root element for the new associations
	 * @param poOldAssociations -	the old associations from which the DMs are taken
	 * @return								- a list of new associations between newRoot and the
	 * DMs found in oldAssociations
	 */
	/*private ArrayList<clsAssociationDriveMesh> createNewDMAssociations(
			clsPrimaryDataStructure poNewRoot,
			ArrayList<clsAssociation> poOldAssociations) {
		ArrayList<clsAssociationDriveMesh> oReturnlist = new ArrayList<clsAssociationDriveMesh>();
		
		for (clsAssociation entry : poOldAssociations) {
			if (entry instanceof clsAssociationDriveMesh) {
				clsAssociationDriveMesh oldAssDM = (clsAssociationDriveMesh)entry;
				clsAssociationDriveMesh newAssDM = 
					new clsAssociationDriveMesh(
							new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONDM, "ASSOCIATIONDM"),
							oldAssDM.getDM(),
							poNewRoot);
				oReturnlist.add(newAssDM);
			}
		}
		return oReturnlist;
	}*/

	/**
	 * Calculates the match between two lists of properties (of two items).<br>
	 * <br>
	 * The quality of the match is the number of matching properties divided by
	 * the total number of properties.<br>
	 * If there are no blocked associations, then the result depends on whether
	 * the roots of the associations match.<br> 
	 * Example: a cake with no specified attributes matches any other cake,
	 * therefore the result is a full match, but a stone without properties does
	 * NOT match any cake, therefore the match is zero.
	 *
	 * @author Zottl Marcus (e0226304),
	 * 22.06.2011, 23:41:36
	 *
	 * @param poBlockedAssocs		- list of associated properties of the blocked
	 * content item.
	 * @param poPerceivedAssocs	- list of associated properties of the perceived
	 * content item.
	 * @param poRootMatch				- a number indicating whether the root elements of
	 * the associations are considered a match (1.0) or not (0.0).
	 * @return									- the quality of the match between the to lists of
	 * properties.
	 */
	/*private double getAssocAttributeMatch(
			ArrayList<clsAssociation> poBlockedAssocs,
			ArrayList<clsAssociation> poPerceivedAssocs,
			double poRootMatch) {
		double oMatchFactor;
		int oAssocCount = 0;
		int oAssocMatches = 0;
		for(clsAssociation blockedAssocEntry : poBlockedAssocs) {
			if(blockedAssocEntry.getLeafElement() instanceof clsThingPresentation) {
			oAssocCount++; // we only count associated TPs, not DMs!
			for(clsAssociation perceivedAssocEntry : poPerceivedAssocs) {
					if(perceivedAssocEntry.getLeafElement() instanceof clsThingPresentation) {
						clsThingPresentation blockedTP = (clsThingPresentation)blockedAssocEntry.getLeafElement();
						clsThingPresentation perceivedTP = (clsThingPresentation)perceivedAssocEntry.getLeafElement();

						if((perceivedTP.getMoContentType() == blockedTP.getMoContentType()) &&
								(perceivedTP.getMoContent().toString() == blockedTP.getMoContent().toString())) {
							oAssocMatches++; // we have found a match!
							break; // leave inner loop, because there can't be more than one match: that would lead to matchFactor greater 1.
						}
					}
				}
				// matching ONLY calculated for TPs for now, DMs are simply ignored.
			}
		}
		// (Association)matchFactor of two DataStructures is the number of matching Associations divided by the total number of
		// Associations of the blocked DataStructure
		if(oAssocCount > 0)
			oMatchFactor = ((double) oAssocMatches) / oAssocCount;
		else
			/* if there are no (blocked) associations, then the result depends on whether the roots of the associations match. 
			 * Example: a cake with no specified attributes matches any other cake, therefore the result is a full match,
			 * but a stone without properties does NOT match any cake, therefore the match is zero.
			 *  
			 */
			/*oMatchFactor = poRootMatch;
		return oMatchFactor;
	}*/
	
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
		for (clsPair<clsDataStructurePA, ArrayList<clsAssociation>> entry : moBlockedContent) {
			if (entry.a.equals(poRemoveContent)) {
				moBlockedContent.remove(i);
				break;
			}
			i++;
		}
	}
    
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 30.06.2011, 14:40:39
	 * 
	 * This method is used by "F06: defense mechanisms for drives"
	 * 
	 */ 
	/**
	 * Add DMs to repressed content storage
	 *
	 * @since 12.07.2011 16:09:03
	 *
	 * @param poDM
	 */
	public void add(clsDriveMesh poDM){
		//Input könnte dann ein Container sein
		clsPair<clsDataStructurePA, ArrayList<clsAssociation>> oAddDM = new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(poDM, new ArrayList<clsAssociation>());
		moBlockedContent.add(oAddDM);
    }
	
	/**
	 * Add TPMs, TI and TPs to the repressed content storage
	 *
	 * @since 12.07.2011 16:08:32
	 *
	 * @param poDS
	 */
	public void add(clsPhysicalRepresentation poDS) {
		
		if ((poDS instanceof clsTemplateImage) == false) {
			clsTemplateImage newTI = new clsTemplateImage(new clsTripple<Integer, eDataType, String>(-1, eDataType.TI, "TI"), new ArrayList<clsAssociation>(), "REPRESSEDDRIVEOBJECT");
			newTI.assignDataStructure(new clsAssociationTime(new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "ASSOCIATIONTEMP"), newTI, poDS));
			
			clsPair<clsDataStructurePA, ArrayList<clsAssociation>> oAddDS = new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(newTI, new ArrayList<clsAssociation>());
			moBlockedContent.add(oAddDS);
		} else {
			clsPair<clsDataStructurePA, ArrayList<clsAssociation>> oAddDS = new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(poDS, new ArrayList<clsAssociation>());
			moBlockedContent.add(oAddDS);
		}
	}
	


	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.receive._v38.D2_3_receive#receive_D2_3(java.util.ArrayList)
	 */
	@Override
	public void receive_D2_3(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.receive._v38.D2_1_receive#receive_D2_1(java.util.ArrayList)
	 */
	/*@Override
	public void receive_D2_1(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}*/

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.send._v38.D2_4_send#send_D2_4(java.util.ArrayList)
	 */
	@Override
	public clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPrimaryDataStructureContainer>> send_D2_4() {
		//AW: This IF goes to F35
		return new clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPrimaryDataStructureContainer>>(moEnvironmentalPerception, moAssociatedMemories);
		
	}
	
	@Override
	public void receive_D2_4(clsPrimaryDataStructureContainer poData, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		//AW: This IF goes to F35
		//Here, an input image is received from F35, where matching is performed
		matchBlockedContentPerception(poData, poAssociatedMemories);
		moEnvironmentalPerception = poData;
		moAssociatedMemories = poAssociatedMemories;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.send._v38.D2_2_send#send_D2_2(java.util.ArrayList)
	 */
	@Override
	public void send_D2_2(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub
		
		
	}

	@Override
	public String toString() {
		return moBlockedContent.toString();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 15:47:22
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Module {E36} retrieves blocked content from the defense mechanisms. This content tries to be become unblocked again by emerging from {E36} and {E35} into the flow of the functional model. A special storage containing these blocked contents is necessary. The stored data is of type thing presentations with attached quota. Figure \ref{fig:model:functional:repressed_content} shows that the two modules are connected to this special type of storage with a read ({D2.2} and {D2.4}) and a write ({D2.1} and {D2.3}) interface. The two incoming interfaces into module {E36} are {I4.1} and {I4.2}. The first one transports blocked drives in the form of thing presentations plus attached quota of affects. The other one transports blocked thing presentations representing incoming perceptions in the same format. Both incoming information are stored into the memory via interface {D2.3}. Depending on future results of the functions of the module {E36}, drives pushed into this storage try to pass the defense mechanisms. Thus, drives in the form of thing presentations and attached quota of affects are sent via interface {I4.3} back to {E6}. The alternative possibility to reappear for blocked contents is module {E35}. Incoming perceptions in the form of thing presentations (transfered through interface {I2.14}) are compared with stored blocked content. If matching content is found it is attached to the incoming perception. The stored thing presentation plus attached quota of affects can be used as a whole or it can be split and only the thing presentation or the quota of affect are attached. The enriched thing presentation of the perception is forwarded via interface {I2.15} to the next module.";
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 15:47:22
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D2_2, eInterfaces.D2_4) );
	}
}
