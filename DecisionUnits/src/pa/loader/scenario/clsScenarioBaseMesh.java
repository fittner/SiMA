/**
 * clsScenarioBaseMesh.java: DecisionUnits - pa.loader.scenario
 * 
 * @author langr
 * 25.10.2009, 13:06:33
 */
package pa.loader.scenario;

import java.util.HashMap;

import pa.datatypes.clsAssociation;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.datatypes.clsWordPresentation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 25.10.2009, 13:06:33
 * 
 */
public class clsScenarioBaseMesh extends clsSecondaryInformationMesh{

	public HashMap<Integer, clsScenarioStateMesh> moStates;
	public int mnCurrentState = 0;

	public clsScenarioBaseMesh(clsWordPresentation poWordPresentation) {
		super(poWordPresentation);

		moStates = new HashMap<Integer, clsScenarioStateMesh>(); 

	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 25.10.2009, 13:08:40
	 *
	 * @param scenarioMesh
	 */
	public void addState(clsScenarioStateMesh poScenarioMesh) {

		moStates.put(poScenarioMesh.mnId, poScenarioMesh);
		moAssociations.add(new clsAssociation<clsSecondaryInformation>(this, poScenarioMesh));

	}

}
