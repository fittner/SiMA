/**
 * clsPlanInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 25.10.2009, 16:17:03
 */
package inspectors.mind.pa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;

import pa.datatypes.clsAssociation;
import pa.datatypes.clsSecondaryInformation;
import pa.loader.plan.clsPlanAction;
import pa.loader.plan.clsPlanBaseMesh;
import pa.loader.plan.clsPlanStateMesh;
import pa.loader.plan.clsPlanTransition;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

import com.jgraph.components.labels.MultiLineVertexView;
import com.jgraph.components.labels.RichTextBusinessObject;
import com.jgraph.components.labels.RichTextGraphModel;
import com.jgraph.components.labels.RichTextValue;
import com.jgraph.example.JGraphGraphFactory;
import com.jgraph.layout.DataGraphLayoutCache;
import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphModelFacade;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 25.10.2009, 16:17:03
 * 
 */
public class clsPlanInspector extends Inspector implements ActionListener {

	private static final long serialVersionUID = 586283139693057158L;
	public Inspector moOriginalInspector;
	private clsPlanBaseMesh moPlan;
	JGraph moGraph = null;
	
	private JButton moBtnUpdate;
	
    /**
     * Constructs a treegraph in the inspector that represents the ArrayList of clsThingPresentationMeshes
     * poMeshContainer is the class-instance that contains the ArrayList
     * poMeshListMemberName holds the name of the member-variable that is the ArrayList
     * 
     * When updateControl() is called (e.g. by clicking the update-button) this inspector is able to get the 
     * current AND actual ArrayList directly from the container.
     * 
     *  NOTE: The Method updateInspector() is implemented empty. If you want the inspector to update 
     *  automatically in each step, extend this class and override the updateInspector()-method and call 
     *  updateControl().
     * 
     * @author langr
     * 15.10.2009, 22:17:47
     *
     * @param originalInspector
     * @param wrapper
     * @param guiState
     * @param poMeshContainer
     * @param poMeshListMemberName
     */
    public clsPlanInspector(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsPlanBaseMesh poPlan)
    {
		moOriginalInspector = originalInspector;
		moPlan = poPlan;
		
		updateControl();	//loading data into the graph
	
		moBtnUpdate = new JButton("Update graph...");	//create an update-button
		moBtnUpdate.addActionListener(this);
		
        setLayout(new BorderLayout());
        add(moBtnUpdate, BorderLayout.NORTH);
		add(moGraph, BorderLayout.CENTER);
    }
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 13.08.2009, 01:46:51
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {

	}
	
	public void updateControl() {

		// Construct Model and GraphLayoutCache
		GraphModel model = new RichTextGraphModel();
		// When not using a JGraph instance, a GraphLayoutCache does not
		// automatically listen to model changes. Therefore, use a special
		// layout cache with a built-in listener
		GraphLayoutCache cache = new DataGraphLayoutCache(model,
				new DefaultCellViewFactory() {
			@Override
			protected VertexView createVertexView(Object cell) {
				return new MultiLineVertexView(cell);
			}
		}, true);
		
		//helper array-list to collect each created cell in the right order for the registration later on
		//without knowing the total number of elements
		ArrayList<DefaultGraphCell> oCellList = new ArrayList<DefaultGraphCell>();

		//get graph-cells for each sub-thingpresentation of the mesh
		readScenarioGraph(oCellList, "EntryState");
		
		//transfer graph-cells from arraylist to fix-size array (needed for registration)
		DefaultGraphCell[] cells = new DefaultGraphCell[oCellList.size()];
		for(int i=0; i<oCellList.size(); i++) {
			cells[i] = (DefaultGraphCell)oCellList.get(i);
		}
		// Insert the cells via the cache
		JGraphGraphFactory.insert(model, cells);
		// Create the layout facade. When creating a facade for the tree
		// layouts, pass in any cells that are intended to be the tree roots
		// in the layout
		JGraphFacade facade = new JGraphModelFacade(model, cells);
		
		// Create the layout to be applied (Tree)
		JGraphHierarchicalLayout layout = new JGraphHierarchicalLayout();
//		layout.setNodeDistance(15); //minimal distance from node to node horizontal
//		layout.setLevelDistance(30); //minimal distance from node to node vertical
		layout.setOrientation(SwingConstants.NORTH);
		// Run the layout, the facade holds the results
		layout.run(facade);
		// Obtain the output of the layout from the facade. The second
		// parameter defines whether or not to flush the output to the
		// origin of the graph
		Map nested = facade.createNestedMap(true, true);
		// Apply the result to the graph
		cache.edit(nested);

		if(moGraph==null) {
			moGraph = new JGraph(model);
		}
		else {
			moGraph.setModel(model);	
		}
		moGraph.getGraphLayoutCache().edit(nested); // Apply the results to the actual graph
		moGraph.updateUI();
	}

	/**
	 * for each mesh in list
	 *
	 * @author langr
	 * 14.10.2009, 16:59:24
	 *
	 * @param poCellList
	 * @param poParent
	 */
	private void readScenarioGraph(ArrayList<DefaultGraphCell> poCellList, String poAssociationName) {

		HashMap<Integer, DefaultGraphCell> oStateCells = new HashMap<Integer, DefaultGraphCell>();
		
		//create each state
		for( Map.Entry<Integer, clsPlanStateMesh> oMesh : moPlan.moStates.entrySet()) {
			DefaultGraphCell oStateNode = null;
			if(moPlan.mnCurrentState == oMesh.getKey()) {
				oStateNode = createVertex(oMesh.getValue().moWP.moContentName + "\n"+ oMesh.getValue().moWP.moContent, 20, 20, 150, 40, new Color(200,60,60));
			}
			else {
				oStateNode = createVertex(oMesh.getValue().moWP.moContentName + "\n"+ oMesh.getValue().moWP.moContent, 20, 20, 150, 40, new Color(60,60,200));
			}
			oStateCells.put( oMesh.getKey(), oStateNode);
			poCellList.add(oStateNode);
		}
		
		//create the transitions and connect with states
		for( Map.Entry<Integer, clsPlanStateMesh> oMesh : moPlan.moStates.entrySet()) {
			for( clsAssociation<clsSecondaryInformation> oAssoc : oMesh.getValue().moAssociations ) {
				
				DefaultGraphCell oSourceNode = oStateCells.get(oMesh.getKey());
				
				if( oAssoc.moElementB instanceof clsPlanTransition ) {
					
					clsPlanTransition oTransition = (clsPlanTransition)oAssoc.moElementB;
					
					DefaultGraphCell oTargetNode = oStateCells.get(oTransition.mnTargetId);
					
					DefaultEdge edge = new DefaultEdge(oTransition.moWP.moContent + " ["+oTransition.mrMatch+"]");
					edge.setSource(oSourceNode.getChildAt(0));
					edge.setTarget(oTargetNode.getChildAt(0));
					poCellList.add(edge);
					
					GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(edge.getAttributes(), true);
					
				}
				else if( oAssoc.moElementB instanceof clsPlanAction ) {
					clsPlanAction oAction = (clsPlanAction)oAssoc.moElementB;
					DefaultGraphCell oActionNode = createVertex(oAction.moWP.moContentType + "\n" + oAction.moWP.moContent, 20, 20, 150, 40, new Color(60,200,60));
					oStateCells.put( oMesh.getKey(), oActionNode);
					poCellList.add(oActionNode);
					
					DefaultEdge edge = new DefaultEdge();
					edge.setSource(oSourceNode.getChildAt(0));
					edge.setTarget(oActionNode.getChildAt(0));
					poCellList.add(edge);
					
					GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
					GraphConstants.setEndFill(edge.getAttributes(), true);
				}
			}
		}
	}	
	
	/**
	 * static helper to create a graph-cell and set the default style within this inspector
	 *
	 * @author langr
	 * 15.10.2009, 22:31:41
	 *
	 * @param name
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public static DefaultGraphCell createVertex(String name, double x,
			double y, double w, double h, Color poColor) {

		//Richtext to enable linebreaks
		RichTextBusinessObject userObject = new RichTextBusinessObject();
		RichTextValue textValue = new RichTextValue(name);
		userObject.setValue(textValue);
		
		// Create vertex with the given name
		DefaultGraphCell cell = new DefaultGraphCell(userObject);

		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));
		GraphConstants.setGradientColor( cell.getAttributes(), poColor);
		//GraphConstants.setInset(cell.getAttributes(), 10);
		// Make sure the cell is resized on insert
		GraphConstants.setResize(cell.getAttributes(), true);
		GraphConstants.setAutoSize(cell.getAttributes(), true);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
		GraphConstants.setBackground(cell.getAttributes(), new Color(240,240,240));
		
		// Add a Port
		cell.addPort();

		return cell;
	}

	/* this is the update button - if pressed-->reload & redraw
	 *
	 * @author langr
	 * 15.10.2009, 22:44:13
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if( source == moBtnUpdate) {
			updateControl();
		}
	}
}