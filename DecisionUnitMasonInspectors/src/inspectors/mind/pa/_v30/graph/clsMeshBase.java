/**
 * clsSemanticInformationIspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author muchitsch
 * 24.10.2009, 23:58:33
 */
package inspectors.mind.pa._v30.graph;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import pa._v30.tools.clsPair;
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
		DefaultGraphCell oParent = createDefaultGraphVertex(moRootNodeName, 20, 20, 150, 40, Color.GRAY);
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
	@SuppressWarnings("unchecked")
	private void readInspectorDataAndGenerateGraphCells(DefaultGraphCell poParent) 
	{
		//check for the 3 main data types possible
		for(int i=0; i<moMesh.size(); i++){
			Object oO = moMesh.get(i);
		
			if (oO instanceof List) {
				for (Object o:(List<Object>)oO) {
					rIDAGGC(poParent, o);
				}
				
			} else if (oO instanceof Map) {
				@SuppressWarnings("rawtypes")
				Map t = (Map)oO;
				
				@SuppressWarnings("rawtypes")
				Iterator oI = t.keySet().iterator();
				
				while (oI.hasNext()) {
					Object oKey = oI.next();
					Object oValue = t.get(oKey);
					rIDAGGC(poParent, oValue);
				}
				
			} else if (oO instanceof Double) {
				generateGraphCell(poParent, (Double)oO);
				
			} else {
				rIDAGGC(poParent, oO);
			}
			
		}
	}
	
	private void rIDAGGC(DefaultGraphCell poParent, Object oO) {
		if (oO instanceof clsDataStructurePA) {
			clsDataStructurePA oNextMemoryObject = (clsDataStructurePA)oO;
			generateGraphCell(poParent, oNextMemoryObject);
		} else if (oO instanceof clsPair) {
			@SuppressWarnings("rawtypes")
			clsPair oNextMemoryObject = (clsPair)oO;
			generateGraphCell(poParent, oNextMemoryObject);
		} else if (oO instanceof clsDataStructureContainer)	{
			clsDataStructureContainer oNextMemoryObject = (clsDataStructureContainer)oO;
			generateGraphCell(poParent, oNextMemoryObject);
		} else if (oO == null) {
			generateNULLGraphCell(poParent);
			
		} else {
			generateGraphCell(poParent, oO.toString());
			System.out.println("[clsSemanticInformationInspector.rIDAGGC] Unkown data structure: "+oO.getClass());
		}
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
		else
		{
			throw new NullPointerException("ARS Exeption: DefaultGraphCell is NULL, object type clsDataStructurePA not found?");
		}
		
		if(oRootCell == null)
			throw new NullPointerException("ARS Exeption: DefaultGraphCell is NULL, object type clsDataStructurePA not found?");
		
		//get edge to paretn cell
		DefaultEdge oEdgeParent = new DefaultEdge("");
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
		DefaultGraphCell oPairCellRoot = createDefaultGraphVertex("PAIR", Color.YELLOW);
		moCellList.add(oPairCellRoot);
		//edge to the [parrent cell] <-> [root of pair]
		DefaultEdge oEdgeParent = new DefaultEdge("pair");
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oPairCellRoot.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		//generate root of the pair for [A] and [B]
		DefaultGraphCell oPairCellA = createDefaultGraphVertex(poMemoryObject.toString()+"-A", Color.YELLOW);
		DefaultGraphCell oPairCellB = createDefaultGraphVertex(poMemoryObject.toString()+"-B", Color.YELLOW);
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
		
		
		
		//check if A is type of one of our main datatypes and recursively generate the children
		if (poMemoryObject.a == null)
		{
			generateNULLGraphCell(oPairCellA);
		}
		else if(poMemoryObject.a instanceof Double)
		{
			Double oNextMemoryObject = (Double)poMemoryObject.a;
			generateGraphCell(oPairCellA, oNextMemoryObject);
		}
		else if(poMemoryObject.a instanceof clsDataStructurePA)
		{
			clsDataStructurePA oNextMemoryObject = (clsDataStructurePA)poMemoryObject.a;
			generateGraphCell(oPairCellA, oNextMemoryObject);
		}
		else if(poMemoryObject.a instanceof clsPair)
		{
			clsPair<?,?> oNextMemoryObject = (clsPair<?,?>)poMemoryObject.a;
			generateGraphCell(oPairCellA, oNextMemoryObject);
		}
		else if(poMemoryObject.a instanceof clsDataStructureContainer)
		{
			clsDataStructureContainer oNextMemoryObject = (clsDataStructureContainer)poMemoryObject.a;
			generateGraphCell(oPairCellA, oNextMemoryObject);
		}
		else
		{
			throw new java.lang.NoSuchMethodError("ARS Exeption: Type of pair.a not recognised!!! "+poMemoryObject.a.getClass());
		}
		
		//check if A is type of one of our main datatypes and recursively generate the children
		if (poMemoryObject.b == null)
		{
			generateNULLGraphCell(oPairCellB);
		}
		else if(poMemoryObject.b instanceof Double)
		{
			Double oNextMemoryObject = (Double)poMemoryObject.b;
			generateGraphCell(oPairCellB, oNextMemoryObject);
		}
		else if(poMemoryObject.b instanceof clsDataStructurePA)
		{
			clsDataStructurePA oNextMemoryObject = (clsDataStructurePA)poMemoryObject.b;
			generateGraphCell(oPairCellB, oNextMemoryObject);
		}
		else if(poMemoryObject.b instanceof clsPair)
		{
			clsPair<?,?> oNextMemoryObject = (clsPair<?,?>)poMemoryObject.b;
			generateGraphCell(oPairCellB, oNextMemoryObject);
		}
		else if(poMemoryObject.b instanceof clsDataStructureContainer)
		{
			clsDataStructureContainer oNextMemoryObject = (clsDataStructureContainer)poMemoryObject.b;
			generateGraphCell(oPairCellB, oNextMemoryObject);
		}
		else
		{
			throw new java.lang.NoSuchMethodError("ARS Exeption: Type of pair.b not recognised!!!");
		}
		
		
		//add edge [A] <-> [B], creates a triangle  [A] <-> [root of pair] <-> [B]
		DefaultEdge oEdge = new DefaultEdge("pair");
		oEdge.setSource(oPairCellA.getChildAt(0));
		oEdge.setTarget(oPairCellB.getChildAt(0));
		moCellList.add(oEdge);
		
		GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_TECHNICAL);
		GraphConstants.setLineBegin(oEdge.getAttributes(), GraphConstants.ARROW_TECHNICAL);
		GraphConstants.setLineStyle(oEdge.getAttributes(), GraphConstants.STYLE_BEZIER);
		GraphConstants.setLineWidth(oEdge.getAttributes(), 2);
		GraphConstants.setEndFill(oEdge.getAttributes(), true);
		
		return oPairCellRoot;
	}
	
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
		DefaultGraphCell oContainerRootCell = createDefaultGraphVertex(oContainerRootDataStructure.toString(), new Color(0xff99CC33) );
		this.moCellList.add(oContainerRootCell);
		//edge to the [parrent cell] <-> [container root cell]
		DefaultEdge oEdgeParent = new DefaultEdge("");
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
				DefaultEdge oEdge = new DefaultEdge("ContAss");
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
				DefaultEdge oEdge = new DefaultEdge("ContAss");
				oEdge.setSource(oContainerRootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else
			{ //should not be laut heimo!!!
				//throw new UnsupportedOperationException("ARS Exeption: Neither A nor B are root element. Go ask Heimo about his memory implementation");
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
		
		//create container root struct
		DefaultGraphCell oContainerRootCell = createDefaultGraphVertex(oContainerRootDataStructure.toString(), new Color(0xff3366CC) );
		this.moCellList.add(oContainerRootCell);
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge("");
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
				DefaultEdge oEdge = new DefaultEdge("ContAss");
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
				DefaultEdge oEdge = new DefaultEdge("ContAss");
				oEdge.setSource(oContainerRootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else
			{ //should not be laut heimo!!!
				//throw new UnsupportedOperationException("ARS Exeption: Neither A nor B are root element. Go ask Heimo about his memory implementation");
			}
		}
		return oContainerRootCell;
	}
	
	
	/** DM
	 * Generating cells from clsDriveMesh
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsDriveMesh poMemoryObject)
	{
		//generate root of the mesh
		DefaultGraphCell oDMrootCell = createDefaultGraphVertex(poMemoryObject.toString(), Color.ORANGE);
		this.moCellList.add(oDMrootCell);
		
		for(clsAssociation oDMAssociations : poMemoryObject.getMoAssociatedContent())
		{
			if(poMemoryObject.getMoDS_ID() == oDMAssociations.getMoAssociationElementA().getMoDS_ID())
			{
				clsDataStructurePA oMemoryObjectB = oDMAssociations.getMoAssociationElementB();
				DefaultGraphCell oTargetCell = generateGraphCell(oDMrootCell, oMemoryObjectB);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("DM");
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
				DefaultEdge oEdge = new DefaultEdge("DM");
				oEdge.setSource(oDMrootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else
			{ //should not be laut heimo!!!
				throw new UnsupportedOperationException("ARS Exeption: Neither A nor B are root element. Go ask Heimo about his memory implementation");
			}
			
		}
		return oDMrootCell;	
	}
	
	/** [TP]
	 * Generating cells from clsThingPresentation
	 */
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsThingPresentation poMemoryObject)
	{
		DefaultGraphCell oCell = createDefaultGraphVertex(poMemoryObject.toString(), moColorTP);
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
		DefaultGraphCell oCell = createDefaultGraphVertex(poMemoryObject.toString(), Color.LIGHT_GRAY);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge("");
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
		DefaultGraphCell oCell = createDefaultGraphVertex(poMemoryObject.toString(), Color.GRAY);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge("");
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
		DefaultGraphCell oCell = createCircleGraphVertex(popoMemoryObject.toString(), 30, 30, 30, 30, Color.WHITE);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge("");
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
		DefaultGraphCell oCell = createCircleGraphVertex(popoMemoryObject.toString(), 30, 30, 30, 30, Color.MAGENTA);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge("");
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
		//generate act root cell
		DefaultGraphCell oActRootCell = createDefaultGraphVertex(poMemoryObject.toString(), Color.YELLOW);
		this.moCellList.add(oActRootCell);
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge("");
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
}


