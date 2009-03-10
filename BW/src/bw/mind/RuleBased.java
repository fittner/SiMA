/**
 * @author deutsch
 * 03.03.2009, 17:33:16
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.mind;

import bw.body.motionplatform.clsBrainAction;
import bw.utils.enums.eActionCommandType;

public class RuleBased extends clsMind {
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
}
