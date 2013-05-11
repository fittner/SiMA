package pa._v38.tools.planningHelpers.heuristics;

import pa._v38.tools.planningHelpers.clsAStarHeuristic;
import pa._v38.tools.planningHelpers.clsMover;
import pa._v38.tools.planningHelpers.clsTileBasedMap;

/**
 * A heuristic that drives the search based on the Manhattan distance
 * between the current location and the target
 * 
 * @author Kevin Glass / modified Andreas Perner
 */
public class clsManhattanHeuristic implements clsAStarHeuristic {
	/** The minimum movement cost from any one square to the next */
	private int minimumCost;
	
	/**
	 * Create a new heuristic 
	 * 
	 * @param minimumCost The minimum movement cost from any one square to the next
	 */
	public clsManhattanHeuristic(int minimumCost) {
		this.minimumCost = minimumCost;
	}
	
	/**
	 * @see clsAStarHeuristic#getCost(clsTileBasedMap, clsMover, int, int, int, int)
	 */
	@Override
	public float getCost(clsTileBasedMap map, clsMover mover, int x, int y, int tx,
			int ty) {
		return minimumCost * (Math.abs(x-tx) + Math.abs(y-ty));
	}

}
