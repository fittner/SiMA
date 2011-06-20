/**
 * clsScenarioTransition.java: DecisionUnits - pa.loader.scenario
 * 
 * @author langr
 * 25.10.2009, 12:56:58
 */
package pa._v19.loader.scenario;

import pa._v19.datatypes.clsSecondaryInformation;
import pa._v19.datatypes.clsWordPresentation;

/**
 * 
 * @author langr
 * 25.10.2009, 12:56:58
 * 
 */
@Deprecated
public class clsScenarioTransition extends clsSecondaryInformation {

	public int mnTargetId;
	public double mrMatch;

	public clsScenarioTransition(clsWordPresentation poWordPresentation) {
		super(poWordPresentation);
		
	}

}
