/**
 * @author langr
 * 25.02.2009, 16:24:09
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

/**
 * 2st call in the overall body cycle (complete cycle see itfStep)
 * 
 * Sensor update is triggered
 * 
 * @author langr
 * 25.02.2009, 16:24:09
 * 
 */
public interface itfStepSensing extends itfStep {

	public void stepSensing();
	
}
