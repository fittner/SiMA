/**
 * @author Benny Dönz
 * 15.04.2009, 18:04:32
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.exceptions ;

/**
 * Exception when trying to bind more objects than possible 
 * 
 * @author Benny Dönz
 * 15.04.2009, 18:04:32
 * 
 */
public class exInvalidBinding extends exException {

	/* (non-Javadoc)
	 * @see bw.exceptions.bwException#toString()
	 */
	@Override
	public String toString() {
		return ("exInvalidBinding: Trying to bind more objects than possible.");
	}
}
