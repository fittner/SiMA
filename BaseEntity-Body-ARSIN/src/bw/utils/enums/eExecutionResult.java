/**
 * @author Benny Doenz
 * 15.04.2009, 18:45:26
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.enums;

/**
 * Execution result @see bw.body.io.actuators.clsActionProcessor
 * 
 * @author Benny Doenz
 * 15.04.2009, 18:45:26
 * 
 */
public enum eExecutionResult {

	EXECUTIONRESULT_ANY,
	EXECUTIONRESULT_EXECUTED,
	EXECUTIONRESULT_NOTEXECUTED,
	EXECUTIONRESULT_CONSTRAINTVIOLATION,
	EXECUTIONRESULT_DISABLED ,
	EXECUTIONRESULT_INHIBITED ,
	EXECUTIONRESULT_MUTUALEXCLUSION,
	EXECUTIONRESULT_NOSTAMINA
}
