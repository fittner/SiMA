/**
 * clsMeshKnowledgebase.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v38.graph
 * 
 * @author deutsch
 * 21.04.2011, 14:25:28
 */
package inspectors.mind.pa._v38.graph;

//import java.util.HashMap;

import java.util.ArrayList;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.old.clsKnowledgeBaseHandler;
import pa._v38.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 21.04.2011, 14:25:28
 * 
 */
public class clsMeshKnowledgebase extends clsMeshBase {

	private clsKnowledgeBaseHandler moKnowledgeBaseHandler;
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 21.04.2011, 14:25:41
	 */
	private static final long serialVersionUID = -753396202094570784L;

	public clsMeshKnowledgebase(clsKnowledgeBaseHandler poKnowledgeBaseHandler) {
		super();
		moKnowledgeBaseHandler = poKnowledgeBaseHandler;
		setRootNodeName("KB");
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 14:25:28
	 * 
	 * @see inspectors.mind.pa._v38.graph.clsGraphBase#updateinspectorData()
	 */
	@Override
	protected void updateinspectorData() {
		moMesh =  retrieveTPs();
		//HashMap<String, clsDataStructurePA> oTemp = moKnowledgeBaseHandler.initContainerRetrieval(poInput)moSearchSpaceHandler.getSearchSpace().getDataStructureTable();
	}
	
	private ArrayList<Object> retrieveTPs () {
		ArrayList<Object> oRetVal = new ArrayList<Object>();
		
		ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>>oSearchResult = new ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>>(); 
		
		oSearchResult = MemorySearch(eDataType.TP ); 
		oRetVal.addAll(oSearchResult);
		return oRetVal;
	}
	
	public ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> MemorySearch(
			eDataType poDataType
			) {
		
		ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult = new ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>>();
		
		//FIXME CM what to do in search pattern to get all TP's
		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>(); 

		//oSearchPattern = createSearchPattern(poDataType, poPerception);
		poSearchResult.addAll( moKnowledgeBaseHandler.initMemorySearch(oSearchPattern));
		
		//Set Instance values

		return poSearchResult;
	}

}
