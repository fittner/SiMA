/**
 * @author deutsch
 * 25.02.2009, 14:03:50
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.simpleLoader;

import bw.utils.config.clsBWProperties;
import sim.creation.clsLoader;
import sim.engine.SimState;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 25.02.2009, 14:03:50
 * 
 * @deprecated
 */
public class clsSimpleLoader extends clsLoader {

	private int mnNumCans;
	private int mnNumRemoteBots;
	private int mnNumStones;
	private int mnNumCakes;
	private int mnNumBots;
	private int mnNumFungusEaters;
	private int mnNumFungi;
	private int mnNumUraniumOre;
	private int mnNumBases;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 25.02.2009, 14:26:04
	 *
	 * @param poSimState
	 * @param pnWidth
	 * @param pnHeight
	 */
	public clsSimpleLoader(SimState poSimState, String poPropertiesFilename, int pnWidth, int pnHeight) {
		super(poSimState, poPropertiesFilename);
		
		mnNumRemoteBots = 1;
		mnNumBots = 2;
		mnNumFungusEaters = 2;

		mnNumCans = 5;
		mnNumStones = 3;
		mnNumCakes = 1;
		mnNumFungi = 1;
		mnNumUraniumOre = 1;
		mnNumBases = 1;
		
		createGrids(pnWidth, pnHeight);
		// TODO (deutsch) - Auto-generated constructor stub
	}
	
	public clsSimpleLoader(SimState poSimState, String poPropertiesFilename, int pnWidth, int pnHeight, int pnNumRemoteBots, int pnNumBots, int pnNumFungusEaters, int pnNumCans, int pnNumStones, int pnNumCakes, int pnNumFungi, int pnNumUraniumOre, int pnNumBases) {
		super(poSimState, poPropertiesFilename);
		
		
		mnNumRemoteBots = pnNumRemoteBots;
		mnNumBots = pnNumBots;
		mnNumFungusEaters = pnNumFungusEaters;

		mnNumCans = pnNumCans;
		mnNumStones = pnNumStones;
		mnNumCakes = pnNumCakes;
		mnNumCakes = pnNumCakes;
		mnNumFungi = pnNumFungi;
		mnNumUraniumOre = pnNumUraniumOre;
		mnNumBases = pnNumBases;
		
		createGrids(pnWidth, pnHeight);

		setTitle("Simple Loader");
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
		clsEntityLoader.loadInanimate(mnNumCans, mnNumStones, mnNumCakes, mnNumFungi, mnNumUraniumOre, mnNumBases);	
		clsAgentLoader.loadAgents(mnNumRemoteBots, mnNumBots, mnNumFungusEaters);
	}

	/* (non-Javadoc)
	 *
	 * @author tobias
	 * Jul 26, 2009, 3:31:01 PM
	 * 
	 * @see sim.creation.clsLoader#checkVersionCompatibility(java.lang.String, bw.utils.config.clsBWProperties)
	 */
	@Override
	protected void checkVersionCompatibility(String poPrefix,
			clsBWProperties poProp) {
		// nothing to do - this is the first and the last version - moreove this loader does not read files ...
	}
	
	/* (non-Javadoc)
	 *
	 * @author tobias
	 * Jul 26, 2009, 3:39:27 PM
	 * 
	 * @see sim.creation.clsLoader#verifyLoaderType(java.lang.String, bw.utils.config.clsBWProperties)
	 */
	@Override
	protected void verifyLoaderType(String poPrefix, clsBWProperties poProp) {
		// nothing to do - this loader is deprecated
		
	}	
}
