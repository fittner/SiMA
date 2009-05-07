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
		super(pnId, poStartingPose, poStartingVelocity, poShape, poMass, clsInanimate.getFinalConfig(poConfig));
		
		applyConfig();
	}


	private void applyConfig() {
		//TODO add ...

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}
	
}
