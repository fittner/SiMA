/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import sim.physics2D.shape.Shape;

import bw.utils.config.clsBWProperties;
import enums.eEntityType;


/**
 * Mason representative (physics+renderOnScreen) for a wall.  
 * 
 * FIXME: CLEMENS - REIMPLEMENT PLEASE!!!!
 * 
 * @author langr
 * 
 */
public class clsWall extends clsStationary  {
    
	public static final String P_LENGTH = "length"; 
	public static final String P_WIDTH = "width";
	public static final String P_POS_X = "pos_x";
	public static final String P_POS_Y = "pos_y";
	public static final String P_POS_ANGLE = "pos_angle";
	public static final String P_ID = "entity_ID";
	public static final String P_COLOR = "color";
	
	public double radius;
    
    public clsWall(String poPrefix, clsBWProperties poProp, Shape poShape) {
    	super(poPrefix, poProp, poShape);
    	
    	applyProperties(poPrefix, poProp, poShape);
    	
    	//FIXME direction of wall ...
    } 

	
	
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		return oProp;
	}	
		
	private void applyProperties(String poPrefix, clsBWProperties poProp, Shape poShape){		
		
	}	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.02.2009, 15:35:16
	 * 
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.WALL;
		
	}


}