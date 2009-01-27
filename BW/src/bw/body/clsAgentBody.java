/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;


import bw.clsEntity;
import bw.body.internalSystems.clsInternalStates;
import bw.body.intraBodySystems.clsIntraBodySystem;
import bw.body.clsBrain;
import bw.body.io.clsExternalIO;
import bw.body.io.clsInternalIO;

/**
 * The agent body is the basic container for each entity the body needs: 
 * int./ext. I/O's,  flesh, int. states, biosys., brain 
 * 
 * 
 * @author langr
 * 
 */
public class clsAgentBody {
    public clsBrain moBrain;
    public clsInternalStates moInternalStates;
    public clsIntraBodySystem moIntraBodySystem;
    public clsExternalIO moExternalIO;
    public clsInternalIO moInternalIO;
    
	/**
	 * CTOR
	 */
	public clsAgentBody(clsEntity poEntity) {
  	   moBrain = new clsBrain();
	   moInternalStates = new clsInternalStates();
	   moIntraBodySystem = new clsIntraBodySystem();
	   moExternalIO = new clsExternalIO(poEntity, this);
	   moInternalIO = new clsInternalIO(this);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	public void step() {
		moInternalStates.step();
		moIntraBodySystem.step();
		moExternalIO.step();
		moInternalIO.step();
	}
}
