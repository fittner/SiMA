/**
 * CHANGELOG
 *
 * Sep 11, 2012 herret - File created
 *
 */
package graph;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


import javax.swing.ToolTipManager;

import memorymgmt.enums.eActivationType;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;

import prementalapparatus.symbolization.representationsymbol.clsSymbolVision;
import prementalapparatus.symbolization.representationsymbol.clsSymbolVisionEntry;
import prementalapparatus.symbolization.representationsymbol.clsSymbolVisionEntryAction;
import prementalapparatus.symbolization.representationsymbol.itfSymbol;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoRulesCheck;import base.datatypes.clsAct;
import base.datatypes.clsAffect;
import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructureContainer;
import base.datatypes.clsSecondaryDataStructure;
import base.datatypes.clsSecondaryDataStructureContainer;
import base.datatypes.clsTemplateImage;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentation;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;

import com.jgraph.components.labels.RichTextBusinessObject;
import com.jgraph.components.labels.RichTextGraphModel;
import com.jgraph.components.labels.RichTextValue;
import com.jgraph.example.JGraphGraphFactory;
import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.demo.JGraphLayoutMorphingManager;
import com.jgraph.layout.demo.JGraphLayoutProgressMonitor;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import complexbody.io.sensors.datatypes.clsBump;
import complexbody.io.sensors.datatypes.clsPositionChange;
import complexbody.io.sensors.datatypes.clsRadiation;
import complexbody.io.sensors.datatypes.clsSensorExtern;
import complexbody.io.sensors.datatypes.clsSensorIntern;
import complexbody.io.sensors.datatypes.clsSensorRingSegment;
import complexbody.io.sensors.datatypes.clsSensorRingSegmentEntry;




/**
 * Class that holds one Graph
 * 
 * @author herret
 * Sep 11, 2012, 4:44:44 PM
 * 
 */
public class clsGraph extends JGraph {

	private static final long serialVersionUID = -3249202451622954082L;

	private final int mnEdgeDecimalPlaces =2;
	private final boolean mbOrientationVertical;
	private int mnXLevel = 0;
	protected ArrayList<Object> moMesh = new ArrayList<Object>();
	private String moRootNodeName = "root";
	private boolean mbUseSimpleView = false;
	
	private boolean mbLinked = true;
	private ArrayList<clsGraph> moLinkedPartner = new ArrayList<clsGraph>();
	
	private boolean mbMaster= true;
	
	private boolean mbShowInternAssoc = true;
	private boolean mbShowExternAssoc = false;
	
	private boolean mbShowInternAssocFirstLevel = false;
	private boolean mbShowInternAssocBest = false;
	
	protected boolean mbLayoutChangeIsRunning =false;
	

	private int mnRuleNumber = 1; //Ivy
	
	protected ArrayList<DefaultGraphCell> moCellList = new ArrayList<DefaultGraphCell>();
	
	
	//colors for all datatypes, used in clsMeshBase:
	protected static final Color moColorTP = new Color(0xff99FF33); //light green
	protected static final Color moColorNULL = Color.WHITE; 
	protected static final Color moColorString = Color.WHITE;
	protected static final Color moColorDouble = Color.WHITE;
	protected static final Color moColorDummy = Color.WHITE;
	protected static final Color moColorWP = new Color(0xff6699FF); //light blue
	protected static final Color moColorDD = new Color(0xffCC66CC);  //Drive Demand, lavendel
	protected static final Color moColorACT = new Color(0xffFF9933); //brown
	protected static final Color moColorPairRoot = new Color(0xffFFCC00); //light orange
	protected static final Color moColorPair = new Color(0xffFFCC00);
	protected static final Color moColorTrippleRoot = new Color(0xffffFF99); //light yellow
	protected static final Color moColorTripple = new Color(0xffffFF99);
	protected static final Color moColorRoot = Color.GRAY;
	protected static final Color moColorPrimaryDataStructureContainer = new Color(0xff99CC33); //dark green
	protected static final Color moColorSecondaryDataStructureContainer = new Color(0xff3366CC); //dark blue
	protected static final Color moColorDMRoot = new Color(0xffff0066); //pinkish red
	protected static final Color moColorDMBestAssocBorder = Color.black; //light orange
	protected static final Color moColorTPMRoot = new Color(0xff99CC33); //dark green
	protected static final Color moColorTI = new Color(0xffFF9933); //brown
	protected static final Color moColorWPMRoot = new Color(0xff1874CD); //dark blue
	protected static final Color moColorEmotion = new Color(0xffffa500); //orange
	protected static final Color moColorAffect = Color.PINK;
	protected static final Color moColorSensorIntern = new Color(0xff00E5EE); //türkis
	protected static final Color moColorSymbolVision = new Color(0xffBEBEBE); //gray
	protected static final Color moColorVisionEntry = new Color(0xff8B864E); //khaki
	protected static final Color moColorSensorExtern = new Color(0xffFFE4C4); //skin 
	
	public boolean UseSimpleView() {
		return mbUseSimpleView;
	}
	public void setUseSimpleView(boolean poUseSimpleView) {
		mbUseSimpleView=poUseSimpleView;
	}
	public void setMaster(boolean m) {
		mbMaster = m;
	}
	public boolean isMaster(){
		return mbMaster;
	}
	
	public void showInternAssoc(boolean i){
		mbShowInternAssoc =i;
	}
	
	public boolean isInternAssocShown(){
		return mbShowInternAssoc;
	}
	
	public void showInternAssocFirstLevel(boolean i){
		mbShowInternAssocFirstLevel =i;
	}
	
	public boolean isInternAssocFirstLevelShown(){
		return mbShowInternAssocFirstLevel;
	}
	
	public void showInternAssocBest(boolean i){
		mbShowInternAssocBest =i;
	}
	
	public boolean isInternAssocBestShown(){
		return mbShowInternAssocBest;
	}
	
	public void showExternAssoc(boolean i){
		mbShowExternAssoc =i;
	}
	
	public boolean isExternAssocShown(){
		return mbShowExternAssoc;
	}
	public void setLinkedPartner(ArrayList<clsGraph> poPartners){
		moLinkedPartner = poPartners;
	}
	
	public ArrayList<clsGraph> getLinkedPartner(){
		return moLinkedPartner;
	}
	public boolean linked(){
		return mbLinked;
	}
	public void setLinked(boolean poLinked){
		mbLinked=poLinked;
	}
	
	public clsGraph(boolean pbOrientationVertical){
		super (new DefaultGraphModel());
		mbOrientationVertical=pbOrientationVertical;
		setGridSize(2);
		setGridEnabled(true);
		setAntiAliased(true);
		setCloneable(true);
		setModel(new RichTextGraphModel());
		initializeListeners();
		ToolTipManager.sharedInstance().registerComponent(this);
	}
	
    public void setRootNodeName(String poRootNodeName){
    	moRootNodeName = poRootNodeName;
    }
    
	
    private void initializeListeners()
    {
            // Installs mouse wheel listener for zooming
            MouseWheelListener wheelTracker = new MouseWheelListener()
            {
                @Override
				public void mouseWheelMoved(MouseWheelEvent e)
                {
                    if (e.getSource() instanceof JGraph || e.isControlDown())
                    {
                            mouseWheelMovedAction(e);
                            if(linked()){
                            	for (clsGraph oGraph: moLinkedPartner){
                            		oGraph.mouseWheelMovedAction(e);
                            	}
                            }
                    }
                }
            };

            // Handles mouse wheel events in the outline and graph component
            addMouseWheelListener(wheelTracker);
            
            MouseAdapter onMouseClick = new MouseAdapter (){
            	
            	@Override
				public void mousePressed(MouseEvent e){
            		mousePressedAction(e);
            	}
            	
            };
            addMouseListener(onMouseClick);            
    }
    
    private void mousePressedAction(MouseEvent e){
    	    	
    	DefaultGraphCell cell=(DefaultGraphCell) getFirstCellForLocation(e.getX(),e.getY());
    	
    	//restore all old Colors
		for( DefaultGraphCell oCell: moCellList){
			if(oCell instanceof clsGraphCell){
				((clsGraphCell) oCell).restoreOldColor();
			}
		}
	

    	//test if clicked on cell
    	if(cell!=null){
    		//test if onclicked cell has object saved
	    	if( cell instanceof clsGraphCell){
	    		//test if object of onclicked cell !=null
	    		if(((clsGraphCell) cell).getReference()!=null){
	    			//test if object of onclicked cell is compareable (->is clsDataStructurePA)
	    			if(((clsGraphCell) cell).getReference() instanceof clsDataStructurePA){
		    			clsDataStructurePA struct = (clsDataStructurePA)((clsGraphCell) cell).getReference();
	    				//compare all cells in list with selected cell
		    			if(struct.getDS_ID()!=-1){
			    			for( DefaultGraphCell oCell: moCellList){
			    				
			    				if(oCell instanceof clsGraphCell){
			    					Object oO =((clsGraphCell) oCell).getReference();
			    					if(oO instanceof clsDataStructurePA){
			    						clsDataStructurePA oStruct = (clsDataStructurePA)((clsGraphCell) oCell).getReference();
			    						if(oStruct.getDS_ID()==struct.getDS_ID()){
			    							((clsGraphCell) oCell).highlight(Color.white);
			    						}
			    					}
			    				}
			    				
			    			}
		    			}
	    			}
	    			((clsGraphCell) cell).highlight(Color.white);
	    			
	    		}
	    		else {
	    			// Reference not set e.g. LIST
	    		}
	    	}
	    	else{
	    		// no clsGraphCell e.g. DefaultEdge
	    		
	    	}
    	}
    	updateModel();
    	
    	
    }
    
    private void mouseWheelMovedAction(MouseWheelEvent e)
    {
    	//get the position of the visible rect
    	Rectangle rxy = getVisibleRect();
    	rxy.x= rxy.x+e.getX();
    	rxy.y= rxy.y+e.getY();
    	//alter the visible rect to the mouse position, so we zoom on the mouse cursor
    	scrollRectToVisible(rxy);
    	
    	int oWheelRotation = e.getWheelRotation();
            if (oWheelRotation < 0)
            { //zoom in
            	if(oWheelRotation < -5) // -5 is max
            		oWheelRotation = -5;
            		
            		setScale(1.3 * getScale()); //scale change can be up to 1.5, but thats a bit to big of a change
            }
            else
            { //zoom out
            	if(oWheelRotation > 5) 
            		oWheelRotation = 5;
            	
            		setScale(getScale() / 1.3);
            }
    }
    
    
    protected void updateControl() {

		//clear the cell list, or u get doubles, tripples, ...
		moCellList.clear();

		moCellList.add(createGraph());
		
		redraw();
		
	}
    
    
    protected void redraw() {

    	updateModel();
		
		performGraphLayoutChange();
		updateUI();

	}
    
    private void updateModel(){
		// Construct Model
		GraphModel model = new RichTextGraphModel();
		
		DefaultGraphCell[] cells = new DefaultGraphCell[moCellList.size()];
		for(int i=0; i<moCellList.size(); i++) {
			cells[i] = (DefaultGraphCell)moCellList.get(i);
			
		}
		
		// Insert the cells
		JGraphGraphFactory.insert(model, cells);
		setModel(model);

    }
       

    protected clsGraphCell createDefaultGraphVertex(String name, double x,
			double y, double w, double h, Color poNodeColor) {

		name = name.replace("|", "\n"); // to enable linebrakes in standard toString()
		RichTextValue textValue = new RichTextValue(name);
	
		return createDefaultGraphVertex(textValue, x, y, w, h, poNodeColor);
	}
	
	protected clsGraphCell createDefaultGraphVertex(RichTextValue richText, double x,
			double y, double w, double h, Color poNodeColor) {
	
		//Richtext to enable linebreaks
		RichTextBusinessObject userObject = new RichTextBusinessObject();
		userObject.setValue(richText);
		
		// Create vertex with the given name
		clsGraphCell cell = new clsGraphCell(userObject);

		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));
		GraphConstants.setBackground(cell.getAttributes(), poNodeColor);
		//GraphConstants.setGradientColor( cell.getAttributes(), poNodeColor);

		// Make sure the cell is resized on insert
		GraphConstants.setResize(cell.getAttributes(), true);
		GraphConstants.setAutoSize(cell.getAttributes(), true);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
		//GraphConstants.setBackground(cell.getAttributes(), new Color(240,240,240));
		
		GraphConstants.setHorizontalAlignment(cell.getAttributes(),  JLabel.LEFT);  //aligns the text in the cell
		
		// Add a Port
		cell.addPort();

		return cell;
	}
	

	
	
	/**
	 * DOCUMENT Override of original vertex creation.
	 * To create a default size
	 *
	 * @author muchitsch
	 * 25.08.2010, 14:29:42
	 *
	 * @param name
	 * @param poNodeColor
	 * @return
	 */
	protected clsGraphCell createDefaultGraphVertex(String name, Color poNodeColor) {
		
		//clsGraphCell oCell = createDefaultGraphVertex(name, xLevel*xStep, yLevel*yStep, 150, 50, poNodeColor);
		clsGraphCell oCell = createDefaultGraphVertex(name, 40, 40, 150, 40, poNodeColor);
		return oCell;
	}
	
	protected clsGraphCell createDefaultGraphVertex(RichTextValue richText, Color poNodeColor) {

			clsGraphCell oCell = createDefaultGraphVertex(richText, 40, 40, 150, 40, poNodeColor);
		return oCell;
	}
	
	/**
	 * experimental creation of a circular vertex, not working yet (CM 08.06.2011)
	 *
	 * @author muchitsch
	 * 08.06.2011, 10:28:51
	 */
	protected clsGraphCell createCircleGraphVertex(String name, double x,
			double y, double w, double h, Color poNodeColor) {

		
		//Richtext to enable linebreaks
		RichTextBusinessObject userObject = new RichTextBusinessObject();
		RichTextValue textValue = new RichTextValue(name);
		userObject.setValue(textValue);
		
		// Create vertex with the given name
		clsGraphCell cell = new clsGraphCell(userObject);

		//VertexView view = new JGraphEllipseView();
		//createVertex(20, 20, 60, 30, Color.BLUE, false, new DefaultGraphCell("hello"), "com.jgraph.example.mycellview.JGraphEllipseView");
		
		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));
		//GraphConstants.setGradientColor( cell.getAttributes(), poNodeColor);
		GraphConstants.setBackground(cell.getAttributes(), poNodeColor);
		//GraphConstants.setInset(cell.getAttributes(), 10);
		// Make sure the cell is resized on insert
		GraphConstants.setResize(cell.getAttributes(), true);
		GraphConstants.setAutoSize(cell.getAttributes(), true);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
		//GraphConstants.setBackground(cell.getAttributes(), new Color(240,240,240));
		
		// Add a Port
		cell.addPort();
		
		VertexView vv = new com.jgraph.example.mycellview.JGraphEllipseView();
		vv.setCell(cell);

		return cell;
	}
    

	protected clsGraphCell createGraph() {

		//helper array-list to collect each created cell in the right order for the registration later on
		//without knowing the total number of elements
		//ArrayList<DefaultGraphCell> oCellList = new ArrayList<DefaultGraphCell>();
		//create root node (it's a mesh-list) and add it to the registration list
		clsGraphCell oParent = createDefaultGraphVertex(moRootNodeName, 0, 0, 60, 40, moColorRoot);
		//get graph-cells for each object in the of the mesh
		mnXLevel=0;
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
	private void readInspectorDataAndGenerateGraphCells(clsGraphCell poParent) 
	{
		//check for the main list types possible
		if(moMesh!=null){
			for(int i=0; i<moMesh.size(); i++){
				Object oO = moMesh.get(i);
				generateGraphCell(poParent, oO);
			}
		}
	}
	
	/** 
	 * check for the main data types possible
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParent, Object oO) {
		
		clsGraphCell oRootCell = null;
		//set setEdge =true if typ is not connected to the rest of the graph

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
				
				//if the value of the map is a simple double, display the key aswell
				if(oValue instanceof Double ) {
					String otmpValue = oKey.toString() + " | " + oValue.toString();
					generateGraphCell(poParent, otmpValue);
				}
				else {
					generateGraphCell(poParent, oValue);
				}
			}

		} else if (oO instanceof clsDataStructurePA) {
			clsDataStructurePA oNextMemoryObject = (clsDataStructurePA)oO;
			oRootCell = generateGraphCell(poParent, oNextMemoryObject);
			
			
		} else if (oO instanceof clsDataContainer) {
			clsDataContainer oNextMemoryObject = (clsDataContainer)oO;
			oRootCell = generateGraphCell(poParent, oNextMemoryObject);

		} else if (oO instanceof clsDataPoint) {
			clsDataPoint oNextMemoryObject = (clsDataPoint)oO;
			oRootCell = generateGraphCell(poParent, oNextMemoryObject);

		}
		else if (oO instanceof clsPair) {
			@SuppressWarnings("rawtypes")
			clsPair oNextMemoryObject = (clsPair)oO;
			oRootCell = generateGraphCell(poParent, oNextMemoryObject);
			
		} else if (oO instanceof clsTriple) {
			@SuppressWarnings("rawtypes")
			clsTriple oNextMemoryObject = (clsTriple)oO;
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
			oRootCell = generateGraphCell(poParent, (clsSymbolVision) oO);
			
		} else if (oO instanceof clsSymbolVisionEntry) {
			oRootCell = generateGraphCell(poParent, (clsSymbolVisionEntry)oO);
			
		} else if (oO instanceof clsSymbolVisionEntryAction) {
			oRootCell = generateGraphCell(poParent, (clsSymbolVisionEntryAction)oO);	
				
		} else if (oO instanceof clsSensorIntern) {
			oRootCell = generateGraphCell(poParent, (clsSensorIntern) oO); 
			
		} else if (oO instanceof clsSensorExtern) {
			oRootCell = generateGraphCell(poParent, (clsSensorExtern) oO); 
			
		} else if (oO instanceof clsActionCommand) {
			oRootCell = generateGraphCell(poParent, oO.toString()); //TODO MUCHITSCH generate specialized functions to display this datatype
 
		} else if (oO instanceof String) {
			oRootCell = generateGraphCell(poParent, oO.toString()); 
 

		} else if (oO instanceof clsSuperEgoRulesCheck) { //Ivy
			clsSuperEgoRulesCheck bla = (clsSuperEgoRulesCheck) oO;
			oRootCell = generateGraphCell(poParent, bla);
		} else {
			oRootCell = generateGraphCell(poParent, oO.toString());
			System.out.println("[clsMeshBase.rIDAGGC] Unkown data structure, displaying toString(): "+oO.getClass());
		}
		
		if(oRootCell!=null && !oRootCell.equals(poParent)){
			DefaultEdge oEdgeParent = new DefaultEdge();
			oEdgeParent.setSource(poParent.getChildAt(0));
			oEdgeParent.setTarget(oRootCell.getChildAt(0));
			moCellList.add(oEdgeParent);
		}
		if(oRootCell!=null){
			oRootCell.setReference(oO);
		}
		return oRootCell;
	}


	/** [MAIN]...
	 * Generating cells from clsDataStructurePA
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsDataStructurePA poMemoryObject)
	{
		clsGraphCell oRootCell = null;
		
		mnXLevel++;
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
		else if(poMemoryObject instanceof clsTemplateImage)
		{
			clsTemplateImage tmpRootMemoryObject = (clsTemplateImage)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		}
		else if(poMemoryObject instanceof clsWordPresentationMesh)
		{
			clsWordPresentationMesh tmpRootMemoryObject = (clsWordPresentationMesh)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		} 
		else if (poMemoryObject instanceof clsEmotion){
			clsEmotion tmpRootMemoryObject = (clsEmotion)poMemoryObject;
			oRootCell = generateGraphCell(poParentCell, tmpRootMemoryObject);
		} 
		else if (poMemoryObject instanceof clsAffect) {
			clsAffect tmpRootMemoryObject = (clsAffect)poMemoryObject;
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
		//DefaultEdge oEdgeParent = new DefaultEdge();
		//oEdgeParent.setSource(poParentCell.getChildAt(0));
		//oEdgeParent.setTarget(oRootCell.getChildAt(0));
		//moCellList.add(oEdgeParent);
		mnXLevel--;
		// attach object to GraphCell
		oRootCell.setReference(poMemoryObject);
		return oRootCell;
	}
	
	/** [PAIR]
	 * Generating cells from clsPair
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsPair<?,?> poMemoryObject) {
		
		//create root of the pair
		clsGraphCell oPairCellRoot = createDefaultGraphVertex("PAIR", moColorPairRoot);
		moCellList.add(oPairCellRoot);
		
		//edge to the [parent cell] <-> [root of pair]
		DefaultEdge oEdgeToParent = new DefaultEdge();
		oEdgeToParent.setSource(poParentCell.getChildAt(0));
		oEdgeToParent.setTarget(oPairCellRoot.getChildAt(0));
		moCellList.add(oEdgeToParent);
		
		//generate root of the pair for [A] and [B]
		clsGraphCell oPairCellA = generateGraphCell(oPairCellRoot, poMemoryObject.a);
		clsGraphCell oPairCellB = generateGraphCell(oPairCellRoot, poMemoryObject.b);
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
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsDataStructureContainer poMemoryObject)
	{
		
		clsGraphCell oRootCell = null;
		
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
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsPrimaryDataStructureContainer poMemoryObject)
	{
		clsDataStructurePA oContainerRootDataStructure = poMemoryObject.getMoDataStructure();
		ArrayList<clsAssociation> oAssociatedDataStructures =  poMemoryObject.getMoAssociatedDataStructures();
		
		//create container root cell
		String oDescription = oContainerRootDataStructure.toString().substring(0, 5);

		if(!UseSimpleView()) 
		{
			oDescription = 	oContainerRootDataStructure.toString();
		}
		
		clsGraphCell oContainerRootCell = createDefaultGraphVertex(oDescription, moColorPrimaryDataStructureContainer );
		this.moCellList.add(oContainerRootCell);
//		edge to the [parrent cell] <-> [container root cell]
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oContainerRootCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		//create assosiations
		for(clsAssociation oContainerAssociations : oAssociatedDataStructures)
		{
			if(oContainerRootDataStructure.getDS_ID() == oContainerAssociations.getAssociationElementA().getDS_ID())
			{
				clsDataStructurePA oMemoryObjectB = oContainerAssociations.getAssociationElementB();
				clsGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectB);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("ContAss w:" + oContainerAssociations.getMrWeight());
				oEdge.setSource(oContainerRootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else if(oContainerRootDataStructure.getDS_ID() == oContainerAssociations.getAssociationElementA().getDS_ID())
			{
				clsDataStructurePA oMemoryObjectA = oContainerAssociations.getAssociationElementA();
				clsGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectA);
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
				System.out.println("[clsMeshBase.generateGraphCell] [PDC] Neither A nor B are root element. using element:" + oContainerAssociations.getAssociationElementB().getDS_ID());
				
				clsDataStructurePA oMemoryObjectB = oContainerAssociations.getAssociationElementB();
				clsGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectB);
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
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsSecondaryDataStructureContainer poMemoryObject)
	{
		
		
		clsDataStructurePA oContainerRootDataStructure = poMemoryObject.getMoDataStructure();
		//TODO WP werden neu erzeugt und koennen daher nicht zu den associations gefunden werden weil ID = -1, vielleicht kommt da mal eine aenderung! CM+HZ
		ArrayList<clsAssociation> oAssociatedDataStructures = new ArrayList<clsAssociation>(); // poMemoryObject.moAssociatedDataStructures;
		
		String oDescription = oContainerRootDataStructure.toString().substring(0, 5);

		if(!UseSimpleView()) 
		{
			oDescription = 	oContainerRootDataStructure.toString();
		}
		
		//create container root struct
		clsGraphCell oContainerRootCell = createDefaultGraphVertex(oDescription, moColorSecondaryDataStructureContainer );
		this.moCellList.add(oContainerRootCell);
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oContainerRootCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		//create assosiations (siehe weiter oben, wird nicht durchlaufen! da immer leer im SecContainer)
		for(clsAssociation oContainerAssociations : oAssociatedDataStructures)
		{
			if(oContainerRootDataStructure.getDS_ID() == oContainerAssociations.getAssociationElementA().getDS_ID())
			{
				clsDataStructurePA oMemoryObjectB = oContainerAssociations.getAssociationElementB();
				clsGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectB);
				//add edge
				DefaultEdge oEdge = new DefaultEdge("ContAss w:" + oContainerAssociations.getMrWeight());
				oEdge.setSource(oContainerRootCell.getChildAt(0));
				oEdge.setTarget(oTargetCell.getChildAt(0));
				moCellList.add(oEdge);
				GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_DOUBLELINE);
				GraphConstants.setEndFill(oEdge.getAttributes(), true);
			}
			else if(oContainerRootDataStructure.getDS_ID() == oContainerAssociations.getAssociationElementB().getDS_ID())
			{
				clsDataStructurePA oMemoryObjectA = oContainerAssociations.getAssociationElementA();
				clsGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectA);
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
				System.out.println("[clsMeshBase.generateGraphCell] [SDC] Neither A nor B are root element. using B: " + oContainerAssociations.getAssociationElementB().getDS_ID());

				clsDataStructurePA oMemoryObjectB = oContainerAssociations.getAssociationElementB();
				clsGraphCell oTargetCell = generateGraphCell(oContainerRootCell, oMemoryObjectB);
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
	
	
	/** [DM]
	 * Generating cells from clsDriveMesh
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsDriveMesh poMemoryObject)
	{
		//TODO CM auf neue dm ausbessern
		String oDescription = poMemoryObject.getDriveComponent().name().equals("UNDEFINED") ? "": poMemoryObject.getDriveComponent().name();
		oDescription += poMemoryObject.getPartialDrive().name().equals("UNDEFINED") ? "":  "\n" + poMemoryObject.getPartialDrive().name();
		oDescription += poMemoryObject.getActualDriveSourceAsENUM().toString().equals("UNDEFINED") ? "": "\n" + poMemoryObject.getActualDriveSourceAsENUM();
		oDescription += poMemoryObject.getActualBodyOrificeAsENUM().toString().equals("UNDEFINED") ? "": "\n" + poMemoryObject.getActualBodyOrificeAsENUM();

		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}

		//generate root of the mesh
		clsGraphCell oDMrootCell = createDefaultGraphVertex(oDescription, moColorDMRoot);
		
		this.moCellList.add(oDMrootCell);

		
		
		if(mbShowInternAssoc || (mnXLevel==1 && mbShowInternAssocFirstLevel)){
			for(clsAssociation oDMAssociations : poMemoryObject.getInternalAssociatedContent())
			{
				if(poMemoryObject.getDS_ID() == oDMAssociations.getAssociationElementA().getDS_ID())
				{
					
					boolean externAssocStatus = isExternAssocShown();
					showExternAssoc(false);
					clsDataStructurePA oMemoryObjectB = oDMAssociations.getAssociationElementB();
					clsGraphCell oTargetCell = generateGraphCell(oDMrootCell, oMemoryObjectB);
					showExternAssoc(externAssocStatus);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("DM w:" + (Math.round(oDMAssociations.getMrWeight()*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces)));
					oEdge.setSource(oDMrootCell.getChildAt(0));
					oEdge.setTarget(oTargetCell.getChildAt(0));
					moCellList.add(oEdge);
					
					GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(oEdge.getAttributes(), true);
				}
				else if(poMemoryObject.getDS_ID() == oDMAssociations.getAssociationElementB().getDS_ID())
				{
					clsDataStructurePA oMemoryObjectA = oDMAssociations.getAssociationElementA();
					clsGraphCell oTargetCell = generateGraphCell(oDMrootCell, oMemoryObjectA);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("DM w:" + (Math.round(oDMAssociations.getMrWeight()*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces)));
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
		}
		if(mbShowExternAssoc){
			//evaluate best rated Association
			clsAssociation bestAssoc= evaluateBestAssociation(poMemoryObject.getExternalAssociatedContent());
			for(clsAssociation oDMAssociations : poMemoryObject.getExternalAssociatedContent())
			{
				if(poMemoryObject.getDS_ID() == oDMAssociations.getAssociationElementA().getDS_ID())
				{
					clsDataStructurePA oMemoryObjectB = oDMAssociations.getAssociationElementB();
					//save intern assoc status
					boolean internAssocStatus = isInternAssocShown();
					if(bestAssoc.equals(oDMAssociations) && mbShowInternAssocBest) showInternAssoc(true);
					//else showInternAssoc(false);
					clsGraphCell oTargetCell = generateGraphCell(oDMrootCell, oMemoryObjectB);
					//mark best rated external association
					if(bestAssoc.equals(oDMAssociations)){
						GraphConstants.setBorder(oTargetCell.getAttributes(), BorderFactory.createLineBorder(moColorDMBestAssocBorder, (int)oDMAssociations.getMrWeight()*5+2));
					}
					//restore intern assoc status
					showInternAssoc(internAssocStatus);
					
					//add edge
					
					DefaultEdge oEdge = new DefaultEdge("DM w:" + (Math.round(oDMAssociations.getMrWeight()*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces)));
					oEdge.setSource(oDMrootCell.getChildAt(0));
					oEdge.setTarget(oTargetCell.getChildAt(0));
					moCellList.add(oEdge);
					GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(oEdge.getAttributes(), true);
				}
				else if(poMemoryObject.getDS_ID() == oDMAssociations.getAssociationElementB().getDS_ID())
				{
					clsDataStructurePA oMemoryObjectA = oDMAssociations.getAssociationElementA();
					clsGraphCell oTargetCell = generateGraphCell(oDMrootCell, oMemoryObjectA);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("DM w:" + (Math.round(oDMAssociations.getMrWeight()*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces)));
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
		}
		
		
		return oDMrootCell;	
	}
	
	/** [TPM]
	 * Generating cells from clsThingPresantationMesh
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsThingPresentationMesh poMemoryObject)
	{
		
		String oDescription = 	poMemoryObject.getContentType().name() + "\n" +
				poMemoryObject.getContent();
		oDescription += poMemoryObject.getActivations().get(eActivationType.SIMILARITY_ACTIVATION)!=null 
				?"\nSIM: "+(Math.round(poMemoryObject.getActivations().get(eActivationType.SIMILARITY_ACTIVATION)*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces))
				:"";
		oDescription += poMemoryObject.getActivations().get(eActivationType.EMBODIMENT_ACTIVATION)!=null 
				?"\nEMB: "+(Math.round(poMemoryObject.getActivations().get(eActivationType.EMBODIMENT_ACTIVATION)*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces))
				:"";
		oDescription += poMemoryObject.getActivations().get(eActivationType.CATHEXIS)!=null 
				?"\nCAT: "+(Math.round(poMemoryObject.getActivations().get(eActivationType.CATHEXIS)*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces))
				:"";
		oDescription += poMemoryObject.getActivations().get(eActivationType.ASSOCIATIVE_ACTIVATION)!=null 
				?"\nASS: "+(Math.round(poMemoryObject.getActivations().get(eActivationType.ASSOCIATIVE_ACTIVATION)*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces))
				:"";
//		oDescription += poMemoryObject.getMoListMissing()!=null 
//				?"\nMISSING: "+poMemoryObject.getMoListMissing()
//				:"";
						
		//oDescription +="\n" + poMemoryObject.getMoActivations().get(eActivationType.EMBODIMENT_ACTIVATION);
		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}

		//generate root of the mesh
		clsGraphCell oTPMrootCell = createDefaultGraphVertex(oDescription, moColorTPMRoot);
		this.moCellList.add(oTPMrootCell);


		
		if(mbShowInternAssoc){
			for(clsAssociation oDMAssociations : poMemoryObject.getInternalAssociatedContent())
			{
				if(poMemoryObject.getDS_ID() == oDMAssociations.getAssociationElementA().getDS_ID())
				{
					clsDataStructurePA oMemoryObjectB = oDMAssociations.getAssociationElementB();
					clsGraphCell oTargetCell = generateGraphCell(oTPMrootCell, oMemoryObjectB);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("TPM w:" + (Math.round(oDMAssociations.getMrWeight()*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces)));
					
					oEdge.setSource(oTPMrootCell.getChildAt(0));
					oEdge.setTarget(oTargetCell.getChildAt(0));
					moCellList.add(oEdge);
					GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(oEdge.getAttributes(), true);
				}
				else if(poMemoryObject.getDS_ID() == oDMAssociations.getAssociationElementB().getDS_ID())
				{
					clsDataStructurePA oMemoryObjectA = oDMAssociations.getAssociationElementA();
					clsGraphCell oTargetCell = generateGraphCell(oTPMrootCell, oMemoryObjectA);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("TPM w:" + (Math.round(oDMAssociations.getMrWeight()*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces)));
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
		}
		
		if(mbShowExternAssoc){

			mbShowExternAssoc=false;

			for(clsAssociation oDMAssociations : poMemoryObject.getExternalAssociatedContent())
			{ 	
				
				if(poMemoryObject.getDS_ID() == oDMAssociations.getAssociationElementA().getDS_ID())
				{
					
					clsDataStructurePA oMemoryObjectB = oDMAssociations.getAssociationElementB();
					clsGraphCell oTargetCell = generateGraphCell(oTPMrootCell, oMemoryObjectB);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("w:" + (Math.round(oDMAssociations.getMrWeight()*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces)));
					oEdge.setSource(oTPMrootCell.getChildAt(0));
					oEdge.setTarget(oTargetCell.getChildAt(0));
					moCellList.add(oEdge);
					GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(oEdge.getAttributes(), true);
				}
				else if(poMemoryObject.getDS_ID() == oDMAssociations.getAssociationElementB().getDS_ID())
				{
					clsDataStructurePA oMemoryObjectA = oDMAssociations.getAssociationElementA();
					clsGraphCell oTargetCell = generateGraphCell(oTPMrootCell, oMemoryObjectA);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("w:" + (Math.round(oDMAssociations.getMrWeight()*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces)));
					oEdge.setSource(oTPMrootCell.getChildAt(0));
					oEdge.setTarget(oTargetCell.getChildAt(0));
					moCellList.add(oEdge);
					GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(oEdge.getAttributes(), true);
				}
				else
				{ //should not be laut heimo!!!
					//System.out.println("ARS Exeption: [DM] Neither A nor B are root element.");
					//throw new UnsupportedOperationException("ARS Exeption: Neither A nor B are root element. argh");
				}

			}
			mbShowExternAssoc=true;
		}
		return oTPMrootCell;	
	}
	
	
	
	/** [clsDataContainer]
	 * Generating cells from clsThingPresantationMesh
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsDataContainer poMemoryObject)
	{
		
		String oDescription = 	"Container";


		//generate root of the mesh
		clsGraphCell oContainer = createDefaultGraphVertex(oDescription, moColorString);
		this.moCellList.add(oContainer);


		/* Add childs */
		
		for(clsDataPoint oDataPoint: poMemoryObject.getData()){
		
			clsGraphCell oTargetCell = generateGraphCell(oContainer, oDataPoint);
			//add edge
			DefaultEdge oEdge = new DefaultEdge(" ");
						
			oEdge.setSource(oContainer.getChildAt(0));
			oEdge.setTarget(oTargetCell.getChildAt(0));
			moCellList.add(oEdge);
			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(oEdge.getAttributes(), true);
		}
		
		return oContainer;

	}
	
	/** [clsDataPoint]
	 * Generating cells from clsThingPresantationMesh
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsDataPoint poMemoryObject)
	{
		
		String oDescription =poMemoryObject.getValue()+" : "+poMemoryObject.getType();


		//generate root of the mesh
		clsGraphCell oContainer = createDefaultGraphVertex(oDescription, moColorString);
		this.moCellList.add(oContainer);


		/* Add childs */
		
		for(clsDataPoint oDataPoint: poMemoryObject.getAssociatedDataPoints()){
		
			clsGraphCell oTargetCell = generateGraphCell(oContainer, oDataPoint);
			//add edge
			DefaultEdge oEdge = new DefaultEdge(" ");
						
			oEdge.setSource(oContainer.getChildAt(0));
			oEdge.setTarget(oTargetCell.getChildAt(0));
			moCellList.add(oEdge);
			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(oEdge.getAttributes(), true);
		}
		
		return oContainer;

	}
	
	
	/**
	 * DOCUMENT (Jordakieva Ivy) - draws the stored rules which passed the Superego
	 *
	 * @since 05.11.2013 17:41:35
	 *
	 * @param poParentCell
	 * @param poRule - one rule
	 * @return oRootRulesCell - the finish "tree"
	 */
	private clsGraphCell generateGraphCell (clsGraphCell poParentCell, clsSuperEgoRulesCheck poRule) {
		
		clsSuperEgoRulesCheck oRule = poRule;
		
		clsGraphCell oRootRulesCell = createDefaultGraphVertex("Rule " + mnRuleNumber + ":", moColorTPMRoot); //rule number in the csv-File
		this.moCellList.add(oRootRulesCell); //adding to the list of graphCells
		mnRuleNumber++;
		
		ArrayList <clsGraphCell> oTheCells = new ArrayList <clsGraphCell> (); //saving away the finished Cells to be able to put the edges
		
		//for every Drive in this rule
		for (int b = 0; b < oRule.driveSize(); b++) {
			String oPart = oRule.getDriveRule(b).a.toString() + " " + oRule.getDriveRule(b).b.toString() + "\nbetween " + oRule.getDriveRule(b).c[0].toString() + " and " + oRule.getDriveRule(b).c[1].toString(); 
			clsGraphCell oRuleChildren = createDefaultGraphVertex(oPart, moColorDMRoot); //creates a cell
			this.moCellList.add(oRuleChildren); //adding the cell to the root
			
			DefaultEdge oEdge = new DefaultEdge (); //creates an edge
			oEdge.setSource(oRootRulesCell.getChildAt(0)); //defining the from
			oEdge.setTarget(oRuleChildren.getChildAt(0)); //defining the to
			moCellList.add(oEdge); //adding the cell to the root
			
//			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC); //Without this there will be no arrow, just the line		
			oTheCells.add(oRuleChildren); //saving away the created cell - needing it for later
		}
		//for every Emotion in this rule
		for (int b = 0; b < oRule.emotionSize(); b++) {
			String oPart =  oRule.getEmotionRule(b).a.toString() + "\nbetween " + oRule.getEmotionRule(b).b[0].toString() + " and " + oRule.getEmotionRule(b).b[1].toString();  
			clsGraphCell oRuleChildren = createDefaultGraphVertex(oPart, moColorEmotion);
			this.moCellList.add(oRuleChildren);
			
			DefaultEdge oEdge = new DefaultEdge ();
			oEdge.setSource(oRootRulesCell.getChildAt(0));
			oEdge.setTarget(oRuleChildren.getChildAt(0));
			moCellList.add(oEdge);
			
//			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			oTheCells.add(oRuleChildren);
		}
		//for every Perception in this rule
		for (int b = 0; b < oRule.perceptionSize(); b++) {
			String oPart = oRule.getPerceptionRule(b).a.toString() + " " + oRule.getPerceptionRule(b).b.toString();  
			clsGraphCell oRuleChildren = createDefaultGraphVertex(oPart, moColorPair);
			this.moCellList.add(oRuleChildren);
			
			DefaultEdge oEdge = new DefaultEdge ();
			oEdge.setSource(oRootRulesCell.getChildAt(0));
			oEdge.setTarget(oRuleChildren.getChildAt(0));
			moCellList.add(oEdge);
			
//			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			oTheCells.add(oRuleChildren);
		}
		//making every saved away cell (oTheCells) to show an edge-cell named "then"
		clsGraphCell oThen = createDefaultGraphVertex ("then", moColorTPMRoot);
		for (int b = 0; b < oTheCells.size(); b++) {
			DefaultEdge oEdge = new DefaultEdge ();
			oEdge.setSource(oTheCells.get(b).getChildAt(0));
			oEdge.setTarget(oThen.getChildAt(0));
			moCellList.add (oThen);
			moCellList.add (oEdge);
			
			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
		}
		//for every forbiddenDrive in this rule pointing from "then"
		for (int b = 0; b < oRule.FDriveSize(); b++) {
			String oPart = oRule.getForbiddenDrive(b).a.toString() + " " + oRule.getForbiddenDrive(b).b.toString();
			clsGraphCell oRuleChildren = createDefaultGraphVertex(oPart, moColorDMRoot);
			this.moCellList.add(oRuleChildren);
			
			DefaultEdge oEdge = new DefaultEdge ();
			
			oEdge.setSource(oThen.getChildAt(0));
			oEdge.setTarget(oRuleChildren.getChildAt(0));
			moCellList.add(oEdge);
		}
		//for every forbiddenEmotion in this rule pointing from "then"
		for (int b = 0; b < oRule.FEmotionSize(); b++) {
			String oPart = oRule.getForbiddenEmotion(b).toString();
			clsGraphCell oRuleChildren = createDefaultGraphVertex(oPart, moColorEmotion);
			this.moCellList.add(oRuleChildren);
			
			DefaultEdge oEdge = new DefaultEdge ();
			
			oEdge.setSource(oThen.getChildAt(0));
			oEdge.setTarget(oRuleChildren.getChildAt(0));
			moCellList.add(oEdge);
		}
		//for every forbiddenObject in this rule pointing from "then"
		for (int b = 0; b < oRule.FObjectSize(); b++) {
			String oPart = oRule.getForbiddenObject(b).a.toString() + " " + oRule.getForbiddenObject(b).b.toString();
			clsGraphCell oRuleChildren = createDefaultGraphVertex(oPart, moColorPair);
			this.moCellList.add(oRuleChildren);
			
			DefaultEdge oEdge = new DefaultEdge ();
			
			oEdge.setSource(oThen.getChildAt(0));
			oEdge.setTarget(oRuleChildren.getChildAt(0));
			moCellList.add(oEdge);
		}
		
		return oRootRulesCell;
	}
	
	/** [TP]
	 * Generating cells from clsThingPresentation
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsThingPresentation poMemoryObject)
	{
		String 	oDescription = 	poMemoryObject.getContentType() + "\n" +
				poMemoryObject.getContent();

		if(!UseSimpleView()) 
		{
			oDescription = 	"TP\n" +
			"DSID: "+ poMemoryObject.getDS_ID() + "\n" +
			"ContentType: "+ poMemoryObject.getContentType() + "\n" +
			"Content: "+ poMemoryObject.getContent();
		}
		
		//RichTextValue oRichText = new RichTextValue(oDescription);
		clsGraphCell oCell = createDefaultGraphVertex(oDescription, moColorTP);
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
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsWordPresentation poMemoryObject)
	{
		String oDescription = poMemoryObject.getContentType() + "\n" +
				poMemoryObject.getContent();

		if(!UseSimpleView()) 
		{
			oDescription = poMemoryObject.toString();
		}
		
		clsGraphCell oCell = createDefaultGraphVertex(oDescription, moColorWP);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oCell;
	}
	
	/** [WPM]
	 * Generating cells from clsDriveMesh
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsWordPresentationMesh poMemoryObject)
	{
		String oDescription = poMemoryObject.getContent() + "\n" +
				poMemoryObject.getContentType();

		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}

		//generate root of the mesh
		clsGraphCell oWPMrootCell = createDefaultGraphVertex(oDescription, moColorWPMRoot);
		this.moCellList.add(oWPMrootCell);
		
		//interne associationen
		if(mbShowInternAssoc){
			for(clsAssociation oWPMAssociations : poMemoryObject.getInternalAssociatedContent())
			{
				if(poMemoryObject.getDS_ID() == oWPMAssociations.getAssociationElementA().getDS_ID())
				{
					clsDataStructurePA oMemoryObjectB = oWPMAssociations.getAssociationElementB();
					clsGraphCell oTargetCell = generateGraphCell(oWPMrootCell, oMemoryObjectB);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("WPM w:" + oWPMAssociations.getMrWeight());
					oEdge.setSource(oWPMrootCell.getChildAt(0));
					oEdge.setTarget(oTargetCell.getChildAt(0));
					moCellList.add(oEdge);
					GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(oEdge.getAttributes(), true);
				}
				else if(poMemoryObject.getDS_ID() == oWPMAssociations.getAssociationElementB().getDS_ID())
				{
					clsDataStructurePA oMemoryObjectA = oWPMAssociations.getAssociationElementA();
					clsGraphCell oTargetCell = generateGraphCell(oWPMrootCell, oMemoryObjectA);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("WPM w:" + oWPMAssociations.getMrWeight());
					oEdge.setSource(oWPMrootCell.getChildAt(0));
					oEdge.setTarget(oTargetCell.getChildAt(0));
					moCellList.add(oEdge);
					GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(oEdge.getAttributes(), true);
				}
				else
				{ //should not be laut heimo!!!
					System.out.println("ARS Exeption: [WPM] Neither A nor B are root element.");
					throw new UnsupportedOperationException("ARS Exeption: Neither A nor B are root element. argh");
				}
				
			}
		}
		boolean bSaveIntern =mbShowInternAssoc;
		if(mbShowExternAssoc){
			mbShowExternAssoc = false;
			mbShowInternAssoc = false;
			for(clsAssociation oDMAssociations : poMemoryObject.getExternalAssociatedContent())
			{ 	
				
				if(poMemoryObject.getDS_ID() == oDMAssociations.getAssociationElementA().getDS_ID())
				{
					
					clsDataStructurePA oMemoryObjectB = oDMAssociations.getAssociationElementB();
					clsGraphCell oTargetCell = generateGraphCell(oWPMrootCell, oMemoryObjectB);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("w:" + (Math.round(oDMAssociations.getMrWeight()*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces)));
					oEdge.setSource(oWPMrootCell.getChildAt(0));
					oEdge.setTarget(oTargetCell.getChildAt(0));
					moCellList.add(oEdge);
					GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(oEdge.getAttributes(), true);
				}
				else if(poMemoryObject.getDS_ID() == oDMAssociations.getAssociationElementB().getDS_ID())
				{
					clsDataStructurePA oMemoryObjectA = oDMAssociations.getAssociationElementA();
					clsGraphCell oTargetCell = generateGraphCell(oWPMrootCell, oMemoryObjectA);
					//add edge
					DefaultEdge oEdge = new DefaultEdge("w:" + (Math.round(oDMAssociations.getMrWeight()*Math.pow(10, mnEdgeDecimalPlaces))/Math.pow(10, mnEdgeDecimalPlaces)));
					oEdge.setSource(oWPMrootCell.getChildAt(0));
					oEdge.setTarget(oTargetCell.getChildAt(0));
					moCellList.add(oEdge);
					GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(oEdge.getAttributes(), true);
				}
				else
				{ //should not be laut heimo!!!
					//System.out.println("ARS Exeption: [DM] Neither A nor B are root element.");
					//throw new UnsupportedOperationException("ARS Exeption: Neither A nor B are root element. argh");
				}
			}
			mbShowInternAssoc = bSaveIntern;
			mbShowExternAssoc=true;
		}
		
		
		
		return oWPMrootCell;	
	}
	
	/** [EMOTION]
	 * Generating cells from clsEmotion
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsEmotion poMemoryObject)
	{
		String oDescription = poMemoryObject.getContent() + "\n" +
				poMemoryObject.getContentType();

		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}

		//generate root of the mesh
		clsGraphCell oEmpotionRootCell = createDefaultGraphVertex(oDescription, moColorEmotion);
		this.moCellList.add(oEmpotionRootCell);
		
	
		return oEmpotionRootCell;	
	}
	
	/** [AFFECT]
	 * Generating cells from clsAffect
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsAffect poMemoryObject)
	{
		String oDescription = "Affect - Pleasure: " + poMemoryObject.getPleasure();

		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}

		//generate root of the mesh
		clsGraphCell oEmpotionRootCell = createDefaultGraphVertex(oDescription, moColorAffect);
		this.moCellList.add(oEmpotionRootCell);
		
	
		return oEmpotionRootCell;	
	}
	
	/** [clsSensorIntern]
	 * Generating cells from clsEmotion
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsSensorIntern poMemoryObject)
	{
		
		String oDescription = poMemoryObject.toString();
		
		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}

		//generate root of the mesh
		clsGraphCell oEmpotionRootCell = createDefaultGraphVertex(oDescription, moColorSensorIntern);
		this.moCellList.add(oEmpotionRootCell);
		
	
		return oEmpotionRootCell;	
	}
	
	/** [clsSensorExtern]
	 * Generating cells from clsEmotion
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsSensorExtern poMemoryObject)
	{
		boolean showChilds=false;
		clsGraphCell oCell;
		String oDescription="";
		if( poMemoryObject instanceof clsBump){
			oDescription="Bumped: "+((clsBump)poMemoryObject).getBumped();
			
		}else if (poMemoryObject instanceof clsPositionChange){
			oDescription=((clsPositionChange)poMemoryObject).getSensorType() +"\n"+
					"X: "+ ((clsPositionChange)poMemoryObject).getX() +"\n" +
					"Y: "+ ((clsPositionChange)poMemoryObject).getY();
		}else if (poMemoryObject instanceof clsRadiation){
			oDescription="Radiation Intensity: "+((clsRadiation)poMemoryObject).getIntensity();
			
		}else if (poMemoryObject instanceof clsSensorRingSegment){
			showChilds=true;
			oDescription=((clsSensorRingSegment)poMemoryObject).getSensorType().toString();
			
		}else if(poMemoryObject instanceof clsSensorRingSegmentEntry){
			oDescription=((clsSensorRingSegmentEntry)poMemoryObject).getEntityId()+"\n"+
					((clsSensorRingSegmentEntry)poMemoryObject).getShapeType()+"\n"+
					"X: "+ ((clsSensorRingSegmentEntry)poMemoryObject).getPolarcoordinate().getVector().mrX+"\n"+
					"Y: "+ ((clsSensorRingSegmentEntry)poMemoryObject).getPolarcoordinate().getVector().mrY;
		}else{
			showChilds=true;
			oDescription = poMemoryObject.getSensorType().toString();
		}
			
	
			
			if(!UseSimpleView()) 
			{
				oDescription = 	poMemoryObject.toString();
			}
	
			//generate root of the mesh
			oCell = createDefaultGraphVertex(oDescription, moColorSensorExtern);
			this.moCellList.add(oCell);
			
			
			if(mbShowInternAssoc && showChilds){
				mbShowInternAssoc=false;
				for (clsSensorExtern oO: poMemoryObject.getDataObjects()){
					clsGraphCell oTargetCell = generateGraphCell(oCell, (Object) oO);
					//add edge
					DefaultEdge oEdge = new DefaultEdge();
					oEdge.setSource(oCell.getChildAt(0));
					oEdge.setTarget(oTargetCell.getChildAt(0));
					moCellList.add(oEdge);
					
					GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(oEdge.getAttributes(), true);
				}
				mbShowInternAssoc=true;
			}
			
		
		return oCell;
		
	}
	/** [ACT]
	 * Generating cells from clsAct
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsAct poMemoryObject)
	{
		String oDescription = "ACT";

		if(!UseSimpleView()) 
		{
			oDescription = 	poMemoryObject.toString();
		}
		
		//generate act root cell
		clsGraphCell oActRootCell = createDefaultGraphVertex(oDescription, moColorACT);
		this.moCellList.add(oActRootCell);
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oActRootCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		//add a cell for every acossiation
		for(clsSecondaryDataStructure oAssociatedContent : poMemoryObject.getMoAssociatedContent())
		{
			clsGraphCell oTargetCell = generateGraphCell(oActRootCell, oAssociatedContent);
			
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
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsTriple<?,?,?> poMemoryObject) {
		//create root of the tripple
		clsGraphCell oTrippleCellRoot = createDefaultGraphVertex("TRI", moColorTrippleRoot);
		moCellList.add(oTrippleCellRoot);
		//edge to the [parrent cell] <-> [root of tripple]
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oTrippleCellRoot.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		//generate root of the pair for [A] and [B] and [C]
		clsGraphCell oTrippleCellA = generateGraphCell(oTrippleCellRoot, poMemoryObject.a);
		clsGraphCell oTrippleCellB = generateGraphCell(oTrippleCellRoot, poMemoryObject.b);
		clsGraphCell oTrippleCellC = generateGraphCell(oTrippleCellRoot, poMemoryObject.c);
		
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
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, List poMemoryObject) {
		
		//create root of the LIST
		
		clsGraphCell oListCellRoot = createDefaultGraphVertex("LIST", moColorTrippleRoot);
		moCellList.add(oListCellRoot);
		//edge to the [parrent cell] <-> [root of list]
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oListCellRoot.getChildAt(0));
		moCellList.add(oEdgeParent);

		for (int j=0; j<poMemoryObject.size(); j++) {
			Object o = poMemoryObject.get(j);
			generateGraphCell(oListCellRoot, o);
		}

		return poParentCell;
	}
	
	/** [TI]
	 * Generating cells from clsThingPresentation
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsTemplateImage poMemoryObject)
	{
		String oDescription = "TI";

		if(!UseSimpleView()) 
		{
			oDescription = poMemoryObject.toString();
		}
		
		clsGraphCell oCell = createDefaultGraphVertex(oDescription, moColorTI);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oCell;
	}
	

	
	/**
	 * [clsSymbolVision]
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsSymbolVision poMemoryObject)
	{
		String oDescription = poMemoryObject.getSensorType().toString();
		
		
		clsGraphCell oCell = createDefaultGraphVertex(oDescription, moColorSymbolVision);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		for(itfSymbol oAssociatiedObject : poMemoryObject.getEntries())
		{
			
			clsGraphCell oTargetCell = generateGraphCell(oCell, oAssociatiedObject);
			//add edge
			DefaultEdge oEdge = new DefaultEdge("");
			oEdge.setSource(oCell.getChildAt(0));
			oEdge.setTarget(oTargetCell.getChildAt(0));
			moCellList.add(oEdge);
			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(oEdge.getAttributes(), true);			
		}
		
		
		return oCell;
	}
	
	
	
	/**
	 * [clsVisionEntry]
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsSymbolVisionEntry poMemoryObject)
	{
		String oDescription = poMemoryObject.getEntityType().toString();
		oDescription ="  ?  ";	
		clsGraphCell oCell = createDefaultGraphVertex(oDescription, moColorVisionEntry);
		this.moCellList.add(oCell);
		poMemoryObject.getClass().getFields();

		ArrayList<Object> childs = new ArrayList<Object>();
		//add entity prperties
		childs.add("ShapeType\n"+poMemoryObject.getShapeType().toString());
		String color = Integer.toHexString(poMemoryObject.getColor().getRGB());
		color=color.replaceFirst("ff", "");
		childs.add("Color\n"+"#"+color);
		childs.add("Alive\n"+ poMemoryObject.getAlive());
		
/*		//add entity actions
		for(ePercievedActionType oAction: poMemoryObject.getActions()){
			childs.add("Action\n"+oAction.toString());
		}
	*/	
		for (Object oO:childs){

			clsGraphCell oTargetCell = generateGraphCell(oCell, oO);
			//add edge
			DefaultEdge oEdge = new DefaultEdge();
			oEdge.setSource(oCell.getChildAt(0));
			oEdge.setTarget(oTargetCell.getChildAt(0));
			moCellList.add(oEdge);
			
			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(oEdge.getAttributes(), true);
		}
		if(poMemoryObject.getSymbolAction()!=null){
			
			clsGraphCell oTargetCell = generateGraphCell(oCell, poMemoryObject.getSymbolAction());
			//add edge
			DefaultEdge oEdge = new DefaultEdge();
			oEdge.setSource(oCell.getChildAt(0));
			oEdge.setTarget(oTargetCell.getChildAt(0));
			moCellList.add(oEdge);
			
			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(oEdge.getAttributes(), true);
		}
		
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
	
		return oCell;
	}

	/**
	 * [clsVisionEntryAction]
	 */
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, clsSymbolVisionEntryAction poMemoryObject)
	{
		String oDescription = "Action\n" + "  ?  ";//; poMemoryObject.getActionName();
		clsGraphCell oCell = createDefaultGraphVertex(oDescription, moColorVisionEntry);
		this.moCellList.add(oCell);
		poMemoryObject.getClass().getFields();

		ArrayList<Object> childs = new ArrayList<Object>();
		//add entity prperties
		childs.add("Name\n"+poMemoryObject.getActionName());
		
		for (Object oO:childs){

			clsGraphCell oTargetCell = generateGraphCell(oCell, oO);
			//add edge
			DefaultEdge oEdge = new DefaultEdge();
			oEdge.setSource(oCell.getChildAt(0));
			oEdge.setTarget(oTargetCell.getChildAt(0));
			moCellList.add(oEdge);
			
			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(oEdge.getAttributes(), true);
		}
	
		
		if(poMemoryObject.getObjectVisionEntry()!=null){
			
			clsGraphCell oTargetCell = generateGraphCell(oCell, poMemoryObject.getObjectVisionEntry());
			//add edge
			DefaultEdge oEdge = new DefaultEdge();
			oEdge.setSource(oCell.getChildAt(0));
			oEdge.setTarget(oTargetCell.getChildAt(0));
			moCellList.add(oEdge);
			
			GraphConstants.setLineEnd(oEdge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(oEdge.getAttributes(), true);
		}
		
		
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
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, Double popoMemoryObject)
	{
		clsGraphCell oCell = createCircleGraphVertex(popoMemoryObject.toString(), 30, 30, 30, 30, moColorDouble);
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
	private clsGraphCell generateGraphCell(clsGraphCell poParentCell, String popoMemoryObject)
	{
		clsGraphCell oCell = createDefaultGraphVertex(popoMemoryObject.toString(), 30, 30, 30, 30, moColorString);
		//clsGraphCell oCell = createCircleGraphVertex(popoMemoryObject.toString(), 30, 30, 30, 30, moColorString);
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
	private clsGraphCell generateNULLGraphCell(clsGraphCell poParentCell)
	{
		clsGraphCell oCell = createDefaultGraphVertex("NULL", moColorNULL);
		this.moCellList.add(oCell);
		
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge("");
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oCell;
	}
	

	
	
	public clsGraphCell generateDummyCell(clsGraphCell poParentCell){
		clsGraphCell oCell = createDefaultGraphVertex("test",0,0,100,100,moColorDummy);

		this.moCellList.add(oCell);
		//get edge to parent cell
		DefaultEdge oEdgeParent = new DefaultEdge();
		oEdgeParent.setSource(poParentCell.getChildAt(0));
		oEdgeParent.setTarget(oCell.getChildAt(0));
		moCellList.add(oEdgeParent);
		
		return oCell;
	}
	/**
	* creates a standard layout for the methode performLayoutChange(JGraphLayout layout)
	 */
    public void performGraphLayoutChange(){
		JGraphCompactTreeLayout layout = new JGraphCompactTreeLayout();
		layout.setOrientation(mbOrientationVertical?SwingConstants.WEST:SwingConstants.NORTH);
		performGraphLayoutChange(layout);
    }
	/**
	 * Executes the current layout on the current graph by creating a facade and
	 * progress monitor for the layout and invoking it's run method in a
	 * separate thread so this method call returns immediately. To display the
	 * result of the layout algorithm a {@link JGraphLayoutMorphingManager} is
	 * used.
	 */
    public synchronized void performGraphLayoutChange(final JGraphLayout layout) 
	{
    	try{

			if (isEnabled() && isMoveable()
					&& layout != null) {
				final JGraphFacade facade = createFacade(this);
			//	final JGraphLayoutMorphingManager moMorpher = new JGraphLayoutMorphingManager();
				final ProgressMonitor progressMonitor = (layout instanceof JGraphLayout.Stoppable) ? createProgressMonitor(
						this, (JGraphLayout.Stoppable) layout)
						: null;
				new Thread() {
					@Override
					public void run() {
						synchronized (this) {
							try {
								// Executes the layout and checks if the user has
								// clicked
								// on cancel during the layout run. If no progress
								// monitor
								// has been displayed or cancel has not been pressed
								// then
								// the result of the layout algorithm is processed.

	
								layout.run(facade);
	
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										try{
											boolean ignoreResult = false;
											if (progressMonitor != null) {
												ignoreResult = progressMonitor
														.isCanceled();
												progressMonitor.close();
											}
											if (!ignoreResult) {
											
												// Processes the result of the layout algorithm
												// by creating a nested map based on the global
												// settings and passing the map to a morpher
												// for the graph that should be changed.
												// The morpher will animate the change and then
												// invoke the edit method on the graph layout
												// cache.
												Map<?,?> map = facade.createNestedMap(true,true);
												//moMorpher.morph(me, map);
												getGraphLayoutCache().edit(map);
												
												//moMorpher.morph(me, map);
												//requestFocus();
											}
										}
										catch(Exception e){
											
										}
									}
								});
							} catch (Exception e) {
								//e.printStackTrace();
								//JOptionPane.showMessageDialog(this, e.getMessage());
							}
						}
					}
				}.start(); // fork
			}
    	}
    	catch(Exception e){
    		
    	}
	}
	
	/**
	 * Creates a {@link JGraphFacade} and makes sure it contains a valid set of
	 * root cells if the specified layout is a tree layout. A root cell in this
	 * context is one that has no incoming edges.
	 * 
	 * @param graph
	 *            The graph to use for the facade.
	 * @return Returns a new facade for the specified layout and graph.
	 */
    private JGraphFacade createFacade(JGraph graph) {
		// Creates and configures the facade using the global switches
		JGraphFacade facade = new JGraphFacade(graph, graph.getSelectionCells());
		facade.setIgnoresUnconnectedCells(true);
		facade.setIgnoresCellsInGroups(true);
		facade.setIgnoresHiddenCells(true);
		facade.setOrdered(true);
		//facade.setDirected(directedCheckBox.isSelected()); need?

		// Removes all existing control points from edges
		facade.resetControlPoints();
		return facade;
	}
    
    private clsAssociation evaluateBestAssociation(ArrayList<clsAssociation> poAssociation){
		double best=-1.0;
		clsAssociation moAssociation=null;
    	for(clsAssociation oDMAssociations : poAssociation)
		{	
			if(oDMAssociations.getMrWeight()>best){
				best=oDMAssociations.getMrWeight();
				moAssociation=oDMAssociations;
			}
		}
    	return moAssociation;
    }
    
    public void expandGraphCellInternAssoc(clsGraphCell poCell){
    		boolean saveShowInternAssoc = mbShowInternAssoc;
    		boolean saveShowExternAssoc =mbShowExternAssoc;
    		
    		mbShowInternAssoc = true;
    		mbShowExternAssoc =false;
    		
    		expand(poCell);

    		//restore show Assoc settings
    		mbShowInternAssoc = saveShowInternAssoc;
    		mbShowExternAssoc =saveShowExternAssoc;
    		
    }

    public void expandGraphCellExternAssoc(clsGraphCell poCell){
		boolean saveShowInternAssoc = mbShowInternAssoc;
		boolean saveShowExternAssoc = mbShowExternAssoc;
		boolean saveShowInternAssocBest = mbShowInternAssocBest;
		boolean saveShowInternAssocFirstLevel = mbShowInternAssocFirstLevel;
		
		mbShowInternAssoc = false;
		mbShowExternAssoc =true;
		mbShowInternAssocFirstLevel =false;
		mbShowInternAssocBest =false;
		
		expand(poCell);

		//restore show Assoc settings
		mbShowInternAssoc = saveShowInternAssoc;
		mbShowExternAssoc =saveShowExternAssoc;
		mbShowInternAssocBest=saveShowInternAssocBest;
		mbShowInternAssocFirstLevel=saveShowInternAssocFirstLevel;
		
    }
    
    private void expand(clsGraphCell poCell){
		int oldLength = moCellList.size();
		DefaultGraphCell oCell= generateGraphCell(poCell,poCell.getReference());

		DefaultEdge link=null;
		//redefine Edges
    	for(int i = oldLength;i<moCellList.size();i++) {
    		if(moCellList.get(i) instanceof DefaultEdge){
    			DefaultPort iPortSource =(DefaultPort)((DefaultEdge) moCellList.get(i)).getSource();

    			if(iPortSource.getParent().equals(oCell)){
    				
    				((DefaultEdge) moCellList.get(i)).setSource(poCell.getChildAt(0));        				
    			}
    			DefaultPort iPortTaget =(DefaultPort)((DefaultEdge) moCellList.get(i)).getTarget();
    			if(iPortTaget.getParent().equals(oCell)){
    				link=(DefaultEdge)moCellList.get(i);
    			}
    		}
    	}
    	if(link!=null)moCellList.remove(link);
    	moCellList.remove(oCell);
    }
 
	
	/**
	 * Creates a {@link JGraphLayoutProgressMonitor} for the specified layout.
	 * 
	 * @param graph
	 *            The graph to use as the parent component.
	 * @param layout
	 *            The layout to create the progress monitor for.
	 * @return Returns a new progress monitor.
	 */
    private ProgressMonitor createProgressMonitor(JGraph graph,
			JGraphLayout.Stoppable layout) {
		ProgressMonitor monitor = new JGraphLayoutProgressMonitor(graph,
				((JGraphLayout.Stoppable) layout).getProgress(),
				"PerformingLayout");
		monitor.setMillisToDecideToPopup(100);
		monitor.setMillisToPopup(500);
		return monitor;
	}
	
	/**
	 * Resets the graph to compact tree Layout.
	 */
    public void reset() {
		//performGraphLayoutChange(new JGraphCompactTreeLayout());
		clearSelection();
		JGraphLayoutMorphingManager.fitViewport(this);
		
	} 
      
    
}
