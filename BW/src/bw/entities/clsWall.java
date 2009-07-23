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
    
	public static final String P_ID = "entity_ID";
	public static final String P_ENTIY_COLOR_B = "colorB";
	public static final String P_ENTIY_COLOR_G = "colorG";
	public static final String P_ENTIY_COLOR_R = "colorR";
	public static final String P_MOBILE_SHAPE_TYPE = "shape_type"; 
	
	public double radius;
    
    public clsWall(String poPrefix, clsBWProperties poProp) {
    	super(poPrefix, poProp);
    	
    	applyProperties(poPrefix, poProp);
    	
    	//FIXME direction of wall ...
    } 

	
	
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsStationary.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_ENTIY_COLOR_B, Color.black.getBlue());
		oProp.setProperty(pre+P_ENTIY_COLOR_B, Color.black.getBlue());
		oProp.setProperty(pre+P_ENTIY_COLOR_G, Color.black.getGreen());
		oProp.setProperty(pre+P_SHAPE_TYPE, "SHAPE_CIRCLE");
			
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