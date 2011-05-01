/**
 * @author deutsch
 * 03.03.2009, 17:33:16
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package simple.rulebased;

import config.clsBWProperties;
import decisionunit.clsBaseDecisionUnit;
import du.enums.eDecisionType;

public class RuleBased extends clsBaseDecisionUnit {
	public RuleBased(String poPrefix, clsBWProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsBaseDecisionUnit.getDefaultProperties(poPrefix) );
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);

	}
	
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
	public void process() {
		// TODO (deutsch) - Auto-generated method stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:55:33
	 * 
	 * @see decisionunit.clsBaseDecisionUnit#setDecisionUnitType()
	 */
	@Override
	protected void setDecisionUnitType() {
		meDecisionType = eDecisionType.RULE_BASED;
		
	}
}
