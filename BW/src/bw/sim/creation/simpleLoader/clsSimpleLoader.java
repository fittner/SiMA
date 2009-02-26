/**
 * @author deutsch
 * 25.02.2009, 14:03:50
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.simpleLoader;

import sim.engine.SimState;
import bw.sim.creation.clsLoader;


/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 25.02.2009, 14:03:50
 * 
 */
public class clsSimpleLoader extends clsLoader {


	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 25.02.2009, 14:26:04
	 *
	 * @param poSimState
	 * @param pnWidth
	 * @param pnHeight
	 */
	public clsSimpleLoader(SimState poSimState, int pnWidth, int pnHeight) {
		super(poSimState, pnWidth, pnHeight);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 25.02.2009, 14:04:09
	 * 
	 * @see bw.sim.creation.clsLoader#loadObjects(sim.physics2D.PhysicsEngine2D)
	 */
	@Override
	public void loadObjects() {
		clsWorldBoundaries.loadWorldBoundaries();
		clsEntityLoader.loadInanimate(1, 2);	
		clsAgentLoader.loadAgents(1, 2);
	}


}
