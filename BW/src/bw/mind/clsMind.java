/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.mind;

import bw.body.itfStepProcessing;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsAnimate;

/**
 * Base class for every further specializations of the mind 
 * (rule, simple, classic, psy).
 * 
 * @author langr
 * 
 */
public class clsMind implements itfStepProcessing{

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.03.2009, 10:27:48
	 * 
	 * @see bw.body.itfStepProcessing#stepProcessing(bw.entities.clsAnimate, bw.body.motionplatform.clsBrainActionContainer)
	 */
	public void stepProcessing(clsAnimate poAnimate,
			clsBrainActionContainer poActionList) {
		// TODO Auto-generated method stub
		
	}
}
