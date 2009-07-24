/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import bw.utils.config.clsBWProperties;
import bw.utils.enums.eShapeType;
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
    
	
	public double radius;
    
    public clsWall(String poPrefix, clsBWProperties poProp) {
    	super(poPrefix, poProp);
    	
    	applyProperties(poPrefix, poProp);
    	
    	//FIXME direction of wall ...
    } 

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsStationary.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_ENTITY_COLOR_RGB, Color.black);
		oProp.setProperty(pre+P_SHAPE_TYPE, eShapeType.SHAPE_RECTANGLE.name());
			
		return oProp;
	}	
		
	private void applyProperties(String poPrefix, clsBWProperties poProp){		
		
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