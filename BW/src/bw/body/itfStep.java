/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

/**
 * This interface defines which functions objects which are to be called each simulation step have to implement.
 * 
 * @author deutsch
 * 
 */
public interface itfStep {
	/**
	 * This function should be called by the class owner each simulation step.
	 *
	 */
	public void step();
}
