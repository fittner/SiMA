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
import pa.datatypes.clsThingPresentation;
import pa.datatypes.clsThingPresentationMesh;
import pa.datatypes.clsThingPresentationSingle;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 13.10.2009, 21:53:56
 * 
 */
public class clsTPMeshListInspector extends Inspector implements ActionListener {

	private static final long serialVersionUID = 586283139693057158L;
	public Inspector moOriginalInspector;
	private Object moMeshContainer;
	private String moMeshListMemberName;
	private ArrayList<clsThingPresentationMesh> moMesh;
	JGraph moGraph = null;
	
	private JButton moBtnUpdate;
	
    public clsTPMeshListInspector(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            Object poMeshContainer,
            String poMeshListMemberName)
    {
		moOriginalInspector = originalInspector;
		moMeshContainer = poMeshContainer;
		moMeshListMemberName = poMeshListMemberName;
		
		updateControl();
	
		moBtnUpdate = new JButton("Update graph...");
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
	private void updateTPMeshData() {
		try {
			Object oMeshList = moMeshContainer.getClass().getField(moMeshListMemberName).get(moMeshContainer);
			moMesh = (ArrayList<clsThingPresentationMesh>)oMeshList;
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

		updateTPMeshData();

		// Construct Model and GraphLayoutCache
		GraphModel model = new DefaultGraphModel();
		// When not using a JGraph instance, a GraphLayoutCache does not
		// automatically listen to model changes. Therefore, use a special
		// layout cache with a built-in listener
		GraphLayoutCache cache = new DataGraphLayoutCache(model,
				new DefaultCellViewFactory());
		
		ArrayList<DefaultGraphCell> oCellList = new ArrayList<DefaultGraphCell>();
		DefaultGraphCell oParent = createVertex(moMeshListMemberName, 20, 20, 150, 40);
		oCellList.add( oParent );
		readMeshList(oCellList, oParent, "");
		
		DefaultGraphCell[] cells = new DefaultGraphCell[oCellList.size()];
		int i=0;
		for(DefaultGraphCell oCell : oCellList) {
			cells[i] = (DefaultGraphCell)oCell;
			i++;
		}
		// Insert the cells via the cache
		JGraphGraphFactory.insert(model, cells);
		// Create the layout facade. When creating a facade for the tree
		// layouts, pass in any cells that are intended to be the tree roots
		// in the layout
		JGraphFacade facade = new JGraphModelFacade(model, new Object[]{cells[0]});
		
		facade.setIgnoresUnconnectedCells(false);
		facade.setIgnoresCellsInGroups(false);
		facade.setIgnoresHiddenCells(false);
		facade.setDirected(true);
		
		// Create the layout to be applied
		JGraphCompactTreeLayout layout = new JGraphCompactTreeLayout();
		layout.setNodeDistance(15);
		layout.setLevelDistance(15);
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
		
		//layout.setAlignment(SwingConstants.NORTH);

		
//		moGraph.refresh();
		moGraph.updateUI();
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 14.10.2009, 16:59:24
	 *
	 * @param poCellList
	 * @param poParent
	 */
	private void readMeshList(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, String poAssociationName) {
		for(clsThingPresentationMesh oTPMesh : moMesh) {
			readMesh(poCellList, poParent, oTPMesh, poAssociationName);
		}
	}	
	
	private void readMesh(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsThingPresentationMesh poTPMesh, String poAssociationName) {

			DefaultGraphCell oCurrentVertex = createVertex(poTPMesh.meContentName + ":\n" + poTPMesh.moContent, 20, 20, 150, 40);
			poCellList.add( oCurrentVertex );
			DefaultEdge edge1 = new DefaultEdge(poAssociationName);
			edge1.setSource(poParent.getChildAt(0));
			edge1.setTarget(oCurrentVertex.getChildAt(0));
			poCellList.add(edge1);
			
			for( clsAssociationContext<clsThingPresentation> oChildTPAssoc :  poTPMesh.moAssociations) {

				String oName = "";
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
	
	private void readSingle(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsThingPresentationSingle poTPSingle, String poAssociationName) {

			DefaultGraphCell oCurrentVertex = createVertex(poTPSingle.meContentName + ":\n" + poTPSingle.moContent, 20, 20, 150, 40);
			poCellList.add( oCurrentVertex );
			DefaultEdge edge1 = new DefaultEdge(poAssociationName);
			edge1.setSource(poParent.getChildAt(0));
			edge1.setTarget(oCurrentVertex.getChildAt(0));
			poCellList.add(edge1);
	}
	
	public static DefaultGraphCell createVertex(String name, double x,
			double y, double w, double h) {

		// Create vertex with the given name
		DefaultGraphCell cell = new DefaultGraphCell(name);

		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));
		GraphConstants.setGradientColor( cell.getAttributes(), Color.LIGHT_GRAY);
		GraphConstants.setAutoSize(cell.getAttributes(), true);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		// Add a Port
		cell.addPort();

		return cell;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 14.10.2009, 19:59:28
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