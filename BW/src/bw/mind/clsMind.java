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
	 * @author deutsch
	 * 23.04.2009, 10:38:12
	 * 
	 * @see bw.body.itfStepProcessing#stepProcessing()
	 */
	@Override
	public clsBrainActionContainer stepProcessing() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.03.2009, 10:27:48
	 * 
	 * @see bw.body.itfStepProcessing#stepProcessing(bw.entities.clsAnimate, bw.body.motionplatform.clsBrainActionContainer)
	 */

}
