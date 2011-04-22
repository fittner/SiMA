/**
 * clsSemanticInformationIspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author muchitsch
 * 24.10.2009, 23:58:33
 */
package inspectors.mind.pa._v30.graph;


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
import javax.swing.AbstractButton;
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
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;
import sim.portrayal.Inspector;
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
public abstract class clsGraphBase extends Inspector implements ActionListener {
	
	/**
	 * @author deutsch
	 * 10.08.2010, 17:54:49
	 */
	private static final long serialVersionUID = 1698933486037476484L;
	
	//class members...
	private JGraph moGraph = new JGraph(new DefaultGraphModel());
	private JTaskPane moTaskPane = new JTaskPane();
	private JLabel moLabelStatusBar = new JLabel("Status...");
	private JCheckBox flushOriginCheckBox = new JCheckBox("Flush", true);
	private JGraphLayoutMorphingManager moMorpher = new JGraphLayoutMorphingManager();
	private boolean moAutoUpdate = false;
	private int moStepCounter = 0;
	
	protected ArrayList<DefaultGraphCell> moCellList = new ArrayList<DefaultGraphCell>();
	
	//colors:
	protected static Color moColorTP = Color.RED;
	protected static Color moColorNULL = Color.BLACK;
	


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
    public clsGraphBase()  {
		initializePanel();	//put all components on the panel		
    }
    
	/**
	 * loads the TPMesh-List from the corresponding container
	 *
	 * @author muchitsch
	 * 13.10.2009, 22:34:11
	 *
	 */
	protected abstract void updateinspectorData();
	protected abstract DefaultGraphCell createGraph();
	
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
		
		oTaskGroupCommands.add(new AbstractAction("Updata Data") {
			private static final long serialVersionUID = -7683571637499420675L;
			@Override
			public void actionPerformed(ActionEvent e) {
				updateControl();
			}
		});
		
		//
		javax.swing.JCheckBox oAutoUpdateCB = new javax.swing.JCheckBox("Auto Update");
		
		    ActionListener actionListener = new ActionListener() {
		        @Override
				public void actionPerformed(ActionEvent actionEvent) {
		          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		          moAutoUpdate = abstractButton.getModel().isSelected();
		          //oAutoUpdateCB.setText("!Auto Update!");
		        }
		      };
		      oAutoUpdateCB.addActionListener(actionListener);
		      oTaskGroupCommands.add(oAutoUpdateCB);
			
		oTaskGroupCommands.add(new AbstractAction("Actual Size") {
			private static final long serialVersionUID = -7683571637499420675L;
			@Override
			public void actionPerformed(ActionEvent e) {
				moGraph.setScale(1);
			}
		});
		oTaskGroupCommands.add(new AbstractAction("Fit Window") {
			private static final long serialVersionUID = -4236402393050941924L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JGraphLayoutMorphingManager.fitViewport(moGraph);
			}
		});
		oTaskGroupCommands.add(new AbstractAction("Zoom In") {
			private static final long serialVersionUID = 4963188240381232166L;
			@Override
			public void actionPerformed(ActionEvent e) {
				moGraph.setScale(1.5 * moGraph.getScale());
			}
		});
		oTaskGroupCommands.add(new AbstractAction("Zoom Out") {
			private static final long serialVersionUID = 6518488789619229086L;
			@Override
			public void actionPerformed(ActionEvent e) {
				moGraph.setScale(moGraph.getScale() / 1.5);
			}
		});
		oTaskGroupCommands.add(new AbstractAction("Reset") {
			private static final long serialVersionUID = 4769006307236101696L;
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		oTaskGroupCommands.add(new AbstractAction("Group") {
			private static final long serialVersionUID = 4769006307236101696L;
			@Override
			public void actionPerformed(ActionEvent e) {
				groupCells(moGraph.getSelectionCells());
			}
		});
		oTaskGroupCommands.add(new AbstractAction("UnGroup") {
			private static final long serialVersionUID = 4769006307236101696L;
			@Override
			public void actionPerformed(ActionEvent e) {
				ungroupCells(moGraph.getSelectionCells());
			}
		});
		
		moTaskPane.add(oTaskGroupCommands);
		
		// === SEARCH ===
		// ADD TaskPaneGroup for Search
		JTaskPaneGroup oTaskGroupSearch = new JTaskPaneGroup();
		oTaskGroupSearch.setTitle("Search");
		moTaskPane.add(oTaskGroupSearch);
		
		// === Legend ===
		// ADD TaskPaneGroup for Legend
		JTaskPaneGroup oTaskGroupLegend = new JTaskPaneGroup();
		oTaskGroupLegend.setTitle("Legend");
		moTaskPane.add(oTaskGroupLegend);
		
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
	 * Executes the current layout on the current graph by creating a facade and
	 * progress monitor for the layout and invoking it's run method in a
	 * separate thread so this method call returns immediately. To display the
	 * result of the layout algorithm a {@link JGraphLayoutMorphingManager} is
	 * used.
	 */
    private void performGraphLayoutChange(final JGraphLayout layout) 
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
										Map<?,?> map = facade.createNestedMap(true,
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
    private JGraphFacade createFacade(JGraph graph) {
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
	 * Resets the graph to a circular layout.
	 */
    private void reset() {
		performGraphLayoutChange(new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE));
		moGraph.clearSelection();
		JGraphLayoutMorphingManager.fitViewport(moGraph);
		
		moLabelStatusBar.setText("JGraph reseted to start");
	}

	/**
	 * Hook from GraphEd to set attributes of a new cell
	 */
    private Map<?,?> createCellAttributes(Point2D point) {
		@SuppressWarnings("rawtypes")
		Map<?,?> map = new Hashtable();
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
    private Map<?,?> createEdgeAttributes() {
		@SuppressWarnings("rawtypes")
		Map<?,?> map = new Hashtable();
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

		//per hakerl ein/aus ob das tab automatisch upgedatet werden soll, wenn ja dann updateControl() ausf�hren
		if(moAutoUpdate && moStepCounter > 119)
		{
			moStepCounter = 0;
			moCellList.clear();
			updateControl();
		}
		else
		{
			moStepCounter++;
		}
	}
	
	/**
	 * DOCUMENT Main method for displaying inspector data. Generates the graph layout and starts the data display...
	 *
	 * @author muchitsch
	 * 25.08.2010, 14:02:45
	 *
	 */
	protected void updateControl() {

		//clear the cell list, or u get doubles, tripples, ...
		moCellList.clear();
		
		
		// fetch the new ArrayList from the containing class
		updateinspectorData();

		// Construct Model and GraphLayoutCache
		GraphModel model = new RichTextGraphModel();
		// When not using a JGraph instance, a GraphLayoutCache does not
		// automatically listen to model changes. Therefore, use a special
		// layout cache with a built-in listener
		GraphLayoutCache cache = new DataGraphLayoutCache(model,
				new DefaultCellViewFactory() {
					private static final long serialVersionUID = 5527702598146461914L;
					@Override
				protected VertexView createVertexView(Object cell) {
						 // Return an EllipseView for EllipseCells
					   // TODO... if (cell instanceof EllipseCell)
					   //   return new EllipseView(cell);
					    // Else Call Superclass
					    return new MultiLineVertexView(cell);
					}
				}, true);
		
		moCellList.add( createGraph() );
		
		//transfer graph-cells from arraylist to fix-size array (needed for registration)
		DefaultGraphCell[] cells = new DefaultGraphCell[moCellList.size()];
		for(int i=0; i<moCellList.size(); i++) {
			cells[i] = (DefaultGraphCell)moCellList.get(i);
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
		Map<?,?> nested = facade.createNestedMap(true, true);
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
	 * static helper to create a graph-cell and set the default style within this inspector
	 *
	 * @author muchitsch
	 * 15.10.2010, 22:31:41
	 *
	 * @param name
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	protected DefaultGraphCell createDefaultGraphVertex(String name, double x,
			double y, double w, double h, Color poNodeColor) {

		name = name.replace("|", "\n");
		
		//Richtext to enable linebreaks
		RichTextBusinessObject userObject = new RichTextBusinessObject();
		RichTextValue textValue = new RichTextValue(name);
		userObject.setValue(textValue);
		
		// Create vertex with the given name
		DefaultGraphCell cell = new DefaultGraphCell(userObject);

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
	protected DefaultGraphCell createDefaultGraphVertex(String name, Color poNodeColor) {
		return createDefaultGraphVertex(name, 40, 40, 150, 40, poNodeColor);
	}
	
	protected DefaultGraphCell createCircleGraphVertex(String name, double x,
			double y, double w, double h, Color poNodeColor) {

		
		//Richtext to enable linebreaks
		RichTextBusinessObject userObject = new RichTextBusinessObject();
		RichTextValue textValue = new RichTextValue(name);
		userObject.setValue(textValue);
		
		// Create vertex with the given name
		DefaultGraphCell cell = new DefaultGraphCell(userObject);

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
	
	// Create a Group that Contains the Cells
	private void groupCells(Object[] cells) {
		// Order Cells by Model Layering
		cells = moGraph.order(cells);
		// If Any Cells in View
		if (cells != null && cells.length > 0) {
			DefaultGraphCell group = createDefaultGraphVertex("GROUP", 30, 30, 30, 30, Color.CYAN);
			// Insert into model
			moGraph.getGraphLayoutCache().insertGroup(group, cells);
		}
	}
	
	// Ungroup the Groups in Cells and Select the Children
	private void ungroupCells(Object[] cells) {
		moGraph.getGraphLayoutCache().ungroup(cells);
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
		//Object source = e.getSource(); //never used!
		/*
		if( source == moBtnUpdate) {
			updateControl();
		}
		*/
	}
	
	// Define EllipseCell
	// TODO (MUCHITSCH): create circles!!! and refactor this into a seperate file
	public class EllipseCell extends DefaultGraphCell {
	    /**
		 * DOCUMENT (deutsch) - insert description 
		 * 
		 * @author deutsch
		 * 21.04.2011, 12:35:07
		 */
		private static final long serialVersionUID = -5269098274256292503L;
		// Empty Constructor
	    public EllipseCell() {
	      this(null);
	    }
	    // Construct Cell for Userobject
	    public EllipseCell(Object userObject) {
	      super(userObject);
	    }
	 }
}

