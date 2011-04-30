/**
 * clsSemanticInformationIspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author muchitsch
 * 24.10.2009, 23:58:33
 */
package inspectors.mind.pa._v30.graph;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import com.jgraph.components.labels.RichTextValue;

import du.itf.actions.clsActionCommand;
import du.itf.sensors.clsSensorExtern;
import du.itf.sensors.clsSensorIntern;

import pa._v30.symbolization.representationsymbol.clsSymbolVision;
import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.datatypes.clsAct;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsThingPresentation;
import pa._v30.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v30.memorymgmt.datatypes.clsWordPresentation;


/**
 * The one and only Inspector for Heimos Memory. Can display all memory Infoemation aka eDataType
 * just map the inspector tab with this inspector here: clsInspectorMappingPA
 * and fill it with a hash map containing the information pairs 
 * 
 * @author muchitsch
 * 24.10.2009, 23:58:33
 * 
 */
public abstract class clsMeshBase extends clsGraphBase {
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 21.04.2011, 12:16:33
	 */
	private static final long serialVersionUID = -6638061388429348390L;
	
	protected ArrayList<Object> moMesh;
	private String moRootNodeName = "root";
	
	/**
     * Constructor of the class. Creates the panel, buttons etc. 
     * 
     * @author muchitsch
     * 04.08.2010, 16:56:58
     *
     * @param originalInspector
     * @param wrapper
     * @param guiState
     * @param poModuleContainer eg moPA.moG02Id.moG06AffectGeneration.moE05GenerationOfAffectsForDrives
     * @param poModuleMemoryMemberName 
     */
    public clsMeshBase() {
    	super();
     }
    	
    protected void setRootNodeName(String poRootNodeName){
    	moRootNodeName = poRootNodeName;
    }
    
	@Override
	protected DefaultGraphCell createGraph() {
		//helper array-list to collect each created cell in the right order for the registration later on
		//without knowing the total number of elements
		//ArrayList<DefaultGraphCell> oCellList = new ArrayList<DefaultGraphCell>();
		//create root node (it's a mesh-list) and add it to the registration list
		DefaultGraphCell oParent = createDefaultGraphVertex(moRootNodeName, 20, 20, 60, 40, moColorRoot);
		//get graph-cells for each object in the of the mesh
		readInspectorDataAndGenerateGraphCells(oParent);
		
		return oParent;
	}
	
	/**
	 * Main entrance point for the JGraph cell genration. Calls the other specialized methods for generating cells.
	 * Recursion starts here!
	 *
	 * @author muchitsch
	 * 30.08.2010, 17:54:57
	 *
	 * @param poParent
	 */
	private void readInspectorDataAndGenerateGraphCells(DefaultGraphCell poParent) 
	{
		//check for the main list types possible
		for(int i=0; i<moMesh.size(); i++){
			Object oO = moMesh.get(i);
		
			generateGraphCell(poParent, oO);
		}
	}
	
	/** 
	 * check for the main data types possible
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParent, Object oO) {
		
		DefaultGraphCell oRootCell = null;
		
		if (oO instanceof List) {
			@SuppressWarnings("rawtypes")
			List oL = (List)oO;
			
			oRootCell = generateGraphCell(poParent, oL);
			
		} else if (oO instanceof Map) {
			@SuppressWarnings("rawtypes")
			Map t = (Map)oO;
			
			@SuppressWarnings("rawtypes")
			Iterator oI = t.keySet().iterator();
			
			while (oI.hasNext()) {
				Object oKey = oI.next();
				Object oValue = t.get(oKey);
				generateGraphCell(poParent, oValue);
			}

		} else if (oO instanceof clsDataStructurePA) {
			clsDataStructurePA oNextMemoryObject = (clsDataStructurePA)oO;
			oRootCell = generateGraphCell(poParent, oNextMemoryObject);
			
		} else if (oO instanceof clsPair) {
			@SuppressWarnings("rawtypes")
			clsPair oNextMemoryObject = (clsPair)oO;
			oRootCell = generateGraphCell(poParent, oNextMemoryObject);
			
		} else if (oO instanceof clsTripple) {
			@SuppressWarnings("rawtypes")
			clsTripple oNextMemoryObject = (clsTripple)oO;
			oRootCell = generateGraphCell(poParent, oNextMemoryObject);
			
		} else if (oO instanceof clsDataStructureContainer)	{
			clsDataStructureContainer oNextMemoryObject = (clsDataStructureContainer)oO;
			oRootCell = generateGraphCell(poParent, oNextMemoryObject);
			
		} else if (oO == null) {
			oRootCell = generateNULLGraphCell(poParent);
			
		} else if (oO instanceof Double) {
			oRootCell = generateGraphCell(poParent, (Double)oO);
			
		} else if (oO instanceof Integer) {
			oRootCell = generateGraphCell(poParent, oO.toString()); //mit mir nicht, Integer gibts keine!
			
		} else if (oO instanceof clsSymbolVision) {
			oRootCell = generateGraphCell(poParent, oO.toString()); //TODO MUCHITSCH generate specialized functions to display this datatype
			
		} else if (oO instanceof clsSensorIntern) {
			oRootCell = generateGraphCell(poParent, oO.toString()); //TODO MUCHITSCH generate specialized functions to display this datatype
			
		} else if (oO instanceof clsSensorExtern) {
			oRootCell = generateGraphCell(poParent, oO.toString()); //TODO MUCHITSCH generate specialized functions to display this datatype

		} else if (oO instanceof clsActionCommand) {
			oRootCell = generateGraphCell(poParent, oO.toString()); //TODO MUCHITSCH generate specialized functions to display this datatype

		} else {
			oRootCell = generateGraphCell(poParent, oO.toString());
			System.out.println("[clsMeshBase.rIDAGGC] Unkown data structure, displaying toString(): "+oO.getClass());
		}
		
		return oRootCell;
	}


	/** [MAIN]...
	 * Generating cells from clsDataStructurePA
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsDataStructurePA poMemoryObject)
	{
		DefaultGraphCell oRootCell = null;
		
		//check for the  main data types possible for clsDataStructurePA
		if(poMemoryObject instanceof clsDriveMesh)
		{
			clsDriveMesh tmpRootMemoryObject = (clsDriveMesh)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		}
		else if(poMemoryObject instanceof clsThingPresentation)
		{
			clsThingPresentation tmpRootMemoryObject = (clsThingPresentation)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		}
		else if(poMemoryObject instanceof clsWordPresentation)
		{
			clsWordPresentation tmpRootMemoryObject = (clsWordPresentation)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		}
		else if(poMemoryObject instanceof clsDriveDemand)
		{
			clsDriveDemand tmpRootMemoryObject = (clsDriveDemand)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		}
		else if(poMemoryObject instanceof clsAct)
		{
			clsAct tmpRootMemoryObject = (clsAct)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		}
		else if(poMemoryObject instanceof clsThingPresentationMesh)
		{
			clsThingPresentationMesh tmpRootMemoryObject = (clsThingPresentationMesh)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		}
		else
		{
			System.out.println("ARS Exeption: DefaultGraphCell(clsDataStructurePA) is NULL, object type not found? class:" + poMemoryObject.getClass());
			//throw new NullPointerException("ARS Exeption: DefaultGraphCell is NULL, object type clsDataStructurePA not found?");
		}
		
		if(oRootCell == null)
		{
			System.out.println("ARS Exeption: DefaultGraphCell is NULL, object type not found?");
			throw new NullPointerException("ARS Exeption: DefaultGraphCell is NULL, object type clsDataStructurePA not found?");
		}
		
		//get edge to parent cell [parent cell] <-> [rootCell]
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oRootCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oRootCell;
	}
	
	/** [PAIR]
	 * Generating cells from clsPair
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsPair<?,?> poMemoryObject) {
		
		//create root of the pair
		DefaultGraphCell oPairCellRoot = createDefaultGraphVertex("PAIR", moColorPairRoot);
		moCellList.add(oPairCellRoot);
		
		//edge to the [parent cell] <-> [root of pair]
		DefaultEdge oEdgeToParent = new DefaultEdge();
		oEdgeToParent.setSource(poParentCell.getChildAt(0));
		oEdgeToParent.setTarget(oPairCellRoot.getChildAt(0));
		moCellList.add(oEdgeToParent);
		
		//generate root of the pair for [A] and [B]
		DefaultGraphCell oPairCellA = generateGraphCell(oPairCellRoot, poMemoryObject.a);
		DefaultGraphCell oPairCellB = generateGraphCell(oPairCellRoot, poMemoryObject.b);
		this.moCellList.add(oPairCellA);
		this.moCellList.add(oPairCellB);
		
		//edge [A] <-> [root of pair]
		DefaultEdge oEdgeA = new DefaultEdge("pair A");
		oEdgeA.setSource(oPairCellRoot.getChildAt(0));
		oEdgeA.setTarget(oPairCellA.getChildAt(0));
		moCellList.add(oEdgeA);
		
		//edge [A] <-> [root of pair]
		DefaultEdge oEdgeB = new DefaultEdge("pair B");
		oEdgeB.setSource(oPairCellRoot.getChildAt(0));
		oEdgeB.setTarget(oPairCellB.getChildAt(0));
		moCellList.add(oEdgeB);
		
		return oPairCellRoot;
	}
	
	/** [PAIR]
	 * Generating cells from clsPair
	 */
	
	/** [DC]
	 * Generating cells from clsDataStructureContainer
	 * splits in PDC or SDC
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsDataStructureContainer poMemoryObject)
	{
		
		DefaultGraphCell oRootCell = null;
		
		//check what special type of DataContainer we have
		if(poMemoryObject instanceof clsPrimaryDataStructureContainer)
		{
			clsPrimaryDataStructureContainer tmpRootMemoryObject = (clsPrimaryDataStructureContainer)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		}
		else if(poMemoryObject instanceof clsSecondaryDataStructureContainer)
		{
			clsSecondaryDataStructureContainer tmpRootMemoryObject = (clsSecondaryDataStructureContainer)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		}
		else
		{
			System.out.println("ARS Exeption: DefaultGraphCell is NULL, object type clsPair not found?");
			throw new NullPointerException("ARS Exeption: DefaultGraphCell is NULL, object type clsPair not found?");
		}
		
		return oRootCell;
		
	}
	
	/** [PDC]
	 * Generating cells from clsPrimaryDataStructureContainer
	 * [DataStructurePA] - <acossiations>
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsPrimaryDataStructureContainer poMemoryObject)
	{
		clsDataStructurePA oContainerRootDataStructure = poMemoryObject.getMoDataStructure();
		ArrayList<clsAssociation> oAssociatedDataStructures =  poMemoryObject.getMoAssociatedDataStructures();
		
		//create container root cell

//NEW VERSION		
//		DefaultGraphCell oContainerRootCell = generateGraphCell(poParentCell, oContainerRootDataStructure);
		
//OLD VERSION
		String oDescription = oContainerRootDataStructure.toString().substring(0, 5);

		if(!UseSimpleView()) 
		{
			oDescription = 	oContainerRootDataStructure.toString();
		}
		
		DefaultGraphCell oContainerRootCell = createDefaultGraphVertex(oDescription, moColorPrimaryDataStructureContainer );
		this.moCellList.add(oContainerRootCell);
//		edge to the [parrent cell] <-> [container root cell]
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oContainerRootCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		//create assosiations
		for(clsAssociation oContainerAssociations : oAssociatedDataStructures)
		{
			if(oContainerRootDataStructure.getMoDS_ID() == oContainerAssociations.getMoAssociationElementA().getMoDS_ID())
			{
				clsDataStructurePA oMemoryObjectB = oContainerAssociations.getMoAssociationElementB();
				DefaultGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectB);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("ContAss w:" + oContainerAssociations.getMrWeight());
				oEdge.setSource(oContainerRootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else if(oContainerRootDataStructure.getMoDS_ID() == oContainerAssociations.getMoAssociationElementA().getMoDS_ID())
			{
				clsDataStructurePA oMemoryObjectA = oContainerAssociations.getMoAssociationElementA();
				DefaultGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectA);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("ContAss w:" + oContainerAssociations.getMrWeight());
				oEdge.setSource(oContainerRootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else
			{ 
				System.out.println("[clsMeshBase.generateGraphCell] [PDC] Neither A nor B are root element. using element:" + oContainerAssociations.getMoAssociationElementB().getMoDS_ID());
				
				clsDataStructurePA oMemoryObjectB = oContainerAssociations.getMoAssociationElementB();
				DefaultGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectB);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("ContAss w:" + oContainerAssociations.getMrWeight());
				oEdge.setSource(oContainerRootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			
		}
		return oContainerRootCell;
	}
	
	/** [SDC]
	 * Generating cells from clsSecondaryDataStructureContainer
	 * [DataStructurePA] - <acossiations>
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsSecondaryDataStructureContainer poMemoryObject)
	{
		
		
		clsDataStructurePA oContainerRootDataStructure = poMemoryObject.getMoDataStructure();
		//TODO WP werden neu erzeugt und können daher nicht zu den associations gefunden werden weil ID = -1, vielleicht kommt da mal eine änderung! CM+HZ
		ArrayList<clsAssociation> oAssociatedDataStructures = new ArrayList<clsAssociation>(); // poMemoryObject.moAssociatedDataStructures;
		
//NEW VERSION		
//		DefaultGraphCell oContainerRootCell = generateGraphCell(poParentCell, oContainerRootDataStructure);
		
//OLD VERSION		
		String oDescription = oContainerRootDataStructure.toString().substring(0, 5);

		if(!UseSimpleView()) 
		{
			oDescription = 	oContainerRootDataStructure.toString();
		}
		
		//create container root struct
		DefaultGraphCell oContainerRootCell = createDefaultGraphVertex(oDescription, moColorSecondaryDataStructureContainer );
		this.moCellList.add(oContainerRootCell);
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oContainerRootCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		//create assosiations (siehe weiter oben, wird nicht durchlaufen! da immer leer im SecContainer)
		for(clsAssociation oContainerAssociations : oAssociatedDataStructures)
		{
			if(oContainerRootDataStructure.getMoDS_ID() == oContainerAssociations.getMoAssociationElementA().getMoDS_ID())
			{
				clsDataStructurePA oMemoryObjectB = oContainerAssociations.getMoAssociationElementB();
				DefaultGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectB);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("ContAss w:" + oContainerAssociations.getMrWeight());
				oEdge.setSource(oContainerRootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else if(oContainerRootDataStructure.getMoDS_ID() == oContainerAssociations.getMoAssociationElementB().getMoDS_ID())
			{
				clsDataStructurePA oMemoryObjectA = oContainerAssociations.getMoAssociationElementA();
				DefaultGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectA);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("ContAss w:" + oContainerAssociations.getMrWeight());
				oEdge.setSource(oContainerRootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else
			{ //should not be laut heimo!!!
				System.out.println("[clsMeshBase.generateGraphCell] [SDC] Neither A nor B are root element. using B: " + oContainerAssociations.getMoAssociationElementB().getMoDS_ID());

				clsDataStructurePA oMemoryObjectB = oContainerAssociations.getMoAssociationElementB();
				DefaultGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectB);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("ContAss w:" + oContainerAssociations.getMrWeight());
				oEdge.setSource(oContainerRootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
		}
		return oContainerRootCell;
	}
	
	
	/** DM
	 * Generating cells from clsDriveMesh
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsDriveMesh poMemoryObject)
	{
		String oDescription = "DM";

		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}

		//generate root of the mesh
		DefaultGraphCell oDMrootCell = createDefaultGraphVertex(oDescription, moColorDMRoot);
		this.moCellList.add(oDMrootCell);
		
		for(clsAssociation oDMAssociations : poMemoryObject.getMoAssociatedContent())
		{
			if(poMemoryObject.getMoDS_ID() == oDMAssociations.getMoAssociationElementA().getMoDS_ID())
			{
				clsDataStructurePA oMemoryObjectB = oDMAssociations.getMoAssociationElementB();
				DefaultGraphCell oTargetCell = generateGraphCell(oDMrootCell, oMemoryObjectB);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("DM w:" + oDMAssociations.getMrWeight());
				oEdge.setSource(oDMrootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else if(poMemoryObject.getMoDS_ID() == oDMAssociations.getMoAssociationElementB().getMoDS_ID())
			{
				clsDataStructurePA oMemoryObjectA = oDMAssociations.getMoAssociationElementA();
				DefaultGraphCell oTargetCell = generateGraphCell(oDMrootCell, oMemoryObjectA);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("DM w:" + oDMAssociations.getMrWeight());
				oEdge.setSource(oDMrootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else
			{ //should not be laut heimo!!!
				System.out.println("ARS Exeption: [DM] Neither A nor B are root element.");
				throw new UnsupportedOperationException("ARS Exeption: Neither A nor B are root element. argh");
			}
			
		}
		return oDMrootCell;	
	}
	
	/** TPM
	 * Generating cells from clsDriveMesh
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsThingPresentationMesh poMemoryObject)
	{
		String oDescription = "TPM";

		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}

		//generate root of the mesh
		DefaultGraphCell oTPMrootCell = createDefaultGraphVertex(oDescription, moColorTPMRoot);
		this.moCellList.add(oTPMrootCell);
		
		for(clsAssociation oDMAssociations : poMemoryObject.getMoAssociatedContent())
		{
			if(poMemoryObject.getMoDS_ID() == oDMAssociations.getMoAssociationElementA().getMoDS_ID())
			{
				clsDataStructurePA oMemoryObjectB = oDMAssociations.getMoAssociationElementB();
				DefaultGraphCell oTargetCell = generateGraphCell(oTPMrootCell, oMemoryObjectB);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("TPM w:" + oDMAssociations.getMrWeight());
				oEdge.setSource(oTPMrootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else if(poMemoryObject.getMoDS_ID() == oDMAssociations.getMoAssociationElementB().getMoDS_ID())
			{
				clsDataStructurePA oMemoryObjectA = oDMAssociations.getMoAssociationElementA();
				DefaultGraphCell oTargetCell = generateGraphCell(oTPMrootCell, oMemoryObjectA);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("TPM w:" + oDMAssociations.getMrWeight());
				oEdge.setSource(oTPMrootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else
			{ //should not be laut heimo!!!
				System.out.println("ARS Exeption: [DM] Neither A nor B are root element.");
				throw new UnsupportedOperationException("ARS Exeption: Neither A nor B are root element. argh");
			}
			
		}
		return oTPMrootCell;	
	}
	
	/** [TP]
	 * Generating cells from clsThingPresentation
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsThingPresentation poMemoryObject)
	{
		String oDescription = "TP";

		if(!UseSimpleView()) 
		{
			oDescription = 	"TP\n" +
			"DSID: "+ poMemoryObject.getMoDS_ID() + "\n" +
			"ContentType: "+ poMemoryObject.getMoContentType() + "\n" +
			"Content: "+ poMemoryObject.getMoContent();
		}
		
		RichTextValue oRichText = new RichTextValue(oDescription);
		DefaultGraphCell oCell = createDefaultGraphVertex(oRichText, moColorTP);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge("");
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oCell;
	}
	
	/** [WP]
	 * Generating cells from clsWordPresentation
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsWordPresentation poMemoryObject)
	{
		String oDescription = "WP";

		if(!UseSimpleView()) 
		{
			oDescription = poMemoryObject.toString();
		}
		
		DefaultGraphCell oCell = createDefaultGraphVertex(oDescription, moColorWP);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oCell;
	}
	
	/** [DD]
	 * Generating cells from clsDriveDemand
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsDriveDemand poMemoryObject)
	{
		String oDescription = "DD";

		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}
		
		DefaultGraphCell oCell = createDefaultGraphVertex(oDescription, moColorDD);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oCell;
	}
	
	/** [Double]
	 * Generating cells when a Pair a Double, happens in search
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, Double popoMemoryObject)
	{
		DefaultGraphCell oCell = createCircleGraphVertex(popoMemoryObject.toString(), 30, 30, 30, 30, moColorDouble);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oCell;
	}
	
	/** [String]
	 * Generating cells when a Pair a Double, happens in search
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, String popoMemoryObject)
	{
		DefaultGraphCell oCell = createCircleGraphVertex(popoMemoryObject.toString(), 30, 30, 30, 30, moColorString);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oCell;
	}	
	
	/** [NULL]
	 * Generating cells when a Pair is NULL, should not be, but happens
	 */
	private DefaultGraphCell generateNULLGraphCell(DefaultGraphCell poParentCell)
	{
		DefaultGraphCell oCell = createDefaultGraphVertex("NULL", moColorNULL);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge("");
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oCell;
	}
	
	/** [ACT]
	 * Generating cells from clsAct
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsAct poMemoryObject)
	{
		String oDescription = "ACT";

		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}
		
		//generate act root cell
		DefaultGraphCell oActRootCell = createDefaultGraphVertex(oDescription, moColorACT);
		this.moCellList.add(oActRootCell);
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oActRootCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		//add a cell for every acossiation
		//TODO could be any data type? now its only to string
		for(clsSecondaryDataStructure oAssociatedContent : poMemoryObject.getMoAssociatedContent())
		{
			DefaultGraphCell oTargetCell = generateGraphCell(oActRootCell, oAssociatedContent);
			
			//get edge to parent cell
			DefaultEdge oEdgeAssociatedParent = new DefaultEdge("AssContent");
			oEdgeParent.setSource(oActRootCell.getChildAt(0));
			oEdgeParent.setTarget(oTargetCell.getChildAt(0));
			moCellList.add(oEdgeAssociatedParent);
		}
		return oActRootCell;
	}
	
	/** [TRIPPLE]
	 * Generating cells from clsPair
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsTripple<?,?,?> poMemoryObject) {
		//create root of the tripple
		DefaultGraphCell oTrippleCellRoot = createDefaultGraphVertex("TRI", moColorTrippleRoot);
		moCellList.add(oTrippleCellRoot);
		//edge to the [parrent cell] <-> [root of tripple]
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oTrippleCellRoot.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		//generate root of the pair for [A] and [B] and [C]
		DefaultGraphCell oTrippleCellA = generateGraphCell(oTrippleCellRoot, poMemoryObject.a);
		DefaultGraphCell oTrippleCellB = generateGraphCell(oTrippleCellRoot, poMemoryObject.b);
		DefaultGraphCell oTrippleCellC = generateGraphCell(oTrippleCellRoot, poMemoryObject.c);
		
		this.moCellList.add(oTrippleCellA);
		this.moCellList.add(oTrippleCellB);
		this.moCellList.add(oTrippleCellC);
		
		//edge [A] <-> [root of tripple]
		DefaultEdge oEdgeA = new DefaultEdge("tripple A");
		oEdgeA.setSource(oTrippleCellRoot.getChildAt(0));
		oEdgeA.setTarget(oTrippleCellA.getChildAt(0));
		moCellList.add(oEdgeA);
		
		//edge [B] <-> [root of tripple]
		DefaultEdge oEdgeB = new DefaultEdge("tripple B");
		oEdgeB.setSource(oTrippleCellRoot.getChildAt(0));
		oEdgeB.setTarget(oTrippleCellB.getChildAt(0));
		moCellList.add(oEdgeB);
		
		//edge [C] <-> [root of tripple]
		DefaultEdge oEdgeC = new DefaultEdge("tripple C");
		oEdgeC.setSource(oTrippleCellRoot.getChildAt(0));
		oEdgeC.setTarget(oTrippleCellC.getChildAt(0));
		moCellList.add(oEdgeC);
		
		return oTrippleCellRoot;
	}
	
	
	
	/** [LIST]
	 * Generating cells from clsPair
	 */
	@SuppressWarnings("rawtypes")
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, List poMemoryObject) {
		
		//create root of the LIST
		DefaultGraphCell oListCellRoot = createDefaultGraphVertex("LIST", moColorTrippleRoot);
		moCellList.add(oListCellRoot);
		//edge to the [parrent cell] <-> [root of list]
		DefaultEdge oEdgeParent = new DefaultEdge("list");
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oListCellRoot.getChildAt(0));
		moCellList.add(oEdgeParent);

		for (int j=0; j<poMemoryObject.size(); j++) {
			Object o = poMemoryObject.get(j);
			generateGraphCell(oListCellRoot, o);
		}

		return poParentCell;
	}
	
}


