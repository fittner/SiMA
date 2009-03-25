/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.brain;

import bw.body.itfStep;
import bw.body.itfStepProcessing;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsAnimate;
import bw.mind.clsMind;
import bw.mind.ai.clsDumbMindA;

/**
 * The brain is the container for the mind and has a direct connection to external and internal IO.
 * TODO: re-think if we insert a clsCerebellum for the neuroscientific perception-modules like R. Velik.
 * 
 * @author langr
 * 
 */
public class clsBrain implements itfStepProcessing {

	public clsMind moMind;
	
	public clsBrain() {
		moMind = new clsDumbMindA();
		
	}

	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	@Override
	public void stepProcessing(clsAnimate poAnimate, clsBrainActionContainer poActionList) {

		moMind.stepProcessing(poAnimate, poActionList);
		
	}
	
	
	
	/*************************************************
	 *         GETTER & SETTER
	 ************************************************/
	
	/**
	 * @author langr
	 * 25.03.2009, 10:25:22
	 * 
	 * @return the moMind
	 */
	public clsMind getMind() {
		return moMind;
	}
	
}
