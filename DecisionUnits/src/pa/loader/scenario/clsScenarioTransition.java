/**
 * clsScenarioTransition.java: DecisionUnits - pa.loader.scenario
 * 
 * @author langr
 * 25.10.2009, 12:56:58
 */
package pa.loader.scenario;

import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsWordPresentation;

/**
 * DOCUMENT (langr) - insert description 
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
		// TODO (langr) - Auto-generated constructor stub
	}

}
