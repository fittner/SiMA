/**
 * @author deutsch
 * 11.05.2009, 17:55:57
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import bw.body.motionplatform.clsBrainActionContainer;
import bw.utils.container.clsConfigMap;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.05.2009, 17:55:57
 * 
 */
public class clsSimpleBody extends clsBaseBody {

	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.05.2009, 17:56:02
	 *
	 * @param poConfig
	 */
	public clsSimpleBody(clsConfigMap poConfig) {
		super(poConfig);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.05.2009, 17:55:57
	 * 
	 * @see bw.body.itfStepSensing#stepSensing()
	 */
	@Override
	public void stepSensing() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.05.2009, 17:55:57
	 * 
	 * @see bw.body.itfStepUpdateInternalState#stepUpdateInternalState()
	 */
	@Override
	public void stepUpdateInternalState() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.05.2009, 17:55:57
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
	 * @author deutsch
	 * 11.05.2009, 17:55:57
	 * 
	 * @see bw.body.itfStepExecution#stepExecution(bw.body.motionplatform.clsBrainActionContainer)
	 */
	@Override
	public void stepExecution(clsBrainActionContainer poActionList) {
		// TODO Auto-generated method stub

	}

}
