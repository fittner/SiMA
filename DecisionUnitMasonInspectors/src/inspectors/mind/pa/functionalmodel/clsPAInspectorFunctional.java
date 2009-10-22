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
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.event.MouseInputAdapter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

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
//import com.jgraph.example.GraphSelectionDemo.SyncGraphSelectionListener;
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
	private JGraph moGraph;
	private ArrayList<clsNode> moRootNodes;
	private JButton moBtnUpdate;
	
	boolean mnCompact;
	int w;
	int h;
	int x_mult;
	int x_offset;
	int y_mult;
	int y_offset;	

    public clsPAInspectorFunctional(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState, JTree poTree, boolean pnCompact)
    {
		moOriginalInspector = originalInspector;
		moRootNodes = clsGenerateFunctionalModel.getRootNodes();
		mnCompact = pnCompact;
		
		setDisplayValues();
		
		moGraph = new JGraph();
		moGraph.addMouseListener(new MyMouseListener(poTree, moGraph));
		moGraph.setEditable(false);
		moGraph.setConnectable(false);
		moGraph.setDisconnectable(false);
		
		updateControl();	//loading data into the graph
	
		moBtnUpdate = new JButton("Update graph...");	//create an update-button
		moBtnUpdate.addActionListener(this);
		
        setLayout(new BorderLayout());
        add(moBtnUpdate, BorderLayout.NORTH);
		add(moGraph, BorderLayout.WEST);
    }
    
    private void setDisplayValues() {
    	if (mnCompact) {
    		w = 30;
    		h = 30;
    		x_mult = w + w/2;
    		x_offset = 10;
    		y_mult = h + h/2;
    		y_offset = 10;        		
    	} else {
    		w = 120;
    		h = 65;
    		x_mult = w + w/2;
    		x_offset = 10;
    		y_mult = h + h/2;
    		y_offset = 10;    		
    	}
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
	
	@SuppressWarnings({ "serial", "unchecked" })
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

		Map nested = facade.createNestedMap(true, true);
		cache.edit(nested);

		moGraph.setModel(model);	
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
	
	private DefaultGraphCell createNode(clsNode poNode) {
		//Richtext to enable linebreaks
		RichTextBusinessObject userObject = new RichTextBusinessObject();
		RichTextValue textValue = new RichTextValue(poNode.getName(mnCompact));
		userObject.setValue(textValue);
		
		// Create vertex with the given name
		NodeCell cell = new NodeCell(userObject, poNode.moId);
		
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
	
	private DefaultEdge createConnection(clsConnection poConnection, DefaultGraphCell poSource, DefaultGraphCell poTarget) {
		String name = "";
		if (!mnCompact) {
			name = poConnection.toString();
		}
		DefaultEdge edge = new DefaultEdge(name);
		
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
	
	/**
	 * Prevent from loosing the synchronisation after a graph element is dragged
	 */
	public static class MyMouseListener extends MouseInputAdapter {
		protected JTree moMyTree;
		private JGraph moMyGraph;
		
		public MyMouseListener(JTree poTree, JGraph poGraph) {
			moMyTree = poTree;
			moMyGraph = poGraph;
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
            if (e.getSource() instanceof JGraph) {
            	Object[] selection = moMyGraph.getSelectionModel().getSelectionCells();
    			if (selection != null) {
    				for (Object s:selection) {
    					if (s instanceof NodeCell) {
    						selectNodeInTree( (NodeCell)s );
    					}
    				}
    			}
            }
        }
        
        private void selectNodeInTree(NodeCell poNode) {
        	TreePath oPath = findNode(poNode.getId());
        	moMyTree.setSelectionPath(oPath);  
        	moMyTree.expandPath(oPath);  
        	moMyTree.makeVisible(oPath); 
        }
        
        private TreePath findNode( String nodeName ) {
        	TreeNode[] oPath = findNodeRecursive( (DefaultMutableTreeNode) moMyTree.getModel().getRoot(), nodeName );
        	return new TreePath(oPath);
        	   
        }
		
		private TreeNode[] findNodeRecursive( DefaultMutableTreeNode node, String nodeName ) {
			TreeNode[] result = null;
			
			if ( node.getUserObject().toString().startsWith( nodeName ) ) {
				result = node.getPath(); 
			}
			
			for ( int i=0; i<node.getChildCount(); i++ ) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)node.getChildAt( i );
				
				result = findNodeRecursive( child, nodeName );
				if (result != null) {
					break;
				}
			}
			
			return result;
		} 
        
    }
	
	

}