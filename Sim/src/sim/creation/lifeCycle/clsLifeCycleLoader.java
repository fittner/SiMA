/**
 * @author deutsch
 * 12.05.2009, 18:14:45
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.lifeCycle;

import config.clsBWProperties;
import du.utils.enums.eDecisionType;
import sim.creation.clsLoader;
import sim.engine.SimState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 18:14:45
 * 
 * @deprecated
 */
public class clsLifeCycleLoader extends clsLoader {

	private int mnNumCarrots;
	private int mnNumHares;
	private int mnNumLynx;
	private int mnNumStones;
	private eDecisionType meHareDU;
	private eDecisionType meLynxDU;

	public clsLifeCycleLoader(SimState poSimState, String poPropertiesFilename, int pnWidth, int pnHeight, 
			                  int pnCarrots, int pnHares, int pnLynx, int pnStones,
			                  eDecisionType peHareDU, eDecisionType peLynxDU) {
		super(poSimState, poPropertiesFilename);
		
		meHareDU = peHareDU;
		meLynxDU = peLynxDU;
		
		mnNumCarrots = pnCarrots;
		mnNumHares = pnHares;
		mnNumLynx = pnLynx;
		mnNumStones = pnStones;
		
		createGrids(pnWidth, pnHeight);

		setTitle("Life Cycle");
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
		clsEntityLoader.loadCarrots(mnNumCarrots);
		clsEntityLoader.loadHares(mnNumHares, meHareDU);
		clsEntityLoader.loadLynx(mnNumLynx, meLynxDU);
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
