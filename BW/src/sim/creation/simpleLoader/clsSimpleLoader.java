/**
 * @author deutsch
 * 25.02.2009, 14:03:50
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.simpleLoader;

import sim.creation.clsLoader;
import sim.engine.SimState;


/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 25.02.2009, 14:03:50
 * 
 */
public class clsSimpleLoader extends clsLoader {

	private int mnNumCans;
	private int mnNumRemoteBots;
	private int mnNumStones;
	private int mnNumCakes;
	private int mnNumBots;

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
		super(poSimState);
		
		mnNumRemoteBots = 1;
		mnNumBots = 2;

		mnNumCans = 5;
		mnNumStones = 3;
		mnNumCakes = 1;
		
		createGrids(pnWidth, pnHeight);
		// TODO Auto-generated constructor stub
	}
	
	public clsSimpleLoader(SimState poSimState, int pnWidth, int pnHeight, int pnNumRemoteBots, int pnNumBots, int pnNumCans, int pnNumStones, int pnNumCakes) {
		super(poSimState);
		
		
		mnNumRemoteBots = pnNumRemoteBots;
		mnNumBots = pnNumBots;

		mnNumCans = pnNumCans;
		mnNumStones = pnNumStones;
		mnNumCakes = pnNumCakes;
		
		createGrids(pnWidth, pnHeight);
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
		clsEntityLoader.loadInanimate(mnNumCans, mnNumStones, mnNumCakes);	
		clsAgentLoader.loadAgents(mnNumRemoteBots, mnNumBots);
	}


}
