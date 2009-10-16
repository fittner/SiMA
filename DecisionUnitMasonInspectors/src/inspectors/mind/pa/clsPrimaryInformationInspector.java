/**
 * clsTPMeshListInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.10.2009, 21:53:56
 */
package inspectors.mind.pa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import com.jgraph.example.JGraphGraphFactory;
import com.jgraph.layout.DataGraphLayoutCache;
import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphModelFacade;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;

import pa.datatypes.clsAssociationContext;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.datatypes.clsThingPresentationMesh;
import pa.datatypes.clsThingPresentationSingle;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * Inspector to draw a tree-graph of an ArrayList<clsThingPresentationMesh>
 * One root node gets each element within the list as child. Childs can be either 
 * TPMeshes (and will get further child nodes) or TPSingles (the deepest element in the tree)
 * 
 * The construct of passing the containing class and the name of the member-variable that is the ArrayList
 * is necessary to get the correct reference in each simulation step (the ArrayList itself is re-created 
 * in every step and therefore the data would not be changed)
 * 
 * @author langr
 * 13.10.2009, 21:53:56
 * 
 */
public class clsPrimaryInformationInspector  extends Inspector implements ActionListener {

	private static final long serialVersionUID = 586283139693057158L;
	public Inspector moOriginalInspector;
	private Object moMeshContainer;
	private String moMeshListMemberName;
	private ArrayList<clsPrimaryInformation> moPrimary;
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
    public clsPrimaryInformationInspector(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            Object poMeshContainer,
            String poMeshListMemberName)
    {
		moOriginalInspector = originalInspector;
		moMeshContainer = poMeshContainer;				//container class	
		moMeshListMemberName = poMeshListMemberName;	//member name of the list within the containing class
		
		updateControl();	//loading data into the graph
	
		moBtnUpdate = new JButton("Update graph...");	//create an update-button
		moBtnUpdate.addActionListener(this);
		
        setLayout(new BorderLayout());
        add(moBtnUpdate, BorderLayout.NORTH);
		add(moGraph, BorderLayout.CENTER);
    }

	/**
	 * loads the TPMesh-List from the corresponding container
	 *
	 * @author langr
	 * 13.10.2009, 22:34:11
	 *
	 */
	@SuppressWarnings("unchecked")
	private void updateTPMeshData() {
		try {
			Object oMeshList = moMeshContainer.getClass().getField(moMeshListMemberName).get(moMeshContainer);
			moPrimary = (ArrayList<clsPrimaryInformation>)oMeshList;
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
	
	public void updateControl() {

		// fetch the new ArrayList from the containing class
		updateTPMeshData();

		// Construct Model and GraphLayoutCache
		GraphModel model = new DefaultGraphModel();
		// When not using a JGraph instance, a GraphLayoutCache does not
		// automatically listen to model changes. Therefore, use a special
		// layout cache with a built-in listener
		GraphLayoutCache cache = new DataGraphLayoutCache(model,
				new DefaultCellViewFactory());
		
		//helper array-list to collect each created cell in the right order for the registration later on
		//without knowing the total number of elements
		ArrayList<DefaultGraphCell> oCellList = new ArrayList<DefaultGraphCell>();
		//create root node (it's a mesh-list) and add it to the registration list
		DefaultGraphCell oParent = createVertex(moMeshListMemberName+" (Primary Informations)", 20, 20, 150, 40);
		oCellList.add( oParent );
		//get graph-cells for each sub-thingpresentation of the mesh
		readPrimaryInfoList(oCellList, oParent, "");
		
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
		layout.setLevelDistance(15); //minimal distance from node to node vertical
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
	private void readPrimaryInfoList(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, String poAssociationName) {
		for(clsPrimaryInformation oPrim : moPrimary) {
			readPrim(poCellList, poParent, oPrim, poAssociationName);
		}
	}	
	
	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 15.10.2009, 22:56:34
	 *
	 * @param poCellList
	 * @param poParent
	 * @param prim
	 * @param poAssociationName
	 */
	private void readPrim(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsPrimaryInformation prim,
			String poAssociationName) {
		// TODO (langr) - Auto-generated method stub
		
	}

	/**
	 * Creates a graph-cell for the mesh, connects the graph-cell with an arrowed edge to the parent and
	 * recursively does the same for sub-meshes or sub-TPsingles.
	 *
	 * @author langr
	 * 15.10.2009, 22:25:39
	 *
	 * @param poCellList
	 * @param poParent
	 * @param poTPMesh
	 * @param poAssociationName
	 */
	private void readMesh(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsThingPresentationMesh poTPMesh, String poAssociationName) {

			String oVertexName = poTPMesh.meContentName + ":\n" + poTPMesh.moContent;
			DefaultGraphCell oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40);
			poCellList.add( oCurrentVertex );
			DefaultEdge edge = new DefaultEdge(poAssociationName);
			edge.setSource(poParent.getChildAt(0));
			edge.setTarget(oCurrentVertex.getChildAt(0));
			poCellList.add(edge);
			
			GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(edge.getAttributes(), true);
			
			for( clsAssociationContext<clsThingPresentation> oChildTPAssoc :  poTPMesh.moAssociations) {

				String oName = ""; //the edge will get the name of the association context
				if(oChildTPAssoc.moAssociationContext != null && oChildTPAssoc.moAssociationContext.meDriveContentCathegory != null) {
					oName+=oChildTPAssoc.moAssociationContext.meDriveContentCathegory.toString();	
				}
				
				if(oChildTPAssoc.moElementB instanceof clsThingPresentationSingle) {
					readSingle(poCellList, oCurrentVertex, (clsThingPresentationSingle)oChildTPAssoc.moElementB, oName );
				} 
				else if( oChildTPAssoc.moElementB instanceof clsThingPresentationMesh ) {

					readMesh(poCellList, oCurrentVertex, (clsThingPresentationMesh)oChildTPAssoc.moElementB, oName );
				}
			}
	}
	
	/**
	 * Creates a graph-cell for the clsThingPresentationSingle and connects the graph-cell 
	 * with an arrowed edge to the parent
	 *
	 * @author langr
	 * 15.10.2009, 22:29:11
	 *
	 * @param poCellList
	 * @param poParent
	 * @param poTPSingle
	 * @param poAssociationName
	 */
	private void readSingle(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsThingPresentationSingle poTPSingle, String poAssociationName) {

		String oVertexName = poTPSingle.meContentName + ": \n " + poTPSingle.moContent;
		DefaultGraphCell oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40);
		poCellList.add( oCurrentVertex );
		DefaultEdge edge = new DefaultEdge(poAssociationName);
		edge.setSource(poParent.getChildAt(0));
		edge.setTarget(oCurrentVertex.getChildAt(0));
		poCellList.add(edge);
		GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(edge.getAttributes(), true);
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
			double y, double w, double h) {

		// Create vertex with the given name
		DefaultGraphCell cell = new DefaultGraphCell(name);

		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));
		GraphConstants.setGradientColor( cell.getAttributes(), Color.LIGHT_GRAY);
		GraphConstants.setInset(cell.getAttributes(), 2);
		//GraphConstants.setAutoSize(cell.getAttributes(), true);
		GraphConstants.setOpaque(cell.getAttributes(), true);
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