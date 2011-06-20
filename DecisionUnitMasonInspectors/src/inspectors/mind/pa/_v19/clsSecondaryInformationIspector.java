/**
 * clsSecondaryInformationIspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 24.10.2009, 23:58:33
 */
package inspectors.mind.pa._v19;

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

import pa._v19.datatypes.clsAffect;
import pa._v19.datatypes.clsAssociation;
import pa._v19.datatypes.clsAssociationContent;
import pa._v19.datatypes.clsAssociationContext;
import pa._v19.datatypes.clsSecondaryInformation;
import pa._v19.datatypes.clsSecondaryInformationMesh;
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
 *
 * 
 * @author langr
 * 24.10.2009, 23:58:33
 * 
 */
@Deprecated
@SuppressWarnings("rawtypes")
public class clsSecondaryInformationIspector extends Inspector implements ActionListener {

	private static final long serialVersionUID = 586283139693057158L;
	public Inspector moOriginalInspector;
	private Object moMeshContainer;
	private String moMeshListMemberName;
	private ArrayList<clsSecondaryInformation> moSecondary;
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
    public clsSecondaryInformationIspector(Inspector originalInspector,
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
	private void updateSecInfoData() {
		try {
			Object oMeshList = moMeshContainer.getClass().getField(moMeshListMemberName).get(moMeshContainer);
			moSecondary = (ArrayList<clsSecondaryInformation>)oMeshList;
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
		updateSecInfoData();

		// Construct Model and GraphLayoutCache
		GraphModel model = new RichTextGraphModel();
		// When not using a JGraph instance, a GraphLayoutCache does not
		// automatically listen to model changes. Therefore, use a special
		// layout cache with a built-in listener
		GraphLayoutCache cache = new DataGraphLayoutCache(model,
				new DefaultCellViewFactory() {
			/**
					 * @author deutsch
					 * 10.08.2010, 18:00:23
					 */
					private static final long serialVersionUID = 4592321719003039384L;

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
	 * @author langr
	 * 14.10.2009, 16:59:24
	 *
	 * @param poCellList
	 * @param poParent
	 */
	private void readSecondaryInfoList(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, String poAssociationName) {
		for(clsSecondaryInformation oSec : moSecondary) {
			readSec(poCellList, poParent, oSec, poAssociationName);
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
	 * @author langr
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
	 * @author langr
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
	 * @author langr
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