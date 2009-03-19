/**
 * @author langr
 * 25.02.2009, 16:24:49
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import java.util.ArrayList;

import bw.body.motionplatform.clsBrainAction;
import bw.body.motionplatform.clsBrainActionContainer;

/**
 * 4th call in the overall body cycle (complete cycle see itfStep)
 * Executes the action commands (from thinking) within the entities actuators  
 * 
 * @author langr
 * 25.02.2009, 16:24:49
 * 
 */
public interface itfStepExecution extends itfStep {

	public void stepExecution(clsBrainActionContainer poActionList);
	
}
