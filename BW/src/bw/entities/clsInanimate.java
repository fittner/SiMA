/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import bw.utils.container.clsConfigMap;
import ARSsim.physics2D.util.clsPose;
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
	public clsInanimate(int pnId, clsPose poStartingPose, Double2D poStartingVelocity, Shape poShape, double poMass, clsConfigMap poConfig) {
		super(pnId, poStartingPose, poStartingVelocity, poShape, poMass, poConfig);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 17:47:05
	 * 
	 * @see bw.entities.clsEntity#getDefaultConfig()
	 */
	@Override
	protected clsConfigMap getDefaultConfig() {
		// TODO Auto-generated method stub
		clsConfigMap oDefault = super.getDefaultConfig();
	
		
		return oDefault;
	}
	
}
