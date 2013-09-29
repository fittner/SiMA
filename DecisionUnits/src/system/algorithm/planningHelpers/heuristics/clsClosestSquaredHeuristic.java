package system.algorithm.planningHelpers.heuristics;

import system.algorithm.planningHelpers.clsAStarHeuristic;
import system.algorithm.planningHelpers.clsMover;
import system.algorithm.planningHelpers.clsTileBasedMap;

/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile. In this case the sqrt is removed
 * and the distance squared is used instead
 * 
 * @author Kevin Glass / modified Andreas Perner
 */
public class clsClosestSquaredHeuristic implements clsAStarHeuristic {

	/**
	 * @see clsAStarHeuristic#getCost(clsTileBasedMap, clsMover, int, int, int, int)
	 */
	@Override
	public float getCost(clsTileBasedMap map, clsMover mover, int x, int y, int tx, int ty) {		
		float dx = tx - x;
		float dy = ty - y;
		
		return ((dx*dx)+(dy*dy));
	}

}
