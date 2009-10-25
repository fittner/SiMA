/**
 * clsPlanAction.java: DecisionUnits - pa.loader.plan
 * 
 * @author langr
 * 25.10.2009, 15:51:44
 */
package pa.loader.plan;

import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsWordPresentation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 25.10.2009, 15:51:44
 * 
 */
public class clsPlanAction extends clsSecondaryInformation {

	public boolean mnOnce = false;
	public boolean mnFinish = false;

	public clsPlanAction(clsWordPresentation poWordPresentation) {
		super(poWordPresentation);
		// TODO (langr) - Auto-generated constructor stub
	}

}
