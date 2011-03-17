/**
 * clsSemanticInformationIspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author muchitsch
 * 24.10.2009, 23:58:33
 */
package inspectors.mind.pa._v30;


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


import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;




import pa.memorymgmt.datatypes.clsAct;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsSecondaryDataStructure;

import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.tools.clsPair;


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
	private Object moModuleContainer; //container of the memory. holds the Array list with the memory infos
	private String moModuleMemoryMemberName;
	private ArrayList moInspectorData;
	private boolean moAutoUpdate = false;
	private int moStepCounter = 0;
	
	private ArrayList<DefaultGraphCell> moCellList = new ArrayList<DefaultGraphCell>();
	
	//clors:
	private static Color moColorTP = Color.RED;
	private static Color moColorNULL = Color.BLACK;
	


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
    public clsSemanticInformationIspector(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            Object poModuleContainer,
            String poModuleMemoryMemberName)
    {
 	
		moOriginalInspector = originalInspector;		//
		moModuleContainer = poModuleContainer;				//container class 	
		moModuleMemoryMemberName = poModuleMemoryMemberName;	//member name of the list within the containing class
		
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
		
		oTaskGroupCommands.add(new AbstractAction("Insert Test Data") {
			private static final long serialVersionUID = 4963188240381232166L;
			@Override
			public void actionPerformed(ActionEvent e) {
				showTestData();
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
		
		javax.swing.JLabel oLabelTP = new javax.swing.JLabel("TP");
		oLabelTP.setBackground(moColorTP);
		oTaskGroupCommands.add(oLabelTP);
		
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
	private void updateinspectorData() {
		
		//Object oMeshList = moMeshContainer.getClass().getField(moMeshListMemberName).get(moMeshContainer);
		//moSecondary = (ArrayList<clsSecondaryInformation>)oMeshList;
		
		try {
			//returns the ArrayList of the wanted member
			Object oMeshList = moModuleContainer.getClass().getField(moModuleMemoryMemberName).get(moModuleContainer);
			
			//java.lang.Class[] parameterType = null; 
			//java.lang.reflect.Method method = moModuleContainer.getClass().getMethod( moModuleMemoryMemberName, parameterType ); 
			//java.lang.Object[] argument = null; 
			//Object oMeshList = method.invoke( moModuleContainer, argument );
			
			moInspectorData = (ArrayList)oMeshList;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
//		}catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
		}
			
			
		
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
	
	@Deprecated //just for testing purpose of the graph Layout, no bad code, but not needed.
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

		//per hakerl ein/aus ob das tab automatisch upgedatet werden soll, wenn ja dann updateControl() ausführen
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
	public void updateControl() {

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
		
		//helper array-list to collect each created cell in the right order for the registration later on
		//without knowing the total number of elements
		//ArrayList<DefaultGraphCell> oCellList = new ArrayList<DefaultGraphCell>();
		//create root node (it's a mesh-list) and add it to the registration list
		DefaultGraphCell oParent = createDefaultGraphVertex(moModuleMemoryMemberName+" (todo)", 20, 20, 150, 40, Color.GRAY);
		moCellList.add( oParent );
		//get graph-cells for each object in the of the mesh
		readInspectorDataAndGenerateGraphCells(oParent);
		
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
		//check for the 3 main data types possible
		for(int i=0;i<moInspectorData.size();i++){
		
			if(moInspectorData.get(i) instanceof clsDataStructurePA)
			{
				clsDataStructurePA oNextMemoryObject = (clsDataStructurePA)moInspectorData.get(i);
				generateGraphCell(poParent, oNextMemoryObject);
			}
			else if(moInspectorData.get(i) instanceof clsPair)
			{
				clsPair oNextMemoryObject = (clsPair)moInspectorData.get(i);
				generateGraphCell(poParent, oNextMemoryObject);
			}
			else if(moInspectorData.get(i) instanceof clsDataStructureContainer)
			{
				clsDataStructureContainer oNextMemoryObject = (clsDataStructureContainer)moInspectorData.get(i);
				generateGraphCell(poParent, oNextMemoryObject);
			}
			
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
	private DefaultGraphCell generateGraphCell(DefaultGraphCell poParentCell, clsPair poMemoryObject)
	{
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
			clsPair oNextMemoryObject = (clsPair)poMemoryObject.a;
			generateGraphCell(oPairCellA, oNextMemoryObject);
		}
		else if(poMemoryObject.a instanceof clsDataStructureContainer)
		{
			clsDataStructureContainer oNextMemoryObject = (clsDataStructureContainer)poMemoryObject.a;
			generateGraphCell(oPairCellA, oNextMemoryObject);
		}
		else
		{
			throw new java.lang.NoSuchMethodError("ARS Exeption: Type of pair.a not recognised!!!");
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
			clsPair oNextMemoryObject = (clsPair)poMemoryObject.b;
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
		ArrayList<clsAssociation> oAssociatedDataStructures = new ArrayList(); // poMemoryObject.moAssociatedDataStructures;
		
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
	public static DefaultGraphCell createDefaultGraphVertex(String name, double x,
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
	public static DefaultGraphCell createDefaultGraphVertex(String name, Color poNodeColor) {
		return createDefaultGraphVertex(name, 40, 40, 150, 40, poNodeColor);
	}
	
	public static DefaultGraphCell createCircleGraphVertex(String name, double x,
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
	public void groupCells(Object[] cells) {
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
	public void ungroupCells(Object[] cells) {
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
	  public class EllipseCell extends DefaultGraphCell {
	    // Empty Constructor
	    public EllipseCell() {
	      this(null);
	    }
	    // Construct Cell for Userobject
	    public EllipseCell(Object userObject) {
	      super(userObject);
	    }
	  }
	  
	  
//	// Define the View for an EllipseCell
//	  public class EllipseView extends VertexView {
//	    static EllipseRenderer renderer = new EllipseRenderer();
//	    // Constructor for Superclass
//	    public EllipseView(Object cell, GraphModel model,
//	              CellMapper cm) {
//	      super(cell, model, cm);
//	    }
//	    // Returns Perimeter Point for Ellipses
//	    public Point getPerimeterPoint(Point source, Point p) { ...
//	    }
//	    // Returns the Renderer for this View
//	    protected CellViewRenderer getRenderer() {
//	      return renderer;
//	    }
//	    // Define the Renderer for an EllipseView
//	    static class EllipseRenderer extends VertexRenderer {
//	      public void paint(Graphics g) { ... }
//	    }
//	  }
//
//	  http://dev.cs.uni-magdeburg.de/java/jgraph/tutorial/t1.html

}


