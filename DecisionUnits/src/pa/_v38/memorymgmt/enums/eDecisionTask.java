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
public enum eDecisionTask {
	NULLOBJECT,
	NOTHING,
	
	NEED_FOCUS,						//Set focus
	GOAL_REACHABLE_PERCEPTION,		//Go to entity
	NEED_MORE_PERCEPTIONAL_INFO, 	//Trigger search
	NEED_INTERNAL_INFO;				//Trigger phantasy
}
