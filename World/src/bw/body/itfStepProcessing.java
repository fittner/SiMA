/**
 * @author langr
 * 25.02.2009, 16:24:22
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

/**
 * 3rd call in the overall body cycle (complete cycle see itfStep)
 * 
 * After every sensor data and body states are updated, perception (e.g. neuro-symbolic) and
 * reasoning (e.g.  psychoanalytical approach) is called.
 * 
 * @author langr
 * 25.02.2009, 16:24:22
 * 
 */
public interface itfStepProcessing extends itfStep {

	public void stepProcessing();
	
}
