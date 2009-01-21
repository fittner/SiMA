/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw;

import bw.body.*;


/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public class clsAnimate extends clsEntity {

	/**
	 *  CTOR
	 */
	public clsAnimate() {
		super();
	}
	
	
	public clsAgentBody moAgentBody = new clsAgentBody(); // the instance of a body


}
