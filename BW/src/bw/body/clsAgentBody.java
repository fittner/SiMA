/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;


import bw.body.internalSystems.clsInternalStates;
import bw.body.intraBodySystems.clsIntraBodySystem;
import bw.body.clsBrain;

/**
 * The agent body is the basic container for each entity the body needs: 
 * int./ext. I/O's,  flesh, int. states, biosys., brain 
 * 
 * 
 * @author langr
 * 
 */
public class clsAgentBody {
    private clsFlesh moFlesh;
    private clsBrain moBrain;
    private clsInternalStates moInternalStates;
    private clsIntraBodySystem moIntraBodySystem;
	
	/**
	 * CTOR
	 */
	public clsAgentBody() {
  	   moFlesh = new clsFlesh();
  	   moBrain = new clsBrain();
	   moInternalStates = new clsInternalStates();
	   moIntraBodySystem = new clsIntraBodySystem();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	public void step() {
		moInternalStates.step();
		moIntraBodySystem.step();
	}
}
