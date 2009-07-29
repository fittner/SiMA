/**
 * @author deutsch
 * 03.03.2009, 17:33:16
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package simple.rulebased;

import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.itfActionProcessor;


public class RuleBased extends clsBaseDecisionUnit {
/*	
	public clsBrainAction doThinking(clsSensorData Input) {
		clsBrainAction Result = null;
		
		if (Input.Bumped()) {
			Result = new clsBrainAction(eActionCommandType.STOPMOTION);
		} else if (Input.EnemySighted()) {
			Result = new clsBrainAction(eActionCommandType.ATTACK);
		} else if (Input.FoodSighted()) {
			Result = new clsBrainAction(eActionCommandType.EAT);
		} else {
			Result = new clsBrainAction(eActionCommandType.EXPLORE);
		}
		
		return Result;
	}
*/
	@Override
	public void process(itfActionProcessor poActionProcessor) {
		// TODO (deutsch) - Auto-generated method stub
	}
}
