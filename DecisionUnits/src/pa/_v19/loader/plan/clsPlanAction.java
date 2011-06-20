/**
 * clsPlanAction.java: DecisionUnits - pa.loader.plan
 * 
 * @author langr
 * 25.10.2009, 15:51:44
 */
package pa._v19.loader.plan;

import pa._v19.datatypes.clsSecondaryInformation;
import pa._v19.datatypes.clsWordPresentation;

/**
 * 
 * @author langr
 * 25.10.2009, 15:51:44
 * 
 */
@Deprecated
public class clsPlanAction extends clsSecondaryInformation {

	public boolean mnOnce = false;
	public boolean mnFinish = false;

	public clsPlanAction(clsWordPresentation poWordPresentation) {
		super(poWordPresentation);
		
	}

}
