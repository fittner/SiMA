/**
 * clsSemanticInformationIspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author muchitsch
 * 24.10.2009, 23:58:33
 */
package inspectors.mind.pa;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;



import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ProgressMonitor;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;

import pa.datatypes.clsAffect;
import pa.datatypes.clsAssociation;
import pa.datatypes.clsAssociationContent;
import pa.datatypes.clsAssociationContext;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
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
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.JGraphModelFacade;

import com.jgraph.layout.demo.JGraphLayoutMorphingManager;
import com.jgraph.layout.demo.JGraphLayoutProgressMonitor;
import com.jgraph.layout.graph.JGraphSimpleLayout;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;
import com.jgraph.layout.tree.JGraphRadialTreeLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;
import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;


/**
 * The one and only Inspector for Heimos Memory. Can display all memory Infoemation aka eDataType
 * just map the inspector tab with this inspector here: clsInspectorMappingPA
 * and fill it with a hash map containing the information pairs 
 * 
 * @author muchitsch
 * 24.10.2009, 23:58:33
 * 
 */
public class clsSemanticInformationIspector extends Inspector implements ActionListener {
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 10.08.2010, 17:54:49
	 */
	private static final long serialVersionUID = 1698933486037476484L;
	//class members...
	protected static JGraphGraphFactory moGraphFactory = new JGraphGraphFactory();
	protected JGraph moGraph = new JGraph(new DefaultGraphModel());
	protected JTaskPane moTaskPane = new JTaskPane();
	protected JLabel moLabelStatusBar = new JLabel("Status...");
	
	protected JCheckBox flushOriginCheckBox = new JCheckBox("Flush", true),	directedCheckBox = new JCheckBox("Directed", true);
	//Holds the morphing manager.
	protected JGraphLayoutMorphingManager moMorpher = new JGraphLayoutMorphingManager();

	
	public Inspector moOriginalInspector;
	private Object moMeshContainer;
	private String moMeshListMemberName;
	//private ArrayList<clsSecondaryInformation> moSecondary;


    /**
     * Constructor of the class. Creates the panel, buttons etc. 
     * 
     * @author muchitsch
     * 04.08.2010, 16:56:58
     *
     * @param originalInspector
     * @param wrapper
     * @param guiState
     * @param poMeshContainer
     * @param poMeshListMemberName
     */
    public clsSemanticInformationIspector(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            Object poMeshContainer,
            String poMeshListMemberName)
    {
 	
		moOriginalInspector = originalInspector;		//
		moMeshContainer = poMeshContainer;				//container class	
		moMeshListMemberName = poMeshListMemberName;	//member name of the list within the containing class
		
		initializePanel();	//put all components on the panel
		
		updateControl();	//loading data into the graph
	
		 
		
		/*
		moBtnUpdate = new JButton("Update graph...");	//create an update-button
		moBtnUpdate.addActionListener(this);
		
        setLayout(new BorderLayout());
        add(moBtnUpdate, BorderLayout.NORTH);
		add(moGraph, BorderLayout.CENTER);
		*/
    }
    
    /**
     * Creates the Panel Design with all its buttons etc.
     *
     * @author muchitsch
     * 05.08.2010, 10:04:58
     */
    private void initializePanel()
    {
    	setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		// Configures the jgraph
		moGraph.setGridSize(4);
		moGraph.setGridEnabled(true);
		moGraph.setAntiAliased(true);
		moGraph.setCloneable(true);
		
		// === LAYOUT ===
		// ADD TaskPaneGroup for Layout
		JTaskPaneGroup oTaskGroupLayout = new JTaskPaneGroup();
		oTaskGroupLayout.setTitle("Graph Layout");
		oTaskGroupLayout.add(new AbstractAction("Hierarchical") {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				performGraphLayoutChange(new JGraphHierarchicalLayout());
			}
		});
		oTaskGroupLayout.add(new AbstractAction("Fast Organic") {
			/**
			 * DOCUMENT (deutsch) - insert description 
			 * 
			 * @author deutsch
			 * 10.08.2010, 17:54:49
			 */
			private static final long serialVersionUID = 6447974553914052766L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JGraphFastOrganicLayout layout = new JGraphFastOrganicLayout();
				layout.setForceConstant(60);
				performGraphLayoutChange(layout);
			}
		});
		
		oTaskGroupLayout.add(new AbstractAction("Simple Circle") {
			/**
			 * DOCUMENT (deutsch) - insert description 
			 * 
			 * @author deutsch
			 * 10.08.2010, 17:54:49
			 */
			private static final long serialVersionUID = -1444623169928884258L;

			@Override
			public void actionPerformed(ActionEvent e) {
				performGraphLayoutChange(new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE));
			}
		});
		
		oTaskGroupLayout.add(new AbstractAction("Simple Tilt") {
			/**
			 * DOCUMENT (deutsch) - insert description 
			 * 
			 * @author deutsch
			 * 10.08.2010, 17:54:49
			 */
			private static final long serialVersionUID = -6513607636960953397L;

			@Override
			public void actionPerformed(ActionEvent e) {
				performGraphLayoutChange(new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_TILT));
			}
		});
		
		oTaskGroupLayout.add(new AbstractAction("Compact Tree") {
			/**
			 * DOCUMENT (deutsch) - insert description 
			 * 
			 * @author deutsch
			 * 10.08.2010, 17:54:49
			 */
			private static final long serialVersionUID = -2811903925630396473L;

			@Override
			public void actionPerformed(ActionEvent e) {
				performGraphLayoutChange(new JGraphCompactTreeLayout());
			}
		});
		oTaskGroupLayout.add(new AbstractAction("Radialtree") {
			/**
			 * DOCUMENT (deutsch) - insert description 
			 * 
			 * @author deutsch
			 * 10.08.2010, 17:54:49
			 */
			private static final long serialVersionUID = -7537382541569261175L;

			@Override
			public void actionPerformed(ActionEvent e) {
				performGraphLayoutChange(new JGraphRadialTreeLayout());
			}
		});
		oTaskGroupLayout.add(new AbstractAction("Tree") {
			/**
			 * DOCUMENT (deutsch) - insert description 
			 * 
			 * @author deutsch
			 * 10.08.2010, 17:54:49
			 */
			private static final long serialVersionUID = 1618477318723383606L;

			@Override
			public void actionPerformed(ActionEvent e) {
				performGraphLayoutChange(new JGraphTreeLayout());
			}
		});

		moTaskPane.add(oTaskGroupLayout);
		
		
		// === FILTER ===
		// ADD TaskPaneGroup for Filters
		JTaskPaneGroup oTaskGroupFilter = new JTaskPaneGroup();
		oTaskGroupFilter.setTitle("Filter");
		moTaskPane.add(oTaskGroupFilter);
		
		// === COMMAND ===
		// ADD TaskPaneGroup for Commands
		JTaskPaneGroup oTaskGroupCommands = new JTaskPaneGroup();
		oTaskGroupCommands.setTitle("Commands");
		
		oTaskGroupCommands.add(new AbstractAction("Actual Size") {
			/**
			 * DOCUMENT (deutsch) - insert description 
			 * 
			 * @author deutsch
			 * 10.08.2010, 17:54:49
			 */
			private static final long serialVersionUID = -7683571637499420675L;

			@Override
			public void actionPerformed(ActionEvent e) {
				moGraph.setScale(1);
			}
		});
		oTaskGroupCommands.add(new AbstractAction("Fit Window") {
			/**
			 * DOCUMENT (deutsch) - insert description 
			 * 
			 * @author deutsch
			 * 10.08.2010, 17:54:49
			 */
			private static final long serialVersionUID = -4236402393050941924L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JGraphLayoutMorphingManager.fitViewport(moGraph);
			}
		});
				
		oTaskGroupCommands.add(new AbstractAction("Reset") {
			/**
			 * DOCUMENT (deutsch) - insert description 
			 * 
			 * @author deutsch
			 * 10.08.2010, 17:54:49
			 */
			private static final long serialVersionUID = 4769006307236101696L;

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		
		oTaskGroupCommands.add(new AbstractAction("Insert Test Data") {
			/**
			 * DOCUMENT (deutsch) - insert description 
			 * 
			 * @author deutsch
			 * 10.08.2010, 17:54:49
			 */
			private static final long serialVersionUID = 4963188240381232166L;

			@Override
			public void actionPerformed(ActionEvent e) {
				showTestData();
			}
		});

		moTaskPane.add(oTaskGroupCommands);
		
		//create the SplitPane and add the two windows
		JScrollPane oMenuScrollPane = new JScrollPane(moTaskPane);
		JScrollPane oGraphScrollPane = new JScrollPane(moGraph);
		// Adds the split pane
		JSplitPane oSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, oMenuScrollPane, oGraphScrollPane);
		oSplitPane.setDividerLocation(150);
		
		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		oMenuScrollPane.setMinimumSize(minimumSize);
		oGraphScrollPane.setMinimumSize(minimumSize);
		
		//add the SplitPane to the Inspector (final magic)
    	this.add(oSplitPane, BorderLayout.CENTER);
    	
    	// Adds the status bar at bottom (final final)
		moLabelStatusBar.setText(JGraphLayout.VERSION  +  " - Semantic Memory Inspector");
		moLabelStatusBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		moLabelStatusBar.setFont(moLabelStatusBar.getFont().deriveFont(Font.PLAIN));
		this.add(moLabelStatusBar, BorderLayout.SOUTH);
    }

	/**
	 * loads the TPMesh-List from the corresponding container
	 *
	 * @author muchitsch
	 * 13.10.2009, 22:34:11
	 *
	 */
	private void updateSecInfoData() {
		
		
			/*Object oMeshList = moMeshContainer.getClass().getField(moMeshListMemberName).get(moMeshContainer);
			moSecondary = (ArrayList<clsSecondaryInformation>)oMeshList;
			*/
		
	}
	
	/**
	 * Executes the current layout on the current graph by creating a facade and
	 * progress monitor for the layout and invoking it's run method in a
	 * separate thread so this method call returns immediately. To display the
	 * result of the layout algorithm a {@link JGraphLayoutMorphingManager} is
	 * used.
	 */
	public void performGraphLayoutChange(final JGraphLayout layout) 
	{
		if (moGraph != null && moGraph.isEnabled() && moGraph.isMoveable()
				&& layout != null) {
			final JGraphFacade facade = createFacade(moGraph);

			final ProgressMonitor progressMonitor = (layout instanceof JGraphLayout.Stoppable) ? createProgressMonitor(
					moGraph, (JGraphLayout.Stoppable) layout)
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
									boolean ignoreResult = false;
									if (progressMonitor != null) {
										ignoreResult = progressMonitor
												.isCanceled();
										progressMonitor.close();
									}
									if (!ignoreResult) {

										// Processes the result of the layout
										// algorithm
										// by creating a nested map based on the
										// global
										// settings and passing the map to a
										// morpher
										// for the graph that should be changed.
										// The morpher will animate the change
										// and then
										// invoke the edit method on the graph
										// layout
										// cache.
										Map map = facade.createNestedMap(true,
												(flushOriginCheckBox
														.isSelected()) ? true
														: false);
										moMorpher.morph(moGraph, map);
										moGraph.requestFocus();
									}
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(moGraph, e.getMessage());
						}
					}
				}
			}.start(); // fork
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
	protected JGraphFacade createFacade(JGraph graph) {
		// Creates and configures the facade using the global switches
		JGraphFacade facade = new JGraphFacade(graph, graph.getSelectionCells());
		facade.setIgnoresUnconnectedCells(true);
		facade.setIgnoresCellsInGroups(true);
		facade.setIgnoresHiddenCells(true);
		//facade.setDirected(directedCheckBox.isSelected()); need?

		// Removes all existing control points from edges
		facade.resetControlPoints();
		return facade;
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
	protected ProgressMonitor createProgressMonitor(JGraph graph,
			JGraphLayout.Stoppable layout) {
		ProgressMonitor monitor = new JGraphLayoutProgressMonitor(graph,
				((JGraphLayout.Stoppable) layout).getProgress(),
				"PerformingLayout");
		monitor.setMillisToDecideToPopup(100);
		monitor.setMillisToPopup(500);
		return monitor;
	}
	
	/**
	 * Resets the graph to a circular layout.
	 */
	public void reset() {
		performGraphLayoutChange(new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE));
		moGraph.clearSelection();
		JGraphLayoutMorphingManager.fitViewport(moGraph);
		
		moLabelStatusBar.setText("JGraph reseted to start");
	}
	
	@Deprecated
	public void showTestData() {
		moGraphFactory.insertConnectedGraphSampleData(moGraph,
				createCellAttributes(new Point2D.Double(0, 0)),
				createEdgeAttributes());
		reset();
	}
	
	/**
	 * Hook from GraphEd to set attributes of a new cell
	 */
	public Map createCellAttributes(Point2D point) {
		Map map = new Hashtable();
		// Snap the Point to the Grid
		point = moGraph.snap((Point2D) point.clone());
		// Add a Bounds Attribute to the Map
		GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(),
				point.getY(), 0, 0));
		// Make sure the cell is resized on insert
		GraphConstants.setResize(map, true);
		// Add a nice looking gradient background
		GraphConstants.setGradientColor(map, Color.blue);
		// Add a Border Color Attribute to the Map
		GraphConstants.setBorderColor(map, Color.black);
		// Add a White Background
		GraphConstants.setBackground(map, Color.white);
		// Make Vertex Opaque
		GraphConstants.setOpaque(map, true);
		GraphConstants.setInset(map, 2);
		GraphConstants.setGradientColor(map, new Color(200, 200, 255));
		return map;
	}

	/**
	 * Hook from GraphEd to set attributes of a new edge
	 */
	public Map createEdgeAttributes() {
		Map map = new Hashtable();
		// Add a Line End Attribute
		GraphConstants.setLineEnd(map, GraphConstants.ARROW_SIMPLE);
		// Add a label along edge attribute
		GraphConstants.setLabelAlongEdge(map, true);
		// Adds a parallel edge router
		GraphConstants.setLineStyle(map, GraphConstants.STYLE_SPLINE);
		GraphConstants.setFont(map, GraphConstants.DEFAULTFONT.deriveFont(10f));
		return map;
	}
	
	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 13.08.2009, 01:46:51
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {

	}
	
	public void updateControl() {

		// fetch the new ArrayList from the containing class
		updateSecInfoData();

		// Construct Model and GraphLayoutCache
		GraphModel model = new RichTextGraphModel();
		// When not using a JGraph instance, a GraphLayoutCache does not
		// automatically listen to model changes. Therefore, use a special
		// layout cache with a built-in listener
		GraphLayoutCache cache = new DataGraphLayoutCache(model,
				new DefaultCellViewFactory() {
			/**
					 * DOCUMENT (deutsch) - insert description 
					 * 
					 * @author deutsch
					 * 10.08.2010, 17:54:49
					 */
					private static final long serialVersionUID = 5527702598146461914L;

			@Override
			protected VertexView createVertexView(Object cell) {
				return new MultiLineVertexView(cell);
			}
		}, true);
		
		//helper array-list to collect each created cell in the right order for the registration later on
		//without knowing the total number of elements
		ArrayList<DefaultGraphCell> oCellList = new ArrayList<DefaultGraphCell>();
		//create root node (it's a mesh-list) and add it to the registration list
		DefaultGraphCell oParent = createVertex(moMeshListMemberName+" (Secondary Informations)", 20, 20, 150, 40);
		oCellList.add( oParent );
		//get graph-cells for each sub-thingpresentation of the mesh
		readSecondaryInfoList(oCellList, oParent, "");
		
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
		JGraphFacade facade = new JGraphModelFacade(model, new Object[]{cells[0]});
		
		// Create the layout to be applied (Tree)
		JGraphCompactTreeLayout layout = new JGraphCompactTreeLayout();
		layout.setNodeDistance(15); //minimal distance from node to node horizontal
		layout.setLevelDistance(30); //minimal distance from node to node vertical
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
	 * @author muchitsch
	 * 14.10.2009, 16:59:24
	 *
	 * @param poCellList
	 * @param poParent
	 */
	private void readSecondaryInfoList(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, String poAssociationName) {
		/*
		for(clsSecondaryInformation oSec : moSecondary) {
			readSec(poCellList, poParent, oSec, poAssociationName);
		}
		*/
	}	
	
	/**
	 * creates either the graph for a mesh or the singel, but adds the affect too.
	 *
	 * @author muchitsch
	 * 15.10.2009, 22:56:34
	 *
	 * @param poCellList
	 * @param poParent
	 * @param prim
	 * @param poAssociationName
	 */
	private void readSec(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsSecondaryInformation sec,
			String poAssociationName) {

		if(sec instanceof clsSecondaryInformationMesh) {
			DefaultGraphCell oCell = readMesh(poCellList, poParent, (clsSecondaryInformationMesh)sec, poAssociationName);
			readAffect(poCellList, oCell, (clsAffect)sec.moAffect, "affect_assoc");
		}
		else if(sec instanceof clsSecondaryInformation) {
			DefaultGraphCell oCell = readSingle(poCellList, poParent, (clsSecondaryInformation)sec, poAssociationName);
			readAffect(poCellList, oCell, (clsAffect)sec.moAffect, "affect_assoc");
		}
	}

	/**
	 * Creates a graph-cell for the mesh, connects the graph-cell with an arrowed edge to the parent and
	 * recursively does the same for sub-meshes or sub-TPsingles.
	 *
	 * @author muchitsch
	 * 15.10.2009, 22:25:39
	 *
	 * @param poCellList
	 * @param poParent
	 * @param poPrimMesh
	 * @param poAssociationName
	 */
	private DefaultGraphCell readMesh(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsSecondaryInformationMesh poSecMesh, String poAssociationName) {

			String oVertexName = poSecMesh.moWP.moContentName + ":\n" + poSecMesh.moWP.moContent;
			DefaultGraphCell oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40);
			poCellList.add( oCurrentVertex );
			DefaultEdge edge = new DefaultEdge(poAssociationName);
			edge.setSource(poParent.getChildAt(0));
			edge.setTarget(oCurrentVertex.getChildAt(0));
			poCellList.add(edge);
			
			GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(edge.getAttributes(), true);
			
			for( clsAssociation<clsSecondaryInformation> oChildAssoc :  poSecMesh.moAssociations) {

				String oName = ""; //the edge will get the name of the association context
				if(oChildAssoc instanceof clsAssociationContext 
								&& ((clsAssociationContext<clsSecondaryInformation>)oChildAssoc).moAssociationContext != null) {
						oName+=((clsAssociationContext<clsSecondaryInformation>)oChildAssoc).moAssociationContext.toGraphDisplayString();	
				}
			    else if(oChildAssoc instanceof clsAssociationContent 
								&& ((clsAssociationContent<clsSecondaryInformation>)oChildAssoc).moAssociationContent != null){
						oName+=((clsAssociationContent<clsSecondaryInformation>)oChildAssoc).moAssociationContent.toGraphDisplayString();
				}
				
				if(oChildAssoc.moElementB instanceof clsSecondaryInformationMesh) {
					readMesh(poCellList, oCurrentVertex, (clsSecondaryInformationMesh)oChildAssoc.moElementB, oName );
				} 
				else if( oChildAssoc.moElementB instanceof clsSecondaryInformation ) {
					readSingle(poCellList, oCurrentVertex, (clsSecondaryInformation)oChildAssoc.moElementB, oName );
					
				}
			}
			return oCurrentVertex;
	}
	
	/**
	 * Creates a graph-cell for the clsThingPresentationSingle and connects the graph-cell 
	 * with an arrowed edge to the parent
	 *
	 * @author muchitsch
	 * 15.10.2009, 22:29:11
	 *
	 * @param poCellList
	 * @param poParent
	 * @param poPrimSingle
	 * @param poAssociationName
	 */
	private DefaultGraphCell readSingle(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsSecondaryInformation poSecondary, String poAssociationName) {

		String oVertexName = poSecondary.moWP.moContentName + ": \n " + poSecondary.moWP.moContent;
		DefaultGraphCell oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40);
		poCellList.add( oCurrentVertex );
		DefaultEdge edge = new DefaultEdge(poAssociationName);
		edge.setSource(poParent.getChildAt(0));
		edge.setTarget(oCurrentVertex.getChildAt(0));
		poCellList.add(edge);
		GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(edge.getAttributes(), true);
		return oCurrentVertex;
	}
	
	/**
	 * Creates a graph-cell for the clsAffect and connects the graph-cell 
	 * with an arrowed edge to the parent
	 *
	 * @author muchitsch
	 * 15.10.2009, 22:29:11
	 *
	 * @param poCellList
	 * @param poParent
	 * @param poAffecte
	 * @param poAssociationName
	 */
	private DefaultGraphCell readAffect(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsAffect poAffect, String poAssociationName) {

		DefaultGraphCell oCurrentVertex = null;
		
		if(poAffect != null ) {
			String oVertexName =  "Affect: \n " + poAffect.getValue();
			oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40);
			poCellList.add( oCurrentVertex );
			DefaultEdge edge = new DefaultEdge(poAssociationName);
			edge.setSource(poParent.getChildAt(0));
			edge.setTarget(oCurrentVertex.getChildAt(0));
			poCellList.add(edge);
			GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(edge.getAttributes(), true);
		}
		return oCurrentVertex;
	}
	
	/**
	 * static helper to create a graph-cell and set the default style within this inspector
	 *
	 * @author muchitsch
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
			double y, double w, double h) {

		//Richtext to enable linebreaks
		RichTextBusinessObject userObject = new RichTextBusinessObject();
		RichTextValue textValue = new RichTextValue(name);
		userObject.setValue(textValue);
		
		// Create vertex with the given name
		DefaultGraphCell cell = new DefaultGraphCell(userObject);

		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));
		GraphConstants.setGradientColor( cell.getAttributes(), Color.BLUE);
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
	 * @author muchitsch
	 * 15.10.2009, 22:44:13
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		/*
		if( source == moBtnUpdate) {
			updateControl();
		}
		*/
	}
}