/**
 * clsMeshKnowledgebase.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30.graph
 * 
 * @author deutsch
 * 21.04.2011, 14:25:28
 */
package inspectors.mind.pa._v30.graph;

import java.util.HashMap;

import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.informationrepresentation.clsInformationRepresentationManagement;

/**
 * 
 * 
 * @author deutsch
 * 21.04.2011, 14:25:28
 * 
 */
public class clsMeshKnowledgebase extends clsMeshBase {

	private clsInformationRepresentationManagement moKnowledgeBaseHandler;
	/**
	 * 
	 * 
	 * @author deutsch
	 * 21.04.2011, 14:25:41
	 */
	private static final long serialVersionUID = -753396202094570784L;

	public clsMeshKnowledgebase(clsInformationRepresentationManagement poKnowledgeBaseHandler) {
		super();
		moKnowledgeBaseHandler = poKnowledgeBaseHandler;
		setRootNodeName("KB");
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 14:25:28
	 * 
	 * @see inspectors.mind.pa._v30.graph.clsGraphBase#updateinspectorData()
	 */
	@Override
	protected void updateinspectorData() {
		HashMap<String, clsDataStructurePA> oTemp = moKnowledgeBaseHandler.moSearchSpaceHandler.getSearchSpace().getDataStructureTable();
	}

}
