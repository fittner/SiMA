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
	 * @see bw.body.itfStep#step()
	 */
	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
