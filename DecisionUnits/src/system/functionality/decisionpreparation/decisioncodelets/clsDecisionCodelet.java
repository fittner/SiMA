/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package system.functionality.decisionpreparation.decisioncodelets;

import system.functionality.decisionpreparation.clsCodelet;
import system.functionality.decisionpreparation.clsCodeletHandler;

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
