/**
 * @author deutsch
 * 12.05.2009, 18:14:45
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.lifeCycle;

import bw.sim.creation.clsLoader;
import sim.engine.SimState;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 18:14:45
 * 
 */
public class clsLifeCycleLoader extends clsLoader {

	private int mnNumPlants;
	private int mnNumHares;
	private int mnNumLynx;
	private int mnNumStones;

	public clsLifeCycleLoader(SimState poSimState, int pnWidth, int pnHeight, int pnPlants, int pnHares, int pnLynx, int pnStones) {
		super(poSimState);
		
		
		mnNumPlants = pnPlants;
		mnNumHares = pnHares;
		mnNumLynx = pnLynx;
		mnNumStones = pnStones;
		
		createGrids(pnWidth, pnHeight);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.05.2009, 18:15:23
	 * 
	 * @see bw.sim.creation.clsLoader#loadObjects()
	 */
	@Override
	public void loadObjects() {
		clsWorldBoundaries.loadWorldBoundaries();
		clsEntityLoader.loadStones(mnNumStones);
		clsEntityLoader.loadPlants(mnNumPlants);
		clsEntityLoader.loadHares(mnNumHares);
		clsEntityLoader.loadLynx(mnNumLynx);
	}	
}
