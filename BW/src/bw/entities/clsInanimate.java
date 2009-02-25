/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import sim.physics2D.shape.Shape;
import sim.physics2D.util.Double2D;


/**
 * Inanimates represent dead objects (cannot grow, move, ...)
 * 
 * @author langr
 * 
 */
public abstract class clsInanimate extends clsMobile {

	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 25.02.2009, 17:27:06
	 *
	 * @param pos
	 * @param vel
	 * @param circle
	 * @param pnId
	 */
	public clsInanimate(Double2D poStartingPosition, Double2D poStartingVelocity, Shape poShape, double poMass, int pnId) {
		super(poStartingPosition, poStartingVelocity, poShape, poMass, pnId);
		// TODO Auto-generated constructor stub
	}



	
}
