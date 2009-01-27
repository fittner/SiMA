/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;


import bw.clsEntity;
import bw.body.interBodyWorldSystems.clsInterBodyWorldSystem;
import bw.body.internalSystems.clsInternalSystem;
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
public class clsAgentBody implements itfStep {
	private clsBrain moBrain;
    private clsInternalSystem moInternalStates;
    private clsIntraBodySystem moIntraBodySystem;
    private clsInterBodyWorldSystem moInterBodyWorldSystem;
    private clsExternalIO moExternalIO;
    private clsInternalIO moInternalIO;
    
	/**
	 * CTOR
	 */
	public clsAgentBody(clsEntity poEntity) {
  	   moBrain = new clsBrain();
	   moInternalStates = new clsInternalSystem();
	   moIntraBodySystem = new clsIntraBodySystem();
	   moExternalIO = new clsExternalIO(poEntity, this);
	   moInternalIO = new clsInternalIO(this);
	   moInterBodyWorldSystem = new clsInterBodyWorldSystem();
	}
	
	
	
	/**
	 * @return the moBrain
	 */
	public clsBrain getBrain() {
		return moBrain;
	}



	/**
	 * @return the moInternalStates
	 */
	public clsInternalSystem getInternalStates() {
		return moInternalStates;
	}



	/**
	 * @return the moIntraBodySystem
	 */
	public clsIntraBodySystem getIntraBodySystem() {
		return moIntraBodySystem;
	}



	/**
	 * @return the moExternalIO
	 */
	public clsExternalIO getExternalIO() {
		return moExternalIO;
	}



	/**
	 * @return the moInternalIO
	 */
	public clsInternalIO getInternalIO() {
		return moInternalIO;
	}

	/**
	 * @return the moInterBodyWorldSystem
	 */
	public clsInterBodyWorldSystem getInterBodyWorldSystem() {
		return moInterBodyWorldSystem;
	}


	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	public void step() {
		moInternalStates.step();
		moIntraBodySystem.step();
		moExternalIO.step();
		moInternalIO.step();
		moInterBodyWorldSystem.step();
		
		moBrain.step();
	}
}
