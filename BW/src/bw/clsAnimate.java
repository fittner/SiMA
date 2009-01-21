/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw;

import sim.engine.SimState;
import tstBw.body.*;
import bw.body.clsAgentBody;


/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public class clsAnimate extends clsEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1734482865454985954L;
	
	public clsAgentBody moAgentBody; // the instance of a body

	/**
	 *  CTOR
	 */
	public clsAnimate() {
		super();
		
		 moAgentBody = new clsAgentBody();
	}
	
	

	public void step(SimState state) {
		// TODO Auto-generated method stub
		moAgentBody.step();
	}
}
