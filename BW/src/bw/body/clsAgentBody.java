/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;


import bw.body.internalSystems.clsInternalStates;
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
    private clsBioSystem moBioSystem;
    private clsBrain moBrain;
    private clsInternalStates moInternalStates;
    private clsGrowthSystem moGrowthSystem;
	
	/**
	 * CTOR
	 */
	public clsAgentBody() {
  	   moFlesh = new clsFlesh();
  	   moBioSystem = new clsBioSystem();
  	   moBrain = new clsBrain();
	   moInternalStates = new clsInternalStates();
	   moGrowthSystem = new clsGrowthSystem();
	}
	


	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	public void step() {
		moInternalStates.step();
	}
}
