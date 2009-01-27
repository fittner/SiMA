/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import bw.body.clsAgentBody;
import bw.body.itfStep;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInternalIO implements itfStep {

	public clsAgentBody moBody;
	
	public clsInternalIO(clsAgentBody poBody) {
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
