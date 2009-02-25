/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import bw.body.clsAgentBody;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInternalIO extends clsBaseIO{

	public clsAgentBody moBody;
	
	public clsInternalIO(clsAgentBody poBody) {
		super(poBody.getInternalSystem().getInternalEnergyConsumption());

		moBody = poBody;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:51:12
	 * 
	 * @see bw.body.itfStepSensing#stepSensing()
	 */
	@Override
	public void stepSensing() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:51:12
	 * 
	 * @see bw.body.itfStepExecution#stepExecution()
	 */
	@Override
	public void stepExecution() {
		// TODO Auto-generated method stub
		
	}
	
}
