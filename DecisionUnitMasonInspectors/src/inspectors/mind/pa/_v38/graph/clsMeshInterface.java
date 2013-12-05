/**
 * clsMeshInterface.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v38.graph
 * 
 * @author deutsch
 * 21.04.2011, 12:53:46
 */
package inspectors.mind.pa._v38.graph;

import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.modules.clsPsychicApparatus;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 21.04.2011, 12:53:46
 * 
 */
public class clsMeshInterface extends clsMeshBase {
	private clsPsychicApparatus moPA;
	private eInterfaces meI;
	
	public clsMeshInterface(clsPsychicApparatus poPA, eInterfaces peI) {
		super();
		meI = peI;
		moPA = poPA;
		
		setRootNodeName(meI.toString());
		updateControl();	//loading data into the graph
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 21.04.2011, 12:53:50
	 */
	private static final long serialVersionUID = 4633221906357434261L;

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 12:53:46
	 * 
	 * @see inspectors.mind.pa._v38.graph.clsGraphBase#updateinspectorData()
	 */
	@Override
	protected void updateinspectorData() {
		moMesh = moPA.moInterfaceData.get(meI);
	}



}
