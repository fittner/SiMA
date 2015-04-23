/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.decisioncodelets;

import secondaryprocess.functionality.decisionpreparation.clsCodelet;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:05:50
 * 
 */
public abstract class clsDecisionCodelet extends clsCodelet {

	
	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 22.09.2012 17:15:57
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 */
	public clsDecisionCodelet(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);

	}

}
