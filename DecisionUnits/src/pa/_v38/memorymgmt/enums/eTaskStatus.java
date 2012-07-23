/**
 * CHANGELOG
 *
 * 16.07.2012 wendt - File created
 *
 */
package pa._v38.memorymgmt.enums;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 16.07.2012, 13:34:39
 * 
 */
public enum eTaskStatus {
	NULLOBJECT,
	NOTHING,
	
	//Initial
	NEED_BASIC_ACT_ANALYSIS,		//Needs an act analysis. This option has to be triggered by an action
	NEED_GOAL_FOCUS,				//Needs a set focus
	NEED_INTERNAL_INFO,				//Trigger phantasy
	
	
	FOCUS_ON_SET,					//Focus set on the supportive data structure in the action
	FOCUS_MOVEMENTACTION_SET,		//It has been focused on in front of the agent		
	NEED_PERCEPTIONAL_INFO, 		//Trigger search
	PERFORM_RECOMMENDED_ACTION,		//For acts, perform the recommended action, which is saved with the act
	GOAL_NOT_REACHABLE,				//This is put on acts, where there is no match
	GOAL_REACHABLE,
	
	
	NEED_INTERNAL_INFO_SET;			//Phantasy was already triggered
}
