/**
 * clsScenarioTransition.java: DecisionUnits - pa.loader.scenario
 * 
 * @author langr
 * 25.10.2009, 12:56:58
 */
package pa._v19.loader.plan;

import pa._v19.datatypes.clsSecondaryInformation;
import pa._v19.datatypes.clsWordPresentation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 25.10.2009, 12:56:58
 * 
 */
@Deprecated
public class clsPlanTransition extends clsSecondaryInformation {

	public int mnTargetId;
	public double mrMatch;

	public clsPlanTransition(clsWordPresentation poWordPresentation) {
		super(poWordPresentation);
		// TODO (langr) - Auto-generated constructor stub
	}

}
