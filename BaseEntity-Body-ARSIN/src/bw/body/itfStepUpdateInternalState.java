/**
 * @author langr
 * 25.02.2009, 16:22:03
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

/**
 * 1nd call in the overall body cycle (complete cycle see itfStep)
 * 
 * Body-internal values are updated
 * 
 * @author langr
 * 25.02.2009, 16:22:03
 * 
 */
public interface itfStepUpdateInternalState extends itfStep {

	public void stepUpdateInternalState();
	
}
