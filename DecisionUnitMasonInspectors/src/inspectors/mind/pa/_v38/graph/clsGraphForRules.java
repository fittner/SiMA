/**
 * CHANGELOG
 *
 * 01.11.2013 Jordakieva - File created
 *
 */
package inspectors.mind.pa._v38.graph;

import java.util.ArrayList;

import javax.swing.JSplitPane;


import pa._v38.interfaces.itfInspectorForRules;
import primaryprocess.functionality.superegofunctionality.clsReadSuperEgoRules;

/**
 * DOCUMENT (Jordakieva) - insert description 
 * 
 * @author Jordakieva
 * 01.11.2013, 16:05:13
 * 
 */
public class clsGraphForRules extends clsGraphWindow {

	private ArrayList<clsReadSuperEgoRules> moRules;
	
	/** DOCUMENT (Jordakieva) - insert description; @since 01.11.2013 16:05:50 */
	private static final long serialVersionUID = -924552491855248160L;

	/**
	 * DOCUMENT (Jordakieva) - insert description 
	 *
	 * @since 01.11.2013 16:05:22
	 *
	 * @param poOrtientationVertical
	 */
	public clsGraphForRules(boolean poOrtientationVertical, itfInspectorForRules poModule) {
		super(poOrtientationVertical);
		
		moRules = poModule.getDriverules();
		
		moGraphes.get(0).setRootNodeName("saved Rules");
		updateGraphes();
	}

	/* (non-Javadoc)
	 *
	 * @since 01.11.2013 16:05:13
	 * 
	 * @see inspectors.mind.pa._v38.graph.clsGraphWindow#createGraphes()
	 */
	@Override
	protected void createGraphes() {
		moGraphes = new ArrayList<clsGraph>(); // the graph
		moSplitPanes = new ArrayList<JSplitPane>(); //left side that allows me to manipulate the graph
		
		moGraphes.add(new clsGraph(mbOrientationVertical));	
	}

	/* (non-Javadoc)
	 *
	 * @since 01.11.2013 16:05:13
	 * 
	 * @see inspectors.mind.pa._v38.graph.clsGraphWindow#updateInspectorData()
	 */
	@Override
	protected void updateInspectorData() {
		
		ArrayList<Object> oMeshRules = new ArrayList<Object>();
		
		
		for (int i = 0, nRule = 1, max = moRules.size(); i < max; i++, nRule++) {
			oMeshRules.add (moRules.get(i));
		}
		
		moGraphes.get(0).moMesh = oMeshRules;		
	}

}
