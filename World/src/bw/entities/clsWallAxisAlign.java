/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import config.clsProperties;
import du.enums.eEntityType;

import bw.entities.tools.clsShapeCreator;
import bw.utils.enums.eShapeType;


/**
 * Mason representative (physics+renderOnScreen) for a wall.  
 * 
 * FIXME: CLEMENS - REIMPLEMENT PLEASE!!!!
 * 
 * @author langr
 * 
 */
public class clsWallAxisAlign extends clsStationary  {	
    public clsWallAxisAlign(String poPrefix, clsProperties poProp, int uid) {
    	super(poPrefix, poProp, uid);
    	
    	applyProperties(poPrefix, poProp);
    } 

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsStationary.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_TYPE, eShapeType.RECTANGLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_WIDTH, 10);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_HEIGHT, 10);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_COLOR, Color.black);
			
		return oProp;
	}	
		
	private void applyProperties(String poPrefix, clsProperties poProp){		
		// nothing to do
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