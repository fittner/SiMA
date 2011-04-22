/**
 * clsDriveInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30.graph
 * 
 * @author deutsch
 * 22.04.2011, 18:26:26
 */
package inspectors.mind.pa._v30.graph;

import java.util.ArrayList;
import pa._v30.tools.clsQuadruppel;

import org.jgraph.graph.DefaultGraphCell;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.04.2011, 18:26:26
 * 
 */
public class clsDriveInspector extends clsGraphBase {
	private ArrayList<clsDriveMesh> moDriveList; 
	private ArrayList<clsQuadruppel <String, String, String, String> > moDrivePairs;

	public clsDriveInspector(ArrayList<clsDriveMesh> poDriveList, 
			ArrayList<clsQuadruppel <String, String, String, String> > poDrivePairs) {
		super();
		moDriveList = poDriveList;
		moDrivePairs = poDrivePairs;
	}
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 22.04.2011, 18:26:30
	 */
	private static final long serialVersionUID = -5931380754570369230L;

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.04.2011, 18:26:26
	 * 
	 * @see inspectors.mind.pa._v30.graph.clsGraphBase#updateinspectorData()
	 */
	@Override
	protected void updateinspectorData() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.04.2011, 18:26:26
	 * 
	 * @see inspectors.mind.pa._v30.graph.clsGraphBase#createGraph()
	 */
	@Override
	protected DefaultGraphCell createGraph() {
		// TODO (deutsch) - Auto-generated method stub
		return null;
	}

}
