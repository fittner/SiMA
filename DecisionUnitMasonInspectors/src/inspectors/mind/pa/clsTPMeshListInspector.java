/**
 * clsTPMeshListInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.10.2009, 21:53:56
 */
package inspectors.mind.pa;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import pa.datatypes.clsThingPresentationMesh;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 13.10.2009, 21:53:56
 * 
 */
public class clsTPMeshListInspector extends Inspector {

	private static final long serialVersionUID = 586283139693057158L;
	public Inspector moOriginalInspector;
	private Object moMeshContainer;
	private String moMeshListMemberName;
	private ArrayList<clsThingPresentationMesh> moMesh;
	mxGraphComponent moGraphFrame;
	mxGraph moGraph;
	
    public clsTPMeshListInspector(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            Object poMeshContainer,
            String poMeshListMemberName)
    {
		moOriginalInspector = originalInspector;
		moMeshContainer = poMeshContainer;
		moMeshListMemberName = poMeshListMemberName;
				
		updateInspector();
	
        setLayout(new BorderLayout());
		add(new JScrollPane(moGraphFrame), BorderLayout.CENTER);
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

		updateTPMeshData();
		
		moGraph = new mxGraph();		
		Object parent = moGraph.getDefaultParent();
		
		//GraphLayoutCache view = new GraphLayoutCache(model, new DefaultCellViewFactory());

		moGraph.getModel().beginUpdate();
		try
		{
			int i=0;
			for(clsThingPresentationMesh oTPMesh : moMesh) {
				
				moGraph.insertVertex(parent, null, oTPMesh.meContentName + ":\n" + oTPMesh.moContent, 200*i, 50, 150,
						30);
				i++;
			}
			
			
//			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
//					30);
//			Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
//					80, 30);
//			graph.insertEdge(parent, null, "Edge", v1, v2);
		}
		finally
		{
			moGraph.getModel().endUpdate();
		}

		if(moGraphFrame == null ) {
			moGraphFrame = new mxGraphComponent(moGraph);			
		}
		else {
			moGraphFrame.setGraph(moGraph);
		}
		moGraphFrame.refresh();
	}
}