/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import bw.utils.config.clsBWProperties;
import sim.physics2D.shape.Shape;



/**
 * Inanimates represent dead objects (cannot grow, move, ...)
 * 
 * @author langr
 * 
 */
public abstract class clsInanimate extends clsMobile {

	public static final String P_POS_X = "pos_x";
	public static final String P_POS_Y = "pos_y";
	public static final String P_POS_ANGLE = "pos_angle";
	public static final String P_START_VELOCITY_X = "start_velocity_x";
	public static final String P_START_VELOCITY_Y = "start_velocity_y";
	public static final String P_MASS = "mass";
	public static final String P_ID = "entity_ID";
	
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
	
	public clsInanimate(String poPrefix, clsBWProperties poProp,  Shape poShape) {
		super(poPrefix, poProp, poShape);
		
		applyProperties(poPrefix, poProp, poShape);
	}


	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();

		oProp.setProperty(pre+P_POS_X, 0.0);
		oProp.setProperty(pre+P_POS_Y, 0.0);
		oProp.setProperty(pre+P_POS_ANGLE, 0.0);
		oProp.setProperty(pre+P_START_VELOCITY_X, 0.0);
		oProp.setProperty(pre+P_START_VELOCITY_Y, 0.0);
		

		return oProp;
	}	
		
	private void applyProperties(String poPrefix, clsBWProperties poProp, Shape poShape){		
		
	}	

}
