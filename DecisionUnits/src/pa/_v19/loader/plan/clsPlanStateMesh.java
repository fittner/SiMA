/**
 * clsScenarioStateMesh.java: DecisionUnits - pa.loader.scenario
 * 
 * @author langr
 * 25.10.2009, 12:55:16
 */
package pa._v19.loader.plan;

import pa._v19.datatypes.clsSecondaryInformationMesh;
import pa._v19.datatypes.clsWordPresentation;

/**
 * 
 * @author langr
 * 25.10.2009, 12:55:16
 * 
 */
@Deprecated
public class clsPlanStateMesh extends clsSecondaryInformationMesh {

	public int mnId;

	public clsPlanStateMesh(clsWordPresentation poWordPresentation) {
		super(poWordPresentation);
	}

}
