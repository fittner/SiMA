/**
 * @author langr
 * 25.02.2009, 16:24:49
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package nao.body;


/**
 * 4th call in the overall body cycle (complete cycle see itfStep)
 * Executes the action commands (from thinking) within the entities actuators  
 * 
 * BD - Removed ActionContainer - logic
 * 
 * @author langr
 * 25.02.2009, 16:24:49
 * 
 */
public interface itfStepExecution extends itfStep {

	public void stepExecution();
	
}
