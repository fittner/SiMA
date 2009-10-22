/**
 * clsPrimaryInformationPairInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 18.10.2009, 15:08:13
 */
package inspectors.mind.pa.functionalmodel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;

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

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 18.10.2009, 15:08:13
 * 
 */
public class clsPAInspectorFunctional extends Inspector implements ActionListener {

	private static final long serialVersionUID = -1191073481242249784L;
	public Inspector moOriginalInspector;
	JGraph moGraph = null;
	ArrayList<clsNode> moRootNodes;
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
    public clsPAInspectorFunctional(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState)
    {
		moOriginalInspector = originalInspector;
		moRootNodes = clsGenerateFunctionalModel.getRootNodes();
		
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
	
	private void resetAdded() {
		for (clsNode oNode:moRootNodes) {
			oNode.resetAdded();
		}
	}
	
	public void updateControl() {
		GraphModel model = new RichTextGraphModel();
		GraphLayoutCache cache = new DataGraphLayoutCache(model,
				new DefaultCellViewFactory() {
			@Override
			protected VertexView createVertexView(Object cell) {
				return new MultiLineVertexView(cell);
			}
		}, true);
		
		ArrayList<DefaultGraphCell> oCells = new ArrayList<DefaultGraphCell>();
		ArrayList<DefaultGraphCell> oRootCells = new ArrayList<DefaultGraphCell>();		
		generateAllCells(oCells, oRootCells);
		
		DefaultGraphCell[] cells = new DefaultGraphCell[oCells.size()];
		for (int i=0; i<oCells.size(); ++i) { 
			cells[i]=oCells.get(i);			
		}
		Object[] roots = oRootCells.toArray();
		
		JGraphGraphFactory.insert(model, cells);
		JGraphFacade facade = new JGraphModelFacade(model, roots);
/*		
		JGraphCompactTreeLayout layout = new JGraphCompactTreeLayout();
		layout.setNodeDistance(50); //minimal distance from node to node horizontal
		layout.setLevelDistance(25); //minimal distance from node to node vertical

		layout.setOrientation(SwingConstants.WEST);
		layout.run(facade);
		*/
		Map nested = facade.createNestedMap(true, true);
		cache.edit(nested);

		if(moGraph==null) {
			moGraph = new JGraph(model);
		} else {
			moGraph.setModel(model);	
		}
		moGraph.getGraphLayoutCache().edit(nested); // Apply the results to the actual graph
		moGraph.updateUI();
	}

	private void generateAllCells(ArrayList<DefaultGraphCell> poCells, ArrayList<DefaultGraphCell> poRootCells) {
		resetAdded();

		HashMap<Integer, DefaultGraphCell> oNodes = new HashMap<Integer, DefaultGraphCell>();
		ArrayList<DefaultGraphCell> oConnections = new ArrayList<DefaultGraphCell>();
		
		for (clsNode oNode:moRootNodes) {
			oNode.resetAdded();
		}
		
		for (clsNode oNode:moRootNodes) {
			generateCells(oNode, oNodes, oConnections);
			poRootCells.add(oNodes.get(oNode.moId));
		}
		
		poCells.addAll(oNodes.values());
		poCells.addAll(oConnections);
	}
	
	private void generateCells(clsNode poNode, HashMap<Integer, DefaultGraphCell> poNodes, ArrayList<DefaultGraphCell> poConnections) {
		if (poNode.mnAdded == false) {
			poNode.mnAdded = true;
			DefaultGraphCell oThis = createNode(poNode);
			poNodes.put(poNode.moId, oThis);
			
			for (clsConnection oC:poNode.getNextModules()) {
				generateCells(oC.moTarget, poNodes, poConnections);
				
				DefaultGraphCell oOther = poNodes.get(oC.moTarget.moId);
				if (oOther == null) {
					throw new java.lang.NullPointerException("node requested, but not found. "+poNode.moId+" -> "+oC.moTarget.moId);
				}
				
				poConnections.add( (DefaultGraphCell)createConnection(oC, oThis, oOther));
			}
		}
	}
	
	private static DefaultGraphCell createNode(clsNode poNode) {
		final int w = 120;
		final int h = 65;
		final int x_mult = w + w/2;
		final int x_offset = 10;
		final int y_mult = h + h/2;
		final int y_offset = 10;

		//Richtext to enable linebreaks
		RichTextBusinessObject userObject = new RichTextBusinessObject();
		RichTextValue textValue = new RichTextValue(poNode.toString());
		userObject.setValue(textValue);
		
		// Create vertex with the given name
		DefaultGraphCell cell = new DefaultGraphCell(userObject);

		// Set bounds
		int x = poNode.mnCol*x_mult + x_offset;
		int y = poNode.mnRow*y_mult + y_offset;
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));
		GraphConstants.setGradientColor( cell.getAttributes(), poNode.mePsychicInstance.getColor());
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
	
	private static DefaultEdge createConnection(clsConnection poConnection, DefaultGraphCell poSource, DefaultGraphCell poTarget) {
		DefaultEdge edge = new DefaultEdge(poConnection.toString());
		
		edge.setSource(poSource.getChildAt(0));
		edge.setTarget(poTarget.getChildAt(0));

		GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(edge.getAttributes(), true);
		
		return edge;
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