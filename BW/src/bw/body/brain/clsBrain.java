/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.brain;

import bw.body.itfStepProcessing;
import bw.body.brain.symbolization.clsSymbolization;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsAnimate;
import bw.mind.clsMind;
import bw.mind.ai.clsDumbMindA;

/**
 * The brain is the container for the mind and has a direct connection to external and internal IO.
 * Done: re-think if we insert a clsCerebellum for the neuroscientific perception-modules like R. Velik.
 * Answer: moSymbolization
 * 
 * @author langr
 * 
 */
public class clsBrain implements itfStepProcessing {

	public clsMind moMind;
	public clsSymbolization moSymbolization;
	
	public clsBrain() {
		moMind = new clsDumbMindA();
		
	}

	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
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

	/**
	 * @author langr
	 * 21.04.2009, 13:09:29
	 * 
	 * @return the moSymbolization
	 */
	public clsSymbolization getSymbolization() {
		return moSymbolization;
	}
	
}
