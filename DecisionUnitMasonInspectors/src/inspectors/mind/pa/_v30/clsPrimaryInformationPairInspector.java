/**
 * clsPrimaryInformationPairInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 18.10.2009, 15:08:13
 */
package inspectors.mind.pa._v30;

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
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;

import pa.datatypes.clsAffect;
import pa.datatypes.clsAssociation;
import pa.datatypes.clsAssociationContent;
import pa.datatypes.clsAssociationContext;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
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
import com.jgraph.layout.JGraphModelFacade;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 18.10.2009, 15:08:13
 * 
 */
public class clsPrimaryInformationPairInspector extends Inspector implements ActionListener {

	private static final long serialVersionUID = 586283139693057158L;
	public Inspector moOriginalInspector;
	private Object moMeshContainer;
	private String moMeshListMemberName;
	private ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moPrimaryPair;
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
    public clsPrimaryInformationPairInspector(Inspector originalInspector,
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
	private void updatePrimInfoData() {
		try {
			Object oMeshList = moMeshContainer.getClass().getField(moMeshListMemberName).get(moMeshContainer);
			moPrimaryPair = (ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>>)oMeshList;
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
		updatePrimInfoData();

		// Construct Model and GraphLayoutCache
		GraphModel model = new RichTextGraphModel();
		// When not using a JGraph instance, a GraphLayoutCache does not
		// automatically listen to model changes. Therefore, use a special
		// layout cache with a built-in listener
		GraphLayoutCache cache = new DataGraphLayoutCache(model,
				new DefaultCellViewFactory() {
			/**
					 * @author deutsch
					 * 10.08.2010, 18:00:15
					 */
					private static final long serialVersionUID = 8041363352918648603L;

			@Override
			protected VertexView createVertexView(Object cell) {
				return new MultiLineVertexView(cell);
			}
		}, true);
		
		//helper array-list to collect each created cell in the right order for the registration later on
		//without knowing the total number of elements
		ArrayList<DefaultGraphCell> oCellList = new ArrayList<DefaultGraphCell>();
		//create root node (it's a mesh-list) and add it to the registration list
		DefaultGraphCell oParent = createVertex(moMeshListMemberName+" (Primary Informations)", 20, 20, 150, 40, Color.LIGHT_GRAY);
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
	 * @author langr
	 * 14.10.2009, 16:59:24
	 *
	 * @param poCellList
	 * @param poParent
	 */
	private void readPrimaryInfoList(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, String poAssociationName) {
		for(clsPair<clsPrimaryInformation, clsPrimaryInformation> oPrimPair : moPrimaryPair) {
			
			DefaultGraphCell oCell = readPrim(poCellList, poParent, oPrimPair.a, poAssociationName, Color.LIGHT_GRAY);
			if(oPrimPair.b!=null) { //show attached primaryInfo too
				readPrim(poCellList, oCell, oPrimPair.b, poAssociationName, Color.GREEN);
			}
		}
	}	
	
	/**
	 * creates either the graph for a mesh or the singel, but adds the affect too.
	 *
	 * @author langr
	 * 15.10.2009, 22:56:34
	 *
	 * @param poCellList
	 * @param poParent
	 * @param prim
	 * @param poAssociationName
	 */
	private DefaultGraphCell readPrim(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsPrimaryInformation prim,
			String poAssociationName, Color poNodeColor) {

		DefaultGraphCell oCell = null;
		if(prim instanceof clsPrimaryInformationMesh) {
			oCell = readMesh(poCellList, poParent, (clsPrimaryInformationMesh)prim, poAssociationName, poNodeColor);
			readAffect(poCellList, oCell, (clsAffect)prim.moAffect, "affect_assoc", poNodeColor);
		}
		else if(prim instanceof clsPrimaryInformation) {
			oCell = readSingle(poCellList, poParent, (clsPrimaryInformation)prim, poAssociationName, poNodeColor);
			readAffect(poCellList, oCell, (clsAffect)prim.moAffect, "affect_assoc", poNodeColor);
		}
		return oCell;
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
	 * @param poPrimMesh
	 * @param poAssociationName
	 */
	private DefaultGraphCell readMesh(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsPrimaryInformationMesh poPrimMesh, String poAssociationName, Color poNodeColor) {

			String oVertexName = poPrimMesh.moTP.meContentName + ":\n" + poPrimMesh.moTP.moContent;
			DefaultGraphCell oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40, poNodeColor);
			poCellList.add( oCurrentVertex );
			DefaultEdge edge = new DefaultEdge(poAssociationName);
			edge.setSource(poParent.getChildAt(0));
			edge.setTarget(oCurrentVertex.getChildAt(0));
			poCellList.add(edge);
			
			GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(edge.getAttributes(), true);
			
			for( clsAssociation<clsPrimaryInformation> oChildAssoc :  poPrimMesh.moAssociations) {
				String oName = ""; //the edge will get the name of the association context
				
				if(oChildAssoc instanceof clsAssociationContext 
						&& ((clsAssociationContext<clsPrimaryInformation>)oChildAssoc).moAssociationContext != null) {
					oName+=((clsAssociationContext<clsPrimaryInformation>)oChildAssoc).moAssociationContext.toGraphDisplayString();	
				}
				else if(oChildAssoc instanceof clsAssociationContent 
						&& ((clsAssociationContent<clsPrimaryInformation>)oChildAssoc).moAssociationContent != null){
					oName+=((clsAssociationContent<clsPrimaryInformation>)oChildAssoc).moAssociationContent.toGraphDisplayString();
				}
							
				if(oChildAssoc.moElementB instanceof clsPrimaryInformationMesh) {
					readMesh(poCellList, oCurrentVertex, (clsPrimaryInformationMesh)oChildAssoc.moElementB, oName, poNodeColor );
				} 
				else if( oChildAssoc.moElementB instanceof clsPrimaryInformation ) {
					readSingle(poCellList, oCurrentVertex, (clsPrimaryInformation)oChildAssoc.moElementB, oName, poNodeColor );
					
				}
			}
			return oCurrentVertex;
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
	 * @param poPrimSingle
	 * @param poAssociationName
	 */
	private DefaultGraphCell readSingle(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsPrimaryInformation poPrimSingle, String poAssociationName, Color poNodeColor) {

		String oVertexName = poPrimSingle.moTP.meContentName + ": \n " + poPrimSingle.moTP.moContent;
		DefaultGraphCell oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40, poNodeColor);
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
	 * @author langr
	 * 15.10.2009, 22:29:11
	 *
	 * @param poCellList
	 * @param poParent
	 * @param poAffecte
	 * @param poAssociationName
	 */
	private DefaultGraphCell readAffect(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsAffect poAffect, String poAssociationName, Color poNodeColor) {

		DefaultGraphCell oCurrentVertex = null;
		
		if(poAffect != null ) {
			String oVertexName =  "Affect: \n " + poAffect.getValue();
			oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40, poNodeColor);
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
			double y, double w, double h, Color poNodeColor) {

		//Richtext to enable linebreaks
		RichTextBusinessObject userObject = new RichTextBusinessObject();
		RichTextValue textValue = new RichTextValue(name);
		userObject.setValue(textValue);
		
		// Create vertex with the given name
		DefaultGraphCell cell = new DefaultGraphCell(userObject);

		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));
		GraphConstants.setGradientColor( cell.getAttributes(), poNodeColor);
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