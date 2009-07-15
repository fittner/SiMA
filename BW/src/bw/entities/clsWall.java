/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import bw.utils.container.clsConfigMap;
import ARSsim.physics2D.util.clsPose;
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
	private static Color moDefaultColor = Color.LIGHT_GRAY;
    public double radius;
    
    public clsWall(int pnId, clsPose poPose, double prLength, double prWidth, clsConfigMap poConfig) {
    	super(pnId, poPose, new sim.physics2D.shape.Rectangle(prLength, prWidth, clsWall.moDefaultColor), clsWall.getFinalConfig(poConfig));
    	
    	applyConfig();
    	
    	//FIXME direction of wall ...
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